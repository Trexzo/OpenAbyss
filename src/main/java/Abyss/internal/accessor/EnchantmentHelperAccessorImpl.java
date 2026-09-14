/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.item.ItemStack
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;

public final class EnchantmentHelperAccessorImpl {
    private static Accessor N = MethodAccessors.C(EnchantmentHelper.class, new Class[]{ItemStack.class, EnumCreatureAttribute.class}, new String[]{"getModifierForCreature", "getModifierForCreature"});

    public static float P(ItemStack var0, EnumCreatureAttribute var1) {
        return Accessor.t(N, new Object[]{var0, var1});
}
}