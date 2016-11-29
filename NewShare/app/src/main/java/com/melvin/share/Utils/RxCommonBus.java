package com.melvin.share.Utils;

import com.hwangjr.rxbus.Bus;
/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/6
 * <p>
 * 描述：通用在activity
 */
public final class RxCommonBus {
    private static Bus mBus;

    public synchronized static Bus get() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }
}
