/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostUpdateEvent;
import Abyss.module.impl.combat.AutoBlock;

public final class AutoBlockPostUpdateInvoker
implements EventInvoker {
    final AutoBlock Z;

    @Override
    public void c(long var1, Object var3) {
        long var4 = (var1 ^ 0x7C6A9B1DFEFAL) >>> 16;
        int var6 = (int)((var1 ^ 0x7C6A9B1DFEFAL) << 48 >>> 48);
        this.Z.onPostUpdate(var4, (short)var6, (PostUpdateEvent)var3);
}
    public AutoBlockPostUpdateInvoker(AutoBlock var1) {
        this.Z = var1;
}
}