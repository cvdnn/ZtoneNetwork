package android.network;

import android.Android;
import android.Manifest;
import android.annotation.SuppressLint;
import android.assist.Assert;
import android.concurrent.ThreadUtils;
import android.content.Context;
import android.io.FileUtils;
import android.log.Log;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.NetworkInfo.State;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.ConditionVariable;
import android.os.Looper;
import android.support.annotation.RequiresPermission;
import android.support.annotation.WorkerThread;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.Const.CHARSET_ENCODING;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_MOBILE_DUN;
import static android.net.ConnectivityManager.TYPE_MOBILE_HIPRI;
import static android.net.ConnectivityManager.TYPE_MOBILE_MMS;
import static android.net.ConnectivityManager.TYPE_MOBILE_SUPL;
import static android.net.ConnectivityManager.TYPE_WIFI;

/**
 * need android.permission.ACCESS_WIFI_STATE
 *
 * @author handy
 */
@SuppressLint("NewApi")
public final class NetState {
    private static final String TAG = "NetState";

    public interface OnConnectionCheckedListener {

        void onChecked(boolean isConnected);
    }

    /**
     * This is the map described in the Javadoc comment above. The positions of the elements of the array must
     * correspond to the ordinal values of <code>DetailedState</code>.
     */
    private static final EnumMap<DetailedState, State> mWIFIStateMap = new EnumMap<>(DetailedState.class);

    static {
        mWIFIStateMap.put(DetailedState.IDLE, State.DISCONNECTED);
        mWIFIStateMap.put(DetailedState.SCANNING, State.DISCONNECTED);
        mWIFIStateMap.put(DetailedState.CONNECTING, State.CONNECTING);
        mWIFIStateMap.put(DetailedState.AUTHENTICATING, State.CONNECTING);
        mWIFIStateMap.put(DetailedState.OBTAINING_IPADDR, State.CONNECTING);
        mWIFIStateMap.put(DetailedState.CONNECTED, State.CONNECTED);
        mWIFIStateMap.put(DetailedState.SUSPENDED, State.SUSPENDED);
        mWIFIStateMap.put(DetailedState.DISCONNECTING, State.DISCONNECTING);
        mWIFIStateMap.put(DetailedState.DISCONNECTED, State.DISCONNECTED);
        mWIFIStateMap.put(DetailedState.FAILED, State.DISCONNECTED);
        mWIFIStateMap.put(DetailedState.BLOCKED, State.DISCONNECTED);

        if (VERSION.SDK_INT >= Android.JELLY_BEAN) {
            mWIFIStateMap.put(DetailedState.VERIFYING_POOR_LINK, State.CONNECTING);
        }

        if (VERSION.SDK_INT >= Android.JELLY_BEAN_MR1) {
            mWIFIStateMap.put(DetailedState.CAPTIVE_PORTAL_CHECK, State.CONNECTING);
        }
    }

    private static final int TIMEOUT_CONNECT = 1500;

    private static final String DEFAULT_GATEWAY_HOST_NAME = "aliyun.com";
    private static final int DEFAULT_GATEWAY_PORT = 80;

    public static boolean isWIFIEnable(Context appContext) {
        boolean result = false;

        if (appContext != null) {
            WifiManager wifi = (WifiManager) appContext.getApplicationContext().getSystemService(WIFI_SERVICE);
            result = wifi.isWifiEnabled();
        }

        return result;
    }

    public static boolean isConnected(Context appContext) {
        boolean result = false;

        if (appContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            result = networkInfo != null && networkInfo.isConnected();
        }

        return result;
    }

    public static boolean isWIFIConnected(Context appContext) {
        boolean result = false;

        if (appContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(TYPE_WIFI);

            result = networkInfo != null && networkInfo.isConnected();
        }

        return result;
    }

    public static void checkInternetConnected(OnConnectionCheckedListener listener) {
        checkInternetConnected(DEFAULT_GATEWAY_HOST_NAME, DEFAULT_GATEWAY_PORT, listener);
    }

