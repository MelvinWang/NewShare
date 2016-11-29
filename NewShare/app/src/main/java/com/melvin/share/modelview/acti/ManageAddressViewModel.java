package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ManageAddressAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.AddressBean;
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
 * Data： 2016/7/24
 * <p/>
 * 描述： 管理收货地址ViewModel
 */
public class ManageAddressViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ManageAddressAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public ManageAddressViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new ManageAddressAdapter(context, getData());
    }


    public void requestData(Map map) {
        fromNetwork.findByCustomer(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<AddressBean>>(context) {
                    @Override
                    protected void myNext(ArrayList<AddressBean> list) {
                        data.addAll(list);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }


                });
    }


    public void requestQueryData(Map map) {
        fromNetwork.findByCustomer(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<AddressBean>>(context) {
                    @Override
                    protected void myNext(ArrayList<AddressBean> list) {
                        data.clear();
                        data.addAll(list);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }


                });
    }

    /**
     * 设置为默认地址
     *
     * @param bean
     */
    public void setDefaultAddress(AddressBean bean) {
        Map map = new HashMap();
        map.put("id", bean.id);
        fromNetwork.persistAddress(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<AddressBean>>(context) {
                    @Override
                    protected void myNext(ArrayList<AddressBean> list) {
                        data.addAll(list);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }


                });
    }

    /**
     * 删除地址
     *
     * @param bean
     */
    public void deleteAddress(final AddressBean bean) {
        Map map = new HashMap();
        map.put("id", bean.id);
        fromNetwork.delete(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<BaseReturnModel>(context) {
                    @Override
                    protected void myNext(BaseReturnModel baseReturnModel) {
                        if (baseReturnModel.success) {
                            data.remove(bean);
                            onRequestSuccess(data);
                        }
                        Utils.showToast(context, baseReturnModel.message);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }


    public ManageAddressAdapter getAdapter() {
        return adapter;
    }


}
