/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockMobSpawner
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.server.S22PacketMultiBlockChange
 *  net.minecraft.network.play.server.S22PacketMultiBlockChange$BlockUpdateData
 *  net.minecraft.network.play.server.S23PacketBlockChange
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BlocksESPBinder;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.ClientUtil;
import Abyss.util.DeferredRendererReload;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RaytraceUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class BlocksESP
extends Module
implements EventSubscriber {
    public static BooleanSetting tracers;
    public static HeaderSetting oresSettings;
    private static Set<Vec3i> Y;
    private static Set<Vec3i> a;
    private static String[] u;
    private static Object[] o;
    public static BooleanSetting redstone;
    private static Minecraft s;
    private static long[] e;
    public static BooleanSetting lapis;
    public static BooleanSetting coal;
    public static NumberSetting range;
    public static PercentageSetting opacity;
    private static long d;
    public static BooleanSetting gold;
    public static BooleanSetting diamond;
    public static NumberSetting cavesRadius;
    public static BooleanSetting emerald;
    private static Map n;
    public static Set<BlockPos> L;
    public static BooleanSetting spawner;
    public static BooleanSetting shade;
    public static Set<BlockPos> x;
    public static BooleanSetting iron;
    public static BooleanSetting obsidian;
    public static BooleanSetting outline;
    public static BooleanSetting cavesOnly;

    @Override
    public final void x(long var1, EventBus var3) {
        BlocksESPBinder.b(var3, this);
}
    public BlocksESP(long var1) {
        super(d ^ var1 ^ 0x5137F5F11E03L);
        this.declare("BlocksESP", Category.Visual_utility, "Highlight some blocks", new Setting[0]);
        var1 = d ^ var1;
}
    public static boolean y(BlockPos var0) {
        if (!cavesOnly.c()) {
            return true;
}
        if (cavesRadius.L() >= 2.0f) {
            for (Vec3i var2 : a) {
                if (!BlocksESP.U(var0.add(var2))) continue;
                return true;
}
        } else {
            for (Vec3i var4 : Y) {
                if (!BlocksESP.U(var0.add(var4))) continue;
                return true;
}
}
        return false;
}
    private Color x(char var1, Block var2, int var3) {
        if (var2 == Blocks.gold_ore) {
            return new Color(0xFFFF85);
}
        if (var2 == Blocks.iron_ore) {
            return new Color(0xFFFFFF);
}
        if (var2 == Blocks.coal_ore) {
            return new Color(0);
}
        if (var2 == Blocks.lapis_ore) {
            return new Color(0x5555FF);
}
        if (var2 == Blocks.redstone_ore || var2 == Blocks.lit_redstone_ore) {
            return new Color(0xFF4445);
}
        if (var2 == Blocks.diamond_ore) {
            return new Color(0x55FFFF);
}
        if (var2 == Blocks.emerald_ore) {
            return new Color(0x55FF55);
}
        return var2 == Blocks.obsidian ? new Color(0xAA00AA) : new Color(-1);
}
    private static void a() {
        BlocksESP.o[0] = "b \u0011\u001e\"PK";
        BlocksESP.o[1] = Long.TYPE;
        BlocksESP.u[1] = "java/lang/Long";
        BlocksESP.o[2] = "\u0012\u0018^=~^%\u000fZ73z2\u0004\u0000+";
        BlocksESP.o[3] = "8I\r=\u0019P\u001a";
        BlocksESP.o[4] = Void.TYPE;
        BlocksESP.u[4] = "java/lang/Void";
        BlocksESP.o[5] = "S\u00008qqpX\u000f)>\u0010~S\u0004-d";
        BlocksESP.o[6] = "\u0004 \u001c#!A\u001dg\u0013\u001dq0\u001df\u001ay'\\\u0012hG\u001d\"I[!\u0005o+WZcz'v\nZ \u0019\u007f\u007f\f\u001e\u0019@`\u007fL\u0006$\u0004s#\tb";
}
    private static boolean U(BlockPos var0) {
        if (!BlocksESP.s.theWorld.isBlockLoaded(var0, false)) {
            return false;
}
        Block var1 = BlocksESP.s.theWorld.getBlockState(var0).getBlock();
        return var1 instanceof BlockMobSpawner || !var1.isFullBlock() || !var1.getMaterial().isOpaque() || var1.canProvidePower();
}
    @Override
    public void A(long var1) {
        L.clear();
        x.clear();
}
    public void onReceivePacket(ReceivePacketEvent var1) {
        S23PacketBlockChange var6;
        if (var1.d instanceof S22PacketMultiBlockChange) {
            for (S22PacketMultiBlockChange.BlockUpdateData var5 : ((S22PacketMultiBlockChange)var1.d).getChangedBlocks()) {
                if (!BlocksESP.L(var5.getBlockState().getBlock())) continue;
                x.add(new BlockPos((Vec3i)var5.getPos()));
}
        } else if (var1.d instanceof S23PacketBlockChange && BlocksESP.L((var6 = (S23PacketBlockChange)var1.d).getBlockState().getBlock())) {
            x.add(new BlockPos((Vec3i)var6.getBlockPosition()));
}
}
    public void onRender3D(long var1, Render3DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        Vec3 var10 = BlocksESP.s.gameSettings.thirdPersonView == 0 ? new Vec3(0.0, 0.0, 1.0).rotatePitch((float)(-Math.toRadians(MathUtil.k(BlocksESP.s.getRenderViewEntity().rotationPitch, BlocksESP.s.getRenderViewEntity().prevRotationPitch, ClientUtil.H(112506723048534L))))).rotateYaw((float)(-Math.toRadians(MathUtil.k(BlocksESP.s.getRenderViewEntity().rotationYaw, BlocksESP.s.getRenderViewEntity().prevRotationYaw, ClientUtil.H(112506723048534L))))) : new Vec3(0.0, 0.0, 0.0).rotatePitch((float)(-Math.toRadians(MathUtil.k(BlocksESP.s.thePlayer.cameraPitch, BlocksESP.s.thePlayer.prevCameraPitch, ClientUtil.H(112506723048534L))))).rotateYaw((float)(-Math.toRadians(MathUtil.k(BlocksESP.s.thePlayer.cameraYaw, BlocksESP.s.thePlayer.prevCameraYaw, ClientUtil.H(112506723048534L)))));
        var10 = new Vec3(var10.xCoord, var10.yCoord + (double)s.getRenderViewEntity().getEyeHeight(), var10.zCoord);
        RenderUtil.L();
        for (BlockPos var12 : L) {
            if (x.contains(var12)) {
                L.remove(var12);
                continue;
}
            if (BlocksESP.L(BlocksESP.s.theWorld.getBlockState(var12).getBlock()) && BlocksESP.y(var12)) {
                this.u(var12, 114872601337382L, BlocksESP.s.theWorld.getBlockState(var12).getBlock(), var10);
                continue;
}
            L.remove(var12);
}
        for (BlockPos var16 : x) {
            if (BlocksESP.L(BlocksESP.s.theWorld.getBlockState(var16).getBlock()) && BlocksESP.y(var16)) {
                this.u(var16, 114872601337382L, BlocksESP.s.theWorld.getBlockState(var16).getBlock(), var10);
                continue;
}
            x.remove(var16);
}
        RenderUtil.w();
}
    public static boolean L(Block var0) {
        return iron.c() && var0 == Blocks.iron_ore || gold.c() && var0 == Blocks.gold_ore || diamond.c() && var0 == Blocks.diamond_ore || emerald.c() && var0 == Blocks.emerald_ore || lapis.c() && var0 == Blocks.lapis_ore || redstone.c() && var0 == Blocks.redstone_ore || coal.c() && var0 == Blocks.coal_ore || spawner.c() && var0 == Blocks.mob_spawner || obsidian.c() && var0 == Blocks.obsidian;
}
    @Override
    public void i(long var1) {
        DeferredRendererReload.request();
}
    public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
        if (var1.H instanceof EntityPlayerSP) {
            L.clear();
            x.clear();
}
}
    private void u(BlockPos var1, long var2, Block var4, Vec3 var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (RaytraceUtil.Y(var1, range.L(), 119767551018300L)) {
            Color var17 = this.x('\u0000', var4, 1134423564);
            if (outline.c()) {
                RenderUtil.n(var1, 1.0, var17.getRed(), var17.getGreen(), var17.getBlue(), 114394550953247L, 255, 1.5f);
}
            if (shade.c()) {
                RenderUtil.C(var1, 1.0, var17.getRed(), 96914206771396L, var17.getGreen(), var17.getBlue(), (int)(2.55 * (double)opacity.k()));
}
            if (tracers.c()) {
                RenderUtil.r(var5, 92754948049078L, (double)var1.getX() + 0.5, (double)var1.getY() + 0.5, (double)var1.getZ() + 0.5, (float)var17.getRed() / 255.0f, (float)var17.getGreen() / 255.0f, (float)var17.getBlue() / 255.0f, 1.0f, 1.5f);
}
}
}
    private static void zkm$clinit() {
        try {
            long var11 = d ^ 24998517968569L;
            o = new Object[7];
            u = new String[7];
            a();
            n = new HashMap(13);
            Cipher var0;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var1 = 1; var1 < 8; ++var1) {
                var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
            }
            (var0 = Cipher.getInstance("DES/CBC/NoPadding")).init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var6 = new long[30];
            int var3 = 0;
            String var4 = "M\u00e4w\u00fffp\u00bd\u00d2|\u00b2\u00cau\u001b\u00a9\u0084M\u0095\u00b0\u00a0\u00b4\u00de\u00b5\u001d\u00b0\u00cf\u00b2\u008eY\t\u00c9\u0085\u0015`&\u0007\u0085\u008f_8\u00e8\u0087\u00d8\u00a7V\u00a93\u00dc\u0017{\u00f3F1\u00d6\u0018\u00d1b\u00aa\u00f9\u00f1Z\u00c2hx\u00c9`8\u008c\u00ea7\u00ecU\u00fb\u000b\u00edA\u00b5\u00e4-\u00db]+GI\n\u009b\u00bf\u00efN\u00da\u0015(\u00d5c?\u00c2M\u00aa#\u00d2I\u00acYL\u0001q\u001aY\u001f\u00b1\u0010\u0005\u00ad\u00e7!6e\u00d8_\u001a\\=\u00f9nQ\u00d9\u00be\u00c8\u00e2~\u00b6:o\u0097VQ\u0099\u00d2(\u00ba&\u00d1\u0019\u00c5\u00c9\u007f\u00a9\u0001#\u00b1\u00ae\u0010\u00fe\u00f6\u008fE\u0012\u00e3I\u00a3N\u00a8G\u00ec\u00a1L\u007f\u00f7\u0089\u00c7\u00b2sOu\u0095y\u009f\u00fa\u00c8\u00c5*\u00f1\u00e6\u0003\u0012\u00fdYK\u008f#C(\u00ce\u0096\u00dd\u00eb\u00dd\u001b\u00a6]\u00f5\u00d4k\u0099!\u00ca\u00e7\u00d4\u00b8\u0006\u0094\u0081v;\u00b1\u0089y\u00ad7I\b\u00d7\u00e2\u00bf@";
            int var5 = "M\u00e4w\u00fffp\u00bd\u00d2|\u00b2\u00cau\u001b\u00a9\u0084M\u0095\u00b0\u00a0\u00b4\u00de\u00b5\u001d\u00b0\u00cf\u00b2\u008eY\t\u00c9\u0085\u0015`&\u0007\u0085\u008f_8\u00e8\u0087\u00d8\u00a7V\u00a93\u00dc\u0017{\u00f3F1\u00d6\u0018\u00d1b\u00aa\u00f9\u00f1Z\u00c2hx\u00c9`8\u008c\u00ea7\u00ecU\u00fb\u000b\u00edA\u00b5\u00e4-\u00db]+GI\n\u009b\u00bf\u00efN\u00da\u0015(\u00d5c?\u00c2M\u00aa#\u00d2I\u00acYL\u0001q\u001aY\u001f\u00b1\u0010\u0005\u00ad\u00e7!6e\u00d8_\u001a\\=\u00f9nQ\u00d9\u00be\u00c8\u00e2~\u00b6:o\u0097VQ\u0099\u00d2(\u00ba&\u00d1\u0019\u00c5\u00c9\u007f\u00a9\u0001#\u00b1\u00ae\u0010\u00fe\u00f6\u008fE\u0012\u00e3I\u00a3N\u00a8G\u00ec\u00a1L\u007f\u00f7\u0089\u00c7\u00b2sOu\u0095y\u009f\u00fa\u00c8\u00c5*\u00f1\u00e6\u0003\u0012\u00fdYK\u008f#C(\u00ce\u0096\u00dd\u00eb\u00dd\u001b\u00a6]\u00f5\u00d4k\u0099!\u00ca\u00e7\u00d4\u00b8\u0006\u0094\u0081v;\u00b1\u0089y\u00ad7I\b\u00d7\u00e2\u00bf@".length();
            int var2 = 0;
            block6: while (true) {
                int var10001 = var2;
                byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                long[] var18 = var6;
                var10001 = var3++;
                long var21 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                int var25 = -1;
                while (true) {
                    long var8 = var21;
                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                    long var27 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                    switch (var25) {
                        case 0: {
                            var18[var10001] = var27;
                            if (var2 < var5) break;
                            e = var6;
                            L = ConcurrentHashMap.newKeySet();
                            x = ConcurrentHashMap.newKeySet();
                            Vec3i[] var23 = new Vec3i[]{new Vec3i(0, -1, 0), new Vec3i(1, 0, 0), new Vec3i(0, 0, -1), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 0), new Vec3i(0, 1, 0)};
                            Y = new LinkedHashSet<Vec3i>(Arrays.asList(var23));
                            Vec3i[] var24 = new Vec3i[]{new Vec3i(0, -2, 0), new Vec3i(1, -1, 0), new Vec3i(0, -1, -1), new Vec3i(0, -1, 0), new Vec3i(0, -1, 1), new Vec3i(-1, -1, 0), new Vec3i(2, 0, 0), new Vec3i(0, 0, 2), new Vec3i(0, 0, -2), new Vec3i(-2, 0, 0), new Vec3i(1, 0, -1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1), new Vec3i(0, 0, -1), new Vec3i(0, 0, 1), new Vec3i(-1, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(-1, 0, 1), new Vec3i(1, 1, 0), new Vec3i(0, 1, -1), new Vec3i(0, 1, 0), new Vec3i(0, 1, 1), new Vec3i(-1, 1, 0), new Vec3i(0, 2, 0)};
                            a = new LinkedHashSet<Vec3i>(Arrays.asList(var24));
                            return;
}
                        default: {
                            var18[var10001] = var27;
                            if (var2 < var5) continue block6;
                            var4 = "\u0017\u0001c\u00b7\u00fe.E\u0081\u0098\u00a9\u0005\u008e]8\u00e5:";
                            var5 = "\u0017\u0001c\u00b7\u00fe.E\u0081\u0098\u00a9\u0005\u008e]8\u00e5:".length();
                            var2 = 0;
}
}
                    int var20 = var2;
                    var7 = var4.substring(var20, var2 += 8).getBytes("ISO-8859-1");
                    var18 = var6;
                    var10001 = var3++;
                    var21 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                    var25 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var16) {
            throw new RuntimeException(var16);
}
}
    static {
        d = 610396979265L;
        zkm$clinit();
        s = MinecraftRef.c((byte)0, 0L);
        coal = new BooleanSetting("Coal", false);
        lapis = new BooleanSetting("Lapis", false);
        tracers = new BooleanSetting("Tracers", false);
        shade = new BooleanSetting("Shade", true);
        cavesRadius = new NumberSetting("Caves-radius", 2.0f, 1.0f, 2.0f, 1.0f);
        range = new NumberSetting("Range", 128.0f, 0.0f, 512.0f, 1.0f);
        spawner = new BooleanSetting("Spawner", false);
        diamond = new BooleanSetting("Diamond", true);
        cavesOnly = new BooleanSetting("Caves-only", true);
        iron = new BooleanSetting("Iron", false);
        opacity = new PercentageSetting("Opacity", 40);
        gold = new BooleanSetting("Gold", false);
        obsidian = new BooleanSetting("Obsidian", false);
        redstone = new BooleanSetting("Redstone", false);
        emerald = new BooleanSetting("Emerald", false);
        outline = new BooleanSetting("Outline", true);
        oresSettings = new HeaderSetting("Ores settings");
}
}