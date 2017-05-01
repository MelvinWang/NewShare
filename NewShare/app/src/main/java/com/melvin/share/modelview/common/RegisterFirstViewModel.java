package com.melvin.share.modelview.common;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.BaseCommonViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.ui.activity.common.RegisterFirstActivity;
import com.melvin.share.rx.RxSubscribe;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;

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
        Map map=new HashMap<>();
        map.put("userName",userName);
        map.put("phone",phone);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        LogUtils.i(jsonObject.toString());
        fromNetwork.checkCustomer(jsonObject)
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
