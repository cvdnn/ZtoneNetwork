package com.taobao.api;

import android.util.Log;

import com.taobao.api.internal.cluster.ClusterManager;
import com.taobao.api.internal.cluster.DnsConfig;
import com.taobao.api.internal.util.WebUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class HttpdnsClient {

    private static final AtomicBoolean init = new AtomicBoolean(false);
    private static final String TAG = "ClusterManager";

    public static void init(String appKey, String appSecret) {

        if (init.compareAndSet(false, true)) {
            WebUtils.setIgnoreHostCheck(true);
            ClusterManager.initRefreshThread(appKey, appSecret);
        }
    }

    public static String getUrl(String url) {

        if (!init.get()) {
            Log.e(TAG, "Taobao HttpdnsClient is not initialized...");
            return url;
        }
        DnsConfig dnsConfig = ClusterManager.GetDnsConfigFromCache();
        if (dnsConfig == null) {
            return url;
        } else {
            return dnsConfig.getVipUrl(url);
        }
    }

}
