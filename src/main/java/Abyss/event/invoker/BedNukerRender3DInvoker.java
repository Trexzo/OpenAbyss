/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.world.BedNuker;

public final class BedNukerRender3DInvoker
implements EventInvoker {
    final BedNuker H;

    public BedNukerRender3DInvoker(BedNuker var1) {
        this.H = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x4A64BF55591DL;
        this.H.onRender3D(var4, (Render3DEvent)var3);
}
}