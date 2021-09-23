/*
 * Copyright (C) 2009 The Android Open Source Project
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

package libcore.xml;

import com.android.org.kxml2.io.KXmlSerializer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlSerializer;
import static tests.support.Support_Xml.domOf;

public final class KxmlSerializerTest extends TestCase {
    private static final String NAMESPACE = null;

    public void testWhitespaceInAttributeValue() throws Exception {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer serializer = new KXmlSerializer();
        serializer.setOutput(stringWriter);
        serializer.startDocument("UTF-8", null);
        serializer.startTag(NAMESPACE, "a");
        serializer.attribute(NAMESPACE, "cr", "\r");
        serializer.attribute(NAMESPACE, "lf", "\n");
        serializer.attribute(NAMESPACE, "tab", "\t");
        serializer.attribute(NAMESPACE, "space", " ");
        serializer.endTag(NAMESPACE, "a");
        serializer.endDocument();
        assertXmlEquals("<a cr=\"&#13;\" lf=\"&#10;\" tab=\"&#9;\" space=\" \" />",
                stringWriter.toString());
    }

    public void testWriteDocument() throws Exception {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer serializer = new KXmlSerializer();
        serializer.setOutput(stringWriter);
        serializer.startDocument("UTF-8", null);
        serializer.startTag(NAMESPACE, "foo");
        serializer.attribute(NAMESPACE, "quux", "abc");
        serializer.startTag(NAMESPACE, "bar");
        serializer.endTag(NAMESPACE, "bar");
        serializer.startTag(NAMESPACE, "baz");
        serializer.endTag(NAMESPACE, "baz");
        serializer.endTag(NAMESPACE, "foo");
        serializer.endDocument();
        assertXmlEquals("<foo quux=\"abc\"><bar /><baz /></foo>", stringWriter.toString());
    }

    // http://code.google.com/p/android/issues/detail?id=21250
    public void testWriteSpecialCharactersInText() throws Exception {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer serializer = new KXmlSerializer();
        serializer.setOutput(stringWriter);
        serializer.startDocument("UTF-8", null);
        serializer.startTag(NAMESPACE, "foo");
        serializer.text("5'8\", 5 < 6 & 7 > 3!");
        serializer.endTag(NAMESPACE, "foo");
        serializer.endDocument();
        assertXmlEquals("<foo>5'8\", 5 &lt; 6 &amp; 7 &gt; 3!</foo>", stringWriter.toString());
    }

    private void assertXmlEquals(String expectedXml, String actualXml) throws Exception {
        String declaration = "<?xml version='1.0' encoding='UTF-8' ?>";
        assertEquals(declaration + expectedXml, actualXml);
    }

    private static XmlSerializer newSerializer() throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        XmlSerializer serializer = new KXmlSerializer();
        serializer.setOutput(bytesOut, "UTF-8");
        serializer.startDocument("UTF-8", null);
        return serializer;
    }

    public String fromCodePoint(int codePoint) {
        if (codePoint > Character.MAX_VALUE) {
            return new String(Character.toChars(codePoint));
        }
        return Character.toString((char) codePoint);
    }

    // http://b/17960630
    public void testSpeakNoEvilMonkeys() throws Exception {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer serializer = new KXmlSerializer();
        serializer.setOutput(stringWriter);
        serializer.startDocument("UTF-8", null);
        serializer.startTag(NAMESPACE, "tag");
        serializer.attribute(NAMESPACE, "attr", "a\ud83d\ude4ab");
        serializer.text("c\ud83d\ude4ad");
        serializer.cdsect("e\ud83d\ude4af");
        serializer.endTag(NAMESPACE, "tag");
        serializer.endDocument();
        assertXmlEquals("<tag attr=\"a&#128586;b\">" +
                        "c&#128586;d" +
                        "<![CDATA[e]]>&#128586;<![CDATA[f]]>" +
                        "</tag>", stringWriter.toString());

        // Check we can parse what we just output.
        Document doc = domOf(stringWriter.toString());
        Node root = doc.getDocumentElement();
        assertEquals("a\ud83d\ude4ab", root.getAttributes().getNamedItem("attr").getNodeValue());
        Text text = (Text) root.getFirstChild();
        assertEquals("c\ud83d\ude4ade\ud83d\ude4af", text.getNodeValue());
    }

    public void testBadSurrogates() throws Exception {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer serializer = new KXmlSerializer();
        serializer.setOutput(stringWriter);
        serializer.startDocument("UTF-8", null);
        serializer.startTag(NAMESPACE, "tag");
        try {
            serializer.attribute(NAMESPACE, "attr", "a\ud83d\u0040b");
            fail();
        } catch (IllegalArgumentException expected) {
        }
        try {
            serializer.text("c\ud83d\u0040d");
            fail();
        } catch (IllegalArgumentException expected) {
        }
        try {
            serializer.cdsect("e\ud83d\u0040f");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    // Cover all the BMP code points plus a few that require us to use surrogates.
    private static int MAX_TEST_CODE_POINT = 0x10008;

    public void testInvalidCharactersInText() throws IOException {
        XmlSerializer serializer = newSerializer();
        serializer.startTag(NAMESPACE, "root");
        for (int c = 0; c <= MAX_TEST_CODE_POINT; ++c) {
            final String s = fromCodePoint(c);
            if (isValidXmlCodePoint(c)) {
                serializer.text("a" + s + "b");
            } else {
                try {
                    serializer.text("a" + s + "b");
                    fail(s);
                } catch (IllegalArgumentException expected) {
                }
            }
        }
        serializer.endTag(NAMESPACE, "root");
    }

    public void testInvalidCharactersInAttributeValues() throws IOException {
        XmlSerializer serializer = newSerializer();
        serializer.startTag(NAMESPACE, "root");
        for (int c = 0; c <= MAX_TEST_CODE_POINT; ++c) {
            final String s = fromCodePoint(c);
            if (isValidXmlCodePoint(c)) {
                serializer.attribute(NAMESPACE, "a", "a" + s + "b");
            } else {
                try {
                    serializer.attribute(NAMESPACE, "a", "a" + s + "b");
                    fail(s);
                } catch (IllegalArgumentException expected) {
                }
            }
        }
        serializer.endTag(NAMESPACE, "root");
    }

    public void testInvalidCharactersInCdataSections() throws IOException {
        XmlSerializer serializer = newSerializer();
        serializer.startTag(NAMESPACE, "root");
        for (int c = 0; c <= MAX_TEST_CODE_POINT; ++c) {
            final String s = fromCodePoint(c);
            if (isValidXmlCodePoint(c)) {
                serializer.cdsect("a" + s + "b");
            } else {
                try {
                    serializer.cdsect("a" + s + "b");
                    fail(s);
                } catch (IllegalArgumentException expected) {
                }
            }
        }
        serializer.endTag(NAMESPACE, "root");
    }

    public void testCdataWithTerminatorInside() throws Exception {
        StringWriter writer = new StringWriter();
        XmlSerializer serializer = new KXmlSerializer();
        serializer.setOutput(writer);
        serializer.startDocument("UTF-8", null);
        serializer.startTag(NAMESPACE, "p");
        serializer.cdsect("a]]>b");
        serializer.endTag(NAMESPACE, "p");
        serializer.endDocument();
        // Adjacent CDATA sections aren't merged, so let's stick them together ourselves...
        Document doc = domOf(writer.toString());
        NodeList children = doc.getFirstChild().getChildNodes();
        String text = "";
        for (int i = 0; i < children.getLength(); ++i) {
            text += children.item(i).getNodeValue();
        }
        assertEquals("a]]>b", text);
    }

    private static boolean isValidXmlCodePoint(int c) {
        // http://www.w3.org/TR/REC-xml/#charsets
        return (c >= 0x20 && c <= 0xd7ff) || (c == 0x9) || (c == 0xa) || (c == 0xd) ||
                (c >= 0xe000 && c <= 0xfffd) || (c >= 0x10000 && c <= 0x10ffff);
    }
}
