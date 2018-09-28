/**
 *
 */
package android.network.wifi;

import android.Manifest;
import android.assist.Assert;
import android.content.Context;
import android.log.Log;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.reflect.ClazzLoader;
import android.support.annotation.RequiresPermission;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

/**
 * @author Linzh
 */
public class WifiAdmin {
    private static final String TAG = "WifiAdmin";

    public static final String WIFI_INFO = "wifi_info";

    public final static int ENCRYPT_NONE = 1;
    public final static int ENCRYPT_WEP = 2;
    public final static int ENCRYPT_WPA = 3;

    private static WifiAdmin wifiAdmin = null;
    private List<WifiConfiguration> mWifiConfiguration; // 无线网络配置信息类集合(网络连接列表)
    private List<ScanResult> mWifiList; // 检测到接入点信息类集合
    private WifiInfo mWifiInfo;// 描述Wifi连接状态信息

    private WifiManager.WifiLock mWifilock; // 能够阻止wifi进入睡眠状态，使wifi一直处于活跃状态

    public WifiManager mWifiManager;

    /**
     * 获取实例
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static WifiAdmin getInstance(Context context) {
        if (wifiAdmin == null) {
            wifiAdmin = new WifiAdmin(context);
        }

        return wifiAdmin;
    }

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    private WifiAdmin(Context context) {
        if (context != null) {
            // 获取系统Wifi服务 WIFI_SERVICE
            mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            if (mWifiManager != null) {
                // 获取连接信息
                mWifiInfo = mWifiManager.getConnectionInfo();
            }
        }
    }

    /**
     * 判断是否存在指定热点信息
     *
     * @param ssid 热点名称
     * @return
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public WifiConfiguration isExist(String ssid) {
        if (mWifiManager != null && mWifiManager.getConfiguredNetworks() != null) {
            Iterator<WifiConfiguration> localIterator = mWifiManager.getConfiguredNetworks().iterator();
            WifiConfiguration localWifiConfiguration;
            do {
                if (!localIterator.hasNext()) {
                    return null;
                }
                localWifiConfiguration = localIterator.next();
            } while (!localWifiConfiguration.SSID.equals("\"" + ssid + "\""));

            return localWifiConfiguration;
        }

        return null;
    }

    /**
     * 打开Wifi
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean openWifi() {
        if (mWifiManager != null && !mWifiManager.isWifiEnabled()) {
            return mWifiManager.setWifiEnabled(true);
        }

        return true;
    }

    /**
     * 关闭Wifi
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean closeWifi() {
        if (mWifiManager != null && mWifiManager.isWifiEnabled()) {
            return mWifiManager.setWifiEnabled(false);
        }
        return true;
    }

    /**
     * 连接指定networkId的网络
     *
     * @param netId
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean connectWifi(int netId) {
        return mWifiManager != null && mWifiManager.enableNetwork(netId, true);
    }

    /**
     * 连接指定网络
     *
     * @param wifiConfiguration
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean connectWifi(WifiConfiguration wifiConfiguration) {
        boolean result = false;

        if (wifiConfiguration != null && mWifiManager != null) {
            result = mWifiManager.enableNetwork(wifiConfiguration.networkId, true);
        }

        return result;
    }

    /**
     * 断开指定networkId的网络
     *
     * @param netId
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean disconnectWifi(int netId) {

        return mWifiManager != null && mWifiManager.disableNetwork(netId);
    }

    /**
     * 断开指定网络
     *
     * @param wifiConfiguration
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean disconnectWifi(WifiConfiguration wifiConfiguration) {
        boolean result = false;

        if (wifiConfiguration != null && mWifiManager != null) {
            result = mWifiManager.disableNetwork(wifiConfiguration.networkId);
        }

        return result;
    }

    /**
     * 添加指定网络
     *
     * @param wifiConfiguration
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean addNetwork(WifiConfiguration wifiConfiguration) {
        boolean result = false;

        if (wifiConfiguration != null && mWifiManager != null) {
            result = mWifiManager.enableNetwork(mWifiManager.addNetwork(wifiConfiguration), true);
        }

        return result;
    }

    /**
     * 锁定WifiLock，当下载大文件时需要锁定
     */
    public void acquireWifiLock() {
        if (mWifilock != null) {
            mWifilock.acquire();
        }
    }

    /**
     * 创建一个WifiLock
     *
     * @param tag
     */
    public void createWifiLock(String tag) {
        if (mWifiManager != null) {
            if (mWifilock == null) {
                mWifilock = mWifiManager.createWifiLock(tag);
            }
        }
    }

    /**
     * 解锁WifiLock
     */
    public void releaseWifilock() {
        if (mWifilock != null && mWifilock.isHeld()) {
            mWifilock.release();
        }
    }

