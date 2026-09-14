/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreRenderEvent;
import Abyss.module.impl.visual.Chams;

public final class ChamsPreRenderInvoker
implements EventInvoker {
    final Chams j;

    public ChamsPreRenderInvoker(Chams var1) {
        this.j = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.j.onPreRender((PreRenderEvent)var3);
}
}