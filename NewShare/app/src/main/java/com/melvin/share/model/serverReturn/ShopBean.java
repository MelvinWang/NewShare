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
    public String logo;
    public String name;


    @Override
    public String toString() {
        return "ShopBean{" +
                "id='" + id + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.logo);
        dest.writeString(this.name);
    }

    public ShopBean() {
    }

    protected ShopBean(Parcel in) {
        this.id = in.readString();
        this.logo = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ShopBean> CREATOR = new Parcelable.Creator<ShopBean>() {
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
