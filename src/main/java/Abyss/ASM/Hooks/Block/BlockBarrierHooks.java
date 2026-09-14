/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM.Hooks.Block;

import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.module.ModuleManager;

public class BlockBarrierHooks {
    public static void getRenderType(CallbackInfoReturnable<Integer> var0) {
        if (ModuleManager.W != null && ModuleManager.W.o()) {
            var0.setReturnValue(3);
            var0.cancel();
}
}
}