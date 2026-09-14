/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.combat.KeepSprint;

public final class KeepSprintPreTickInvoker
implements EventInvoker {
    final KeepSprint V;

    public KeepSprintPreTickInvoker(KeepSprint var1) {
        this.V = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x21D9F55D80ADL;
        this.V.onPreTick(var4, (PreTickEvent)var3);
}
}