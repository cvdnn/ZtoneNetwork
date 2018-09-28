package android.network;

import android.assist.Assert;
import android.content.Context;
import android.log.Log;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.alibaba.sdk.android.httpdns.DegradationFilter;
import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import okhttp3.Dns;
import okhttp3.OkHttpClient;

public class DNSUtils {
    private static final String TAG = "DNSUtils";

    private static HttpDnsService mHttpDnsService;
    private static final ArrayMap<String, DNSMeta> mDNSMetaMap = new ArrayMap<String, DNSMeta>(64);

    /**
     * 阿里云okdns
     */
    private static final Dns ALiYunOKDNS = new Dns() {

        @Override
        public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            List<InetAddress> addressArray = null;

            String lookupName = lookupHost(hostname);
            if (Assert.notEmpty(lookupName)) {
                addressArray = Arrays.asList(InetAddress.getByName(lookupName));
            } else {
                addressArray = Arrays.asList(InetAddress.getAllByName(hostname));
            }

            return addressArray;
        }
    };

    public static void onInit(@NonNull final Context context, @NonNull String accountID, String... hostList) {
        if (mHttpDnsService == null && Assert.notEmpty(accountID)) {
            mHttpDnsService = HttpDns.getService(context, accountID);
            mHttpDnsService.setDegradationFilter(new DegradationFilter() {

                @Override
                public boolean shouldDegradeHttpDNS(String hostName) {

                    return NetUtils.isNetwoekByProxy(context);
                }
            });

            if (Assert.notEmpty(hostList)) {
                mHttpDnsService.setPreResolveHosts(new ArrayList<String>(Arrays.asList(hostList)));
            }
        }
    }

    public static void binding(OkHttpClient.Builder clientBuilder) {
        if (clientBuilder != null) {
            clientBuilder.dns(ALiYunOKDNS);
        }
    }

    public static void setLogEnabled(boolean enable) {
        if (mHttpDnsService != null) {
            mHttpDnsService.setLogEnabled(enable);
        }
    }

    public static void setPreResolveHosts(String... hostList) {
        if (mHttpDnsService != null) {
            mHttpDnsService.setPreResolveHosts(new ArrayList<String>(Arrays.asList(hostList)));
        }
    }

    public static String[] lookupHostArrays(String... hosts) {
        if (Assert.notEmpty(hosts)) {
            for (int i = 0; i < hosts.length; i++) {
                hosts[i] = lookupHost(hosts[i]);
            }
        }

        return hosts;
    }

    /**
     * 同步解析DNS,不可在主线程中解析
     *
     * @param host
     * @return
     */
    @NonNull
    public static String lookupHost(String host) {
        String ip = host;

        if (!NetUtils.isAnIP(host) && NetUtils.isAHost(host) && mHttpDnsService != null && mDNSMetaMap != null) {
            DNSMeta dnsMeta = mDNSMetaMap.get(host);
            if (dnsMeta == null) {
                synchronized (DNSUtils.class) {
                    if (dnsMeta == null) {
                        dnsMeta = new DNSMeta(host);

                        mDNSMetaMap.put(host, dnsMeta);
                    }
                }
            }

            if (dnsMeta.isExpired() && NetState.isInternetConnected()) {
                // alibaba httpdns, 暂不本地解析,由于部分域名解析后无法访问
                try {
                    ip = mHttpDnsService.getIpByHostAsync(host);
                } catch (Exception e) {
                    Log.d(TAG, e);
                }

                dnsMeta.ip = ip;
                dnsMeta.queryTime = System.currentTimeMillis();

                Log.d(TAG, "NN: DNS: %s = %s", host, ip);
            } else {
                ip = dnsMeta.ip;
            }
        }

        if (Assert.isEmpty(ip)) {
            ip = "";
        }

        return ip;
    }

    /**
     * 同步解析DNS,不可在主线程中解析
     *
     * @param ip
     * @return
     */
    @NonNull
    public static boolean lookupIp(String ip) {
        boolean result = false;

        if (NetUtils.isAnIP(ip) && mHttpDnsService != null && mDNSMetaMap != null) {
            Collection<DNSMeta> dnsMetas = mDNSMetaMap.values();
            if (Assert.notEmpty(dnsMetas)) {
                for (DNSMeta meta : dnsMetas) {
                    if (meta != null && ip.equals(meta.ip)) {
                        result = true;

                        break;
                    }
                }
            }
        }

        return result;
    }

    public static String[] lookupArrays(String... urlArrays) {
        if (Assert.notEmpty(urlArrays)) {
            for (int i = 0; i < urlArrays.length; i++) {
                urlArrays[i] = lookup(urlArrays[i]);
            }
        }

        return urlArrays;
    }

    public static String lookup(String url) {

        return lookup(URI.create(url));
    }

    public static String lookup(URI uri) {
        String url = "";

        if (uri != null) {
            url = uri.toString();

            String host = uri.getHost(), ip = lookupHost(host);
            if (Assert.notEmpty(ip)) {
                url = url.replaceFirst(host, ip);
            }
        }

        return url;
    }

    public static void setExpiredIPEnabled(boolean enable) {
        if (mHttpDnsService != null) {
            mHttpDnsService.setExpiredIPEnabled(enable);
        }
    }

    public static void setDegradationFilter(DegradationFilter degradationFilter) {
        if (mHttpDnsService != null) {
            mHttpDnsService.setDegradationFilter(degradationFilter);
        }
    }
}
