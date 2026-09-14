/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.impl.movement.NoSlow;

public final class NoSlowUpdateWalkingPlayerInvoker
implements EventInvoker {
    final NoSlow U;

    public NoSlowUpdateWalkingPlayerInvoker(NoSlow var1) {
        this.U = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5D70085EB084L;
        this.U.onUpdateWalkingPlayer((UpdateWalkingPlayerEvent)var3, var4);
}
}