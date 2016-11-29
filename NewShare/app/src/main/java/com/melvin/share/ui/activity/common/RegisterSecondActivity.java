package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityRegisterSBinding;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.view.RxSubscribe;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        map.put("username", username);
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
        fromNetwork.regist(map)
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
}
