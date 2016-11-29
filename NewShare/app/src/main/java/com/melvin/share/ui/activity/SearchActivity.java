package com.melvin.share.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivitySearchBinding;
import com.melvin.share.model.SearchBean;
import com.melvin.share.modelview.acti.SearchViewModel;
import com.melvin.share.network.GlobalData;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/3
 * <p/>
 * 描述： 搜索页面
 */
public class SearchActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivitySearchBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private SearchViewModel searchViewModel;
    private EditText searchEnter;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        mRoot = binding.root;
        mRecyclerView = binding.recyclerView;
        searchEnter = binding.searchEnter;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        searchViewModel = new SearchViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(searchViewModel);
        searchViewModel.requestData();
    }

    public void search(View v) {
        String searchWord = searchEnter.getText().toString();
        if (TextUtils.isEmpty(searchWord)) {
            Utils.showToast(mContext, "请输入关键字");
            return;
        }
        SearchBean searchBean = new SearchBean(searchWord);
        GlobalData.recentSearchList.add(searchBean);
        Intent intent = new Intent(mContext, SearchProductActivity.class);
        intent.putExtra("keyWord", searchWord);
        startActivity(intent);
        finish();

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        searchViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
