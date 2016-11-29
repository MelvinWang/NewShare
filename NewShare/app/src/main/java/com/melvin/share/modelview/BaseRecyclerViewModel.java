package com.melvin.share.modelview;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.melvin.share.BR;
import com.melvin.share.network.NetworkUtil;
import com.melvin.share.view.DynamicBox;

import java.util.ArrayList;
import java.util.List;

import retrofit.Retrofit;

/**
 * Created Time: 2016/7/24.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：viewModel基类
 */
public abstract class BaseRecyclerViewModel<T> extends BaseObservable {
    public LinearLayoutManager linearLayoutManager;
    protected DynamicBox box;
    protected NetworkUtil.FromNetwork fromNetwork;
    protected Retrofit retrofit;
    @Bindable
    private List<T> data = new ArrayList<>();//

    /**
     * @param context
     * @param mRoot   每个view的根布局对象
     */
    public BaseRecyclerViewModel(Context context, View mRoot) {
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        retrofit = NetworkUtil.getRetrofit();
        fromNetwork = retrofit.create(NetworkUtil.FromNetwork.class);
        box = new DynamicBox(context, mRoot);

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
