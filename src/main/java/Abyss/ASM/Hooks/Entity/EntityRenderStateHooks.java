/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.AbyssClient;
import Abyss.event.events.EntityRenderStateEvent;
import Abyss.util.render.VisualSpoofRenderer;
import net.minecraft.entity.Entity;

public class EntityRenderStateHooks {
    private static final long public static void onRenderPre(Entity var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new EntityRenderStateEvent(0, '\u65f0', 2592973, var0), 18670087776179L);
}
}
    public static void onRenderPost(Entity var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new EntityRenderStateEvent(0, '\u65f0', 2592973, var0), 18670087776179L);
}
}
}