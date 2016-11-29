package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.melvin.share.R;
import com.melvin.share.Utils.CodeUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityLoginBinding;
import com.melvin.share.databinding.ActivityPhoneLoginBinding;
import com.melvin.share.modelview.common.PhoneLoginViewModel;
import com.melvin.share.modelview.common.RegisterFirstViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Time: 2016/8/7.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：手机登录页面
 */

public class PhoneLoginActivity extends BaseActivity {
    private ActivityPhoneLoginBinding binding;
    private Context context;
    //产生的验证码
    private String realCode;
    private ImageView iv_showCode;
    private EditText phoneEt;
    private EditText codeEt;
    private EditText cerificationCodeEt;
    private Map map;
    private PhoneLoginViewModel phoneLoginViewModel;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_login);
        context = this;
        initWindow();
        iv_showCode = binding.ivShowCode;
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {
        map = new HashMap();
        //将验证码用图片的形式显示出来s
        iv_showCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode();
        phoneLoginViewModel = new PhoneLoginViewModel(context);

        phoneEt = binding.phoneNumber;
        codeEt = binding.userValinumber;
        cerificationCodeEt = binding.cerificationCode;
    }

    /**
     * 登录
     *
     * @param v
     */
    public void login(View v) {

    }

    /**
     * 获取验证码
     *
     * @param view
     */
    public void getCode(View view) {
        if (TextUtils.isEmpty(phoneEt.getText().toString())) {
            Utils.showToast(context, "请输入电话号码");
            return;
        }
        map.put("phone", phoneEt.getText().toString());
        map.put("type", "2");
        phoneLoginViewModel.requestData(map);

    }

    /**
     * 登录
     *
     * @param view
     */
    public void loginDone(View view) {
        if (TextUtils.isEmpty(phoneEt.getText().toString())) {
            Utils.showToast(context, "请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(codeEt.getText().toString())) {
            Utils.showToast(context, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(cerificationCodeEt.getText().toString())) {
            Utils.showToast(context, "请输入图片验证码");
            return;
        }
        if (!cerificationCodeEt.getText().toString().equalsIgnoreCase(realCode)) {
            Utils.showToast(context, "图片验证码不一致");
            return;
        }
        map.put("phone", phoneEt.getText().toString());
        map.put("code", codeEt.getText().toString());
        phoneLoginViewModel.loginByCode(map);

    }

    /**
     * 刷新验证码
     *
     * @param view
     */
    public void refreshCerificationCode(View view) {
        iv_showCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode();
    }


}
