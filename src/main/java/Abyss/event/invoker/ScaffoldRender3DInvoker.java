/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.world.Scaffold;

public final class ScaffoldRender3DInvoker
implements EventInvoker {
    final Scaffold o;

    public ScaffoldRender3DInvoker(Scaffold var1) {
        this.o = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x601A5F8341AAL) >>> 32);
        int var5 = (int)((var1 ^ 0x601A5F8341AAL) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x601A5F8341AAL) << 48 >>> 48);
        this.o.onRender3D(var4, (short)var5, (Render3DEvent)var3, var6);
}
}