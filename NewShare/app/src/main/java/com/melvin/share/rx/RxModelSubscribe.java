package com.melvin.share.rx;

import android.content.Context;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.NetworkUtils;

import rx.Subscriber;

/**
 * Created Time: 2017/3/8.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：请求集合,对象数据返统一处理
 */
public abstract class RxModelSubscribe<T> extends Subscriber<T> {
    private Context mContext;
    private boolean showLoading;


    /**
     * @param context
     * @param showLoading 是否显示加载中
     */
    public RxModelSubscribe(Context context, boolean showLoading) {
        this.mContext = context;
        this.showLoading = showLoading;

    }

    @Override
    public void onCompleted() {
        if (showLoading) {
            ProgressDialogUtil.dismiss();
        }
    }

    @Override
    public void onNext(T t) {
        myNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetworkUtils.isConnected(mContext)) {
            myError("网络不可用");
        } else {
            LogUtils.e(e.toString());
            myError("网络连接超时，请稍后再试...");
        }
        if (showLoading) {
            ProgressDialogUtil.dismiss();
        }

    }

    protected abstract void myNext(T t);

    protected abstract void myError(String message);

}
