package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityAmendPasswrodBinding;
import com.melvin.share.databinding.ActivityAmendPhoneBinding;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;

import java.util.HashMap;
import java.util.Map;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述：修改手机号码
 */
public class AmendPhoneActivity extends BaseActivity {
    private ActivityAmendPhoneBinding binding;
    private EditText phoneEt;
    private EditText codeEt;
    private EditText passwordEt;
    private Map map;
    private Context mContext;
    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_amend_phone);
        initWindow();
        mContext = this;
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {
        map = new HashMap();
        phoneEt = binding.userPassword;
        codeEt = binding.userValinumber;
        passwordEt = binding.confirmPassword;
    }

    /**
     * 获取验证码
     *
     * @param view
     */
    public void getCode(View view) {
        if (TextUtils.isEmpty(phoneEt.getText().toString())) {
            Utils.showToast(mContext, "请输入电话号码");
            return;
        }
        Map map=new HashMap<>();
        map.put("phone",phoneEt.getText().toString());
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        LogUtils.i(jsonObject.toString());
        fromNetwork.checkCustomer(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(AmendPhoneActivity.this,true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        if (commonReturnModel.success) {
                            requestCode(phoneEt.getText().toString());
                        } else {
                            Utils.showToast(mContext, commonReturnModel.message);
                        }
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });





    }

    /**
     * 请求验证码
     *
     * @param phone
     */
    public void requestCode(String phone) {
        map.put("phone", phoneEt.getText().toString());
        map.put("type", "UPDATE_PHONE");
        fromNetwork.sendMessage(map)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(AmendPhoneActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 确认
     *
     * @param view
     */
    public void confrim(View view) {
        if (TextUtils.isEmpty(phoneEt.getText().toString())) {
            Utils.showToast(mContext, "请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(codeEt.getText().toString())) {
            Utils.showToast(mContext, "请输入验证码");
            return;
        }
//        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
//            Utils.showToast(mContext, "请输入密码");
//            return;
//        }
        Map hashMap = new HashMap();
        hashMap.put("phone", phoneEt.getText().toString());
        hashMap.put("code", codeEt.getText().toString());
//        hashMap.put("newPassword", passwordEt.getText().toString());
        ShapreUtils.putParamCustomerId(hashMap);
        fromNetwork.updatePhone(hashMap)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(AmendPhoneActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Intent intent = new Intent();
                        intent.putExtra("result", phoneEt.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

    }

}
