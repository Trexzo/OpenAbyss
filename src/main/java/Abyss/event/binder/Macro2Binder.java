/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.Macro2PreTickInvoker;
import Abyss.module.impl.macro.Macro2;

public final class Macro2Binder {
    private static final long private Macro2Binder() {
}
    public static void d(EventBus var0, Macro2 var3) {
        var0.R(var3, PreTickEvent.class, 3, new Macro2PreTickInvoker(var3));
}
}