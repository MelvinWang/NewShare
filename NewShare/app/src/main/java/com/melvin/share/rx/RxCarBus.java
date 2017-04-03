package com.melvin.share.rx;

import com.hwangjr.rxbus.Bus;
/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/6
 * <p>
 * 描述：购物车
 */
public final class RxCarBus {
    private static Bus mBus;

    public synchronized static Bus get() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }
}
