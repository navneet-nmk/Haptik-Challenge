package com.teenvan.haptik;

import com.squareup.otto.Bus;

/**
 * Created by navneet on 22/12/16.
 */

public class GlobalBus {
    private static Bus sBus;
    public static Bus getBus() {
        if (sBus == null)
            sBus = new Bus();
        return sBus;
    }

}
