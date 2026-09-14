/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.world;

import Abyss.internal.MiningEngine;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.RotationManager;
import Abyss.util.TunnelEngine;
import java.io.UnsupportedEncodingException;

public class AutoTunnel
extends Module {
    public static NumberSetting stuckTimeout;
    public static NumberSetting noBreakTimeout;
    public static BooleanSetting autoBack;
    public static BooleanSetting autoTurn;
    public static BooleanSetting sideOffsetScan;
    private static final long public static ModeSetting mode;
    public static NumberSetting chestScanRadius;
    public static BooleanSetting ownedChestsOnly;
    public static PercentageSetting unsneakChance;
    public static BooleanSetting autoTool;
    public static NumberSetting turnSpeed;
    public static BooleanSetting userManualScreenMove;
    public static NumberSetting unsneakDuration;
    public static BooleanSetting gapAltOnlyStone;
    public static ModeSetting sneakMode;
    public static PercentageSetting rotationSmoothing;

    public AutoTunnel(long var1) {
        super(0x32736CDC645CL ^ var1 ^ 0x250BEFA13D36L);
        this.declare("AutoTunnel", Category.World, "Automatically mine tunnel in MegaWalls", new Setting[0]);
        var1 = 0x32736CDC645CL ^ var1;
}
    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0x3A38010D60CAL;
        long var5 = (var1 ^ 0x2FD59437728L) >>> 32;
        int var7 = (int)((var1 ^ 0x2FD59437728L) << 32 >>> 32);
        long var8 = var1 ^ 0xF9FDC7ACA5AL;
        MiningEngine.uq.B(var5, var7);
        RotationManager.O(var3);
        KeyBindUtil.o(var8, AutoTunnel.f.field_71474_y.field_74351_w.func_151463_i());
        KeyBindUtil.o(var8, AutoTunnel.f.field_71474_y.field_74368_y.func_151463_i());
        KeyBindUtil.o(var8, AutoTunnel.f.field_71474_y.field_74312_F.func_151463_i());
        KeyBindUtil.o(var8, AutoTunnel.f.field_71474_y.field_74314_A.func_151463_i());
        KeyBindUtil.o(var8, AutoTunnel.f.field_71474_y.field_74311_E.func_151463_i());
}
    private static void a() {
}
    @Override
    public void i(long var1) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var3 = (int)((var1 ^ 0x6EE39B8018BBL) >>> 48);
        long var4 = (var1 ^ 0x6EE39B8018BBL) << 16 >>> 16;
        long var8 = var1 ^ 0x666CD61694E8L;
        TunnelEngine.V(0L);
        MiningEngine.uq.a(var8);
        if (!MiningEngine.uq.h()) {
            this.u((short)var3, var4);
}
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    static {
        AutoTunnel.a();
        stuckTimeout = new NumberSetting("Stuck-timeout", 5.0f, 1.0f, 20.0f, 0.1f);
        sideOffsetScan = new BooleanSetting("Side-offset-scan", false);
        noBreakTimeout = new NumberSetting("No-break-timeout", 5.0f, 1.0f, 20.0f, 0.1f);
        sneakMode = new ModeSetting("Sneak-mode", false, "NONE", "RANDOM", "KEEP", "RANDOM", "NONE");
        mode = new ModeSetting("Mode", "STAIRCASE", "NORMAL", "STAIRCASE", "GAP_ALT");
        unsneakChance = new PercentageSetting("Unsneak-chance", 60);
        chestScanRadius = new NumberSetting("Chest-scan-radius", 50.0f, 1.0f, 100.0f, 1.0f);
        autoTurn = new BooleanSetting("Auto-turn", true);
        autoTool = new BooleanSetting("Auto-tool", true);
        turnSpeed = new NumberSetting("Turn-speed", 80.0f, 1.0f, 180.0f, 1.0f);
        autoBack = new BooleanSetting("Auto-back", false);
        unsneakDuration = new NumberSetting("Unsneak-duration", 150.0f, 0.0f, 1000.0f, 1.0f);
        ownedChestsOnly = new BooleanSetting("Owned-chests-only", true);
        rotationSmoothing = new PercentageSetting("Rotation-smoothing", 100);
        gapAltOnlyStone = new BooleanSetting("GapAlt-only-stone", false);
        userManualScreenMove = new BooleanSetting("User-manual-screen-move", true);
}
}