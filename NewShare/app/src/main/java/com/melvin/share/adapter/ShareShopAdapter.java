package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ShareShopItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.modelview.item.ShareShopItemViewModel;

import java.util.List;


/**
 * Created Time: 2016/10/1.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：分享热度的Adapter
 */
public class ShareShopAdapter extends BaseAdapter<ShareShopAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public ShareShopAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShareShopItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.share_shop_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((ShopBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final ShareShopItemBinding binding;

        public BindingHolder(ShareShopItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final ShopBean shopBean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ShareShopItemViewModel(context, shopBean));
            } else {
                binding.getViewModel().setEntity(shopBean);
            }
        }
    }
}



