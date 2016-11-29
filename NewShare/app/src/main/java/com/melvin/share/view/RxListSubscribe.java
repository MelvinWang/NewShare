package com.melvin.share.view;

import android.app.ProgressDialog;
import android.content.Context;

import com.melvin.share.app.BaseApplication;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Jam on 16-7-21
 * Description: 自定义Subscribe
 */
public abstract class RxListSubscribe<T> extends Subscriber<T> {
    private Context mContext;
    private String msg;
    private ProgressDialog m_pDialog;

    protected boolean showDialog() {
        return true;
    }

    /**
     * @param context context
     * @param msg     dialog message
     */
    public RxListSubscribe(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
    }

    /**
     * @param context context
     */
    public RxListSubscribe(Context context) {
        this(context, "请稍后...");
    }

    @Override
    public void onCompleted() {
        if (showDialog())
            m_pDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
//        Toast.makeText(mContext, "123", Toast.LENGTH_SHORT).show();
//        if (showDialog()) {
//            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
//                    .setTitleText(msg);
//            dialog.setCancelable(true);
//            dialog.setCanceledOnTouchOutside(true);
//            //点击取消的时候取消订阅
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    if(!isUnsubscribed()){
//                        unsubscribe();
//                    }
//                }
//            });
////            dialog.show();
//        }
        // 创建连接进度对话框
        m_pDialog = new ProgressDialog(mContext);
        m_pDialog.setMessage(msg);
        m_pDialog.setIndeterminate(false);
        m_pDialog.setCanceledOnTouchOutside(false);
        m_pDialog.show();
    }


    @Override
    public void onNext(T t) {
        myNext((ArrayList<T>) t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!BaseApplication.isNetworkAvailable(mContext)) { //这里自行替换判断网络的代码
            myError("网络不可用");
        } else {
            myError("请求失败，请稍后再试...");
        }
        if (showDialog())
            m_pDialog.dismiss();
    }

    protected abstract void     myNext(ArrayList<T> t);

    protected abstract void myError(String message);

}
