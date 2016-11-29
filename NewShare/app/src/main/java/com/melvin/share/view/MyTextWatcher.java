package com.melvin.share.view;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created Time: 2016/5/19.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：继承，为了去掉两个方法
 */
public abstract class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public abstract void afterTextChanged(Editable s);
}
