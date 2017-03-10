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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.CodeUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.app.BaseApplication;
import com.melvin.share.databinding.FragmentNormalLoginBinding;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.ForgetPasswordActivity;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.activity.common.RegisterFirstActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import static com.melvin.share.R.mipmap.phone;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/8
 * <p>
 * 描述：账号登录
 */
public class NormalLoginFragment extends BaseFragment implements View.OnClickListener {

    private FragmentNormalLoginBinding binding;
    private Context mContext;
    private View root;
    //产生的验证码
    private String realCode;
    private ImageView iv_showCode;
    private EditText phoneEt;
    private EditText passwordEt;
    private EditText cerificationCodeEt;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_normal_login, container, false);
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
        //将验证码用图片的形式显示出来s
        iv_showCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode();

        phoneEt = binding.userName;
        passwordEt = binding.userPassword;
        cerificationCodeEt = binding.cerificationCode;
    }

    /**
     * 绑定点击事件
     */
    private void initClick() {
        binding.ivShowCode.setOnClickListener(this);
        binding.register.setOnClickListener(this);
        binding.forgetPassword.setOnClickListener(this);
        binding.login.setOnClickListener(this);
        binding.qq.setOnClickListener(this);
        binding.wechat.setOnClickListener(this);

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
            case R.id.iv_showCode://刷新验证码
                iv_showCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
                realCode = CodeUtils.getInstance().getCode();
                break;
            case R.id.register://注册
                startActivity(new Intent(mContext, RegisterFirstActivity.class));
                break;
            case R.id.forget_password://忘记密码
                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
                break;
            case R.id.login://登录
                loginByPassword();
                break;
            case R.id.qq://qq登录
                Utils.showToast(mContext, "qq登录");
                break;
            case R.id.wechat://微信登录
                Utils.showToast(mContext, "微信登录");
                break;
        }
    }

    private void loginByPassword() {
        if (TextUtils.isEmpty(phoneEt.getText().toString())) {
            Utils.showToast(mContext, "请输入账号或者电话号码");
            return;
        }
        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
            Utils.showToast(mContext, "请输入密码");
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
        Map map = new HashMap();
        map.put("account", phoneEt.getText().toString());
        map.put("password", passwordEt.getText().toString());
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.loginByPassword(jsonObject)
                .compose(new RxFragmentHelper<CommonReturnModel<SelfInformation>>().ioMain(mContext, NormalLoginFragment.this, true))
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