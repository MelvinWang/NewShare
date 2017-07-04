package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityDepositRecordBinding;
import com.melvin.share.modelview.acti.DepositRecordViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/7/4
 * <p/>
 * 描述： 提现记录
 */
public class DepositRecordActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityDepositRecordBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private DepositRecordViewModel depositRecordViewModel;
    private int pageNo = 1;
    private Map map;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deposit_record);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        map = new HashMap();
        map.put("pageNo", pageNo + "");
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        depositRecordViewModel = new DepositRecordViewModel(this, mRecyclerView);
        binding.setViewModel(depositRecordViewModel);
        depositRecordViewModel.requestData(map);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        map.put("pageNo", pageNo + "");
        depositRecordViewModel.requestData(map);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        map.put("pageNo", pageNo + "");
        depositRecordViewModel.requestQueryData(map);
    }

}
