/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.combat.AutoClicker;

public final class AutoClickerPreTickInvoker
implements EventInvoker {
    final AutoClicker k;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x33F3DE0EE9ABL;
        this.k.onPreTick(var4, (PreTickEvent)var3);
}
    public AutoClickerPreTickInvoker(AutoClicker var1) {
        this.k = var1;
}
}