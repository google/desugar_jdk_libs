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
 *
 */

package wrapper.java.nio.file.attribute;

import java.io.IOException;
import wrapper.java.nio.file.IOExceptionConversions;

/**
 * Type conversions between {@link java.nio.file.attribute.UserPrincipalLookupService} and {@link
 * j$.nio.file.attribute.UserPrincipalLookupService}.
 */
public class UserPrincipalLookupServiceConversions {

  public static j$.nio.file.attribute.UserPrincipalLookupService encode(
      java.nio.file.attribute.UserPrincipalLookupService raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedUserPrincipalLookupService) {
      return ((DecodedUserPrincipalLookupService) raw).delegate;
    }
    return new EncodedUserPrincipalLookupService(raw);
  }

  public static java.nio.file.attribute.UserPrincipalLookupService decode(
      j$.nio.file.attribute.UserPrincipalLookupService encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedUserPrincipalLookupService) {
      return ((EncodedUserPrincipalLookupService) encoded).delegate;
    }
    return new DecodedUserPrincipalLookupService(encoded);
  }

  private UserPrincipalLookupServiceConversions() {}

  static class EncodedUserPrincipalLookupService
      extends j$.nio.file.attribute.UserPrincipalLookupService {

    private final java.nio.file.attribute.UserPrincipalLookupService delegate;

    public EncodedUserPrincipalLookupService(
        java.nio.file.attribute.UserPrincipalLookupService delegate) {
      this.delegate = delegate;
    }

    @Override
    public j$.nio.file.attribute.UserPrincipal lookupPrincipalByName(String name)
        throws IOException {
      try {
        return UserPrincipalConversions.encode(delegate.lookupPrincipalByName(name));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public j$.nio.file.attribute.GroupPrincipal lookupPrincipalByGroupName(String group)
        throws IOException {
      try {
        return GroupPrincipalConversions.encode(delegate.lookupPrincipalByGroupName(group));
      } catch (IOException e) {
        throw IOExceptionConversions.encodeChecked(e);
      }
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof EncodedUserPrincipalLookupService)) {
        return false;
      }
      return delegate.equals(((EncodedUserPrincipalLookupService) obj).delegate);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }

  static class DecodedUserPrincipalLookupService
      extends java.nio.file.attribute.UserPrincipalLookupService {

    private final j$.nio.file.attribute.UserPrincipalLookupService delegate;

    public DecodedUserPrincipalLookupService(
        j$.nio.file.attribute.UserPrincipalLookupService delegate) {
      this.delegate = delegate;
    }

    @Override
    public java.nio.file.attribute.UserPrincipal lookupPrincipalByName(String name)
        throws IOException {
      return UserPrincipalConversions.decode(delegate.lookupPrincipalByName(name));
    }

    @Override
    public java.nio.file.attribute.GroupPrincipal lookupPrincipalByGroupName(String group)
        throws IOException {
      return GroupPrincipalConversions.decode(delegate.lookupPrincipalByGroupName(group));
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof DecodedUserPrincipalLookupService)) {
        return false;
      }
      return delegate.equals(((DecodedUserPrincipalLookupService) obj).delegate);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }
}
