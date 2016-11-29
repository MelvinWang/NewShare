package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ReceiveAddressItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.modelview.item.ManageAddressItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/24
 * <p>
 * 描述： 管理收货地址Adapter
 */
public class ManageAddressAdapter extends BaseAdapter<ManageAddressAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public ManageAddressAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ManageAddressAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ReceiveAddressItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.receive_address_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((AddressBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final ReceiveAddressItemBinding binding;

        public BindingHolder(ReceiveAddressItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final AddressBean addressBean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ManageAddressItemViewModel(context, addressBean));
            } else {
                binding.getViewModel().setEntity(addressBean);
            }
        }
    }

}

