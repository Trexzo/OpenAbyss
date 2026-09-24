/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.Macro4PreTickInvoker;
import Abyss.module.impl.macro.Macro4;

public final class Macro4Binder {
    private Macro4Binder() {
}
    public static void o(EventBus var1, Macro4 var4) {
        var1.R(var4, PreTickEvent.class, 3, new Macro4PreTickInvoker(var4));
}
}