/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.util;


@SuppressWarnings({"unchecked", "deprecation", "all"})
public final class SecurityConstants {

    private SecurityConstants() {
        throw new RuntimeException("Stub!");
    }

    public static final java.security.AllPermission ALL_PERMISSION;

    static {
        ALL_PERMISSION = null;
    }

    public static final java.lang.RuntimePermission CHECK_MEMBER_ACCESS_PERMISSION;

    static {
        CHECK_MEMBER_ACCESS_PERMISSION = null;
    }

    public static final java.security.SecurityPermission CREATE_ACC_PERMISSION;

    static {
        CREATE_ACC_PERMISSION = null;
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final java.lang.RuntimePermission CREATE_CLASSLOADER_PERMISSION;

    static {
        CREATE_CLASSLOADER_PERMISSION = null;
    }

    public static final java.lang.String FILE_DELETE_ACTION = "delete";

    public static final java.lang.String FILE_EXECUTE_ACTION = "execute";

    public static final java.lang.String FILE_READLINK_ACTION = "readlink";

    public static final java.lang.String FILE_READ_ACTION = "read";

    public static final java.lang.String FILE_WRITE_ACTION = "write";

    @android.compat.annotation.UnsupportedAppUsage
    public static final java.lang.RuntimePermission GET_CLASSLOADER_PERMISSION;

    static {
        GET_CLASSLOADER_PERMISSION = null;
    }

    public static final java.security.SecurityPermission GET_COMBINER_PERMISSION;

    static {
        GET_COMBINER_PERMISSION = null;
    }

    public static final java.net.NetPermission GET_COOKIEHANDLER_PERMISSION;

    static {
        GET_COOKIEHANDLER_PERMISSION = null;
    }

    public static final java.lang.RuntimePermission GET_PD_PERMISSION;

    static {
        GET_PD_PERMISSION = null;
    }

    public static final java.security.SecurityPermission GET_POLICY_PERMISSION;

    static {
        GET_POLICY_PERMISSION = null;
    }

    public static final java.net.NetPermission GET_PROXYSELECTOR_PERMISSION;

    static {
        GET_PROXYSELECTOR_PERMISSION = null;
    }

    public static final java.net.NetPermission GET_RESPONSECACHE_PERMISSION;

    static {
        GET_RESPONSECACHE_PERMISSION = null;
    }

    public static final java.lang.RuntimePermission GET_STACK_TRACE_PERMISSION;

    static {
        GET_STACK_TRACE_PERMISSION = null;
    }

    public static final java.net.SocketPermission LOCAL_LISTEN_PERMISSION;

    static {
        LOCAL_LISTEN_PERMISSION = null;
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final java.lang.RuntimePermission MODIFY_THREADGROUP_PERMISSION;

    static {
        MODIFY_THREADGROUP_PERMISSION = null;
    }

    @android.compat.annotation.UnsupportedAppUsage
    public static final java.lang.RuntimePermission MODIFY_THREAD_PERMISSION;

    static {
        MODIFY_THREAD_PERMISSION = null;
    }

    public static final java.lang.String PROPERTY_READ_ACTION = "read";

    public static final java.lang.String PROPERTY_RW_ACTION = "read,write";

    public static final java.lang.String PROPERTY_WRITE_ACTION = "write";

    public static final java.net.NetPermission SET_COOKIEHANDLER_PERMISSION;

    static {
        SET_COOKIEHANDLER_PERMISSION = null;
    }

    public static final java.net.NetPermission SET_PROXYSELECTOR_PERMISSION;

    static {
        SET_PROXYSELECTOR_PERMISSION = null;
    }

    public static final java.net.NetPermission SET_RESPONSECACHE_PERMISSION;

    static {
        SET_RESPONSECACHE_PERMISSION = null;
    }

    public static final java.lang.String SOCKET_ACCEPT_ACTION = "accept";

    public static final java.lang.String SOCKET_CONNECT_ACCEPT_ACTION = "connect,accept";

    public static final java.lang.String SOCKET_CONNECT_ACTION = "connect";

    public static final java.lang.String SOCKET_LISTEN_ACTION = "listen";

    public static final java.lang.String SOCKET_RESOLVE_ACTION = "resolve";

    public static final java.net.NetPermission SPECIFY_HANDLER_PERMISSION;

    static {
        SPECIFY_HANDLER_PERMISSION = null;
    }

    public static final java.lang.RuntimePermission STOP_THREAD_PERMISSION;

    static {
        STOP_THREAD_PERMISSION = null;
    }
}
