package com.melvin.share.model.list;

import com.melvin.share.model.BaseModel;

import java.util.List;

/**
 * 创建日期:2017/3/10 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述:首页分享热度
 */
public class HomeHotProduct extends BaseModel {

    /**
     * id : 1
     * productName : 小米手机
     * picture : /repository/xiaomi.png
     * shareTimes : 300
     * labels : [{"labelName":"好用"},{"labelName":"实惠"}]
     */

    public String id;
    public String productName;
    public String picture;
    public String shareTimes;
    public List<LabelsBean> labels;

    public static class LabelsBean {
        public String labelName;
    }
}
