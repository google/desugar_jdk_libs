package com.google.devtools.build.android;

import java.lang.invoke.LambdaMetafactory;
import java.util.Collection;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/** Utilities that assist {@link org.objectweb.asm} library. */
public final class AsmHelpers {

  private AsmHelpers() {}

  static final int ASM_API_LEVEL = Opcodes.ASM7;
  static final int JAVA_LANGUAGE_LEVEL = Opcodes.V11;

  static final String DESUGAR_API_CLASS_PREFIX = "Desugar";

  static final String LAMBDA_METAFACTORY_INTERNAL_NAME =
      // Expected to be "java/lang/invoke/LambdaMetafactory"
      Type.getType(LambdaMetafactory.class).getInternalName();

  /** The descriptor of the static version of a given instance method. */
  static String instanceMethodToStaticDescriptor(String ownerName, String methodDesc) {
    Type[] argumentTypes = Type.getArgumentTypes(methodDesc);
    Type returnType = Type.getReturnType(methodDesc);
    int n = argumentTypes.length + 1;
    Type[] staticCompanionArgTypes = new Type[n];
    staticCompanionArgTypes[0] = Type.getObjectType(ownerName);
    System.arraycopy(argumentTypes, 0, staticCompanionArgTypes, 1, n - 1);
    return Type.getMethodDescriptor(returnType, staticCompanionArgTypes);
  }

  static boolean hasAnyAnnotation(MethodNode mn, Collection<String> annotationInternalNames) {
    return annotationInternalNames.stream()
        .anyMatch(annotationName -> hasAnnotation(mn, annotationName));
  }

  static boolean hasAnyAnnotation(FieldNode fn, Collection<String> annotationInternalNames) {
    return annotationInternalNames.stream()
        .anyMatch(annotationName -> hasAnnotation(fn, annotationName));
  }

  static boolean hasAnnotation(MethodNode mn, String annotationInternalName) {
    if (mn.visibleAnnotations == null) {
      return false;
    }
    String annotationDescriptor = Type.getObjectType(annotationInternalName).getDescriptor();
    for (AnnotationNode annotationNode : mn.visibleAnnotations) {
      if (annotationNode.desc.equals(annotationDescriptor)) {
        return true;
      }
    }
    return false;
  }

  static boolean hasAnnotation(FieldNode mn, String annotationInternalName) {
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
  static MethodNode transformToStaticCompanion(ClassNode baseClassNode, MethodNode baseMethodNode) {
    if ((baseMethodNode.access & Opcodes.ACC_STATIC) != 0) {
      return baseMethodNode;
    } else {
      MethodNode transformedMethodNode =
          new MethodNode(
              ASM_API_LEVEL,
              /* access= */ baseMethodNode.access | Opcodes.ACC_STATIC,
              /* name= */ baseMethodNode.name,
              /* descriptor= */ instanceMethodToStaticDescriptor(
                  baseClassNode.name, baseMethodNode.desc),
              /* signature= */ null,
              /* exceptions= */ baseMethodNode.exceptions.toArray(new String[0]));
      baseMethodNode.accept(transformedMethodNode);

      return transformedMethodNode;
    }
  }
}
