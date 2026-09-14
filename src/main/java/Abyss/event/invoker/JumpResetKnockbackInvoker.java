/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.KnockbackEvent;
import Abyss.module.impl.combat.JumpReset;

public final class JumpResetKnockbackInvoker
implements EventInvoker {
    final JumpReset X;

    public JumpResetKnockbackInvoker(JumpReset var1) {
        this.X = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x673984DDC556L;
        this.X.onKnockback((KnockbackEvent)var3, var4);
}
}