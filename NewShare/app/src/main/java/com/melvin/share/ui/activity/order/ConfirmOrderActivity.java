package com.melvin.share.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityConfirmOrderBinding;
import com.melvin.share.dialog.PaySuccessDialog;
import com.melvin.share.model.Product;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.modelview.acti.ConfirmOrderViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.common.LoginActivity;
import com.melvin.share.ui.activity.selfcenter.ManageAddressActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarActivity;
import com.melvin.share.view.MyRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;

import static android.R.id.list;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/31
 * <p>
 * 描述： 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {

    private ActivityConfirmOrderBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private ConfirmOrderViewModel confirmOrderViewModel;
    private ArrayList<Product> products;
    private BigDecimal totalPriceBigcimal = new BigDecimal(0);
    private BigDecimal totalPostage = new BigDecimal(0);
    private AddressBean addressBean;
    private String stockId = "";
    private String postage = "";
    private String totalNum = "";
    private String cartIds = "";
    private boolean fromCat;//true代表购物车进入



    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_order);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        fromCat = getIntent().getBooleanExtra("fromCat", true);
        products = getIntent().getParcelableArrayListExtra("products");
        if (fromCat) {
            setTotalFee();
        } else {
            setOtherTotalFee();
        }
        getDefaultAddress();
        mRecyclerView = binding.recyclerView;

        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        confirmOrderViewModel = new ConfirmOrderViewModel(this, mRecyclerView);
        binding.setViewModel(confirmOrderViewModel);
        confirmOrderViewModel.requestData(products);
    }

    /**
     * 设置运费及总计费用 购物车
     */
    private void setTotalFee() {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (i == 0) {
                cartIds = cartIds + product.id;//这个时候的ID是购物车里带过来的ID
            } else {
                cartIds = cartIds + "," + product.id;
            }
            BigDecimal multiply = new BigDecimal((product.price)).multiply(new BigDecimal((product.productNum)));
            totalPriceBigcimal = totalPriceBigcimal.add(multiply);
            totalPostage = totalPostage.add(new BigDecimal((product.postage)));
        }


        binding.freight.setText("￥ " + totalPostage);
        binding.totalFee.setText("￥ " + totalPriceBigcimal);
    }

    /**
     * 设置运费及总计费用  直接购买
     */
    private void setOtherTotalFee() {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            stockId = product.stockId;
            postage = product.postage;
            totalNum = product.productNum;
            BigDecimal multiply = new BigDecimal((product.price)).multiply(new BigDecimal((product.productNum)));
            totalPriceBigcimal = totalPriceBigcimal.add(multiply);
            totalPostage = totalPostage.add(new BigDecimal((product.postage)));
        }
        binding.freight.setText("￥ " + totalPostage);
        binding.totalFee.setText("￥ " + totalPriceBigcimal);
    }


    /**
     * 获取默认地址
     */
    private void getDefaultAddress() {
        fromNetwork.findDefaultAddressByCustomerId(ShapreUtils.getCustomerId())
                .compose(new RxActivityHelper<AddressBean>().ioMain(ConfirmOrderActivity.this, true))
                .subscribe(new RxModelSubscribe<AddressBean>(mContext, true) {
                    @Override
                    protected void myNext(AddressBean bean) {
                        addressBean = bean;
                        if (bean != null) {
                            binding.name.setText(bean.receiver);
                            binding.phone.setText(bean.phone);
                            binding.address.setText(bean.province + bean.city + bean.area + bean.detailAddress);
                        }
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, "请检查是否有新默认地址...");
                    }
                });
    }

    /**
     * 提交订单
     *
     * @param v
     */
    public void goToPay(View v) {
        Intent intent = new Intent(mContext, WaitPayOrderActivity.class);
        String nameS = binding.name.getText().toString();
        String phoneS = binding.phone.getText().toString();
        String addressS = binding.address.getText().toString();
        intent.putExtra("name", nameS);
        intent.putExtra("phone", phoneS);
        intent.putExtra("address", addressS);
        intent.putExtra("cartIds", cartIds);
        intent.putExtra("stockId", stockId);
        intent.putExtra("postage", postage);
        intent.putExtra("fromCat", fromCat);
        intent.putExtra("totalNum", totalNum);
        startActivity(intent);
        if (fromCat) {
            ShoppingCarActivity.updateFlag = true;
            finish();
        }
    }

    /**
     * 修改默认地址
     *
     * @param view
     */
    public void updateAddress(View view) {
        Intent intent = new Intent();
        intent.setClass(mContext, ManageAddressActivity.class);
        intent.putExtra("orderAddress", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Bundle bundle = data.getBundleExtra("bean");
            binding.name.setText(bundle.getString("name"));
            binding.phone.setText(bundle.getString("phone"));
            binding.address.setText(bundle.getString("address"));

        }

    }
}
