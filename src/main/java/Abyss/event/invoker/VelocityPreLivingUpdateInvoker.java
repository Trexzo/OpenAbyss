/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.module.impl.combat.Velocity;

public final class VelocityPreLivingUpdateInvoker
implements EventInvoker {
    final Velocity G;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x183DF49F669AL;
        this.G.onPreLivingUpdate(var4, (PreLivingUpdateEvent)var3);
}
    public VelocityPreLivingUpdateInvoker(Velocity var1) {
        this.G = var1;
}
}