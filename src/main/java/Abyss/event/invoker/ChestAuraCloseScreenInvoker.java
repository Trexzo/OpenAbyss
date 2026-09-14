/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.CloseScreenEvent;
import Abyss.module.impl.player.ChestAura;

public final class ChestAuraCloseScreenInvoker
implements EventInvoker {
    final ChestAura G;

    public ChestAuraCloseScreenInvoker(ChestAura var1) {
        this.G = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x31D0B4BF580BL;
        this.G.onCloseScreen(var4, (CloseScreenEvent)var3);
}
}