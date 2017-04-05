package com.melvin.share.rx;

import com.hwangjr.rxbus.Bus;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/6
 * <p>
 * 描述：分享
 */
public final class RxShareBus {
    private static Bus mBus;

    public synchronized static Bus get() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }
}
