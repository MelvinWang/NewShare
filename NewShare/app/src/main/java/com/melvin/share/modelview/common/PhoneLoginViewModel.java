package com.melvin.share.modelview.common;

import android.content.Context;
import android.content.Intent;

import com.melvin.share.Utils.Utils;
import com.melvin.share.app.BaseApplication;
import com.melvin.share.model.serverReturn.CommonReturnModel;

import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.modelview.BaseCommonViewModel;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.rx.RxSubscribe;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/24
 * <p>
 * 描述： 手机登录ViewModel
 */
public class PhoneLoginViewModel extends BaseCommonViewModel {


    public PhoneLoginViewModel(Context context) {
        super(context);
    }
    /**
     * 获取验证码
     */
    public void requestData(final Map map) {

    }

    /**
     * 登录
     *
     * @param map
     */
    public void loginByCode(Map map) {

    }

}
