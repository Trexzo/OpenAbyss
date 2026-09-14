/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostUpdateWalkingPlayerEvent;
import Abyss.module.impl.player.Blink;

public final class BlinkPostUpdateWalkingPlayerInvoker
implements EventInvoker {
    final Blink F;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x77DFCD9358CL;
        this.F.onPostUpdateWalkingPlayer((PostUpdateWalkingPlayerEvent)var3, var4);
}
    public BlinkPostUpdateWalkingPlayerInvoker(Blink var1) {
        this.F = var1;
}
}