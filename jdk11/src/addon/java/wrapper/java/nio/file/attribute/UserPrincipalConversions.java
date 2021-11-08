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

import java.nio.file.attribute.UserPrincipal;
import javax.security.auth.Subject;

/**
 * Type conversions between {@link java.nio.file.attribute.UserPrincipal} and {@link
 * j$.nio.file.attribute.UserPrincipal}.
 */
public class UserPrincipalConversions {

  public static j$.nio.file.attribute.UserPrincipal encode(
      java.nio.file.attribute.UserPrincipal raw) {
    if (raw == null) {
      return null;
    }
    if (raw instanceof DecodedUserPrincipal) {
      return ((DecodedUserPrincipal) raw).delegate;
    }
    return new EncodedUserPrincipal(raw);
  }

  public static java.nio.file.attribute.UserPrincipal decode(
      j$.nio.file.attribute.UserPrincipal encoded) {
    if (encoded == null) {
      return null;
    }
    if (encoded instanceof EncodedUserPrincipal) {
      return ((EncodedUserPrincipal) encoded).delegate;
    }
    return new DecodedUserPrincipal(encoded);
  }

  private UserPrincipalConversions() {}

  static class EncodedUserPrincipal implements j$.nio.file.attribute.UserPrincipal {

    private final java.nio.file.attribute.UserPrincipal delegate;

    public EncodedUserPrincipal(java.nio.file.attribute.UserPrincipal delegate) {
      this.delegate = delegate;
    }

    public UserPrincipal getDelegate() {
      return delegate;
    }

    @Override
    public String getName() {
      return delegate.getName();
    }

    @Override
    public boolean implies(Subject subject) {
      return delegate.implies(subject);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object another) {
      if (!(another instanceof EncodedUserPrincipal)) {
        return false;
      }
      return delegate.equals(((EncodedUserPrincipal) another).delegate);
    }
  }

  static class DecodedUserPrincipal implements java.nio.file.attribute.UserPrincipal {

    private final j$.nio.file.attribute.UserPrincipal delegate;

    public DecodedUserPrincipal(j$.nio.file.attribute.UserPrincipal delegate) {
      this.delegate = delegate;
    }

    public j$.nio.file.attribute.UserPrincipal getDelegate() {
      return delegate;
    }

    @Override
    public String getName() {
      return delegate.getName();
    }

    @Override
    public boolean implies(Subject subject) {
      return delegate.implies(subject);
    }

    @Override
    public String toString() {
      return delegate.toString();
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    @Override
    public boolean equals(Object another) {
      if (!(another instanceof DecodedUserPrincipal)) {
        return false;
      }
      return delegate.equals(((DecodedUserPrincipal) another).delegate);
    }
  }
}
