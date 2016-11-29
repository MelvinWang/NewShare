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
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/11/29
 * <p>
 * 描述：全部订单
 */
public class AllOrderFragment extends BaseFragment implements MyRecyclerView.LoadingListener  {

    private FragmentAllOrderBinding binding;
    private Context mContext;
    private View root;
    private MyRecyclerView mRecyclerView;
    private WaitEvaluateViewModel waitEvaluateViewModel;
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

    /**
     * 请求数据
     */
    private void initData() {
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        waitEvaluateViewModel = new WaitEvaluateViewModel(mContext, mRecyclerView);
        binding.setViewModel(waitEvaluateViewModel);
        waitEvaluateViewModel.requestData();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
