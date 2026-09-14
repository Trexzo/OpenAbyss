/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.BlockPos
 */
package Abyss.internal;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public final class MiningProgress {
    public final Block U;
    private final long M;
    public final BlockPos i;
    private final long x;

    public MiningProgress(BlockPos var1, Block var2, long var3, long var5) {
        this.i = var1;
        this.U = var2;
        this.x = var3;
        this.M = var5;
}
    public long b() {
        return this.M;
}
    public long R() {
        return this.x;
}
}