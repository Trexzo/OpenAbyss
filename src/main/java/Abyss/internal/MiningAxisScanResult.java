/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package Abyss.internal;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.BlockPos;

public final class MiningAxisScanResult {
    private static long a = 55070367350149L;

    public List<BlockPos> z;
    public int S;
    public int Z;
    public int c;
    public BlockPos x;
    public List<BlockPos> n;
    public int t;
    public BlockPos C;

    public MiningAxisScanResult(long var1) {
        var1 = a ^ var1;
        this.n = new ArrayList<BlockPos>();
        this.z = new ArrayList<BlockPos>();
        this.S = -1;
        this.c = -1;
}
}