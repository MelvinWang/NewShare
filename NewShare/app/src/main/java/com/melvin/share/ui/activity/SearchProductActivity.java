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

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/4
 * <p/>
 * 描述： 搜索出商品来的页面
 */
public class SearchProductActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivitySearchProductBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private SearchProductViewModel searchProductViewModel;
    private EditText searchEnter;
    RadioGroup mRadioGroup;
    private boolean priceClicked = false;//false代表价格向下查询，true代表价格向上查询
    private String keyWord="";
    private Map queryMap;
    private int pageNo = 1;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_product);
        mContext = this;
        initWindow();
        keyWord = getIntent().getStringExtra("keyWord");
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        queryMap = new HashMap();
        searchEnter = binding.searchEnter;
        searchEnter.setText(keyWord);

        queryMap.put("keywords", keyWord);

        mRadioGroup = binding.mRadioGroup;
        //设置选项选中事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                queryMap.clear();
                queryMap.put("keywords", keyWord);
                binding.price.setImageResource(R.mipmap.normal_price);
                switch (checkedId) {
                    case R.id.level:
                        priceClicked = false;
                        queryMap.put("column", "ALL");
                        queryMap.put("order", "DESC");
                        searchProductViewModel.requestData(queryMap);
                        break;
                    case R.id.sale_total:
                        priceClicked = false;
                        queryMap.put("column", "SALE");
                        queryMap.put("order", "DESC");
                        searchProductViewModel.requestData(queryMap);
                        break;
                    case R.id.share_times:
                        priceClicked = false;
                        queryMap.put("column", "SHARE");
                        queryMap.put("order", "DESC");
                        searchProductViewModel.requestData(queryMap);
                        break;
                }
            }
        });
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        searchProductViewModel = new SearchProductViewModel(this, mRecyclerView);
        binding.setViewModel(searchProductViewModel);
        searchProductViewModel.requestData(queryMap);
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
            searchProductViewModel.requestData(queryMap);
        } else {
            binding.price.setImageResource(R.mipmap.price_down);
            queryMap.put("column", "PRICE");
            queryMap.put("order", "ASC");
            searchProductViewModel.requestData(queryMap);
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

        queryMap.clear();
        queryMap.put("keywords", keyWord);
        searchProductViewModel.requestData(queryMap);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        queryMap.put("pageNo", pageNo + "");
        searchProductViewModel.requestData(queryMap);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        queryMap.put("pageNo", pageNo + "");
        searchProductViewModel.requestQueryData(queryMap);
    }
}
