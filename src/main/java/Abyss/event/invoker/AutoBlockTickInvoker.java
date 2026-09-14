/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.TickEvent;
import Abyss.module.impl.combat.AutoBlock;

public final class AutoBlockTickInvoker
implements EventInvoker {
    final AutoBlock g;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2643E9584B68L;
        this.g.onTick((TickEvent)var3, var4);
}
    public AutoBlockTickInvoker(AutoBlock var1) {
        this.g = var1;
}
}