package android.network;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.log.Log;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import static android.content.Context.WIFI_SERVICE;

public class WIFIStateReceiver extends BroadcastReceiver {
    private final byte[] TOKEN_HANDLE = {};
    private final Handler mHandler = new Handler();
    private boolean mNotFirstReceived;

    @Override
    public final void onReceive(final Context context, final Intent intent) {
        long now = System.currentTimeMillis();

        if (mNotFirstReceived) {
            if (NetState.isWIFINetworkType(NetState.getActiveNetworkType(context))) {
                mHandler.removeCallbacksAndMessages(TOKEN_HANDLE);
                mHandler.postAtTime(new Runnable() {

                    @Override
                    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
                    public void run() {
                        WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
                        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
                        if (wifiInfo != null) {
                            onStateChanged(context, intent, wifiInfo, NetState.getWIFIState(context));
                        }
                    }
                }, TOKEN_HANDLE, SystemClock.uptimeMillis() + 1500);
            }
        } else {
            mNotFirstReceived = true;
        }

        Log.e("TAG", "BB: action: %dms, Receiver: %s", System.currentTimeMillis() - now, intent.getAction());
    }

    public void onStateChanged(@NonNull final Context context, @NonNull Intent intent, @NonNull WifiInfo wifiInfo,
                               @NonNull NetworkInfo.State wifiState) {

    }
}