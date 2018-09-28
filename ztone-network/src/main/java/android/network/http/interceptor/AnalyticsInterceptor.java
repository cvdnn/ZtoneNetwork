package android.network.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AnalyticsInterceptor implements Interceptor {
    private static final String TAG = "AnalyticsInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

//        final String url = HTTPUtils.getURL(request), action = HTTPUtils.getAction(request);
//        final ArrayMap<String, String> extraInfoMap = HTTPUtils.createActionMap(action);
//        final NetworkAnalytics networkAnalytics = AnalyticsUtils.createNetworkAnalytics(HTTPUtils.getHostName(request), request.method());

        // 开始网络请求监听
//        if (networkAnalytics != null) {
//            networkAnalytics.start(action);
//        }

        long t1 = System.nanoTime();

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
//        Log.d(TAG, "NN: Received: action '%s' in %.1fms", HTTPUtils.getAction(response.request()), (t2 - t1) / 1e6d);

        // 记录数据返回时间
//        if (networkAnalytics != null) {
//            networkAnalytics.connected();
//
//            if (response != null) {
//                // 记录接收数据
//                networkAnalytics.response();
//                networkAnalytics.status(response.code(), url, extraInfoMap);
//
//                // 记录接收的数据量
//                networkAnalytics.setContentLenght(response.body().contentLength());
//
//                // 记录接收数据
//                networkAnalytics.response();
//
//                // 数据接收处理结束
//                networkAnalytics.putExtraInfo(extraInfoMap);
//                networkAnalytics.finish();
//            } else if (networkAnalytics != null) {
//                networkAnalytics.error(new InternalErrorException(), url, extraInfoMap);
//            }
//        }

        return response;
    }
}