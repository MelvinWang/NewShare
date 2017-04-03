package com.melvin.share.ui.activity.shopcar;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.rx.RxCarBus;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ShopCarAdapter;
import com.melvin.share.databinding.ActivityShoppingCarBinding;
import com.melvin.share.event.PostEvent;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.order.ConfirmOrderActivity;
import com.melvin.share.view.MyRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/3/31
 * <p/>
 * 描述：购物车
 */
public class ShoppingCarActivity extends BaseActivity implements MyRecyclerView.LoadingListener, View.OnClickListener {

    private ActivityShoppingCarBinding binding;
    private Context mContext;
    private MyRecyclerView mRecyclerView;
    private ShopCarAdapter shopCarAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private Map map = new HashMap();
    public static boolean updateFlag = false;
    private TextView totalPrice;
    private BigDecimal totalPriceBigcimal;
    private int pageNo = 1;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_car);
        mContext = this;
        initWindow();
        RxCarBus.get().register(this); //注册
        initToolbar(binding.toolbar);
        initData();
        initAdapter();
        requestData(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (updateFlag) {
            updateFlag = false;
            requestData(true);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        map.put("pageNo", pageNo + "");
        totalPrice = binding.totalPrice;
        binding.allChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalPriceBigcimal = new BigDecimal(0);
                    for (Product product : productList) {
                        product.isChecked = isChecked;
                        BigDecimal multiply = new BigDecimal((product.price)).multiply(new BigDecimal((product.productNum)));
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
    protected void onDestroy() {
        super.onDestroy();
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
     * 请求网络 true代表刷新
     */
    private void requestData(final boolean flag) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findCartByCustomer(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain(ShoppingCarActivity.this, true))
                .subscribe(new RxModelSubscribe<CommonList<Product>>(mContext, true) {
                    @Override
                    protected void myNext(CommonList<Product> bean) {
                        if (flag) {
                            data.clear();
                            productList.clear();
                        }
                        data.addAll(bean.rows);
                        productList.addAll(bean.rows);
                        shopCarAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                        mRecyclerView.loadMoreComplete();
                    }

                    @Override
                    protected void myError(String message) {
                        mRecyclerView.refreshComplete();
                        mRecyclerView.loadMoreComplete();
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        map.put("pageNo", pageNo + "");
        requestData(true);

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        map.put("pageNo", pageNo + "");
        requestData(false);

    }

}
