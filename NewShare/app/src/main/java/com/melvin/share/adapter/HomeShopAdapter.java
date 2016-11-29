package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.RecommendShopItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.modelview.item.HomeShopItemViewModel;

import java.util.List;


/**
 * Created Time: 2016/7/31.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：首页店铺推荐的Adapter
 */
public class HomeShopAdapter extends BaseAdapter<HomeShopAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public HomeShopAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecommendShopItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recommend_shop_item,
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
        final RecommendShopItemBinding binding;

        public BindingHolder(RecommendShopItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final ShopBean shopBean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new HomeShopItemViewModel(context, shopBean));
            } else {
                binding.getViewModel().setEntity(shopBean);
            }
        }
    }
}



