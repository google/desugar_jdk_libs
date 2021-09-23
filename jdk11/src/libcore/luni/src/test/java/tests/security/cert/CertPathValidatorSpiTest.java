/*
 * Copyright (C) 2021 The Android Open Source Project
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
 * limitations under the License
 */

package tests.security.cert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.cert.CertPath;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CertPathValidatorSpiTest {
  // engineValidate() implementations are tested via the CertPathValidator*Tests.

  @Test
  public void engineGetRevocationChecker_Unsupported() {
    CertPathValidatorSpi spi = new PathValidatorSpiNoRevocation();
    try {
      spi.engineGetRevocationChecker();
      fail();
    } catch (UnsupportedOperationException e) {
      // Expected
    }
  }

  @Test
  public void engineGetRevocationChecker_Supported() {
    CertPathValidatorSpi spi = new PathValidatorSpiWithRevocation();
    assertEquals(PathChecker.class, spi.engineGetRevocationChecker().getClass());
  }

  // Stub CertPathValidatorSpi which has no revocation checker.
  private static class PathValidatorSpiNoRevocation extends CertPathValidatorSpi {
    @Override
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params) {
      throw new AssertionError("Stub");
    }
  }

  // Stub CertPathValidatorSpi which has a revocation checker.
  private static class PathValidatorSpiWithRevocation extends CertPathValidatorSpi {

    @Override
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params) {
      throw new AssertionError("Stub");
    }

    @Override
    public CertPathChecker engineGetRevocationChecker() {
      return new PathChecker();
    }
  }

  private static class PathChecker implements CertPathChecker {
    @Override
    public void init(boolean forward) {
      throw new AssertionError("Stub");
    }

    @Override
    public boolean isForwardCheckingSupported() {
      throw new AssertionError("Stub");
    }

    @Override
    public void check(Certificate cert) {
      throw new AssertionError("Stub");
    }
  }
}
