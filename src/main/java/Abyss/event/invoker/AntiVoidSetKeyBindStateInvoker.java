/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.module.impl.world.AntiVoid;

public final class AntiVoidSetKeyBindStateInvoker
implements EventInvoker {
    final AntiVoid p;

    @Override
    public void c(long var1, Object var3) {
        long var4 = (var1 ^ 0x4C7161B98FE9L) >>> 16;
        int var6 = (int)((var1 ^ 0x4C7161B98FE9L) << 48 >>> 48);
        this.p.onSetKeyBindState((SetKeyBindStateEvent)var3, var4, (char)var6);
}
    public AntiVoidSetKeyBindStateInvoker(AntiVoid var1) {
        this.p = var1;
}
}