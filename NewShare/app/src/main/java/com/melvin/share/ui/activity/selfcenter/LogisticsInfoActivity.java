package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityLogisticsInfoBinding;
import com.melvin.share.model.LogisticsModel;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.acti.LogisticsInfoViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.order.WaitPayOrderActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/9
 * <p/>
 * 描述： 查看物流页面
 */
public class LogisticsInfoActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityLogisticsInfoBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;

    private LogisticsInfoViewModel logisticsInfoViewModel;
    private String orderItemId;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logistics_info);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        orderItemId = getIntent().getStringExtra("orderItemId");
        LogUtils.i("orderItemId" + orderItemId);

        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        logisticsInfoViewModel = new LogisticsInfoViewModel(this, mRecyclerView);
        binding.setViewModel(logisticsInfoViewModel);
        findLogistByOrderItemId();
    }

    /**
     * 获取物流信息
     */
    private void findLogistByOrderItemId() {
        fromNetwork.findLogistByOrderItemId(orderItemId)
                .compose(new RxActivityHelper<CommonReturnModel<LogisticsModel>>().ioMain(LogisticsInfoActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel<LogisticsModel>>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel<LogisticsModel> bean) {
                        binding.orderNumber.setText("运单号："+bean.result.logist.no);
                        binding.company.setText("信息来源："+bean.result.logist.company);
                        logisticsInfoViewModel.requestData(bean.result.logist.list);

                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