    /**
     * 获取Wifi配置信息
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public List<WifiConfiguration> getWifiConfigurations() {
        if (mWifiManager != null) {
            mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        }

        return mWifiConfiguration;
    }

    /**
     * 获取BSSID
     *
     * @return
     */
    public String getBSSID() {
        return mWifiInfo == null ? null : mWifiInfo.getBSSID();
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public int getIPAddress() {
        return mWifiInfo == null ? 0 : mWifiInfo.getIpAddress();
    }

    /**
     * 获取Mac地址
     *
     * @return
     */
    public String getMacAddress() {
        return mWifiInfo == null ? "NULL" : mWifiInfo.getMacAddress();
    }

    /**
     * 获取网络id
     *
     * @return
     */
    public int getNetworkId() {
        return mWifiInfo == null ? 0 : mWifiInfo.getNetworkId();
    }

    /**
     * 获取热点创建状态
     *
     * @return
     */
    public int getWifiApState() {
        try {
            if (mWifiManager != null) {
                return ((Integer) mWifiManager.getClass().getMethod("getWifiApState", new Class[0])
                        .invoke(this.mWifiManager)).intValue();
            }

        } catch (Exception localException) {
        }

        return 4; // 未知wifi网卡状态
    }

    /**
     * 获取wifi连接信息
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public WifiInfo getWifiInfo() {
        if (mWifiManager != null) {
            mWifiInfo = mWifiManager.getConnectionInfo();
        }

        return mWifiInfo;
    }

    /**
     * 扫描Wifi
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean startScan() {
        boolean result = false;

        if (mWifiManager != null) {
            result = mWifiManager.startScan();
        }

        return result;
    }

    /**
     * 获取Wifi扫描列表
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public List<ScanResult> getWifiList() {
        if (mWifiManager != null) {
            mWifiList = mWifiManager.getScanResults();
        }

        return mWifiList;
    }

    /**
     * 查看Wifi扫描结果
     *
     * @return
     */
    public StringBuilder lookUpScan() {
        StringBuilder localStringBuilder = new StringBuilder();
        if (Assert.notEmpty(mWifiList)) {
            for (int i = 0, size = mWifiList.size(); i < size; i++) {
                localStringBuilder.append("Index_");
                localStringBuilder.append(i + 1);
                localStringBuilder.append(":");
                // 将ScanResult信息转换成一个字符串包
                // 其中把包括：BSSID、SSID、capabilities、frequency、level
                localStringBuilder.append((mWifiList.get(i)).toString());
                localStringBuilder.append("\n");
            }
        }

        return localStringBuilder;
    }

    /**
     * 根据wifi配置信息创建或关闭一个热点
     *
     * @param wifiConfiguration
     * @param wifiState
     */
    public void createWifiAP(WifiConfiguration wifiConfiguration, boolean wifiState) {
        if (mWifiManager != null) {
            try {
                // Class<?>[] arrayOfClass = new Class[2];
                // arrayOfClass[0] = WifiConfiguration.class;
                // arrayOfClass[1] = Boolean.TYPE;
                //
                // Class<?> localClass = mWifiManager.getClass();
                // Method localMethod = localClass.getMethod("setWifiApEnabled", arrayOfClass);
                //
                // WifiManager localWifiManager = mWifiManager;
                //
                // Object[] arrayOfObject = new Object[2];
                // arrayOfObject[0] = wifiConfiguration;
                // arrayOfObject[1] = Boolean.valueOf(wifiState);
                // localMethod.invoke(localWifiManager, arrayOfObject);

                ClazzLoader.invoke(mWifiManager, "setWifiApEnabled", wifiConfiguration, wifiState);

                return;
            } catch (Exception e) {
                Log.e(TAG, e);
            }
        }
    }

    /**
     * 创建一个wifi配置信息
     *
     * @param ssid
     * @param pwd
     * @param encrypt     1是无密码，2是简单密码，3是wap加密
     * @param networkType wifi/ap
     * @return
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE})
    public WifiConfiguration createWifiConfiguration(String bssid, String ssid, String pwd, int encrypt, String networkType) {
        // 配置网络信息类
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        // 设置配置网络属性
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();

        if (networkType.equals("wt")) { // wifi模式
            wifiConfiguration.SSID = ("\"" + ssid + "\"");
            wifiConfiguration.BSSID = bssid;
            WifiConfiguration localWifiConfiguration = isExist(ssid);
            if (localWifiConfiguration != null) {
                mWifiManager.removeNetwork(localWifiConfiguration.networkId); // 从列表中删除指定的网络配置网络
            }
            switch (encrypt) {
                case ENCRYPT_NONE: { // 没有密码
                    wifiConfiguration.wepKeys[0] = "";
                    wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    wifiConfiguration.wepTxKeyIndex = 0;
                    break;
                }
                case ENCRYPT_WEP: { // 简单密码
                    wifiConfiguration.hiddenSSID = true;
                    wifiConfiguration.wepKeys[0] = ("\"" + pwd + "\"");
                    break;
                }
                case ENCRYPT_WPA: { // wap加密
                    wifiConfiguration.preSharedKey = ("\"" + pwd + "\"");
                    wifiConfiguration.hiddenSSID = true;
                    wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                    wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                    wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                    wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                    break;
                }
            }
        } else {// ap模式
            wifiConfiguration.SSID = ssid;
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;

            switch (encrypt) {
                case ENCRYPT_NONE: {// 没有密码
                    wifiConfiguration.wepKeys[0] = "";
                    wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    wifiConfiguration.wepTxKeyIndex = 0;
                    break;
                }
                case ENCRYPT_WEP: {// 简单密码
                    wifiConfiguration.hiddenSSID = true;// 网络上不广播ssid
                    wifiConfiguration.wepKeys[0] = pwd;
                    break;
                }
                case ENCRYPT_WPA: { // wap加密
                    wifiConfiguration.preSharedKey = pwd;
                    wifiConfiguration.allowedAuthAlgorithms.set(0);
                    wifiConfiguration.allowedProtocols.set(1);
                    wifiConfiguration.allowedProtocols.set(0);
                    wifiConfiguration.allowedKeyManagement.set(1);
                    wifiConfiguration.allowedPairwiseCiphers.set(2);
                    wifiConfiguration.allowedPairwiseCiphers.set(1);
                    break;
                }
            }
        }
        return wifiConfiguration;
    }

    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public void saveWifiConfiguration(WifiConfiguration wifiConfiguration) {
        if (mWifiManager != null) {
            mWifiManager.saveConfiguration();
        }
    }

}
