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

package com.google.testing.apianalyzer;

import com.google.common.collect.ImmutableSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;

/** Obtains referenced types from a given class file in bytes. */
public class TypeReferenceAnalyzer {
  private final Predicate<String> typeInternalNameFilter;
  private final byte[] classFileBytes;
  private final boolean skipDebugAndFrames;

  public TypeReferenceAnalyzer(
      Predicate<String> typeInternalNameFilter, byte[] classFileBytes, boolean skipDebugAndFrames) {
    this.typeInternalNameFilter = typeInternalNameFilter;
    this.classFileBytes = classFileBytes;
    this.skipDebugAndFrames = skipDebugAndFrames;
  }

  public ImmutableSet<String> getAllReferencedTypes() {
    Set<String> referencedTypes = new LinkedHashSet<>();
    TypeReferenceScanner typeReferenceScanner =
        new TypeReferenceScanner(typeInternalNameFilter, referencedTypes);
    ClassReader cr = new ClassReader(classFileBytes);
    cr.accept(typeReferenceScanner, /* parsingOptions= */ skipDebugAndFrames ? ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES: 0);
    return ImmutableSet.copyOf(referencedTypes);
  }

  static class TypeReferenceScanner extends ClassRemapper {

    TypeReferenceScanner(Predicate<String> typeInternalNameFilter, Set<String> referencedTypes) {
      super(
          new ClassNode(),
          new Remapper() {
            @Override
            public String map(String internalName) {
              if (typeInternalNameFilter.test(internalName)) {
                referencedTypes.add(internalName);
              }
              return super.map(internalName);
            }
          });
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
      // Skip MethodHandles-related type reference checking in InnerClasses attribute.
      // Classes with lambdas and method references include an InnerClasses class attribute that
      // indicates MethodHandles/MethodHandles$Lookup outer/inner class relationship, which is
      // beyond the class being visited.
      // TODO(b/192205034): Consider to strip out this information from innerClasses attributes
      // instead of skip checking.
      if ("java/lang/invoke/MethodHandles".equals(outerName)
          && "java/lang/invoke/MethodHandles$Lookup".equals(name)) {
        return;
      }
      super.visitInnerClass(name, outerName, innerName, access);
    }
  }
}
