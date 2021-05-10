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
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/** Generates classes with desugar-supported APIs enclosed. */
public class DesugarSupportedApiClassGenerator {

  private static final String DESUGAR_API_CLASS_PREFIX = "Desugar";

  /** The ASM API versions of the current project, such as {@code Opcodes.ASM7}. */
  private final int asmApiVersion;

  /** The class node based on which new class nodes with supported APIs are to be generated. */
  private final ClassNode baseClassNode;

  private final ImmutableSet<String> apiExtensionClassMethodAnnotations;

  public DesugarSupportedApiClassGenerator(
      int asmApiVersion,
      ClassNode baseClassNode,
      ImmutableSet<String> apiExtensionClassMethodAnnotations) {
    this.asmApiVersion = asmApiVersion;
    this.baseClassNode = baseClassNode;
    this.apiExtensionClassMethodAnnotations = apiExtensionClassMethodAnnotations;
  }

  /** Generates a list of class nodes that enclose supported APIs. */
  public ImmutableList<ClassNode> getClassNodesWithSupportedApis() {
    ImmutableList<MethodNode> allSupportedApis = findAllSupportedApis();
    if (allSupportedApis.isEmpty()) {
      return ImmutableList.of();
    }
    ClassNode generatedClassNode = new ClassNode();

    int simpleNameStart = baseClassNode.name.lastIndexOf('/');
    String generatedClassName =
        baseClassNode.name.substring(0, simpleNameStart)
            + "/"
            + DESUGAR_API_CLASS_PREFIX
            + baseClassNode.name.substring(simpleNameStart + 1);
    generatedClassNode.visit(
        Opcodes.V11,
        /* access= */ ACC_SYNTHETIC | ACC_PUBLIC | ACC_SUPER | ACC_FINAL,
        generatedClassName,
        /* signature= */ null,
        /* superName= */ "java/lang/Object",
        /* interfaces= */ new String[] {});

    MethodNode privateConstructorNode =
        new MethodNode(
            asmApiVersion,
            Opcodes.ACC_PRIVATE,
            "<init>",
            "()V",
            /* signature= */ null,
            /* exceptions= */ null);
    privateConstructorNode.visitCode();
    privateConstructorNode.visitVarInsn(Opcodes.ALOAD, 0);
    privateConstructorNode.visitMethodInsn(
        Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", /* isInterface= */ false);
    privateConstructorNode.visitInsn(Opcodes.RETURN);
    privateConstructorNode.visitMaxs(1, 1);
    privateConstructorNode.visitEnd();
    privateConstructorNode.accept(generatedClassNode);

    for (MethodNode mn : allSupportedApis) {
      mn.accept(generatedClassNode);
    }
    return ImmutableList.of(generatedClassNode);
  }

  /** Finds all APIs of the current class to be supported. */
  private ImmutableList<MethodNode> findAllSupportedApis() {
    return baseClassNode.methods.stream()
        .filter(methodNode -> hasAnyAnnotation(methodNode, apiExtensionClassMethodAnnotations))
        .map(this::transformToGeneratedApi)
        .collect(toImmutableList());
  }

  private static boolean hasAnyAnnotation(
      MethodNode mn, Collection<String> annotationInternalNames) {
    return annotationInternalNames.stream()
        .anyMatch(annotationName -> hasAnnotation(mn, annotationName));
  }

  private static boolean hasAnnotation(MethodNode mn, String annotationInternalName) {
    if (mn.visibleAnnotations == null) {
      return false;
    }
    for (AnnotationNode annotationNode : mn.visibleAnnotations) {
      if (annotationNode.desc.equals(Type.getObjectType(annotationInternalName).getDescriptor())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Transform a given API method to be support to its counter part in desugar-supported api
   * classes. For instance, an input node of {@code Double#sum(double, double)} will be transformed
   * to {@code DesugarDouble#sum(double, double)}.
   */
  private MethodNode transformToGeneratedApi(MethodNode methodNode) {
    if ((methodNode.access & Opcodes.ACC_STATIC) != 0) {
      return methodNode;
    } else {
      MethodNode transformedMethodNode =
          new MethodNode(
              asmApiVersion,
              /* access= */ methodNode.access | Opcodes.ACC_STATIC,
              /* name= */ methodNode.name,
              /* descriptor= */ instanceMethodToStaticDescriptor(
                  baseClassNode.name, methodNode.desc),
              /* signature= */ null,
              /* exceptions= */ methodNode.exceptions.toArray(new String[0]));
      methodNode.accept(transformedMethodNode);

      return transformedMethodNode;
    }
  }

  /** The descriptor of the static version of a given instance method. */
  private static String instanceMethodToStaticDescriptor(String ownerName, String methodDesc) {
    Type[] argumentTypes = Type.getArgumentTypes(methodDesc);
    Type returnType = Type.getReturnType(methodDesc);
    int n = argumentTypes.length + 1;
    Type[] staticCompanionArgTypes = new Type[n];
    staticCompanionArgTypes[0] = Type.getObjectType(ownerName);
    System.arraycopy(argumentTypes, 0, staticCompanionArgTypes, 1, n - 1);
    return Type.getMethodDescriptor(returnType, staticCompanionArgTypes);
  }
}
