package com.melvin.share.ui.fragment.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.databinding.FragmentAllOrderBinding;
import com.melvin.share.modelview.acti.WaitEvaluateViewModel;
import com.melvin.share.modelview.frag.AllOrderViewModel;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/3
 * <p>
 * 描述：全部订单
 */
public class AllOrderFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentAllOrderBinding binding;
    private Context mContext;
    private View root;
    private MyRecyclerView mRecyclerView;
    private AllOrderViewModel allOrderViewModel;
    private Map map;
    private int pageNo = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_order, container, false);
        if (root == null) {
            mContext = getActivity();
            initData();
            root = binding.getRoot();
        } else {
            ViewUtils.removeParent(root);
        }
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    /**
     * 请求数据
     */
    private void initData() {
        map = new HashMap();
        map.put("pageNo", pageNo);
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        allOrderViewModel = new AllOrderViewModel(mContext, AllOrderFragment.this, mRecyclerView);
        binding.setViewModel(allOrderViewModel);
//        map.put("orderStatus",null);
        allOrderViewModel.requestData(map);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        map.put("pageNo", pageNo);
        allOrderViewModel.requestData(map);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        map.put("pageNo", pageNo);
        allOrderViewModel.requestQueryData(map);
    }
}
