/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.util.AutoToolService;

public final class AutoToolServicePreTickInvoker
implements EventInvoker {
    final AutoToolService U;

    public AutoToolServicePreTickInvoker(AutoToolService var1) {
        this.U = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0xBB2C299AF96L;
        this.U.onPreTick(var4, (PreTickEvent)var3);
}
}