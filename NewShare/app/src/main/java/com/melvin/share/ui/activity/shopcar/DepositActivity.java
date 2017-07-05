package com.melvin.share.ui.activity.shopcar;

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
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityDepositBinding;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.activity.selfcenter.DepositRecordActivity;
import com.melvin.share.ui.activity.selfcenter.UploadCertificateDoneActivity;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.phoneNumber;

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
    private EditText realName;
//    private EditText phoneNumber;
//    private EditText valiNumber;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deposit);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        cash = binding.cash;
        alipayNumber = binding.alipayNumber;
        realName = binding.realName;
        String account = getIntent().getStringExtra("account");
        cash.setHint("最大提现金额为："+account);
//        phoneNumber = binding.phoneNumber;
//        valiNumber = binding.valiNumber;

    }

    /**
     * 提现
     *
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
        if (TextUtils.isEmpty(realName.getText().toString())) {
            Utils.showToast(mContext, "请输入真实姓名");
            return;
        }
//        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
//            Utils.showToast(mContext, "请输入手机号码");
//            return;
//        }
//        if (TextUtils.isEmpty(valiNumber.getText().toString())) {
//            Utils.showToast(mContext, "请输入验证码");
//            return;
//        }

        applyDeposit();

    }

    /**
     * 申请提现
     */
    private void applyDeposit() {
        String scash = cash.getText().toString();
        String salipayNumber = alipayNumber.getText().toString();
        String sName = realName.getText().toString();
        Map map = new HashMap();
        map.put("money", scash);
        map.put("account", salipayNumber);
        map.put("name", sName);
        ShapreUtils.putParamCustomerId(map);

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.withdrawCash(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(DepositActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel bean) {
                        Utils.showToast(mContext, bean.message);
                        startActivity(new Intent(mContext, DepositRecordActivity.class));
                        finish();

                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }


    public void toRecord(View view) {
        startActivity(new Intent(mContext, DepositRecordActivity.class));
    }
}
