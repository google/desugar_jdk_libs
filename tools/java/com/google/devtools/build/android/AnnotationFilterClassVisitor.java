package com.google.devtools.build.android;

import com.google.common.collect.ImmutableSet;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;

/** An ASM class visitor for stripping out annotations in the byte code. */
class AnnotationFilterClassVisitor extends ClassVisitor {

  private final ImmutableSet<String> omittedAnnotations;

  public AnnotationFilterClassVisitor(
      ImmutableSet<String> omittedAnnotations, ClassVisitor classVisitor, int api) {
    super(api, classVisitor);
    this.omittedAnnotations = omittedAnnotations;
  }

  private boolean omitAnnotation(String descriptor) {
    return omittedAnnotations.contains(descriptor);
  }

  @Override
  public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
    return omitAnnotation(descriptor) ? null : super.visitAnnotation(descriptor, visible);
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(
      int typeRef, TypePath typePath, String descriptor, boolean visible) {
    return omitAnnotation(descriptor)
        ? null
        : super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
  }

  @Override
  public MethodVisitor visitMethod(
      int access, String name, String descriptor, String signature, String[] exceptions) {
    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
    return mv == null ? null : new AnnotationFilterMethodVisitor(api, mv);
  }

  @Override
  public FieldVisitor visitField(
      int access, String name, String descriptor, String signature, Object value) {
    FieldVisitor fv = super.visitField(access, name, descriptor, signature, value);
    return fv == null ? null : new AnnotationFilterFieldVisitor(api, fv);
  }

  class AnnotationFilterFieldVisitor extends FieldVisitor {

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      return omitAnnotation(descriptor) ? null : super.visitAnnotation(descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(
        int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return omitAnnotation(descriptor)
          ? null
          : super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    public AnnotationFilterFieldVisitor(int api, FieldVisitor fieldVisitor) {
      super(api, fieldVisitor);
    }
  }

  class AnnotationFilterMethodVisitor extends MethodVisitor {

    public AnnotationFilterMethodVisitor(int api, MethodVisitor methodVisitor) {
      super(api, methodVisitor);
    }

    @Override
    public void visitParameter(String name, int access) {
      // Omit MethodParameters attribute in the pre-selected desugar library in consistent with
      // other platform class path entries, such as android.jar.
      // See: b/198841565, the attribute causes build-time check failures.
      // TODO(deltazulu): Move to a separate visitor to perform the stripping functionality.
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      return omitAnnotation(descriptor) ? null : super.visitAnnotation(descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(
        int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return omitAnnotation(descriptor)
          ? null
          : super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(
        int parameter, String descriptor, boolean visible) {
      return omitAnnotation(descriptor)
          ? null
          : super.visitParameterAnnotation(parameter, descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitInsnAnnotation(
        int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return omitAnnotation(descriptor)
          ? null
          : super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitTryCatchAnnotation(
        int typeRef, TypePath typePath, String descriptor, boolean visible) {
      return omitAnnotation(descriptor)
          ? null
          : super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(
        int typeRef,
        TypePath typePath,
        Label[] start,
        Label[] end,
        int[] index,
        String descriptor,
        boolean visible) {
      return omitAnnotation(descriptor)
          ? null
          : super.visitLocalVariableAnnotation(
              typeRef, typePath, start, end, index, descriptor, visible);
    }
  }
}
