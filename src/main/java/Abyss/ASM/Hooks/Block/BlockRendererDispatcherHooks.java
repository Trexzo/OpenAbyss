/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockBed
 *  net.minecraft.block.BlockBed$EnumPartType
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3i
 */
package Abyss.ASM.Hooks.Block;

import Abyss.AbyssClient;
import Abyss.module.impl.visual_utility.BlocksESP;
import net.minecraft.block.BlockBed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

public class BlockRendererDispatcherHooks {
    public static void onRenderBlock(IBlockState var0, BlockPos var1) {
        if (var0.func_177230_c() instanceof BlockBed && var0.func_177229_b((IProperty)BlockBed.field_176472_a) == BlockBed.EnumPartType.HEAD) {
            AbyssClient.G.add(new BlockPos((Vec3i)var1));
}
        if (BlocksESP.L(var0.func_177230_c()) && BlocksESP.y(var1)) {
            BlocksESP.L.add(new BlockPos((Vec3i)var1));
}
}
}