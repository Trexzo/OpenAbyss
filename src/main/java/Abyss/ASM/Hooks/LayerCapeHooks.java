/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 */
package Abyss.ASM.Hooks;

import Abyss.AbyssClient;
import Abyss.event.events.PostRenderCapeEvent;
import Abyss.event.events.PreRenderCapeEvent;
import Abyss.util.render.VisualSpoofRenderer;
import net.minecraft.client.entity.AbstractClientPlayer;

public class LayerCapeHooks {
    private static final long public static void onRenderPost(AbstractClientPlayer var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new PostRenderCapeEvent(var0), 18670087776179L);
}
}
    public static void onRenderPre(AbstractClientPlayer var0) {
        if (!VisualSpoofRenderer.H()) {
            AbyssClient.w.e(new PreRenderCapeEvent(0, 413183256, 697, var0), 18670087776179L);
}
}
}