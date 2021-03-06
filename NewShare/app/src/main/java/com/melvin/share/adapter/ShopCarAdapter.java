package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.OrderCodeItemBinding;
import com.melvin.share.databinding.ShopCarItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.OrderCodeItemViewModel;
import com.melvin.share.modelview.item.ShopCarItemViewModel;

import java.util.List;


/**
 * Created Time: 2017/3/31.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：购物车Adapter
 */
public class ShopCarAdapter extends BaseAdapter<ShopCarAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public ShopCarAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShopCarItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.shop_car_item,
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
        final ShopCarItemBinding binding;

        public BindingHolder(ShopCarItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final Product product) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ShopCarItemViewModel(context, product));
            } else {
                binding.getViewModel().setEntity(product);
            }
        }
    }
}



