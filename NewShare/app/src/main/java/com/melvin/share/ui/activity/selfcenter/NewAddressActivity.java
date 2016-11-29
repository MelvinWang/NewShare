package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityNewAddressBinding;
import com.melvin.share.event.AddressEvent;
import com.melvin.share.model.Product;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.CityTransparentActivity;
import com.melvin.share.view.RxSubscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
    private Map map;
    private AddressEvent addressEvent;

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
        map = new HashMap();
        addressEvent = new AddressEvent(map);
        binding.setEvent(addressEvent);
        addressBean = getIntent().getParcelableExtra("addressBean");
        ShapreUtils.putParamCustomerDotId(map);
        if (addressBean != null) {//修改
            map.put("id", addressBean.id);
            binding.setAddressBean(addressBean);
            binding.titleName.setText("修改地址");
        }
    }

    /**
     * 页面保存按钮
     *
     * @param v
     */
    public void save(View v) {
        map.put("recever", binding.recever.getText().toString());
        map.put("receverPhone", binding.receverPhone.getText().toString());
        map.put("postalcode", binding.postalcode.getText().toString());
        map.put("address", binding.address.getText().toString());
        if (TextUtils.isEmpty((String) map.get("recever"))) {
            Utils.showToast(context, "请输入姓名");
            return;
        }
        if (TextUtils.isEmpty((String) map.get("receverPhone"))) {
            Utils.showToast(context, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(binding.area.getText().toString())) {
            Utils.showToast(context, "请选择城市");
            return;
        }
        if (TextUtils.isEmpty((String) map.get("address"))) {
            Utils.showToast(context, "请输入详细地址");
            return;
        }
        map.put("area", binding.area.getText().toString());
        saveToServer();
    }

    /**
     * 保存
     */
    private void saveToServer() {
        fromNetwork.persistAddress(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<BaseReturnModel>(context) {
                    @Override
                    protected void myNext(BaseReturnModel baseReturnModel) {
                        Utils.showToast(context, baseReturnModel.message);
                        if (baseReturnModel.success) {
                            ManageAddressActivity.saveOrUpdate = true;
                            finish();
                        }
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
                binding.area.setText(split[0]);
            }
        }
    }
}
