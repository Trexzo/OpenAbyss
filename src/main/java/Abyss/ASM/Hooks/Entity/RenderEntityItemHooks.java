/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.client.renderer.entity.RenderEntityItem
 *  net.minecraft.client.resources.model.IBakedModel
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MathHelper
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.internal.accessor.RenderEntityItemAccessorImpl;
import Abyss.module.ModuleManager;
import Abyss.module.impl.visual.ItemScale;
import Abyss.util.LunarClientDetector;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class RenderEntityItemHooks {
    private static final Minecraft M;
    private static final long public static void onRenderEntityItem(RenderEntityItem var0, EntityItem var1, double var2, double var4, double var6, float var8, IBakedModel var9, CallbackInfoReturnable<Integer> var10) {
        if (ModuleManager.v.o()) {
            ItemStack var18 = var1.func_92059_d();
            Item var19 = var18.func_77973_b();
            if (var19 == null) {
                var10.setReturnValue(0);
                return;
}
            boolean var20 = var9.func_177556_c();
            int var21 = RenderEntityItemAccessorImpl.x(var0, var18);
            float var22 = MathHelper.func_76126_a((float)(((float)var1.func_174872_o() + var8) / 10.0f + var1.field_70290_d)) * 0.1f + 0.1f;
            float var23 = var9.func_177552_f().func_181688_b((ItemCameraTransforms.TransformType)ItemCameraTransforms.TransformType.GROUND).field_178363_d.y;
            GlStateManager.func_179109_b((float)((float)var2), (float)((float)var4 + var22 + 0.25f * var23), (float)((float)var6));
            if (var20 || MinecraftRef.c((byte)0, (long)0L).func_175598_ae().field_78733_k != null) {
                if (var9.func_177556_c()) {
                    float var24 = (((float)var1.func_174872_o() + var8) / 20.0f + var1.field_70290_d) * 57.295776f;
                    GlStateManager.func_179114_b((float)var24, (float)0.0f, (float)1.0f, (float)0.0f);
                } else {
                    GlStateManager.func_179114_b((float)(180.0f - RenderEntityItemHooks.M.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
                    GlStateManager.func_179114_b((float)RenderEntityItemHooks.getItemPitchRotation(), (float)1.0f, (float)0.0f, (float)0.0f);
}
}
            if (!var20) {
                float var27 = -0.0f * (float)(var21 - 1) * 0.5f;
                float var25 = -0.0f * (float)(var21 - 1) * 0.5f;
                float var26 = -0.046875f * (float)(var21 - 1) * 0.5f;
                GlStateManager.func_179109_b((float)var27, (float)var25, (float)var26);
}
            float var28 = -0.0f * (float)(var21 - 1) * 0.5f;
            float var29 = -0.0f * (float)(var21 - 1) * 0.5f;
            float var30 = -0.046875f * (float)(var21 - 1) * 0.5f;
            if (ItemScale.c(var18)) {
                GlStateManager.func_179109_b((float)var28, (float)(var29 + ItemScale.scale.L() / 8.0f), (float)var30);
                GlStateManager.func_179152_a((float)ItemScale.scale.L(), (float)ItemScale.scale.L(), (float)ItemScale.scale.L());
}
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            var10.setReturnValue(var21);
            var10.cancel();
}
}
    private static float getItemPitchRotation() {
        if (LunarClientDetector.q(0L)) {
            return -RenderEntityItemHooks.M.func_175598_ae().field_78732_j;
}
        return RenderEntityItemHooks.M.field_71474_y.field_74320_O == 2 ? RenderEntityItemHooks.M.func_175598_ae().field_78732_j : -RenderEntityItemHooks.M.func_175598_ae().field_78732_j;
}
    static {
        boolean var2 = false;
        M = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}