/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MathHelper
 */
package Abyss.util;

import Abyss.AbyssClient;
import Abyss.event.events.JumpEvent;
import Abyss.util.ClientUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class MoveUtil {
    
    
    public static final double n = (double)0.42f;
    private static Integer[] c;
    private static Object[] e;
    public static final double p = 0.5203620003898759;
    private static String[] f;
    private static Minecraft z;
    private static long[] b;
    private static long a;
    private static Map d;
    public static final double Y = 0.221;

    public static boolean i(double var0, double var2) {
        return MoveUtil.I(var0, var2, -1.0);
}
    public static double A(double var0) {
        return MoveUtil.z.thePlayer.isPotionActive(Potion.jump) ? var0 + (double)((float)(MoveUtil.z.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f) : var0;
}
    public static boolean a(EntityLivingBase var0) {
        return var0 != null && (var0.moveForward != 0.0f || var0.moveStrafing != 0.0f);
}
    public static double I(EntityPlayer var0) {
        double var1 = 0.2873;
        if (var0.isPotionActive(Potion.moveSpeed)) {
            int var3 = var0.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            var1 *= 1.0 + 0.2 * (double)(var3 + 1);
}
        return var1;
}
    private static int a(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x3FB2;
        if (c[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = b[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])d.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    d.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/util/MoveUtil", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            MoveUtil.c[var3] = var15;
}
        return c[var3];
}
    public static int K() {
        int var0 = 0;
        if (MoveUtil.z.gameSettings.keyBindLeft.isKeyDown()) {
            ++var0;
}
        if (MoveUtil.z.gameSettings.keyBindRight.isKeyDown()) {
            --var0;
}
        return var0;
}
    public static void h(float var0, char var1, boolean var2, long var3) {
        long var5 = ((long)var1 << 48 | var3 << 16 >>> 16) ^ a;
        long var9 = var5 ^ 0x36DFA07FAA8L;
        JumpEvent var11 = new JumpEvent((float)MoveUtil.P(), var0);
        AbyssClient.w.e(var11, var9);
        if (!var11.a()) {
            MoveUtil.z.thePlayer.motionY = var11.o();
            if (MoveUtil.z.thePlayer.isPotionActive(Potion.jump)) {
                MoveUtil.z.thePlayer.motionY += (double)((float)(MoveUtil.z.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f);
}
            if (var2) {
                float var12 = var11.j() * ((float)Math.PI / 180);
                MoveUtil.z.thePlayer.motionX -= (double)(MathHelper.sin((float)var12) * 0.2f);
                MoveUtil.z.thePlayer.motionZ += (double)(MathHelper.cos((float)var12) * 0.2f);
}
            MoveUtil.z.thePlayer.isAirBorne = true;
}
}
    public static boolean A$r1() {
        return Math.abs(MoveUtil.z.thePlayer.moveForward) >= 0.8f || Math.abs(MoveUtil.z.thePlayer.moveStrafing) >= 0.8f;
}
    public static double y() {
        float var2 = RotationManager.p();
        if (MoveUtil.z.thePlayer.movementInput.moveForward < 0.0f) {
            var2 += 180.0f;
}
        float var3 = 1.0f;
        if (MoveUtil.z.thePlayer.movementInput.moveForward < 0.0f) {
            var3 = -0.5f;
        } else if (MoveUtil.z.thePlayer.movementInput.moveForward > 0.0f) {
            var3 = 0.5f;
}
        if (MoveUtil.z.thePlayer.movementInput.moveStrafe > 0.0f) {
            var2 -= 90.0f * var3;
}
        if (MoveUtil.z.thePlayer.movementInput.moveStrafe < 0.0f) {
            var2 += 90.0f * var3;
}
        return Math.toRadians(var2);
}
    public static double k(long var0) {
        return MoveUtil.U(29842568986254L, (EntityPlayer)MoveUtil.z.thePlayer);
}
    public static boolean V(int var0, char var1, int var2, boolean var3) {
        long var4 = ((long)var0 << 32 | (long)var1 << 48 >>> 32 | (long)var2 << 48 >>> 48) ^ a;
        return var3 ? !(!(MoveUtil.z.thePlayer.moveForward >= 0.8f) || MoveUtil.z.thePlayer.isCollidedHorizontally || MoveUtil.z.thePlayer.getFoodStats().getFoodLevel() <= MoveUtil.a(6529, 0x661F4232FAD341BEL ^ var4) && !MoveUtil.z.thePlayer.capabilities.allowFlying || MoveUtil.z.thePlayer.isPotionActive(Potion.blindness) || MoveUtil.z.thePlayer.isUsingItem() || MoveUtil.z.thePlayer.isSneaking()) : MoveUtil.A$r1();
}
    public static double P() {
        return MoveUtil.A(0.42f);
}
    public static double U(long var0, EntityPlayer var2) {
        return var2 != null && var2.ticksExisted >= 1 ? MoveUtil.F(var2.lastTickPosX, var2.lastTickPosZ) * (double)(20.0f * ClientUtil.b((long)75703014522979L).timerSpeed) : 0.0;
}
    public static double H(int var0, int var1) {
        return Math.sqrt(var0 * var0 + var1 * var1);
}
    public static boolean I(double var0, double var2, double var4) {
        AxisAlignedBB var6 = MoveUtil.z.thePlayer.getEntityBoundingBox().offset(var0, var4, var2);
        return MoveUtil.z.theWorld.getCollidingBoundingBoxes((Entity)MoveUtil.z.thePlayer, var6).isEmpty();
}
    public static float X(int var0, short var1) {
        return MoveUtil.i(RotationManager.p(), MoveUtil.f(), MoveUtil.K());
}
    public static double u(EntityPlayer var0) {
        return Math.sqrt(var0.motionX * var0.motionX + var0.motionZ * var0.motionZ);
}
    public static void y(double var0, long var2) {
        var2 = a ^ var2;
        int var4 = (int)((var2 ^ 0x629B058A464L) >>> 56);
        double var7 = MinecraftRef.c((byte)((byte)var4), (long)0L).gameSettings.keyBindForward.isKeyDown() ? 1.0 : (MinecraftRef.c((byte)((byte)var4), (long)0L).gameSettings.keyBindBack.isKeyDown() ? -1.0 : 0.0);
        double var9 = MinecraftRef.c((byte)((byte)var4), (long)0L).gameSettings.keyBindLeft.isKeyDown() ? 1.0 : (MinecraftRef.c((byte)((byte)var4), (long)0L).gameSettings.keyBindRight.isKeyDown() ? -1.0 : 0.0);
        float var11 = RotationManager.p();
        if (MoveUtil.o()) {
            if (var7 != 0.0) {
                if (var9 > 0.0) {
                    var11 += var7 > 0.0 ? -45.0f : 45.0f;
                } else if (var9 < 0.0) {
                    var11 += var7 > 0.0 ? 45.0f : -45.0f;
}
                var9 = 0.0;
                if (var7 > 0.0) {
                    var7 = 1.0;
                } else if (var7 < 0.0) {
                    var7 = -1.0;
}
}
            double var12 = Math.cos(Math.toRadians(var11 + 89.5f));
            double var14 = Math.sin(Math.toRadians(var11 + 89.5f));
            MoveUtil.z.thePlayer.motionX = var7 * var0 * var12 + var9 * var0 * var14;
            MoveUtil.z.thePlayer.motionZ = var7 * var0 * var14 - var9 * var0 * var12;
        } else {
            MoveUtil.z.thePlayer.motionX = 0.0;
            MoveUtil.z.thePlayer.motionZ = 0.0;
}
}
    public static boolean o() {
        return MoveUtil.a((EntityLivingBase)MoveUtil.z.thePlayer);
}
    public static void m() {
        MoveUtil.z.thePlayer.motionZ = 0.0;
        MoveUtil.z.thePlayer.motionY = 0.0;
        MoveUtil.z.thePlayer.motionX = 0.0;
}
    public static double p(float var0, double var1, double var3) {
        if (var1 < 0.0) {
            var0 += 180.0f;
}
        float var5 = 1.0f;
        if (var1 < 0.0) {
            var5 = -0.5f;
        } else if (var1 > 0.0) {
            var5 = 0.5f;
}
        if (var3 > 0.0) {
            var0 -= 90.0f * var5;
}
        if (var3 < 0.0) {
            var0 += 90.0f * var5;
}
        return Math.toRadians(var0);
}
    public static double V(Entity var0) {
        return Math.sqrt(var0.motionX * var0.motionX + var0.motionZ * var0.motionZ);
}
    public static boolean v(long var0) {
        return MoveUtil.z.thePlayer.moveForward >= 0.8f && !MoveUtil.z.thePlayer.isCollidedHorizontally && (MoveUtil.z.thePlayer.getFoodStats().getFoodLevel() > MoveUtil.a(27762, 6312494855400308921L) || MoveUtil.z.thePlayer.capabilities.allowFlying) && !MoveUtil.z.thePlayer.isPotionActive(Potion.blindness) && !MoveUtil.z.thePlayer.isSneaking();
}
    public static boolean Q() {
        AxisAlignedBB var0 = MoveUtil.z.thePlayer.getEntityBoundingBox();
        AxisAlignedBB var1 = var0.offset(0.0, -0.01, 0.0);
        return !MoveUtil.z.thePlayer.worldObj.getCollidingBoundingBoxes((Entity)MoveUtil.z.thePlayer, var1).isEmpty();
}
    private static void a() {
        MoveUtil.e[0] = "QY\r\rEUZ";
        MoveUtil.e[1] = Float.TYPE;
        MoveUtil.f[1] = "java/lang/Float";
        MoveUtil.e[2] = "p\"^%7b{-OjVlp&K0";
        MoveUtil.e[3] = "\u0016\u0019*d\bpYF:\u0005\u0013\u0000\u0012\u001a`g\u0000aPO->k";
}
    public static void L() {
        MoveUtil.z.thePlayer.motionZ = 0.0;
        MoveUtil.z.thePlayer.motionX = 0.0;
}
    public static double V() {
        return MoveUtil.V((Entity)MoveUtil.z.thePlayer);
}
    public static double F(double var0, double var2) {
        double var4 = MoveUtil.z.thePlayer.posX - var0;
        double var6 = MoveUtil.z.thePlayer.posZ - var2;
        return MathHelper.sqrt_double((double)(var4 * var4 + var6 * var6));
}
    public static double A() {
        return MoveUtil.I((EntityPlayer)MoveUtil.z.thePlayer);
}
    public static int f() {
        int var0 = 0;
        if (MoveUtil.z.gameSettings.keyBindForward.isKeyDown()) {
            ++var0;
}
        if (MoveUtil.z.gameSettings.keyBindBack.isKeyDown()) {
            --var0;
}
        return var0;
}
    public static void r(double var0) {
        if (MoveUtil.o()) {
            double var9 = MoveUtil.y();
            MoveUtil.z.thePlayer.motionX = -Math.sin(var9) * var0 / 4.0;
            MoveUtil.z.thePlayer.motionZ = Math.cos(var9) * var0 / 4.0;
}
}
    public static float i(float var0, float var1, float var2) {
        if (var1 < 0.0f) {
            var0 += 180.0f;
}
        if (var2 != 0.0f) {
            float var3 = var1 == 0.0f ? 1.0f : 0.5f * Math.signum(var1);
            var0 += -90.0f * var3 * Math.signum(var2);
}
        return MathHelper.wrapAngleTo180_float((float)var0);
}
    public static double r() {
        return MoveUtil.u((EntityPlayer)MoveUtil.z.thePlayer);
}
    private static void zkm$clinit() {
        try {
            long var11 = a ^ 104661204716309L;
            e = new Object[4];
            f = new String[4];
            a();
            d = new HashMap(13);
            Cipher var0;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var1 = 1; var1 < 8; ++var1) {
                var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
            }
            (var0 = Cipher.getInstance("DES/CBC/NoPadding")).init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var6 = new long[6];
            int var3 = 0;
            String var4 = "V\u00f29\u000f\u0000s\u00b1D\u0098\u00e6\u009f\u00ffW\u0019\u0017vlm\u001e\u00e6=\u0010\u0003e\b\u0090\u00bf\u00a3'\u0005\u0013W";
            int var5 = "V\u00f29\u000f\u0000s\u00b1D\u0098\u00e6\u009f\u00ffW\u0019\u0017vlm\u001e\u00e6=\u0010\u0003e\b\u0090\u00bf\u00a3'\u0005\u0013W".length();
            int var2 = 0;
            block6: while (true) {
                int var10001 = var2;
                byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                long[] var18 = var6;
                var10001 = var3++;
                long var21 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                int var23 = -1;
                while (true) {
                    long var8 = var21;
                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                    long var25 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                    switch (var23) {
                        case 0: {
                            var18[var10001] = var25;
                            if (var2 < var5) break;
                            b = var6;
                            c = new Integer[6];
                            return;
}
                        default: {
                            var18[var10001] = var25;
                            if (var2 < var5) continue block6;
                            var4 = "vx\u00cd\u00d7e\u000b\u00a6\u00e3\u008b#]\u000b\u00a5\u007f]\u00a9";
                            var5 = "vx\u00cd\u00d7e\u000b\u00a6\u00e3\u008b#]\u000b\u00a5\u007f]\u00a9".length();
                            var2 = 0;
}
}
                    int var20 = var2;
                    var7 = var4.substring(var20, var2 += 8).getBytes("ISO-8859-1");
                    var18 = var6;
                    var10001 = var3++;
                    var21 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                    var23 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var16) {
            throw new RuntimeException(var16);
}
}
    static {
        a = 118218968134521L;
        zkm$clinit();
        z = MinecraftRef.c((byte)0, 0L);
}
}