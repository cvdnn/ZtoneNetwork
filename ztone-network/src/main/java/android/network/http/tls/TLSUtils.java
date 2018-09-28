/*
 * Copyright 2015 Yan Zhenjie
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
package android.network.http.tls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class TLSUtils {
    private static final String TAG = "SSLUtil";

    public static void set(OkHttpClient.Builder clientBuilder) {
        if (clientBuilder != null) {
            clientBuilder.hostnameVerifier(IHostNameVerifier.sVerifierInstance);

            SSLContext sslContext = getDefaultSSLContext();
            if (sslContext != null) {
                clientBuilder.sslSocketFactory(sslContext.getSocketFactory(), mTrustManagers);
            }
        }
    }

    /**
     * 拿到https证书, SSLContext (NoHttp已经修补了系统的SecureRandom的bug)。
     */
    @SuppressLint("TrulyRandom")
    public static SSLContext getSSLContext(Context context, String serPath, char[] password) {
        SSLContext sslContext = null;

        if (context != null && !TextUtils.isEmpty(serPath)) {
            try {
                sslContext = SSLContext.getInstance("TLS");
                InputStream inputStream = context.getAssets().open(serPath);

                CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
                Certificate cer = cerFactory.generateCertificate(inputStream);

                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                keyStore.load(null, password);
                keyStore.setCertificateEntry("trust", cer);

                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, null);

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);

                sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        return sslContext;
    }

    /**
     * 如果不需要https证书.(NoHttp已经修补了系统的SecureRandom的bug)。
     */
    public static SSLContext getDefaultSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{mTrustManagers}, new SecureRandom());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return sslContext;
    }

    private static X509TrustManager mTrustManagers = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

}
