/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.EntityRenderer
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.client.renderer.EntityRenderer;

public final class EntityRendererAccessor {
    private static Accessor k = MethodAccessors.G(EntityRenderer.class, "setupCameraTransform", "setupCameraTransform", new Class[]{Float.TYPE, Integer.TYPE});

    public static void k(EntityRenderer var0, float var1, int var2) {
        Accessor.v(k, new Object[]{var0, Float.valueOf(var1), var2});
}
}