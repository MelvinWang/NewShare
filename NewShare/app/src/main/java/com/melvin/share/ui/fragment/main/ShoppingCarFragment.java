package com.melvin.share.ui.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.RxCarBus;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.ShopCarAdapter;
import com.melvin.share.databinding.FragmentShoppingCarBinding;
import com.melvin.share.event.PostEvent;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.ui.activity.selfcenter.NewAddressActivity;
import com.melvin.share.ui.activity.shopcar.ConfirmOrderActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarEditActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RxSubscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/17
 * <p/>
 * 描述：购物车
 */
public class ShoppingCarFragment extends BaseFragment implements MyRecyclerView.LoadingListener, View.OnClickListener {

    private FragmentShoppingCarBinding binding;
    private Context mContext;
    private MyRecyclerView mRecyclerView;
    private ShopCarAdapter shopCarAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private View root;
    private Map map = new HashMap();
    public static boolean updateFlag = false;
    private TextView totalPrice;
    private BigDecimal totalPriceBigcimal;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_car, container, false);
        if (root == null) {
            mContext = getActivity();
            LogUtils.i("ShoppingCarFragment+initView");
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        } else {
            ViewUtils.removeParent(root);// 移除frameLayout之前的爹
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
//        LogUtils.i("ShoppingCarFragment+onStart");
        RxCarBus.get().register(this); //注册
        if (updateFlag) {
            updateFlag = false;
            requestData();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        totalPrice = binding.totalPrice;
        binding.allChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPriceBigcimal = new BigDecimal(0);
                    for (Product product : productList) {
                        product.isChecked = isChecked;
                        BigDecimal multiply = new BigDecimal((product.price)).multiply(new BigDecimal((product.productNumber)));
                        totalPriceBigcimal = totalPriceBigcimal.add(multiply);
                    }
                    totalPrice.setText(totalPriceBigcimal + "");
                } else {
                    for (Product product : productList) {
                        product.isChecked = isChecked;
                    }
                    totalPrice.setText("0");
                }
                shopCarAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        binding.ShopCarEdit.setOnClickListener(this);
        binding.gotoPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._shop_car_edit:
                startActivity(new Intent(mContext, ShoppingCarEditActivity.class));
                break;
            case R.id.goto_pay:
                gotoPay();

                break;
        }
    }

    /**
     * 去结算
     */
    private void gotoPay() {
        final List<Product> products = new ArrayList<>();//选中的商品
        for (Product product : productList) {
            if (product.isChecked) {
                products.add(product);
            }
        }
        if (products.size() == 0) {
            Utils.showToast(mContext, "至少选择一个");
            return;
        }
        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
        intent.putParcelableArrayListExtra("products", (ArrayList<? extends Parcelable>) products);
        startActivity(intent);

    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        shopCarAdapter = new ShopCarAdapter(mContext, data);
        mRecyclerView.setAdapter(shopCarAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
//        LogUtils.i("ShoppingCarFragment+onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
//        LogUtils.i("ShoppingCarFragment+onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        RxCarBus.get().unregister(this);//销毁
    }

    /**
     * 接收一个消息
     */
    @Subscribe
    public void flag(PostEvent postEvent) {
        String toPrice = totalPrice.getText().toString();
        if (TextUtils.isEmpty(toPrice)) {
            toPrice = "0";
        }
        if (postEvent.flag) {
            totalPrice.setText(postEvent.price.add(new BigDecimal(toPrice)) + "");
        } else {
            totalPrice.setText(new BigDecimal(toPrice).subtract(postEvent.price) + "");
        }

    }

    /**
     * 请求网络
     */
    private void requestData() {
        ShapreUtils.putParamCustomerId(map);
        fromNetwork.findCartByCustomer(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<Product>>(mContext) {
                    @Override
                    protected void myNext(ArrayList<Product> list) {
                        data.clear();
                        productList.clear();
                        data.addAll(list);
                        productList.addAll(list);
                        shopCarAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        data.clear();
        requestData();
        mRecyclerView.refreshComplete();

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        requestData();
        mRecyclerView.loadMoreComplete();
    }


}
