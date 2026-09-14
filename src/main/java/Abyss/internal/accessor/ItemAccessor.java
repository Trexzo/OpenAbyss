/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

final class ItemAccessor {
    private static Accessor B = MethodAccessors.G(Item.class, "func_179544_c_", "shouldCauseReequipAnimation", new Class[]{ItemStack.class, ItemStack.class, Boolean.TYPE});

    private ItemAccessor() {
}
    static Accessor p() {
        return B;
}
}