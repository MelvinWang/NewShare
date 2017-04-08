package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityOpenShopDBinding;
import com.melvin.share.databinding.ActivityOpenShopSBinding;
import com.melvin.share.model.query.OpenShop;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.activity.common.PictureActivity;

import java.util.HashMap;
import java.util.Map;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/8
 * <p>
 * 描述： 上传证件
 */
public class UploadCertificateDoneActivity extends BaseActivity {
    private ActivityOpenShopDBinding binding;
    private Context mContext = null;
    private boolean shopLocation;//true代表定位， false代表开店
    private String addressLongitude;
    private String addressLatitude;
    private String shopName;
    private String businessLicence;
    private OpenShop openShop;
    private String resultPicturePath;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_openshop_done);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        Intent intent = getIntent();
        shopLocation = intent.getBooleanExtra("shopLocation", false);
        //定位传参数
        addressLongitude = intent.getStringExtra("addressLongitude");
        addressLatitude = intent.getStringExtra("addressLatitude");
        shopName = intent.getStringExtra("shopName");
        businessLicence = intent.getStringExtra("businessLicence");
        //开店
        openShop = getIntent().getParcelableExtra("openShop");

    }

    /**
     * 完成
     *
     * @param view
     */
    public void done(View view) {
        if (TextUtils.isEmpty(resultPicturePath)) {
            Utils.showToast(context, "请传身份证");
            return;
        }
        if (shopLocation) {
            applyExperience();
        } else {
            openShop.idCard = resultPicturePath;
            applyUser();
        }

    }

    /**
     * 申请店铺
     */
    private void applyUser() {
        Map map = new HashMap();
        map.put("picture", openShop.picture);
        map.put("name", openShop.name);
        map.put("idCard", openShop.idCard);
        map.put("description", openShop.describeContents());
        map.put("businessLicence", openShop.businessLicence);
        map.put("phone", openShop.phone);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.applyUser(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(UploadCertificateDoneActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel bean) {
                        Utils.showToast(mContext, bean.message);
                        startActivity(new Intent(mContext, MainActivity.class));

                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 申请成为体验馆
     */
    private void applyExperience() {
        Map map = new HashMap();
        map.put("experienceName", shopName);
        map.put("longitude", addressLongitude);
        map.put("latitude", addressLatitude);
        map.put("businessLicence", businessLicence);
        map.put("idCard", resultPicturePath);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.applyExperience(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(UploadCertificateDoneActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel bean) {
                        Utils.showToast(mContext, bean.message);
                        startActivity(new Intent(mContext, MainActivity.class));

                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }


    public void uploadImg(View view) {
        Intent intent = new Intent();
        intent.setClass(mContext, PictureActivity.class);
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
            if (data != null) {
                String result = data.getExtras().getString("result");

                if (!TextUtils.isEmpty(result)) {
                    Glide.with(mContext)
                            .load((GlobalUrl.SERVICE_URL + result))
                            .placeholder(R.mipmap.logo)
                            .centerCrop()
                            .into(binding.image);
                }
                resultPicturePath = result;
            }
        }
    }
}
