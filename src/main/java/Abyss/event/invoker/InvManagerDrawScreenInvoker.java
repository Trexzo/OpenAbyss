/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.DrawScreenEvent;
import Abyss.module.impl.player.InvManager;

public final class InvManagerDrawScreenInvoker
implements EventInvoker {
    final InvManager y;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x1ADD6F9DEA46L) >>> 48);
        int var5 = (int)((var1 ^ 0x1ADD6F9DEA46L) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x1ADD6F9DEA46L) << 48 >>> 48);
        this.y.onDrawScreen((char)var4, var5, var6, (DrawScreenEvent)var3);
}
    public InvManagerDrawScreenInvoker(InvManager var1) {
        this.y = var1;
}
}