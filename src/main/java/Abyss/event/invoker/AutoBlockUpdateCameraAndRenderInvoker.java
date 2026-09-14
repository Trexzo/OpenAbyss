/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.UpdateCameraAndRenderEvent;
import Abyss.module.impl.combat.AutoBlock;

public final class AutoBlockUpdateCameraAndRenderInvoker
implements EventInvoker {
    final AutoBlock B;

    public AutoBlockUpdateCameraAndRenderInvoker(AutoBlock var1) {
        this.B = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x54AB629AB6ECL;
        this.B.onUpdateCameraAndRender(var4, (UpdateCameraAndRenderEvent)var3);
}
}