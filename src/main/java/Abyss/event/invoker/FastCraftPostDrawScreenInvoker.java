/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostDrawScreenEvent;
import Abyss.module.impl.player.FastCraft;

public final class FastCraftPostDrawScreenInvoker
implements EventInvoker {
    final FastCraft z;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x366496A2A81DL;
        this.z.onPostDrawScreen((PostDrawScreenEvent)var3, var4);
}
    public FastCraftPostDrawScreenInvoker(FastCraft var1) {
        this.z = var1;
}
}