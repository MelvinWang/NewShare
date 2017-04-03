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
import com.melvin.share.modelview.frag.AllOrderViewModel;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/3
 * <p>
 * 描述：待付款
 */
public class WaitPayOrderFragment extends BaseFragment implements MyRecyclerView.LoadingListener  {

    private FragmentAllOrderBinding binding;
    private Context mContext;
    private View root;
    private MyRecyclerView mRecyclerView;
    private AllOrderViewModel allOrderViewModel;
    private Map map;
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
        map=new HashMap();
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        allOrderViewModel= new AllOrderViewModel(mContext, WaitPayOrderFragment.this,mRecyclerView);
        binding.setViewModel(allOrderViewModel);
        map.put("orderStatus","1");
        allOrderViewModel.requestData(map);
    }

    @Override
    public void onRefresh() {
        allOrderViewModel.requestData(map);
    }

    @Override
    public void onLoadMore() {

    }
}
