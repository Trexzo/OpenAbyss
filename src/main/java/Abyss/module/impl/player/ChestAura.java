/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockChest
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.util.BlockPos
 */
package Abyss.module.impl.player;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ChestAuraBinder;
import Abyss.event.events.CloseScreenEvent;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.BlockUtil;
import Abyss.util.CombatUtil;
import Abyss.util.EntityUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.TimerUtil;
import Abyss.util.packet.OutgoingPacketState;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ChestAura
extends PriorityModule
implements EventSubscriber {
    private static Map e;
    private static String[] d;
    private long g;
    private final TimerUtil O;
    private static long b = 34799082319422L;
    private boolean J;
    public static NumberSetting range;
    public static BooleanSetting throughWall;
        private BlockPos E;
    private BlockPos N;
    private static Map n;
    private long Y;
    private static long[] h;
    private static String[] B;
    public static NumberSetting disableWhenPlayersInRange;
    private static Object[] x;
        private static Map u;
    private boolean p;
    public static BooleanSetting rotation;
    public static List<BlockPos> H;
    private final TimerUtil y;
    public static ModeSetting moveFix;
    private boolean s;

    private void l(BlockPos var1) {
        if (ChestAura.f.theWorld != null && ChestAura.f.theWorld.getBlockState(var1).getBlock() instanceof BlockChest) {
            this.T(var1);
}
}
    private BlockPos getDistanceSqToCenter(char var1, double var2, int var4, char var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var6 = ((long)var1 << 48 | (long)var4 << 32 >>> 16 | (long)var5 << 48 >>> 48) ^ b;
        int var8 = (int)((var6 ^ 0x368850BA5DCAL) >>> 32);
        int var9 = (int)((var6 ^ 0x368850BA5DCAL) << 32 >>> 32);
        int var10 = (int)Math.ceil(var2);
        BlockPos var11 = null;
        double var12 = Double.MAX_VALUE;
        double var14 = var2 * var2;
        BlockPos var16 = new BlockPos(ChestAura.f.thePlayer.posX, ChestAura.f.thePlayer.posY, ChestAura.f.thePlayer.posZ);
        for (int var17 = -var10; var17 <= var10; ++var17) {
            for (int var18 = -var10; var18 <= var10; ++var18) {
                for (int var19 = -var10; var19 <= var10; ++var19) {
                    double var21;
                    BlockPos var20 = var16.add(var17, var18, var19);
                    if (!this.p(var8, var9, var20) || !((var21 = ChestAura.f.thePlayer.getDistanceSqToCenter(var20)) <= var14) || !(var21 < var12)) continue;
                    var12 = var21;
                    var11 = var20;
}
}
}
        return var11;
}
    private void b(BlockPos var1) {
        if (var1 != null) {
            this.T(var1);
            this.l(var1.north());
            this.l(var1.south());
            this.l(var1.east());
            this.l(var1.west());
}
}
    private void T(BlockPos var1) {
        if (var1 != null && !H.contains(var1)) {
            H.add(var1);
}
}
    private static String b(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                byte var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }
    private void p(byte var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 56 | 0x57045DF7C2D1L) ^ b;
        int var6 = (int)((var4 ^ 0x3409E1DE6331L) >>> 56);
        int var7 = (int)((var4 ^ 0x3409E1DE6331L) << 8 >>> 32);
        for (TileEntity var10 : ChestAura.f.theWorld.loadedTileEntityList) {
            if (!(var10 instanceof TileEntityChest)) continue;
            TileEntityChest var11 = (TileEntityChest)var10;
            BlockPos var12 = var11.getPos();
            if (!H.contains(var12) && var11.numPlayersUsing > 0) {
                this.b(var12);
}
            if (H.contains(var12) || !BlockUtil.Y((byte)var6, var12, var7)) continue;
            this.b(var12);
}
}
    public void onPlayerRightClick(short var1, int var2, short var3, PlayerRightClickEvent var4) {
        BlockPos var7;
        if (ChestAura.f.theWorld != null && ChestAura.f.theWorld.getBlockState(var7 = var4.a$r2()).getBlock() instanceof BlockChest) {
            this.N = var7;
            this.p = true;
            this.Y = System.currentTimeMillis();
}
}
    public void onCloseScreen(long var1, CloseScreenEvent var3) {
        this.g = System.currentTimeMillis();
        if (this.p && this.N != null) {
            this.b(this.N);
}
        this.p = false;
        this.N = null;
        this.s = false;
        this.E = null;
        this.o(0L);
}
    @Override
    public final void x(long var1, EventBus var3) {
        ChestAuraBinder.E(var3, this);
}
    private void v(short var1, BlockPos var2, int var3, int var4) {
        long var5 = ((long)var1 << 48 | (long)var3 << 32 >>> 16 | (long)var4 << 48 >>> 48) ^ b;
        int var7 = (int)((var5 ^ 0x5EDF7539F0A3L) >>> 48);
        int var8 = (int)((var5 ^ 0x5EDF7539F0A3L) << 16 >>> 32);
        int var9 = (int)((var5 ^ 0x5EDF7539F0A3L) << 48 >>> 48);
        long var10 = var5 ^ 0x6D12D4547C5EL;
        switch (moveFix.Y()) {
            case "SILENT": {
                RotationManager.n(RotationMode.SILENT);
                break;
}
            case "STRICT": {
                RotationManager.n(RotationMode.STRICT);
                break;
}
            case "NONE": {
                RotationManager.n(RotationMode.NONE);
}
}
        if (this.y.L(1L, true)) {
            float[] var14 = RotationUtil.S((char)var7, var8, (char)var9, var2, BlockUtil.D(var2));
            RotationManager.N(var10, var14[0], var14[1]);
            this.J = true;
}
}
    @Override
    public String g(long var1) {
        return String.valueOf(range.L());
}
    public ChestAura(long var1) {
        super((b ^ var1 ^ 0x2AEC1B8AB05AL) >>> 16, (char)((b ^ var1 ^ 0x2AEC1B8AB05AL) << 48 >>> 48));
        this.declare("ChestAura", Category.Player, "Automatically open chests in range", new Setting[0]);
        var1 = b ^ var1;
        this.y = new TimerUtil();
        this.O = new TimerUtil();
        this.g = System.currentTimeMillis();
        this.Y = 0L;
        this.J = false;
        this.s = false;
        this.p = false;
        this.E = null;
        this.N = null;
}
    private void Y(int var1) {
        this.o(0L);
        this.s = false;
        this.E = null;
        if (!this.p) {
            this.N = null;
}
}
    public void onEntityJoinWorld(long var1, EntityJoinWorldEvent var3) {
        if (var3.H instanceof EntityPlayerSP) {
            H.clear();
            this.o(0L);
            this.s = false;
            this.E = null;
            this.N = null;
            this.p = false;
}
}
    private boolean R(long var1) {
        int var5 = 226;
        int var8 = 64121;
        if (!EntityUtil.J(EntityUtil.o(17403, '\u7fa5', (short)var8, disableWhenPlayersInRange.L()), false, true, false, 4469, 3915520, false, (byte)var5).isEmpty()) {
            return true;
}
        if (!this.Y()) {
            this.T(false);
            return true;
}
        return !OutgoingPacketState.Y() ? true : System.currentTimeMillis() - this.g <= 100L;
}
    private void o(long var1) {
        if (this.J) {
            RotationManager.O(123115463851087L);
            this.J = false;
}
}
    private boolean p(int var1, int var2, BlockPos var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 32 | (long)var2 << 32 >>> 32) ^ b;
        int var6 = (int)((var4 ^ 0x72CB430F1ED9L) >>> 56);
        int var7 = (int)((var4 ^ 0x72CB430F1ED9L) << 8 >>> 32);
        int var9 = (int)((var4 ^ 0x3FFAF8D07E8CL) >>> 48);
        long var10 = (var4 ^ 0x3FFAF8D07E8CL) << 16 >>> 16;
        if (var3 == null || ChestAura.f.theWorld == null) {
            return false;
}
        if (H.contains(var3)) {
            return false;
}
        Block var12 = ChestAura.f.theWorld.getBlockState(var3).getBlock();
        if (!(var12 instanceof BlockChest)) {
            return false;
}
        return BlockUtil.Y((byte)var6, var3, var7) ? false : throughWall.c() || !RaytraceUtil.r((short)var9, var10, var3, BlockUtil.D(var3), range.L());
}
    @Override
    public void A(long var1) {
        this.o(0L);
        this.s = false;
        this.E = null;
        this.N = null;
        this.p = false;
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var8 = 19661;
        int var17 = 36958;
        if (ChestAura.f.theWorld != null && ChestAura.f.thePlayer != null) {
            this.p((byte)0, 95676268004049L);
            if (ChestAura.f.currentScreen instanceof GuiChest) {
                if (this.N != null) {
                    this.b(this.N);
}
                this.p = false;
                this.N = null;
                this.s = false;
                this.E = null;
                this.o(0L);
            } else if (this.p) {
                if (System.currentTimeMillis() - this.Y > 1200L) {
                    this.p = false;
                    this.N = null;
}
                this.o(0L);
            } else if (this.R(32375407106993L)) {
                this.Y(16590);
            } else if (!this.s || this.E == null) {
                BlockPos var23 = this.getDistanceSqToCenter('\u0000', range.L(), 954787180, (char)var8);
                if (var23 == null) {
                    this.Y(16590);
                } else {
                    if (rotation.c()) {
                        this.v((short)0, var23, 845277282, var17);
}
                    this.s = true;
                    this.E = var23;
}
            } else if (!this.p(4550, -14237895, this.E)) {
                this.Y(16590);
            } else if (this.O.L(80L, true)) {
                BlockPos var21 = this.E;
                CombatUtil.u(var21, BlockUtil.D(var21), BlockUtil.f(var21, BlockUtil.D(var21)), true, false);
                this.N = var21;
                this.p = true;
                this.Y = System.currentTimeMillis();
                this.s = false;
                this.E = null;
}
        } else {
            this.Y(16590);
}
}
    static {
        H = new ArrayList<BlockPos>();
        x = new Object[30];
        B = new String[30];
        e = new HashMap(13);
        d = new String[3];
        n = new HashMap(13);
        h = new long[]{-8449384215971602076L, -8875789389776584978L, -8344971114425550018L, 2371771012195526087L};
        u = new HashMap(13);
        throughWall = new BooleanSetting("Through-wall", false);
        moveFix = new ModeSetting("Move-fix", "SILENT", "STRICT", "NONE");
        range = new NumberSetting("Range", 3.0f, 0.0f, 10.0f, 0.1f);
        disableWhenPlayersInRange = new NumberSetting("Disable-when-players-in-range", 5.0f, 0.0f, 20.0f, 0.1f);
        rotation = new BooleanSetting("Rotation", true);
}
}