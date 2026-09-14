/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.GetArmSwingAnimationEndEvent;
import Abyss.event.events.RenderItemInFirstPersonEvent;
import Abyss.event.invoker.AnimationsGetArmSwingAnimationEndInvoker;
import Abyss.event.invoker.AnimationsRenderItemInFirstPersonInvoker;
import Abyss.module.impl.visual.Animations;

public final class AnimationsBinder {
    private static final long private AnimationsBinder() {
}
    public static void A(EventBus var0, Animations var3) {
        var0.R(var3, GetArmSwingAnimationEndEvent.class, 3, new AnimationsGetArmSwingAnimationEndInvoker(var3));
        var0.R(var3, RenderItemInFirstPersonEvent.class, 3, new AnimationsRenderItemInFirstPersonInvoker(var3));
}
}