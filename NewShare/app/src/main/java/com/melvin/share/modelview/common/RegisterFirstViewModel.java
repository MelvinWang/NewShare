package com.melvin.share.modelview.common;

import android.content.Context;

import com.melvin.share.Utils.Utils;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.modelview.BaseCommonViewModel;
import com.melvin.share.view.RxSubscribe;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/24
 * <p/>
 * 描述： 登录页面ViewModel
 */
public class RegisterFirstViewModel extends BaseCommonViewModel {
    private boolean flag = false;//是否可以获取验证码

    public RegisterFirstViewModel(Context context) {
        super(context);
    }
    public void requestData(final Map map) {
        if (!flag) {
            fromNetwork.checkCustomer(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxSubscribe<BaseReturnModel>(context) {
                        @Override
                        protected void myNext(BaseReturnModel baseReturnModel) {
                            if (baseReturnModel.success) {
                                requestCode(map);
                            } else {
                                Utils.showToast(context, baseReturnModel.message);
                            }
                        }


                        @Override
                        protected void myError(String message) {
                            Utils.showToast(context, message);
                        }
                    });
        } else {
            requestCode(map);
        }

    }

    /**
     * 请求验证码
     *
     * @param map
     */
    private void requestCode(Map map) {
        map.put("type", "1");
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


//        fromNetwork.sendMessage(map)
//                .compose(RxHelper.<CommonModel>handleResult())
//                .subscribe(new RxSubscribe<CommonModel>(context) {
//                    @Override
//                    protected void myNext(CommonModel CommonModel) {
//                        Utils.showToast(context, "短信已发送");
//                    }
//                    @Override
//                    protected void myError(String message) {
//                        Utils.showToast(context, message);
//                    }
//                });
    }

}
