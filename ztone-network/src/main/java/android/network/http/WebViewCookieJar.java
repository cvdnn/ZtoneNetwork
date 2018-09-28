package android.network.http;

/**
 * Created by Blue on 16/7/31.
 */

import android.Android;
import android.assist.Assert;
import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Provides a synchronization point between the webview cookie store and OkHttpClient cookie store
 */
public final class WebViewCookieJar implements CookieJar {

    public WebViewCookieJar(Context context) {
        final CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
        cookieSyncManager.sync();

        CookieManager.getInstance().setAcceptCookie(true);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        List<Cookie> cookieList = null;

        CookieSyncManager.getInstance().sync();

        String cookieValue = CookieManager.getInstance().getCookie(httpUrl.toString());
        if (Assert.notEmpty(cookieValue)) {
            cookieList = new ArrayList<>();
            cookieList.add(Cookie.parse(httpUrl, cookieValue));
        } else {
            cookieList = new ArrayList<>(0);
        }

        return cookieList;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (url != null) {
            CookieManager cookieManager = CookieManager.getInstance();

            String strURl = url.toString();
            if (Assert.notEmpty(cookies)) {
                for (Cookie cookie : cookies) {
                    if (cookie != null) {
                        cookieManager.setCookie(strURl, cookie.toString());
                    }
                }
            } else {
                cookieManager.setCookie(strURl, "");
            }

            if (Build.VERSION.SDK_INT >= Android.LOLLIPOP) {
                cookieManager.flush();

            } else {
                CookieSyncManager.getInstance().sync();
            }
        }
    }
}
