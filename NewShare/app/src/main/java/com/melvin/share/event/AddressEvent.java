package com.melvin.share.event;

import android.text.Editable;

import com.melvin.share.view.MyTextWatcher;

import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/28
 * <p>
 * 描述：地址Event
 */
public class AddressEvent {

    Map map;

    public AddressEvent(Map map) {
        this.map = map;
    }

    /**
     * 收货人
     *
     * @return
     */
    public MyTextWatcher getReceverEditTextWatcher() {
        return new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                map.put("recever", editable.toString());
            }
        };
    }

    /**
     * 手机号码
     *
     * @return
     */
    public MyTextWatcher getReceverPhoneEditTextWatcher() {
        return new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                map.put("receverPhone", editable.toString());
            }
        };
    }

    /**
     * 邮政编码
     *
     * @return
     */
    public MyTextWatcher getPostalcodeEditTextWatcher() {
        return new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                map.put("postalcode", editable.toString());
            }
        };
    }

    /**
     * 详细地址
     *
     * @return
     */
    public MyTextWatcher getAddressEditTextWatcher() {
        return new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                map.put("address", editable.toString());
            }
        };
    }



}
