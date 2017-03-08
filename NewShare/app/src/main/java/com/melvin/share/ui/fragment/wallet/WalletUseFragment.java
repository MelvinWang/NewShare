package com.melvin.share.ui.fragment.wallet;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.adapter.WalletMoneyAdapter;
import com.melvin.share.databinding.FragmentWalletUseBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.home.LocationModeSourceActivity;
import com.melvin.share.ui.activity.shopcar.DepositActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/11/29
 * <p/>
 * 描述：钱包可用
 */
public class WalletUseFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentWalletUseBinding binding;
    private Context mContext;
    private MyRecyclerView recyclerView;
    private WalletMoneyAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();
    private View root;
    private View headerView;
    private TextView sumMoneyView;
    private Button immediatelyUseButton;
    private Button depositButton;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_use, container, false);
        if (root == null) {
            mContext = getActivity();
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        recyclerView = binding.recyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        //头部
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = layoutInflater.inflate(R.layout.wallet_use_title, null, false);
        sumMoneyView = (TextView) headerView.findViewById(R.id.sum_money);
        immediatelyUseButton = (Button) headerView.findViewById(R.id.immediately_use);
        depositButton = (Button) headerView.findViewById(R.id.deposit);
        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, DepositActivity.class));
            }
        });
        recyclerView.addHeaderView(headerView);
        recyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setLoadingListener(this);
    }


    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        adpter = new WalletMoneyAdapter(mContext, dataList);
        recyclerView.setAdapter(adpter);
    }


    /**
     * 请求网络
     */
    private void requestData() {
        List list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.password = i + "";
            user.username = i + "";
            list.add(user);
        }
        dataList.addAll(list);
        adpter.notifyDataSetChanged();

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        dataList.clear();
        requestData();
        recyclerView.refreshComplete();

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        requestData();
        recyclerView.loadMoreComplete();
    }
}