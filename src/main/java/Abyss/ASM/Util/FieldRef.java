/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM.Util;

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

public final class FieldRef {
    private static long a;
    static {
        a = 75098664684037L;
    }
    private static long b;
    private final String Z;
    private final String H;
    
    public FieldRef(String var1, String var2) {
        this.Z = var1;
        this.H = var2;
}
    static String Z(FieldRef var0) {
        return var0.H;
}
    public int hashCode() {
        return (int)b * this.Z.hashCode() + this.H.hashCode();
}
    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
}
        if (!(var1 instanceof FieldRef)) {
            return false;
}
        FieldRef var2 = (FieldRef)var1;
        return this.Z.equals(var2.Z) && this.H.equals(var2.H);
}
    static {
        try {
            long var0 = a ^ 0x7EF513738299L;
            byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var3 = 1; var3 < 8; ++var3) {
                var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
}
            Cipher var2 = Cipher.getInstance("DES/CBC/NoPadding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long var4 = -5385318924343614151L;
            byte[] var6 = var2.doFinal(new byte[]{(byte)(var4 >>> 56), (byte)(var4 >>> 48), (byte)(var4 >>> 40), (byte)(var4 >>> 32), (byte)(var4 >>> 24), (byte)(var4 >>> 16), (byte)(var4 >>> 8), (byte)var4});
            long var8 = ((long)var6[0] & 0xFFL) << 56 | ((long)var6[1] & 0xFFL) << 48 | ((long)var6[2] & 0xFFL) << 40 | ((long)var6[3] & 0xFFL) << 32 | ((long)var6[4] & 0xFFL) << 24 | ((long)var6[5] & 0xFFL) << 16 | ((long)var6[6] & 0xFFL) << 8 | (long)var6[7] & 0xFFL;
            int var10001 = -1;
            b = var8;
}
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var7) {
            throw new RuntimeException(var7);
}
}
}