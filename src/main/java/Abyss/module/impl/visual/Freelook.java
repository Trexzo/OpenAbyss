/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Freelook
extends Module {
    public static boolean G;
    private static long a;
    private int T;
    private float p;
    
    private float s;
    public static float N;
    public static ModeSetting mode;
    public static float v;

    public static float M() {
        return v;
}
    private static void zkm$clinit() {
        try {
            long var7 = a ^ 109798899218549L;
            Cipher var2;
            byte[] var10003 = new byte[]{(byte)(var7 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var3 = 1; var3 < 8; ++var3) {
                var10003[var3] = (byte)(var7 << var3 * 8 >>> 56);
            }
            (var2 = Cipher.getInstance("DES/CBC/NoPadding")).init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            byte[] var6 = var2.doFinal(new byte[]{19, -30, -100, 37, -37, 34, -57, -128});
            long var10 = ((long)var6[0] & 0xFFL) << 56 | ((long)var6[1] & 0xFFL) << 48 | ((long)var6[2] & 0xFFL) << 40 | ((long)var6[3] & 0xFFL) << 32 | ((long)var6[4] & 0xFFL) << 24 | ((long)var6[5] & 0xFFL) << 16 | ((long)var6[6] & 0xFFL) << 8 | (long)var6[7] & 0xFFL;
            long var0 = var10;
            G = (var0 & 1L) != 0L;
}
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var9) {
            throw new RuntimeException(var9);
}
}
    public static void L(boolean var0) {
        G = var0;
}
    public static void B(float var0) {
        N = var0;
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    public static float v() {
        return N;
}
    public Freelook(long var1) {
        super(a ^ var1 ^ 0x15C341ACD566L);
        this.declare("Freelook", Category.Visual, "Allows you to move your camera without moving your head", new Setting[0]);
        var1 = a ^ var1;
}
    public static boolean c() {
        return G;
}
    public static void v(float var0) {
        v = var0;
}
    @Override
    public void i(long var1) {
        this.p = Freelook.f.thePlayer.rotationYaw;
        this.s = Freelook.f.thePlayer.rotationPitch;
        this.T = Freelook.f.gameSettings.thirdPersonView;
        Freelook.f.gameSettings.thirdPersonView = 1;
        Freelook.B(this.p);
        Freelook.v(this.s);
        Freelook.L(true);
}
    @Override
    public void A(long var1) {
        Freelook.L(false);
        Freelook.f.gameSettings.thirdPersonView = this.T;
        Freelook.f.thePlayer.rotationYaw = this.p;
        Freelook.f.thePlayer.rotationPitch = this.s;
}
    static {
        a = 47083344118779L;
        zkm$clinit();
        mode = new ModeSetting("Mode", true, "HOLD", "HOLD", "TOGGLE");
}
}