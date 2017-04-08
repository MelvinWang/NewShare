package com.melvin.share.ui.fragment.qrcode;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.OrderCodeAdapter;
import com.melvin.share.databinding.FragmentOrderCodeBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.WalletProduct;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.ui.fragment.wallet.WalletUseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.melvin.share.R.id.recyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/5
 * <p/>
 * 描述：订单二维码
 */
public class OrderCodeFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentOrderCodeBinding binding;
    private Context mContext;
    private MyRecyclerView mRecyclerView;
    private OrderCodeAdapter orderCodeAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private View root;
    private int pageNo = 1;
    private Map map;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_code, container, false);
        if (root == null) {
            mContext = getActivity();
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        map = new HashMap();
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
    }


    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        orderCodeAdapter = new OrderCodeAdapter(mContext, data);
        mRecyclerView.setAdapter(orderCodeAdapter);
    }


    /**
     * 请求网络
     */
    private void requestData() {
        map.put("pageNo", pageNo);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findProductScanCode(jsonObject)
                .compose(new RxFragmentHelper<CommonList<Product>>().ioMain(mContext, OrderCodeFragment.this, true))
                .subscribe(new RxModelSubscribe<CommonList<Product>>(mContext, true) {
                    @Override
                    protected void myNext(CommonList<Product> commonList) {
                        data.addAll(commonList.rows);
                        orderCodeAdapter.notifyDataSetChanged();
                        if (pageNo == 1) {
                            mRecyclerView.refreshComplete();
                        } else {
                            mRecyclerView.loadMoreComplete();
                        }

                    }


                    @Override
                    protected void myError(String message) {
                        mRecyclerView.refreshComplete();
                        mRecyclerView.loadMoreComplete();
                        Utils.showToast(mContext, message);
                    }
                });

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        data.clear();
        orderCodeAdapter.notifyDataSetChanged();
        requestData();

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        requestData();
    }
}