package com.melvin.share.ui.activity.order;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.AllOrderAdapter;
import com.melvin.share.databinding.ActivityAllOrderBinding;
import com.melvin.share.model.AllOrderBusFlag;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxCommonBus;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created Time: 2016/11/29.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：全部订单
 */
public class AllOrderActivity extends BaseActivity {


    private ActivityAllOrderBinding binding;
    private Context mContext;
    public TabLayout mTabLayout;
    private ViewPager viewpager;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_order);
        mContext = this;
        initWindow();
        RxCommonBus.get().register(this);
        initToolbar(binding.toolbar);
        initData();
    }

    /**
     * 初始化标题,绑定
     */
    protected void initData() {
        AllOrderAdapter viewPagerAdapter = new AllOrderAdapter(getSupportFragmentManager());
        viewpager = binding.viewpager;
        viewpager.setAdapter(viewPagerAdapter);//设置适配器
        mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxCommonBus.get().unregister(this);//销毁
    }

    @Subscribe
    public void getCode(AllOrderBusFlag allOrderBusFlag) {
        if (allOrderBusFlag.flagId == 1) {//订单确认收货
            Map map = new HashMap();
            map.put("orderId", allOrderBusFlag.id);
            ShapreUtils.putParamCustomerId(map);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
            fromNetwork.makeSureOrderReceived(jsonObject)
                    .compose(new RxActivityHelper<CommonReturnModel>().ioMain(AllOrderActivity.this, true))
                    .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                        @Override
                        protected void myNext(CommonReturnModel bean) {
                            Utils.showToast(mContext, bean.message);
                        }

                        @Override
                        protected void myError(String message) {
                            Utils.showToast(mContext, message);
                        }
                    });
        }

    }
}
