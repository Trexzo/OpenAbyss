/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 */
package Abyss.ASM.Hooks.Render;

import Abyss.AbyssClient;
import Abyss.event.events.PostRenderModelBipedEvent;
import Abyss.event.events.PreRenderModelBipedEvent;
import Abyss.util.ClientUtil;
import Abyss.util.MathUtil;
import Abyss.util.RotationManager;
import Abyss.util.render.VisualSpoofRenderer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class ModelBipedHooks {
    public static void onRenderPre(Entity var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new PreRenderModelBipedEvent(var0), 18670087776179L);
}
}
    public static void onRenderPost(Entity var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new PostRenderModelBipedEvent(var0), 18670087776179L);
}
}
    public static float modifyHeadPitch(float var0, Entity var1) {
        return var1 instanceof EntityPlayerSP ? MathUtil.r(RotationManager.F, RotationManager.K, ClientUtil.b((long)75703014522979L).renderPartialTicks) : var0;
}
}