package com.melvin.share.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/31
 * <p>
 * 描述：
 */
public class Product  extends BaseModel implements Parcelable {

    /**
     * picture : /repository/niuzaiku1.png|/repository/niuzaiku2.png|/repository/niuzaiku3.png
     * id : 1
     * shareTimes : 321
     * productName : 牛仔裤
     */

    public String picture;
    public String id;
    public String shareTimes;
    public String productName;


    public String price;
    public String place;

    public String recordId;
    public String productId;

    public boolean isChecked=false;
    public boolean isShow=false;
    //购物车
    public String productNumber;
    public String repertoryId;
    public String repertoryName;

    public String mainProduct;
    public String collectId;
    public String name;
    public String mainPicture;
    public String stockId;
    public String productNum;
    public String postage;
    public String scanCode;
    public boolean scanFlag;//true代表订单码，false代表店铺码

    @Override
    public String toString() {
        return "Product{" +
                "picture='" + picture + '\'' +
                ", id='" + id + '\'' +
                ", shareTimes='" + shareTimes + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", place='" + place + '\'' +
                ", recordId='" + recordId + '\'' +
                ", productId='" + productId + '\'' +
                ", isChecked=" + isChecked +
                ", isShow=" + isShow +
                ", productNumber='" + productNumber + '\'' +
                ", repertoryId='" + repertoryId + '\'' +
                ", repertoryName='" + repertoryName + '\'' +
                ", mainProduct='" + mainProduct + '\'' +
                ", collectId='" + collectId + '\'' +
                ", name='" + name + '\'' +
                ", mainPicture='" + mainPicture + '\'' +
                ", stockId='" + stockId + '\'' +
                ", productNum='" + productNum + '\'' +
                ", postage='" + postage + '\'' +
                ", scanCode='" + scanCode + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picture);
        dest.writeString(this.id);
        dest.writeString(this.shareTimes);
        dest.writeString(this.productName);
        dest.writeString(this.price);
        dest.writeString(this.place);
        dest.writeString(this.recordId);
        dest.writeString(this.productId);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
        dest.writeString(this.productNumber);
        dest.writeString(this.repertoryId);
        dest.writeString(this.repertoryName);
        dest.writeString(this.mainProduct);
        dest.writeString(this.collectId);
        dest.writeString(this.name);
        dest.writeString(this.mainPicture);
        dest.writeString(this.stockId);
        dest.writeString(this.productNum);
        dest.writeString(this.postage);
        dest.writeString(this.scanCode);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.picture = in.readString();
        this.id = in.readString();
        this.shareTimes = in.readString();
        this.productName = in.readString();
        this.price = in.readString();
        this.place = in.readString();
        this.recordId = in.readString();
        this.productId = in.readString();
        this.isChecked = in.readByte() != 0;
        this.isShow = in.readByte() != 0;
        this.productNumber = in.readString();
        this.repertoryId = in.readString();
        this.repertoryName = in.readString();
        this.mainProduct = in.readString();
        this.collectId = in.readString();
        this.name = in.readString();
        this.mainPicture = in.readString();
        this.stockId = in.readString();
        this.productNum = in.readString();
        this.postage = in.readString();
        this.scanCode = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
