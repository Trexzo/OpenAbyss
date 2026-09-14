/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM.Hooks.World;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.module.ModuleManager;

public class WorldClientHooks {
    public static void onDoVoidFogParticles(CallbackInfo var0) {
        if (ModuleManager.W != null && ModuleManager.W.o()) {
            var0.cancel();
}
}
}