/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 */
package Abyss.module.impl.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockInPlacement {
    public final float j;
    public final BlockPos h;
    public final float S;
    public final EnumFacing C;

    public BlockInPlacement(BlockPos var1, EnumFacing var2, float var3, float var4) {
        this.h = var1;
        this.C = var2;
        this.j = var3;
        this.S = var4;
}
}