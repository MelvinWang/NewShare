package com.melvin.share.modelview.common;

import android.content.Context;
import android.content.Intent;

import com.melvin.share.Utils.Utils;
import com.melvin.share.app.BaseApplication;
import com.melvin.share.model.serverReturn.BaseReturnModel;

import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.modelview.BaseCommonViewModel;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.view.RxSubscribe;

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
        mPref = Utils.getShare(BaseApplication.getApplication());
    }

    public void requestData(final Map map) {
        fromNetwork.sendMessage(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<BaseReturnModel>(context) {
                    @Override
                    protected void myNext(BaseReturnModel baseReturnModel) {
                        Utils.showToast(context, baseReturnModel.message);
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });

    }

    /**
     * 登录
     *
     * @param map
     */
    public void loginByCode(Map map) {
        fromNetwork.loginByCode(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<BaseReturnModel<SelfInformation>>(context) {
                    @Override
                    protected void myNext(BaseReturnModel<SelfInformation> baseReturnModel) {
                        Utils.showToast(context, baseReturnModel.message);
                        if (baseReturnModel.success) {
                            mPref.edit().putString("customerId", baseReturnModel.result.customer.id + "").commit();
                            context.startActivity(new Intent(context, MainActivity.class));
                        }
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }

}
