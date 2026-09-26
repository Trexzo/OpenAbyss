/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.FallIndicatorBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.RaytraceUtil;
import Abyss.util.render.CustomFont;
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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class FallIndicator
extends Module
implements EventSubscriber {
    private static String[] c;
    private static Map h;
    private String M;
    private static int d;
    private float F;
    private double u;
    public static PercentageSetting minDamagePercentage;
    private static String[] v;
    private static Object[] o;
    private boolean s;
    private double e;
    public static BooleanSetting showFallDistance;
    private static float[] t;
    private String O;
    public static BooleanSetting onlyWhileSneaking;
    private static long b;
    private final ItemStack[] Y;
    private static Map n;
    private final ItemStack[] K;
    private static String[] g;
    private boolean L;
    private int U;
    
        private static long[] k;
    private int r;

    @Override
    public final void x(long var1, EventBus var3) {
        FallIndicatorBinder.r(var3, this);
}
    @Override
    public void A(long var1) {
        this.a(0L);
}
    private static int R(float var0) {
        float var3 = 2.5f;
        float var4 = 20.0f;
        float var5 = MathHelper.clamp_float((float)((var0 - var3) / (var4 - var3)), (float)0.0f, (float)1.0f);
        int var6 = (int)(255.0f * (1.0f - var5));
        return 0xFFFF0000 | var6 << 8;
}
    private static String z(float var0) {
        return var0 == (float)((long)var0) ? Long.toString((long)var0) : Float.toString(var0);
}
    public void onPostTick(PostTickEvent var1, long var2) {
        if (!this.isSneaking()) {
            this.G('\u0000');
}
}
    public void onRender2D(Render2DEvent var1, long var2) {
        if (!this.isSneaking() && this.L && this.M != null) {
            CustomFont var12 = Font.F(0L);
            ScaledResolution var13 = var1.C;
            int var14 = var13.getScaledWidth() / 2;
            int var15 = var13.getScaledHeight() / 2 + var13.getScaledHeight() / 45;
            var12.v(this.M, (float)var14 - var12.R(this.M, 52019766876817L) / 2.0f, var15, -1, 88827598794260L, true);
            if (showFallDistance.c() && this.O != null) {
                var12.v(this.O, (float)var14 - var12.R(this.O, 52019766876817L) / 2.0f, (float)var15 + var12.o(60714858652844L), this.r, 88827598794260L, true);
}
}
}
    private static float o(float var0, int var1) {
        if (var1 < 0) {
            return var0;
}
        float var2 = var1 < t.length ? t[var1] : (float)Math.pow(10.0, var1);
        return (float)Math.round(var0 * var2) / var2;
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
    public FallIndicator(long var1) {
        super(b ^ var1 ^ 0x1926C86F8612L);
        this.declare("FallIndicator", Category.Visual_utility, "Display the damage amount you might receive when looking at the ground", new Setting[0]);
        var1 = b ^ var1;
        this.Y = new ItemStack[4];
        this.K = new ItemStack[4];
        this.e = -1.0;
        this.u = -1.0;
        this.F = 0.0f;
        this.U = -1;
        this.s = false;
        this.L = false;
        this.M = null;
        this.O = null;
        this.r = -1;
}
    private void a(long var1) {
        this.e = -1.0;
        this.u = -1.0;
        this.F = 0.0f;
        this.U = -1;
        for (int var3 = 0; var3 < 4; ++var3) {
            this.Y[var3] = null;
            this.K[var3] = null;
}
        this.s = false;
        this.L = false;
        this.M = null;
        this.O = null;
        this.r = -1;
}
    private void G(char var1) {
        this.L = false;
        this.M = null;
        this.O = null;
        this.r = -1;
        MovingObjectPosition var8 = RaytraceUtil.J(1000.0);
        BlockPos var9 = var8.getBlockPos();
        if (var9 != null && !(RaytraceUtil.d(FallIndicator.f.thePlayer.posX, FallIndicator.f.thePlayer.posZ, var9.getX(), var9.getZ()) >= 5.0)) {
            float var13;
            double var10 = var8.getBlockPos().getY();
            boolean var12 = FallIndicator.f.thePlayer.onGround;
            if (var12) {
                this.e = -1.0;
                this.u = -1.0;
                this.F = 0.0f;
                if (var10 == -1.0) {
                    return;
}
                var13 = (float)Math.max(0.0, FallIndicator.f.thePlayer.posY - var10);
            } else {
                if (this.e == -1.0) {
                    this.e = FallIndicator.f.thePlayer.posY;
                    this.u = var10;
                    this.F = 0.0f;
                } else if (var10 != this.u) {
                    this.u = var10;
                    this.F = 0.0f;
}
                if (this.e == -1.0 || this.u == -1.0) {
                    return;
}
                if (this.F == 0.0f) {
                    this.F = (float)Math.max(0.0, this.e - this.u);
}
                var13 = this.F;
}
            if (!(var13 <= 4.0f)) {
                double var36;
                double var26;
                var13 -= 1.0f;
                PotionEffect var14 = FallIndicator.f.thePlayer.getActivePotionEffect(Potion.jump);
                float var15 = var14 != null ? (float)(var14.getAmplifier() + 1) : 0.0f;
                PotionEffect var16 = FallIndicator.f.thePlayer.getActivePotionEffect(Potion.resistance);
                boolean var17 = var16 != null;
                int var18 = var17 ? var16.getAmplifier() + 1 : 0;
                boolean var19 = false;
                for (int var20 = 0; var20 < 4; ++var20) {
                    ItemStack var21;
                    this.K[var20] = var21 = FallIndicator.f.thePlayer.inventory.armorItemInSlot(var20);
                    if (this.Y[var20] == var21) continue;
                    var19 = true;
}
                int var32 = this.U;
                if (var19 || !this.s) {
                    long var33 = 0L;
                    for (int var23 = 0; var23 < 100; ++var23) {
                        int var24 = EnchantmentHelper.getEnchantmentModifierDamage((ItemStack[])this.K, (DamageSource)DamageSource.fall);
                        if (var24 > 20) {
                            var24 = 20;
}
                        var33 += (long)var24;
}
                    this.U = var32 = (int)Math.round((double)var33 / 100.0);
                    System.arraycopy(this.K, 0, this.Y, 0, 4);
                    this.s = true;
}
                float var34 = var13 - 3.0f - var15;
                double var22 = Math.round(Math.max(0.0f, var34));
                if (var17 && var22 > 0.0) {
                    int var35 = var18 * 5;
                    int var25 = 25 - var35;
                    var22 = (double)var25 * var22 / 25.0;
}
                if (var22 > 0.0 && var32 > 0) {
                    var22 = (double)(25 - var32) * var22 / 25.0;
}
                if (!((var26 = var22 / (var36 = (double)FallIndicator.f.thePlayer.getHealth()) * 100.0) < (double)minDamagePercentage.k()) && var26 != 0.0) {
                    double var28 = var22 / var36;
                    String var30 = var22 >= var36 ? "\u00a74" : (var28 >= 0.7 ? "\u00a7c" : (var28 >= 0.5 ? "\u00a76" : (var28 >= 0.3 ? "\u00a7e" : "\u00a7a")));
                    this.M = var30 + "-" + FallIndicator.z(FallIndicator.o((float)var22, 1)) + " " + var30 + "HP";
                    this.L = true;
                    if (showFallDistance.c()) {
                        this.r = FallIndicator.R(var13);
                        this.O = FallIndicator.z(FallIndicator.o(var13, 1)) + "m";
}
}
}
}
}
    private static void a() {
        FallIndicator.o[0] = "Q\u0015J+`'\\";
        FallIndicator.o[1] = Long.TYPE;
        FallIndicator.v[1] = "java/lang/Long";
        FallIndicator.o[2] = "\"qY)z]\u0015f]#7y\u0002m\u0007?";
        FallIndicator.o[3] = "d?\u0010mN1U";
        FallIndicator.o[4] = Void.TYPE;
        FallIndicator.v[4] = "java/lang/Void";
        FallIndicator.o[5] = "\u00127\u00005U?\u00198\u0011z41\u00123\u0015 ";
        FallIndicator.o[6] = "@e>\u0003o[]n'mq>\u001b?0\u0010m\u0007O}/\u001d\u000b\u0007Fie\f{\u000eL|&m1T\u0013bf\u0017`@Af_V4\\Xt`\u001csYI\u0004";
}
    private boolean isSneaking() {
        if (FallIndicator.f.currentScreen != null) {
            return true;
}
        if (FallIndicator.f.gameSettings.thirdPersonView != 0) {
            return true;
}
        if (FallIndicator.f.gameSettings.showDebugInfo) {
            return true;
}
        if (FallIndicator.f.thePlayer.capabilities.isCreativeMode) {
            return true;
}
        return FallIndicator.f.thePlayer.capabilities.allowFlying ? true : onlyWhileSneaking.c() && !FallIndicator.f.thePlayer.isSneaking();
}
    private static void zkm$clinit() {
        try {
            o = new Object[7]; v = new String[7]; a(); h = new HashMap(13); long var11 = b ^ 97273488151114L;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var14 = 1; var14 < 8; ++var14) { var10003[var14] = (byte)(var11 << var14 * 8 >>> 56); }
            Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var20 = new String[6];
            int var18 = 0;
            String var17 = "\u00a2\u0086\u00ba/4g\u00e6\u00f4\u00c9\u00b5\u0080\u00ed\u0084\u007f\u00b7E\u0010\u00ef\u00a7g\u0014\u00b2\u00e7>\u00fc\u000f\u0006\u00ad\\\u00a6\u00b7\r.\u0010\u00ed\u00b9\u009b6\u00cfN\u00b3\u00a9\u0084m\u00e2yN86r\u0010\u00ff\u0099\u00a7\u0087\u0000\u009bi\u00dd;\u00f6\u00f3\u00d2I\u00cdG\u00ee";
            int var19 = "\u00a2\u0086\u00ba/4g\u00e6\u00f4\u00c9\u00b5\u0080\u00ed\u0084\u007f\u00b7E\u0010\u00ef\u00a7g\u0014\u00b2\u00e7>\u00fc\u000f\u0006\u00ad\\\u00a6\u00b7\r.\u0010\u00ed\u00b9\u009b6\u00cfN\u00b3\u00a9\u0084m\u00e2yN86r\u0010\u00ff\u0099\u00a7\u0087\u0000\u009bi\u00dd;\u00f6\u00f3\u00d2I\u00cdG\u00ee".length();
            int var16 = 16;
            int var25 = -1;
            block9: while (true) {
                String var26 = var17.substring(++var25, var25 + var16);
                int var10001 = -1;
                while (true) {
                    byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
                    String var37 = FallIndicator.b(var21).intern();
                    switch (var10001) {
                        case 0: {
                            var20[var18++] = var37;
                            if ((var25 += var16) >= var19) {
                                c = var20;
                                g = new String[6];
                                n = new HashMap(13);
                                var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var1 = 1; var1 < 8; ++var1) {
                                    var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var6 = new long[15];
                                int var3 = 0;
                                String var4 = "5\u00ae\b\u00d6A\u00b6\"\u00d60\u00c3R\u009d\u00e2\u00ba\u00a7\u00d9\u00c5%\u0007\u0096\u0005\u0006b\t\u00ef\u00ebq\u00a0\u009a\u00af\u0083o\u00b9N\u00b0\u00ad\u00f4\u0082\u00c1\u008bM\u000b\u00a9\u00ceC)E3\u00cf\u0006\u00c7o\u0087g\u00f7\u00bf\u00d0\u001bs\u00a5Lv\u0002kuuG\u00b3\u00b8b\u00a7q-\r6\u0001\u00d3\u00d6\u0088\u00c7\u0015X/\u0096\u0093;\u00b2 \u00a2\u009e\u00a8\u00f6ZJ\u00e2\u00db{\u00fa\u00e58>?V4";
                                int var5 = "5\u00ae\b\u00d6A\u00b6\"\u00d60\u00c3R\u009d\u00e2\u00ba\u00a7\u00d9\u00c5%\u0007\u0096\u0005\u0006b\t\u00ef\u00ebq\u00a0\u009a\u00af\u0083o\u00b9N\u00b0\u00ad\u00f4\u0082\u00c1\u008bM\u000b\u00a9\u00ceC)E3\u00cf\u0006\u00c7o\u0087g\u00f7\u00bf\u00d0\u001bs\u00a5Lv\u0002kuuG\u00b3\u00b8b\u00a7q-\r6\u0001\u00d3\u00d6\u0088\u00c7\u0015X/\u0096\u0093;\u00b2 \u00a2\u009e\u00a8\u00f6ZJ\u00e2\u00db{\u00fa\u00e58>?V4".length();
                                int var2 = 0;
                                block12: while (true) {
                                    var10001 = var2;
                                    byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                                    long[] var29 = var6;
                                    var10001 = var3++;
                                    long var41 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                    int var44 = -1;
                                    while (true) {
                                        long var8 = var41;
                                        byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                        long var46 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                        switch (var44) {
                                            case 0: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) break;
                                                k = var6;
                                                d = -1;
                                                t = new float[]{1.0f, 10.0f, 100.0f, 1000.0f};
                                                return;
}
                                            default: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) continue block12;
                                                var4 = "\u00d8\u0006\u0090.\u00ea\u00a6\u00cc\u0091\u0007\u00ca\u00aeqHy\u00ef\u00cd";
                                                var5 = "\u00d8\u0006\u0090.\u00ea\u00a6\u00cc\u0091\u0007\u00ca\u00aeqHy\u00ef\u00cd".length();
                                                var2 = 0;
}
}
                                        int var35 = var2;
                                        var7 = var4.substring(var35, var2 += 8).getBytes("ISO-8859-1");
                                        var29 = var6;
                                        var10001 = var3++;
                                        var41 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                        var44 = 0;
}
}
}
                            var16 = var17.charAt(var25);
                            break;
}
                        default: {
                            var20[var18++] = var37;
                            if ((var25 += var16) < var19) {
                                var16 = var17.charAt(var25);
                                continue block9;
}
                            var17 = "\u00f1pg\u00e1\u00de\u00174`\u00cd\u000e\u0003-\u00a0=\u0000v\u0010Br\u00aac\u00dbT\u00be\u0080\u00aa=\u009aP\u00f2\u00bd:Y";
                            var19 = "\u00f1pg\u00e1\u00de\u00174`\u00cd\u000e\u0003-\u00a0=\u0000v\u0010Br\u00aac\u00dbT\u00be\u0080\u00aa=\u009aP\u00f2\u00bd:Y".length();
                            var16 = 16;
                            var25 = -1;
}
}
                    var26 = var17.substring(++var25, var25 + var16);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var22) {
            throw new RuntimeException(var22);
}
}
    static {
        b = 69942045818855L;
        zkm$clinit();
        minDamagePercentage = new PercentageSetting("Min-damage-percentage", 0);
        showFallDistance = new BooleanSetting("Show-fall-distance", true);
        onlyWhileSneaking = new BooleanSetting("Only-while-sneaking", false);
}
}