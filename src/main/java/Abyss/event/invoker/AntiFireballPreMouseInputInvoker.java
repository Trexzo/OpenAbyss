/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.combat.AntiFireball;

public final class AntiFireballPreMouseInputInvoker
implements EventInvoker {
    final AntiFireball e;

    public AntiFireballPreMouseInputInvoker(AntiFireball var1) {
        this.e = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x25D3406111E0L;
        this.e.onPreMouseInput((PreMouseInputEvent)var3, var4);
}
}