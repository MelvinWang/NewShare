package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.RxCommonBus;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.app.BaseApplication;
import com.melvin.share.databinding.ActivitySettingBinding;
import com.melvin.share.model.customer.Customer;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.popwindow.SexPopupWindow;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.AmendPasswordActivity;
import com.melvin.share.ui.activity.common.AmendPhoneActivity;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.CityTransparentActivity;
import com.melvin.share.ui.activity.common.PictureActivity;
import com.melvin.share.ui.activity.common.RegisterFirstActivity;
import com.melvin.share.view.SelectTimeopupWindow;

import static com.melvin.share.R.id.map;
import static com.melvin.share.R.mipmap.phone;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/24
 * <p/>
 * 描述： 设置
 */
public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding;
    private Context mContext = null;
    private SexPopupWindow sexPopupWindow;
    private SelectTimeopupWindow selectTimeopupWindow; // 自定义的头像编辑弹出框
    private String sex;
    private Customer mCustomer;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        mContext = this;
        sexPopupWindow = new SexPopupWindow(this, itemsOnClick);
        selectTimeopupWindow = new SelectTimeopupWindow(mContext);
        initWindow();
        RxCommonBus.get().register(this); //注册
        initToolbar(binding.toolbar);
        initData();
    }

    /**
     * 获取数据
     */
    private void initData() {
        selectTimeopupWindow.setOnClickListener(new SelectTimeopupWindow.OnCliclListener() {
            @Override
            public void confirm(String date) {
                selectTimeopupWindow.dismiss();
                binding.tvBirthDay.setText(date);
            }
        });
        fromNetwork.findCustomerById(ShapreUtils.getCustomerId())
                .compose(new RxActivityHelper<Customer>().ioMain(SettingActivity.this, true))
                .subscribe(new RxModelSubscribe<Customer>(mContext, true) {
                    @Override
                    protected void myNext(Customer customer) {
                        mCustomer = customer;
                        binding.setModel(mCustomer);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

        binding.userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.userName.setGravity(Gravity.LEFT);
                } else {
                    binding.userName.setGravity(Gravity.RIGHT);
                }
            }
        });
        binding.nickName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.nickName.setGravity(Gravity.LEFT);
                } else {
                    binding.nickName.setGravity(Gravity.RIGHT);
                }
            }
        });

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sex_cancel:
                    sexPopupWindow.dismiss();
                    break;
                case R.id.sex_confirm:
                    sexPopupWindow.dismiss();
                    binding.tvSex.setText(sex);
                    break;
            }

        }

    };


    @Subscribe
    public void sexSelect(String sex) {
        this.sex = sex;
    }


    /**
     * 头像
     *
     * @param v
     */
    public void uploadHead(View v) {
        startActivity(new Intent(mContext, PictureActivity.class));
    }

    /**
     * 修改性别
     *
     * @param v
     */
    public void amendSex(View v) {
        sexPopupWindow.showAtLocation(binding.root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 修改生日
     *
     * @param v
     */
    public void amendBirthDay(View v) {
        selectTimeopupWindow.showAtLocation(findViewById(R.id.root),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 修改居住地
     *
     * @param v
     */
    public void amendAddress(View v) {
        Intent intent = new Intent();
        intent.setClass(mContext, CityTransparentActivity.class);
        startActivityForResult(intent, 10);
    }


    /**
     * 修改密码
     *
     * @param v
     */
    public void amendPassword(View v) {
        startActivity(new Intent(mContext, AmendPasswordActivity.class));
    }

    /**
     * 退出当前账号
     *
     * @param view
     */
    public void loginOut(View view) {
        ShapreUtils.setCustomerId(null);
        ShapreUtils.setUserName(null);
        ShapreUtils.setPicture(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxCommonBus.get().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                String result = data.getExtras().getString("result");
                String[] split = result.split("-");
                if (split.length >= 3) {

                    binding.tvAddress.setText(split[0] + split[1] + split[2]);
                }
            }
        }
    }

    /**
     * toolbar上菜单的选择事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                updateCutomerById();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 返回键先关闭侧滑菜单
     */
    @Override
    public void onBackPressed() {
        updateCutomerById();
    }

    /**
     * 保存
     */
    private void updateCutomerById() {
        mCustomer.customerId = mCustomer.id;
        mCustomer.realName = mCustomer.userName;
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(mCustomer)));

        fromNetwork.updateCutomerById(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(SettingActivity.this, false))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, false) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        finish();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

}
