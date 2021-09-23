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

package libcore.java.net;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * An {@link InputStream} that reads from a delegate and also writes an audit log of all data
 * that was read to the given {@code audit} {@link OutputStream}.
 */
class AuditInputStream extends FilterInputStream {
    private final OutputStream audit;

    protected AuditInputStream(InputStream in, OutputStream audit) {
        super(in);
        this.audit = Objects.requireNonNull(audit);
    }

    @Override
    public int read() throws IOException {
        int result = super.read();
        audit.write(result);
        return result;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int result = super.read(b);
        if (result > 0) {
            audit.write(b, 0, result);
        }
        return result;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);
        if (result > 0) {
            audit.write(b, off, result);
        }
        return result;
    }
}
