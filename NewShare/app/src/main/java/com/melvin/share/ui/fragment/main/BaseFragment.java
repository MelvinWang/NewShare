package com.melvin.share.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.share.network.NetworkUtil;

import retrofit.Retrofit;


/**
 * Created Time: 2016/7/17.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能： Fragement基类
 */
public abstract class BaseFragment extends Fragment {
    protected Retrofit retrofit;
    protected NetworkUtil.FromNetwork fromNetwork;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        retrofit = NetworkUtil.getRetrofit();
        fromNetwork = retrofit.create(NetworkUtil.FromNetwork.class);
        return initView(inflater,container);

    }

    //返回页面
    protected abstract View initView(LayoutInflater inflater, ViewGroup container);



}
