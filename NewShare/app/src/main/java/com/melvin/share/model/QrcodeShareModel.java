package com.melvin.share.model;

/**
 * 创建日期:2017/4/8 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述:
 */
public class QrcodeShareModel {

    public String code;
    //1代表微信  2代表朋友圈  3代表QQ ，4代表空间
    public int flagCode ;

    @Override
    public String toString() {
        return "QrcodeShareModel{" +
                "code='" + code + '\'' +
                ", flagCode=" + flagCode +
                '}';
    }
}
