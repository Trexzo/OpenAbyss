/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM.Hooks.Render;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.module.impl.configuration.Gadgets;

public class EffectRendererHooks {
    public static void cancelDestroyParticles(CallbackInfo var0) {
        if (Gadgets.noMiningParticles.c()) {
            var0.cancel();
}
}
    public static void cancelHitParticles(CallbackInfo var0) {
        if (Gadgets.noMiningParticles.c()) {
            var0.cancel();
}
}
}