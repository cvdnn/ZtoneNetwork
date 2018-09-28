package android.network.pull.interceptor;

import android.entity.PullEntity;
import android.framework.entity.PullEntity;
import android.framework.pull.PulledFilter;
import android.network.pull.PulledFilter;

/**
 * Created by handy on 17-1-25.
 */

public class PulledFilterInterceptor implements PullInterceptor {

    private final PulledFilter mPulledFilter;

    public PulledFilterInterceptor(PulledFilter pulledFilter) {
        this.mPulledFilter = pulledFilter;
    }

    @Override
    public <P extends PullEntity> P onIntercept(PullRelate<P> relate)  {
        P p = relate.proceed(relate.request());

        if (mPulledFilter != null) {
            mPulledFilter.accept(p);
        }

        return p;
    }
}
