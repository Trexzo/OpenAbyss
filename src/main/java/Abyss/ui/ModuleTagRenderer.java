/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.ui;

import Abyss.AbyssClient;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ModuleTagRendererBinder;
import Abyss.event.events.ModuleTagEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.util.MinecraftRef;
import java.util.List;
import net.minecraft.client.Minecraft;

public class ModuleTagRenderer
implements EventSubscriber {
    private static String[] g;
    public static boolean X;
        private static Object[] e;
    private static Minecraft f;

    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x5B33DF57E69EL) >>> 32);
        ModuleTagRendererBinder.Q(var4, var3, this);
}
    public void onRender2D(long var1, Render2DEvent var3) {
        if (X) {
            ModuleTagEvent var8 = new ModuleTagEvent();
            AbyssClient.w.e(var8, 18670087776179L);
            if (var8.a()) {
                return;
}
            List<String> var9 = var8.i();
            for (int var10 = 0; var10 < var9.size(); ++var10) {
                ModuleTagRenderer.f.fontRendererObj.drawStringWithShadow(var9.get(var10), 20.0f, (float)(20 + var10 * ModuleTagRenderer.f.fontRendererObj.FONT_HEIGHT), -1);
}
}
}
    static {
        boolean var13 = false;
        e = new Object[8];
        g = new String[8];
        f = MinecraftRef.c((byte)(var13 ? 1 : 0), 0L);
        X = false;
}
}