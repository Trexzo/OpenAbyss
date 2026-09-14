/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.RenderEntityItem
 *  net.minecraft.item.ItemStack
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.item.ItemStack;

public final class RenderEntityItemAccessorImpl {
    private static Accessor O = MethodAccessors.G(RenderEntityItem.class, "func_177078_a", "func_177078_a", new Class[]{ItemStack.class});

    public static int x(RenderEntityItem var0, ItemStack var1) {
        return Accessor.D(O, new Object[]{var0, var1});
}
}