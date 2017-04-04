package com.melvin.share.ui.activity.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.RadioGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityCategoryBinding;
import com.melvin.share.modelview.acti.CategoryViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/4
 * <p/>
 * 描述： 分类查询
 */
public class CategoryActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityCategoryBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;

    private CategoryViewModel categoryViewModel;
    private String categoryId;
    private String categoryName;
    RadioGroup mRadioGroup;
    private boolean priceClicked = false;//false代表价格向下查询，true代表价格向上查询
    private Map queryMap;
    private int pageNo = 1;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        mContext = this;
        categoryId = getIntent().getStringExtra("categoryId");
        categoryName = getIntent().getStringExtra("categoryName");
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        queryMap = new HashMap();
        //设置标题
        binding.titleName.setText(categoryName);
        queryMap.put("categoryId", categoryId);

        mRadioGroup = binding.mRadioGroup;
        //设置选项选中事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                queryMap.clear();
                queryMap.put("categoryId", categoryId);
                binding.price.setImageResource(R.mipmap.normal_price);
                switch (checkedId) {
                    case R.id.level:
                        priceClicked = false;
                        queryMap.put("column", "ALL");
                        queryMap.put("order", "DESC");
                        categoryViewModel.requestData(queryMap);
                        break;
                    case R.id.sale_total:
                        priceClicked = false;
                        queryMap.put("column", "SALE");
                        queryMap.put("order", "DESC");
                        categoryViewModel.requestData(queryMap);
                        break;
                    case R.id.share_times:
                        priceClicked = false;
                        queryMap.put("column", "SHARE");
                        queryMap.put("order", "DESC");
                        categoryViewModel.requestData(queryMap);
                        break;
                }
            }
        });
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        categoryViewModel = new CategoryViewModel(this, mRecyclerView);
        binding.setViewModel(categoryViewModel);
        categoryViewModel.requestData(queryMap);
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
            queryMap.put("column", "PRICE");
            queryMap.put("order", "DESC");
            categoryViewModel.requestData(queryMap);
        } else {
            binding.price.setImageResource(R.mipmap.price_down);
            queryMap.put("column", "PRICE");
            queryMap.put("order", "ASC");
            categoryViewModel.requestData(queryMap);
        }
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        queryMap.put("pageNo", pageNo + "");
        categoryViewModel.requestData(queryMap);

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        queryMap.put("pageNo", pageNo + "");
        categoryViewModel.requestQueryData(queryMap);
    }
}
