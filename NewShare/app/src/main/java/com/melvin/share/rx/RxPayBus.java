package com.melvin.share.rx;

import com.hwangjr.rxbus.Bus;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/5/4
 * <p>
 * 描述：支付
 */
public final class RxPayBus {
    private static Bus mBus;

    public synchronized static Bus get() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }
}
