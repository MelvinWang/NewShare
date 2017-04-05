package com.melvin.share.ui.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.GlideImgManager;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.ShareHotAdapter;
import com.melvin.share.databinding.FragmentSelfBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.common.LoginActivity;
import com.melvin.share.ui.activity.selfcenter.AboutUsActivity;
import com.melvin.share.ui.activity.order.AllOrderActivity;
import com.melvin.share.ui.activity.selfcenter.LocationShopActivity;
import com.melvin.share.ui.activity.selfcenter.ManageAddressActivity;
import com.melvin.share.ui.activity.selfcenter.MessageActivity;
import com.melvin.share.ui.activity.selfcenter.OpenshopFirstActivity;
import com.melvin.share.ui.activity.selfcenter.ProductCollectionActivity;
import com.melvin.share.ui.activity.selfcenter.QueryHelpActivity;
import com.melvin.share.ui.activity.selfcenter.SettingActivity;
import com.melvin.share.ui.activity.selfcenter.ShopCollectionActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/5
 * <p/>
 * 描述：个人中心
 */
public class SelfFragment extends BaseFragment implements View.OnClickListener {

    private FragmentSelfBinding binding;
    private Context mContext;
    private View root;
    private RecyclerView recyclerView;
    private ShareHotAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();
    private Map map;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_self, container, false);
        if (root == null) {
            mContext = getActivity();
            LogUtils.i("SelfFragment+initView");
            initClick();
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        } else {
            ViewUtils.removeParent(root);
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        String userName = ShapreUtils.getUserName();
        String picture = ShapreUtils.getPicture();
        if (!TextUtils.isEmpty(userName)) {
            binding.name.setText(userName);
        } else {
            binding.name.setText("点击登录");
        }
        if (!TextUtils.isEmpty(picture)) {
            GlideImgManager.glideLoader(mContext, picture, R.mipmap.self_center_avatar, R.mipmap.self_center_avatar,
                    binding.selfCenterAvatar, 0);
        } else {
            Glide.with(mContext).
                    load(R.mipmap.self_center_avatar).
                    into(binding.selfCenterAvatar);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        map = new HashMap();
        recyclerView = binding.recyclerView;
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adpter = new ShareHotAdapter(mContext, dataList);
        recyclerView.setAdapter(adpter);
    }

    /**
     * 绑定点击事件
     */
    private void initClick() {
        binding.clickAvatar.setOnClickListener(this);
        binding.message.setOnClickListener(this);
        binding.clickShopCar.setOnClickListener(this);
        binding.clickAllOrder.setOnClickListener(this);
        binding.clickProductCollection.setOnClickListener(this);
        binding.clickShopCollection.setOnClickListener(this);
        binding.clickOpenShop.setOnClickListener(this);
        binding.clickVip.setOnClickListener(this);
        binding.clickReceiveAddress.setOnClickListener(this);

        binding.clickQueryHelp.setOnClickListener(this);
        binding.clickAboutUs.setOnClickListener(this);
        binding.clickSetting.setOnClickListener(this);

    }

    /**
     * 点击事件分发
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.click_avatar://头像登录
                String customerId = ShapreUtils.getCustomerId();
                LogUtils.i(customerId + "customerId");
                if (!TextUtils.isEmpty(customerId)) {
                    intent.setClass(mContext, SettingActivity.class);
                    mContext.startActivity(intent);
                } else {
                    intent.setClass(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }
                break;
            case R.id.message://消息
                intent.setClass(mContext, MessageActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_shop_car://购物车
                intent.setClass(mContext, ShoppingCarActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_all_order://我的订单
                intent.setClass(mContext, AllOrderActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_product_collection://商品收藏
                intent.setClass(mContext, ProductCollectionActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_shop_collection://店铺收藏
                intent.setClass(mContext, ShopCollectionActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_open_shop://申请线上商场
                intent.setClass(mContext, OpenshopFirstActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_vip://申请线下体验馆
                intent.setClass(mContext, LocationShopActivity.class);
                mContext.startActivity(intent);
                break;

            case R.id.click_receive_address://收货地址
                intent.setClass(mContext, ManageAddressActivity.class);
                mContext.startActivity(intent);
                break;

            case R.id.click_query_help://咨询与帮助
                intent.setClass(mContext, QueryHelpActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_about_us://关于我们
                intent.setClass(mContext, AboutUsActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.click_setting://设置
                intent.setClass(mContext, SettingActivity.class);
                mContext.startActivity(intent);
                break;

        }

    }

    /**
     * 请求网络，分享热度商品
     */
    private void requestData() {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findHistoryProduct(jsonObject)
                .compose(new RxFragmentHelper<CommonList<HomeHotProduct>>().ioMain(mContext, SelfFragment.this, true))
                .subscribe(new RxModelSubscribe<CommonList<HomeHotProduct>>(mContext, true) {
                    @Override
                    protected void myNext(CommonList<HomeHotProduct> commonList) {
                        dataList.addAll(commonList.rows);
                        adpter.notifyDataSetChanged();
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

    }
}
