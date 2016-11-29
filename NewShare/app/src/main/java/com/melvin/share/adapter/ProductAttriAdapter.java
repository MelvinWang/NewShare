package com.melvin.share.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.model.serverReturn.ProductDetailBean;

import java.util.List;


/**
 * Created Time: 2016/7/25.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品详细属性信息Adapter
 */
public class ProductAttriAdapter extends BaseAdapter<ProductAttriAdapter.BindingHolder> {
    private List<ProductDetailBean.AttributesBean.AttributeValuesBean> list;
    private Context context;
    private int layoutPosition;
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public ProductAttriAdapter(Context context, List<ProductDetailBean.AttributesBean.AttributeValuesBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //当viewholder创建时的回调
        View view = View.inflate(context, R.layout.product_attri_item, null);
        return new BindingHolder(view);
    }
    @Override
    public void onBindViewHolder(final BindingHolder holder, int position) {
        //当viewholder和数据绑定时的回调
        holder.mButton.setText(list.get(position).attributeValueName);
        holder.itemView.setTag(list.get(position).id+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前点击的位置
                layoutPosition = holder.getLayoutPosition();
                notifyDataSetChanged();
                mOnItemClickListener.onItemClick(holder.itemView, (String) holder.itemView.getTag(), layoutPosition);

            }
        });
        //更改状态
        if (position == layoutPosition) {
            holder.mButton.setBackground(context.getResources().getDrawable(R.drawable.btn_redrectangle_withradus));
            holder.mButton.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.mButton.setBackground(context.getResources().getDrawable(R.drawable.btn_rectangle_withradus));
            holder.mButton.setTextColor(context.getResources().getColor(R.color.app_common_red));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        private TextView mButton;
        public BindingHolder(View itemView) {
            super(itemView);
            mButton = (TextView) itemView.findViewById(R.id.value);
        }
    }
}



