/*
 * Copyright (C) 2008 The Android Open Source Project
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

package libcore.java.sql;

import junit.framework.TestCase;

import SQLite.JDBCDriver;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;

public final class DriverTest extends TestCase {

    public static final String SQLITE_JDBC_URL = "jdbc:sqlite:/only_used_at_connect_time";

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Trigger the static initializer that will cause the driver to register itself with
        // DriverManager.
        Class.forName("SQLite.JDBCDriver");
    }

    public void testDriverImplementation() throws Exception {
        Driver driver = getDriver();
        assertTrue(driver instanceof JDBCDriver);
    }

    public void testAcceptsURL() throws Exception {
        Driver driver = getDriver();
        assertTrue(driver.acceptsURL(SQLITE_JDBC_URL));
    }

    public void testGetMajorVersion() throws Exception {
        assertTrue(getDriver().getMajorVersion() > 0);
    }

    public void testGetMinorVersion() throws Exception {
        assertTrue(getDriver().getMinorVersion() > 0);
    }

    public void testGetPropertyInfo() throws Exception {
        Driver driver = getDriver();
        DriverPropertyInfo[] info = driver.getPropertyInfo(SQLITE_JDBC_URL, null);
        assertNotNull(info);
        assertTrue(info.length > 0);
    }

    public void testJdbcCompliant() throws Exception {
        // The SQLite JDBC driver used by these tests is not actually JDBC compliant.
        assertFalse(getDriver().jdbcCompliant());
    }

    private Driver getDriver() throws SQLException {
        Driver driver = DriverManager.getDriver(SQLITE_JDBC_URL);
        assertNotNull(driver);
        return driver;
    }
}
