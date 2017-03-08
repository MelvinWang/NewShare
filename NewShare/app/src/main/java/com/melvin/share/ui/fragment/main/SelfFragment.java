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

import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.RecommendShopAdapter;
import com.melvin.share.app.BaseApplication;
import com.melvin.share.databinding.FragmentSelfBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.common.LoginActivity;
import com.melvin.share.ui.activity.selfcenter.AboutUsActivity;
import com.melvin.share.ui.activity.selfcenter.AllOrderActivity;
import com.melvin.share.ui.activity.selfcenter.LocationShopActivity;
import com.melvin.share.ui.activity.selfcenter.ManageAddressActivity;
import com.melvin.share.ui.activity.selfcenter.OpenshopFirstActivity;
import com.melvin.share.ui.activity.selfcenter.ProductCollectionActivity;
import com.melvin.share.ui.activity.selfcenter.QueryHelpActivity;
import com.melvin.share.ui.activity.selfcenter.SettingActivity;
import com.melvin.share.ui.activity.selfcenter.ShopCollectionActivity;
import com.melvin.share.ui.activity.selfcenter.ShoppingCarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/11/29
 * <p/>
 * 描述：个人中心
 */
public class SelfFragment extends BaseFragment implements View.OnClickListener {

    private FragmentSelfBinding binding;
    private Context mContext;
    private View root;
    private RecyclerView recyclerView;
    private RecommendShopAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();

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

    /**
     * 初始化数据
     */
    private void initData() {
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
        adpter = new RecommendShopAdapter(mContext, dataList);
        recyclerView.setAdapter(adpter);
    }

    /**
     * 绑定点击事件
     */
    private void initClick() {

        binding.clickAvatar.setOnClickListener(this);
        binding.clickShopCar.setOnClickListener(this);
        binding.clickAllOrder.setOnClickListener(this);
        binding.clickProductCollection.setOnClickListener(this);
        binding.clickShopCollection.setOnClickListener(this);
//        binding.clickWaitPay.setOnClickListener(this);
//        binding.clickWaitSendProduct.setOnClickListener(this);
//        binding.clickWaitReceiveProduct.setOnClickListener(this);
//        binding.clickWaitEvaluate.setOnClickListener(this);
//        binding.clickRefund.setOnClickListener(this);

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
                String customerId = BaseApplication.mPre.getString("customerId", null);
                if (!TextUtils.isEmpty(customerId)) {
                    intent.setClass(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                } else {
                    intent.setClass(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }
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

//            case R.id.click_wait_pay://待付款
//                intent.setClass(mContext, WaitPayActivity.class);
//                mContext.startActivity(intent);
//                break;
//            case R.id.click_wait_send_product://待发货
//                intent.setClass(mContext, WaitSendProductActivity.class);
//                mContext.startActivity(intent);
//                break;
//            case R.id.click_wait_receive_product://待收货
//                intent.setClass(mContext, WaitReceiveProductActivity.class);
//                mContext.startActivity(intent);
//                break;
//            case R.id.click_wait_evaluate://待评价
//                intent.setClass(mContext, WaitEvaluateActivity.class);
//                mContext.startActivity(intent);
//                break;
//            case R.id.click_refund://退款
//                intent.setClass(mContext, RefundActivity.class);
//                mContext.startActivity(intent);
//                break;


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
        for (int i = 0; i <= 10; i++) {
            User user = new User();
            user.username = "username" + i;
            dataList.add(user);
        }
        adpter.notifyDataSetChanged();
//        fromNetwork.findRecommendedSeller()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxSubscribe<ArrayList<ShopBean>>(mContext) {
//                    @Override
//                    protected void myNext(ArrayList<ShopBean> list) {
//                        dataList.addAll(list);
//                        adpter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    protected void myError(String message) {
//
//                    }
//
//                });
    }
}
