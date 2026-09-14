/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.GetArmSwingAnimationEndEvent;
import Abyss.module.impl.visual.Animations;

public final class AnimationsGetArmSwingAnimationEndInvoker
implements EventInvoker {
    final Animations N;

    @Override
    public void c(long var1, Object var3) {
        this.N.onGetArmSwingAnimationEnd((GetArmSwingAnimationEndEvent)var3);
}
    public AnimationsGetArmSwingAnimationEndInvoker(Animations var1) {
        this.N = var1;
}
}