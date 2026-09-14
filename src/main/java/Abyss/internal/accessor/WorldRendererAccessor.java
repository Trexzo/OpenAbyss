/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.WorldRenderer
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.client.renderer.WorldRenderer;

public final class WorldRendererAccessor {
    private static Accessor s = MethodAccessors.G(WorldRenderer.class, "getColorIndex", "getColorIndex", new Class[]{Integer.TYPE});

    public static int t(WorldRenderer var0, int var1) {
        return Accessor.D(s, new Object[]{var0, var1});
}
}