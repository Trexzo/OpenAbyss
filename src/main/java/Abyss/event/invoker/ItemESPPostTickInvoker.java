/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.ItemESP;

public final class ItemESPPostTickInvoker
implements EventInvoker {
    final ItemESP M;

    public ItemESPPostTickInvoker(ItemESP var1) {
        this.M = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.M.onPostTick((PostTickEvent)var3);
}
}