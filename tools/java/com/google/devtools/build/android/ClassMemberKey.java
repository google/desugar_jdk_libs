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

import com.google.auto.value.AutoValue;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/** Describes a class or interface method or field. */
@AutoValue
public abstract class ClassMemberKey {

  public abstract String owner();

  public abstract String name();

  public abstract String desc();

  public static ClassMemberKey create(String owner, String name, String desc) {
    return new AutoValue_ClassMemberKey(owner, name, desc);
  }

  public static ClassMemberKey create(ClassNode classNode, MethodNode methodNode) {
    return create(classNode.name, methodNode.name, methodNode.desc);
  }

  public static ClassMemberKey create(ClassNode classNode, FieldNode fieldNode) {
    return create(classNode.name, fieldNode.name, fieldNode.desc);
  }
}
