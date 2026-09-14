/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.synthetic;

import Abyss.enums.TargetHudElement;
import java.io.UnsupportedEncodingException;
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

public class ClosestPlayerHUDSwitchMapTargetHudElement {
    public static int[] b;

    static {
        try {
            long var11 = 70876829993951L;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var2 = 1; var2 < 8; ++var2) {
                var10003[var2] = (byte)(var11 << var2 * 8 >>> 56);
}
            Cipher var1 = Cipher.getInstance("DES/CBC/NoPadding");
            var1.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var0 = new long[2];
            int var4 = 0;
            String var5 = "(:\u00d5\u00a6\u00fc\u00cd\u0085\\\u00b4\u00e3\u00e9\u001d\u00df\u00ddL]";
            int var6 = "(:\u00d5\u00a6\u00fc\u00cd\u0085\\\u00b4\u00e3\u00e9\u001d\u00df\u00ddL]".length();
            int var3 = 0;
            do {
                int var10001 = var3;
                byte[] var7 = var5.substring(var10001, var3 += 8).getBytes("ISO-8859-1");
                long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                byte[] var10 = var1.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                long var10004 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                int var23 = -1;
                var0[var4++] = var10004;
            } while (var3 < var6);
            b = new int[TargetHudElement.values().length];
            try {
                ClosestPlayerHUDSwitchMapTargetHudElement.b[TargetHudElement.HEAD.ordinal()] = 1;
}
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
}
            try {
                ClosestPlayerHUDSwitchMapTargetHudElement.b[TargetHudElement.NAME.ordinal()] = 2;
}
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
}
            try {
                ClosestPlayerHUDSwitchMapTargetHudElement.b[TargetHudElement.HP.ordinal()] = 3;
}
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
}
            try {
                ClosestPlayerHUDSwitchMapTargetHudElement.b[TargetHudElement.HEIGHT.ordinal()] = 4;
}
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
}
            try {
                ClosestPlayerHUDSwitchMapTargetHudElement.b[TargetHudElement.ARROW.ordinal()] = 5;
}
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
}
            try {
                ClosestPlayerHUDSwitchMapTargetHudElement.b[TargetHudElement.TEAM.ordinal()] = (int)var0[1];
}
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
}
            try {
                ClosestPlayerHUDSwitchMapTargetHudElement.b[TargetHudElement.DIST.ordinal()] = (int)var0[0];
}
            catch (NoSuchFieldError noSuchFieldError) {}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var21) {
            throw new RuntimeException(var21);
}
}
}