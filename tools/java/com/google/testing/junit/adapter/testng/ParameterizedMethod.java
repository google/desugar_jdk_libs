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

import java.lang.reflect.Method;
import java.util.Objects;
import org.junit.runners.model.FrameworkMethod;

/** Describes a test method with a particular data set. */
class ParameterizedMethod extends FrameworkMethod {

  private final Method baseMethod;
  private final ParameterInputEntry datasetEntry;
  private final SingleInstanceFactory testFactory;

  public ParameterizedMethod(
      Method baseMethod, ParameterInputEntry datasetEntry, SingleInstanceFactory testFactory) {
    super(baseMethod);
    this.baseMethod = baseMethod;
    this.datasetEntry = datasetEntry;
    this.testFactory = testFactory;
  }

  public Object createTestInstance() throws Exception {
    return testFactory.call();
  }

  public Object[] evaluateParameters() {
    return datasetEntry.payload();
  }

  @Override
  public String getName() {
    return super.getName() + getParameterizationSuffix();
  }

  @Override
  public String toString() {
    return super.toString() + getParameterizationSuffix();
  }

  private String getParameterizationSuffix() {
    return "_param$" + datasetEntry + "_factory$" + testFactory;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof ParameterizedMethod) {
      ParameterizedMethod that = (ParameterizedMethod) obj;
      return this.testFactory.equals(that.testFactory)
          && this.datasetEntry.equals(that.datasetEntry)
          && this.baseMethod.equals(that.baseMethod);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), datasetEntry, testFactory);
  }
}
