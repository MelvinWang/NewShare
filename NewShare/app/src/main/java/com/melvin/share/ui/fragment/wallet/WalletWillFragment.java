package com.melvin.share.ui.fragment.wallet;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.WalletMoneyAdapter;
import com.melvin.share.databinding.FragmentWalletWillBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Reward;
import com.melvin.share.model.User;
import com.melvin.share.model.WalletProduct;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/11/29
 * <p/>
 * 描述：钱包即将可用
 */
public class WalletWillFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentWalletWillBinding binding;
    private Context mContext;
    private MyRecyclerView recyclerView;
    private WalletMoneyAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();
    private View root;
    private View headerView;
    private TextView sumMoneyView;
    private int pageNo = 1;
    private Map map;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_will, container, false);
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
        recyclerView = binding.recyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        //头部
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = layoutInflater.inflate(R.layout.wallet_will_title, null, false);
        sumMoneyView = (TextView) headerView.findViewById(R.id.sum_money);

        recyclerView.addHeaderView(headerView);
        recyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setLoadingListener(this);
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        adpter = new WalletMoneyAdapter(mContext, dataList);
        recyclerView.setAdapter(adpter);
    }


    /**
     * 请求网络
     */
    private void requestData() {
        requestProduct();
        requestReward();

    }
    /**
     * 请求网络商品
     */
    private void requestProduct() {
        map.put("pageNo", pageNo);
        map.put("used", false);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findMyReward(jsonObject)
                .compose(new RxFragmentHelper<CommonList<WalletProduct>>().ioMain(mContext, WalletWillFragment.this, true))
                .subscribe(new RxModelSubscribe<CommonList<WalletProduct>>(mContext, true) {
                    @Override
                    protected void myNext(CommonList<WalletProduct> commonList) {
                        dataList.addAll(commonList.rows);
                        adpter.notifyDataSetChanged();
                        if (pageNo == 1) {
                            recyclerView.refreshComplete();
                        } else {
                            recyclerView.loadMoreComplete();
                        }

                    }


                    @Override
                    protected void myError(String message) {
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 请求返利
     */
    private void requestReward() {
        fromNetwork.findMyTotalReward(ShapreUtils.getCustomerId())
                .compose(new RxFragmentHelper<Reward>().ioMain(mContext, WalletWillFragment.this, false))
                .subscribe(new RxModelSubscribe<Reward>(mContext, false) {
                    @Override
                    protected void myNext(Reward bean) {
                        sumMoneyView.setText(bean.cashbackWill);
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
        pageNo = 1;
        dataList.clear();
        adpter.notifyDataSetChanged();
        requestProduct();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        requestProduct();

    }
}

