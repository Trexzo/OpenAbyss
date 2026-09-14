/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.module.impl.world.AutoDigPlace;

public final class AutoDigPlaceHeldItemChangeInvoker
implements EventInvoker {
    final AutoDigPlace G;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x15770F6D84ACL) >>> 32);
        int var5 = (int)((var1 ^ 0x15770F6D84ACL) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x15770F6D84ACL) << 48 >>> 48);
        this.G.onHeldItemChange((HeldItemChangeEvent)var3, var4, var5, var6);
}
    public AutoDigPlaceHeldItemChangeInvoker(AutoDigPlace var1) {
        this.G = var1;
}
}