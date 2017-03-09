package com.melvin.share.modelview.common;

import android.content.Context;

import com.melvin.share.Utils.Utils;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.BaseCommonViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.ui.activity.common.RegisterFirstActivity;
import com.melvin.share.rx.RxSubscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/24
 * <p/>
 * 描述： 登录页面ViewModel
 */
public class RegisterFirstViewModel extends BaseCommonViewModel {


    public RegisterFirstViewModel(Context context) {
        super(context);
    }

    public void requestData(String userName, final String phone) {
        fromNetwork.checkCustomer(userName, phone)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain((RegisterFirstActivity)context,true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        if (commonReturnModel.success) {
                            requestCode(phone);
                        } else {
                            Utils.showToast(context, commonReturnModel.message);
                        }
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });

    }

    /**
     * 请求验证码
     *
     * @param phone
     */
    public void requestCode(String phone) {
        Map map = new HashMap();
        map.put("phone", phone);
        map.put("type", "REGIST");
        fromNetwork.sendMessage(map)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain((RegisterFirstActivity)context,true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(context, commonReturnModel.message);
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });



    }

}
