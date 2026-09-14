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

final class ItemStackAccessor {
    private static Accessor M = MethodAccessors.O(Item.class, new Class[]{ItemStack.class}, new String[]{"getColorFromDamage", "getMetadata"});
    private static Accessor S = MethodAccessors.O(Item.class, new Class[]{Integer.TYPE}, new String[]{"getMetadata", "getMetadata"});

    static Accessor q() {
        return S;
}
    private ItemStackAccessor() {
}
    static Accessor j() {
        return M;
}
}