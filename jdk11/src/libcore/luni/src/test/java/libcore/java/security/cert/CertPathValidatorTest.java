/*
 * Copyright 2016 The Android Open Source Project
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

package libcore.java.security.cert;

import java.security.KeyStore.PrivateKeyEntry;
import java.security.PrivateKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import libcore.java.security.TestKeyStore;
import libcore.junit.junit3.TestCaseWithRules;
import libcore.junit.util.EnableDeprecatedBouncyCastleAlgorithmsRule;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.BasicOCSPRespBuilder;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.OCSPRespBuilder;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Rule;
import org.junit.rules.TestRule;

public class CertPathValidatorTest extends TestCaseWithRules {

    // Allow access to deprecated BC algorithms in this test, so we can ensure they
    // continue to work
    @Rule
    public TestRule enableDeprecatedBCAlgorithmsRule =
            EnableDeprecatedBouncyCastleAlgorithmsRule.getInstance();

    private OCSPResp generateOCSPResponse(X509Certificate serverCertJca, X509Certificate caCertJca,
            PrivateKey caKey, CertificateStatus status) throws Exception {
        X509CertificateHolder caCert = new JcaX509CertificateHolder(caCertJca);

        DigestCalculatorProvider digCalcProv = new BcDigestCalculatorProvider();
        BasicOCSPRespBuilder basicBuilder = new BasicOCSPRespBuilder(
                SubjectPublicKeyInfo.getInstance(caCertJca.getPublicKey().getEncoded()),
                digCalcProv.get(CertificateID.HASH_SHA1));

        CertificateID certId = new CertificateID(digCalcProv.get(CertificateID.HASH_SHA1),
                caCert, serverCertJca.getSerialNumber());

        basicBuilder.addResponse(certId, status);

        BasicOCSPResp resp = basicBuilder.build(
                new JcaContentSignerBuilder("SHA1withRSA").build(caKey), null, new Date());

        OCSPRespBuilder builder = new OCSPRespBuilder();
        return builder.build(OCSPRespBuilder.SUCCESSFUL, resp);
    }

    private void runOCSPStapledTest(CertificateStatus certStatus, final boolean goodStatus)
            throws Exception {
        PrivateKeyEntry serverEntry = TestKeyStore.getServer().getPrivateKey("RSA", "RSA");
        PrivateKeyEntry caEntry = TestKeyStore.getIntermediateCa().getPrivateKey("RSA", "RSA");
        PrivateKeyEntry rootCaEntry = TestKeyStore.getRootCa().getPrivateKey("RSA", "RSA");

        X509Certificate serverCert = (X509Certificate) serverEntry.getCertificate();
        OCSPResp ocspResponse = generateOCSPResponse(serverCert,
                (X509Certificate) caEntry.getCertificate(), caEntry.getPrivateKey(), certStatus);

        PKIXParameters params = new PKIXParameters(Collections
                .singleton(new TrustAnchor((X509Certificate) rootCaEntry.getCertificate(), null)));

        // By default we shouldn't have a PKIXRevocationChecker already.
        for (PKIXCertPathChecker checker : params.getCertPathCheckers()) {
            assertFalse(checker instanceof PKIXRevocationChecker);
        }

        CertPathValidator cpv = CertPathValidator.getInstance("PKIX");

        PKIXRevocationChecker revChecker = (PKIXRevocationChecker) cpv.getRevocationChecker();
        revChecker.setOptions(Collections.singleton(Option.ONLY_END_ENTITY));
        revChecker.setOcspResponses(
                Collections.singletonMap(serverCert, ocspResponse.getEncoded()));

        List<PKIXCertPathChecker> checkers = new ArrayList<>(params.getCertPathCheckers());
        checkers.add(revChecker);
        params.setCertPathCheckers(checkers);

        ArrayList<X509Certificate> chain = new ArrayList<>();
        chain.add(serverCert);
        chain.add((X509Certificate) caEntry.getCertificate());

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        CertPath certPath = cf.generateCertPath(chain);

        try {
            cpv.validate(certPath, params);
            assertTrue("should fail with failure OCSP status", goodStatus);
        } catch (CertPathValidatorException maybeExpected) {
            assertFalse("should not fail with good OCSP status", goodStatus);
        }
    }

    public void test_OCSP_EndEntity_KeyCompromise_Failure() throws Exception {
        runOCSPStapledTest(new RevokedStatus(new Date(), CRLReason.keyCompromise), false);
    }

    public void test_OCSP_EndEntity_Good_Success() throws Exception {
        runOCSPStapledTest(CertificateStatus.GOOD, true);
    }
}
