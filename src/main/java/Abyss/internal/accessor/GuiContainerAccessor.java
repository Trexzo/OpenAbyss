/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.item.ItemStack
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.FieldAccessors;
import Abyss.internal.accessor.TypedValueStore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public final class GuiContainerAccessor {
    private static TypedValueStore E = FieldAccessors.X(GuiContainer.class, "draggedStack", "draggedStack");

    public static void S(GuiContainer var0, ItemStack var1) {
        E.d(var0, var1);
}
}