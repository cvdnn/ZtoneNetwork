package android.network.pull;

import android.entity.PullEntity;

/**
 * pulled数据过滤器
 *
 * @author handy
 */
public interface PulledFilter {

    boolean accept(PullEntity entity);
}
