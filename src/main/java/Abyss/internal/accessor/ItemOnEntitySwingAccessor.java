/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemOnEntitySwingAccessor {
    private static Accessor w = MethodAccessors.G(Item.class, "hitEntity", "onEntitySwing", new Class[]{EntityLivingBase.class, ItemStack.class});

    private ItemOnEntitySwingAccessor() {
}
    public static Accessor S() {
        return w;
}
}