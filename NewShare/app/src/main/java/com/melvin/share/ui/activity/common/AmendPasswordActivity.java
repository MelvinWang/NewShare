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
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述：修改密码
 */
public class AmendPasswordActivity extends BaseActivity {
    private ActivityAmendPasswrodBinding binding;
    private Context context;
    private Map map;
    private EditText userPassword;
    private EditText confirmPassword;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_amend_password);
        initWindow();
        initToolbar(binding.toolbar);
        context = this;
        initData();
    }

    private void initData() {
        map = new HashMap();
        userPassword = binding.userPassword;
        confirmPassword = binding.confirmPassword;
    }

    public void updatePassword(View view) {
        if (TextUtils.isEmpty(userPassword.getText().toString())) {
            Utils.showToast(this, "请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            Utils.showToast(context, "请输入新密码");
            return;
        }

        ShapreUtils.putParamCustomerId(map);
        map.put("oldPassword", userPassword.getText().toString());
        map.put("newPassword", confirmPassword.getText().toString());

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.updatePassword(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(AmendPasswordActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(context, commonReturnModel.message);
                        finish();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }
}
