package com.melvin.share.model;

import java.util.ArrayList;

/**
 * 创建日期:2017/4/1 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述:
 */
public class WaitPayOrderInfo extends BaseModel{

    public OrderBean order;


    public static class OrderBean extends BaseModel{
        /**
         * id : 41
         * orderNumber : 201704011602434092
         * total : 1000.00
         * totalNum : 1
         * message : 1
         * postage : 0.00
         * orderStatus : 1
         * createTime : 1491033763000
         * receiver : 1
         * recevierPhone : 13883387332
         * receiveAddress : 1
         * orderItemResponses : [{"id":19,"productId":1,"productName":"小米手机 : 1G红色","mainPicture":"/repository/xiaomi.png","totalNum":1,"total":"1000.00","postage":"0.00","scanCode":"70748BFCEE6A5622343AE6201DA5C490","orderItemStatus":1}]
         */

        public String id;
        public String orderNumber;
        public String total;
        public String totalNum;
//        public String message;
        public String postage;
        public String orderStatus;
        public String createTime;
        public String receiver;
        public String recevierPhone;
        public String receiveAddress;

        public ArrayList<OrderItemResponsesBean> orderItemResponses=new ArrayList<>();

        public static class OrderItemResponsesBean extends BaseModel{
            /**
             * id : 19
             * productId : 1
             * productName : 小米手机 : 1G红色
             * mainPicture : /repository/xiaomi.png
             * totalNum : 1
             * total : 1000.00
             * postage : 0.00
             * scanCode : 70748BFCEE6A5622343AE6201DA5C490
             * orderItemStatus : 1
             */

            public String id;
            public String productId;
            public String productName;
            public String mainPicture;
            public String totalNum;
            public String total;
            public String postage;
            public String scanCode;
            public String orderItemStatus;
        }
    }

}
