/*
 * Copyright (C) 2013 The Android Open Source Project
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

import com.android.org.kxml2.io.KXmlParser;
import com.android.org.kxml2.io.KXmlSerializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import junit.framework.TestCase;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class XmlPullParserFactoryTest extends TestCase {

    public void testDefaultNewInstance() throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance(null, null);
        XmlPullParser parser = factory.newPullParser();
        XmlSerializer serializer = factory.newSerializer();

        assertNotNull(parser);
        assertNotNull(serializer);
        assertTrue(parser instanceof KXmlParser);
        assertTrue(serializer instanceof KXmlSerializer);
    }

    /**
     * Tests that trying to instantiate a parser with an empty list of
     * parsers and serializers fails.
     */
    public void testOverriding_emptyClassList() {
        TestXmlPullParserFactory tf = new TestXmlPullParserFactory(null, null);

        try {
            tf.newPullParser();
            fail();
        } catch (XmlPullParserException expected) {
        }

        try {
            tf.newPullParser();
            fail();
        } catch (XmlPullParserException expected) {
        }
    }

    public void testOverriding_customClassList() throws Exception {
        TestXmlPullParserFactory tf = new TestXmlPullParserFactory(
                new String[] { "libcore.xml.XmlPullParserFactoryTest$XmlPullParserStub" },
                new String[] { "libcore.xml.XmlPullParserFactoryTest$XmlSerializerStub" });

        assertTrue(tf.newPullParser() instanceof XmlPullParserStub);
        assertTrue(tf.newSerializer() instanceof XmlSerializerStub);

        // Also check that we ignore instantiation errors as long as
        // at least one parser / serializer is instantiable.
        tf = new TestXmlPullParserFactory(
                new String[] {
                        "libcore.xml.XmlPullParserFactoryTest$InaccessibleXmlParser",
                        "libcore.xml.XmlPullParserFactoryTest$XmlPullParserStub" },
                new String[] {
                        "libcore.xml.XmlPullParserFactoryTest$InaccessibleXmlSerializer",
                        "libcore.xml.XmlPullParserFactoryTest$XmlSerializerStub" });

        assertTrue(tf.newPullParser() instanceof XmlPullParserStub);
        assertTrue(tf.newSerializer() instanceof XmlSerializerStub);
    }

    // https://b/12956724
    public void testSetFeature_setsFeatureOnlyIfTrue() throws Exception {
        TestXmlPullParserFactory tf = new TestXmlPullParserFactory(
                new String[] { "libcore.xml.XmlPullParserFactoryTest$XmlParserThatHatesAllFeatures" }, null);

        tf.setFeature("foo", false);
        tf.newPullParser();
    }


    /**
     * A class that makes use of inherited XmlPullParserFactory fields to check they are
     * supported.
     */
    static final class TestXmlPullParserFactory extends XmlPullParserFactory {
        TestXmlPullParserFactory(String[] parserClassList, String[] serializerClassList) {
            super();
            parserClasses.remove(0);
            serializerClasses.remove(0);

            try {
                if (parserClassList != null) {
                    for (String parserClass : parserClassList) {
                        parserClasses.add(Class.forName(parserClass));
                    }
                }

                if (serializerClassList != null) {
                    for (String serializerClass : serializerClassList) {
                        serializerClasses.add(Class.forName(serializerClass));
                    }
                }
            } catch (ClassNotFoundException ignored) {
                throw new AssertionError(ignored);
            }
        }
    }

    public static final class XmlParserThatHatesAllFeatures extends XmlPullParserStub {
        @Override
        public void setFeature(String name, boolean state) {
            fail();
        }
    }

    static final class InaccessibleXmlSerializer extends XmlSerializerStub {
    }

    static final class InaccessibleXmlParser extends XmlPullParserStub {
    }

    public static class XmlSerializerStub implements XmlSerializer {

        public void setFeature(String name, boolean state) throws IllegalArgumentException,
                IllegalStateException {
        }

        public boolean getFeature(String name) {
            return false;
        }

        public void setProperty(String name, Object value) {
        }

        public Object getProperty(String name) {
            return null;
        }

        public void setOutput(OutputStream os, String encoding) throws IOException {
        }

        public void setOutput(Writer writer) throws IOException {
        }

        public void startDocument(String encoding, Boolean standalone) throws IOException {
        }

        public void endDocument() throws IOException {
        }

        public void setPrefix(String prefix, String namespace) throws IOException {
        }

        public String getPrefix(String namespace, boolean generatePrefix) throws IllegalArgumentException {
            return null;
        }

        public int getDepth() {
            return 0;
        }

        public String getNamespace() {
            return null;
        }

        public String getName() {
            return null;
        }

        public XmlSerializer startTag(String namespace, String name) throws IOException {
            return null;
        }

        public XmlSerializer attribute(String namespace, String name, String value) throws IOException {
            return null;
        }

        public XmlSerializer endTag(String namespace, String name) throws IOException {
            return null;
        }

        public XmlSerializer text(String text) throws IOException {
            return null;
        }

        public XmlSerializer text(char[] buf, int start, int len) throws IOException {
            return null;
        }

        public void cdsect(String text) throws IOException {
        }

        public void entityRef(String text) throws IOException {
        }

        public void processingInstruction(String text) throws IOException {
        }

        public void comment(String text) throws IOException {
        }

        public void docdecl(String text) throws IOException {
        }

        public void ignorableWhitespace(String text) throws IOException {
        }

        public void flush() throws IOException {
        }
    }

    public static class XmlPullParserStub implements XmlPullParser {
        public void setFeature(String name, boolean state) throws XmlPullParserException {
        }

        public boolean getFeature(String name) {
            return false;
        }

        public void setProperty(String name, Object value) throws XmlPullParserException {
        }

        public Object getProperty(String name) {
            return null;
        }

        public void setInput(Reader in) throws XmlPullParserException {
        }

        public void setInput(InputStream inputStream, String inputEncoding)
                throws XmlPullParserException {
        }

        public String getInputEncoding() {
            return null;
        }

        public void defineEntityReplacementText(String entityName, String replacementText)
                throws XmlPullParserException {
        }

        public int getNamespaceCount(int depth) throws XmlPullParserException {
            return 0;
        }

        public String getNamespacePrefix(int pos) throws XmlPullParserException {
            return null;
        }

        public String getNamespaceUri(int pos) throws XmlPullParserException {
            return null;
        }

        public String getNamespace(String prefix) {
            return null;
        }

        public int getDepth() {
            return 0;
        }

        public String getPositionDescription() {
            return null;
        }

        public int getLineNumber() {
            return 0;
        }

        public int getColumnNumber() {
            return 0;
        }

        public boolean isWhitespace() throws XmlPullParserException {
            return false;
        }

        public String getText() {
            return null;
        }

        public char[] getTextCharacters(int[] holderForStartAndLength) {
            return null;
        }

        public String getNamespace() {
            return null;
        }

        public String getName() {
            return null;
        }

        public String getPrefix() {
            return null;
        }

        public boolean isEmptyElementTag() throws XmlPullParserException {
            return false;
        }

        public int getAttributeCount() {
            return 0;
        }

        public String getAttributeNamespace(int index) {
            return null;
        }

        public String getAttributeName(int index) {
            return null;
        }

        public String getAttributePrefix(int index) {
            return null;
        }

        public String getAttributeType(int index) {
            return null;
        }

        public boolean isAttributeDefault(int index) {
            return false;
        }

        public String getAttributeValue(int index) {
            return null;
        }

        public String getAttributeValue(String namespace, String name) {
            return null;
        }

        public int getEventType() throws XmlPullParserException {
            return 0;
        }

        public int next() throws XmlPullParserException, IOException {
            return 0;
        }

        public int nextToken() throws XmlPullParserException, IOException {
            return 0;
        }

        public void require(int type, String namespace, String name)
                throws XmlPullParserException, IOException {
        }

        public String nextText() throws XmlPullParserException, IOException {
            return null;
        }

        public int nextTag() throws XmlPullParserException, IOException {
            return 0;
        }
    }
}
