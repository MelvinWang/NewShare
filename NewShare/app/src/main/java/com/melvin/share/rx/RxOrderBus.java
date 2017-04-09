package com.melvin.share.rx;

import com.hwangjr.rxbus.Bus;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/6
 * <p>
 * 描述：订单
 */
public final class RxOrderBus {
    private static Bus mBus;

    public synchronized static Bus get() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }
}
