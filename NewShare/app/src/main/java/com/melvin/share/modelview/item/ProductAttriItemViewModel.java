package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.serverReturn.ImgUrlBean;
import com.melvin.share.model.serverReturn.ProductDetailBean;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品item的ViewModel
 */
public class ProductAttriItemViewModel extends BaseObservable {

    private ProductDetailBean.AttributesBean.AttributeValuesBean attributeValuesBean;
    private Context context;

    public ProductAttriItemViewModel(Context context, ProductDetailBean.AttributesBean.AttributeValuesBean attributeValuesBean) {
        this.attributeValuesBean = attributeValuesBean;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getAttriName() {
        return attributeValuesBean.attributeValueName;
    }

    public void setEntity(ProductDetailBean.AttributesBean.AttributeValuesBean attributeValuesBean) {
        this.attributeValuesBean = attributeValuesBean;
        notifyChange();
    }
}
