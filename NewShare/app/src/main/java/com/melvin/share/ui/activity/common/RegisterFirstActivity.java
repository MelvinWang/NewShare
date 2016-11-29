package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityRegisterFBinding;
import com.melvin.share.modelview.common.RegisterFirstViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/21
 * <p/>
 * 描述：注册第一页
 */
public class RegisterFirstActivity extends BaseActivity {
    private ActivityRegisterFBinding binding;
    private Context context;
    private EditText username;
    private EditText phone;
    private EditText code;
    private Map map;
    private RegisterFirstViewModel registerFirstViewModel;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_first);
        context = this;
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }
    private void initData() {
        map = new HashMap();
        registerFirstViewModel = new RegisterFirstViewModel(context);
        username = binding.userName;
        phone = binding.userPhone;
        code = binding.userValinumber;

    }
    /**
     * 获取验证码
     *
     * @param view
     */
    public void getCode(View view) {
        if (TextUtils.isEmpty(username.getText().toString())) {
            Utils.showToast(context, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Utils.showToast(context, "请输入电话号码");
            return;
        }
        map.put("phone", phone.getText().toString());
        registerFirstViewModel.requestData(map);

    }

    /**
     * 下一步
     *
     * @param view
     */
    public void clickNext(View view) {
        if (TextUtils.isEmpty(username.getText().toString())) {
            Utils.showToast(context, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Utils.showToast(context, "请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(code.getText().toString())) {
            Utils.showToast(context, "请输入验证码");
            return;
        }
        Intent intent = new Intent(context, RegisterSecondActivity.class);
        intent.putExtra("phone", phone.getText().toString());
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("code", code.getText().toString());
        startActivity(intent);
    }

}
