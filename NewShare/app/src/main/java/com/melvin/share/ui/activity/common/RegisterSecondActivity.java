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
import com.melvin.share.databinding.ActivityRegisterSBinding;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/21
 * <p/>
 * 描述：注册第二页
 */
public class RegisterSecondActivity extends BaseActivity {
    private ActivityRegisterSBinding binding;
    private Context context;
    private String phone;
    private String username;
    private String code;
    private EditText userPassword;
    private EditText confirmPassword;
    private Map map;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_second);
        context = this;
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {
        map = new HashMap();
        phone = getIntent().getStringExtra("phone");
        username = getIntent().getStringExtra("username");
        code = getIntent().getStringExtra("code");

        map.put("phone", phone);
        map.put("userName", username);
        map.put("code", code);

        userPassword = binding.userPassword;
        confirmPassword = binding.confirmPassword;
    }

    /**
     * 完成
     *
     * @param view
     */
    public void done(View view) {
        if (TextUtils.isEmpty(userPassword.getText().toString())) {
            Utils.showToast(context, "请输入密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            Utils.showToast(context, "请输入确认密码");
            return;
        }
        if (!userPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            Utils.showToast(context, "两次密码不一致");
            return;
        }
        regist();
    }

    //注册
    private void regist() {
        map.put("password", confirmPassword.getText().toString());

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        LogUtils.i(jsonObject.toString());
        fromNetwork.regist(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(RegisterSecondActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(context, commonReturnModel.message);
                        startActivity(new Intent(context, LoginActivity.class));
                        finish();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }
}
