/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.BlockWall
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 */
package Abyss.module.impl.world;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BlockInBinder;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.module.impl.world.BlockInFaceOffset;
import Abyss.module.impl.world.BlockInPlacement;
import Abyss.module.impl.world.BlockInPlacementCandidate;
import Abyss.module.impl.world.BlockInRotationCandidate;
import Abyss.module.impl.world.BlockInScoredBlockPos;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.BlockUtil;
import Abyss.util.CombatUtil;
import Abyss.util.ItemUtil;
import Abyss.util.MathUtil;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.Sneaky;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockWall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockIn
extends PriorityModule
implements EventSubscriber {
    private boolean e;
    private BlockPos v;
    public static NumberSetting rotationTolerance;
    private float G;
    public static NumberSetting angleStep;
    private int O;
    private Vec3 T;
    private EnumFacing n;
    public static NumberSetting range;
    private float B;
    private boolean I;
    
    private static long[] o;
    private static int d;
    private static BlockInFaceOffset[] K;
    private static final double S = 0.2;
    private static final double a = 0.05;
    private BlockPos R;
    private static EnumFacing[] J;
    private boolean Y;
    private static String[] g;
    private boolean y;
        private static long b;
    public static BooleanSetting swing;
    public static ModeSetting moveFix;
    private int M;
    private EnumFacing s;
    
    private BlockInPlacement E(long var1) {
        BlockInPlacement var31;
        BlockPos var5 = new BlockPos(MathHelper.func_76128_c((double)BlockIn.f.field_71439_g.field_70165_t), MathHelper.func_76128_c((double)BlockIn.f.field_71439_g.field_70163_u), MathHelper.func_76128_c((double)BlockIn.f.field_71439_g.field_70161_v));
        BlockPos var6 = var5.func_177984_a();
        double var7 = range.L();
        Vec3 var9 = BlockIn.f.field_71439_g.func_174824_e(1.0f);
        ArrayList<BlockPos> var10 = new ArrayList<BlockPos>(8);
        for (EnumFacing var14 : J) {
            var10.add(var5.func_177972_a(var14));
            var10.add(var6.func_177972_a(var14));
}
        ArrayList<BlockPos> var26 = new ArrayList<BlockPos>(var10.size());
        for (BlockPos var29 : var10) {
            if (!BlockUtil.a$r1(var29) || !this.j(var29, var5, var6)) continue;
            var26.add(var29);
}
        if (var26.isEmpty()) {
            return null;
}
        Vec3 var28 = this.r(100.0);
        if (var28 != null) {
            var10.sort((var1x, var2) -> {
                double var3x = BlockIn.H((double)var1x.func_177958_n() + 0.5 - var28.field_72450_a) + BlockIn.H((double)var1x.func_177956_o() + 0.5 - var28.field_72448_b) + BlockIn.H((double)var1x.func_177952_p() + 0.5 - var28.field_72449_c);
                double var5x = BlockIn.H((double)var2.func_177958_n() + 0.5 - var28.field_72450_a) + BlockIn.H((double)var2.func_177956_o() + 0.5 - var28.field_72448_b) + BlockIn.H((double)var2.func_177952_p() + 0.5 - var28.field_72449_c);
                return Double.compare(var3x, var5x);
            });
            int var30 = 0;
            for (BlockPos var15 : var10) {
                if (var30 >= 3) break;
                if (!BlockUtil.a$r1(var15) || !this.j(var15, var5, var6)) continue;
                BlockInPlacement var16 = this.g(Collections.singletonList(var15), 0L, var7, var9);
                if (var16 != null) {
                    return var16;
}
                ++var30;
}
}
        if ((var31 = this.g(var26, 0L, var7, var9)) != null) {
            return var31;
}
        ArrayList var33 = new ArrayList(var26);
        HashSet<Long> var34 = new HashSet<Long>(var33.size() * 8);
        for (BlockPos var17 : var33) {
            var34.add(var17.func_177986_g());
}
        for (int var36 = 0; var36 < 5 && !var33.isEmpty(); ++var36) {
            BlockInPlacement var38;
            ArrayList<BlockPos> var37 = new ArrayList<BlockPos>(var33.size() * 3);
            for (BlockPos var19 : var33) {
                for (EnumFacing var23 : EnumFacing.values()) {
                    BlockPos var24 = var19.func_177972_a(var23);
                    if (!BlockUtil.a$r1(var24) || !var34.add(var24.func_177986_g())) continue;
                    var37.add(var24);
}
}
            if (!var37.isEmpty() && (var38 = this.g(var37, 0L, var7, var9)) != null) {
                return var38;
}
            var33 = var37;
}
        return null;
}
    private boolean j(BlockPos var1, BlockPos ... var2) {
        for (EnumFacing var6 : EnumFacing.values()) {
            BlockPos var7 = var1.func_177972_a(var6);
            if (!BlockUtil.a$r1(var7)) continue;
            boolean var8 = false;
            for (BlockPos var12 : var2) {
                if (!var7.equals((Object)var12)) continue;
                var8 = true;
                break;
}
            if (var8) continue;
            return true;
}
        return false;
}
    private BlockInPlacement g(List var1, long var2, double var4, Vec3 var6) {
        if (var1 != null && !var1.isEmpty() && this.M >= 0 && this.M <= 8) {
            ItemStack var7 = BlockIn.f.field_71439_g.field_71071_by.field_70462_a[this.M];
            float var8 = RotationManager.r;
            float var9 = RotationManager.G;
            MovingObjectPosition var10 = this.I(var4, var8, var9);
            if (var10.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos var11 = var10.func_178782_a();
                EnumFacing var12 = var10.field_178784_b;
                if (!BlockUtil.a$r1(var11) && this.I(var7, var11, var12)) {
                    for (BlockPos var14 : var1) {
                        BlockInPlacement var15 = this.j(var4, var8, var9, var11, var12, var14);
                        if (var15 == null) continue;
                        return var15;
}
}
}
            double var50 = 0.020000000000000004;
            double var51 = 0.949;
            double var52 = 0.051000000000000004;
            ArrayList<BlockInPlacementCandidate> var17 = new ArrayList<BlockInPlacementCandidate>(Math.max(16, var1.size() * 6 * (d + 1) * (d + 1)));
            for (BlockPos var19 : var1) {
                for (BlockInFaceOffset var23 : K) {
                    BlockPos var24 = new BlockPos(var19.func_177958_n() + var23.C, var19.func_177956_o() + var23.m, var19.func_177952_p() + var23.I);
                    if (BlockUtil.a$r1(var24) || !this.I(var7, var24, var23.z)) continue;
                    double var25 = var24.func_177958_n();
                    double var27 = var24.func_177956_o();
                    double var29 = var24.func_177952_p();
                    for (int var31 = 0; var31 <= d; ++var31) {
                        boolean var32 = (var31 & 1) == 0;
                        double var33 = BlockIn.B((double)var31 * 0.2 + BlockIn.i(var50));
                        for (int var35 = 0; var35 <= d; ++var35) {
                            double var42;
                            double var44;
                            double var40;
                            double var38;
                            double var36 = BlockIn.B((double)var35 * 0.2 + BlockIn.i(var50));
                            double d = var38 = var32 ? var36 : 1.0 - var36;
                            if (var23.m != 0) {
                                var40 = var25 + var38;
                                var44 = var29 + var33;
                                var42 = var27 + (var23.m < 0 ? var51 : var52);
                            } else if (var23.I != 0) {
                                var40 = var25 + var38;
                                var42 = var27 + var33;
                                var44 = var29 + (var23.I < 0 ? var51 : var52);
                            } else {
                                var44 = var29 + var38;
                                var42 = var27 + var33;
                                var40 = var25 + (var23.C < 0 ? var51 : var52);
}
                            float[] var46 = RotationUtil.W(new Vec3(var40, var42, var44), var6);
                            float var47 = Math.abs(MathHelper.func_76142_g((float)(var46[0] - var8)));
                            float var48 = Math.abs(var46[1] - var9);
                            if (var47 < 0.1f && var48 < 0.1f) continue;
                            var17.add(new BlockInPlacementCandidate(var47 + var48, var46[0], var46[1], var24, var23.z, var19));
}
}
}
}
            var17.sort((var0, var1x) -> Double.compare(var0.M, var1x.M));
            for (BlockInPlacementCandidate var54 : var17) {
                BlockInPlacement var55 = this.j(var4, var54.W, var54.w, var54.z, var54.B, var54.Q);
                if (var55 == null) continue;
                return var55;
}
            return null;
}
        return null;
}
    private static double i(double var0) {
        return var0 > 0.0 ? (Math.random() * 2.0 - 1.0) * var0 : 0.0;
}
    public void onHeldItemChange(long var1, HeldItemChangeEvent var3) {
        if (this.Y) {
            var3.I(21307, 3074332907L);
}
}
    private static double B(double var0) {
        return var0 < 0.0 ? 0.0 : Math.min(var0, 1.0);
}
    private float H(Block var1) {
        float var2 = var1.func_176195_g((World)BlockIn.f.field_71441_e, null);
        if (var2 < 0.0f) {
            return Float.MAX_VALUE;
}
        return var2 == 0.0f ? 0.0f : var2 * (var1.func_149688_o().func_76229_l() ? 30.0f : 100.0f);
}
    private void x$r3(long var1) {
        var1 = b ^ var1;
        int var5 = (int)((var1 ^ 0x6E01DE530DF7L) >>> 32);
        int var6 = (int)((var1 ^ 0x6E01DE530DF7L) << 32 >>> 32);
        int var9 = (int)((var1 ^ 0x517BB61A8B5AL) >>> 32);
        int var10 = (int)((var1 ^ 0x517BB61A8B5AL) << 32 >>> 48);
        int var11 = (int)((var1 ^ 0x517BB61A8B5AL) << 48 >>> 48);
        long var12 = var1 ^ 0x104CDDF84D71L;
        this.E();
        if (BlockIn.f.field_71462_r != null) {
            this.s(0L);
        } else {
            int var14 = this.S(true);
            int var15 = this.S(false);
            if (var14 == -1 && var15 == -1) {
                this.s(0L);
            } else {
                int n2 = this.M = var14 != -1 ? var14 : var15;
                if (!this.isOffset(var12)) {
                    this.s(0L);
                } else {
                    int n3 = this.I ? (var14 != -1 ? var14 : var15) : (this.M = var15 != -1 ? var15 : var14);
                    if (!this.Y) {
                        this.c(var9, (char)var10, (char)var11);
}
                    this.v(var5, var6);
}
}
}
}
    static void $jnicClinit() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        b = 26605011805357L;
        long var20 = b ^ 0x6D2E84110FBCL;
        h = new HashMap(13);
        byte[] var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};
        for (int var12 = 1; var12 < 8; ++var12) {
            var10003[var12] = (byte)(var20 << var12 * 8 >>> 56);
}
        Cipher var11 = Cipher.getInstance("DES/CBC/PKCS5Padding");
        var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
        String[] var18 = new String[3];
        int var16 = 0;
        String var15 = "P\u00802D\u00c3QQ\u00ce\u00aam\\\u009d\u000e\u00b3\u001dW\u0010\u0000Q(X0x_\u00a9\u0086\u008c\u00fa\u00a1\u00f5\u00e3\u00c2\u00f9\u0010\u00e8\u00c6\u00cd\u00cf\u00b5\u00ec\u009c\u001f\u009d\u00f4]\u00cdx\u00ba\u0091\u00ef";
        int var17 = "P\u00802D\u00c3QQ\u00ce\u00aam\\\u009d\u000e\u00b3\u001dW\u0010\u0000Q(X0x_\u00a9\u0086\u008c\u00fa\u00a1\u00f5\u00e3\u00c2\u00f9\u0010\u00e8\u00c6\u00cd\u00cf\u00b5\u00ec\u009c\u001f\u009d\u00f4]\u00cdx\u00ba\u0091\u00ef".length();
        int var14 = 16;
        int var13 = -1;
        while (true) {
            byte[] var19 = var11.doFinal(var15.substring(++var13, var13 + var14).getBytes("ISO-8859-1"));
            String var31 = BlockIn.b(var19).intern();
            int var10001 = -1;
            var18[var16++] = var31;
            if ((var13 += var14) >= var17) {
                c = var18;
                g = new String[3];
                r = new HashMap(13);
                var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                for (int var1 = 1; var1 < 8; ++var1) {
                    var10003[var1] = (byte)(var20 << var1 * 8 >>> 56);
}
                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                long[] var6 = new long[11];
                int var3 = 0;
                String var4 = "]\u00cd\u00aa\u000b;\u0099)\u0006X?]1\u00ac\f\u00dd\u00f61\u00ac\u0016\u0011&\u00bf\u00a4!\u008cr\u00e2\u00dd\u0097\u00ca\u000e\u00ab\u00b5\u00e5\u00f7\u00c6:pA\u00fb\u00f3wh4\u0019\u00eag\u00a84\u00f5\u0088\u0002\u0081\u00e3\u0013\u00b0LE\u0007\u00e6(\u00f6\u00a7\u00a0\u00a6\u0081\u0002\u00cc9\u00b1\u00a9\u001b";
                int var5 = "]\u00cd\u00aa\u000b;\u0099)\u0006X?]1\u00ac\f\u00dd\u00f61\u00ac\u0016\u0011&\u00bf\u00a4!\u008cr\u00e2\u00dd\u0097\u00ca\u000e\u00ab\u00b5\u00e5\u00f7\u00c6:pA\u00fb\u00f3wh4\u0019\u00eag\u00a84\u00f5\u0088\u0002\u0081\u00e3\u0013\u00b0LE\u0007\u00e6(\u00f6\u00a7\u00a0\u00a6\u0081\u0002\u00cc9\u00b1\u00a9\u001b".length();
                int var2 = 0;
                block6: while (true) {
                    var10001 = var2;
                    byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                    long[] var25 = var6;
                    var10001 = var3++;
                    long var34 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                    int var37 = -1;
                    while (true) {
                        long var8 = var34;
                        byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                        long var39 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                        switch (var37) {
                            case 0: {
                                var25[var10001] = var39;
                                if (var2 < var5) break;
                                o = var6;
                                J = new EnumFacing[]{EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.NORTH};
                                BlockInFaceOffset[] var26 = new BlockInFaceOffset[]{new BlockInFaceOffset(0, 1, 0, EnumFacing.DOWN), new BlockInFaceOffset(0, -1, 0, EnumFacing.UP), new BlockInFaceOffset(0, 0, -1, EnumFacing.NORTH), new BlockInFaceOffset(0, 0, 1, EnumFacing.SOUTH), new BlockInFaceOffset(1, 0, 0, EnumFacing.EAST), new BlockInFaceOffset(-1, 0, 0, EnumFacing.WEST)};
                                K = var26;
                                return;
}
                            default: {
                                var25[var10001] = var39;
                                if (var2 < var5) continue block6;
                                var4 = "k\u00fe #\u00c7\u00ea\u00ee\u00d6\u00ebJ\n?+\u00ae)\u0090";
                                var5 = "k\u00fe #\u00c7\u00ea\u00ee\u00d6\u00ebJ\n?+\u00ae)\u0090".length();
                                var2 = 0;
}
}
                        int var30 = var2;
                        var7 = var4.substring(var30, var2 += 8).getBytes("ISO-8859-1");
                        var25 = var6;
                        var10001 = var3++;
                        var34 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                        var37 = 0;
}
                    break;
}
}
            var14 = var15.charAt(var13);
}
}
    private static double H(double var0) {
        return var0 * var0;
}
    private boolean C(BlockPos var1) {
        BlockPos var2 = new BlockPos(MathHelper.func_76128_c((double)BlockIn.f.field_71439_g.field_70165_t), MathHelper.func_76128_c((double)BlockIn.f.field_71439_g.field_70163_u), MathHelper.func_76128_c((double)BlockIn.f.field_71439_g.field_70161_v));
        int var3 = var1.func_177958_n() - var2.func_177958_n();
        int var4 = var1.func_177956_o() - var2.func_177956_o();
        int var5 = var1.func_177952_p() - var2.func_177952_p();
        return var3 == 0 && var5 == 0 && var4 == 2 ? true : (var4 == 0 || var4 == 1) && (Math.abs(var3) == 1 && var5 == 0 || Math.abs(var5) == 1 && var3 == 0);
}
    private void s(long var1) {
        this.T(false);
        if (this.Y) {
            if (this.y && this.O != -1 && this.O != BlockIn.f.field_71439_g.field_71071_by.field_70461_c) {
                ItemUtil.P(this.O);
}
            this.Y = false;
            this.y = false;
            this.O = -1;
            this.M = -1;
            this.e = false;
            this.E();
}
}
    private void l(List var1, Vec3 var2, double var3, double var5, double var7, float var11, float var12) {
        float[] var13 = RotationUtil.W(new Vec3(var3, var5, var7), var2);
        var1.add(new BlockInRotationCandidate(Math.abs(MathHelper.func_76142_g((float)(var13[0] - var11))) + Math.abs(var13[1] - var12), var13[0], var13[1]));
}
    public void onPreMouseInput(PreMouseInputEvent var1, long var2) {
        try {
            var2 = b ^ var2;
            long var10001 = var2 ^ 0x6200919A1B22L;
            int var4 = (int)((var2 ^ 0x6200919A1B22L) >>> 32);
            int var5 = (int)((var2 ^ 0x6200919A1B22L) << 32 >>> 48);
            int var9 = (int)((var2 ^ 0x6A3947CD4752L) >>> 32);
            int var10 = (int)((var2 ^ 0x6A3947CD4752L) << 32 >>> 48);
            int var11 = (int)((var2 ^ 0x6A3947CD4752L) << 48 >>> 48);
            int var12 = (int)((var2 ^ 0x3AE663C692C7L) >>> 48);
            long var13 = (var2 ^ 0x3AE663C692C7L) << 16 >>> 16;
            long var15 = var2 ^ 0x2B86E94C5E1FL;
            long var17 = var2 ^ 0x2BA27D578BA3L;
            this.x$r3(var15);
            if (this.Y() && this.Y && this.R != null && this.n != null) {
                this.T(true);
                this.T(var9, (short)var10, (short)var11);
                if (this.e) {
                    this.e = false;
                    if (this.v != null && this.s != null && this.T != null) {
                        CombatUtil.u(this.v, this.s, this.T, swing.c(), false);
}
}
                if (this.Y) {
                    var1.q(var4, var5);
}
            } else {
                this.u((short)var12, var13);
                this.s(0L);
                this.e = false;
                this.E();
                RotationManager.O(var17);
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private boolean isOffset(long var1) {
        BlockInPlacement var7 = this.N(0L);
        if (var7 == null) {
            var7 = this.E(0L);
}
        if (var7 == null) {
            return false;
}
        BlockPos var8 = var7.h.func_177972_a(var7.C);
        this.I = this.C(var8);
        this.R = var7.h;
        this.n = var7.C;
        this.G = var7.j;
        this.B = var7.S;
        return true;
}
    private MovingObjectPosition I(double var1, float var3, float var4) {
        return BlockUtil.F(new float[]{var3, var4}, var1);
}
    private boolean I(ItemStack var1, BlockPos var2, EnumFacing var3) {
        return var1 != null && var1.func_77973_b() instanceof ItemBlock ? ((ItemBlock)var1.func_77973_b()).func_179222_a((World)BlockIn.f.field_71441_e, var2, var3, (EntityPlayer)BlockIn.f.field_71439_g, var1) : false;
}
    private void c(int var1, char var2, char var3) {
        if (!this.Y) {
            this.Y = true;
            this.y = false;
            this.O = BlockIn.f.field_71439_g.field_71071_by.field_70461_c;
}
}
    private double e(Vec3 var1, BlockPos var2) {
        double var3 = Math.max((double)var2.func_177958_n(), Math.min((double)(var2.func_177958_n() + 1), var1.field_72450_a));
        double var5 = Math.max((double)var2.func_177956_o(), Math.min((double)(var2.func_177956_o() + 1), var1.field_72448_b));
        double var7 = Math.max((double)var2.func_177952_p(), Math.min((double)(var2.func_177952_p() + 1), var1.field_72449_c));
        double var9 = var1.field_72450_a - var3;
        double var11 = var1.field_72448_b - var5;
        double var13 = var1.field_72449_c - var7;
        return var9 * var9 + var11 * var11 + var13 * var13;
}
    @Override
    public void A(long var1) {
        long var5 = var1 ^ 0x3A38010D60CAL;
        this.s(0L);
        this.e = false;
        this.E();
        RotationManager.O(var5);
}
    private BlockInPlacement N(long var1) {
        Vec3 var5 = new Vec3(BlockIn.f.field_71439_g.field_70165_t, BlockIn.f.field_71439_g.field_70163_u, BlockIn.f.field_71439_g.field_70161_v);
        BlockPos var6 = new BlockPos(MathHelper.func_76128_c((double)var5.field_72450_a), MathHelper.func_76128_c((double)var5.field_72448_b) + 2, MathHelper.func_76128_c((double)var5.field_72449_c));
        if (BlockUtil.a$r1(var6) && this.M >= 0 && this.M <= 8) {
            ItemStack var7 = BlockIn.f.field_71439_g.field_71071_by.field_70462_a[this.M];
            double var8 = range.L();
            Vec3 var10 = BlockIn.f.field_71439_g.func_174824_e(1.0f);
            double var11 = var8 * var8;
            double var13 = (var8 + 1.0) * (var8 + 1.0);
            int var15 = MathHelper.func_76128_c((double)var10.field_72448_b) + 1;
            int var16 = MathHelper.func_76128_c((double)(var10.field_72448_b + var8));
            int var17 = MathHelper.func_76128_c((double)(var10.field_72450_a - var8));
            int var18 = MathHelper.func_76128_c((double)(var10.field_72450_a + var8));
            int var19 = MathHelper.func_76128_c((double)(var10.field_72449_c - var8));
            int var20 = MathHelper.func_76128_c((double)(var10.field_72449_c + var8));
            ArrayList<BlockInScoredBlockPos> var21 = new ArrayList<BlockInScoredBlockPos>();
            for (int var22 = var15; var22 <= var16; ++var22) {
                for (int var23 = var17; var23 <= var18; ++var23) {
                    for (int var24 = var19; var24 <= var20; ++var24) {
                        double var33;
                        Block var32;
                        BlockPos var31;
                        double var25 = (double)var23 + 0.5 - var10.field_72450_a;
                        double var27 = (double)var22 + 0.5 - var10.field_72448_b;
                        double var29 = (double)var24 + 0.5 - var10.field_72449_c;
                        if (var25 * var25 + var27 * var27 + var29 * var29 > var13 || BlockUtil.a$r1(var31 = new BlockPos(var23, var22, var24)) || BlockUtil.p(var32 = BlockUtil.a(var31)) || var32 instanceof BlockFence || var32 instanceof BlockWall || (var33 = this.e(var10, var31)) > var11) continue;
                        var21.add(new BlockInScoredBlockPos(var33, var31));
}
}
}
            var21.sort((var0, var1x) -> Double.compare(var0.k, var1x.k));
            for (BlockInScoredBlockPos var37 : var21) {
                BlockInPlacement var38 = this.f(var7, var37.o, var10, var8, var15);
                if (var38 == null) continue;
                return var38;
}
            return null;
}
        return null;
}
    private boolean T() {
        double var1 = rotationTolerance.L();
        return (double)Math.abs(MathUtil.M(RotationManager.r, this.G)) <= var1 && (double)Math.abs(MathUtil.M(RotationManager.G, this.B)) <= var1;
}
    private void v(int var1, int var2) {
        int var7 = BlockIn.f.field_71439_g.field_71071_by.field_70461_c;
        if (this.M != -1 && this.M != var7) {
            ItemUtil.P(this.M);
            this.y = true;
}
}
    private int S(boolean var3) {
        int var4 = -1;
        float var5 = var3 ? -1.0f : Float.MAX_VALUE;
        for (int var6 = 8; var6 >= 0; --var6) {
            ItemStack var7 = BlockIn.f.field_71439_g.field_71071_by.field_70462_a[var6];
            if (!ItemUtil.u(var7)) continue;
            Block var8 = ((ItemBlock)var7.func_77973_b()).func_179223_d();
            float var9 = this.H(var8);
            if (!(var3 ? var9 > var5 : var9 < var5)) continue;
            var4 = var6;
}
        return var4;
}
                if (var5 < 224) {
                char var6 = (char)((char)(var5 & 0x1F) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 0x3F));
                var3[var1++] = var6;
                continue;
}
            if (var4 >= var2 - 2) continue;
            char var12 = (char)((char)(var5 & 0xF) << 12);
            byte var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F) << 6);
            var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F));
            var3[var1++] = var12;
}
        return new String(var3, 0, var1);
}
    private void T(int var1, short var2, short var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ b;
        long var8 = var4 ^ 0x27286A3318C3L;
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
        float[] var14 = this.a(RotationManager.r, RotationManager.G, this.G, this.B, angleStep.L());
        RotationManager.I(var14[0], 0L);
        RotationManager.A(var8, var14[1]);
        MovingObjectPosition var15 = this.I(range.L(), RotationManager.r, RotationManager.G);
        if (var15.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var12 = var15.func_178782_a();
            EnumFacing var13 = var15.field_178784_b;
            if (var12.equals((Object)this.R) && var13 == this.n && this.T()) {
                this.v = var12;
                this.s = var13;
                this.T = var15.field_72307_f;
                this.e = true;
}
}
}
    private BlockInPlacement f(ItemStack var1, BlockPos var2, Vec3 var3, double var4, int var6) {
        float var11 = RotationManager.r;
        float var12 = RotationManager.G;
        boolean var13 = Math.abs(var3.field_72448_b - (double)(var2.func_177956_o() + 1)) < Math.abs(var3.field_72448_b - (double)var2.func_177956_o());
        boolean var14 = Math.abs(var3.field_72449_c - (double)(var2.func_177952_p() + 1)) < Math.abs(var3.field_72449_c - (double)var2.func_177952_p());
        boolean var15 = Math.abs(var3.field_72450_a - (double)(var2.func_177958_n() + 1)) < Math.abs(var3.field_72450_a - (double)var2.func_177958_n());
        double var16 = var2.func_177958_n();
        double var18 = var2.func_177956_o();
        double var20 = var2.func_177952_p();
        double var22 = 0.020000000000000004;
        ArrayList<BlockInRotationCandidate> var24 = new ArrayList<BlockInRotationCandidate>((d + 1) * (d + 1) * 3 + 1);
        var24.add(new BlockInRotationCandidate(0.0, var11, var12));
        for (int var25 = 0; var25 <= d; ++var25) {
            double var26 = BlockIn.B((double)var25 * 0.2 + BlockIn.i(var22));
            for (int var28 = 0; var28 <= d; ++var28) {
                double var29 = BlockIn.B((double)var28 * 0.2 + BlockIn.i(var22));
                this.l(var24, var3, var16 + var29, var13 ? var18 + 1.0 - 0.05 : var18 + 0.05, var20 + var26, var11, var12);
                this.l(var24, var3, var16 + var29, var18 + var26, var14 ? var20 + 1.0 - 0.05 : var20 + 0.05, var11, var12);
                this.l(var24, var3, var15 ? var16 + 1.0 - 0.05 : var16 + 0.05, var18 + var26, var20 + var29, var11, var12);
}
}
        var24.sort((var0, var1x) -> Double.compare(var0.Y, var1x.Y));
        int var32 = var2.func_177956_o();
        for (BlockInRotationCandidate var27 : var24) {
            MovingObjectPosition var34 = this.I(var4, var27.j, var27.N);
            if (var34.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK) continue;
            BlockPos var35 = var34.func_178782_a();
            EnumFacing var30 = var34.field_178784_b;
            if (!var35.equals((Object)var2) || var35.func_177956_o() < var6 || var30 == EnumFacing.DOWN && var32 == var6 || !this.I(var1, var35, var30)) continue;
            return new BlockInPlacement(var35, var30, var27.j, var27.N);
}
        return null;
}
    public BlockIn(long var1) {
        super((b ^ var1 ^ 0x6E59FD9B6BDAL) >>> 16, (char)((b ^ var1 ^ 0x6E59FD9B6BDAL) << 48 >>> 48));
        this.declare("BlockIn", Category.World, "Automatically surrounds blocks when you are trying to break bed", new Setting[0]);
        var1 = b ^ var1;
        this.O = -1;
        this.M = -1;
}
    private BlockInPlacement j(double var1, float var3, float var4, BlockPos var5, EnumFacing var6, BlockPos var7) {
        MovingObjectPosition var8 = this.I(var1, var3, var4);
        if (var8.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK) {
            return null;
}
        BlockPos var9 = var8.func_178782_a();
        EnumFacing var10 = var8.field_178784_b;
        if (var9.equals((Object)var5) && var10 == var6) {
            BlockPos var11 = var9.func_177972_a(var10);
            return !var11.equals((Object)var7) ? null : new BlockInPlacement(var9, var10, var3, var4);
}
        return null;
}
    private void E() {
        this.R = null;
        this.n = null;
        this.v = null;
        this.s = null;
        this.T = null;
}
    @Override
    public final void x(long var1, EventBus var3) {
        BlockInBinder.s(var3, this);
}
    private Vec3 r(double var1) {
        if (BlockIn.f.field_71441_e != null && BlockIn.f.field_71439_g != null) {
            Vec3 var3 = null;
            double var4 = var1;
            for (Object var7 : BlockIn.f.field_71441_e.field_73010_i) {
                double var13;
                double var11;
                double var9;
                double var15;
                EntityPlayer var8;
                if (!(var7 instanceof EntityPlayer) || (var8 = (EntityPlayer)var7) == BlockIn.f.field_71439_g || f.func_147114_u() == null || f.func_147114_u().func_175102_a(var8.func_110124_au()) == null || !((var15 = (var9 = var8.field_70165_t - BlockIn.f.field_71439_g.field_70165_t) * var9 + (var11 = var8.field_70163_u - BlockIn.f.field_71439_g.field_70163_u) * var11 + (var13 = var8.field_70161_v - BlockIn.f.field_71439_g.field_70161_v) * var13) < var4)) continue;
                var4 = var15;
                var3 = new Vec3(var8.field_70165_t, var8.field_70163_u, var8.field_70161_v);
}
            return var3;
}
        return null;
}
    private float[] a(float var1, float var2, float var3, float var4, double var5) {
        float var7 = (float)var5;
        var7 = MathHelper.func_76131_a((float)var7, (float)1.0f, (float)(var7 * 2.0f));
        float var8 = MathHelper.func_76142_g((float)(var3 - var1));
        float var9 = var4 - var2;
        float var10 = var1 + MathHelper.func_76131_a((float)var8, (float)(-var7), (float)var7);
        float var11 = MathHelper.func_76131_a((float)(var2 + MathHelper.func_76131_a((float)var9, (float)(-var7), (float)var7)), (float)-90.0f, (float)90.0f);
        return new float[]{var10, var11};
}
    static {
        try {
            BlockIn.$jnicClinit();
            d = (int)Math.round(5.0);
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
        swing = new BooleanSetting("Swing", true);
        range = new NumberSetting("Range", 4.5f, 0.0f, 8.0f, 0.1f);
        angleStep = new NumberSetting("Angle-step", 60.0f, 1.0f, 180.0f, 1.0f);
        rotationTolerance = new NumberSetting("Rotation-tolerance", 25.0f, 20.0f, 100.0f, 1.0f);
        moveFix = new ModeSetting("Move-fix", "SILENT", "STRICT", "NONE");
}
}