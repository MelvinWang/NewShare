package com.melvin.share.modelview;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.LinearLayoutManager;

import com.melvin.share.BR;
import com.melvin.share.network.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;


/**
 * Created Time: 2016/7/24.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：viewModel基类
 */
public abstract class BaseRecyclerViewModel<T> extends BaseObservable {
    public LinearLayoutManager linearLayoutManager;
    protected NetworkUtil.FromNetwork fromNetwork;
    protected Retrofit retrofit;
    @Bindable
    private List<T> data = new ArrayList<>();//

    /**
     * @param context
     */
    public BaseRecyclerViewModel(Context context) {
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        retrofit = NetworkUtil.getRetrofit();
        fromNetwork = retrofit.create(NetworkUtil.FromNetwork.class);
    }

    @Bindable
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }

    public void onRequestSuccess(List<T> tList) {
        data.clear();
        data.addAll(tList);
        notifyPropertyChanged(BR.data);

    }
}
