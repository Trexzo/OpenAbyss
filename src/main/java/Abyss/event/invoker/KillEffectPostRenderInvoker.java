/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostRenderEvent;
import Abyss.module.impl.visual.KillEffect;

public final class KillEffectPostRenderInvoker
implements EventInvoker {
    final KillEffect z;

    public KillEffectPostRenderInvoker(KillEffect var1) {
        this.z = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.z.onPostRender((PostRenderEvent)var3);
}
}