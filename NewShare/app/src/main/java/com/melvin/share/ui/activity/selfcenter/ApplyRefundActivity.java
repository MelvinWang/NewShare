package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.event.PostType;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxCommonBus;
import com.melvin.share.databinding.ActivityApplyRefundBinding;
import com.melvin.share.dialog.RefundDialog;
import com.melvin.share.popwindow.RefundReasonPopupWindow;
import com.melvin.share.popwindow.RefundTypePopupWindow;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.order.WaitSendProductOrderInformationActivity;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.phoneNumber;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/5/18
 * <p>
 * 描述： 申请退款
 */
public class ApplyRefundActivity extends BaseActivity {
    private ActivityApplyRefundBinding binding;
    private Context mContext = null;
    private RefundTypePopupWindow refundTypePopupWindow;
    private RefundReasonPopupWindow refundReasonPopupWindow;
    private String refundType;
    private String refundReason;
    private String refundTypeId;//1代表退货，2代表换货
    private TextView refundTypeTV;
    private TextView refundReasonTV;
    private EditText refundNumber;
    private String orderItemId;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_refund);
        mContext = this;
        refundTypePopupWindow = new RefundTypePopupWindow(this, itemsOnClick);
        refundReasonPopupWindow = new RefundReasonPopupWindow(this, itemsOnClick);
        RxCommonBus.get().register(this); //注册
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        orderItemId = getIntent().getStringExtra("orderItemId");
        refundTypeTV = binding.refundType;
        refundReasonTV = binding.refundReason;
        refundNumber = binding.refundNumber;

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            refundTypePopupWindow.dismiss();
            refundReasonPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.refund_type_cancel:
                    refundTypePopupWindow.dismiss();
                    break;
                case R.id.refund_type_confirm:
                    binding.refundType.setText(refundType);
                    break;
                case R.id.refund_reason_cancel:
                    refundReasonPopupWindow.dismiss();
                    break;
                case R.id.refund_reason_confirm:
                    binding.refundReason.setText(refundType);
                    break;
            }

        }

    };

    @Subscribe
    public void refundTypeSelect(PostType postType) {
        if (postType.type == 1) {
            this.refundType = postType.typeName;
            refundTypeId=postType.typeId;
        } else {
            this.refundReason = postType.typeName;
        }
    }


    /**
     * 选择退款类型
     */
    public void selectRefundType(View v) {
        refundTypePopupWindow.showAtLocation(binding.root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 选择退款原因
     */
    public void selectRefundReason(View v) {
        refundReasonPopupWindow.showAtLocation(binding.root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 提交申请退款
     */
    public void applyRefund(View v) {
        if (TextUtils.isEmpty(refundTypeTV.getText().toString())) {
            Utils.showToast(mContext, "请选择退款类型");
            return;
        }
        if (TextUtils.isEmpty(refundReasonTV.getText().toString())) {
            Utils.showToast(mContext, "请选择退款原因");
            return;
        }
        if (TextUtils.isEmpty(refundNumber.getText().toString())) {
            Utils.showToast(mContext, "请输入运单号");
            return;
        }
        confirmRefund();
    }

    /**
     * 申请退款
     */
    private void confirmRefund() {
        String srefundReason = refundReasonTV.getText().toString();
        String srefundNumber = refundNumber.getText().toString();
        Map map = new HashMap();
        map.put("orderItemId", orderItemId);
        map.put("serviceMethod", refundTypeId);
        map.put("reason", srefundReason);
        map.put("trackingNumber", srefundNumber);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));

        fromNetwork.applyOrderItemSellService(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(ApplyRefundActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel bean) {
                        final RefundDialog dialog = new RefundDialog(mContext);
                        dialog.setContentView(null);
                        dialog.show();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxCommonBus.get().unregister(this);
    }

}
