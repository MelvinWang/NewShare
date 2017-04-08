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
public class OnlineStore extends BaseModel implements Parcelable {


    /**
     * id : 1
     * distance : 6.269
     * longitude : 104.08
     * latitude : 30.54
     * experience_name : 小米实体店
     */

    public String id;
    public double distance;
    public double longitude;
    public double latitude;
    public String experience_name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeDouble(this.distance);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.experience_name);
    }

    public OnlineStore() {
    }

    protected OnlineStore(Parcel in) {
        this.id = in.readString();
        this.distance = in.readDouble();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.experience_name = in.readString();
    }

    public static final Creator<OnlineStore> CREATOR = new Creator<OnlineStore>() {
        @Override
        public OnlineStore createFromParcel(Parcel source) {
            return new OnlineStore(source);
        }

        @Override
        public OnlineStore[] newArray(int size) {
            return new OnlineStore[size];
        }
    };
}
