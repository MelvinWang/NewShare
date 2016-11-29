package com.melvin.share.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityShopInformationBinding;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.fragment.shop.AllProductFragment;
import com.melvin.share.ui.fragment.shop.NewProductFragment;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/24
 * <p/>
 * 描述： 店铺信息
 */
public class ShopInformationActivity extends BaseActivity {

    private ActivityShopInformationBinding binding;
    private Context mContext = null;
    private FragmentManager supportFragmentManager;
    public FrameLayout frameLayout;
    public AllProductFragment allProductFragment;
    public NewProductFragment newProductFragment;
    public static String sellerId;
    public String imgUrl = "";
    private ShopBean shopBean;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_infomation);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }


    public void toSearchActivity(View v) {
        startActivity(new Intent(mContext, SearchActivity.class));
    }

    private void ininData() {
        shopBean = getIntent().getParcelableExtra("shopBean");
        sellerId = shopBean.id;
        //设置值
        binding.shopName.setText(shopBean.name);
        String[] split = shopBean.logo.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            LogUtils.e("哈哈" + url);
            imgUrl = url;
        }
        Glide.with(mContext)
                .load(imgUrl)
                .centerCrop()
                .into(binding.shopImg);

        frameLayout = binding.productInformation;
        //获取到fragment的管理者
        supportFragmentManager = getSupportFragmentManager();
        //开启事务,这里是否只能开启一个？只能一个
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        //把两个Fragment对象创建出来
        allProductFragment = new AllProductFragment();
        newProductFragment = new NewProductFragment();
        /**
         * 替换界面
         * 1 需要替换的界面的id
         * 2具体指某一个fragment的对象
         */
        fragmentTransaction.replace(R.id.product_information, allProductFragment).commit();
    }

    /**
     * 所有商品
     *
     * @param v
     */
    public void allProduct(View v) {
        //这里得重新开启一次
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.product_information, allProductFragment);
        //一定要提交
        transaction.commit();
    }

    /**
     * 最新上架
     *
     * @param v
     */
    public void newProduct(View v) {
        //这里得重新开启一次
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.product_information, newProductFragment);
        //一定要提交
        transaction.commit();
    }

}
