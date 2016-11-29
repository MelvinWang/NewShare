package com.melvin.share.Utils;

import com.hwangjr.rxbus.Bus;
/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/6
 * <p>
 * 描述：二维码
 */
public final class RxBus {
    private static Bus sBus;

    public synchronized static Bus get() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }
}
