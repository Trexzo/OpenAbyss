/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 */
package Abyss.internal;

import Abyss.internal.synthetic.BrokenBlockTrackerCtorMarker;
import Abyss.util.MiningConstants;
import net.minecraft.block.Block;

public final class BrokenBlockEntry {
    Block l;
    public long H;
    public int Q;
    private static long long[] Z;

    public boolean q() {
        int var1 = (int)MiningConstants.K;
        if (this.Q < var1) {
            return false;
}
        long var2 = 0L;
        for (int var4 = 0; var4 < var1; ++var4) {
            var2 += this.Z[var4];
}
        long var8 = var2 / (long)var1;
        float var6 = MiningConstants.g;
        for (int var7 = 0; var7 < var1; ++var7) {
            if (!((float)Math.abs(this.Z[var7] - var8) > var6)) continue;
            return false;
}
        return true;
}
    public BrokenBlockEntry(long var1, BrokenBlockTrackerCtorMarker var3) {
        this(a ^ var1 ^ 0x562819DE3FFEL);
        var1 = a ^ var1;
}
    public void R(long var1, Block var3) {
        int var6 = (int)MiningConstants.K;
        if (this.l == null) {
            this.l = var3;
        } else if (!this.l.equals(var3)) {
            this.Q = 0;
            this.l = var3;
}
        this.Z[this.Q % var6] = var1;
        ++this.Q;
        this.H = System.currentTimeMillis();
}
    private BrokenBlockEntry(long var1) {
        var1 = a ^ var1;
        this.Q = 0;
        this.H = 0L;
        this.Z = new long[5];
        this.l = null;
}
}