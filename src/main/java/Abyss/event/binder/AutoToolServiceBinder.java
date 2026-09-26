/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreTickEvent;
import Abyss.event.invoker.AutoToolServicePreTickInvoker;
import Abyss.util.AutoToolService;

public final class AutoToolServiceBinder {
    public static void z(EventBus var2, AutoToolService var3) {
        var2.R(var3, PreTickEvent.class, 3, new AutoToolServicePreTickInvoker(var3));
}
    private AutoToolServiceBinder() {
}
}