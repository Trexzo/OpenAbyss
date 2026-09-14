/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.swing;

import Abyss.ui.swing.VisualSpoofWindowCloseListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import javax.swing.JPanel;

class VisualSpoofPreviewPanel
extends JPanel {
    private static long[] c;
    private static String b;
    private volatile BufferedImage g;
    
    
    

    VisualSpoofPreviewPanel(VisualSpoofWindowCloseListener var1) {
        this();
}
    public void N(BufferedImage var1) {
        this.g = var1;
}
    @Override
    protected void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Graphics2D var4 = (Graphics2D)var1;
        var4.setColor(Color.BLACK);
        var4.fillRect(0, 0, this.getWidth(), this.getHeight());
        BufferedImage var5 = this.g;
        if (var5 == null) {
            var4.setColor(Color.DARK_GRAY);
            var4.drawString(b, 16, 24);
        } else {
            int var6 = this.getWidth();
            int var7 = this.getHeight();
            int var8 = var5.getWidth();
            int var9 = var5.getHeight();
            if (Math.abs(var6 - var8) <= 2 && Math.abs(var7 - var9) <= 2) {
                var4.drawImage(var5, 0, 0, var6, var7, null);
            } else {
                double var10 = Math.min((double)var6 / (double)var8, (double)var7 / (double)var9);
                int var12 = Math.max(1, (int)Math.round((double)var8 * var10));
                int var13 = Math.max(1, (int)Math.round((double)var9 * var10));
                int var14 = (var6 - var12) / 2;
                int var15 = (var7 - var13) / 2;
                var4.drawImage(var5, var14, var15, var12, var13, null);
}
}
}
    private VisualSpoofPreviewPanel() {
}
    static {
        try {
            Cipher var0;
            Cipher var13;
            long var11 = a ^ 0x9B635C1A14BL;
            Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var14 = 1; var14 < 8; ++var14) {
                var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
}
            var10000.init(2, (Key)var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            byte[] var15 = var13.doFinal("vT\u0089\u00ccQ\u00d5\u008dq\u0001;\u00b7\u008d\u00abH\u00a7.&\u0011\u00c2\u00e9B\u0013\u0007/\u00f7\u0098\n9\u0012\u00ff\u00c1\u00ee\u0006\u001e\u00d1\u00ab\u00af\u0099?a".getBytes("ISO-8859-1"));
            String var20 = VisualSpoofPreviewPanel.a(var15).intern();
            int var10001 = -1;
            b = var20;
            e = new HashMap(13);
            var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
            var10002 = SecretKeyFactory.getInstance("DES");
            var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var1 = 1; var1 < 8; ++var1) {
                var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
            var10000.init(2, (Key)var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var6 = new long[2];
            int var3 = 0;
            String var4 = "+\u008a\u0086\u00fb\u00fd\u00cel\u0082j\u008f\u0010\u00dc\u00f8%!a";
            int var5 = "+\u008a\u0086\u00fb\u00fd\u00cel\u0082j\u008f\u0010\u00dc\u00f8%!a".length();
            int var2 = 0;
            do {
                var10001 = var2;
                byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                long var10004 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                int var23 = -1;
                var6[var3++] = var10004;
            } while (var2 < var5);
            c = var6;
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var16) {
            throw new RuntimeException(var16);
}
}
}