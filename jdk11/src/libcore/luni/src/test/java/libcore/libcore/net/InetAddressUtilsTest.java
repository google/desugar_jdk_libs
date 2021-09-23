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
package libcore.libcore.net;

import java.net.InetAddress;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import libcore.net.InetAddressUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnitParamsRunner.class)
public class InetAddressUtilsTest {

    public static String[][] validNumericAddressesAndStringRepresentation() {
        return new String[][] {
                // Regular IPv4.
                { "1.2.3.4", "1.2.3.4" },

                // Regular IPv6.
                { "2001:4860:800d::68", "2001:4860:800d::68" },
                { "1234:5678::9ABC:DEF0", "1234:5678::9abc:def0" },
                { "2001:cdba:9abc:5678::", "2001:cdba:9abc:5678::" },
                { "::2001:cdba:9abc:5678", "::2001:cdba:9abc:5678" },
                { "64:ff9b::1.2.3.4", "64:ff9b::102:304" },

                { "::9abc:5678", "::154.188.86.120" },

                // Mapped IPv4
                { "::ffff:127.0.0.1", "127.0.0.1" },

                // Android does not recognize Octal (leading 0) cases: they are treated as decimal.
                { "0177.00.00.01", "177.0.0.1" },

                // Verify that examples from JavaDoc work correctly.
                { "192.0.2.1", "192.0.2.1" },
                { "2001:db8::1:2", "2001:db8::1:2" },
        };
    }

    public static String[] invalidNumericAddresses() {
        return new String[] {
                "",
                " ",
                "\t",
                "\n",
                "1.2.3.4.",
                "1.2.3",
                "1.2",
                "1",
                "1234",
                "0",
                "0x1.0x2.0x3.0x4",
                "0x7f.0x00.0x00.0x01",
                "0256.00.00.01",
                "fred",
                "www.google.com",
                // IPv6 encoded for use in URL as defined in RFC 2732
                "[fe80::6:2222]",
        };
    }

    @Parameters(method = "validNumericAddressesAndStringRepresentation")
    @Test
    public void parseNumericAddress(String address, String expectedString) {
        InetAddress inetAddress = InetAddressUtils.parseNumericAddress(address);
        assertEquals(expectedString, inetAddress.getHostAddress());
    }

    @Parameters(method = "invalidNumericAddresses")
    @Test
    public void test_parseNonNumericAddress(String address) {
        try {
            InetAddress inetAddress = InetAddressUtils.parseNumericAddress(address);
            fail(String.format(
                    "Address %s is not numeric but was parsed as %s", address, inetAddress));
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains(address);
        }
    }

    @Test
    public void test_parseNumericAddress_null() {
        try {
            InetAddress inetAddress = InetAddressUtils.parseNumericAddress(null);
            fail(String.format("null is not numeric but was parsed as %s", inetAddress));
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Parameters(method = "validNumericAddressesAndStringRepresentation")
    @Test
    public void test_isNumericAddress(String address, String unused) {
        assertTrue("expected '" + address + "' to be treated as numeric",
                InetAddressUtils.isNumericAddress(address));
    }

    @Parameters(method = "invalidNumericAddresses")
    @Test
    public void test_isNotNumericAddress(String address) {
        assertFalse("expected '" + address + "' to be treated as non-numeric",
                InetAddressUtils.isNumericAddress(address));
    }

    @Test
    public void test_isNumericAddress_null() {
        try {
            InetAddressUtils.isNumericAddress(null);
            fail("expected null to throw a NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
