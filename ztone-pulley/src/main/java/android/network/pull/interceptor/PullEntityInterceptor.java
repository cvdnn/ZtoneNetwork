package android.network.pull.interceptor;

import android.entity.PullEntity;

/**
 * Created by handy on 17-1-22.
 */

final class PullEntityInterceptor implements PullInterceptor {

    @Override
    public <P extends PullEntity> P onIntercept(PullRelate<P> relate)  {
        P p = relate.entity();

        if (relate.entity() != null) {
            p = relate.proceed(relate.request());

        } else {
            new RuntimeException("PullEntity is null!");
        }

        return p;
    }
}
