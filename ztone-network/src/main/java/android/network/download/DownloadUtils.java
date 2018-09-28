package android.network.download;

import android.app.DownloadManager;
import android.assist.Assert;
import android.content.Context;
import android.database.Cursor;
import android.log.Log;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import static android.app.DownloadManager.Request.NETWORK_MOBILE;
import static android.app.DownloadManager.Request.NETWORK_WIFI;
import static android.app.DownloadManager.Request.VISIBILITY_VISIBLE;
import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by handy on 17-2-4.
 */

public class DownloadUtils {
    private static final String TAG = "DownloadUtils";

    public static DownloadManager.Request newDownloadRequest(String url) {
        DownloadManager.Request downloadRequest = null;

        if (Assert.notEmpty(url)) {
            downloadRequest = new DownloadManager.Request(Uri.parse(url));
            downloadRequest.setAllowedNetworkTypes(NETWORK_MOBILE | NETWORK_WIFI);
            downloadRequest.setAllowedOverRoaming(false);

            //设置文件类型
            downloadRequest.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url)));

            //在通知栏中显示
            downloadRequest.setNotificationVisibility(VISIBILITY_VISIBLE);
            downloadRequest.setVisibleInDownloadsUi(true);
        }

        return downloadRequest;
    }

    public static int queryDownloadStatus(Context context, long downloadId) {
        int status = -1;

        if (downloadId != 0 && context != null) {
            DownloadManager mDownloadManager = (DownloadManager) context.getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
            if (mDownloadManager != null) {
                Cursor cursor = null;

                try {
                    cursor = mDownloadManager.query(new DownloadManager.Query().setFilterById(downloadId));
                    if (cursor.moveToFirst()) {
                        status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    }
                } catch (Exception e) {
                    Log.i(TAG, e);

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }

        return status;
    }
}
