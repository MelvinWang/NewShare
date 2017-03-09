package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityNewAddressBinding;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.CityTransparentActivity;


/**
 * Created Time: 2016/8/7.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：新增收货地址
 */

public class NewAddressActivity extends BaseActivity {
    private ActivityNewAddressBinding binding;
    private Context context;
    private AddressBean addressBean;
    private boolean newOrUpdate = true; //true代表新增

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_address);
        context = this;
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {
        addressBean = getIntent().getParcelableExtra("addressBean");
        if (addressBean != null) {//修改
            newOrUpdate = false;
            binding.titleName.setText("修改地址");
            binding.area.setText(addressBean.province + addressBean.city + addressBean.area);
        } else {
            newOrUpdate = true;
            addressBean = new AddressBean();
        }
        binding.setAddressBean(addressBean);
    }

    /**
     * 页面保存按钮
     *
     * @param v
     */
    public void save(View v) {
        if (TextUtils.isEmpty(addressBean.receiver)) {
            Utils.showToast(context, "请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(addressBean.phone)) {
            Utils.showToast(context, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(addressBean.province)
                || TextUtils.isEmpty(addressBean.city)
                || TextUtils.isEmpty(addressBean.area)) {
            Utils.showToast(context, "请选择城市");
            return;
        }
        if (TextUtils.isEmpty(addressBean.detailAddress)) {
            Utils.showToast(context, "请输入详细地址");
            return;
        }
        if (newOrUpdate) {
            saveToServer();
        } else {
            updateAddress();
        }
    }

    /**
     * 新增保存
     */
    private void saveToServer() {
        addressBean.customerId = ShapreUtils.getCustomerId();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(addressBean)));

        fromNetwork.insertAddressByCustomerId(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(NewAddressActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(context, commonReturnModel.message);
                        ManageAddressActivity.saveOrUpdate = true;
                        finish();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }

    /**
     * 修改保存
     */
    private void updateAddress() {
        addressBean.addressId = addressBean.id;
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(addressBean)));
        LogUtils.i(jsonObject.toString());
        fromNetwork.updateAddressByAddressId(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(NewAddressActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(context, commonReturnModel.message);
                        ManageAddressActivity.saveOrUpdate = true;
                        finish();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }

    /**
     * 获取城市名称
     *
     * @param v
     */
    public void onCityName(View v) {
        Intent intent = new Intent();
        intent.setClass(context, CityTransparentActivity.class);
        startActivityForResult(intent, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                String result = data.getExtras().getString("result");
                String[] split = result.split("-");
                if (split.length >= 3) {
                    addressBean.province = split[0];
                    addressBean.city = split[1];
                    addressBean.area = split[2];
                    binding.area.setText(addressBean.province + addressBean.city + addressBean.area);
                }
            }
        }
    }
}
