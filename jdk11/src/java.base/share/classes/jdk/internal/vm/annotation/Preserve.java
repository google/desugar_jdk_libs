/*
 * Copyright (c) 2020, Google, Inc. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.internal.vm.annotation;

import java.lang.annotation.*;

/**
 * A class may be annotated for pre-initialization and preserving all its
 * static field values in a CDS image with static archiving, if both of the
 * following conditions hold:
 * <ul>
 * <li><p>The class' static initializer does not have any dependencies on
 * the runtime context, such as using current date or time.
 * <li><p>The class' static initializer does not manipulate any native states
 * that are not archived, such as registering native methods or loading native
 * libraries.
 * </ul>
 * <p>
 * A pre-initialized class can bypass executing its static initializer and use
 * archived static field values directly at runtime in subsequent executions
 * where the archived data is used.
 * <p>
 * A class may be annotated explicitly with false to not pre-initialize and
 * preserve its static fields in the archive:
 * <pre>
 *   &#064;jdk.internal.vm.annotation.Preserve(false)
 * </pre>
 * <p>
 * A field may be annotated individually for pre-initializing and preserving
 * its value in a CDS image with static archiving.
 * <p>
 * A class is in partially pre-initialized state if only some of its static
 * fields are pre-initialized and preserved in the archive, and the rest of
 * its static fields are not. The archived static field values can be loaded
 * and accessed directly at runtime. The rest of the static fields in the class
 * are initialized by executing related bytecode in the static initializer at
 * runtime.
 * <p>
 * This is an example of how individually pre-initialized static field can be
 * implemented to conditionally skipping related runtime bytecode execution:
 * <hr><blockquote><pre>
 *        @Preserve
 *        static Integer[] archivedCache;
 *
 *        static {
 *           int size = (high - low) + 1;
 *
 *           if (archivedCache == null || size > archivedCache.length) {
 *               Integer[] c = new Integer[size];
 *               int j = low;
 *               for(int i = 0; i < c.length; i++) {
 *                   c[i] = new Integer(j++);
 *               }
 *               archivedCache = c;
 *           }
 *        }
 * </pre></blockquote><hr>
 * <p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Preserve {
    /**
     * Returns {@code true} if the initialized static fields can be preserved
     * in the archive, {@code false} otherwise.
     */
    boolean value() default true;
}
