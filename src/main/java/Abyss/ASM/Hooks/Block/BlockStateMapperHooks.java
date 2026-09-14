/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 */
package Abyss.ASM.Hooks.Block;

import java.util.Set;
import net.minecraft.init.Blocks;

public class BlockStateMapperHooks {
    public static boolean getRenderType(Set var0, Object var1) {
        return var1 != Blocks.field_180401_cv && var0.contains(var1);
}
}