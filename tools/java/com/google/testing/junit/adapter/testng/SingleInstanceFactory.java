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
import java.util.ArrayList;
import java.util.List;

/**
 * A creator-source tagged factory that creates a different instance upon each request. It is used
 * for create testing instances and data provider instances.
 */
@AutoValue
public abstract class SingleInstanceFactory
    implements ThrowingCallable<Object, ReflectiveOperationException> {

  abstract DatasetEntry<ThrowingCallable<Object, ReflectiveOperationException>> datasetEntry();

  static SingleInstanceFactory create(
      ParameterizedMemberTag tag, ThrowingCallable<Object, ReflectiveOperationException> factory) {
    return new AutoValue_SingleInstanceFactory(DatasetEntry.create(tag, factory));
  }

  public ParameterizedMemberTag tag() {
    return datasetEntry().tag();
  }

  public ThrowingCallable<Object, ReflectiveOperationException> payload() {
    return datasetEntry().payload();
  }

  @Override
  public String toString() {
    return datasetEntry().toString();
  }

  @Override
  public Object call() throws ReflectiveOperationException {
    return payload().call();
  }

  public static List<SingleInstanceFactory> fromTestInstanceArrayFactory(
      ParameterizedMemberTag baseTag,
      ThrowingCallable<Object[], ReflectiveOperationException> instanceArrayFactory)
      throws ReflectiveOperationException {
    Object[] preRunResults = instanceArrayFactory.call();
    int instanceArrayLength = preRunResults.length;
    List<SingleInstanceFactory> singleTestInstanceFactories = new ArrayList<>(instanceArrayLength);
    for (int i = 0; i < instanceArrayLength; i++) {
      ParameterizedMemberTag creationTag = baseTag.toBuilder().setOutputSerialNumber(i).build();
      singleTestInstanceFactories.add(
          SingleInstanceFactory.create(
              creationTag, () -> instanceArrayFactory.call()[creationTag.outputSerialNumber()]));
    }
    return singleTestInstanceFactories;
  }
}
