package com.melvin.share.ui.activity.common;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.melvin.share.R;
import com.melvin.share.Utils.StatusBarUtils;
import com.melvin.share.network.NetworkUtil;
import com.melvin.share.rx.RxActivity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Retrofit;


/**
 * Created Time: 2016/7/17.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：基类
 */
public abstract class BaseActivity extends RxActivity {
    // 管理运行的所有的activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    protected Retrofit retrofit;
    protected NetworkUtil.FromNetwork fromNetwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = NetworkUtil.getRetrofit();
        fromNetwork = retrofit.create(NetworkUtil.FromNetwork.class);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        initView();
        initMapView(savedInstanceState);
    }

    //一定得重写，初始化页面
    protected abstract void initView();

    //地图
    protected void initMapView(Bundle savedInstanceState) {
    }




    /**
     * 沉浸式状态栏
     */
    protected void initWindow() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.status_bar));
    }

    /**
     * 初始toolbar
     */
    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.fanhui);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * toolbar上菜单的选择事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    public void killAll() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void killApp() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        Collections.reverse(copy);
        for (BaseActivity activity : copy) {
            activity.finish();
        }
//         杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
