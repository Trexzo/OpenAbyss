/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.WorldLoadEvent;
import Abyss.module.impl.combat.JumpReset;

public final class JumpResetWorldLoadInvoker
implements EventInvoker {
    final JumpReset n;

    public JumpResetWorldLoadInvoker(JumpReset var1) {
        this.n = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x1D69FC8A10E2L;
        this.n.onWorldLoad(var4, (WorldLoadEvent)var3);
}
}