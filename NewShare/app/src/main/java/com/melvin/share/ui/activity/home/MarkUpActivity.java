package com.melvin.share.ui.activity.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityMarkupBinding;
import com.melvin.share.modelview.acti.MarkupViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/4
 * <p/>
 * 描述： 彩妆页面
 */
public class MarkUpActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityMarkupBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private MarkupViewModel markupViewModel;
    private Map map=new HashMap();
    private Map queryMap=new HashMap();
    private String id;
    RadioGroup mRadioGroup;
    private boolean priceClicked = false;//false代表价格向下查询，true代表价格向上查询
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_markup);
        mContext = this;
        id = getIntent().getStringExtra("id");
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        map.put("category.id",id);
        mRadioGroup = binding.mRadioGroup;
        //设置选项选中事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                queryMap.clear();
                queryMap.put("category.id",id);
                binding.price.setImageResource(R.mipmap.normal_price);
                switch (checkedId) {
                    case R.id.level:
                        priceClicked = false;
                        queryMap.put("level","1");
                        markupViewModel.requestQueryData(queryMap);
                        Utils.showToast(mContext, "level");
                        break;
                    case R.id.sale_total:
                        priceClicked = false;
                        queryMap.put("saleTotal","1");
                        markupViewModel.requestQueryData(queryMap);
                        Utils.showToast(mContext, "sale_total");
                        break;
                    case R.id.share_times:
                        priceClicked = false;
                        queryMap.put("shareTimes","1");
                        markupViewModel.requestQueryData(queryMap);
                        Utils.showToast(mContext, "share_times");
                        break;
                }
            }
        });


        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        markupViewModel = new MarkupViewModel(this, mRecyclerView);
        binding.setViewModel(markupViewModel);
        markupViewModel.requestData(map);
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
            queryMap.put("price",true);
            markupViewModel.requestQueryData(queryMap);
            Utils.showToast(mContext, "价格从高到低");
        } else {
            binding.price.setImageResource(R.mipmap.price_down);
            queryMap.put("price",false);
            markupViewModel.requestQueryData(queryMap);
            Utils.showToast(mContext, "价格从低到高");
        }
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        markupViewModel.requestData(map);
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
