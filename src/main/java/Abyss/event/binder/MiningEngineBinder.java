/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.MiningEnginePreTickInvoker;
import Abyss.internal.MiningEngine;

public final class MiningEngineBinder {
    private static final long public static void t(EventBus var2, MiningEngine var4) {
        var2.R(var4, PreTickEvent.class, 3, new MiningEnginePreTickInvoker(var4));
}
    private MiningEngineBinder() {
}
}