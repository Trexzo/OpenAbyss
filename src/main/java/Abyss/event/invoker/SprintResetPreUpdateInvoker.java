/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.combat.SprintReset;

public final class SprintResetPreUpdateInvoker
implements EventInvoker {
    final SprintReset D;

    public SprintResetPreUpdateInvoker(SprintReset var1) {
        this.D = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x3A0D1772079L) >>> 32);
        int var5 = (int)((var1 ^ 0x3A0D1772079L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x3A0D1772079L) << 48 >>> 48);
        this.D.onPreUpdate(var4, (PreUpdateEvent)var3, var5, var6);
}
}