package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.WillUsableItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.WillUsableItemViewModel;

import java.util.List;


/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：即将可用返利Adapter
 */
public class WillUsableAdapter extends BaseAdapter<WillUsableAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public WillUsableAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WillUsableItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.will_usable_item,
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
        final WillUsableItemBinding binding;

        public BindingHolder(WillUsableItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User user) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new WillUsableItemViewModel(context, user));
            } else {
                binding.getViewModel().setEntity(user);
            }
        }
    }
}



