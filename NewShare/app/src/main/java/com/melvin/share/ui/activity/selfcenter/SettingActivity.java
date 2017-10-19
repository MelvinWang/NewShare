package com.melvin.share.ui.activity.selfcenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxCommonBus;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivitySettingBinding;
import com.melvin.share.model.customer.Customer;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.popwindow.SexPopupWindow;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.AmendPasswordActivity;
import com.melvin.share.ui.activity.common.AmendPhoneActivity;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.CityTransparentActivity;
import com.melvin.share.ui.activity.common.LoginActivity;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.activity.common.PictureActivity;
import com.melvin.share.ui.activity.common.RegisterFirstActivity;
import com.melvin.share.ui.fragment.login.NormalLoginFragment;
import com.melvin.share.view.SelectTimeopupWindow;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.HashMap;
import java.util.Map;

import static com.umeng.socialize.utils.DeviceConfig.context;

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
    private String resultPicturePath;
    private Customer mCustomer;
    private ProgressDialog dialog;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        mContext = this;
        dialog = new ProgressDialog(mContext);
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
                        resultPicturePath=customer.picture;
                        if (!TextUtils.isEmpty(customer.picture)) {
                            Glide.with(mContext)
                                    .load((GlobalUrl.SERVICE_URL + customer.picture))
                                    .placeholder(R.mipmap.logo)
                                    .centerCrop()
                                    .into(binding.avatarPic);
                        }
                        LogUtils.i(mCustomer.toString());
                        binding.setModel(mCustomer);
                        if(TextUtils.isEmpty(mCustomer.openIdQQ)){
                            binding.tvQq.setText("未绑定");
                        }else{
                            binding.tvQq.setText("已绑定");
                        }
                        if(TextUtils.isEmpty(mCustomer.openIdWechat)){
                            binding.tvWechat.setText("未绑定");
                        }else{
                            binding.tvWechat.setText("已绑定");
                        }
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
        Intent intent = new Intent();
        intent.setClass(mContext, PictureActivity.class);
        startActivityForResult(intent, 20);

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
     * 修改手机号
     *
     * @param v
     */
    public void amendPhone(View v) {
        Intent intent = new Intent();
        intent.setClass(mContext, AmendPhoneActivity.class);
        startActivityForResult(intent, 30);
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
        startActivity(new Intent(mContext, LoginActivity.class));

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


    public void amendWechat(View view) {
        if(TextUtils.isEmpty(mCustomer.openIdWechat)){
            UMShareAPI.get(mContext).doOauthVerify(SettingActivity.this, SHARE_MEDIA.WEIXIN, authListener);
        }else{
            Utils.showToast(mContext, "已绑定");
        }
    }

    public void amendQQ(View view) {
        if(TextUtils.isEmpty(mCustomer.openIdQQ)){
            UMShareAPI.get(mContext).doOauthVerify(SettingActivity.this, SHARE_MEDIA.QQ, authListener);
        }else{
            Utils.showToast(mContext, "已绑定");
        }
    }


    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            LogUtils.i(data.toString()+"成功了");
            if (platform.equals(SHARE_MEDIA.WEIXIN)){
                bingdingWechat(data);
            }else{
                bingdingQQ(data);
            }
//
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            LogUtils.i(t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 微信绑定
     * @param data
     */
    private void bingdingWechat(final Map<String, String> data) {
        Map map = new HashMap();
        map.put("openIdWechat", data.get("openid"));
        map.put("accessToken",data.get("access_token"));
        map.put("customerId",mCustomer.id);
        fromNetwork.bingdingWechat(map)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(SettingActivity.this,true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        mCustomer.openIdWechat=data.get("openid");
                        Utils.showToast(mContext, commonReturnModel.message);
                        binding.tvWechat.setText("已绑定");
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }


    /**
     * QQ绑定
     * @param data
     */
    private void bingdingQQ(final Map<String, String> data) {
        Map map = new HashMap();
        map.put("openIdQQ", data.get("openid"));
        map.put("openKey",data.get("access_token"));
        map.put("customerId",mCustomer.id);
        fromNetwork.bingdingQQ(map)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(SettingActivity.this,true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        mCustomer.openIdQQ=data.get("openid");
                        Utils.showToast(mContext, commonReturnModel.message);
                        binding.tvQq.setText("已绑定");
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

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
        if (TextUtils.isEmpty(mCustomer.nickName)){
            mCustomer.nickName=mCustomer.userName;
        }
        mCustomer.customerId = mCustomer.id;
        mCustomer.picture = resultPicturePath;
        ShapreUtils.setUserName(mCustomer.userName);
        ShapreUtils.setPicture(GlobalUrl.SERVICE_URL+mCustomer.picture);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(mCustomer)));
        LogUtils.i(jsonObject.toString());

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxCommonBus.get().unregister(this);
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                String result = data.getExtras().getString("result");
                String[] split = result.split("-");
                if (split.length >= 3) {

                    binding.tvAddress.setText(split[0] + split[1] + split[2]);
                }
            }
        } else if (requestCode == 20) {
            if (data != null) {
                String result = data.getExtras().getString("result");

                if (!TextUtils.isEmpty(result)) {
                    Glide.with(mContext)
                            .load((GlobalUrl.SERVICE_URL + result))
                            .placeholder(R.mipmap.logo)
                            .centerCrop()
                            .into(binding.avatarPic);
                }
                resultPicturePath = result;
            }
        }else if (requestCode == 30) {
            if (data != null) {
                String result = data.getExtras().getString("result");
                if (!TextUtils.isEmpty(result)) {
                    binding.tvPhone.setText(result);
                    mCustomer.phone=result;
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }



}
