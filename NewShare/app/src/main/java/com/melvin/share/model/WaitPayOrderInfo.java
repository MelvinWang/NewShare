package com.melvin.share.model;

import android.os.Parcel;
import android.os.Parcelable;

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

        public static class OrderItemResponsesBean extends BaseModel implements Parcelable {
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.productId);
                dest.writeString(this.productName);
                dest.writeString(this.mainPicture);
                dest.writeString(this.totalNum);
                dest.writeString(this.total);
                dest.writeString(this.postage);
                dest.writeString(this.scanCode);
                dest.writeString(this.orderItemStatus);
            }

            public OrderItemResponsesBean() {
            }

            protected OrderItemResponsesBean(Parcel in) {
                this.id = in.readString();
                this.productId = in.readString();
                this.productName = in.readString();
                this.mainPicture = in.readString();
                this.totalNum = in.readString();
                this.total = in.readString();
                this.postage = in.readString();
                this.scanCode = in.readString();
                this.orderItemStatus = in.readString();
            }

            public static final Parcelable.Creator<OrderItemResponsesBean> CREATOR = new Parcelable.Creator<OrderItemResponsesBean>() {
                @Override
                public OrderItemResponsesBean createFromParcel(Parcel source) {
                    return new OrderItemResponsesBean(source);
                }

                @Override
                public OrderItemResponsesBean[] newArray(int size) {
                    return new OrderItemResponsesBean[size];
                }
            };
        }
    }

}
