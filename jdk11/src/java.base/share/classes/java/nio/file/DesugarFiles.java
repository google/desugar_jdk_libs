/*
 * Copyright (c) 2007, 2020, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;   // javadoc
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.spi.FileSystemProvider;
import java.nio.file.spi.FileTypeDetector;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import sun.nio.cs.UTF_8;
import sun.nio.fs.AbstractFileSystemProvider;

/**
 * This class consists exclusively of static methods that operate on files,
 * directories, or other types of files.
 *
 * <p> In most cases, the methods defined here will delegate to the associated
 * file system provider to perform the file operations.
 *
 * @since 1.7
 */

public final class DesugarFiles {
    private DesugarFiles() { }

    /**
     * Reads all content from a file into a string, decoding from bytes to characters
     * using the {@link StandardCharsets#UTF_8 UTF-8} {@link Charset charset}.
     * The method ensures that the file is closed when all content have been read
     * or an I/O error, or other runtime exception, is thrown.
     *
     * <p> This method is equivalent to:
     * {@code readString(path, StandardCharsets.UTF_8) }
     *
     * @param   path the path to the file
     *
     * @return  a String containing the content read from the file
     *
     * @throws  IOException
     *          if an I/O error occurs reading from the file or a malformed or
     *          unmappable byte sequence is read
     * @throws  OutOfMemoryError
     *          if the file is extremely large, for example larger than {@code 2GB}
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file.
     *
     * @since 11
     */
    public static String readString(Path path) throws IOException {
        return readString(path, UTF_8.INSTANCE);
    }

    /**
     * Reads all characters from a file into a string, decoding from bytes to characters
     * using the specified {@linkplain Charset charset}.
     * The method ensures that the file is closed when all content have been read
     * or an I/O error, or other runtime exception, is thrown.
     *
     * <p> This method reads all content including the line separators in the middle
     * and/or at the end. The resulting string will contain line separators as they
     * appear in the file.
     *
     * @apiNote
     * This method is intended for simple cases where it is appropriate and convenient
     * to read the content of a file into a String. It is not intended for reading
     * very large files.
     *
     *
     *
     * @param   path the path to the file
     * @param   cs the charset to use for decoding
     *
     * @return  a String containing the content read from the file
     *
     * @throws  IOException
     *          if an I/O error occurs reading from the file or a malformed or
     *          unmappable byte sequence is read
     * @throws  OutOfMemoryError
     *          if the file is extremely large, for example larger than {@code 2GB}
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file.
     *
     * @since 11
     */
    public static String readString(Path path, Charset cs) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(cs);

        byte[] ba = Files.readAllBytes(path);
        // For desugar: Java 9 module is not applicable to Android.
        // if (path.getClass().getModule() != Object.class.getModule())
        //     ba = ba.clone();

        // For desugar: Use public String class API instead.
        // return JLA.newStringNoRepl(ba, cs);
        return new String(ba, cs);
    }

    /**
     * Write a {@linkplain java.lang.CharSequence CharSequence} to a file.
     * Characters are encoded into bytes using the
     * {@link StandardCharsets#UTF_8 UTF-8} {@link Charset charset}.
     *
     * <p> This method is equivalent to:
     * {@code writeString(path, test, StandardCharsets.UTF_8, options) }
     *
     * @param   path
     *          the path to the file
     * @param   csq
     *          the CharSequence to be written
     * @param   options
     *          options specifying how the file is opened
     *
     * @return  the path
     *
     * @throws  IllegalArgumentException
     *          if {@code options} contains an invalid combination of options
     * @throws  IOException
     *          if an I/O error occurs writing to or creating the file, or the
     *          text cannot be encoded using the specified charset
     * @throws  UnsupportedOperationException
     *          if an unsupported option is specified
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkWrite(String) checkWrite}
     *          method is invoked to check write access to the file. The {@link
     *          SecurityManager#checkDelete(String) checkDelete} method is
     *          invoked to check delete access if the file is opened with the
     *          {@code DELETE_ON_CLOSE} option.
     *
     * @since 11
     */
    public static Path writeString(Path path, CharSequence csq, OpenOption... options)
        throws IOException
    {
        return writeString(path, csq, UTF_8.INSTANCE, options);
    }

    /**
     * Write a {@linkplain java.lang.CharSequence CharSequence} to a file.
     * Characters are encoded into bytes using the specified
     * {@linkplain java.nio.charset.Charset charset}.
     *
     * <p> All characters are written as they are, including the line separators in
     * the char sequence. No extra characters are added.
     *
     * <p> The {@code options} parameter specifies how the file is created
     * or opened. If no options are present then this method works as if the
     * {@link StandardOpenOption#CREATE CREATE}, {@link
     * StandardOpenOption#TRUNCATE_EXISTING TRUNCATE_EXISTING}, and {@link
     * StandardOpenOption#WRITE WRITE} options are present. In other words, it
     * opens the file for writing, creating the file if it doesn't exist, or
     * initially truncating an existing {@link #isRegularFile regular-file} to
     * a size of {@code 0}.
     *
     *
     * @param   path
     *          the path to the file
     * @param   csq
     *          the CharSequence to be written
     * @param   cs
     *          the charset to use for encoding
     * @param   options
     *          options specifying how the file is opened
     *
     * @return  the path
     *
     * @throws  IllegalArgumentException
     *          if {@code options} contains an invalid combination of options
     * @throws  IOException
     *          if an I/O error occurs writing to or creating the file, or the
     *          text cannot be encoded using the specified charset
     * @throws  UnsupportedOperationException
     *          if an unsupported option is specified
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkWrite(String) checkWrite}
     *          method is invoked to check write access to the file. The {@link
     *          SecurityManager#checkDelete(String) checkDelete} method is
     *          invoked to check delete access if the file is opened with the
     *          {@code DELETE_ON_CLOSE} option.
     *
     * @since 11
     */
    public static Path writeString(Path path, CharSequence csq, Charset cs, OpenOption... options)
        throws IOException
    {
        // ensure the text is not null before opening file
        Objects.requireNonNull(path);
        Objects.requireNonNull(csq);
        Objects.requireNonNull(cs);

        // For desugar: Use public String API instead:
        // byte[] bytes = JLA.getBytesNoRepl(String.valueOf(csq), cs);
        byte[] bytes = String.valueOf(csq).getBytes(cs);

        // For desugar: always true
        // if (path.getClass().getModule() != Object.class.getModule())
        //     bytes = bytes.clone();
        Files.write(path, bytes, options);

        return path;
    }

}
