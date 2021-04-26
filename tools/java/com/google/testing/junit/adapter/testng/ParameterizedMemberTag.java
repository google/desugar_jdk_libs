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

package com.google.testing.junit.adapter.testng;

import com.google.auto.value.AutoValue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;

/**
 * Describes the tag that distinguishes a parameterized method or constructor that is evaluated at
 * its {@link #inputSerialNumber()}-th dataset and selected {@link #outputSerialNumber()}-th output
 * for use.
 */
@AutoValue
abstract class ParameterizedMemberTag {

  abstract Executable member();

  abstract int inputSerialNumber();

  abstract int outputSerialNumber();

  public static Builder builder() {
    return new AutoValue_ParameterizedMemberTag.Builder();
  }

  public abstract Builder toBuilder();

  public String shortMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(member().getDeclaringClass().getSimpleName());
    sb.append("$");
    if (member() instanceof Constructor) {
      sb.append("$init$");
    } else {
      sb.append(member().getName());
    }

    sb.append("$in");
    sb.append(inputSerialNumber());

    sb.append("$out");
    sb.append(outputSerialNumber());

    return sb.toString();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setMember(Executable value);

    public abstract Builder setInputSerialNumber(int value);

    public abstract Builder setOutputSerialNumber(int value);

    public abstract ParameterizedMemberTag build();
  }
}
