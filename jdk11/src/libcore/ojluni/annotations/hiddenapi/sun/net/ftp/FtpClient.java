/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package sun.net.ftp;

import java.io.*;
import java.net.*;

@SuppressWarnings({"unchecked", "deprecation", "all"})
public abstract class FtpClient implements java.io.Closeable {

    @android.compat.annotation.UnsupportedAppUsage
    protected FtpClient() {
        throw new RuntimeException("Stub!");
    }

    public static final int defaultPort() {
        throw new RuntimeException("Stub!");
    }

    public static sun.net.ftp.FtpClient create() {
        throw new RuntimeException("Stub!");
    }

    public static sun.net.ftp.FtpClient create(java.net.InetSocketAddress dest)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public static sun.net.ftp.FtpClient create(java.lang.String dest)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public abstract sun.net.ftp.FtpClient enablePassiveMode(boolean passive);

    public abstract boolean isPassiveModeEnabled();

    public abstract sun.net.ftp.FtpClient setConnectTimeout(int timeout);

    public abstract int getConnectTimeout();

    public abstract sun.net.ftp.FtpClient setReadTimeout(int timeout);

    public abstract int getReadTimeout();

    public abstract sun.net.ftp.FtpClient setProxy(java.net.Proxy p);

    public abstract java.net.Proxy getProxy();

    public abstract boolean isConnected();

    public abstract sun.net.ftp.FtpClient connect(java.net.SocketAddress dest)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient connect(java.net.SocketAddress dest, int timeout)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.net.SocketAddress getServerAddress();

    public abstract sun.net.ftp.FtpClient login(java.lang.String user, char[] password)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient login(
            java.lang.String user, char[] password, java.lang.String account)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract void close() throws java.io.IOException;

    public abstract boolean isLoggedIn();

    public abstract sun.net.ftp.FtpClient changeDirectory(java.lang.String remoteDirectory)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient changeToParentDirectory()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.lang.String getWorkingDirectory()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient setRestartOffset(long offset);

    public abstract sun.net.ftp.FtpClient getFile(java.lang.String name, java.io.OutputStream local)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.io.InputStream getFileStream(java.lang.String name)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public java.io.OutputStream putFileStream(java.lang.String name)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public abstract java.io.OutputStream putFileStream(java.lang.String name, boolean unique)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public sun.net.ftp.FtpClient putFile(java.lang.String name, java.io.InputStream local)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public abstract sun.net.ftp.FtpClient putFile(
            java.lang.String name, java.io.InputStream local, boolean unique)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient appendFile(
            java.lang.String name, java.io.InputStream local)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient rename(java.lang.String from, java.lang.String to)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient deleteFile(java.lang.String name)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient makeDirectory(java.lang.String name)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient removeDirectory(java.lang.String name)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient noop()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.lang.String getStatus(java.lang.String name)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.util.List<java.lang.String> getFeatures()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient abort()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient completePending()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient reInit()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient setType(sun.net.ftp.FtpClient.TransferType type)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public sun.net.ftp.FtpClient setBinaryType()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public sun.net.ftp.FtpClient setAsciiType()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException {
        throw new RuntimeException("Stub!");
    }

    public abstract java.io.InputStream list(java.lang.String path)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.io.InputStream nameList(java.lang.String path)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract long getSize(java.lang.String path)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.util.Date getLastModified(java.lang.String path)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient setDirParser(sun.net.ftp.FtpDirParser p);

    public abstract java.util.Iterator<sun.net.ftp.FtpDirEntry> listFiles(java.lang.String path)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient useKerberos()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.lang.String getWelcomeMsg();

    public abstract sun.net.ftp.FtpReplyCode getLastReplyCode();

    public abstract java.lang.String getLastResponseString();

    public abstract long getLastTransferSize();

    public abstract java.lang.String getLastFileName();

    public abstract sun.net.ftp.FtpClient startSecureSession()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient endSecureSession()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient allocate(long size)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient structureMount(java.lang.String struct)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.lang.String getSystem()
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract java.lang.String getHelp(java.lang.String cmd)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    public abstract sun.net.ftp.FtpClient siteCmd(java.lang.String cmd)
            throws sun.net.ftp.FtpProtocolException, java.io.IOException;

    private static final int FTP_PORT = 21; // 0x15

    @SuppressWarnings({"unchecked", "deprecation", "all"})
    public static enum TransferType {
        ASCII,
        BINARY,
        EBCDIC;

        private TransferType() {
            throw new RuntimeException("Stub!");
        }
    }
}
