package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.WaitSendProductItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.WaitSendproductItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/6
 * <p>
 * 描述：待发货页面Adapter
 */
public class WaitSendProductAdapter extends BaseAdapter<WaitSendProductAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public WaitSendProductAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public WaitSendProductAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WaitSendProductItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.wait_send_product_item,
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
        final WaitSendProductItemBinding binding;

        public BindingHolder(WaitSendProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User user) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new WaitSendproductItemViewModel(context, user));
            } else {
                binding.getViewModel().setEntity(user);
            }
        }
    }

}

