/*
 * Copyright (C) 2018 The Android Open Source Project
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

package libcore;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/**
 * A List of lines (eg. from a text file or a command's output).
 */
public class Lines extends AbstractList<String> implements RandomAccess {
    public static Lines EMPTY = new Lines(Collections.emptyList());

    private final List<String> delegate;
    private volatile int hashCode = 0;

    public Lines(Collection<String> collection) {
        this.delegate = new ArrayList<>(collection);
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = super.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o.hashCode() != this.hashCode()) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public Iterator<String> iterator() {
        return Collections.unmodifiableList(delegate).iterator();
    }

    @Override
    public String get(int index) {
        return delegate.get(index);
    }

    @Override
    public int size() {
        return delegate.size();
    }
}
