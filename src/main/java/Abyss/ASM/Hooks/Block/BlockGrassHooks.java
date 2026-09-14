/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumWorldBlockLayer
 */
package Abyss.ASM.Hooks.Block;

import Abyss.module.ModuleManager;
import net.minecraft.util.EnumWorldBlockLayer;

public class BlockGrassHooks {
    public static EnumWorldBlockLayer getBlockLayer() {
        return ModuleManager.m != null && ModuleManager.m.o() ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.CUTOUT_MIPPED;
}
}