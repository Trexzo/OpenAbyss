/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.combat.LagRange;

public final class LagRangePostTickInvoker
implements EventInvoker {
    final LagRange n;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x29563C6DFA94L) >>> 32);
        int var5 = (int)((var1 ^ 0x29563C6DFA94L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x29563C6DFA94L) << 48 >>> 48);
        this.n.onPostTick(var4, var5, (char)var6, (PostTickEvent)var3);
}
    public LagRangePostTickInvoker(LagRange var1) {
        this.n = var1;
}
}