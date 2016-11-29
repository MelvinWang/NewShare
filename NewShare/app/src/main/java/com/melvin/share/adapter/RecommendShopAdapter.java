package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.HomeItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.RecommendShopItemViewModel;

import java.util.List;


/**
 * Created Time: 2016/10/1.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：分享热度的Adapter
 */
public class RecommendShopAdapter extends BaseAdapter<RecommendShopAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public RecommendShopAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.home_item,
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
        final HomeItemBinding binding;

        public BindingHolder(HomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User model) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new RecommendShopItemViewModel(context, model));
            } else {
                binding.getViewModel().setEntity(model);
            }
        }
    }
}



