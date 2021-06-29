package com.google.testing.apianalyzer;

import com.google.protobuf.TextFormat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/** Generates class digests from bytecode-based class files. */
public final class ClassDigestGenerator {

  private final Predicate<ClassDigest> typeFilter;

  public ClassDigestGenerator(Predicate<ClassDigest> typeFilter) {
    this.typeFilter = typeFilter;
  }

  public ClassDigestCollection read(Path jarPath) throws IOException {
    ClassDigestCollection.Builder classes = ClassDigestCollection.newBuilder();
    try (JarInputStream in = new JarInputStream(Files.newInputStream(jarPath))) {
      for (JarEntry inEntry; (inEntry = in.getNextJarEntry()) != null; ) {
        if (inEntry.getName().endsWith(".class")) {
          ClassDigest classDigest = read(in.readAllBytes());
          if (typeFilter.test(classDigest)) {
            classes.addClasses(classDigest);
          }
        }
      }
    }
    return classes.build();
  }

  public ClassDigestCollection read(Collection<Class<?>> classes) throws IOException {
    ClassDigestCollection.Builder digestCollection = ClassDigestCollection.newBuilder();
    for (Class<?> clazz : classes) {
      digestCollection.addClasses(read(getClassLiteralBytes(clazz)));
    }
    return digestCollection.build();
  }

  public ClassDigest read(byte[] classBytes) {
    ClassReader cr = new ClassReader(classBytes);
    ClassDigestClassVisitor cv = new ClassDigestClassVisitor(Opcodes.ASM7);
    cr.accept(cv, ClassReader.SKIP_CODE);
    return cv.getClassDigest();
  }

  private static byte[] getClassLiteralBytes(Class<?> clazz) throws IOException {
    return clazz.getResourceAsStream("/" + Type.getInternalName(clazz) + ".class").readAllBytes();
  }

  public static String prettyTextFormat(ClassDigestCollection classes) {
    return TextFormat.printer().printToString(classes);
  }

  private static class ClassDigestClassVisitor extends ClassVisitor {

    private final ClassDigest.Builder classDigestBuilder;
    private ClassDigest classDigest;
    private String className;

    public ClassDigestClassVisitor(int api) {
      super(api);
      classDigestBuilder = ClassDigest.newBuilder();
    }

    public ClassDigest getClassDigest() {
      return classDigest;
    }

    @Override
    public void visit(
        int version,
        int access,
        String name,
        String signature,
        String superName,
        String[] interfaces) {
      className = name;
      classDigestBuilder.setAccess(access);
      classDigestBuilder.setName(name);
      if (superName != null) {
        classDigestBuilder.setSuperClass(superName);
      }
      if (interfaces != null) {
        for (String interfaceName : interfaces) {
          classDigestBuilder.addSuperInterfaces(interfaceName);
        }
      }
      super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(
        int access, String name, String descriptor, String signature, Object value) {
      if ((access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) != 0) {
        classDigestBuilder.addFields(
            Field.newBuilder()
                .setAccess(access)
                .setOwner(className)
                .setName(name)
                .setDesc(descriptor));
      }
      return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(
        int access, String name, String descriptor, String signature, String[] exceptions) {
      if ((access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) != 0) {
        classDigestBuilder.addMethods(
            Method.newBuilder()
                .setAccess(access)
                .setOwner(className)
                .setName(name)
                .setDesc(descriptor)
                .addAllExceptions(exceptions == null ? List.of() : List.of(exceptions)));
      }
      return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
      classDigest = classDigestBuilder.build();
      super.visitEnd();
    }
  }
}
