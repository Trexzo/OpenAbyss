/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumWorldBlockLayer
 */
package Abyss.ASM.Hooks.Block;

import Abyss.module.ModuleManager;
import net.minecraft.util.EnumWorldBlockLayer;

public class BlockLeavesHooks {
    public static EnumWorldBlockLayer getBlockLayer(boolean var0) {
        if (ModuleManager.m != null && ModuleManager.m.o()) {
            return EnumWorldBlockLayer.TRANSLUCENT;
}
        return var0 ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
}
}