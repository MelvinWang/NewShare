package com.melvin.share.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.melvin.share.Utils.Utils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/17
 * <p/>
 * 描述：初始App
 */
public class BaseApplication extends Application {
    {
//        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx472378ccc79bb5c5", "61aa6eae42a5a166f78e8fa3f4456de8");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setQQZone("1106081788", "50VFVIoculvhthJz");
    }
    private static BaseApplication application;
    public static SharedPreferences mPre;
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        application = this;
        mPre = Utils.getShare(application);
//        if (!isNetworkAvailable(application)) {
//            Toast.makeText(application, "当前无可用网络，请检查是否连接网络!!!", Toast.LENGTH_LONG).show();
//        }
    }

    public static Context getApplication() {
        return application;
    }

    /**
     * 检查当前网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
