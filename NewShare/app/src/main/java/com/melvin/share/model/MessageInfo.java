package com.melvin.share.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/22
 * <p>
 * 描述：
 */
public class MessageInfo extends BaseModel implements Parcelable {


    /**
     * createTime : 2017-03-20T06:22:02.980Z
     * id : 0
     * isRead : true
     * title : string
     * type : string
     */

    public String createTime;
    public int id;
    public boolean isRead;
    public String title;
    public String type;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeInt(this.id);
        dest.writeByte(this.isRead ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.type);
    }

    public MessageInfo() {
    }

    protected MessageInfo(Parcel in) {
        this.createTime = in.readString();
        this.id = in.readInt();
        this.isRead = in.readByte() != 0;
        this.title = in.readString();
        this.type = in.readString();
    }

    public static final Creator<MessageInfo> CREATOR = new Creator<MessageInfo>() {
        @Override
        public MessageInfo createFromParcel(Parcel source) {
            return new MessageInfo(source);
        }

        @Override
        public MessageInfo[] newArray(int size) {
            return new MessageInfo[size];
        }
    };
}
