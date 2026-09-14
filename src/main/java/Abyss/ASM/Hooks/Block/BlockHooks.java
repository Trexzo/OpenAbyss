/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockBarrier
 *  net.minecraft.block.BlockSlab
 *  net.minecraft.block.state.BlockState
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumWorldBlockLayer
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package Abyss.ASM.Hooks.Block;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.AbyssClient;
import Abyss.event.events.AddCollisionBoxesToListEvent;
import Abyss.module.ModuleManager;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHooks {
    private static long b;
    
    public static void getMixedBrightnessForBlock(IBlockAccess var0, BlockPos var1, CallbackInfoReturnable<Integer> var2) {
        if (ModuleManager.m != null && ModuleManager.m.o()) {
            var2.setReturnValue((int)b);
        } else {
            Block var5 = var0.func_180495_p(var1).func_177230_c();
            int var6 = var0.func_175626_b(var1, var5.func_149750_m());
            if (var6 == 0 && var5 instanceof BlockSlab) {
                var1 = var1.func_177977_b();
                var5 = var0.func_180495_p(var1).func_177230_c();
                var2.setReturnValue(var0.func_175626_b(var1, var5.func_149750_m()));
            } else {
                var2.setReturnValue(var6);
}
}
        var2.cancel();
}
    public static void addCollisionBoxesToList(World var0, BlockPos var1, IBlockState var2, AxisAlignedBB var3, List<AxisAlignedBB> var4, Entity var5, BlockState var6, Block var7, CallbackInfo var8) {
        AxisAlignedBB var15;
        if (AbyssClient.w != null && var3 != null && (var15 = var7.func_180640_a(var0, var1, var2)) != null) {
            AddCollisionBoxesToListEvent var16 = new AddCollisionBoxesToListEvent(var6.func_177622_c(), var1);
            AbyssClient.w.e(var16, 18670087776179L);
            if (var16.a()) {
                var8.cancel();
            } else {
                if (var3.func_72326_a(var15)) {
                    var4.add(var15);
}
                var8.cancel();
}
}
}
    public static void shouldSideBeRendered(Block var0, IBlockAccess var1, BlockPos var2, EnumFacing var3, CallbackInfoReturnable<Boolean> var4) {
        if (var0 instanceof BlockBarrier) {
            var4.setReturnValue(var1.func_180495_p(var2).func_177230_c() != var0);
            var4.cancel();
}
}
    public static void getBlockLayer(Block var0, CallbackInfoReturnable<EnumWorldBlockLayer> var1) {
        if (ModuleManager.m != null) {
            if (!ModuleManager.m.o() && var0 != Blocks.field_180401_cv) {
                var1.setReturnValue(EnumWorldBlockLayer.SOLID);
            } else {
                var1.setReturnValue(EnumWorldBlockLayer.TRANSLUCENT);
}
            var1.cancel();
}
}
    static {
        b = 3851402091784306888L;
}
}