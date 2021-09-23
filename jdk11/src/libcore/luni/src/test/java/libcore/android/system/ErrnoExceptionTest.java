/*
 * Copyright (C) 2019 The Android Open Source Project
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

package libcore.android.system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.system.ErrnoException;

import java.io.IOException;
import java.net.SocketException;

import static android.system.OsConstants.EBADF;
import static android.system.OsConstants.EINVAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(JUnit4.class)
public class ErrnoExceptionTest {

    @Test public void messageErrnoAndCause() {
        // For simplicitly, we hard-code "EINVAL (Invalid argument)" here even though
        // strictly speaking, those only need to be consistent with errnoName() and
        // strerror().
        check_messageErrnoAndCause("null failed: EINVAL (Invalid argument)", null, EINVAL);
        check_messageErrnoAndCause("name failed: EINVAL (Invalid argument)", "name", EINVAL);
        check_messageErrnoAndCause("name failed: EBADF (Bad file descriptor)", "name", EBADF);
    }

    private static void check_messageErrnoAndCause(String msg, String nameOrNull, int errno) {
        Throwable cause = new Throwable("cause msg");
        assertMessageErrnoAndCause(msg, errno, null, new ErrnoException(nameOrNull, errno));
        assertMessageErrnoAndCause(msg, errno, null, new ErrnoException(nameOrNull, errno, null));
        assertMessageErrnoAndCause(msg, errno, cause, new ErrnoException(nameOrNull, errno, cause));
    }

    private static void assertMessageErrnoAndCause(String message, int errno, Throwable cause,
            ErrnoException e) {
        assertEquals(message, e.getMessage());
        assertEquals(errno, e.errno);
        assertEquals(cause, e.getCause());
    }

    @Test public void rethrow() {
        check_rethrow(new ErrnoException(null, EINVAL));
        check_rethrow(new ErrnoException(null, EINVAL, null));
        check_rethrow(new ErrnoException(null, EINVAL, new Throwable("cause msg")));

        check_rethrow(new ErrnoException("name", EINVAL));
        check_rethrow(new ErrnoException("name", EINVAL, null));
        check_rethrow(new ErrnoException("name", EINVAL, new Throwable("cause msg")));
    }

    private void check_rethrow(ErrnoException cause) {
        try {
            cause.rethrowAsIOException();
            fail();
        } catch (IOException e) {
            assertEquals(cause.getMessage(), e.getMessage());
            assertEquals(cause, e.getCause());
            assertEquals(IOException.class, e.getClass());
        }

        try {
            cause.rethrowAsSocketException();
            fail();
        } catch (SocketException e) {
            assertEquals(cause.getMessage(), e.getMessage());
            assertEquals(cause, e.getCause());
            assertEquals(SocketException.class, e.getClass());
        }
    }
}
