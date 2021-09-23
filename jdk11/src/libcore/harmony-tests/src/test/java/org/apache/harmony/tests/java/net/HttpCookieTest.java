/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.harmony.tests.java.net;

import junit.framework.TestCase;

import java.net.HttpCookie;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HttpCookieTest extends TestCase {
    private Locale locale;

    /**
     * java.net.HttpCookie(String, String).
     * @since 1.6
     */
    public void test_HttpCookie_LString_LString() {
        assertNotNull(new HttpCookie("harmony_6", "test,sem"));
        assertNotNull(new HttpCookie("harmony_6", null));
        assertNotNull(new HttpCookie("harmony    ", null));
        assertEquals("harmony", new HttpCookie("harmony ", null).getName());

        constructHttpCookie("", null);

        String value = "value";
        constructHttpCookie("", value);

        constructHttpCookie("harmony,", value);
        constructHttpCookie("harmony;", value);
        constructHttpCookie("$harmony", value);
        constructHttpCookie("n\tame", value);
        constructHttpCookie("n\rame", value);
        constructHttpCookie("n\r\name", value);
        constructHttpCookie("Comment", value);
        constructHttpCookie("CommentURL", value);
        constructHttpCookie("Domain", value);
        constructHttpCookie("Discard", value);
        constructHttpCookie("Max-Age", value);
        constructHttpCookie("  Path     ", value);
        constructHttpCookie("Port  ", value);
        constructHttpCookie("SeCure", value);
        constructHttpCookie("VErsion", value);
        constructHttpCookie("expires", value);
        constructHttpCookie("na\u0085me", value);
        constructHttpCookie("\u2028me", value);
        constructHttpCookie("na\u2029me", value);

        try {
            new HttpCookie(null, value);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            new HttpCookie("\u007f", value);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        HttpCookie cookie = new HttpCookie("harmony!", null);
        assertEquals("harmony!", cookie.getName());

        cookie = new HttpCookie("harmon$y", null);
        assertEquals("harmon$y", cookie.getName());

    }

    private static void constructHttpCookie(String name, String value) {
        try {
            new HttpCookie(name, value);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * java.net.HttpCookie#domainMatches(String, String).
     * @since 1.6
     */
    public void test_DomainMatches() {

        /*
         * Rule 1: A host isn't in a domain (RFC 2965 sec. 3.3.2) if: The value
         * for the Domain attribute contains no embedded dots, and the value is
         * not .local.
         */
        boolean match = HttpCookie.domainMatches("hostname", "hostname");
        assertFalse(match);

        match = HttpCookie.domainMatches(".com", "test.com");
        assertFalse(match);

        match = HttpCookie.domainMatches(".com.", "test.com");
        assertFalse(match);

        // During comparison, host name is transformed to effective host name
        // first.
        match = HttpCookie.domainMatches(".local", "hostname");
        assertTrue(match);

        /*
         * Rule 3: The request-host is a HDN (not IP address) and has the form
         * HD, where D is the value of the Domain attribute, and H is a string
         * that contains one or more dots.
         */
        match = HttpCookie.domainMatches(".c.d", "a.b.c.d");
        assertTrue(match);

        match = HttpCookie.domainMatches("c.d", "a.b.c.d");
        assertFalse(match);

        match = HttpCookie.domainMatches(".foo.com", "y.x.foo.com");
        assertTrue(match);

        match = HttpCookie.domainMatches(".foo.com", "x.foo.com");
        assertTrue(match);

        match = HttpCookie.domainMatches(".local", "hostname.local");
        assertTrue(match);

        match = HttpCookie.domainMatches(".ajax.com", "a.ajax.com");
        assertTrue(match);

        match = HttpCookie.domainMatches(".ajax.com", "a.AJAX.com");
        assertTrue(match);

        match = HttpCookie.domainMatches("...", "test...");
        assertFalse(match);

        match = HttpCookie.domainMatches(".ajax.com", "b.a.AJAX.com");
        assertTrue(match);

        match = HttpCookie.domainMatches(".a", "b.a");
        assertFalse(match);

        // when either parameter is null
        match = HttpCookie.domainMatches(".ajax.com", null);
        assertFalse(match);

        match = HttpCookie.domainMatches(null, null);
        assertFalse(match);

        match = HttpCookie.domainMatches(null, "b.a.AJAX.com");
        assertFalse(match);

        // JDK-7023713
        match = HttpCookie.domainMatches("hostname.local", "hostname");
        assertTrue(match);
    }

    /**
     * java.net.HttpCookie#getVersion(), setVersion(int).
     * @since 1.6
     */
    public void test_Get_SetVersion() {
        HttpCookie cookie = new HttpCookie("name", "value");
        assertEquals(1, cookie.getVersion());
        cookie.setVersion(0);
        assertEquals(0, cookie.getVersion());
        cookie.setVersion(1);
        assertEquals(1, cookie.getVersion());

        try {
            cookie.setVersion(-1);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            cookie.setVersion(2);
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * java.net.HttpCookie#getValue(), setValue(String)
     * @since 1.6
     */
    public void test_Get_SetValue() {
        HttpCookie cookie = new HttpCookie("name", "value");
        assertEquals("value", cookie.getValue());
        cookie.setValue("newValue");
        assertEquals("newValue", cookie.getValue());

        cookie.setValue(null);
        assertNull(cookie.getValue());

        cookie.setValue("na\u64DEme");
        assertEquals("na\u64DEme", cookie.getValue());
        cookie.setVersion(0);
        cookie.setValue("{(new value, 11)}");
        assertEquals("{(new value, 11)}", cookie.getValue());
    }

    /**
     * java.net.HttpCookie#getName()
     * @since 1.6
     */
    public void test_GetName() {
        HttpCookie cookie = new HttpCookie("testName", "value");
        assertEquals("testName", cookie.getName());
    }

    /**
     * java.net.HttpCookie#getSecure(), setSecure(boolean)
     * @since 1.6
     */
    public void test_Get_SetSecure() {
        HttpCookie cookie = new HttpCookie("testName", "value");
        assertFalse(cookie.getSecure());
        cookie.setVersion(0);
        assertFalse(cookie.getSecure());

        cookie.setSecure(true);
        assertTrue(cookie.getSecure());
        cookie.setSecure(false);
        cookie.setVersion(1);
        assertFalse(cookie.getSecure());
    }

    /**
     * java.net.HttpCookie#getPath(), setPath(String)
     * @since 1.6
     */
    public void test_Get_SetPath() {
        HttpCookie cookie = new HttpCookie("name", "test new value");
        assertNull(cookie.getPath());

        cookie.setPath("{}()  test,; 43!@");
        assertEquals("{}()  test,; 43!@", cookie.getPath());

        cookie.setPath(" test");
        assertEquals(" test", cookie.getPath());

        cookie.setPath("\u63DF\u64DE");
        cookie.setDomain("test");
        assertEquals("\u63DF\u64DE", cookie.getPath());
    }

    /**
     * java.net.HttpCookie#getMaxAge(), setMaxAge(long)
     * @since 1.6
     */
    public void test_Get_SetMaxAge() {
        HttpCookie cookie = new HttpCookie("name", "test new value");
        assertEquals(-1, cookie.getMaxAge());

        cookie.setMaxAge(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, cookie.getMaxAge());

        cookie.setMaxAge(Long.MIN_VALUE);
        cookie.setDiscard(false);
        assertEquals(Long.MIN_VALUE, cookie.getMaxAge());
    }

    /**
     * java.net.HttpCookie#getDomain(), setDomain(String)
     * @since 1.6
     */
    public void test_Get_SetDomain() {
        HttpCookie cookie = new HttpCookie("name", "test new value");
        assertNull(cookie.getDomain());

        cookie.setDomain("a.b.d.c.com.");
        assertEquals("a.b.d.c.com.", cookie.getDomain());

        cookie.setDomain("   a.b.d.c.com.  ");
        assertEquals("   a.b.d.c.com.  ", cookie.getDomain());

        cookie.setPath("temp/subTemp");
        cookie.setDomain("xy.foo.bar.de.edu");
        assertEquals("xy.foo.bar.de.edu", cookie.getDomain());
    }

    /**
     * java.net.HttpCookie#getPortlist(), setPortlist(String)
     * @since 1.6
     */
    public void test_Get_SetPortlist() {
        HttpCookie cookie = new HttpCookie("cookieName", "cookieName value");
        assertNull(cookie.getPortlist());

        cookie.setPortlist("80,23,20");
        assertEquals("80,23,20", cookie.getPortlist());
        cookie.setPortlist("abcdefg1234567");
        cookie.setValue("cookie value again");
        assertEquals("abcdefg1234567", cookie.getPortlist());
    }

    /**
     * java.net.HttpCookie#getDiscard(), setDiscard(boolean)
     * @since 1.6
     */
    public void test_Get_SetDiscard() {
        HttpCookie cookie = new HttpCookie("cookie'sName",
                "cookie's Test value");
        assertFalse(cookie.getDiscard());

        cookie.setDiscard(true);
        assertTrue(cookie.getDiscard());
        cookie.setDiscard(false);
        cookie.setMaxAge(-1);
        assertFalse(cookie.getDiscard());
    }

    /**
     * java.net.HttpCookie#getCommentURL(), setCommentURL(String)
     * @since 1.6
     */
    public void test_Get_SetCommentURL() {
        HttpCookie cookie = new HttpCookie("cookie'\"sName",
                "cookie's Test value");
        assertNull(cookie.getCommentURL());

        cookie.setCommentURL("http://www.test.com");
        assertEquals("http://www.test.com", cookie.getCommentURL());

        cookie.setCommentURL("schema://harmony.test.org");
        cookie.setComment("just a comment");
        assertEquals("schema://harmony.test.org", cookie.getCommentURL());
    }

    /**
     * java.net.HttpCookie#getComment(), setComment(String)
     * @since 1.6
     */
    public void test_Get_SetComment() {
        HttpCookie cookie = new HttpCookie("cookie'\"sName?",
                "cookie's Test??!@# value");
        assertNull(cookie.getComment());

        cookie.setComment("");
        assertEquals("", cookie.getComment());

        cookie.setComment("cookie''s @#$!&*()");
        cookie.setVersion(0);
        assertEquals("cookie''s @#$!&*()", cookie.getComment());
    }

    /**
     * java.net.HttpCookie#hasExpired()
     * @since 1.6
     */
    public void test_HasExpired() {
        HttpCookie cookie = new HttpCookie("cookie'\"sName123456",
                "cookie's Test?()!@# value");
        assertFalse(cookie.hasExpired());

        cookie.setMaxAge(0);
        assertTrue(cookie.hasExpired());

        cookie.setMaxAge(Long.MAX_VALUE);
        cookie.setVersion(0);
        assertFalse(cookie.hasExpired());

        cookie.setMaxAge(Long.MIN_VALUE);
        cookie.setDiscard(false);
        assertTrue(cookie.hasExpired());

        cookie.setDiscard(true);
        cookie.setMaxAge(-1);
        assertFalse(cookie.hasExpired());
    }

    /**
     * Regression test for http://b/25682357.
     */
    public void test_HasExpiredBug25682357() throws Exception {
        HttpCookie cookie1 = HttpCookie.parse("Set-Cookie:name=value;max-age=2;").get(0);
        HttpCookie cookie2 = HttpCookie.parse("Set-Cookie:name=value;max-age=100;").get(0);
        assertFalse(cookie1.hasExpired());
        assertFalse(cookie2.hasExpired());

        // Sleep for long enough to force expiry of the first cookie.
        Thread.sleep(3000);
        assertTrue(cookie1.hasExpired());
        assertFalse(cookie2.hasExpired());

        assertEquals(2, cookie1.getMaxAge());
        assertEquals(100, cookie2.getMaxAge());

        // Changing the max age should not reset the expiry status.
        cookie1.setMaxAge(2);
        assertTrue(cookie1.hasExpired());
    }

    /**
     * Regression test for http://b/25682357.
     */
    public void test_HasExpiredBug25682357_2() throws Exception {
        // The following tests do not pass on the RI: it may not handle "expires" at all.
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String pastExpiryDate = dateFormat.format(new Date(System.currentTimeMillis() - 100_000));
        String pastExpiryCookieHeader =
            "Set-Cookie:name=value;expires=" + pastExpiryDate + ";";
        HttpCookie pastExpiryCookie = HttpCookie.parse(pastExpiryCookieHeader).get(0);
        assertTrue(pastExpiryCookie.hasExpired());

        String futureExpiryDate = dateFormat.format(new Date(System.currentTimeMillis() + 100_000));
        String futureExpiryCookieHeader =
            "Set-Cookie:name=value;expires=" + futureExpiryDate + ";";
        HttpCookie futureExpiryCookie = HttpCookie.parse(futureExpiryCookieHeader).get(0);
        assertFalse(futureExpiryCookie.hasExpired());
    }

    /**
     * java.net.HttpCookie#equals()
     * @since 1.6
     */
    public void test_Equals() {
        Object obj = new Object();
        HttpCookie cookie = new HttpCookie("test", "testValue");
        HttpCookie cookie2 = new HttpCookie("TesT", "TEstValue");

        assertFalse(cookie.equals(obj));
        assertFalse(cookie.equals(null));
        assertTrue(cookie2.equals(cookie));
        assertTrue(cookie.equals(cookie2));
        assertTrue(cookie.equals(cookie));

        cookie.setDomain("  test");
        cookie2.setDomain("test");
        assertFalse(cookie.equals(cookie2));
        cookie.setDomain("TEST");
        assertTrue(cookie.equals(cookie2));

        cookie.setPath("temp\\e");
        assertFalse(cookie.equals(cookie2));
        cookie2.setPath("temp\\E");
        assertFalse(cookie.equals(cookie2));

        cookie.setDiscard(true);
        cookie.setMaxAge(-1234);
        cookie2.setPath("temp\\e");
        assertTrue(cookie.equals(cookie2));
    }

    /**
     * java.net.HttpCookie#clone()
     * @since 1.6
     */
    public void test_Clone() {
        HttpCookie cookie = new HttpCookie("test", "testValue");
        cookie.setMaxAge(33l);
        cookie.setComment("test comment");
        HttpCookie cloneCookie = (HttpCookie) cookie.clone();
        assertNotSame(cloneCookie, cookie);
        assertEquals("test", cloneCookie.getName());
        assertEquals(33l, cloneCookie.getMaxAge());
        assertEquals("test comment", cloneCookie.getComment());
    }

    /**
     * java.net.HttpCookie#toString()
     * @since 1.6
     */
    public void test_ToString() {
        HttpCookie cookie = new HttpCookie("test", "testValue");
        cookie.setComment("ABCd");
        cookie.setCommentURL("\u63DF");
        cookie.setDomain(".B.com");
        cookie.setDiscard(true);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("temp/22RuTh");
        cookie.setPortlist("80.562Ab");
        cookie.setSecure(true);
        cookie.setVersion(1);

        assertEquals(
                "test=\"testValue\";$Path=\"temp/22RuTh\";$Domain=\".b.com\";$Port=\"80.562Ab\"",
                cookie.toString());

        cookie.setPath(null);
        assertEquals(
                "test=\"testValue\";$Domain=\".b.com\";$Port=\"80.562Ab\"",
                cookie.toString());
        cookie.setComment(null);
        assertEquals(
                "test=\"testValue\";$Domain=\".b.com\";$Port=\"80.562Ab\"",
                cookie.toString());
        cookie.setPortlist(null);
        assertEquals("test=\"testValue\";$Domain=\".b.com\"", cookie.toString());
        cookie.setDomain(null);
        assertEquals("test=\"testValue\"", cookie.toString());

        cookie.setVersion(0);
        cookie.setPortlist("80,8000");
        assertEquals("test=testValue", cookie.toString());
    }

    /**
     * java.net.HttpCookie#hashCode()
     * @since 1.6
     */
    public void test_HashCode() {
        HttpCookie cookie = new HttpCookie("nAmW_1", "value_1");
        assertEquals(-1052814577, cookie.hashCode());

        cookie.setDomain("a.b.c.de");
        assertEquals(1222695220, cookie.hashCode());

        cookie.setPath("3kmxiq;1");
        assertEquals(-675006347, cookie.hashCode());
        cookie.setPath("3KmxiQ;1");
        assertEquals(989616181, cookie.hashCode());

        cookie.setValue("Vw0,22_789");
        assertEquals(989616181, cookie.hashCode());
        cookie.setComment("comment");
        assertEquals(989616181, cookie.hashCode());

        cookie.setDomain("");
        assertEquals(-1285893616, cookie.hashCode());
    }

    /**
     * java.net.HttpCookie#parse(String) for exception cases
     * @since 1.6
     */
    public void test_Parse_exception() {
        try {
            HttpCookie.parse(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        /*
         * Please note that Netscape draft specification does not fully conform
         * to the HTTP header format. Netscape draft does not specify whether
         * multiple cookies may be sent in one header. Hence, comma character
         * may be present in unquoted cookie value or unquoted parameter value.
         * Refer to <a
         * href="http://jakarta.apache.org/commons/httpclient/apidocs/org/apache/commons/httpclient/cookie/NetscapeDraftSpec.html#parse(java.lang.String,%20int,%20java.lang.String,%20boolean,%20java.lang.String)">
         * http://jakarta.apache.org/commons/httpclient/apidocs/org/apache/commons/httpclient/cookie/NetscapeDraftSpec.html#parse(java.lang.String,%20int,%20java.lang.String,%20boolean,%20java.lang.String)
         * </a>
         */
        // violates the cookie specification's syntax
        checkInvalidCookie("invalid cookie name");
        checkInvalidCookie("Set-Cookie2:");
        checkInvalidCookie("name");
        checkInvalidCookie("$name=");
        checkInvalidCookie("Set-Cookie2:$name=");
        checkInvalidCookie("Set-Cookie:$");
        checkInvalidCookie("Set-Cookie");

        // cookie name contains llegal characters
        checkInvalidCookie("Set-Cookie:n,ame=");
        checkInvalidCookie("Set-Cookie2:n\name=");
        checkInvalidCookie("Set-Cookie2:n,ame=");
        checkInvalidCookie("Set-Cookie2:n\tame=");
        checkInvalidCookie("Set-Cookie2:n\rame=");
        checkInvalidCookie("Set-Cookie2:n\r\name=");
        checkInvalidCookie("Set-Cookie2:na\u0085me=");
        checkInvalidCookie("Set-Cookie2:na\u2028me=");
        checkInvalidCookie("Set-Cookie2:na\u2029me=");
        checkInvalidCookie("Set-Cookie2:=");
        checkInvalidCookie("Set-Cookie2:name=tes,t");

        // 'CommentURL' is one of the tokens reserved, case-insensitive
        checkInvalidCookie("Set-Cookie2:COmmentURL=\"lala\"");

        // check value
        checkInvalidCookie("Set-Cookie2:val,ue");
        checkInvalidCookie("Set-Cookie2:name=test;comMent=sent,ence");
        checkInvalidCookie("Set-Cookie2:name=test;comMentUrL=u,rl");
        checkInvalidCookie("Set-Cookie2:name=test;Discard=fa,lse");
        checkInvalidCookie("Set-Cookie2:name=test;Disc,ard");
        checkInvalidCookie("Set-Cookie2:name=test;Domain=u,rl");
        checkInvalidCookie("Set-Cookie2:name=test;Path=pa,th");
        checkInvalidCookie("Set-Cookie2:name=test;Secure=se,cure");
        checkInvalidCookie("Set-Cookie2:name=test;se,cure");
        checkInvalidCookie("Set-Cookie2:name=test;Max-Age=se,cure");
        checkInvalidCookie("Set-Cookie2:name=test;Max-Age=");
        checkInvalidCookie("Set-Cookie2:name=test;Max-Age=max-age");
        checkInvalidCookie("Set-Cookie2:name=test;Max-Age=1000.0");
    }

    /**
     * java.net.HttpCookie#parse(String) for locales other than
     * Locale.ENGLISH.
     * @since 1.6
     */
    public void test_Parse_locale() {
        Locale.setDefault(Locale.FRENCH);
        List<HttpCookie> list = HttpCookie
                .parse("Set-Cookie:name=test;expires=Thu, 30-Oct-2008 19:14:07 GMT;");
        HttpCookie cookie = list.get(0);
        assertTrue(cookie.hasExpired());

        Locale.setDefault(Locale.GERMAN);
        list = HttpCookie
                .parse("Set-Cookie:name=test;expires=Sun, 30-Oct-2005 19:14:07 GMT;");
        cookie = list.get(0);
        assertTrue(cookie.hasExpired());

        Locale.setDefault(Locale.KOREA);
        list = HttpCookie
                .parse("Set-Cookie:name=test;max-age=1234;expires=Sun, 30-Oct-2005 19:14:07 GMT;");
        cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
        assertEquals(1234, cookie.getMaxAge());
        assertFalse(cookie.hasExpired());

        Locale.setDefault(Locale.TAIWAN);
        list = HttpCookie
                .parse("Set-Cookie:name=test;max-age=-12345;");
        cookie = list.get(0);
        assertEquals(-12345, cookie.getMaxAge());
        assertTrue(cookie.hasExpired());

        // Locale does not affect version 1 cookie.
        Locale.setDefault(Locale.ITALIAN);
        list = HttpCookie.parse("Set-Cookie2:name=test;max-age=1000");
        cookie = list.get(0);
        assertEquals(1000, cookie.getMaxAge());
        assertFalse(cookie.hasExpired());
    }

    /**
     * java.net.HttpCookie#parse(String) for normal cases
     * @since 1.6
     */
    public void test_Parse() {
        List<HttpCookie> list = HttpCookie.parse("test=\"null\"");
        HttpCookie cookie = list.get(0);
        // when two '"' presents, the parser ignores it.
        assertEquals("null", cookie.getValue());
        assertNull(cookie.getComment());
        assertNull(cookie.getCommentURL());
        assertFalse(cookie.getDiscard());
        assertNull(cookie.getDomain());
        assertEquals(-1, cookie.getMaxAge());
        assertNull(cookie.getPath());
        assertNull(cookie.getPortlist());
        assertFalse(cookie.getSecure());
        // default version is 0
        assertEquals(0, cookie.getVersion());

        list = HttpCookie.parse("Set-cookie2:name=\"tes,t\"");
        cookie = list.get(0);
        // when two '"' presents, the parser ignores it.
        assertEquals("tes,t", cookie.getValue());

        // If cookie header = Set-Cookie2, version = 1
        list = HttpCookie
                .parse("Set-cookie2:test=null\";;Port=abde,82;Path=/temp;;;Discard;commentURl=http://harmonytest.org;Max-age=-10;");
        cookie = list.get(0);
        assertEquals("null\"", cookie.getValue());
        assertEquals(1, cookie.getVersion());
        assertEquals("/temp", cookie.getPath());
        assertTrue(cookie.getDiscard());
        assertEquals("http://harmonytest.org", cookie.getCommentURL());
        assertEquals(-10l, cookie.getMaxAge());
        assertTrue(cookie.hasExpired());
        assertEquals("abde,82", cookie.getPortlist());
        // Version 0 cookie
        list = HttpCookie
                .parse("Set-Cookie:name=tes,t;Comment=version1-cookie;Discard=false;commentURL=vers\nion1-cookie-url;Domain=x.y;");
        cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
        assertEquals("tes,t", cookie.getValue());
        assertEquals("name", cookie.getName());
        assertEquals("version1-cookie", cookie.getComment());
        assertEquals("vers\nion1-cookie-url", cookie.getCommentURL());
        assertEquals("x.y", cookie.getDomain());
        assertTrue(cookie.getDiscard());

        // Check value
        checkValidValue("Set-Cookie:", "val,ue");
        checkValidValue("Set-Cookie:", "val\nue");
        checkValidValue("Set-Cookie:", "value=value");
        checkValidValue("Set-Cookie2:", "val\nue");
        checkValidValue("Set-Cookie2:", "val\u2029ue");
        checkValidValue("Set-Cookie2:", "value=value");

        // Check comment
        // In RFC 2965 '=' is mandatory, but this is not the case in RI.
        list = HttpCookie.parse("Set-Cookie:name=tes,t;Comment;");
        cookie = list.get(0);
        assertNull(cookie.getComment());

        list = HttpCookie
                .parse("Set-Cookie:name=tes,t;Comment=sentence;Comment=anotherSentence");
        cookie = list.get(0);
        assertEquals("sentence", cookie.getComment());

        // Check CommentURL
        list = HttpCookie
                .parse("Set-Cookie:name=tes,t;Commenturl;commentuRL=(la,la)");
        cookie = list.get(0);
        assertEquals("(la,la)", cookie.getCommentURL());

        // Check Domain
        list = HttpCookie.parse("Set-Cookie:name=test;Domain=a_domain");
        cookie = list.get(0);
        assertEquals("a_domain", cookie.getDomain());

        // Check Path
        list = HttpCookie.parse("Set-Cookie:name=test;PaTh=pa$th");
        cookie = list.get(0);
        assertEquals("pa$th", cookie.getPath());

        // Check Max-Age
        list = HttpCookie.parse("Set-Cookie:name=test;Max-Age=1000");
        cookie = list.get(0);
        assertEquals(1000, cookie.getMaxAge());

        list = HttpCookie.parse("Set-Cookie:name=test;Max-Age=-1000");
        cookie = list.get(0);
        assertEquals(-1000, cookie.getMaxAge());

        // TODO: Uncomment when Long.parseLong() accepts numbers with a leading +
        // list = HttpCookie.parse("Set-Cookie:name=test;max-age=+12345;");
        // cookie = list.get(0);
        // assertEquals(12345, cookie.getMaxAge());

        list = HttpCookie.parse("Set-Cookie:name=test;max-age=0;");
        cookie = list.get(0);
        assertEquals(0, cookie.getMaxAge());

        // Check portlist
        list = HttpCookie.parse("Set-Cookie:name=tes,t;port");
        cookie = list.get(0);
        assertEquals("", cookie.getPortlist());

        list = HttpCookie.parse("Set-Cookie:name=tes,t;port=");
        cookie = list.get(0);
        assertEquals("", cookie.getPortlist());

        list = HttpCookie.parse("Set-Cookie:name=tes,t;port=123 345");
        cookie = list.get(0);
        assertEquals("123 345", cookie.getPortlist());

        list = HttpCookie.parse("Set-Cookie:name=tes,t;port=123,345");
        cookie = list.get(0);
        assertEquals("123,345", cookie.getPortlist());

        // Check Secure
        list = HttpCookie.parse("Set-Cookie:name=test;secure");
        cookie = list.get(0);
        assertTrue(cookie.getSecure());

        list = HttpCookie.parse("Set-Cookie:name=test;secure=fa");
        cookie = list.get(0);
        assertTrue(cookie.getSecure());
        assertFalse(cookie.hasExpired());

        list = HttpCookie.parse("Set-Cookie2:name=test;secure=false");
        cookie = list.get(0);
        assertTrue(cookie.getSecure());

        // Check expire
        list = HttpCookie.parse("Set-Cookie:name=test;expires=2006-10-23");
        cookie = list.get(0);
        assertEquals(0, cookie.getMaxAge());
        assertTrue(cookie.hasExpired());

        // Also recognize invalid date
        list = HttpCookie
                .parse("Set-Cookie:name=test;expires=Sun, 29-Feb-1999 19:14:07 GMT");
        cookie = list.get(0);
        assertTrue(cookie.getMaxAge() < 0);
        assertTrue(cookie.hasExpired());

        // Parse multiple cookies
        list = HttpCookie
                .parse("Set-Cookie2:name=test;,Set-Cookie2:name2=test2;comment=c234;");
        cookie = list.get(0);
        assertEquals("name", cookie.getName());
        assertEquals(1, cookie.getVersion());
        assertEquals("test", cookie.getValue());
        cookie = list.get(1);
        assertEquals(1, cookie.getVersion());
        // From the second cookie, the "set-cookie2" header does not take effect
        assertEquals("Set-Cookie2:name2", cookie.getName());
        assertEquals("c234", cookie.getComment());

        list = HttpCookie.parse("Set-Cookie2:name=test,name2=test2");
        assertEquals(1, list.get(0).getVersion());
        assertEquals(1, list.get(1).getVersion());

        // Must begin with "set-cookie2" header
        list = HttpCookie.parse("name=test,Set-Cookie2:name2=test2");
        cookie = list.get(0);
        assertEquals(1, list.size());

        HttpCookie c = HttpCookie.parse(
                "Set-cookie:NAME2=VALUE2;path=/t;domain=.b.c;version=1").get(0);
        assertEquals(1, c.getVersion());

        c = HttpCookie.parse(
                "Set-cookie2:NAME2=VALUE2;path=/t;domain=.b.c;version=0")
                .get(0);
        assertEquals(1, c.getVersion());

        list = HttpCookie.parse("Set-cookie:null=;Domain=null;Port=null");
        cookie = list.get(0);

        assertNotNull(cookie.getValue());
        assertNotNull(cookie.getName());
        assertNotNull(cookie.getDomain());
        assertNotNull(cookie.getPortlist());

        try {
            list = HttpCookie
                    .parse("Set-Cookie:a  name=tes,t;Commenturl;commentuRL=(la,la);path=hello");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException expected) {
        }


        list = HttpCookie
                .parse("Set-Cookie:name=tes,t;Commenturl;commentuRL=(la,la);commentuRL=hello");
        cookie = list.get(0);
        assertEquals("(la,la)", cookie.getCommentURL());

        list = HttpCookie
                .parse("Set-Cookie:name=tes,t;Commenturl;commentuRL=(la,la); path  =hello");
        cookie = list.get(0);
        assertEquals("(la,la)", cookie.getCommentURL());
        assertEquals("hello", cookie.getPath());

        try {
            list = HttpCookie
                    .parse("a  Set-Cookie:name=tes,t;Commenturl;commentuRL=(la,la);path=hello");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException expected) {
        }
    }

    public void test_Parse_httpOnly() {
        // Default is !httpOnly.
        List<HttpCookie> list = HttpCookie.parse("Set-Cookie: SID=31d4d96e407aad42");
        HttpCookie cookie = list.get(0);

        // Well formed, simple.
        list = HttpCookie.parse("Set-Cookie: SID=31d4d96e407aad42; HttpOnly");
        cookie = list.get(0);

        // Well formed, other attributes present.
        list = HttpCookie.parse("Set-Cookie: SID=31d4d96e407aad42; Path=/; Secure; HttpOnly");
        cookie = list.get(0);
        assertTrue(cookie.getSecure());
        assertEquals("/", cookie.getPath());

        // Mangled spacing, casing and attributes that have an (ignored) value.
        list = HttpCookie.parse("Set-Cookie:SID=31d4d96e407aad42;Path=/;secure=false;httponly=false");
        cookie = list.get(0);
        assertTrue(cookie.getSecure());
        assertEquals("/", cookie.getPath());
    }

    /**
     * java.net.HttpCookie#parse(String) for version conflict cases
     * @since 1.6
     */
    public void test_Parse_versionConflict() {
        // If attribute expires presents, cookie will be recognized as version
        // 0. No matter header is Set-cookie or Set-cookie2
        List<HttpCookie> list = HttpCookie
                .parse("Set-Cookie2:name=;expires=;discard");
        HttpCookie cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
        assertTrue(cookie.getDiscard());

        list = HttpCookie.parse("Set-Cookie: name=value;port=80");
        cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
        assertEquals("80", cookie.getPortlist());

        // In Set-Cookie header, max-age does not take effect when expires
        // exists.
        list = HttpCookie
                .parse("Set-Cookie:name=test;expires=Tue, 27-Jan-1998 19:14:07 GMT;Max-Age=1000");
        cookie = list.get(0);
        assertTrue(cookie.getMaxAge() < 0);
        assertTrue(cookie.hasExpired());
        assertFalse(cookie.getDiscard());
        // Reverse sequence. max-age takes effect and decides the result of
        // hasExpired() method.
        list = HttpCookie
                .parse("Set-Cookie:name=value;max-age=1000;expires=Tue, 17-Jan-1998 19:14:07 GMT;version=1");
        cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
        assertEquals(1000, cookie.getMaxAge());
        assertFalse(cookie.hasExpired());

        // expires decides the version. Not take Set-cookie header, version into
        // consideration if expires exists.
        list = HttpCookie
                .parse("Set-Cookie2:name=value;max-age=1000;version=1;expires=Tue, 17-Jan-1998 19:07:14 GMT;");
        cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
        assertEquals(1000, cookie.getMaxAge());
        assertFalse(cookie.hasExpired());

        // expires does not cover other version 1 attributes.
        list = HttpCookie
                .parse("Set-Cookie2: name=value;expires=Sun, 27-Jan-2018 19:14:07 GMT;comment=mycomment;port=80,8080");
        cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
        assertEquals("80,8080", cookie.getPortlist());
        assertEquals("mycomment", cookie.getComment());

        // When expires does not exist, version takes effect.
        list = HttpCookie.parse("Set-Cookie:name=test;Version=1");
        cookie = list.get(0);
        assertEquals(1, cookie.getVersion());
        assertEquals(-1, cookie.getMaxAge());
        list = HttpCookie.parse("Set-Cookie:name=test;vERsion=0;Version=1;versioN=0;vErsIon=1");
        cookie = list.get(0);
        assertEquals(1, cookie.getVersion());

        // When expires does not exist, max-age takes effect.
        list = HttpCookie.parse("Set-Cookie:name=test;Max-Age=11");
        cookie = list.get(0);
        assertEquals(1, cookie.getVersion());
        assertEquals(11, cookie.getMaxAge());
        // other version 1 attributes does not take effect
        list = HttpCookie
                .parse("Set-Cookie:name=test;comment=mycomment;commentURL=url;discard;domain=a.b.com;path=temp;port=79;secure");
        cookie = list.get(0);
        assertEquals(0, cookie.getVersion());
    }

    // http://b/31039416. Android N+ checks current time in hasExpired.
    // Repeated invocations of cookie.hasExpired() may return different results
    // due to time passage.
    // This was not the case in earlier android versions, where hasExpired
    // was testing the value of max-age/expires at the time of cookie creation.
    public void test_hasExpired_checksTime() throws Exception {
        List<HttpCookie> list = HttpCookie.parse("Set-Cookie:name=test;Max-Age=1");
        HttpCookie cookie = list.get(0);
        assertFalse(cookie.hasExpired());
        Thread.sleep(2000);
        assertTrue(cookie.hasExpired());
    }

    /**
     * java.net.HttpCookie#parse(String) on multiple threads
     * Regression test for HARMONY-6307
     * @since 1.6
     */
    class ParseThread extends Thread {
        public AssertionError error = null;

        public void run() {
            try {
                for (int i = 0; i < 200; i++) {
                    List<HttpCookie> list = HttpCookie.parse("Set-cookie:PREF=test;path=/;domain=.b.c;");
                    assertEquals(1, list.size());
                    HttpCookie cookie = list.get(0);
                    assertEquals(0, cookie.getVersion());
                    assertEquals(".b.c", cookie.getDomain());
                }
            } catch (AssertionError e) {
                error = e;
            }
        }
    }

    public void test_Parse_multipleThreads() throws InterruptedException {
        ParseThread[] threads = new ParseThread[10];
        // create threads
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ParseThread();
        }

        // start threads
        for (ParseThread thread : threads) {
            thread.start();
        }

        // wait for threads to finish
        for (ParseThread thread : threads) {
            thread.join();
        }

        for (ParseThread thread : threads) {
            if (thread.error != null) {
                fail("Assertion thrown in thread " + thread + ": " + thread.error);
            }
        }
    }

    private void checkValidValue(String header, String value) {
        List<HttpCookie> list = HttpCookie
                .parse(header + "name=" + value + ";");
        HttpCookie cookie = list.get(0);
        assertEquals(value, cookie.getValue());
    }

    private void checkInvalidCookie(String header) {
        try {
            HttpCookie.parse(header);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // version 0 cookie only takes effect on Locale.ENGLISH
        locale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
    }

    @Override
    protected void tearDown() throws Exception {
        Locale.setDefault(locale);
        super.tearDown();
    }

}
