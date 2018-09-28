package android.network.http;

import android.assist.Assert;
import android.assist.TextUtilz;
import android.content.Context;
import android.log.Log;
import android.math.Maths;
import android.math.ShortDigest;
import android.network.DNSUtils;
import android.network.http.interceptor.AnalyticsInterceptor;
import android.network.http.interceptor.GeneralHeaderInterceptor;
import android.network.http.interceptor.GzipRequestInterceptor;
import android.network.http.tls.TLSUtils;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.webkit.URLUtil;

import java.io.File;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Version;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by handy on 17-2-3.
 */

public final class HTTPx {
    private static final String TAG = "HTTP";

    public static final int TIMEOUT_CONNECTION = 1500;
    public static final int TIMEOUT_READ_WRITE = 20;

    public static final long VALIDITY_TIME = 300000;

    public static final class Client {
        public static final ConnectionPool mConnectionPool = new ConnectionPool(8, 5, TimeUnit.MINUTES);

        public static OkHttpClient Impl;

        public static void onInit(Context context) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

            clientBuilder.followRedirects(true);
            clientBuilder.followSslRedirects(true);

            clientBuilder.connectTimeout(TIMEOUT_CONNECTION, SECONDS);
            clientBuilder.readTimeout(TIMEOUT_READ_WRITE, SECONDS);
            clientBuilder.writeTimeout(TIMEOUT_READ_WRITE, SECONDS);

            // config
            clientBuilder.connectionPool(mConnectionPool);
            clientBuilder.cookieJar(new WebViewCookieJar(context));
            DNSUtils.binding(clientBuilder);
            TLSUtils.set(clientBuilder);

            // interceptor
            clientBuilder.addInterceptor(new GeneralHeaderInterceptor());
            clientBuilder.addNetworkInterceptor(new GzipRequestInterceptor());
            clientBuilder.addNetworkInterceptor(new AnalyticsInterceptor());

            Impl = clientBuilder.build();
        }

        public static Response execute(OkHttpClient httpClient, Request request) {
            Response response = null;

            if (httpClient != null && request != null) {
                try {
                    response = httpClient.newCall(request).execute();
                } catch (Exception e) {
                    Log.e(TAG, e);
                }
            }

            return response;
        }

        /**
         * 访问网络
         */
        public static Response execute(Request request) {
            Response httpResponse = null;

            if (request != null) {
                try {
                    httpResponse = Impl.newCall(request).execute();
                } catch (SocketTimeoutException e) {
                    Log.e(TAG, e);

                } catch (SocketException e) {
                    Log.e(TAG, e);

                } catch (Exception e) {
                    Log.e(TAG, e);
                }
            }

            return httpResponse;
        }
    }

    public final class Headers {
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String REPLY_TIMESTAMP = "Reply-Timestamp";
        public static final String RANGE = "Range";
        public static final String RANGE4 = "RANGE";
        public static final String USER_AGENT = "User-Agent";

        public static final String GZIP = "gzip";

        public static final String COOKIE = "Cookie";
    }

    public static String userAgent() {

        return String.format("Android/%s(SDK: %d); %s; %s",
                Build.VERSION.RELEASE, Build.VERSION.SDK_INT, Version.userAgent(), Build.FINGERPRINT);
    }

    public static Map<String, String> query(String url) {
        Map<String, String> valueContent = new TreeMap<>();

        if (URLUtil.isNetworkUrl(url)) {
            int queryIndex = url.indexOf('?'), cursor = queryIndex + 1, len = url.length();
            if (queryIndex >= 0 && cursor < len) {
                int start = cursor;

                String key = null;
                while (cursor < len) {
                    char c = url.charAt(cursor);

                    if (c == '=') {
                        if (start < cursor) {
                            key = url.substring(start, cursor);
                        }

                        start = cursor + 1;
                    } else if (c == '&') {
                        if (Assert.notEmpty(key)) {
                            valueContent.put(key, url.substring(start, cursor + 1 == len ? cursor + 1 : cursor));

                            key = null;
                        }

                        start = cursor + 1;
                    }

                    // end
                    if (cursor + 1 == len && Assert.notEmpty(key)) {
                        valueContent.put(key, url.substring(start, cursor + 1 == len ? cursor + 1 : cursor));

                        key = null;

                        break;
                    }

                    cursor++;
                }
            }
        }

        return valueContent;
    }

    public static HttpUrl parse(@NonNull String url) {
        HttpUrl httpURL = null;

        if (Assert.notEmpty(url)) {
            try {
                httpURL = HttpUrl.parse(url);
            } catch (Exception e) {
                Log.d(TAG, e);
            }
        }

        return httpURL;
    }

    /**
     * 判断是否是Gzip压缩的
     *
     * @param response 请求
     * @return
     */
    public static boolean isGzipEncoding(Response response) {
        boolean result = false;

        if (response != null) {
            String encodingValue = response.header(Headers.CONTENT_ENCODING);
            if (Assert.notEmpty(encodingValue)) {
                result = encodingValue.toLowerCase(Locale.US).contains(Headers.GZIP);
            }
        }

        return result;
    }

    public static int getAPI(String api) {

        return Maths.valueOf(api, 0);
    }

    public static String getFakeMixSign(String text, String timestamp) {

        return TextUtilz.toTrim(text + "" + timestamp);
    }

    public static String getURL(Request request) {
        String url = "";

        if (request != null) {
            HttpUrl httpURL = request.url();
            if (httpURL != null) {
                url = httpURL.toString();
            }
        }

        return url;
    }

    public static String getHostName(Request request) {
        String hostName = "";

        if (request != null) {
            HttpUrl httpURL = request.url();
            if (httpURL != null) {
                hostName = httpURL.host();
            }
        }

        return hostName;
    }

    public static String query(Request request, String name) {

        return request != null ? query(request.url(), name) : "";
    }

    public static String query(String url, String name) {

        return Assert.notEmpty(url) ? query(HttpUrl.parse(url), name) : "";
    }

    public static String query(HttpUrl httpURL, String name) {
        String action = "";

        if (httpURL != null && Assert.notEmpty(name)) {
            try {
                action = httpURL.queryParameter(name);
                if (Assert.isEmpty(action)) {
                    action = httpURL.host();
                }
            } catch (Exception e) {
                Log.e(TAG, e);
            }
        }

        return action;
    }

    public static String getTag(HttpUrl httpUrl) {
        String tag = queryTag(httpUrl);

        if (Assert.isEmpty(tag)) {
            tag += System.currentTimeMillis();
        }

        tag = ShortDigest.encrypt(tag);

        return tag;
    }

    private static String queryTag(HttpUrl httpUrl, String... nameArrays) {
        String tag = "";

        if (httpUrl != null && Assert.notEmpty(nameArrays)) {
            StringBuilder tagBuilder = new StringBuilder();

            for (String name : nameArrays) {
                if (Assert.notEmpty(name)) {
                    tagBuilder.append(httpUrl.queryParameter(name));
                }
            }

            tag = tagBuilder.toString();
        }

        return tag;
    }

    @NonNull
    public static MultipartBody.Builder createRequestBody(ArrayMap<String, Object> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        if (map != null) {
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                if (entry != null) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value != null) {
                        if (value instanceof String) {
                            builder.addFormDataPart(key, (String) value);

                        } else if (value instanceof File) {
                            File file = (File) value;
                            if (file.exists()) {
                                builder.addFormDataPart(key, file.getName(), RequestBody.create(MIME.APPLICATION_OCTET_STREAM, file));
                            }
                        } else {
                            builder.addFormDataPart(key, value.toString());
                        }
                    }
                }
            }
        }
        return builder;
    }
}
