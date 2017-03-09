package com.melvin.share.ui.fragment.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.melvin.share.R;
import com.melvin.share.Utils.CodeUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.app.BaseApplication;
import com.melvin.share.databinding.FragmentPhoneLoginBinding;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.modelview.common.PhoneLoginViewModel;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.activity.common.RegisterFirstActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.melvin.share.Utils.Utils.mPref;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/8
 * <p>
 * 描述：手机登录
 */
public class PhoneLoginFragment extends BaseFragment implements View.OnClickListener {

    private FragmentPhoneLoginBinding binding;
    private Context mContext;
    private View root;
    //产生的验证码
    private String realCode;
    private ImageView iv_showCode;
    private EditText phoneEt;
    private EditText codeEt;
    private EditText cerificationCodeEt;
    private Map map;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_login, container, false);
        if (root == null) {
            mContext = getActivity();
            initData();
            initClick();
            root = binding.getRoot();
        } else {
            ViewUtils.removeParent(root);
        }
        return root;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        iv_showCode = binding.ivShowCode;
        map = new HashMap();
        //将验证码用图片的形式显示出来s
        iv_showCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode();

        phoneEt = binding.phoneNumber;
        codeEt = binding.userValinumber;
        cerificationCodeEt = binding.cerificationCode;
    }

    /**
     * 绑定点击事件
     */
    private void initClick() {
        binding.getCode.setOnClickListener(this);
        binding.ivShowCode.setOnClickListener(this);
        binding.login.setOnClickListener(this);
        binding.register.setOnClickListener(this);
    }

    /**
     * 点击事件分发
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.get_code://获取验证码
                getCode();
                break;
            case R.id.iv_showCode://刷新验证码
                iv_showCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
                realCode = CodeUtils.getInstance().getCode();
                break;
            case R.id.login://登录
                loginDone();
                break;
            case R.id.register://注册
                startActivity(new Intent(mContext, RegisterFirstActivity.class));
                break;
        }

    }

    /**
     * 获取验证码
     */
    public void getCode() {
        if (TextUtils.isEmpty(phoneEt.getText().toString())) {
            Utils.showToast(mContext, "请输入电话号码");
            return;
        }
        map.put("phone", phoneEt.getText().toString());
        map.put("type", "LOGIN");
        fromNetwork.sendMessage(map)
                .compose(new RxFragmentHelper<CommonReturnModel>().ioMain(mContext, PhoneLoginFragment.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);
                    }


                    @Override
                    protected void myError(String message) {

                        Utils.showToast(mContext, message);
                    }
                });

    }

    /**
     * 登录
     */
    public void loginDone() {
        if (TextUtils.isEmpty(phoneEt.getText().toString())) {
            Utils.showToast(mContext, "请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(codeEt.getText().toString())) {
            Utils.showToast(mContext, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(cerificationCodeEt.getText().toString())) {
            Utils.showToast(mContext, "请输入图片验证码");
            return;
        }
        if (!cerificationCodeEt.getText().toString().equalsIgnoreCase(realCode)) {
            Utils.showToast(mContext, "图片验证码不一致");
            return;
        }
        fromNetwork.loginByPhoneCode(phoneEt.getText().toString(), codeEt.getText().toString())
                .compose(new RxFragmentHelper<CommonReturnModel<SelfInformation>>().ioMain(mContext, PhoneLoginFragment.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel<SelfInformation>>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel<SelfInformation> commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);
                        ShapreUtils.setCustomerId(commonReturnModel.result.customer.id);
                        ShapreUtils.setUserName(commonReturnModel.result.customer.userName);
                        ShapreUtils.setPicture(commonReturnModel.result.customer.picture);
                        mContext.startActivity(new Intent(mContext, MainActivity.class));
                        getActivity().finish();
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

    }
}