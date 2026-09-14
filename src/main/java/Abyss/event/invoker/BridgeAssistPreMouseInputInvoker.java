/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.world.BridgeAssist;

public final class BridgeAssistPreMouseInputInvoker
implements EventInvoker {
    final BridgeAssist w;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x41966E82B479L) >>> 32);
        int var5 = (int)((var1 ^ 0x41966E82B479L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x41966E82B479L) << 48 >>> 48);
        this.w.onPreMouseInput(var4, var5, (PreMouseInputEvent)var3, (char)var6);
}
    public BridgeAssistPreMouseInputInvoker(BridgeAssist var1) {
        this.w = var1;
}
}