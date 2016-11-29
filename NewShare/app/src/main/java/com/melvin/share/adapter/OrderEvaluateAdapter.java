package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.OrderEvaluateItemBinding;
import com.melvin.share.databinding.SearchProductItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.OrderEvaluateItemViewModel;
import com.melvin.share.modelview.item.SearchProductItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述： 订单评价页面Adapter
 */
public class OrderEvaluateAdapter extends BaseAdapter<OrderEvaluateAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public OrderEvaluateAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public OrderEvaluateAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderEvaluateItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.order_evaluate_item,
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
        final OrderEvaluateItemBinding binding;

        public BindingHolder(OrderEvaluateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User user) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new OrderEvaluateItemViewModel(context, user));
            } else {
                binding.getViewModel().setEntity(user);
            }
        }
    }

}

