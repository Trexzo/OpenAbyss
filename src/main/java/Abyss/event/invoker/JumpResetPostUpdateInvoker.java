/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostUpdateEvent;
import Abyss.module.impl.combat.JumpReset;

public final class JumpResetPostUpdateInvoker
implements EventInvoker {
    final JumpReset J;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x291A96A1B84DL;
        this.J.onPostUpdate(var4, (PostUpdateEvent)var3);
}
    public JumpResetPostUpdateInvoker(JumpReset var1) {
        this.J = var1;
}
}