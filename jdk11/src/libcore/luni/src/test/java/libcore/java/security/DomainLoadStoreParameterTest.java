/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore.java.security;

import junit.framework.TestCase;

import java.net.URI;
import java.security.KeyStore;
import java.security.DomainLoadStoreParameter;
import java.util.HashMap;
import java.util.Map;

public class DomainLoadStoreParameterTest extends TestCase {
    private static final String KEY_STORE_NAME = "keyStoreName";
    private KeyStore.ProtectionParameter protectionParameter;
    private URI validConfigurationURI;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        protectionParameter = new KeyStore.ProtectionParameter() {};
        validConfigurationURI = new URI("http://UriForConfiguration.SergioRulesTheWorld.com/");
    }

    public void testConstructor_nullValues_throwException() throws Exception {
        try {
            new DomainLoadStoreParameter(null /* configuration */,
                    createNonEmptyParameters(KEY_STORE_NAME, protectionParameter));
            fail("configuration can't be null when creating DomainLoadStoreParameter");
        } catch (NullPointerException expected) {
        }

        try {
            new DomainLoadStoreParameter(validConfigurationURI, null /* protectionParameters */);
            fail("protection parameters can't be null when creating DomainLoadStoreParameter");
        } catch (NullPointerException expected) {
        }
    }

    /**
     * Check that it returns the configuration specified in the constructor.
     */
    public void testGetConfiguration() {
        DomainLoadStoreParameter domainLoadStoreParameter =
                new DomainLoadStoreParameter(validConfigurationURI,
                        createNonEmptyParameters(KEY_STORE_NAME, protectionParameter));
        assertSame(validConfigurationURI, domainLoadStoreParameter.getConfiguration());
    }

    public void testGetProtectionParams() {
        Map<String, KeyStore.ProtectionParameter> protectionParameters =
                createNonEmptyParameters(KEY_STORE_NAME, protectionParameter);
        DomainLoadStoreParameter domainLoadStoreParameter =
                new DomainLoadStoreParameter(validConfigurationURI, protectionParameters);
        Map<String, KeyStore.ProtectionParameter> returnedParams =
                domainLoadStoreParameter.getProtectionParams();
        assertEquals(protectionParameters, returnedParams);

        // Trying to add to the returned set throws an exception
        try {
            returnedParams.put("some_other_keystore", protectionParameter);
            fail("The parameters returned by getProtectionParams should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }

        // Adding to the map passed as parameter doesn't change value in the
        // {@code DomainLoadStoreParameter}, ie, it holds a copy.
        Map<String, KeyStore.ProtectionParameter> originalProtectionParameters
                = new HashMap<>(protectionParameters);
        protectionParameters.put("some_other_keystore", protectionParameter);
        assertEquals(originalProtectionParameters,
                domainLoadStoreParameter.getProtectionParams());

    }

    /**
     * Getter for the protection parameters in this domain.
     *
     * Check that always returns null. Keystore domains do not support a protection parameter.
     */
    public void testGetProtectionParameter() {
        DomainLoadStoreParameter domainLoadStoreParameter =
                new DomainLoadStoreParameter(validConfigurationURI,
                        createNonEmptyParameters("keyStoreName", protectionParameter));
        // Check that always returns null. Keystore domains do not support a protection parameter.
        assertNull(domainLoadStoreParameter.getProtectionParameter());
    }

    private Map<String, KeyStore.ProtectionParameter> createNonEmptyParameters(
            String keyStoreName, KeyStore.ProtectionParameter protectionParameter) {
        Map<String, KeyStore.ProtectionParameter> protectionParameters = new HashMap<>();
        protectionParameters.put(keyStoreName, protectionParameter);
        return protectionParameters;
    }
}
