package libcore.java.sql;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionTest extends TestCase {

    private File dbFile = null;
    private String connectionURL = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Trigger the static initializer that will cause the driver to register itself with
        // DriverManager.
        Class.forName("SQLite.JDBCDriver");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (dbFile != null) {
            dbFile.delete();
        }
    }

    public void testDriverManager_getConnection() throws Exception {
        Connection c = DriverManager.getConnection(getConnectionURL());
        assertFalse(c.isClosed());
        c.close();
        assertTrue(c.isClosed());
    }

    public void testConnect() throws Exception {
        Driver driver = DriverManager.getDriver(getConnectionURL());
        assertNotNull(driver);
        Connection c = driver.connect(getConnectionURL(), null);
        assertFalse(c.isClosed());
        c.close();
        assertTrue(c.isClosed());
    }

    private String getConnectionURL() {
        if (connectionURL == null) {
            String tmp = System.getProperty("java.io.tmpdir");
            File tmpDir = new File(tmp);
            if (tmpDir.isDirectory()) {
                try {
                    dbFile = File.createTempFile("OldJDBCDriverTest", ".db", tmpDir);
                } catch (IOException e) {
                    System.err.println("error creating temporary DB file.");
                }
                dbFile.deleteOnExit();
            } else {
                System.err.println("java.io.tmpdir does not exist");
            }

            connectionURL = "jdbc:sqlite:/" + dbFile.getPath();
        }

        return connectionURL;
    }
}
