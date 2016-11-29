package com.melvin.share.ui.activity.shopcar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.databinding.ActivityConfirmOrderBinding;
import com.melvin.share.dialog.PaySuccessDialog;
import com.melvin.share.model.Product;
import com.melvin.share.modelview.acti.ConfirmOrderViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/3
 * <p>
 * 描述： 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private ActivityConfirmOrderBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private ConfirmOrderViewModel confirmOrderViewModel;
    private ArrayList<Product> products;
    private BigDecimal totalPriceBigcimal = new BigDecimal(0);

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_order);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        products = getIntent().getParcelableArrayListExtra("products");
        setTotalFee();
        mRoot = binding.root;
        mRecyclerView = binding.recyclerView;
        binding.aliPay.setOnCheckedChangeListener(this);
        binding.wechatPay.setOnCheckedChangeListener(this);

        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        confirmOrderViewModel = new ConfirmOrderViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(confirmOrderViewModel);
        confirmOrderViewModel.requestData(products);
    }

    /**
     * 设置运费及总计费用
     */
    private void setTotalFee() {
        for (Product product : products) {
            BigDecimal multiply = new BigDecimal((product.price)).multiply(new BigDecimal((product.productNumber)));
            totalPriceBigcimal = totalPriceBigcimal.add(multiply);
        }
        binding.totalFee.setText("￥ " + totalPriceBigcimal);
    }

    public void goToPay(View v) {
        final PaySuccessDialog dialog = new PaySuccessDialog(mContext);
        dialog.setContentView(null);
        dialog.show();
    }

    /**
     * 支付宝及微信支付
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.ali_pay:
                if (isChecked && binding.wechatPay.isChecked()) {
                    binding.wechatPay.setChecked(false);
                }
                break;
            case R.id.wechat_pay:
                if (isChecked && binding.aliPay.isChecked()) {
                    binding.aliPay.setChecked(false);
                }
                break;
        }
    }
}
