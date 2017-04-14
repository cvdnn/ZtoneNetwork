package android.network.http;

import android.assist.Assert;
import android.io.FileUtils;
import android.io.OnProgressChangeListener;
import android.json.JSONUtils;
import android.log.Log;

import org.json.JSONObject;

import java.io.File;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by handy on 17-2-3.
 */

public class ResponseTransform {
    private static final String TAG = "ResponseTransform";

    private static final String SUFFIX_APK_FILE = ".apk";
    private static final String SUFFIX_TEMP_FILE = ".tmp";

    /**
     * 获取输入流
     */
    public static ContentStream toStream(Response response) {
        ContentStream contentStream = new ContentStream();

        if (response != null && response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                contentStream.contentLength = body.contentLength();
                contentStream.inputStream = body.byteStream();
            }
        } else if (response != null) {
            HttpUrl httpURL = response.request().url();

            Log.e(TAG, "NN: Request: Host: %s, Action: %s, State: %d", httpURL.host(), HTTPx.query(httpURL, "method"), response.code());
        }

        return contentStream;
    }

    /**
     * 从网络中获取字符串
     */
    public static String toText(Response response) {
        String strResult = "";

        if (response != null && response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                try {
                    strResult = body.string();
                } catch (Exception e) {
                    Log.e(TAG, e);
                }
            }
        } else if (response != null) {
            HttpUrl httpURL = response.request().url();

            Log.e(TAG, "NN: Request: Host: %s, Action: %s, State: %d", httpURL.host(), HTTPx.query(httpURL, "method"), response.code());
        }

        return strResult;
    }

    public static JSONObject toJSON(Response response) {

        return JSONUtils.from(toText(response));
    }

    public static boolean download(Request request, String filePath) {

        return download(request, filePath, null);
    }

    public static boolean download(Request request, String filePath, OnProgressChangeListener listener) {
        boolean result = false;

        if (Assert.notEmpty(filePath)) {
            Response response = HTTPx.Client.execute(request);
            ContentStream contentStream = ResponseTransform.toStream(response);
            if (check(contentStream)) {
                try {
                    String tempPath = filePath + SUFFIX_TEMP_FILE;
                    boolean tempResult = FileUtils.write(contentStream.inputStream, tempPath, contentStream.contentLength, listener);

                    File tempFile = new File(tempPath);
                    if (tempResult && tempFile.exists()) {
                        result = tempFile.renameTo(new File(filePath));
                    }

                    tempFile.delete();
                } catch (Exception e) {
                    Log.e(TAG, e);
                }
            }
        }

        return result;
    }

    public static boolean check(ContentStream contentStream) {

        return contentStream != null && contentStream.inputStream != null;
    }
}
