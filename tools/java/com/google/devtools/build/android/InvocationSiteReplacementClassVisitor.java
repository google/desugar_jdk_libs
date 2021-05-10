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

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Replaces an invocation site based on the given pre-scanning information. */
public class InvocationSiteReplacementClassVisitor extends ClassVisitor {

  private final PreScanner preScanner;

  public InvocationSiteReplacementClassVisitor(
      int api, ClassVisitor classVisitor, PreScanner preScanner) {
    super(api, classVisitor);
    this.preScanner = preScanner;
  }

  @Override
  public MethodVisitor visitMethod(
      int access, String name, String descriptor, String signature, String[] exceptions) {
    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
    return mv == null ? null : new InvocationSiteReplacementMethodVisitor(api, mv);
  }

  class InvocationSiteReplacementMethodVisitor extends MethodVisitor {

    public InvocationSiteReplacementMethodVisitor(int api, MethodVisitor methodVisitor) {
      super(api, methodVisitor);
    }

    @Override
    public void visitMethodInsn(
        int opcode, String owner, String name, String descriptor, boolean isInterface) {
      ClassMemberKey methodKey = ClassMemberKey.create(owner, name, descriptor);
      ClassMemberKey replacementKey = preScanner.getReplacementKey(methodKey);
      if (replacementKey != null) {
        super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            replacementKey.owner(),
            replacementKey.name(),
            replacementKey.desc(),
            /* isInterface= */ false);
        return;
      }
      super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
  }
}
