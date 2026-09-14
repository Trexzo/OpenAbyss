/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemBucket
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.FieldAccessors;
import Abyss.internal.accessor.TypedValueStore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBucket;

public final class BlockAccessor {
    private static TypedValueStore c = FieldAccessors.X(ItemBucket.class, "isFull", "isFull");

    public static Block o(int var0, ItemBucket var1, short var2) {
        return (Block)c.v(var1);
}
}