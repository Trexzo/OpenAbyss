/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.world.AutoTool;

public final class AutoToolPreMouseInputInvoker
implements EventInvoker {
    final AutoTool g;

    public AutoToolPreMouseInputInvoker(AutoTool var1) {
        this.g = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x64FEA38A4C3FL;
        this.g.onPreMouseInput(var4, (PreMouseInputEvent)var3);
}
}