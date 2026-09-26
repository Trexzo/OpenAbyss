/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package Abyss.ASM.Hooks;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.module.ModuleManager;
import java.util.BitSet;
import net.minecraft.util.BlockPos;

public class VisGraphHooks {
    private static int getIndex(BlockPos var0) {
        return VisGraphHooks.getIndex(var0.getX() & 0xF, var0.getY() & 0xF, var0.getZ() & 0xF);
}
    private static int getIndex(int var0, int var1, int var2) {
        return var0 << 0 | var1 << 8 | var2 << 4;
}
    public static void func_178606_a(BlockPos var0, BitSet var1, CallbackInfo var2) {
        if (ModuleManager.m != null && ModuleManager.m.o() || ModuleManager.h != null && ModuleManager.h.o()) {
            var1.set(VisGraphHooks.getIndex(var0), true);
            var2.cancel();
}
}
}