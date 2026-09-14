/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.combat.KillAura;

public final class KillAuraPreMouseInputInvoker
implements EventInvoker {
    final KillAura b;

    public KillAuraPreMouseInputInvoker(KillAura var1) {
        this.b = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x13663032DF1CL;
        this.b.onPreMouseInput(var4, (PreMouseInputEvent)var3);
}
}