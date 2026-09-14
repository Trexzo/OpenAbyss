/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.combat.Velocity;

public final class VelocityPreMouseInputInvoker
implements EventInvoker {
    final Velocity w;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x6771199FFC66L) >>> 32);
        int var5 = (int)((var1 ^ 0x6771199FFC66L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x6771199FFC66L) << 48 >>> 48);
        this.w.onPreMouseInput(var4, (PreMouseInputEvent)var3, (char)var5, (short)var6);
}
    public VelocityPreMouseInputInvoker(Velocity var1) {
        this.w = var1;
}
}