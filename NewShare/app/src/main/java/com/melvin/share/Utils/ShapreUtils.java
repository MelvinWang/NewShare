package com.melvin.share.Utils;

import android.text.TextUtils;

import com.melvin.share.app.BaseApplication;

import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/25
 * <p/>
 * 描述：判断用户是否登录
 */
public class ShapreUtils {
    public static void putParamCustomerId(Map map) {
        String customerId = BaseApplication.mPre.getString("customerId", null);
        if (!TextUtils.isEmpty(customerId)) {
            map.put("customerId", customerId);
        }

    }


    public static void putParamCustomerDotId(Map map) {
        String customerId = BaseApplication.mPre.getString("customerId", null);
        if (!TextUtils.isEmpty(customerId)) {
            map.put("customer.id", customerId);
        }

    }
}
