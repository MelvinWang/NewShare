package com.melvin.share.model.query;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/8
 * <p>
 */
public class OpenShop implements Parcelable {
    //picture 店铺Logo,name 店铺名称,
// idCard 身份证,description 店铺描述,businessLicence 店铺营业执照
    public String picture;
    public String name;
    public String idCard;
    public String description;
    public String businessLicence;
    public String phone;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picture);
        dest.writeString(this.name);
        dest.writeString(this.idCard);
        dest.writeString(this.description);
        dest.writeString(this.businessLicence);
        dest.writeString(this.phone);
    }

    public OpenShop() {
    }

    protected OpenShop(Parcel in) {
        this.picture = in.readString();
        this.name = in.readString();
        this.idCard = in.readString();
        this.description = in.readString();
        this.businessLicence = in.readString();
        this.phone = in.readString();
    }

    public static final Parcelable.Creator<OpenShop> CREATOR = new Parcelable.Creator<OpenShop>() {
        @Override
        public OpenShop createFromParcel(Parcel source) {
            return new OpenShop(source);
        }

        @Override
        public OpenShop[] newArray(int size) {
            return new OpenShop[size];
        }
    };
}
