/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 */
package Abyss.internal;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BrokenBlockTrackerBinder;
import Abyss.event.events.ClickBlockReturnEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.TryHarvestBlockHeadEvent;
import Abyss.internal.BrokenBlockAnchor;
import Abyss.internal.BrokenBlockEntry;
import Abyss.internal.MiningEngine;
import Abyss.internal.MiningState;
import Abyss.util.MinecraftRef;
import Abyss.util.MiningConstants;
import Abyss.util.RotationManager;
import Abyss.util.render.BoxRenderer;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;

public class BrokenBlockTracker
implements EventSubscriber {
    private static long a;
    private static Minecraft w;
    private final Map<BlockPos, Long> c;
    private static EnumParticleTypes Z;
    private static long[] g;
    private static Long[] p;
    private static long F;
    private BlockPos T;
    private static long j;
    private long Y;
    private static EnumParticleTypes l;
    private static Map f;
    private final Map<BlockPos, BrokenBlockAnchor> I;
    private final Map<BlockPos, Block> i;
    private final Map<BlockPos, BrokenBlockEntry> H;
    private final LinkedList<BlockPos> e;
    private static String[] s;
    private static Integer[] k;
    private static Object[] r;
    private boolean N;
    private static String[] d;
    private static long t;
    private static String[] b;
    private static Map q;
    private final Map<BlockPos, Block> z;
    private long B;
    private BlockPos W;
    private static Map n;
    private final Map<BlockPos, Long> C;
    public static BrokenBlockTracker m;
    private static EnumParticleTypes h;
    private static long[] o;
    private static long L;

    private String C(Minecraft var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var2 ^ 0x698F6193C24DL;
        StringBuilder var6 = new StringBuilder("Last 4 broken blocks: ");
        String[] var7 = new String[]{"A", "B", "C", "D"};
        int var8 = 0;
        for (BlockPos var10 : this.e) {
            Block var11 = this.i.get(var10);
            Block var12 = var1.theWorld.getBlockState(var10).getBlock();
            String var13 = var7[var8 % var7.length];
            var6.append(var13).append("[").append(this.b(var10, var4)).append(", orig: ").append(var11 != null ? var11.getLocalizedName() : "?").append(", now: ").append(var12.getLocalizedName()).append("] ");
            ++var8;
}
        return var6.toString().trim();
}
    public Block x(BlockPos var1) {
        return this.i.get(var1);
}
    public void N(boolean var1) {
        this.N = var1;
}
    public boolean k(BlockPos var1) {
        return this.D() ? false : this.c.containsKey(var1);
}
    private String b(BlockPos var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return var1 == null ? "null" : String.format("x:%d, y:%d, z:%d", var1.getX(), var1.getY(), var1.getZ());
}
    public void onPostTick(long var1, PostTickEvent var3) {
        if (this.r()) {
            Block var12;
            if (BrokenBlockTracker.w.objectMouseOver != null && BrokenBlockTracker.w.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos var8 = BrokenBlockTracker.w.objectMouseOver.getBlockPos();
                if (this.T == null || !this.T.equals((Object)var8)) {
                    this.T = var8;
                    this.B = System.currentTimeMillis();
                    Block var9 = BrokenBlockTracker.w.theWorld.getBlockState(var8).getBlock();
                    this.z.put(var8, var9);
}
}
            if (this.T != null && BrokenBlockTracker.w.theWorld.isBlockLoaded(this.T) && (var12 = BrokenBlockTracker.w.theWorld.getBlockState(this.T).getBlock()) == Blocks.air) {
                this.W = this.T;
                this.i.put(this.T, this.z.getOrDefault(this.T, Blocks.air));
                this.z.remove(this.T);
                this.e.addFirst(this.T);
                if (this.e.size() > 4) {
                    BlockPos var13 = this.e.removeLast();
                    this.i.remove(var13);
                    this.z.remove(var13);
}
                long var14 = System.currentTimeMillis() - this.B;
                if (!this.D()) {
                    this.c(120137885386950L, this.T, var14);
}
                this.T = null;
}
            this.z(0L);
}
}
    public void onTryHarvestBlockHead(TryHarvestBlockHeadEvent var1, long var2) {
        if (this.r()) {
            BlockPos var6;
            this.W = var6 = var1.n;
            long var7 = System.currentTimeMillis() - this.B;
            this.c(120137885386950L, var6, var7);
            if (this.T != null && this.T.equals((Object)var6)) {
                this.T = null;
}
}
}
    private static long c(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x653F;
        if (p[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = o[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])q.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    q.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/internal/BrokenBlockTracker", var14);
}
            long var15 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
            BrokenBlockTracker.p[var3] = var15;
}
        return p[var3];
}
    private void c(long var1, BlockPos var3, long var4) {
        MiningState var10;
        if (!(this.D() || this.c.containsKey(var3) || (var10 = MiningEngine.uq.s()) != null && (var10.d$r1().contains(var3) || var10.g().contains(var3)))) {
            Block var11 = BrokenBlockTracker.w.theWorld.getBlockState(var3).getBlock();
            BrokenBlockEntry var12 = this.H.get(var3);
            if (var12 == null) {
                var12 = new BrokenBlockEntry(25191003644408L, null);
                this.H.put(var3, var12);
}
            var12.R(var4, var11);
            if (!((float)var12.Q < MiningConstants.K) && var12.q()) {
                for (int var13 = -2; var13 <= 2; ++var13) {
                    this.c.put(new BlockPos(var3.getX(), var3.getY() + var13, var3.getZ()), System.currentTimeMillis());
}
                float var20 = this.U(MiningEngine.uq.h() ? RotationManager.r : BrokenBlockTracker.w.thePlayer.rotationYaw);
                int var14 = 0;
                int var15 = 0;
                int var16 = 0;
                int var17 = 0;
                if (var20 >= 45.0f && var20 < 135.0f) {
                    var15 = 1;
                    var17 = -1;
                } else if (var20 >= 135.0f && var20 < 225.0f) {
                    var14 = -1;
                    var16 = 1;
                } else if (var20 >= 225.0f && var20 < 315.0f) {
                    var15 = -1;
                    var17 = 1;
                } else {
                    var14 = 1;
                    var16 = -1;
}
                for (int var18 = -2; var18 <= 2; ++var18) {
                    this.c.put(new BlockPos(var3.getX() + var14, var3.getY() + var18, var3.getZ() + var15), System.currentTimeMillis());
                    this.c.put(new BlockPos(var3.getX() + var16, var3.getY() + var18, var3.getZ() + var17), System.currentTimeMillis());
}
}
}
}
    private boolean D() {
        return w != null && w.isSingleplayer();
}
    private void m(long var1) {
        this.H.entrySet().removeIf(var3 -> !this.c.containsKey(var3.getKey()) && var1 - ((BrokenBlockEntry)var3.getValue()).H > 10000L);
        this.c.entrySet().removeIf(var2 -> var1 - (Long)var2.getValue() > 15000L);
        this.I.keySet().removeIf(var1x -> !this.c.containsKey(var1x) && !var1x.equals((Object)this.T) && !var1x.equals((Object)this.W));
}
    private boolean r() {
        return MiningConstants.z || this.N;
}
    public List<BlockPos> q() {
        return new ArrayList<BlockPos>(this.e);
}
    public BlockPos b() {
        return this.W;
}
    public boolean m() {
        return this.N;
}
    private void X(BlockPos var1, EnumParticleTypes var2) {
        WorldClient var3 = BrokenBlockTracker.w.theWorld;
        BrokenBlockAnchor var4 = this.I.get(var1);
        if (var4 == null) {
            double var15;
            double var21;
            double var13;
            double var19;
            double var5 = BrokenBlockTracker.w.thePlayer.posX;
            double var7 = BrokenBlockTracker.w.thePlayer.posY + 1.62;
            double var9 = BrokenBlockTracker.w.thePlayer.posZ;
            double var11 = (double)var1.getX() + 0.5;
            double var17 = var11 - var5;
            double var23 = Math.sqrt(var17 * var17 + (var19 = (var13 = (double)var1.getY() + 0.5) - var7) * var19 + (var21 = (var15 = (double)var1.getZ() + 0.5) - var9) * var21);
            var4 = var23 > 0.0 ? new BrokenBlockAnchor(var11 - var17 / var23 * 0.51, var13 - var19 / var23 * 0.51, var15 - var21 / var23 * 0.51) : new BrokenBlockAnchor(var11, var13 + 0.51, var15);
            this.I.put(var1, var4);
}
        if (var2 == l) {
            var3.spawnParticle(var2, var4.u, var4.N, var4.C, 0.0, 0.0, 0.0, new int[0]);
        } else {
            for (int var25 = 0; var25 < 3; ++var25) {
                var3.spawnParticle(var2, var4.u + (Math.random() - 0.5) * 0.1, var4.N + (Math.random() - 0.5) * 0.1, var4.C + (Math.random() - 0.5) * 0.1, (Math.random() - 0.5) * 0.1, (Math.random() - 0.5) * 0.1, (Math.random() - 0.5) * 0.1, new int[0]);
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        BrokenBlockTrackerBinder.z(var3, this);
}
    private BrokenBlockTracker(int var1, short var2, short var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a;
        this.H = new HashMap<BlockPos, BrokenBlockEntry>();
        this.c = new HashMap<BlockPos, Long>();
        this.C = new HashMap<BlockPos, Long>();
        this.I = new HashMap<BlockPos, BrokenBlockAnchor>();
        this.e = new LinkedList();
        this.i = new HashMap<BlockPos, Block>();
        this.z = new HashMap<BlockPos, Block>();
        this.Y = 0L;
        this.N = false;
}
    private void z(long var1) {
        long var3 = System.currentTimeMillis();
        if (var3 - this.Y >= 200L) {
            this.Y = var3;
            if (MiningConstants.j && this.T != null && BrokenBlockTracker.w.theWorld.isBlockLoaded(this.T) && !this.k(this.T)) {
                this.X(this.T, Z);
}
            if (MiningConstants.j && this.W != null && !this.W.equals((Object)this.T) && BrokenBlockTracker.w.theWorld.isBlockLoaded(this.W) && !this.k(this.W)) {
                this.X(this.W, h);
}
            if (MiningConstants.T) {
                for (BlockPos var6 : this.c.keySet()) {
                    Long var7;
                    if (var6 == null || var6.equals((Object)this.T) || var6.equals((Object)this.W) || !BrokenBlockTracker.w.theWorld.isBlockLoaded(var6) || (var7 = this.C.get(var6)) != null && var3 - var7 <= 2000L) continue;
                    this.X(var6, l);
                    this.C.put(var6, var3);
}
}
            this.m(var3);
}
}
    public void onClickBlockReturn(ClickBlockReturnEvent var1, long var2) {
        if (this.r()) {
            BlockPos var7 = var1.b;
            if (this.T == null || !this.T.equals((Object)var7)) {
                this.T = var7;
                this.B = System.currentTimeMillis();
                Block var8 = MinecraftRef.c((byte)0, (long)0L).theWorld.getBlockState(var7).getBlock();
                this.z.put(var7, var8);
}
}
}
    public void e(long var1) {
        long var3 = 99005023413082L;
        Color var5 = new Color(0, 0, 0, 120);
        for (BlockPos var7 : this.c.keySet()) {
            if (var7 == null || !BrokenBlockTracker.w.theWorld.isBlockLoaded(var7)) continue;
            BoxRenderer.p(var7, var3, var5);
}
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x3015;
        if (k[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = g[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])n.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    n.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/internal/BrokenBlockTracker", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            BrokenBlockTracker.k[var3] = var15;
}
        return k[var3];
}
    public BlockPos y() {
        return this.T;
}
    private float U(float var1) {
        if ((var1 %= 360.0f) < 0.0f) {
            var1 += 360.0f;
}
        return var1;
}
    private static void a() {
        r[0] = "v\u0006\u001b\u000f\"u[";
        r[1] = "e\u0013V\nlrR\u0004R\u0000!VE\u000f\b\u001c";
        r[2] = "\u0019\u001b6I\u001d5=";
        r[3] = Long.TYPE;
        s[3] = "java/lang/Long";
        r[4] = Void.TYPE;
        s[4] = "java/lang/Void";
        r[5] = "\u0019 {f\u0006\u000b\u0012/j)g\u0005\u0019$ns";
        r[6] = "U\rf!:H\u0017J-Q%$UY63-\u001c\u0006V<hW\u001d\u0013ZbkjZ\u0010O1Qm]\u0011Id8hD\fV_j=Y\u0001_30,U\u00075";
    }    private static String a(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                int var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                int var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }    private static void zkm$clinit() {
        try {
            long var31 = a ^ 52250733743518L;
            int var33 = (int)((var31 ^ 103359315573225L) >>> 32);
            int var34 = (int)((var31 ^ 103359315573225L) << 32 >>> 48);
            int var35 = (int)((var31 ^ 103359315573225L) << 48 >>> 48);
            r = new Object[7];
            s = new String[7];
            a();
            f = new HashMap(13);
            byte[] var10003 = new byte[]{(byte)(var31 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var23 = 1; var23 < 8; ++var23) {
                var10003[var23] = (byte)(var31 << var23 * 8 >>> 56);
            }
            Cipher var22 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var22.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var29 = new String[6];
            int var27 = 0;
            String var26 = "U\b\u00ec,\u00fa\u0086\bB\u0000UR\u00b7\u00e9s\u00ad\u00ec\u0010b\u00f6\u00a0\u0090\u0098\u0089\u0018\u00d1\n\u00b6\u001bB\u00d9\u00a7|\u00e7(9N\u0096\u0011\u0016\u00d8TUb\u00d2\u00f8j\u0098\u0007\u009c\u0099L\u0083\u0014\u001c\u00f5W\u000f\u00c9\u00d7\u0002\u0086\bL\u0013\u00aa\u0080\u00df\u0089\u00a9\u00ab6\u00bd}\u00a90\u00f6\u00bf{\u009c\u009f\u00ae\u0002\u009e\"\u00d1<\u00a5*\u00ef\u00ac\u000f\\`\u00d2\u00a4\u0006}b\u00b2U\u00ddyX\u00c9t\u00c2\u00ac\u0091#|T/t8\u00a2'\u00f8q\u00fc\u00a4pO`";
            int var28 = "U\b\u00ec,\u00fa\u0086\bB\u0000UR\u00b7\u00e9s\u00ad\u00ec\u0010b\u00f6\u00a0\u0090\u0098\u0089\u0018\u00d1\n\u00b6\u001bB\u00d9\u00a7|\u00e7(9N\u0096\u0011\u0016\u00d8TUb\u00d2\u00f8j\u0098\u0007\u009c\u0099L\u0083\u0014\u001c\u00f5W\u000f\u00c9\u00d7\u0002\u0086\bL\u0013\u00aa\u0080\u00df\u0089\u00a9\u00ab6\u00bd}\u00a90\u00f6\u00bf{\u009c\u009f\u00ae\u0002\u009e\"\u00d1<\u00a5*\u00ef\u00ac\u000f\\`\u00d2\u00a4\u0006}b\u00b2U\u00ddyX\u00c9t\u00c2\u00ac\u0091#|T/t8\u00a2'\u00f8q\u00fc\u00a4pO`".length();
            int var25 = 16;
            int var43 = -1;
            block12: while (true) {
                String var44 = var26.substring(++var43, var43 + var25);
                int var50 = -1;
                while (true) {
                    byte[] var30 = var22.doFinal(var44.getBytes("ISO-8859-1"));
                    String var61 = BrokenBlockTracker.a(var30).intern();
                    switch (var50) {
                        case 0: {
                            var29[var27++] = var61;
                            if ((var43 += var25) >= var28) {
                                b = var29;
                                d = new String[6];
                                n = new HashMap(13);
                                var10003 = new byte[]{(byte)(var31 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(var31 << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var17 = new long[4];
                                int var14 = 0;
                                String var15 = "\u00a1\u009c\u0094I\u0019\u0093Xgb\u00b1U\u0005d\u008e\u00e8R";
                                int var16 = "\u00a1\u009c\u0094I\u0019\u0093Xgb\u00b1U\u0005d\u008e\u00e8R".length();
                                int var13 = 0;
                                block15: while (true) {
                                    int var54 = var13;
                                    byte[] var18 = var15.substring(var54, var13 += 8).getBytes("ISO-8859-1");
                                    long[] var47 = var17;
                                    int var55 = var14++;
                                    long var65 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                    int var71 = -1;
                                    while (true) {
                                        long var19 = var65;
                                        byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                                        long var76 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                                        switch (var71) {
                                            case 0: {
                                                var47[var55] = var76;
                                                if (var13 < var16) break;
                                                g = var17;
                                                k = new Integer[4];
                                                q = new HashMap(13);
                                                var10003 = new byte[]{(byte)(var31 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(var31 << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                long[] var6 = new long[8];
                                                int var3 = 0;
                                                String var4 = "\u00da0\u00b8\u00c9\u0015\u0081e\u008d\u0016k\u0017\u0090\u00e7\u008b\u00fc\u00f4\u00f9IFp-\u00bd?H\u000f\u00d1:\u00f7\u00dd+\u00fe\u00e5\u00ce@\u00a87\u00e4d\u00c2\u000e\u00f4[\u00ee[\u00a8n}\u0016";
                                                int var5 = "\u00da0\u00b8\u00c9\u0015\u0081e\u008d\u0016k\u0017\u0090\u00e7\u008b\u00fc\u00f4\u00f9IFp-\u00bd?H\u000f\u00d1:\u00f7\u00dd+\u00fe\u00e5\u00ce@\u00a87\u00e4d\u00c2\u000e\u00f4[\u00ee[\u00a8n}\u0016".length();
                                                int var2 = 0;
                                                block18: while (true) {
                                                    int var57 = var2;
                                                    byte[] var7 = var4.substring(var57, var2 += 8).getBytes("ISO-8859-1");
                                                    long[] var49 = var6;
                                                    int var58 = var3++;
                                                    long var68 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                    int var74 = -1;
                                                    while (true) {
                                                        long var8 = var68;
                                                        byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                                        var76 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                                        switch (var74) {
                                                            case 0: {
                                                                var49[var58] = var76;
                                                                if (var2 < var5) break;
                                                                o = var6;
                                                                p = new Long[8];
                                                                L = 15000L;
                                                                j = 200L;
                                                                t = 10000L;
                                                                F = 2000L;
                                                                m = new BrokenBlockTracker(var33, (short)var34, (short)var35);
                                                                return;
}
                                                            default: {
                                                                var49[var58] = var76;
                                                                if (var2 < var5) continue block18;
                                                                var4 = "\u0082]\u00031\u008e\u00e7\u0000As\u00d7Ic4\u007f\u009f\u0015";
                                                                var5 = "\u0082]\u00031\u008e\u00e7\u0000As\u00d7Ic4\u007f\u009f\u0015".length();
                                                                var2 = 0;
}
}
                                                        int var59 = var2;
                                                        var7 = var4.substring(var59, var2 += 8).getBytes("ISO-8859-1");
                                                        var49 = var6;
                                                        var58 = var3++;
                                                        var68 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                        var74 = 0;
}
}
}
                                            default: {
                                                var47[var55] = var76;
                                                if (var13 < var16) continue block15;
                                                var15 = "j6u\u00dcJy\u00d1w\u00a6\u0002\u00ea\u0003\n\u0003\u00909";
                                                var16 = "j6u\u00dcJy\u00d1w\u00a6\u0002\u00ea\u0003\n\u0003\u00909".length();
                                                var13 = 0;
}
}
                                        int var56 = var13;
                                        var18 = var15.substring(var56, var13 += 8).getBytes("ISO-8859-1");
                                        var47 = var17;
                                        var55 = var14++;
                                        var65 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                        var71 = 0;
}
}
}
                            var25 = var26.charAt(var43);
                            break;
}
                        default: {
                            var29[var27++] = var61;
                            if ((var43 += var25) < var28) {
                                var25 = var26.charAt(var43);
                                continue block12;
}
                            var26 = "\u0095]J\u00e1\u00fcKi\u0081\u00c6\u00b9\u0084\u0098\u00f7:\u00b8>\u0096\u00ef\u00b7\u00b4\u00e7\t\u00c0{tqxv;D\u001d\u00c5\u0010\u00e6R\n\u00ba!\u00b2\u0000 \u00c6\u0089M=\u0005\u00a51\u00a2";
                            var28 = "\u0095]J\u00e1\u00fcKi\u0081\u00c6\u00b9\u0084\u0098\u00f7:\u00b8>\u0096\u00ef\u00b7\u00b4\u00e7\t\u00c0{tqxv;D\u001d\u00c5\u0010\u00e6R\n\u00ba!\u00b2\u0000 \u00c6\u0089M=\u0005\u00a51\u00a2".length();
                            var25 = 32;
                            var43 = -1;
}
}
                    var44 = var26.substring(++var43, var43 + var25);
                    var50 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var39) {
            throw new RuntimeException(var39);
}
}
    static {
        a = 58141217640642L;
        zkm$clinit();
        Z = EnumParticleTypes.WATER_DROP;
        h = EnumParticleTypes.FLAME;
        l = EnumParticleTypes.BARRIER;
        w = MinecraftRef.c((byte)0, 0L);
}
}