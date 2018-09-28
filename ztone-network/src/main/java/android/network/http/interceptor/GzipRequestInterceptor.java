package android.network.http.interceptor;

import android.log.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

import static android.network.http.HTTPx.Headers.CONTENT_ENCODING;
import static android.network.http.HTTPx.Headers.GZIP;

/**
 * Created by handy on 17-1-5.
 */

public class GzipRequestInterceptor implements Interceptor {
    private static final String TAG = "GzipRequestInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request finallyRequest = null;
        if (originalRequest.body() != null && originalRequest.header(CONTENT_ENCODING) != null) {
            finallyRequest = originalRequest.newBuilder()
                    .header(CONTENT_ENCODING, GZIP)
                    .method(originalRequest.method(), forceContentLength(gzip(originalRequest.body())))
                    .build();
        } else {
            finallyRequest = originalRequest;
        }

        return chain.proceed(finallyRequest);
    }

    /**
     * https://github.com/square/okhttp/issues/350
     */
    private RequestBody forceContentLength(final RequestBody requestBody) throws IOException {
        final Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);

        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return requestBody.contentType();
            }

            @Override
            public long contentLength() {
                return buffer.size();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(buffer.snapshot());
            }
        };
    }

    private RequestBody gzip(final RequestBody body) {

        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                return -1; // We don't know the compressed length in advance!
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                try {
                    body.writeTo(gzipSink);
                } catch (Exception e) {
                    Log.e(TAG, e);
                } finally {
                    gzipSink.close();
                }
            }
        };
    }
}
