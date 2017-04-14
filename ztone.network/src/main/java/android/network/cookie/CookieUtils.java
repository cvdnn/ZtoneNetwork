package android.network.cookie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.Request;

import static android.network.http.HTTPx.Headers.COOKIE;

public final class CookieUtils {
    private static final String TAG = "CookieUtils";

    public static final String SEPARATOR_SEMICOLON = "; ";
    public static final String SEPARATOR_EQUAL_SIGN = "=";

    private CookieUtils() {

    }

    public static String toString(List<Cookie> cookies) {
        StringBuilder cookieText = new StringBuilder();

        for (int i = 0, size = cookies.size(); i < size; i++) {
            Cookie cookie = cookies.get(i);
            if (cookie != null) {
                cookieText.append(cookie.name()).append(SEPARATOR_EQUAL_SIGN).append(cookie.value());

                if (i < size - 1) {
                    cookieText.append(SEPARATOR_SEMICOLON);
                }
            }
        }

        return cookieText.toString();
    }

    public static String toString(Cookie cookie) {
        StringBuilder result = new StringBuilder();

        if (cookie != null) {
            appendCookieMeta(result, cookie.name(), cookie.value());
            appendCookieMeta(result, "Domain", cookie.domain());
            appendCookieMeta(result, "Comment", "");
            appendCookieMeta(result, "Expires", cookie.expiresAt());

            appendCookieMeta(result, "Path", cookie.path());
            appendCookieMeta(result, "", cookie.secure() ? "SECURE" : "");
        }

        return result.toString();
    }

    /**
     * 仅仅获得name=value数据
     *
     * @return
     */
    public String toValue(Cookie cookie) {
        StringBuilder result = new StringBuilder();

        if (cookie != null) {
            appendCookieMeta(result, cookie.name(), cookie.value());
        }

        return result.toString();
    }

    /**
     * 设置cookie
     *
     * @param request
     * @param value
     */
    public static Request add(Request request, String value) {
        if (request != null && !TextUtils.isEmpty(value)) {
            request = request.newBuilder().addHeader(COOKIE, value).build();
        }

        return request;
    }

    /**
     * 设置cookie
     *
     * @param builder
     * @param value
     */
    public static void add(Request.Builder builder, String value) {
        if (builder != null && !TextUtils.isEmpty(value)) {
            builder.header(COOKIE, value);
        }
    }

    /**
     * 获取cookie
     *
     * @param url
     * @return
     */
    public static String get(Context context, String url) {
        String cookie = "";
        if (context != null && !TextUtils.isEmpty(url)) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            cookieSyncManager.sync();

            cookie = get(CookieManager.getInstance(), url);
        }

        return cookie;
    }

    /**
     * 获取cookie
     *
     * @param url
     * @return
     */
    public static String get(CookieManager cookieManager, String url) {
        String cookie = "";
        if (!TextUtils.isEmpty(url) && cookieManager != null && cookieManager.hasCookies()) {
            cookie = cookieManager.getCookie(url);
        }

        return cookie;
    }

    /**
     * 设置cookie
     *
     * @param url
     * @param cookieList
     */
    public static void sync(Context context, String url, List<Cookie> cookieList) {
        if (context != null && !TextUtils.isEmpty(url) && cookieList != null) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            cookieSyncManager.sync();

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);

            sync(cookieManager, url, cookieList);

            cookieSyncManager.sync();
        }
    }

    /**
     * 设置cookie
     *
     * @param url
     * @param cookieList
     */
    public static void sync(CookieManager cookieManager, String url, List<Cookie> cookieList) {
        if (!TextUtils.isEmpty(url) && cookieList != null && cookieManager != null) {
            for (Cookie cookie : cookieList) {
                if (cookie != null) {
                    cookieManager.setCookie(url, toString(cookie));
                }
            }
        }
    }

    /**
     * 移除过期cookie
     */
    public static void removeExpiredCookie(Context context) {
        if (context != null) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            cookieSyncManager.sync();

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);

            cookieManager.removeExpiredCookie();

            cookieSyncManager.sync();
        }
    }

    /**
     * 移除Session cookie
     */
    public static void removeSessionCookie(Context context) {
        if (context != null) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            cookieSyncManager.sync();

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);

            cookieManager.removeSessionCookie();

            cookieSyncManager.sync();
        }
    }

    /**
     * 移除全部cookie
     */
    public static void removeAllCookie(Context context) {
        if (context != null) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            cookieSyncManager.sync();

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);

            cookieManager.removeSessionCookie();
            cookieManager.removeAllCookie();

            cookieSyncManager.sync();
        }
    }

    private static <V> void appendCookieMeta(StringBuilder strBuilder, String key, V v) {
        String value = v != null ? v.toString() : "";

        if (!TextUtils.isEmpty(value)) {
            appendSeparatorSemicolon(strBuilder);

            if (!TextUtils.isEmpty(key)) {
                strBuilder.append(key).append(SEPARATOR_EQUAL_SIGN);
            }

            strBuilder.append(value);

            appendSeparatorSemicolon(strBuilder);
        }
    }

    private static void appendSeparatorSemicolon(@NonNull StringBuilder strBuilder) {
        if (strBuilder.length() > 0 && !endsWith(strBuilder, SEPARATOR_SEMICOLON)) {
            strBuilder.append(SEPARATOR_SEMICOLON);
        }
    }

    /**
     * 判断是否以指定字符结尾
     *
     * @param strBuilder
     * @param suffix
     * @return
     */
    public static boolean endsWith(StringBuilder strBuilder, String suffix) {
        boolean result = false;

        if (strBuilder != null && !TextUtils.isEmpty(suffix)) {
            int endIndex = strBuilder.length() - suffix.length();
            result = endIndex >= 0 && strBuilder.lastIndexOf(suffix) == endIndex;
        }

        return result;
    }
}
