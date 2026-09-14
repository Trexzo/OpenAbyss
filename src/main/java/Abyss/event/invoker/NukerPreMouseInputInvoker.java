/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.world.Nuker;

public final class NukerPreMouseInputInvoker
implements EventInvoker {
    final Nuker n;

    public NukerPreMouseInputInvoker(Nuker var1) {
        this.n = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x603626B94913L) >>> 32);
        int var5 = (int)((var1 ^ 0x603626B94913L) << 32 >>> 56);
        int var6 = (int)((var1 ^ 0x603626B94913L) << 40 >>> 40);
        this.n.onPreMouseInput((PreMouseInputEvent)var3, var4, (byte)var5, var6);
}
}