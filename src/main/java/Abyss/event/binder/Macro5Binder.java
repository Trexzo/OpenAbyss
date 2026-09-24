/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.Macro5PreTickInvoker;
import Abyss.module.impl.macro.Macro5;

public final class Macro5Binder {
    private Macro5Binder() {
}
    public static void j(EventBus var0, Macro5 var3) {
        var0.R(var3, PreTickEvent.class, 3, new Macro5PreTickInvoker(var3));
}
}