/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore.java.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileDescriptor;
import java.net.Inet4Address;
import java.security.AllPermission;

/**
 * Android doesn't support SecurityManager. This test checks that the implementation does nothing
 * or doesn't throw arbitrary exceptions.
 */
@RunWith(JUnit4.class)
public class SecurityManagerTest {

    private static final String SAMPLE_HOST = "www.example.com";
    private static final int SAMPLE_PORT = 80;

    /**
     * Expose protected method from {@link SecurityManager} for test.
     */
    private static class TestSecurityManager extends SecurityManager {
        @Override
        protected Class[] getClassContext() {
            return super.getClassContext();
        }

        @Override
        protected boolean inClass(String name) {
            return super.inClass(name);
        }

        @Override
        protected boolean inClassLoader() {
            return super.inClassLoader();
        }

        @Override
        protected Class<?> currentLoadedClass() {
            return super.currentLoadedClass();
        }

        @Override
        protected ClassLoader currentClassLoader() {
            return super.currentClassLoader();
        }

        @Override
        protected int classDepth(String name) {
            return super.classDepth(name);
        }

        @Override
        protected int classLoaderDepth() {
            return super.classLoaderDepth();
        }
    }

    private TestSecurityManager sm = new TestSecurityManager();

    @Test
    public void testSystemSecurityManager() {
        Assert.assertNull(System.getSecurityManager());
        // Set null allowed
        System.setSecurityManager(null);
        try {
            System.setSecurityManager(sm);
            Assert.fail("System.setSecurityManager(sm) doesn't throw.");
        } catch (SecurityException e) {
            // expected
        }
        Assert.assertNull(System.getSecurityManager());
    }

    @Test
    public void testCheckAccept() {
        sm.checkAccept(SAMPLE_HOST, SAMPLE_PORT);
    }

    @Test
    public void testCheckAccess() {
        sm.checkAccess(Thread.currentThread());
    }

    @Test
    public void testCheckAccessThreadGroup() {
        sm.checkAccess(sm.getThreadGroup());
    }

    @Test
    public void testCheckAwtEventQueueAccess() {
        sm.checkAwtEventQueueAccess();
    }

    @Test
    public void testCheckConnect() {
        sm.checkConnect(SAMPLE_HOST, SAMPLE_PORT);
    }

    @Test
    public void testCheckConnectWithObject() {
        sm.checkConnect(SAMPLE_HOST, SAMPLE_PORT, null);
    }

    @Test
    public void testCheckCreateClassLoader() {
        sm.checkCreateClassLoader();
    }

    @Test
    public void testCheckDelete() {
        sm.checkDelete(null);
        sm.checkDelete("/FILE_NOT_EXIST");
    }

    @Test
    public void testCheckExec() {
        sm.checkExec("invalid command");
    }

    @Test
    public void testCheckExit() {
        sm.checkExit(0);
        sm.checkExit(1);
        sm.checkExit(127);
    }

    @Test
    public void testCheckLink() {
        sm.checkLink("libinvalid.so");
    }

    @Test
    public void testCheckListen() {
        sm.checkListen(SAMPLE_PORT);
    }

    @Test
    public void testCheckMemberAccess() {
        sm.checkMemberAccess(System.class, 0);
    }

    @Test
    public void testCheckMulticast() {
        sm.checkMulticast(Inet4Address.LOOPBACK);
    }

    @Test
    public void testCheckMulticastWithTtl() {
        sm.checkMulticast(Inet4Address.LOOPBACK, (byte) 0);
    }

    @Test
    public void testCheckPackageAccess() {
        sm.checkPackageAccess("java.lang");
        sm.checkPackageAccess("android.invalid.package");
    }

    @Test
    public void testCheckPackageDefinition() {
        sm.checkPackageDefinition("java.lang");
        sm.checkPackageDefinition("android.invalid.package");
    }

    @Test
    public void testCheckPermission() {
        sm.checkPermission(new AllPermission(), this);
    }

    @Test
    public void testCheckPrintJobAccess() {
        sm.checkPrintJobAccess();
    }

    @Test
    public void testCheckPropertiesAccess() {
        sm.checkPropertiesAccess();
    }

    @Test
    public void testCheckPropertyAccess() {
        sm.checkPropertyAccess(null);
        sm.checkPropertyAccess("system.invalid.property");
    }

    @Test
    public void testCheckRead() {
        sm.checkRead((FileDescriptor) null);
        sm.checkRead(FileDescriptor.in);
        sm.checkRead(FileDescriptor.out);
        sm.checkRead(FileDescriptor.err);
    }

    @Test
    public void testCheckReadPath() {
        sm.checkRead((String) null);
        sm.checkRead("/");
        sm.checkRead("/invalid_path");
    }

    @Test
    public void testCheckReadWithObject() {
        sm.checkRead("/", this);
        sm.checkRead("/invalid_path", this);
    }

    @Test
    public void testCheckSecurityAccess() {
        sm.checkSecurityAccess(null);
        sm.checkSecurityAccess("");
    }

    @Test
    public void testCheckSetFactory() {
        sm.checkSetFactory();
    }

    @Test
    public void testCheckSystemClipboardAccess() {
        sm.checkSystemClipboardAccess();
    }

    @Test
    public void testCheckTopLevelWindow() {
        sm.checkTopLevelWindow(null);
        sm.checkTopLevelWindow(this);
    }

    @Test
    public void testCheckWrite() {
        sm.checkWrite((FileDescriptor) null);
        sm.checkWrite(FileDescriptor.in);
        sm.checkWrite(FileDescriptor.out);
        sm.checkWrite(FileDescriptor.err);
    }

    @Test
    public void testCheckWriteWithPath() {
        sm.checkWrite("/");
        sm.checkWrite("/invalid path");
    }

    @Test
    public void testClassDepth() {
        sm.classDepth(null);
    }

    @Test
    public void testClassLoaderDepth() {
        sm.classLoaderDepth();
    }

    @Test
    public void testCurrentClassLoader() {
        sm.currentClassLoader();
    }

    @Test
    public void testCurrentLoadedClass() {
        sm.currentLoadedClass();
    }

    @Test
    public void testGetInCheck() {
        sm.getInCheck();
    }

    @Test
    public void testGetSecurityContext() {
        sm.getSecurityContext();
    }

    @Test
    public void testInClass() {
        sm.inClass(this.getClass().getName());
    }

    @Test
    public void testInClassLoader() {
        sm.inClassLoader();
    }
}
