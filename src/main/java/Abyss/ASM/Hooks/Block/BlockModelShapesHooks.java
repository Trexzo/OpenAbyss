/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.model.IBakedModel
 *  net.minecraft.init.Blocks
 */
package Abyss.ASM.Hooks.Block;

import Abyss.module.ModuleManager;
import Abyss.util.MinecraftRef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;

public class BlockModelShapesHooks {
    private static final Minecraft g;
    private static IBakedModel p;

    public static IBakedModel getModelForState(IBlockState var0, IBakedModel var1) {
        if (var0.func_177230_c() == Blocks.field_180401_cv && ModuleManager.W != null && ModuleManager.W.o()) {
            if (p == null) {
                p = g.func_175602_ab().func_175023_a().func_178125_b(Blocks.field_150399_cn.func_176223_P());
}
            return p;
}
        return var1;
}
    static {
        long var0 = 72764143890033L;
        int var2 = (int)((var0 ^ 0xB5C1B31A7BDL) >>> 56);
        long var3 = (var0 ^ 0xB5C1B31A7BDL) << 8 >>> 8;
        g = MinecraftRef.c((byte)var2, 0L);
}
}