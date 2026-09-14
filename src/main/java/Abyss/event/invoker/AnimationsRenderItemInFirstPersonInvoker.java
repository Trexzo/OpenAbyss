/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.RenderItemInFirstPersonEvent;
import Abyss.module.impl.visual.Animations;

public final class AnimationsRenderItemInFirstPersonInvoker
implements EventInvoker {
    final Animations u;

    public AnimationsRenderItemInFirstPersonInvoker(Animations var1) {
        this.u = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5AB71CED6ADAL;
        this.u.onRenderItemInFirstPerson(var4, (RenderItemInFirstPersonEvent)var3);
}
}