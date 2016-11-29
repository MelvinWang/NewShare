package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ShopCarEditItemBinding;
import com.melvin.share.databinding.ShopCollectionItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.ShopCarEditItemViewModel;
import com.melvin.share.modelview.item.ShopCollectionItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/7
 * <p>
 * 描述：购物车编辑页面Adapter
 */
public class ShopCarEditAdapter extends BaseAdapter<ShopCarEditAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public ShopCarEditAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ShopCarEditAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShopCarEditItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.shop_car_edit_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((Product) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final ShopCarEditItemBinding binding;

        public BindingHolder(ShopCarEditItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final Product product) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ShopCarEditItemViewModel(context, product));
            } else {
                binding.getViewModel().setEntity(product);
            }
        }
    }

}

