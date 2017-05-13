package com.melvin.share.ui.activity.shopcar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityDepositBinding;
import com.melvin.share.ui.activity.common.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/30
 * <p>
 * 描述： 提现
 */
public class DepositActivity extends BaseActivity {
    private ActivityDepositBinding binding;
    private Context mContext = null;
    private EditText cash;
    private EditText alipayNumber;
    private EditText phoneNumber;
    private EditText valiNumber;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deposit);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
    }

    private void ininData() {

        cash = binding.cash;
        alipayNumber = binding.alipayNumber;
        phoneNumber = binding.phoneNumber;
        valiNumber = binding.valiNumber;

    }

    /**
     * 提现
     * @param view
     */
    public void confirmDeposit(View view) {
        if (TextUtils.isEmpty(cash.getText().toString())) {
            Utils.showToast(mContext, "请输入提现金额");
            return;
        }
        if (TextUtils.isEmpty(alipayNumber.getText().toString())) {
            Utils.showToast(mContext, "请输入支付宝账号");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            Utils.showToast(mContext, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(valiNumber.getText().toString())) {
            Utils.showToast(mContext, "请输入验证码");
            return;
        }

        applyDeposit();

    }

    /**
     * 申请提现
     */
    private void applyDeposit() {
        String scash = cash.getText().toString();
        String salipayNumber = alipayNumber.getText().toString();
        String sphoneNumber = phoneNumber.getText().toString();
        String svaliNumber = valiNumber.getText().toString();
        Map map=new HashMap();



        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
    }
}
