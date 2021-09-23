/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tests.support;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * This class is responsible for providing the dynamic names and addresses for
 * the java.net classes. There are two directories which need to be placed on an
 * ftp server and an http server which should accompany this source. The
 * ftp-files have to be placed on an ftp server and have to be the root of a
 * user jcltest with password jclpass. The testres files must be available on an
 * HTTP server and the name and location can be configured below.
 */
public class Support_Configuration {

    public static final String DomainAddress = "apache.org";

    public static final String WebName = "jcltest.";

    public static final String HomeAddress =  WebName + DomainAddress;

    public static final String TestResourcesDir = "/testres231";

    public static final String TestResources =  HomeAddress + TestResourcesDir;

    public static final String HomeAddressResponse = "HTTP/1.1 200 OK";

    public static final String HomeAddressSoftware = "Jetty(6.0.x)";

    public static String ProxyServerTestHost = "jcltest.apache.org";

    public static final String SocksServerTestHost = "jcltest.apache.org";

    public static final int SocksServerTestPort = 1080;

    // the bytes for an address which represents an address which is not
    // one of the addresses for any of our machines on which tests will run
    // it is used to verify we get the expected error when we try to bind
    // to an address that is not one of the machines local addresses
    public static final byte nonLocalAddressBytes[] = { 1, 0, 0, 0 };

    public static final String FTPTestAddress = "jcltest:jclpass@localhost";

    public static final String URLConnectionLastModifiedString = "Mon, 14 Jun 1999 21:06:22 GMT";

    public static final long URLConnectionLastModified = 929394382000L;

    public static final long URLConnectionDate = 929106872000L;
}
