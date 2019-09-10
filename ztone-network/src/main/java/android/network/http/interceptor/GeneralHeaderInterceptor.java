package android.network.http.interceptor;

import android.network.http.HTTPx;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;

import static android.network.http.HTTPx.Headers.CONTENT_ENCODING;
import static android.network.http.HTTPx.Headers.CONTENT_LENGTH;
import static android.network.http.HTTPx.Headers.RANGE;
import static android.network.http.HTTPx.Headers.RANGE4;
import static android.network.http.HTTPx.Headers.TRANSFER_ENCODING;
import static android.network.http.HTTPx.Headers.USER_AGENT;

/**
 * Created by handy on 17-1-5.
 */

public class GeneralHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request finallyRequest = originalRequest.newBuilder()
                .addHeader(USER_AGENT, HTTPx.userAgent())
                .addHeader(RANGE, "bytes=0-")
                .addHeader(RANGE4, "bytes=0-")
                .build();

        Response response = chain.proceed(finallyRequest);

        // 性能差
        if (HttpHeaders.hasBody(response)) {
            Headers headers = response.headers();
            long contentLength = HttpHeaders.contentLength(headers);
            if (contentLength < 0) {
                Response.Builder responseBuilder = response.newBuilder();

                Buffer buffer = new Buffer().readFrom(response.body().byteStream());
                contentLength = buffer.size();

                Headers strippedHeaders = headers.newBuilder()
                        .set(CONTENT_LENGTH, String.valueOf(contentLength))
                        .removeAll(CONTENT_ENCODING)
                        .removeAll(TRANSFER_ENCODING)
                        .build();

                responseBuilder.body(new RealResponseBody(strippedHeaders.toString(), contentLength, buffer));

                response = responseBuilder.build();
            }
        }

        return response;
    }
}
