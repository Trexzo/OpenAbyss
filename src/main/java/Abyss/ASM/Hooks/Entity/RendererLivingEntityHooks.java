/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.AbyssClient;
import Abyss.event.events.PostRenderEvent;
import Abyss.event.events.PreRenderEvent;
import Abyss.module.impl.visual_utility.NameTags;
import Abyss.util.render.VisualSpoofRenderer;
import net.minecraft.entity.EntityLivingBase;

public class RendererLivingEntityHooks {
    private static final long public static void canRenderName(EntityLivingBase var0, CallbackInfoReturnable<Boolean> var1) {
        if (NameTags.x.contains(var0)) {
            var1.setReturnValue(false);
            var1.cancel();
}
}
    public static void onPostRender(EntityLivingBase var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new PostRenderEvent(var0), 18670087776179L);
}
}
    public static void onPreRender(EntityLivingBase var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new PreRenderEvent(var0), 18670087776179L);
}
}
}