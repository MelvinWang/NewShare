package com.melvin.share.modelview.acti;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ProductCollectionAdapter;
import com.melvin.share.adapter.ScanHistoryAdapter;
import com.melvin.share.app.BaseApplication;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;
import com.melvin.share.view.RxSubscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/4
 * <p/>
 * 描述： 浏览记录页面ViewModel
 */
public class ScanHistoryViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ScanHistoryAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    private Button btnDelete;
    private TextView tvEdit;
    private TextView tvcancel;
    public List<BaseModel> data = new ArrayList<>();
    List<Product> listData = new ArrayList<>();

    public ScanHistoryViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot, TextView tvEdit, TextView tvcancel, Button btnDelete) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.btnDelete = btnDelete;
        this.tvEdit = tvEdit;
        this.tvcancel = tvcancel;
        adapter = new ScanHistoryAdapter(context, getData());

    }

    public void requestData(Map map) {
        fromNetwork.findRecords(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<Product>>(context) {
                    @Override
                    protected void myNext(ArrayList<Product> list) {
                        data.addAll(list);
                        listData.addAll(list);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {

                    }


                });

    }


    public void requestQueryData(Map map) {
    }

    public ScanHistoryAdapter getAdapter() {
        return adapter;
    }


    /**
     * 编辑
     *
     * @param v
     */
    public void edit(View v) {
        tvEdit.setVisibility(View.GONE);
        tvcancel.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);
        for (Product product : listData) {
            product.isShow = true;
        }
        adapter.notifyDataSetChanged();

    }

    /**
     * 取消
     *
     * @param v
     */
    public void cancel(View v) {
        tvcancel.setVisibility(View.GONE);
        tvEdit.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.GONE);
        for (Product product : listData) {
            product.isShow = false;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除
     *
     * @param v
     */
    public void delete(View v) {
        Map map = new HashMap();
        List<Product> list = new ArrayList<>();
        for (Product product : listData) {
            if (product.isChecked) {
                list.add(product);
            }
        }
//        fromNetwork.deleteRecord(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxSubscribe<BaseReturnModel>(context) {
//                    @Override
//                    protected void myNext(BaseReturnModel baseReturnModel) {
//                       if (baseReturnModel.success){
//
//                       }
//                        Utils.showToast(context,baseReturnModel.message);
//                    }
//
//                    @Override
//                    protected void myError(String message) {
//                        Utils.showToast(context,message);
//                    }
//                });


        data.removeAll(list);
        onRequestSuccess(data);
    }

}
