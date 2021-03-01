/*
 * Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved.
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

// AUTOMATICALLY GENERATED FILE - DO NOT EDIT

package sun.nio.fs;
class UnixConstants {
    private UnixConstants() { }
    static final int O_RDONLY = 00;
    static final int O_WRONLY = 01;
    static final int O_RDWR = 02;
    static final int O_APPEND = 02000;
    static final int O_CREAT = 0100;
    static final int O_EXCL = 0200;
    static final int O_TRUNC = 01000;
    static final int O_SYNC = 04010000;





    static final int O_DSYNC = 010000;



    static final int O_NOFOLLOW = 0400000;






    static final int O_DIRECT = 040000;





    static final int S_IAMB =
        (0400|0200|0100|(0400 >> 3)|(0200 >> 3)|(0100 >> 3)|((0400 >> 3) >> 3)|((0200 >> 3) >> 3)|((0100 >> 3) >> 3));
    static final int S_IRUSR = 0400;
    static final int S_IWUSR = 0200;
    static final int S_IXUSR = 0100;
    static final int S_IRGRP = (0400 >> 3);
    static final int S_IWGRP = (0200 >> 3);
    static final int S_IXGRP = (0100 >> 3);
    static final int S_IROTH = ((0400 >> 3) >> 3);
    static final int S_IWOTH = ((0200 >> 3) >> 3);
    static final int S_IXOTH = ((0100 >> 3) >> 3);

    static final int S_IFMT = 0170000;
    static final int S_IFREG = 0100000;
    static final int S_IFDIR = 0040000;
    static final int S_IFLNK = 0120000;
    static final int S_IFCHR = 0020000;
    static final int S_IFBLK = 0060000;
    static final int S_IFIFO = 0010000;
    static final int R_OK = 4;
    static final int W_OK = 2;
    static final int X_OK = 1;
    static final int F_OK = 0;
    static final int ENOENT = 2;
    static final int ENXIO = 6;
    static final int EACCES = 13;
    static final int EEXIST = 17;
    static final int ENOTDIR = 20;
    static final int EINVAL = 22;
    static final int EXDEV = 18;
    static final int EISDIR = 21;
    static final int ENOTEMPTY = 39;
    static final int ENOSPC = 28;
    static final int EAGAIN = 11;
    static final int EWOULDBLOCK = 11;
    static final int ENOSYS = 38;
    static final int ELOOP = 40;
    static final int EROFS = 30;





    static final int ENODATA = 61;


    static final int ERANGE = 34;
    static final int EMFILE = 24;



    static final int AT_SYMLINK_NOFOLLOW = 0x100;
    static final int AT_REMOVEDIR = 0x200;






}
