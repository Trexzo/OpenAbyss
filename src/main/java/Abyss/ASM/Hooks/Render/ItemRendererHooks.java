/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemMap
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MathHelper
 */
package Abyss.ASM.Hooks.Render;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.AbyssClient;
import Abyss.event.events.RenderItemInFirstPersonEvent;
import Abyss.internal.accessor.ItemRendererAccessor;
import Abyss.module.ModuleManager;
import Abyss.module.Modules;
import Abyss.module.impl.combat.BlockHit;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.visual.Animations;
import Abyss.module.impl.world.Scaffold;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemRendererHooks {
        private static String b;
    
    private static long[] c;

    public static void onFunc_178110_a(CallbackInfo var0) {
        if (Animations.noRotationsEffect.c() && ModuleManager.d.o()) {
            GlStateManager.func_179114_b((float)0.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.func_179114_b((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            var0.cancel();
}
}
    private static boolean shouldSpoofScaffoldItem() {
        Scaffold var0 = Modules.J(Scaffold.class);
        return var0 != null && var0.o() && Scaffold.fakeItem != null && Scaffold.fakeItem.c();
}
    public static boolean onUpdateEquippedItemLastPart(float var0) {
        return ItemRendererHooks.shouldSpoofScaffoldItem() || Animations.noEquipReset.c() && ModuleManager.d.o() && (KillAura.a || BlockHit.N || ModuleManager.I != null && ModuleManager.I.o()) || var0 < 0.1f;
}
    public static float transformFirstPersonItemEquipProgress(float var0) {
        return !Animations.noEquipReset.c() || !ModuleManager.d.o() || !KillAura.a && !BlockHit.N && (ModuleManager.I == null || !ModuleManager.I.o()) ? var0 : 0.0f;
}
    public static ItemStack spoofScaffoldItemStack(Minecraft var0, ItemStack var1) {
        if (ItemRendererHooks.shouldSpoofScaffoldItem() && var0 != null && var0.field_71439_g != null) {
            int var4 = Modules.J(Scaffold.class).q();
            return var4 >= 0 && var4 < 9 ? var0.field_71439_g.field_71071_by.func_70301_a(var4) : var1;
}
        return var1;
}
    public static void renderItemInFirstPerson(ItemRenderer var0, Minecraft var1, ItemStack var2, float var3, float var4, float var5, CallbackInfo var6) {
        if (ModuleManager.d.o()) {
            Animations.C();
            Animations.U();
            Animations.J();
}
        if (ModuleManager.d.o() && !Animations.mode.R(b)) {
            float var14 = 1.0f - (var3 + (var4 - var3) * var5);
            EntityPlayerSP var15 = var1.field_71439_g;
            float var16 = var15.func_70678_g(var5);
            float var17 = var15.field_70127_C + (var15.field_70125_A - var15.field_70127_C) * var5;
            float var18 = var15.field_70126_B + (var15.field_70177_z - var15.field_70126_B) * var5;
            ItemRendererAccessor.z(var0, var17, var18);
            ItemRendererAccessor.k(var0, (AbstractClientPlayer)var15);
            ItemRendererAccessor.x(var0, var15, var5);
            GlStateManager.func_179091_B();
            GlStateManager.func_179094_E();
            if (var2 == null) {
                if (!var15.func_82150_aj()) {
                    ItemRendererAccessor.q(var0, (AbstractClientPlayer)var15, var14, var16);
}
            } else {
                if (var2.func_77973_b() instanceof ItemMap) {
                    ItemRendererAccessor.Z(var0, (AbstractClientPlayer)var15, var17, var14, var16);
                } else if (var15.func_71052_bv() > 0) {
                    EnumAction var19 = var2.func_77975_n();
                    RenderItemInFirstPersonEvent var20 = new RenderItemInFirstPersonEvent(var19, var14, 7675, var5, var16, '\u4bd9', '\ub6bc', var2);
                    AbyssClient.w.e(var20, 18670087776179L);
                    if (!var20.a()) {
                        if (var19 == EnumAction.NONE) {
                            ItemRendererAccessor.s(var0, var14, 0.0f);
                        } else if (var19 == EnumAction.EAT || var19 == EnumAction.DRINK) {
                            ItemRendererAccessor.v(var0, (AbstractClientPlayer)var15, var5);
                            ItemRendererAccessor.s(var0, 0.2f, var16);
                            GlStateManager.func_179109_b((float)0.0f, (float)0.3f, (float)0.0f);
                        } else if (var19 == EnumAction.BLOCK) {
                            GlStateManager.func_179109_b((float)0.56f, (float)-0.52f, (float)-0.72f);
                            GlStateManager.func_179109_b((float)0.0f, (float)-0.05f, (float)0.0f);
                            GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                            float var21 = MathHelper.func_76126_a((float)(var16 * var16 * (float)Math.PI));
                            float var22 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)var16) * (float)Math.PI));
                            GlStateManager.func_179114_b((float)(var21 * -20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                            GlStateManager.func_179114_b((float)(var22 * -20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                            GlStateManager.func_179114_b((float)(var22 * -80.0f), (float)1.0f, (float)0.0f, (float)0.0f);
                            GlStateManager.func_179152_a((float)0.35f, (float)0.35f, (float)0.35f);
                            ItemRendererAccessor.e(var0);
                            GlStateManager.func_179109_b((float)-0.5f, (float)0.2f, (float)0.0f);
                        } else if (var19 == EnumAction.BOW) {
                            ItemRendererAccessor.s(var0, 0.2f, var16);
                            ItemRendererAccessor.E(var0, var5, (AbstractClientPlayer)var15);
                            GlStateManager.func_179109_b((float)0.0f, (float)0.2f, (float)0.0f);
}
}
                } else {
                    ItemRendererAccessor.U(var0, var16);
                    ItemRendererAccessor.s(var0, var14, var16);
}
                Animations.U();
                var0.func_178099_a((EntityLivingBase)var15, var2, ItemCameraTransforms.TransformType.FIRST_PERSON);
}
            GlStateManager.func_179121_F();
            GlStateManager.func_179101_C();
            RenderHelper.func_74518_a();
            var6.cancel();
}
}
    public static boolean onUpdateEquippedItemLastPartAllowed() {
        return ItemRendererHooks.shouldSpoofScaffoldItem() || Animations.noEquipReset.c() && ModuleManager.d.o() && (KillAura.a || BlockHit.N || ModuleManager.I != null && ModuleManager.I.o());
}
    public static int spoofScaffoldItemSlot(Minecraft var0, int var1) {
        if (ItemRendererHooks.shouldSpoofScaffoldItem() && var0 != null && var0.field_71439_g != null) {
            int var4 = Modules.J(Scaffold.class).q();
            return var4 >= 0 && var4 < 9 ? var4 : var1;
}
        return var1;
}
    static {
        b = "NONE";
        e = new HashMap(13);
        c = new long[]{2015442937976616169L, -1488955058342560494L};
}
}