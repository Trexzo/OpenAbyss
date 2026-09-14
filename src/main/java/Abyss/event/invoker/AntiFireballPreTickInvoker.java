/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.combat.AntiFireball;

public final class AntiFireballPreTickInvoker
implements EventInvoker {
    final AntiFireball e;

    public AntiFireballPreTickInvoker(AntiFireball var1) {
        this.e = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x15E7352F7024L) >>> 48);
        int var5 = (int)((var1 ^ 0x15E7352F7024L) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x15E7352F7024L) << 48 >>> 48);
        this.e.onPreTick((char)var4, var5, (short)var6, (PreTickEvent)var3);
}
}