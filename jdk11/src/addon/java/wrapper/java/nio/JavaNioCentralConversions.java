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
 */

package wrapper.java.nio;

import java.util.LinkedHashMap;
import java.util.Map;
import wrapper.java.nio.file.PathConversions;
import wrapper.java.nio.file.attribute.FileTimeConversions;

/** General Conversions related to {@code java.nio.file} package. */
public final class JavaNioCentralConversions {

  public static Object encode(Object raw) {
    if (raw instanceof java.nio.file.Path) {
      return PathConversions.encode((java.nio.file.Path) raw);
    }
    if (raw instanceof java.nio.file.attribute.FileTime) {
      return FileTimeConversions.encode((java.nio.file.attribute.FileTime) raw);
    }
    // TODO(b/207004118): Add remaining instance converions necessary for java.nio package.
    return raw;
  }

  public static Object decode(Object encoded) {
    if (encoded instanceof j$.nio.file.Path) {
      return PathConversions.decode((j$.nio.file.Path) encoded);
    }
    if (encoded instanceof j$.nio.file.attribute.FileTime) {
      return FileTimeConversions.decode((j$.nio.file.attribute.FileTime) encoded);
    }
    // TODO(b/207004118): Add remaining instance converions necessary for java.nio package.
    return encoded;
  }

  public static <K> Map<K, Object> encodeMapValue(Map<K, Object> rawMap) {
    Map<K, Object> encodedMap = new LinkedHashMap<>();
    for (Map.Entry<K, Object> entry : rawMap.entrySet()) {
      encodedMap.put(entry.getKey(), encode(entry.getValue()));
    }
    return encodedMap;
  }

  public static <T> Map<T, Object> decodeMapValue(Map<T, Object> rawMap) {
    Map<T, Object> decoded = new LinkedHashMap<>();
    for (Map.Entry<T, Object> entry : rawMap.entrySet()) {
      decoded.put(entry.getKey(), decode(entry.getValue()));
    }
    return decoded;
  }

  private JavaNioCentralConversions() {}
}
