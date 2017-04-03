package com.melvin.share.model.serverReturn;

import android.os.Parcel;
import android.os.Parcelable;

import com.melvin.share.model.BaseModel;

/**
 * Created Time: 2016/8/26.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：
 */
public class AddressBean extends BaseModel implements Parcelable {


    /**
     * "id": 1,
     * "receiver": "易梁泉",
     * "phone": "13888888888",
     * "province": "四川省",
     * "city": "成都",
     * "area": "双流",
     * "detailAddress": "军安卫士花园",
     * "postcode": "621114",
     * "isdefault": false
     */

    public String area;
    public String city;
    public String detailAddress;
    public String id;  //地址的ID
    public String customerId;
    public String addressId;
    public boolean isdefault;
    public String phone;
    public String postcode;
    public String province;
    public String receiver;
    public String flag = "1";//1,代表设置默认地址，2代表删除，3代表修改,4代表订单进入页面修改选择地址


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.area);
        dest.writeString(this.city);
        dest.writeString(this.detailAddress);
        dest.writeString(this.id);
        dest.writeString(this.customerId);
        dest.writeString(this.addressId);
        dest.writeByte(this.isdefault ? (byte) 1 : (byte) 0);
        dest.writeString(this.phone);
        dest.writeString(this.postcode);
        dest.writeString(this.province);
        dest.writeString(this.receiver);
        dest.writeString(this.flag);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.area = in.readString();
        this.city = in.readString();
        this.detailAddress = in.readString();
        this.id = in.readString();
        this.customerId = in.readString();
        this.addressId = in.readString();
        this.isdefault = in.readByte() != 0;
        this.phone = in.readString();
        this.postcode = in.readString();
        this.province = in.readString();
        this.receiver = in.readString();
        this.flag = in.readString();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
