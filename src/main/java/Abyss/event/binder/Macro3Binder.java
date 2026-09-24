/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.Macro3PreTickInvoker;
import Abyss.module.impl.macro.Macro3;

public final class Macro3Binder {
    public static void V(EventBus var0, Macro3 var1) {
        var0.R(var1, PreTickEvent.class, 3, new Macro3PreTickInvoker(var1));
}
    private Macro3Binder() {
}
}