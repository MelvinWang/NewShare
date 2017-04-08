package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityOpenShopFBinding;
import com.melvin.share.model.query.OpenShop;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.PictureActivity;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/8
 * <p>
 * 描述： 我要开店第一页
 */
public class OpenshopFirstActivity extends BaseActivity {
    private ActivityOpenShopFBinding binding;
    private Context context = null;
    private String resultPicturePath;
    private OpenShop openShop;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_openshop_first);
        context = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        openShop = new OpenShop();
        binding.setModel(openShop);

    }


    /**
     * 图片
     *
     * @param v
     */
    public void uploadImg(View v) {
        Intent intent = new Intent();
        intent.setClass(context, PictureActivity.class);
        startActivityForResult(intent, 20);
    }

    /**
     * 下一步
     *
     * @param view
     */
    public void clickNext(View view) {
        if (TextUtils.isEmpty(openShop.picture)) {
            Utils.showToast(context, "请传店铺LOGO");
            return;
        }
        if (TextUtils.isEmpty(openShop.name)) {
            Utils.showToast(context, "请输入店铺名称");
            return;
        }
        if (TextUtils.isEmpty(openShop.description)) {
            Utils.showToast(context, "请输入店铺描述");
            return;
        }
        if (TextUtils.isEmpty(openShop.phone)) {
            Utils.showToast(context, "请输入11位电话号码");
            return;
        }
        if (11 != (openShop.phone.length())) {
            Utils.showToast(context, "请输入11位电话号码");
            return;
        }
        Intent intent = new Intent(context, UploadCertificateActivity.class);
        intent.putExtra("openShop", openShop);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
            if (data != null) {
                String result = data.getExtras().getString("result");

                if (!TextUtils.isEmpty(result)) {
                    Glide.with(context)
                            .load((GlobalUrl.SERVICE_URL + result))
                            .placeholder(R.mipmap.logo)
                            .centerCrop()
                            .into(binding.image);
                }
                openShop.picture = result;
                resultPicturePath = result;
            }
        }
    }


}
