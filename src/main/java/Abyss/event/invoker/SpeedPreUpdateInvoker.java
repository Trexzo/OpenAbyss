/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.movement.Speed;

public final class SpeedPreUpdateInvoker
implements EventInvoker {
    final Speed W;

    public SpeedPreUpdateInvoker(Speed var1) {
        this.W = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x3CB006FC3D90L;
        this.W.onPreUpdate(var4, (PreUpdateEvent)var3);
}
}