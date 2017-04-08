package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.OrderInformationItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.modelview.item.OrderInformationItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/8
 * <p>
 * 描述： 订单信息Adapter
 */
public class OrderInformationAdapter extends BaseAdapter<OrderInformationAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public OrderInformationAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public OrderInformationAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderInformationItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.order_information_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((WaitPayOrderInfo.OrderBean.OrderItemResponsesBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final OrderInformationItemBinding binding;

        public BindingHolder(OrderInformationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new OrderInformationItemViewModel(context, bean));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }

}

