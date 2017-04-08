package com.melvin.share.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityOrderEvaluateBinding;
import com.melvin.share.model.Evaluation;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.acti.OrderEvaluateViewModel;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarActivity;
import com.melvin.share.ui.fragment.productinfo.ProductEvaluateFragment;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RatingBar;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.id.map;
import static com.melvin.share.R.id.ratingbar;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/8
 * <p/>
 * 描述： 订单评价页面
 */
public class OderEvaluateActivity extends BaseActivity {

    private ActivityOrderEvaluateBinding binding;
    private Context mContext = null;
    private int starCount;
    private WaitPayOrderInfo.OrderBean.OrderItemResponsesBean orderItemResponsesBean;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_evaluate);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        binding.ratingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int var1) {
                starCount = var1;
            }
        });


        orderItemResponsesBean = getIntent().getParcelableExtra("orderItemResponsesBean");
        if (orderItemResponsesBean != null) {
            String[] split = orderItemResponsesBean.mainPicture.split("\\|");
            if (split != null && split.length >= 1) {
                String url = GlobalUrl.SERVICE_URL + split[0];
                Glide.with(mContext)
                        .load(url)
                        .placeholder(R.mipmap.logo)
                        .centerCrop()
                        .into(binding.image);
            }
            binding.name.setText(orderItemResponsesBean.productName);
        }

    }


    public void submit(View view) {
        String contents = binding.content.getText().toString();
        if (TextUtils.isEmpty(contents)) {
            Utils.showToast(mContext, "请评价");
        }
        if (starCount == 0) {
            Utils.showToast(mContext, "请评分");
        }

        Map map = new HashMap();
        map.put("orderItemId", orderItemResponsesBean.id);
        map.put("startlevel", starCount);
        map.put("picture", orderItemResponsesBean.mainPicture);
        map.put("content", contents);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.insertOderItemEvaluation(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(OderEvaluateActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);
                        finish();

                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });


    }
}
