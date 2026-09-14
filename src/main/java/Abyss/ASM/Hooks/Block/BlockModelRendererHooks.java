/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.BlockModelRenderer
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.resources.model.IBakedModel
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.crash.CrashReportCategory
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.ReportedException
 *  net.minecraft.world.IBlockAccess
 */
package Abyss.ASM.Hooks.Block;

import Abyss.ASM.Hooks.CallbackInfoReturnable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;

public class BlockModelRendererHooks {
    private static long public static void renderModel(IBlockAccess var0, IBakedModel var1, IBlockState var2, BlockPos var3, WorldRenderer var4, boolean var5, BlockModelRenderer var6, CallbackInfoReturnable<Boolean> var7) {
        boolean var10 = var2.func_177230_c().func_149750_m() == 0 && var1.func_177555_b();
        try {
            Block var11 = var2.func_177230_c();
            boolean var15 = var10 ? var6.func_178265_a(var0, var1, var11, var3, var4, var5) : var6.func_178258_b(var0, var1, var11, var3, var4, var5);
            var7.setReturnValue(var15);
            var7.cancel();
}
        catch (Throwable var14) {
            CrashReport var12 = CrashReport.func_85055_a((Throwable)var14, (String)"Tesselating block model");
            CrashReportCategory var13 = var12.func_85058_a("Block model being tesselated");
            CrashReportCategory.func_175750_a((CrashReportCategory)var13, (BlockPos)var3, (IBlockState)var2);
            var13.func_71507_a("Using AO", (Object)var10);
            throw new ReportedException(var12);
}
        var7.cancel();
}
}