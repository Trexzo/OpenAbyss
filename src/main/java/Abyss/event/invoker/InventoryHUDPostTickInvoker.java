/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.InventoryHUD;

public final class InventoryHUDPostTickInvoker
implements EventInvoker {
    final InventoryHUD f;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x4B5C5C51D00CL;
        this.f.onPostTick(var4, (PostTickEvent)var3);
}
    public InventoryHUDPostTickInvoker(InventoryHUD var1) {
        this.f = var1;
}
}