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
 */

package wrapper;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

public final class TypeWrapperConverter {

  private static final LocalDateTime DEFAULT_LOCAL_TIME =
      LocalDateTime.of(2010, 1, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toLocalDateTime();

  private final ImmutableList<Path> inputJarPaths;
  private final Path outputJarPath;

  private final Predicate<String> internalNameFilter;
  private final Function<String, String> internalNameMapper;

  public TypeWrapperConverter(
      ImmutableList<Path> inputJarPaths,
      Path outputJarPath,
      Predicate<String> internalNameFilter,
      Function<String, String> internalNameMapper) {
    this.inputJarPaths = inputJarPaths;
    this.outputJarPath = outputJarPath;
    this.internalNameFilter = internalNameFilter;
    this.internalNameMapper = internalNameMapper;
  }

  public void convert() throws IOException {
    try (JarOutputStream out = new JarOutputStream(Files.newOutputStream(outputJarPath))) {
      for (Path inputJarPath : inputJarPaths) {
        try (JarInputStream in = new JarInputStream(Files.newInputStream(inputJarPath))) {
          for (JarEntry inEntry; (inEntry = in.getNextJarEntry()) != null; ) {
            final String inEntryName = inEntry.getName();
            if (inEntryName.endsWith(".class")) {
              transferClassFileJarEntry(in, out, inEntry);
            } else if (!inEntryName.endsWith("/")) {
              byte[] outBytes = in.readAllBytes();
              JarEntry outJarEntry = createJarEntry(inEntryName, outBytes);
              out.putNextEntry(outJarEntry);
              out.write(outBytes);
              out.closeEntry();
            }
          }
        }
      }
    }
  }

  private void transferClassFileJarEntry(InputStream in, JarOutputStream out, JarEntry inEntry)
      throws IOException {
    ClassReader cr = new ClassReader(in);
    ClassWriter cw = new ClassWriter(0);
    ClassVisitor cv = new TypeWrapperClassRemapper(cw);
    cv = new InvocationRetargetClassVisitor(Opcodes.ASM7, cv);
    cr.accept(cv, 0);
    String outputEntryName = inEntry.getName();
    byte[] outBytes = cw.toByteArray();
    JarEntry outJarEntry = createJarEntry(outputEntryName, outBytes);
    out.putNextEntry(outJarEntry);
    out.write(outBytes);
    out.closeEntry();
  }

  private static JarEntry createJarEntry(String entryName, byte[] bytes) {
    JarEntry je = new JarEntry(entryName);
    je.setTimeLocal(DEFAULT_LOCAL_TIME);
    je.setMethod(JarEntry.STORED);
    int byteLength = bytes.length;
    je.setSize(byteLength);
    CRC32 checksum = new CRC32();
    checksum.update(bytes);
    je.setCrc(checksum.getValue());
    return je;
  }

  class TypeWrapperClassRemapper extends ClassRemapper {

    TypeWrapperClassRemapper(ClassVisitor classVisitor) {
      super(classVisitor, new TypeWrapperRemapper());
    }
  }

  class TypeWrapperRemapper extends Remapper {

    @Override
    public String map(String internalName) {
      if (internalNameFilter.test(internalName)) {
        return internalNameMapper.apply(internalName);
      }
      return internalName;
    }
  }

  static class InvocationRetargetClassVisitor extends ClassVisitor {

    public InvocationRetargetClassVisitor(int api, ClassVisitor classVisitor) {
      super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(
        int access, String name, String descriptor, String signature, String[] exceptions) {
      MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
      return mv == null ? null : new InvocationRetargetMethodVisitor(api, mv);
    }
  }

  static class InvocationRetargetMethodVisitor extends MethodVisitor {

    public InvocationRetargetMethodVisitor(int api, MethodVisitor methodVisitor) {
      super(api, methodVisitor);
    }
  }

  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      throw new IllegalArgumentException(
          "Expected input jar paths and output jar path to be specified.");
    }
    ImmutableList.Builder<Path> inPaths = ImmutableList.builder();
    int n = args.length;
    for (int i = 0; i < n - 1; i++) {
      inPaths.add(Paths.get(args[i]));
    }
    Path outPath = Paths.get(args[n - 1]);

    TypeWrapperConverter typeConverter =
        new TypeWrapperConverter(
            inPaths.build(),
            outPath,
            internalName ->
                internalName.startsWith("java/nio/")
                    || internalName.startsWith("j$/nio/")
                    || internalName.startsWith("java/io/")
                    || internalName.startsWith("j$/io/"),
            internalName -> "__wrapper__/" + internalName);
    typeConverter.convert();
  }
}
