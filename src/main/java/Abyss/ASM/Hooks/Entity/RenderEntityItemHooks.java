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
    public static void onRenderEntityItem(RenderEntityItem var0, EntityItem var1, double var2, double var4, double var6, float var8, IBakedModel var9, CallbackInfoReturnable<Integer> var10) {
        if (ModuleManager.v.o()) {
            ItemStack var18 = var1.getEntityItem();
            Item var19 = var18.getItem();
            if (var19 == null) {
                var10.setReturnValue(0);
                return;
}
            boolean var20 = var9.isGui3d();
            int var21 = RenderEntityItemAccessorImpl.x(var0, var18);
            float var22 = MathHelper.sin((float)(((float)var1.getAge() + var8) / 10.0f + var1.hoverStart)) * 0.1f + 0.1f;
            float var23 = var9.getItemCameraTransforms().getTransform((ItemCameraTransforms.TransformType)ItemCameraTransforms.TransformType.GROUND).scale.y;
            GlStateManager.translate((float)((float)var2), (float)((float)var4 + var22 + 0.25f * var23), (float)((float)var6));
            if (var20 || MinecraftRef.c((byte)0, (long)0L).getRenderManager().options != null) {
                if (var9.isGui3d()) {
                    float var24 = (((float)var1.getAge() + var8) / 20.0f + var1.hoverStart) * 57.295776f;
                    GlStateManager.rotate((float)var24, (float)0.0f, (float)1.0f, (float)0.0f);
                } else {
                    GlStateManager.rotate((float)(180.0f - RenderEntityItemHooks.M.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                    GlStateManager.rotate((float)RenderEntityItemHooks.getItemPitchRotation(), (float)1.0f, (float)0.0f, (float)0.0f);
}
}
            if (!var20) {
                float var27 = -0.0f * (float)(var21 - 1) * 0.5f;
                float var25 = -0.0f * (float)(var21 - 1) * 0.5f;
                float var26 = -0.046875f * (float)(var21 - 1) * 0.5f;
                GlStateManager.translate((float)var27, (float)var25, (float)var26);
}
            float var28 = -0.0f * (float)(var21 - 1) * 0.5f;
            float var29 = -0.0f * (float)(var21 - 1) * 0.5f;
            float var30 = -0.046875f * (float)(var21 - 1) * 0.5f;
            if (ItemScale.c(var18)) {
                GlStateManager.translate((float)var28, (float)(var29 + ItemScale.scale.L() / 8.0f), (float)var30);
                GlStateManager.scale((float)ItemScale.scale.L(), (float)ItemScale.scale.L(), (float)ItemScale.scale.L());
}
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            var10.setReturnValue(var21);
            var10.cancel();
}
}
    private static float getItemPitchRotation() {
        if (LunarClientDetector.q(0L)) {
            return -RenderEntityItemHooks.M.getRenderManager().playerViewX;
}
        return RenderEntityItemHooks.M.gameSettings.thirdPersonView == 2 ? RenderEntityItemHooks.M.getRenderManager().playerViewX : -RenderEntityItemHooks.M.getRenderManager().playerViewX;
}
    static {
        boolean var2 = false;
        M = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}