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

import org.junit.runners.model.Statement;

/** Additional {@link Statement}-derived classes. */
public final class JUnitStatements {

  private JUnitStatements() {}

  /** A no-op {@code Statement}. */
  public static final class EmptyStatement extends Statement {

    private static final EmptyStatement INSTANCE = new EmptyStatement();

    public static EmptyStatement getDefaultInstance() {
      return INSTANCE;
    }

    private EmptyStatement() {}

    @Override
    public void evaluate() throws Throwable {}
  }

  /** Wraps two consecutive {@code Statement} into one. */
  public static final class ComposedStatement extends Statement {

    private final Statement baseStatement;
    private final Statement nextStatement;

    ComposedStatement(Statement baseStatement, Statement nextStatement) {
      this.baseStatement = baseStatement;
      this.nextStatement = nextStatement;
    }

    @Override
    public void evaluate() throws Throwable {
      baseStatement.evaluate();
      nextStatement.evaluate();
    }

    public ComposedStatement andThen(Statement statement) {
      return new ComposedStatement(this, statement);
    }
  }
}
