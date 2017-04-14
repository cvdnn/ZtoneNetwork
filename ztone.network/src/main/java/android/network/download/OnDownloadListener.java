package android.network.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.log.Log;

/**
 * Created by handy on 17-2-4.
 */

public abstract class OnDownloadListener extends BroadcastReceiver {

    protected abstract void on(long downloadId);

    @Override
    public final void onReceive(Context context, Intent intent) {
        long now = System.currentTimeMillis();

        if (intent != null) {
            long tempId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (tempId != -1) {
                on(tempId);
            }
        }

        Log.e("TAG", "BB: action: %dms, Receiver: %s", System.currentTimeMillis() - now, intent.getAction());
    }
}
