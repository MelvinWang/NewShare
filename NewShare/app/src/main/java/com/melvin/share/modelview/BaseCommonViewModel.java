package com.melvin.share.modelview;

import android.content.Context;
import android.content.SharedPreferences;

import com.melvin.share.network.NetworkUtil;

import retrofit2.Retrofit;


/**
 * Created Time: 2016/8/24.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：viewModel基类
 */
public abstract class BaseCommonViewModel {
    protected NetworkUtil.FromNetwork fromNetwork;
    protected Retrofit retrofit;
    protected Context context;
    protected SharedPreferences mPref;
    /**
     * @param context
     */
    public BaseCommonViewModel(Context context) {
        this.context = context;
        retrofit = NetworkUtil.getRetrofit();
        fromNetwork = retrofit.create(NetworkUtil.FromNetwork.class);
    }
}
