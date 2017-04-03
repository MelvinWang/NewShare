package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

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
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivity;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.fragment.wallet.WalletUseFragment;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;
import static com.melvin.share.R.id.recyclerView;

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
        //定位传参数
        shopLocation = intent.getBooleanExtra("shopLocation", false);
        addressLongitude = intent.getStringExtra("addressLongitude");
        addressLatitude = intent.getStringExtra("addressLatitude");
        shopName = intent.getStringExtra("shopName");

    }

    /**
     * 完成
     *
     * @param view
     */
    public void done(View view) {
        if (shopLocation) {
            applyExperience();
        }

    }

    /**
     * 申请成为体验馆
     */
    private void applyExperience() {
        Map map = new HashMap();
        map.put("experienceName", shopName);
        map.put("longitude", addressLongitude);
        map.put("latitude", addressLatitude);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.applyExperience(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(UploadCertificateActivity.this, true))
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
}
