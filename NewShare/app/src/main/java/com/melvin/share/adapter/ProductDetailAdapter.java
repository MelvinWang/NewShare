package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ProductDetailItemBinding;
import com.melvin.share.databinding.ProductInfoimgItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.serverReturn.ImgUrlBean;
import com.melvin.share.model.serverReturn.ProductDetailBean;
import com.melvin.share.modelview.item.ProductDetailItemViewModel;
import com.melvin.share.modelview.item.ProductInfoimgItemViewModel;

import java.util.List;


/**
 * Created Time: 2016/7/25.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品详细里的详细信息Adapter
 */
public class ProductDetailAdapter extends BaseAdapter<ProductDetailAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public ProductDetailAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductDetailItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.product_detail_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((ProductDetailBean.DetailsBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final ProductDetailItemBinding binding;

        public BindingHolder(ProductDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final ProductDetailBean.DetailsBean detailsBean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ProductDetailItemViewModel(context, detailsBean));
            } else {
                binding.getViewModel().setEntity(detailsBean);
            }
        }
    }
}



