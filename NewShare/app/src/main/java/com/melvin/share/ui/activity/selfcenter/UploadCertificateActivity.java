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
import com.melvin.share.databinding.ActivityOpenShopSBinding;
import com.melvin.share.model.Product;
import com.melvin.share.model.WalletProduct;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.query.OpenShop;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxActivity;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.activity.common.PictureActivity;
import com.melvin.share.ui.fragment.wallet.WalletUseFragment;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;
import static com.melvin.share.R.id.recyclerView;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/3
 * <p>
 * 描述： 上传证件
 */
public class UploadCertificateActivity extends BaseActivity {
    private ActivityOpenShopSBinding binding;
    private Context mContext = null;
    private boolean shopLocation;//true代表定位， false代表开店
    private String addressLongitude;
    private String addressLatitude;
    private String shopName;
    private OpenShop openShop;
    private String resultPicturePath;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_openshop_second);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        Intent intent = getIntent();
        //true代表申请体验馆， false代表开店
        shopLocation = intent.getBooleanExtra("shopLocation", false);
        //定位传参数
        addressLongitude = intent.getStringExtra("addressLongitude");
        addressLatitude = intent.getStringExtra("addressLatitude");
        shopName = intent.getStringExtra("shopName");
        //开店
        openShop = getIntent().getParcelableExtra("openShop");

    }

    /**
     * 下一步
     *
     * @param view
     */
    public void clickNext(View view) {
        if (TextUtils.isEmpty(resultPicturePath)) {
            Utils.showToast(context, "请传营业执照");
            return;
        }
        if (shopLocation) {//申请体验馆
            Intent intent = new Intent(mContext, UploadCertificateDoneActivity.class);
            intent.putExtra("addressLongitude",addressLongitude);
            intent.putExtra("addressLatitude",addressLatitude);
            intent.putExtra("shopName",shopName);
            intent.putExtra("businessLicence",resultPicturePath);
            intent.putExtra("shopLocation",true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, UploadCertificateDoneActivity.class);
            openShop.businessLicence = resultPicturePath;
            intent.putExtra("openShop", openShop);
            startActivity(intent);
        }

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
