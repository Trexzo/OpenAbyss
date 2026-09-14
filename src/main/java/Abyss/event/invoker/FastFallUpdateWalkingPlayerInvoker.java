/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.impl.movement.FastFall;

public final class FastFallUpdateWalkingPlayerInvoker
implements EventInvoker {
    final FastFall Z;

    @Override
    public void c(long var1, Object var3) {
        this.Z.onUpdateWalkingPlayer((UpdateWalkingPlayerEvent)var3);
}
    public FastFallUpdateWalkingPlayerInvoker(FastFall var1) {
        this.Z = var1;
}
}