package com.melvin.share.ui.fragment.productinfo;

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
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.ProductEvaluateAdapter;
import com.melvin.share.databinding.FragmentProductEvaluateBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Evaluation;
import com.melvin.share.model.User;
import com.melvin.share.model.WalletProduct;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.ui.fragment.wallet.WalletUseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.melvin.share.R.id.recyclerView;
import static com.melvin.share.ui.activity.ProductInfoActivity.productId;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/4
 * <p/>
 * 描述：单个商品信息的评价
 */
public class ProductEvaluateFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentProductEvaluateBinding binding;
    private Context mContext;
    private MyRecyclerView mRecyclerView;
    private ProductEvaluateAdapter productEvaluateAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private View root;
    private int pageNo = 1;
    private Map map;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_evaluate, container, false);
        if (root == null) {
            mContext = getActivity();
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        }
        return root;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        map = new HashMap();
        mRecyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        productEvaluateAdapter = new ProductEvaluateAdapter(mContext, data);
        mRecyclerView.setAdapter(productEvaluateAdapter);
    }

    /**
     * 请求网络
     */
    private void requestData() {
        map.put("pageNo", pageNo);
        map.put("productId", productId);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findEvaluationsByProduct(jsonObject)
                .compose(new RxFragmentHelper<CommonList<Evaluation>>().ioMain(mContext, ProductEvaluateFragment.this, false))
                .subscribe(new RxModelSubscribe<CommonList<Evaluation>>(mContext, false) {
                    @Override
                    protected void myNext(CommonList<Evaluation> commonList) {
                        data.addAll(commonList.rows);
                        productEvaluateAdapter.notifyDataSetChanged();
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
        productEvaluateAdapter.notifyDataSetChanged();
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