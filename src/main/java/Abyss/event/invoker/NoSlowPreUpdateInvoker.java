/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.movement.NoSlow;

public final class NoSlowPreUpdateInvoker
implements EventInvoker {
    final NoSlow h;

    public NoSlowPreUpdateInvoker(NoSlow var1) {
        this.h = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x5047020E0D58L) >>> 48);
        int var5 = (int)((var1 ^ 0x5047020E0D58L) << 16 >>> 48);
        int var6 = (int)((var1 ^ 0x5047020E0D58L) << 32 >>> 32);
        this.h.onPreUpdate((short)var4, (PreUpdateEvent)var3, (char)var5, var6);
}
}