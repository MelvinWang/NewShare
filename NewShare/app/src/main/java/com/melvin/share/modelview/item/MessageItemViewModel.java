package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.Utils.DateUtil;
import com.melvin.share.model.MessageInfo;
import com.melvin.share.model.User;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2016/8/7.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：消息通知页面item的ViewModel
 */
public class MessageItemViewModel extends BaseObservable {

    private MessageInfo model;
    private Context context;

    public MessageItemViewModel(Context context, MessageInfo model) {
        this.model = model;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getCreateTime() {
        String dateString = DateUtil.getDateString(model.createTime);
        return dateString;
    }
    public boolean getIsRed() {

        return model.isRead;
    }

    public String getType() {
        if (TextUtils.equals(model.type, "LOGISTICS")) {
            return "物流信息";
        } else if (TextUtils.equals(model.type, "ACTIVITY")) {
            return "活动消息";
        } else if (TextUtils.equals(model.type, "BUSINESS")) {
            return "交易消息";
        } else {
            return "系统消息";
        }
    }

    public String getTitle() {
        return model.title;
    }

    public String getImgUrl() {
//        String[] split = product.mainPicture.split("\\|");
//        if (split != null && split.length >= 1) {
//            String url = GlobalUrl.SERVICE_URL + split[0];
//            return url;
//        }
//        return "";

        return "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    }

    public void setEntity(MessageInfo model) {
        this.model = model;
        notifyChange();
    }
}
