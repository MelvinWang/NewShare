package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.LinearLayout;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.RxCommonBus;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.databinding.ActivityReceiveAddressBinding;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.modelview.acti.ManageAddressViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/24
 * <p/>
 * 描述： 管理收货地址
 */
public class ManageAddressActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityReceiveAddressBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private ManageAddressViewModel manageAddressViewModel;
    private Map map;
    public static boolean saveOrUpdate = false;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_receive_address);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        RxCommonBus.get().register(this); //注册
        map = new HashMap();
        //     map.put("currentPage", "1");
        ShapreUtils.putParamCustomerDotId(map);
        mRoot = binding.root;
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        manageAddressViewModel = new ManageAddressViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(manageAddressViewModel);
        manageAddressViewModel.requestData(map);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        manageAddressViewModel.requestQueryData(map);
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (saveOrUpdate) {
            manageAddressViewModel.requestQueryData(map);
            saveOrUpdate = false;
        }
    }

    /**
     * 新增收货地址
     *
     * @param v
     */
    public void onNewAddress(View v) {
        startActivity(new Intent(mContext, NewAddressActivity.class));
    }


    /**
     * @param bean 地址
     */
    @Subscribe()
    public void operateorAddress(AddressBean bean) {
        if ("1".equals(bean.flag)) {
            manageAddressViewModel.setDefaultAddress(bean);
        } else if ("2".equals(bean.flag)) {
            manageAddressViewModel.deleteAddress(bean);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxCommonBus.get().unregister(this);
    }
}
