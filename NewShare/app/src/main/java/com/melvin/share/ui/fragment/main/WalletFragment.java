package com.melvin.share.ui.fragment.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.WalletAdapter;
import com.melvin.share.databinding.FragmentWalletBinding;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/11/29
 * <p/>
 * 描述：钱包
 */
public class WalletFragment extends BaseFragment {
    private FragmentWalletBinding binding;
    private Context mContext;
    private View root;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false);
        if (root == null) {
            mContext = getActivity();
            LogUtils.i("WalletFragment+initView");
            initTable();
            root = binding.getRoot();
        } else {
            ViewUtils.removeParent(root);// 移除frameLayout之前的爹
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * 初始化标题,绑定
     */
    private void initTable() {
        WalletAdapter viewPagerAdapter = new WalletAdapter(getChildFragmentManager(), getActivity());
        binding.viewpager.setAdapter(viewPagerAdapter);//设置适配器
        TabLayout mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }
}
