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
    public static void renderModel(IBlockAccess var0, IBakedModel var1, IBlockState var2, BlockPos var3, WorldRenderer var4, boolean var5, BlockModelRenderer var6, CallbackInfoReturnable<Boolean> var7) {
        boolean var10 = var2.getBlock().getLightValue() == 0 && var1.isAmbientOcclusion();
        try {
            Block var11 = var2.getBlock();
            boolean var15 = var10 ? var6.renderModelAmbientOcclusion(var0, var1, var11, var3, var4, var5) : var6.renderModelStandard(var0, var1, var11, var3, var4, var5);
            var7.setReturnValue(var15);
            var7.cancel();
}
        catch (Throwable var14) {
            CrashReport var12 = CrashReport.makeCrashReport((Throwable)var14, (String)"Tesselating block model");
            CrashReportCategory var13 = var12.makeCategory("Block model being tesselated");
            CrashReportCategory.addBlockInfo((CrashReportCategory)var13, (BlockPos)var3, (IBlockState)var2);
            var13.addCrashSection("Using AO", (Object)var10);
            throw new ReportedException(var12);
}
        var7.cancel();
}
}