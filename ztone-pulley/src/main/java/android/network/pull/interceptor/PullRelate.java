package android.network.pull.interceptor;

import android.entity.PullEntity;
import android.network.pull.PullFollow;

import okhttp3.Request;

/**
 * Created by handy on 17-3-31.
 */

public interface PullRelate<P extends PullEntity> {
    Request request();

    PullFollow<P> follow();

    P entity();

    P proceed(Request request);
}