    public static void checkInternetConnected(final String host, final int port, final OnConnectionCheckedListener listener) {
        if (listener != null) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                new AsyncTask<Void, Integer, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {

                        return isInternetConnected(host, port);
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        listener.onChecked(result);
                    }
                }.execute();
            } else {
                listener.onChecked(isInternetConnected());
            }
        }
    }

    /**
     * 是否能连接上互联网
     */
    @WorkerThread
    public static boolean isInternetConnected() {
        return isInternetConnected(DEFAULT_GATEWAY_HOST_NAME, DEFAULT_GATEWAY_PORT);
    }

    /**
     * 是否能连接上互联网
     */
    @WorkerThread
    public static boolean isInternetConnected(String host, int port) {
        final AtomicBoolean result = new AtomicBoolean();
        final ConditionVariable conditionVariable = new ConditionVariable();

        final String hostName = Assert.notEmpty(host) ? host : DEFAULT_GATEWAY_HOST_NAME;
        final int hostPort = port > 0 ? port : DEFAULT_GATEWAY_PORT;
        ThreadUtils.start(new Runnable() {

            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(hostName, hostPort), TIMEOUT_CONNECT);

                    result.set(true);
                } catch (Exception e) {
                    //
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (Exception e) {
                            //
                        }
                    }

                    conditionVariable.open();
                }
            }
        }, "THREAD_CHECK_INTERNET_CONNECT");

        conditionVariable.block(TIMEOUT_CONNECT + 100);

        return result.get();
    }

    /**
     * 获取MAC地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getMacAddress(Context appContext) {
        String macStr = "";

        if (VERSION.SDK_INT >= Android.M) {
            String wifiInterfaceName = System.getProperty("wifi.interface", "wlan0");

            try {
                NetworkInterface netInfo = NetworkInterface.getByName(wifiInterfaceName);
                if (netInfo != null) {
                    macStr = NetUtils.toMAC(netInfo.getHardwareAddress());
                }

                if (Assert.isEmpty(macStr)) {
                    String macPath = new StringBuilder("/sys/class/net/").append(wifiInterfaceName).append("/address").toString();
                    macStr = FileUtils.read(new File(macPath), CHARSET_ENCODING);
                }
            } catch (Exception e) {
                Log.i(TAG, e);
            }
        } else if (appContext != null) {
            WifiManager wifiManager = (WifiManager) appContext.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo.getMacAddress() != null) {
                macStr = wifiInfo.getMacAddress(); // MAC地址
            }
        }

        return macStr;
    }

    /**
     * 判断wifi是否连接成功,不是network
     *
     * @return
     */
    @RequiresPermission(allOf = {"android.permission.ACCESS_NETWORK_STATE"})
    public static State getWIFIState(Context appContext) {
        State wifiState = State.UNKNOWN;

        if (appContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            wifiState = getWIFIState(connectivityManager.getNetworkInfo(TYPE_WIFI));
        }

        return wifiState;
    }

    /**
     * 判断wifi是否连接成功,不是network
     *
     * @return
     */
    public static State getWIFIState(NetworkInfo wifiNetworkInfo) {
        State wifiState = State.UNKNOWN;

        if (wifiNetworkInfo != null) {
            DetailedState detailedState = wifiNetworkInfo.getDetailedState();
            if (detailedState != null) {
                wifiState = mWIFIStateMap.get(detailedState);
            }
        }

        return wifiState;
    }

    public static int getActiveNetworkType(Context appContext) {
        int result = -1;

        if (appContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                result = networkInfo.getType();
            }
        }

        return result;
    }

    public static String getActiveNetworkName(Context appContext) {
        String typeName = "";

        if (appContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                typeName = networkInfo.getTypeName();
            }
        }

        return typeName;
    }

    public static boolean isMobileNetworkType(int networkType) {

        return networkType == TYPE_MOBILE //
                || networkType == TYPE_MOBILE_MMS //
                || networkType == TYPE_MOBILE_SUPL //
                || networkType == TYPE_MOBILE_DUN //
                || networkType == TYPE_MOBILE_HIPRI;
    }

    public static boolean isWIFINetworkType(int networkType) {

        return networkType == TYPE_WIFI;
    }

    public static boolean isWAPConnected(Context appContext) {
        boolean result = false;

        if (appContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(TYPE_MOBILE);

            result = networkInfo != null && networkInfo.isConnected();
        }


        return result;
    }

    public static String getProxyHost(Context appContext) {

        return isWAPConnected(appContext) ? Proxy.getDefaultHost() : null;
    }
}
