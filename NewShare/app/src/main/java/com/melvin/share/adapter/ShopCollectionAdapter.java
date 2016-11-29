package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.SearchProductItemBinding;
import com.melvin.share.databinding.ShopCollectionItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.SearchProductItemViewModel;
import com.melvin.share.modelview.item.ShopCollectionItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/3
 * <p>
 * 描述：店铺收藏页面Adapter
 */
public class ShopCollectionAdapter extends BaseAdapter<ShopCollectionAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public ShopCollectionAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ShopCollectionAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShopCollectionItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.shop_collection_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((User) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final ShopCollectionItemBinding binding;

        public BindingHolder(ShopCollectionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User user) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ShopCollectionItemViewModel(context, user));
            } else {
                binding.getViewModel().setEntity(user);
            }
        }
    }

}

