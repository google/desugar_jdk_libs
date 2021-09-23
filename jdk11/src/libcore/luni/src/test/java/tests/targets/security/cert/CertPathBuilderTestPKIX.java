/*
 * Copyright (C) 2009 The Android Open Source Project
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
package tests.targets.security.cert;

import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import libcore.java.security.TestKeyStore;
import tests.security.CertPathBuilderTest;

public class CertPathBuilderTestPKIX extends CertPathBuilderTest {

    public CertPathBuilderTestPKIX() {
        super("PKIX");
    }

    @Override
    public CertPathParameters getCertPathParameters() throws Exception {
        TestKeyStore clientAndCa = TestKeyStore.getClientCertificate();
        PrivateKeyEntry pke = clientAndCa.getPrivateKey("RSA", "RSA");
        X509Certificate clientCert = (X509Certificate) pke.getCertificate();

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("rootCA", clientAndCa.getRootCertificate("RSA"));

        X509CertSelector targetConstraints = new X509CertSelector();
        targetConstraints.setCertificate(clientCert);

        List<Certificate> certList = new ArrayList<Certificate>();
        for (Certificate certificate : pke.getCertificateChain()) {
            certList.add(certificate);
        }
        CertStoreParameters storeParams = new CollectionCertStoreParameters(certList);

        CertStore certStore = CertStore.getInstance("Collection", storeParams);

        PKIXBuilderParameters parameters = new PKIXBuilderParameters(keyStore, targetConstraints);
        parameters.addCertStore(certStore);
        parameters.setRevocationEnabled(false);
        return parameters;
    }

    @Override
    public void validateCertPath(CertPath path) {
        List<? extends Certificate> certificates = path.getCertificates();

        // CertPath should not include the Trust Anchor, so the path should be:
        // [[ end entity <- intermediate CA ]] <- root CA
        assertEquals(2, certificates.size());

        Certificate endEntityCert = certificates.get(0);
        assertEquals("Certificate must be of X.509 type", "X.509", endEntityCert.getType());

        X509Certificate endEntityX509Cert = (X509Certificate) endEntityCert;
        X500Principal endEntityPrincipal = endEntityX509Cert.getSubjectX500Principal();

        X500Principal expectedPrincipal = new X500Principal("emailAddress=test@user");

        assertEquals(expectedPrincipal, endEntityPrincipal);
    }
}
