/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM.Hooks.Render;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.module.impl.configuration.Gadgets;

public class LoadingScreenRendererHooks {
    public static void forSkipProgress(int var0, CallbackInfo var1) {
        if (var0 < 0 || Gadgets.betterWorldSwapping.c()) {
            var1.cancel();
}
}
}