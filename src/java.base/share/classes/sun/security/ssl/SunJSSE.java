/*
 * Copyright (c) 1999, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.security.ssl;

import java.security.Provider;
import java.util.*;
import static sun.security.util.SecurityConstants.PROVIDER_VER;

/**
 * The JSSE provider.
 */
public class SunJSSE extends java.security.Provider {

    @java.io.Serial
    private static final long serialVersionUID = 3231825739635378733L;

    private static final String info = "Sun JSSE provider" +
        "(PKCS12, SunX509/PKIX key/trust factories, " +
        "SSLv3/TLSv1/TLSv1.1/TLSv1.2/TLSv1.3/DTLSv1.0/DTLSv1.2)";

    public SunJSSE() {
        super("SunJSSE", PROVIDER_VER, info);
        registerAlgorithms();
    }

    private void ps(String type, String algo, String cn,
            List<String> a, HashMap<String, String> attrs) {
        putService(new Provider.Service(this, type, algo, cn, a, attrs));
    }

    private void registerAlgorithms() {
        ps("Signature", "MD5andSHA1withRSA",
            "sun.security.ssl.RSASignature", null, null);

        ps("KeyManagerFactory", "SunX509",
            "sun.security.ssl.KeyManagerFactoryImpl$SunX509", null, null);
        ps("KeyManagerFactory", "NewSunX509",
            "sun.security.ssl.KeyManagerFactoryImpl$X509",
            List.of("PKIX"), null);

        ps("TrustManagerFactory", "SunX509",
            "sun.security.ssl.TrustManagerFactoryImpl$SimpleFactory",
            null, null);
        ps("TrustManagerFactory", "PKIX",
            "sun.security.ssl.TrustManagerFactoryImpl$PKIXFactory",
            List.of("SunPKIX", "X509", "X.509"), null);

        ps("SSLContext", "TLSv1",
            "sun.security.ssl.SSLContextImpl$TLS10Context",
            List.of("SSLv3"), null);
        ps("SSLContext", "TLSv1.1",
            "sun.security.ssl.SSLContextImpl$TLS11Context", null, null);
        ps("SSLContext", "TLSv1.2",
            "sun.security.ssl.SSLContextImpl$TLS12Context", null, null);
        ps("SSLContext", "TLSv1.3",
            "sun.security.ssl.SSLContextImpl$TLS13Context", null, null);
        ps("SSLContext", "TLS",
            "sun.security.ssl.SSLContextImpl$TLSContext",
            List.of("SSL"), null);

        ps("SSLContext", "DTLSv1.0",
            "sun.security.ssl.SSLContextImpl$DTLS10Context", null, null);
        ps("SSLContext", "DTLSv1.2",
            "sun.security.ssl.SSLContextImpl$DTLS12Context", null, null);
        ps("SSLContext", "DTLS",
            "sun.security.ssl.SSLContextImpl$DTLSContext", null, null);

        ps("SSLContext", "Default",
            "sun.security.ssl.SSLContextImpl$DefaultSSLContext", null, null);

        /*
         * KeyStore
         */
        ps("KeyStore", "PKCS12",
            "sun.security.pkcs12.PKCS12KeyStore", null, null);
    }
}
