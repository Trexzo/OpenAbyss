/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.module.impl.visual.KillEffect;

public final class KillEffectEntityJoinWorldInvoker
implements EventInvoker {
    final KillEffect S;

    @Override
    public void c(long var1, Object var3) {
        this.S.onEntityJoinWorld((EntityJoinWorldEvent)var3);
}
    public KillEffectEntityJoinWorldInvoker(KillEffect var1) {
        this.S = var1;
}
}