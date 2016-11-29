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
     * id : 1
     * recever : 易梁泉
     * receverPhone : 15881258593
     * area : 四川省成都市
     * address : 双流县中和镇
     * postalcode : 621114
     * defaultAddress : false
     */

    public String id;
    public String recever;
    public String receverPhone;
    public String area;
    public String address;
    public String postalcode;
    public boolean defaultAddress;
    public String flag="1";//1,代表设置默认地址，2代表删除，3代表修改

    @Override
    public String toString() {
        return "AddressBean{" +
                "id='" + id + '\'' +
                ", recever='" + recever + '\'' +
                ", receverPhone='" + receverPhone + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", defaultAddress=" + defaultAddress +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.recever);
        dest.writeString(this.receverPhone);
        dest.writeString(this.area);
        dest.writeString(this.address);
        dest.writeString(this.postalcode);
        dest.writeByte(this.defaultAddress ? (byte) 1 : (byte) 0);
        dest.writeString(this.flag);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.id = in.readString();
        this.recever = in.readString();
        this.receverPhone = in.readString();
        this.area = in.readString();
        this.address = in.readString();
        this.postalcode = in.readString();
        this.defaultAddress = in.readByte() != 0;
        this.flag = in.readString();
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
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
