/*
 * Copyright (c) 2021 Google LLC
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Google designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Google in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.google.devtools.build.android;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.devtools.build.android.AsmHelpers.ASM_API_LEVEL;
import static com.google.devtools.build.android.AsmHelpers.getReplacementTypeInternalName;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;

import com.google.common.collect.ImmutableList;
import java.lang.invoke.LambdaMetafactory;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/** Generates classes with desugar-supported APIs enclosed. */
public class DesugarSupportedApiClassGenerator {

  /** The class node based on which new class nodes with supported APIs are to be generated. */
  private final ClassNode baseClassNode;

  private final PreScanner preScanner;

  public DesugarSupportedApiClassGenerator(ClassNode baseClassNode, PreScanner preScanner) {
    this.baseClassNode = baseClassNode;
    this.preScanner = preScanner;
  }

  private class LambdaTargetOwnerRewriter extends MethodVisitor {

    private final String lambdaMetafactoryInternalName =
      // Expected to be "java/lang/invoke/LambdaMetafactory"
      Type.getType(LambdaMetafactory.class).getInternalName();

    private final String baseName = baseClassNode.name;
    private final String rewrittenName = getReplacementTypeInternalName(baseClassNode.name);

    public LambdaTargetOwnerRewriter(int api, MethodVisitor mv) {
      super(api, mv);
    }

    @Override
    public void visitInvokeDynamicInsn(
        String name,
        String descriptor,
        Handle bootstrapMethodHandle,
        Object... bootstrapMethodArguments) {
      if (bootstrapMethodHandle.getOwner().equals(lambdaMetafactoryInternalName)) {
        if (bootstrapMethodHandle.getName().equals("metafactory")) {
          Object[] newArgs = bootstrapMethodArguments.clone();
          if (newArgs.length != 3 || !(newArgs[1] instanceof Handle)) {
            throw new AssertionError(
                "Unsupported desugaring with invalid arguments in lambda metafactory in the class "
                    + baseName
                    + " (number of arguments "
                    + newArgs.length
                    + ")");
          }
          Handle handle = (Handle) newArgs[1];
          if (handle.getOwner().equals(baseName)) {
            newArgs[1] =
                new Handle(
                    handle.getTag(),
                    rewrittenName,
                    handle.getName(),
                    handle.getDesc(),
                    handle.isInterface());
          }
          super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, newArgs);
          return;
        }
        if (bootstrapMethodHandle.getName().equals("altMetafactory")) {
          throw new AssertionError(
              "Unsupported desugaring with lambda altMetafactory in the class " + baseName);
        }
      }
      super.visitInvokeDynamicInsn(
          name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }
  }

  /** Generates a list of class nodes that enclose supported APIs. */
  public ImmutableList<ClassNode> getClassNodesWithSupportedMembers() {
    ImmutableList<FieldNode> allDesugarSupportedFields = findAllDesugarSupportedFields();
    ImmutableList<MethodNode> allDesugarSupportedClassMembers = findAllDesugarSupportedMethods();
    if (allDesugarSupportedFields.isEmpty() && allDesugarSupportedClassMembers.isEmpty()) {
      return ImmutableList.of();
    }

    ClassNode generatedClassNode =
        new ClassNode(AsmHelpers.ASM_API_LEVEL) {
          @Override
          public MethodVisitor visitMethod(
              int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor subvisitor =
                super.visitMethod(access, name, descriptor, signature, exceptions);
            return new LambdaTargetOwnerRewriter(AsmHelpers.ASM_API_LEVEL, subvisitor);
          }
        };

    generatedClassNode.visit(
        AsmHelpers.JAVA_LANGUAGE_LEVEL,
        /* access= */ ACC_SYNTHETIC | ACC_PUBLIC | ACC_SUPER | ACC_FINAL,
        getReplacementTypeInternalName(baseClassNode.name),
        /* signature= */ null,
        /* superName= */ "java/lang/Object",
        /* interfaces= */ new String[] {});

    MethodNode privateConstructorNode =
        new MethodNode(
            ASM_API_LEVEL,
            ACC_PRIVATE,
            "<init>",
            "()V",
            /* signature= */ null,
            /* exceptions= */ null);
    privateConstructorNode.visitCode();
    privateConstructorNode.visitVarInsn(Opcodes.ALOAD, 0);
    privateConstructorNode.visitMethodInsn(
        INVOKESPECIAL, "java/lang/Object", "<init>", "()V", /* isInterface= */ false);
    privateConstructorNode.visitInsn(Opcodes.RETURN);
    privateConstructorNode.visitMaxs(1, 1);
    privateConstructorNode.visitEnd();
    privateConstructorNode.accept(generatedClassNode);

    for (FieldNode fn : allDesugarSupportedFields) {
      fn.accept(generatedClassNode);
    }

    for (MethodNode mn : allDesugarSupportedClassMembers) {
      mn.accept(generatedClassNode);
    }
    return ImmutableList.of(generatedClassNode);
  }

  /**
   * Finds all (JDK-)base methods that are to be included in {@code Desugar}-prefixed classes,
   * including both public API methods and private methods that any API depends on. on.
   */
  private ImmutableList<MethodNode> findAllDesugarSupportedMethods() {
    return baseClassNode.methods.stream()
        .filter(
            methodNode ->
                preScanner.getReplacementMethod(ClassMemberKey.create(baseClassNode, methodNode))
                    != null)
        .map(methodNode -> AsmHelpers.transformToStaticCompanion(baseClassNode, methodNode))
        .collect(toImmutableList());
  }

  /**
   * Finds all (JDK-)base fields that are to be included in {@code Desugar}-prefixed classes,
   * including both public API fields and private fields that any API depends on. on.
   */
  private ImmutableList<FieldNode> findAllDesugarSupportedFields() {
    ImmutableList.Builder<FieldNode> supportedFields = ImmutableList.builder();
    for (FieldNode fieldNode : baseClassNode.fields) {
      ClassMemberKey replacementField =
          preScanner.getReplacementField(ClassMemberKey.create(baseClassNode, fieldNode));
      if (replacementField != null) {
        FieldNode replacementFieldNode =
            new FieldNode(
                ASM_API_LEVEL,
                fieldNode.access,
                replacementField.name(),
                replacementField.desc(),
                fieldNode.signature,
                fieldNode.value);
        supportedFields.add(replacementFieldNode);
      }
    }
    return supportedFields.build();
  }
}
