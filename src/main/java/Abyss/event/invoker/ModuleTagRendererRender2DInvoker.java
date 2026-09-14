/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.ui.ModuleTagRenderer;

public final class ModuleTagRendererRender2DInvoker
implements EventInvoker {
    final ModuleTagRenderer F;

    public ModuleTagRendererRender2DInvoker(ModuleTagRenderer var1) {
        this.F = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x29F011E0D8L;
        this.F.onRender2D(var4, (Render2DEvent)var3);
}
}