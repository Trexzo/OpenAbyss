/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.Macro1PreTickInvoker;
import Abyss.module.impl.macro.Macro1;

public final class Macro1Binder {
    private static final long public static void g(EventBus var0, short var1, char var2, int var3, Macro1 var4) {
        var0.R(var4, PreTickEvent.class, 3, new Macro1PreTickInvoker(var4));
}
    private Macro1Binder() {
}
}