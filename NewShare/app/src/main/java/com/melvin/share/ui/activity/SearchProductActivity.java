package com.melvin.share.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivitySearchProductBinding;
import com.melvin.share.model.SearchBean;
import com.melvin.share.modelview.acti.SearchProductViewModel;
import com.melvin.share.network.GlobalData;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/25
 * <p/>
 * 描述： 搜索出商品来的页面
 */
public class SearchProductActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivitySearchProductBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private SearchProductViewModel searchProductViewModel;
    private EditText searchEnter;
    RadioGroup mRadioGroup;
    private boolean priceClicked = false;//false代表价格向下查询，true代表价格向上查询
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_product);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        String keyWord = getIntent().getStringExtra("keyWord");
        searchEnter = binding.searchEnter;
        searchEnter.setText(keyWord);

        mRadioGroup = binding.mRadioGroup;
        //设置选项选中事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                binding.price.setImageResource(R.mipmap.normal_price);
                switch (checkedId) {
                    case R.id.level:
                        priceClicked = false;
                        Utils.showToast(mContext, "level");
                        break;
                    case R.id.sale_total:
                        priceClicked = false;
                        Utils.showToast(mContext, "sale_total");
                        break;
                    case R.id.share_times:
                        priceClicked = false;
                        Utils.showToast(mContext, "share_times");
                        break;
                }
            }
        });
        mRoot = binding.root;
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        searchProductViewModel = new SearchProductViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(searchProductViewModel);
        searchProductViewModel.requestData();
    }

    /**
     * 价格查询
     *
     * @param v
     */
    public void priceQuery(View v) {
        mRadioGroup.clearCheck();
        priceClicked = !priceClicked;
        if (priceClicked) {
            binding.price.setImageResource(R.mipmap.price_up);
            Utils.showToast(mContext, "价格从高到低");
        } else {
            binding.price.setImageResource(R.mipmap.price_down);
            Utils.showToast(mContext, "价格从低到高");
        }
    }


    public void search(View v) {
        String searchWord = searchEnter.getText().toString();
        if (TextUtils.isEmpty(searchWord)) {
            Utils.showToast(mContext, "请输入关键字");
            return;
        }
        SearchBean searchBean = new SearchBean(searchWord);
        GlobalData.recentSearchList.add(searchBean);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        searchProductViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
