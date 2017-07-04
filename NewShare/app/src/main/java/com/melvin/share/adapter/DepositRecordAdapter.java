package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.DepositRecordItemBinding;
import com.melvin.share.databinding.ReceiveAddressItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.DepositRecordBean;
import com.melvin.share.modelview.item.DepositRecordItemViewModel;
import com.melvin.share.modelview.item.ManageAddressItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/7/4
 * <p>
 * 描述： 提现记录Adapter
 */
public class DepositRecordAdapter extends BaseAdapter<DepositRecordAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public DepositRecordAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public DepositRecordAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DepositRecordItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.deposit_record_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((DepositRecordBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final DepositRecordItemBinding binding;

        public BindingHolder(DepositRecordItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final DepositRecordBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new DepositRecordItemViewModel(context, bean));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }

}

