package android.network;

import android.assist.Assert;
import android.content.Context;
import android.math.Maths;
import android.os.Build;
import android.text.TextLink;
import android.webkit.URLUtil;

import java.util.regex.Pattern;

import okhttp3.HttpUrl;

public class NetUtils {

    private static final String rex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    private static final Pattern pattern = //
            Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

    public static String toMAC(byte[] addrArrays) {
        String mac = "";

        if (Assert.notEmpty(addrArrays)) {
            TextLink addrLinker = TextLink.create(":");

            for (int i = 0; i < addrArrays.length; i++) {
                addrLinker.append(Maths.toHex(addrArrays[i]));
            }

            mac = addrLinker.toString();
        }

        return mac;
    }

    public static String toIP(byte[] addrArrays) {
        String ip = "";

        if (Assert.notEmpty(addrArrays)) {
            TextLink addrLinker = TextLink.create(".");

            for (int i = 0; i < addrArrays.length; i++) {
                addrLinker.append(String.valueOf((addrArrays[i] < 0) ? 256 + addrArrays[i] : addrArrays[i]));
            }

            ip = addrLinker.toString();
        }

        return ip;
    }

    public static boolean isAHost(String host) {
        boolean result = false;

        if (Assert.notEmpty(host)) {
            char[] bytes = host.toCharArray();
            if ((bytes.length > 0) && (bytes.length <= 255)) {
                result = true;

                for (char aByte : bytes) {
                    if (((aByte < 'A') || (aByte > 'Z')) && ((aByte < 'a') || (aByte > 'z'))
                            && ((aByte < '0') || (aByte > '9')) && (aByte != '.') && (aByte != '-')) {

                        result = false;
                        break;
                    }
                }
            }
        }

        return result;
    }

    public static boolean isAnIP(String ip) {

        return (ip != null) && (ip.length() >= 7) && (ip.length() <= 15) && (!"".equals(ip))
                && (pattern.matcher(ip).matches());
    }

    /**
     * 检测系统是否已经设置代理
     *
     * @param context
     *
     * @return
     */
    public static boolean isNetwoekByProxy(Context context) {
        String proxyHost = "";
        int proxyPort = -1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            proxyHost = System.getProperty("http.proxyHost");
            String port = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt(port != null ? port : "-1");

        } else {
            proxyHost = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }

        return proxyHost != null && proxyPort != -1;
    }

    public static String setKVToURL(String url, String key, String value) {
        if (URLUtil.isNetworkUrl(url) && Assert.notEmpty(key)) {
            if (Assert.notEmpty(value)) {
                url = HttpUrl.parse(url).newBuilder().setQueryParameter(key, value).build().toString();
            } else {
                url = HttpUrl.parse(url).newBuilder().removeAllQueryParameters(key).build().toString();
            }
        }

        return url;
    }

    public static String addKVToURL(String url, String key, String value) {
        if (URLUtil.isNetworkUrl(url) && Assert.notEmpty(key) && Assert.notEmpty(value)) {
            url = HttpUrl.parse(url).newBuilder().addQueryParameter(key, value).build().toString();
        }

        return url;
    }

    public static boolean hasKey(String url, String key) {

        return URLUtil.isNetworkUrl(url) && Assert.notEmpty(key) && //
                Assert.notEmpty(HttpUrl.parse(url).queryParameter(key));
    }
}
