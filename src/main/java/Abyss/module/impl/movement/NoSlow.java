/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.network.play.client.C09PacketHeldItemChange
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 */
package Abyss.module.impl.movement;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.NoSlowBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.event.events.RightClickMouseEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.module.impl.combat.AutoBlock;
import Abyss.module.impl.combat.BlockHit;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.player.Blink;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.ClientUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.packet.PacketManager;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow
extends Module
implements EventSubscriber {
    private static Map k;
    public static ModeSetting mode;
    public static PercentageSetting slowDown;
    public static ModeSetting swordMode;
    public static ModeSetting otherMode;
    public static BooleanSetting onlyEnableWhenAutoblock;
    public static BooleanSetting food;
    public static BooleanSetting potion;
    public static BooleanSetting sword;
    public static BooleanSetting bow;
    public static NumberSetting maxPingSpoof;
    public static NumberSetting whenToFinishEating;
    public static BooleanSetting nonBlinkSpeedBypass;
    public static BooleanSetting slowDownOnSlabs;
    public static NumberSetting amount;
    private static long[] g;
    
    private static Minecraft R;
        
    private static final int MAX_HOLD_TICKS = 600;
    private static int usingTicks;
    private static int airTicks;
    private static boolean onSlab;
    private boolean holding;
    private int holdTicks;
    private boolean rotating;
    private boolean releasedUseKey;

    public static boolean c(long var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (NoSlow.R.field_71439_g == null) {
            return false;
}
        ItemStack var4 = NoSlow.R.field_71439_g.func_70694_bm();
        if (var4 == null) {
            return false;
}
        if (!mode.R("VANILLA")) {
            if (!NoSlow.handles(var4)) {
                return false;
}
            if (mode.R("WATCHDOG_PREDICTION")) {
                return nonBlinkSpeedBypass.c() && usingTicks <= (int)maxPingSpoof.L();
}
            return true;
}
        Item var5 = var4.func_77973_b();
        if (var5 instanceof ItemSword) {
            if (swordMode.R("VANILLA")) {
                float var6 = 0.2f + (float)(100 - slowDown.k()) / 100.0f * 0.8f;
                float var7 = onlyEnableWhenAutoblock.c() ? (AutoBlock.G(51927146516111L) || BlockHit.noSlowLive() ? var6 : 0.2f) : var6;
                return var7 > 0.2f;
}
            return false;
}
        return var4.func_77988_m() > 0 ? otherMode.R("VANILLA") : true;
}
    public NoSlow(long var1) {
        super(a ^ var1 ^ 0x4D89FB6BE5FCL);
        this.declare("NoSlow", Category.Movement, "Change the slowdown when blocking sword, eating and pulling bow", new Setting[0]);
        var1 = a ^ var1;
}
    @Override
    public final void x(long var1, EventBus var3) {
        NoSlowBinder.G(var3, this);
}
    @Override
    public String g(long var1) {
        if (mode.R("VANILLA")) {
            return slowDown.k() + "%";
}
        return mode.R("WATCHDOG_PREDICTION") ? "Watchdog Prediction" : (mode.R("WATCHDOG") ? "Watchdog" : "Prediction");
}
    private static boolean handles(ItemStack var0) {
        if (var0 == null) {
            return false;
}
        Item var1 = var0.func_77973_b();
        if (var1 instanceof ItemFood) {
            return food.c();
}
        if (var1 instanceof ItemPotion) {
            return potion.c() && !ItemPotion.func_77831_g((int)var0.func_77960_j());
}
        if (var1 instanceof ItemBow) {
            return bow.c();
}
        return var1 instanceof ItemSword ? sword.c() : false;
}
    private static boolean holdingSword() {
        ItemStack var0 = NoSlow.R.field_71439_g == null ? null : NoSlow.R.field_71439_g.func_70694_bm();
        return var0 != null && var0.func_77973_b() instanceof ItemSword;
}
    public static boolean swordNoSlowLive() {
        if (!mode.R("VANILLA")) {
            return sword.c();
}
        return swordMode.R("VANILLA") && slowDown.k() < 100;
}
    private void hold() {
        if (this.holding) {
            return;
}
        try {
            if (Modules.J(Blink.class).o()) {
                return;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        if (PacketManager.e()) {
            return;
}
        PacketManager.M(true);
        this.holding = true;
        this.holdTicks = 0;
}
    private void release() {
        if (!this.holding) {
            return;
}
        PacketManager.j();
        PacketManager.M(false);
        this.holding = false;
        this.holdTicks = 0;
}
    private void syncUseKey() {
        if (!this.releasedUseKey) {
            return;
}
        this.releasedUseKey = false;
        try {
            KeyBindUtil.o(0L, NoSlow.R.field_71474_y.field_74313_G.func_151463_i());
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    private void stopRotating() {
        if (!this.rotating) {
            return;
}
        this.rotating = false;
        RotationManager.O(0L);
}
    @Override
    public void A(long var1) {
        this.reset();
}
    private void reset() {
        this.release();
        this.syncUseKey();
        this.stopRotating();
        usingTicks = 0;
        airTicks = 0;
        onSlab = false;
}
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1, long var2) {
        boolean var6;
        EntityPlayerSP var4 = NoSlow.R.field_71439_g;
        if (var4 == null || NoSlow.R.field_71441_e == null) {
            return;
}
        if (mode.R("VANILLA")) {
            this.reset();
            return;
}
        airTicks = var4.field_70122_E ? 0 : airTicks + 1;
        ItemStack var5 = var4.func_70694_bm();
        boolean bl = var6 = var4.func_71039_bw() && NoSlow.handles(var5) && !NoSlow.holdingSword();
        if (mode.R("WATCHDOG_PREDICTION")) {
            this.watchdogPrediction(var4, var6);
        } else if (mode.R("WATCHDOG")) {
            this.watchdog(var4, var1, var6);
        } else {
            usingTicks = var6 ? usingTicks + 1 : 0;
}
}
    private void watchdogPrediction(EntityPlayerSP var1, boolean var2) {
        if (!var2) {
            if (usingTicks > 0) {
                this.release();
                this.syncUseKey();
}
            usingTicks = 0;
            return;
}
        ++usingTicks;
        if (this.holding && !PacketManager.e()) {
            this.holding = false;
            this.holdTicks = 0;
}
        if (KillAura.H6 != null) {
            this.release();
        } else if (usingTicks > (int)maxPingSpoof.L()) {
            this.hold();
}
        if (this.holding && ++this.holdTicks > 600) {
            this.release();
}
        if (usingTicks > (int)whenToFinishEating.L()) {
            try {
                KeyBindUtil.A(0L, NoSlow.R.field_71474_y.field_74313_G.func_151463_i(), false);
                this.releasedUseKey = true;
}
            catch (Throwable throwable) {
                // empty catch block
}
}
}
    private void watchdog(EntityPlayerSP var1, UpdateWalkingPlayerEvent var2, boolean var3) {
        int n2 = usingTicks = var3 ? usingTicks + 1 : 0;
        if (slowDownOnSlabs.c() && !var1.func_71039_bw() && NoSlow.R.field_71441_e.func_180495_p(new BlockPos(var1.field_70165_t, var1.field_70163_u + var1.field_70181_x, var1.field_70161_v)).func_177230_c() != Blocks.field_150350_a) {
            onSlab = false;
}
        if (Math.abs(var1.field_70163_u - (double)Math.round(var1.field_70163_u)) > 0.03 && var1.field_70122_E) {
            onSlab = true;
}
        if (var3) {
            if (airTicks >= 2) {
                var1.field_70122_E = false;
            } else if (var1.field_70122_E && !onSlab) {
                var2.O(var2.s() + 0.001);
}
            if (onSlab && !var1.field_70122_E) {
                var1.field_70159_w *= 0.1;
                var1.field_70179_y *= 0.1;
}
}
}
    public void onRightClickMouse(RightClickMouseEvent var1, long var2) {
        if (!mode.R("WATCHDOG")) {
            return;
}
        EntityPlayerSP var4 = NoSlow.R.field_71439_g;
        if (var4 == null || var4.func_71039_bw()) {
            return;
}
        ItemStack var5 = var4.func_70694_bm();
        if (var5 == null || !NoSlow.handles(var5) || var5.func_77973_b() instanceof ItemSword) {
            return;
}
        if (airTicks != 0 && airTicks < 2 && !onSlab) {
            ClientUtil.t(0L, "You must start eating while in the air even with potions");
            var1.I(0, 0L);
            return;
}
        if (var4.field_70122_E) {
            var4.func_70664_aZ();
            var1.I(0, 0L);
}
}
    public void onPreUpdate(short var1, PreUpdateEvent var2, char var3, int var4) {
        boolean var7;
        if (!mode.R("WATCHDOG_PREDICTION")) {
            this.stopRotating();
            return;
}
        EntityPlayerSP var5 = NoSlow.R.field_71439_g;
        if (var5 == null) {
            return;
}
        ItemStack var6 = var5.func_70694_bm();
        boolean bl = var7 = !(!var5.func_71039_bw() || !NoSlow.handles(var6) || var6.func_77973_b() instanceof ItemSword || var6.func_77973_b() instanceof ItemBow || usingTicks <= 5 || var5.field_70122_E && airTicks <= 2 || KillAura.H6 != null || NoSlow.R.field_71474_y.field_74366_z.func_151470_d() || NoSlow.R.field_71474_y.field_74370_x.func_151470_d() || !this.rotating && RotationManager.X);
        if (!var7) {
            this.stopRotating();
            return;
}
        this.rotating = true;
        RotationManager.n(RotationMode.SILENT);
        RotationManager.v(var5.field_70177_z + 45.0f, 10.0f, 0L, 0.0f);
        RotationManager.f(var5.field_70125_A, 39.0f, 0.0f, 0L);
}
    public void onRedirectIsUsingItem(byte var1, int var2, int var3, RedirectIsUsingItemEvent var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = ((long)var1 << 56 | (long)var2 << 32 >>> 8 | (long)var3 << 40 >>> 40) ^ a;
        long var7 = var5 ^ 0x1E9992FBAAACL;
        if (!mode.R("VANILLA")) {
            this.predictionSlowDown(var4);
            return;
}
        if (NoSlow.R.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword) {
            float var9 = 0.2f + (float)(100 - slowDown.k()) / 100.0f * 0.8f;
            float var10 = onlyEnableWhenAutoblock.c() ? (AutoBlock.G(var7) || BlockHit.noSlowLive() ? var9 : 0.2f) : var9;
            switch (swordMode.Y()) {
                case "VANILLA": {
                    var4.W(var10);
                    break;
}
                default: {
                    var4.W(0.2f);
                    break;
}
}
        } else {
            switch (otherMode.Y()) {
                case "VANILLA": {
                    var4.W(0.2f + (float)(100 - slowDown.k()) / 100.0f * 0.8f);
                    break;
}
                default: {
                    var4.W(0.2f);
}
}
}
}
    private void predictionSlowDown(RedirectIsUsingItemEvent var1) {
        EntityPlayerSP var2 = NoSlow.R.field_71439_g;
        if (var2 == null) {
            return;
}
        ItemStack var3 = var2.func_70694_bm();
        if (var3 == null || !NoSlow.handles(var3)) {
            return;
}
        if (var3.func_77973_b() instanceof ItemSword) {
            this.swordSlowDown(var2, var1);
            return;
}
        if (mode.R("WATCHDOG_PREDICTION")) {
            if (usingTicks > (int)maxPingSpoof.L()) {
                var1.I(0, 0L);
}
        } else if (mode.R("WATCHDOG")) {
            if (!onSlab || var2.field_70122_E) {
                var1.I(0, 0L);
}
        } else if (var2.field_70173_aa % (int)amount.L() != 0 && var2.field_70122_E) {
            var1.I(0, 0L);
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void swordSlowDown(EntityPlayerSP var1, RedirectIsUsingItemEvent var2) {
        if (mode.R("WATCHDOG_PREDICTION")) {
            if (!NoSlow.combatOwnsSword()) {
                boolean[] var3 = NoSlow.combatFlags();
                try {
                    PacketManager.b(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    this.release();
                    PacketManager.b(new C08PacketPlayerBlockPlacement(var1.func_70694_bm()));
}
                finally {
                    NoSlow.combatFlags(var3);
}
}
            var2.I(0, 0L);
            return;
}
        if (mode.R("WATCHDOG")) {
            if (!NoSlow.combatOwnsSword()) {
                int var5 = var1.field_71071_by.field_70461_c;
                boolean[] var4 = NoSlow.combatFlags();
                try {
                    PacketManager.b(new C09PacketHeldItemChange((var5 + 1) % 9));
                    PacketManager.b(new C09PacketHeldItemChange(var5));
}
                finally {
                    NoSlow.combatFlags(var4);
}
}
            var2.I(0, 0L);
            return;
}
        if (var1.field_70173_aa % (int)amount.L() != 0 && var1.field_70122_E) {
            var2.I(0, 0L);
}
}
    private static boolean combatOwnsSword() {
        try {
            if (KillAura.H6 != null) {
                return true;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        try {
            return Modules.J(AutoBlock.class).o() && !AutoBlock.mode.R("NONE");
}
        catch (Throwable var0) {
            return false;
}
}
    private static boolean[] combatFlags() {
        return new boolean[]{OutgoingPacketState.E, OutgoingPacketState.P, OutgoingPacketState.h, OutgoingPacketState.T, OutgoingPacketState.C, OutgoingPacketState.R, OutgoingPacketState.O};
}
    private static void combatFlags(boolean[] var0) {
        OutgoingPacketState.E = var0[0];
        OutgoingPacketState.P = var0[1];
        OutgoingPacketState.h = var0[2];
        OutgoingPacketState.T = var0[3];
        OutgoingPacketState.C = var0[4];
        OutgoingPacketState.R = var0[5];
        OutgoingPacketState.O = var0[6];
}
    static {
        R = MinecraftRef.c((byte)0, 0L);
        mode = new ModeSetting("Mode", "VANILLA", "WATCHDOG_PREDICTION", "WATCHDOG", "PREDICTION");
        slowDown = new PercentageSetting("Slow-down", 0);
        swordMode = new ModeSetting("Sword-mode", "VANILLA", "NONE");
        otherMode = new ModeSetting("Other-mode", "NONE", "VANILLA");
        onlyEnableWhenAutoblock = new BooleanSetting("Only-enable-when-autoblock", true);
        food = new BooleanSetting("Food", true);
        potion = new BooleanSetting("Potion", true);
        sword = new BooleanSetting("Sword", false);
        bow = new BooleanSetting("Bow", true);
        maxPingSpoof = new NumberSetting("Max-ping-spoof", 8.0f, 0.0f, 30.0f, 1.0f);
        whenToFinishEating = new NumberSetting("When-to-finish-eating", 30.0f, 20.0f, 36.0f, 1.0f);
        nonBlinkSpeedBypass = new BooleanSetting("Non-blink-speed-bypass", true);
        slowDownOnSlabs = new BooleanSetting("Slow-down-on-slabs", true);
        amount = new NumberSetting("Amount", 2.0f, 2.0f, 5.0f, 1.0f);
}
}