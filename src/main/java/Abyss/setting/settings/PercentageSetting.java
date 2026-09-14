/*
 * Decompiled with CFR 0.152.
 */
package Abyss.setting.settings;

import Abyss.setting.Setting;
import Abyss.util.MathUtil;
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

public class PercentageSetting
extends Setting {
    private int n;
        private static long f;

    public void d(int var1) {
        this.n = var1;
}
    public PercentageSetting(String var1, int var2) {
        this.q = var1;
        this.n = var2;
}
    public void b(int var1, long var2, int var4) {
        this.n = MathUtil.k(var4, 0, (int)f);
}
    public int k() {
        return this.n;
}
    static {
        try {
            long var0 = a ^ 0x69F5DE2E2556L;
            byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var3 = 1; var3 < 8; ++var3) {
                var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
}
            Cipher var2 = Cipher.getInstance("DES/CBC/NoPadding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long var4 = -525348990261965068L;
            byte[] var6 = var2.doFinal(new byte[]{(byte)(var4 >>> 56), (byte)(var4 >>> 48), (byte)(var4 >>> 40), (byte)(var4 >>> 32), (byte)(var4 >>> 24), (byte)(var4 >>> 16), (byte)(var4 >>> 8), (byte)var4});
            long var8 = ((long)var6[0] & 0xFFL) << 56 | ((long)var6[1] & 0xFFL) << 48 | ((long)var6[2] & 0xFFL) << 40 | ((long)var6[3] & 0xFFL) << 32 | ((long)var6[4] & 0xFFL) << 24 | ((long)var6[5] & 0xFFL) << 16 | ((long)var6[6] & 0xFFL) << 8 | (long)var6[7] & 0xFFL;
            int var10001 = -1;
            f = var8;
}
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var7) {
            throw new RuntimeException(var7);
}
}
}