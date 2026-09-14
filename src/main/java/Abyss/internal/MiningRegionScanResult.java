/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package Abyss.internal;

import Abyss.enums.MiningRegionState;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.BlockPos;

public final class MiningRegionScanResult {
    public List<BlockPos> P;
    public MiningRegionState Z;
    public List<BlockPos> O;
    public MiningRegionState N;
    public MiningRegionState j;
    public List<BlockPos> b;
    public List<BlockPos> D = new ArrayList<BlockPos>();
    public MiningRegionState L;

    public MiningRegionScanResult() {
        this.b = new ArrayList<BlockPos>();
        this.P = new ArrayList<BlockPos>();
        this.O = new ArrayList<BlockPos>();
}
}