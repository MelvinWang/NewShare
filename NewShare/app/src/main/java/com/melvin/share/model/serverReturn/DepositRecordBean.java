package com.melvin.share.model.serverReturn;

import android.os.Parcel;
import android.os.Parcelable;

import com.melvin.share.model.BaseModel;

/**
 * Created Time: 2017/7/4.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：提现记录 bean
 */
public class DepositRecordBean extends BaseModel implements Parcelable {

    public String account;
    public String applyDate;
    public String customerId;
    public String money;
    public String phone;
    public String status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeString(this.applyDate);
        dest.writeString(this.customerId);
        dest.writeString(this.money);
        dest.writeString(this.phone);
        dest.writeString(this.status);
    }

    public DepositRecordBean() {
    }

    protected DepositRecordBean(Parcel in) {
        this.account = in.readString();
        this.applyDate = in.readString();
        this.customerId = in.readString();
        this.money = in.readString();
        this.phone = in.readString();
        this.status = in.readString();
    }

    public static final Creator<DepositRecordBean> CREATOR = new Creator<DepositRecordBean>() {
        @Override
        public DepositRecordBean createFromParcel(Parcel source) {
            return new DepositRecordBean(source);
        }

        @Override
        public DepositRecordBean[] newArray(int size) {
            return new DepositRecordBean[size];
        }
    };
}
