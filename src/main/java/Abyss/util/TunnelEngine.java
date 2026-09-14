/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.AbyssClient;
import Abyss.internal.BrokenBlockTracker;
import Abyss.internal.MiningEngine;
import Abyss.internal.MiningRenderSubscriber;
import Abyss.module.impl.world.AutoTunnel;
import Abyss.util.AutoToolService;
import Abyss.util.MiningConstants;

public class TunnelEngine {
    private static long private static long[] e;
    private static boolean j;
    
    public static void z(long var0) {
        long var2 = var0 ^ 0x25484A76634L;
        if (!j) {
            j = true;
            AbyssClient.w.s(MiningEngine.uq, var2);
            AbyssClient.w.s(AutoToolService.K, var2);
            AbyssClient.w.s(BrokenBlockTracker.m, var2);
            AbyssClient.w.s(new MiningRenderSubscriber(), var2);
}
}
    public static void V(long var0) {
        if (AutoTunnel.mode != null && AutoTunnel.sneakMode != null) {
            switch (AutoTunnel.mode.Y()) {
                case "NORMAL": {
                    MiningConstants.J = 0;
                    MiningConstants.v = false;
                    break;
}
                case "GAP_ALT": {
                    MiningConstants.J = 2;
                    MiningConstants.v = false;
                    break;
}
                default: {
                    MiningConstants.J = 1;
                    MiningConstants.v = true;
}
}
            switch (AutoTunnel.sneakMode.Y()) {
                case "KEEP": {
                    MiningConstants.w = 0;
                    break;
}
                case "NONE": {
                    MiningConstants.w = 2;
                    break;
}
                default: {
                    MiningConstants.w = 1;
}
}
            MiningConstants.r = AutoTunnel.autoTool != null && AutoTunnel.autoTool.c();
            MiningConstants.A = AutoTunnel.autoTurn != null && AutoTunnel.autoTurn.c();
            MiningConstants.x = AutoTunnel.autoBack != null && AutoTunnel.autoBack.c();
            MiningConstants.k = AutoTunnel.ownedChestsOnly != null && AutoTunnel.ownedChestsOnly.c();
            MiningConstants.q = AutoTunnel.sideOffsetScan != null && AutoTunnel.sideOffsetScan.c();
            MiningConstants.gapAltOnlyStone = AutoTunnel.gapAltOnlyStone != null && AutoTunnel.gapAltOnlyStone.c();
            MiningConstants.userManualScreenMove = AutoTunnel.userManualScreenMove == null || AutoTunnel.userManualScreenMove.c();
            MiningConstants.C = AutoTunnel.turnSpeed == null ? MiningConstants.C : AutoTunnel.turnSpeed.L();
            MiningConstants.X = AutoTunnel.stuckTimeout == null ? MiningConstants.X : AutoTunnel.stuckTimeout.L();
            MiningConstants.s = AutoTunnel.noBreakTimeout == null ? MiningConstants.s : AutoTunnel.noBreakTimeout.L();
            MiningConstants.Q = AutoTunnel.unsneakChance == null ? MiningConstants.Q : (float)AutoTunnel.unsneakChance.k();
            MiningConstants.e = AutoTunnel.unsneakDuration == null ? MiningConstants.e : AutoTunnel.unsneakDuration.L();
            MiningConstants.H = AutoTunnel.chestScanRadius == null ? MiningConstants.H : AutoTunnel.chestScanRadius.L();
}
}
    static {
        j = false;
}
}