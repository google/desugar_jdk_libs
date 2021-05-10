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

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/** A preliminary pass of the classes in a jar for information indexing. */
public final class PreScanner {

  private static final String DESUGAR_API_CLASS_PREFIX = "Desugar";
  private final String intoDesugarExtendedClass;

  final Map<ClassMemberKey, ClassMemberKey> replacements = new HashMap<>();

  public PreScanner(String intoDesugarExtendedClassAnnotationName) {
    this.intoDesugarExtendedClass = intoDesugarExtendedClassAnnotationName;
  }

  public void scan(ClassNode classNode) {
    for (MethodNode methodNode : classNode.methods) {
      if (hasAnnotation(methodNode, intoDesugarExtendedClass)) {
        ClassMemberKey mk = getMethodKey(classNode, methodNode);
        replacements.put(mk, getReplacementMethodKey(methodNode.access, mk));
      }
    }
  }

  @Nullable
  public ClassMemberKey getReplacementKey(ClassMemberKey classMemberKey) {
    return replacements.get(classMemberKey);
  }

  private static ClassMemberKey getMethodKey(ClassNode ownerClass, MethodNode method) {
    return ClassMemberKey.create(ownerClass.name, method.name, method.desc);
  }

  private static ClassMemberKey getReplacementMethodKey(
      int methodDeclAccessCode, ClassMemberKey originalMethod) {
    int simpleNameStart = originalMethod.owner().lastIndexOf('/');
    String replacementOwnerName =
        originalMethod.owner().substring(0, simpleNameStart)
            + "/"
            + DESUGAR_API_CLASS_PREFIX
            + originalMethod.owner().substring(simpleNameStart + 1);
    String replacementMethodDesc =
        (methodDeclAccessCode & Opcodes.ACC_STATIC) != 0
            ? originalMethod.desc()
            : instanceMethodToStaticDescriptor(originalMethod.owner(), originalMethod.desc());
    return ClassMemberKey.create(
        replacementOwnerName, originalMethod.name(), replacementMethodDesc);
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
