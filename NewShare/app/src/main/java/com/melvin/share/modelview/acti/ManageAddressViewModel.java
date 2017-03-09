package com.melvin.share.modelview.acti;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ManageAddressAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.selfcenter.ManageAddressActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.melvin.share.R.id.map;

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

    public ManageAddressViewModel(Context context, MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new ManageAddressAdapter(context, getData());
    }


    public void requestData() {
        fromNetwork.findAddressByCustomerId(ShapreUtils.getCustomerId())
                .compose(new RxActivityHelper<ArrayList<AddressBean>>().ioMain((ManageAddressActivity) context, true))
                .subscribe(new RxModelSubscribe<ArrayList<AddressBean>>(context, true) {
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


    public void requestQueryData() {
        fromNetwork.findAddressByCustomerId(ShapreUtils.getCustomerId())
                .compose(new RxActivityHelper<ArrayList<AddressBean>>().ioMain((ManageAddressActivity) context, true))
                .subscribe(new RxModelSubscribe<ArrayList<AddressBean>>(context, true) {
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
        map.put("addressId", bean.id);
        ShapreUtils.putParamCustomerId(map);
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.updateDefaultAddress(map)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain((ManageAddressActivity) context, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(context, commonReturnModel.message);
                        requestQueryData();
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
//        Map map = new HashMap();
//        map.put("id", bean.id);
        String[] arr = {bean.id};
        fromNetwork.deleteAddressByIds(arr)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain((ManageAddressActivity) context, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        data.remove(bean);
                        onRequestSuccess(data);
                        Utils.showToast(context, commonReturnModel.message);
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
