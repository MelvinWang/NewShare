package com.melvin.share.Utils;

import android.text.TextUtils;

import com.melvin.share.app.BaseApplication;

import java.util.Map;

import static com.melvin.share.R.id.map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/25
 * <p/>
 * 描述：
 */
public class ShapreUtils {
    public static void putParamCustomerId(Map map) {
        String customerId = getCustomerId();
        if (!TextUtils.isEmpty(customerId)) {
            map.put("customerId", customerId);
        }

    }


    public static void putParamCustomerDotId(Map map) {
        String customerId = getCustomerId();
        if (!TextUtils.isEmpty(customerId)) {
            map.put("customer.id", customerId);
        }

    }

    //获取ID
    public static String getCustomerId() {
        String customerId = BaseApplication.mPre.getString("customerId", null);
//        String customerId = "1";

        return customerId;
    }

    public static void setCustomerId(String customerId) {
        BaseApplication.mPre.edit().putString("customerId", customerId).commit();

    }

    //获取名称
    public static String getUserName() {
        String customerId = BaseApplication.mPre.getString("userName", null);

        return customerId;
    }

    public static void setUserName(String userName) {
        BaseApplication.mPre.edit().putString("userName", userName).commit();

    }

    //获取头像
    public static String getPicture() {
        String customerId = BaseApplication.mPre.getString("picture", null);

        return customerId;
    }

    public static void setPicture(String picture) {
        BaseApplication.mPre.edit().putString("picture", picture).commit();

    }
}
