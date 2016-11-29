package com.melvin.share.ui.fragment.rebate;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.adapter.UsableAdapter;
import com.melvin.share.databinding.FragmentUsableBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.shopcar.DepositActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/21
 * <p>
 * 描述：可用返利
 */
public class UsableFragment extends BaseFragment implements View.OnClickListener {

    private FragmentUsableBinding binding;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private UsableAdapter usableAdapter;
    private List<BaseModel> data = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_usable, container, false);
        mContext = getActivity();
        initData();
        initAdapter();
        return binding.getRoot();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRecyclerView = binding.recyclerView;
        binding.deposit.setOnClickListener(this);
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        usableAdapter = new UsableAdapter(mContext, data);
        mRecyclerView.setAdapter(usableAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deposit:
                startActivity(new Intent(mContext, DepositActivity.class));
                break;
        }
    }

    /**
     * 请求网络
     */
    private void requestData() {
        List list = new ArrayList<>();
        User user = new User();
        user.password = "1";
        user.username = "2";
        list.add(user);
        User user1 = new User();
        user1.password = "3";
        user1.username = "4";
        list.add(user1);
        data.addAll(list);
        usableAdapter.notifyDataSetChanged();

    }


}