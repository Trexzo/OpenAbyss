/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.invoker.AutoToolPreMouseInputInvoker;
import Abyss.module.impl.world.AutoTool;

public final class AutoToolBinder {
    private AutoToolBinder() {
}
    public static void v(EventBus var0, AutoTool var3) {
        var0.R(var3, PreMouseInputEvent.class, 3, new AutoToolPreMouseInputInvoker(var3));
}
}