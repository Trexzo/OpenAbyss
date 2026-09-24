/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.InventoryHUDPostTickInvoker;
import Abyss.event.invoker.InventoryHUDRender2DInvoker;
import Abyss.module.impl.visual_utility.InventoryHUD;

public final class InventoryHUDBinder {
    public static void j(EventBus var0, InventoryHUD var1, byte var2, int var3, int var4) {
        var0.R(var1, PostTickEvent.class, 3, new InventoryHUDPostTickInvoker(var1));
        var0.R(var1, Render2DEvent.class, 3, new InventoryHUDRender2DInvoker(var1));
}
    private InventoryHUDBinder() {
}
}