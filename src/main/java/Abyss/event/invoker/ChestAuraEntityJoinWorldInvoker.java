/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.module.impl.player.ChestAura;

public final class ChestAuraEntityJoinWorldInvoker
implements EventInvoker {
    final ChestAura f;

    public ChestAuraEntityJoinWorldInvoker(ChestAura var1) {
        this.f = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x1A0E4FE85210L;
        this.f.onEntityJoinWorld(var4, (EntityJoinWorldEvent)var3);
}
}