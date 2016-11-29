package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.LogisticsInfoItemBinding;
import com.melvin.share.databinding.ReceiveAddressItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.LogisticsInfoItemViewModel;
import com.melvin.share.modelview.item.ManageAddressItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/7
 * <p>
 * 描述： 查看物流页面Adapter
 */
public class LogisticsInfoAdapter extends BaseAdapter<LogisticsInfoAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public LogisticsInfoAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public LogisticsInfoAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogisticsInfoItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.logistics_info_item,
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
        final LogisticsInfoItemBinding binding;

        public BindingHolder(LogisticsInfoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User user) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new LogisticsInfoItemViewModel(context, user));
            } else {
                binding.getViewModel().setEntity(user);
            }
        }
    }

}

