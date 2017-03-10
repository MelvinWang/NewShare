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
public class ShopBean extends BaseModel implements Parcelable {

    public String id;
    public String userId;
    public String logo;
    public String name;
    public String picture;
    public boolean isChecked=false;
    public boolean isShow=false;
    public boolean collected=false;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.logo);
        dest.writeString(this.name);
        dest.writeString(this.picture);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
        dest.writeByte(this.collected ? (byte) 1 : (byte) 0);
    }

    public ShopBean() {
    }

    protected ShopBean(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.logo = in.readString();
        this.name = in.readString();
        this.picture = in.readString();
        this.isChecked = in.readByte() != 0;
        this.isShow = in.readByte() != 0;
        this.collected = in.readByte() != 0;
    }

    public static final Creator<ShopBean> CREATOR = new Creator<ShopBean>() {
        @Override
        public ShopBean createFromParcel(Parcel source) {
            return new ShopBean(source);
        }

        @Override
        public ShopBean[] newArray(int size) {
            return new ShopBean[size];
        }
    };
}
