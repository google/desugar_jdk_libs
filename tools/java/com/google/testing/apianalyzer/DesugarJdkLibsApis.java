/*
 * Copyright (c) 2022 Google LLC
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

package com.google.testing.apianalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Utility class that extracts public APIs from desugar_jdk_libs.jar. */
public final class DesugarJdkLibsApis {

  private final Path inputJarPath;

  public DesugarJdkLibsApis(Path inputJarPath) {
    this.inputJarPath = inputJarPath;
  }

  void scan() throws IOException {
    try (JarInputStream in = new JarInputStream(Files.newInputStream(inputJarPath))) {
      for (JarEntry inEntry; (inEntry = in.getNextJarEntry()) != null; ) {
        final String inEntryName = inEntry.getName();
        if (inEntryName.endsWith(".class")) {
          ClassReader cr = new ClassReader(in);
          ClassVisitor cv =
              new DesugarJdkLibsJarClassVisitor(Opcodes.ASM9, /* classVisitor= */ null);
          cr.accept(cv, 0);
        }
      }
    }
  }

  static class DesugarJdkLibsJarClassVisitor extends ClassVisitor {

    private int classAccess;
    private String className;

    public DesugarJdkLibsJarClassVisitor(int api, ClassVisitor classVisitor) {
      super(api, classVisitor);
    }

    @Override
    public void visit(
        int version,
        int access,
        String name,
        String signature,
        String superName,
        String[] interfaces) {
      classAccess = access;
      className = name;
      super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(
        int access, String name, String descriptor, String signature, String[] exceptions) {
      if ((classAccess & Opcodes.ACC_PUBLIC) != 0 && (access & Opcodes.ACC_PUBLIC) != 0) {
        StringBuilder sb = new StringBuilder();

        sb.append(className).append(',');
        sb.append(name).append(',');
        sb.append(descriptor);

        sb.append(",");

        if (className.contains("/Desugar")) {
          String originalClassName = className.replace("/Desugar", "/");

          String originalDescriptor =
              descriptor
                  .replace("(L" + originalClassName + ";", "(")
                  .replace("(L" + originalClassName, "(");

          sb.append(undoDesugarMirroredType(originalClassName)).append(',');
          sb.append(name).append(',');
          sb.append(undoDesugarMirroredType(originalDescriptor));
        } else if (className.endsWith("$-CC")) {
          String originalClassName = className.replace("$-CC", "");

          String originalMethodName = name.replace("$default$", "");

          final String originalDescriptor;
          if (name.startsWith("$default$")) {
            originalDescriptor =
                descriptor
                    .replace("(L" + originalClassName + ";", "(")
                    .replace("(L" + originalClassName, "(");
          } else {
            originalDescriptor = descriptor;
          }

          sb.append(undoDesugarMirroredType(originalClassName)).append(',');
          sb.append(originalMethodName).append(',');
          sb.append(undoDesugarMirroredType(originalDescriptor));
        } else {
          sb.append(undoDesugarMirroredType(className)).append(',');
          sb.append(name).append(',');
          sb.append(undoDesugarMirroredType(descriptor));
        }

        System.out.println(sb);
      }
      return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
  }

  public static String undoDesugarMirroredType(String inType) {
    return inType
        .replace("j$/desugar/", "desugar/")
        .replace("j$/libcore/", "libcore/")
        .replace("j$/jdk/", "jdk/")
        .replace("j$/", "java/")
        .replace("/Desugar", "/")
        .replace("$-CC", "");
  }

  public static void main(String[] args) throws IOException {
    DesugarJdkLibsApis apiExtractor = new DesugarJdkLibsApis(Paths.get(args[0]));
    apiExtractor.scan();
  }
}
