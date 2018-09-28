package android.network.pull.interceptor;

import android.entity.PullEntity;

/**
 * Created by handy on 17-1-20.
 */

public interface PullInterceptor {

    <P extends PullEntity> P onIntercept(PullRelate<P> relate);
}
