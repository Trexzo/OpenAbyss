/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostRenderEvent;
import Abyss.module.impl.visual.Chams;

public final class ChamsPostRenderInvoker
implements EventInvoker {
    final Chams R;

    @Override
    public void c(long var1, Object var3) {
        this.R.onPostRender((PostRenderEvent)var3);
}
    public ChamsPostRenderInvoker(Chams var1) {
        this.R = var1;
}
}