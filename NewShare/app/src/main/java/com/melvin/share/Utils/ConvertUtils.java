package com.melvin.share.Utils;


import android.text.TextUtils;

import com.google.zxing.common.StringUtils;

/**
 * 创建日期:2017/4/3 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述: 转换工具
 */
public class ConvertUtils {
    /**
     * 订单状态ID转为状态名
     */

    public static String statusIdToName(String statusID) {
        if (TextUtils.equals("0", statusID)) {
            return "订单取消";
        }else if (TextUtils.equals("1", statusID)) {//订单确认
            return "待付款";
        } else if (TextUtils.equals("2", statusID)) {//订单支付完毕
            return "待发货";
        }else if (TextUtils.equals("3", statusID)) {//已发货，待收货
            return "待收货";
        }else if (TextUtils.equals("4", statusID)) {//收货后待评价
            return "待评价";
        }else if (TextUtils.equals("5", statusID)) {//商品退货
            return "退货";
        }else if (TextUtils.equals("6", statusID)) {//商品退款
            return "退款";
        }else if (TextUtils.equals("7", statusID)) {//评价完成，订单结束
            return "已评价";
        }
        return "";
    }
}
