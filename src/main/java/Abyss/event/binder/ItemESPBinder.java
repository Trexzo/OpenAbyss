/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.ItemESPPostTickInvoker;
import Abyss.event.invoker.ItemESPRender3DInvoker;
import Abyss.module.impl.visual_utility.ItemESP;

public final class ItemESPBinder {
    public static void v(EventBus var2, ItemESP var3) {
        var2.R(var3, PostTickEvent.class, 3, new ItemESPPostTickInvoker(var3));
        var2.R(var3, Render3DEvent.class, 3, new ItemESPRender3DInvoker(var3));
}
    private ItemESPBinder() {
}
}