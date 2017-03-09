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
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityAmendPhoneBinding;
import com.melvin.share.databinding.ActivityForgetPasswordBinding;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.fragment.login.PhoneLoginFragment;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/7
 * <p/>
 * 描述：忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {
    private ActivityForgetPasswordBinding binding;
    private EditText phoneEt;
    private EditText codeEt;
    private EditText passwordEt;
    private Map map;
    private Context mContext;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
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
        map.put("phone", phoneEt.getText().toString());
        map.put("type", "UPDATE");
        fromNetwork.sendMessage(map)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(ForgetPasswordActivity.this, true))
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
        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
            Utils.showToast(mContext, "请输入密码");
            return;
        }
        Map hashMap = new HashMap();
        hashMap.put("phone", phoneEt.getText().toString());
        hashMap.put("code", codeEt.getText().toString());
        hashMap.put("newPassword", passwordEt.getText().toString());
        fromNetwork.forgetPassword(hashMap)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(ForgetPasswordActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

    }
}
