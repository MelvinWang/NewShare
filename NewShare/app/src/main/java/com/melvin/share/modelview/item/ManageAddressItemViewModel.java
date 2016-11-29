package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.RxCommonBus;
import com.melvin.share.Utils.Utils;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.ui.activity.selfcenter.NewAddressActivity;

/**
 * Created Time: 2016/7/24.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：管理收货地址item的ViewModel
 */
public class ManageAddressItemViewModel extends BaseObservable {

    private AddressBean addressBean;
    private Context context;

    public ManageAddressItemViewModel(Context context, AddressBean addressBean) {
        this.addressBean = addressBean;
        this.context = context;
    }

    public String getName() {
        return addressBean.recever;
    }

    public String getPhone() {
        return addressBean.receverPhone;
    }

    public String getAddress() {
        return addressBean.area + addressBean.address;
    }

    public boolean getIsFocus() {
        return addressBean.defaultAddress;
    }

    /**
     * 设为默认地址
     *
     * @param view
     */
    public void setDefault(View view) {
        addressBean.flag = "1";
        RxCommonBus.get().post(addressBean);
    }

    /**
     * 修改
     *
     * @param view
     */
    public void onUpdate(View view) {
        Intent intent = new Intent(context, NewAddressActivity.class);
        intent.putExtra("addressBean", addressBean);
        context.startActivity(intent);
    }

    /**
     * 删除
     *
     * @param view
     */
    public void onDelete(View view) {
        addressBean.flag = "2";
        RxCommonBus.get().post(addressBean);
    }


    public void setEntity(AddressBean addressBean) {
        this.addressBean = addressBean;
        notifyChange();
    }
}
