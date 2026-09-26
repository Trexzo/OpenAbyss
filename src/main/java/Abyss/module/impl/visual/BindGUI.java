/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BindGUIBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
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
import net.minecraft.client.renderer.GlStateManager;

public class BindGUI
extends Module
implements EventSubscriber {
    private static String[] b;
    private static Map d;
    private static long a;
    private static String[] c;
    private static Object[] n;
        
    private static Map k;
    public static NumberSetting scale;
    private static String[] p;
    private int o;
    
    public static NumberSetting offsetY;
    private static long[] g;
    private final List<Module> m;
    public static NumberSetting offsetX;
    

    private static void a() {
        BindGUI.n[0] = "[h/2`As";
        BindGUI.n[1] = Long.TYPE;
        BindGUI.p[1] = "java/lang/Long";
        BindGUI.n[2] = "\\\u00185p%6k\u000f1zh\u0012|\u0004kf";
        BindGUI.n[3] = "59T}\u007f`\u0002";
        BindGUI.n[4] = Void.TYPE;
        BindGUI.p[4] = "java/lang/Void";
        BindGUI.n[5] = "A^u\u0004T\"JQdK5,AZ`\u0011";
        BindGUI.n[6] = "\u0018C\u0012d78NP\u0013[\u0019H\u000f\u0005\u0015$85\u0010\u0004\u001b[b'\u001cSS<cx\u0011Bia`vIPQ1+(\u0015?Rf>x\u0010P\t*#yu";
}
    public BindGUI(long var1) {
        super(a ^ var1 ^ 0x3BF62B395C32L);
        this.declare("BindGUI", Category.Visual, "Show binds of modules and their enabled status", new Setting[0]);
        var1 = a ^ var1;
        this.m = new ArrayList<Module>();
}
    @Override
    public final void x(long var1, EventBus var3) {
        BindGUIBinder.J(var3, this);
}
    private static String b(int var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var5 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x2BB1;
        if (c[var5] == null) {
            Object[] var4;
            try {
                Long var3 = Thread.currentThread().getId();
                var4 = (Object[])d.get(var3);
                if (var4 == null) {
                    var4 = new Object[]{Cipher.getInstance("DES/CBC/PKCS5Padding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    d.put(var3, var4);
}
}
            catch (Exception var10) {
                throw new RuntimeException("Abyss/module/impl/visual/BindGUI", var10);
}
            byte[] var6 = new byte[8];
            var6[0] = (byte)(var1 >>> 56);
            for (int var7 = 1; var7 < 8; ++var7) {
                var6[var7] = (byte)(var1 << var7 * 8 >>> 56);
}
            DESKeySpec var11 = new DESKeySpec(var6);
            SecretKey var8 = ((SecretKeyFactory)var4[1]).generateSecret(var11);
            ((Cipher)var4[0]).init(2, (Key)var8, (IvParameterSpec)var4[2]);
            byte[] var9 = b[var5].getBytes("ISO-8859-1");
            BindGUI.c[var5] = BindGUI.b(((Cipher)var4[0]).doFinal(var9));
}
        return c[var5];
}
    @Override
    public void A(long var1) {
        this.m.clear();
        this.o = 0;
}
    public void onPostTick(long var1, PostTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.O(108535276639352L);
}
    private void O(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        CustomFont var10 = Font.s(0L);
        ArrayList<Module> var11 = new ArrayList<Module>();
        int var12 = 0;
        for (int var13 = 0; var13 < ModuleManager.S.size(); ++var13) {
            Module var14 = ModuleManager.S.get(var13);
            if (var14.h() == 0) continue;
            var11.add(var14);
            int var15 = (int)var10.R(var14.b() + " [" + KeyBindUtil.p(1864665317L, '\ufb9b', var14.h()) + "]", 52019766876817L);
            if (var15 <= var12) continue;
            var12 = var15;
}
        this.m.clear();
        this.m.addAll(var11);
        this.o = var12;
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
    public void onRender2D(long var1, Render2DEvent var3) {
        float var23 = scale.L();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)var23, (float)var23, (float)var23);
        CustomFont var24 = Font.F(0L);
        List<Module> var25 = this.m;
        float var26 = (float)((double)offsetX.L() + 1.0 * (double)var23);
        float var27 = (float)((double)offsetY.L() + 1.0 * (double)var23);
        float var28 = 1.0f;
        float var29 = var24.R("BindGUI      (Empty)", 52019766876817L);
        float var30 = !var25.isEmpty() ? Math.max((float)this.o, var29) : var29;
        float var31 = var24.o(60714858652844L) + (var28 *= var23) * 2.0f;
        float var32 = var30 + var28 * 2.0f;
        float var33 = var27;
        RenderUtil.c(125644905353792L, var26 / var23, var33 / var23, (var26 + (var32 *= var23)) / var23, (var33 + (var31 *= var23)) / var23, new Color(0, 0, 0, 170).getRGB());
        if (!var25.isEmpty()) {
            RenderUtil.k(var26 / var23, (var33 + var31) / var23, (var26 + var32) / var23, (var33 + var31) / var23, 121972467785353L, 1.0f, -1);
}
        int var19 = 0xFFFFFF;
        float var20 = (var33 + var28 * 2.0f) / var23;
        float var21 = (var26 + var28) / var23;
        String var22 = var25.isEmpty() ? "BindGUI      (Empty)" : "BindGUI";
        var24.T(37697014677608L, var22, var21, var20, var19);
        for (int var34 = 0; var34 < var25.size(); ++var34) {
            Module var35 = var25.get(var34);
            RenderUtil.c(125644905353792L, var26 / var23, (var33 += var31) / var23, (var26 + var32) / var23, (var33 + var31) / var23, new Color(0, 0, 0, 100).getRGB());
            var19 = var35.o() ? 4456292 : 11714229;
            var20 = (var33 + var28 * 2.0f) / var23;
            var21 = (var26 + var28) / var23;
            var22 = var35.b() + " [" + KeyBindUtil.p(1864665317L, '\ufb9b', var35.h()) + "]";
            var24.T(37697014677608L, var22, var21, var20, var19);
}
        GlStateManager.popMatrix();
}
    private static void zkm$clinit() {
        try {
            n = new Object[7]; p = new String[7]; a(); d = new HashMap(13); long var11 = a ^ 106843107854997L;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var14 = 1; var14 < 8; ++var14) { var10003[var14] = (byte)(var11 << var14 * 8 >>> 56); }
            Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var20 = new String[5];
            int var18 = 0;
            String var17 = "}5V\u00af\u00f7\u00af3,\u00d8A\u008c:\u00c5\u00a9O\u00de(\u00a5w\u00e7If\u0001\u00a2j\u0094\u00f2\u00e5*\u00f2\u00da\u00a92\u00e8a\u00f8y\u0001G\u00c2\u00e4\u00fc\u001eT\u00d93Xl\u00d4@j9\u00e83S\u0080\u0090\u0010\u00b4\u00df\u0016}\u008f\u00c0\u001eNl%\u00ea\u0089\u00dd\u00c2\u0090\u00ea";
            int var19 = "}5V\u00af\u00f7\u00af3,\u00d8A\u008c:\u00c5\u00a9O\u00de(\u00a5w\u00e7If\u0001\u00a2j\u0094\u00f2\u00e5*\u00f2\u00da\u00a92\u00e8a\u00f8y\u0001G\u00c2\u00e4\u00fc\u001eT\u00d93Xl\u00d4@j9\u00e83S\u0080\u0090\u0010\u00b4\u00df\u0016}\u008f\u00c0\u001eNl%\u00ea\u0089\u00dd\u00c2\u0090\u00ea".length();
            int var16 = 16;
            int var25 = -1;
            block9: while (true) {
                String var26 = var17.substring(++var25, var25 + var16);
                int var10001 = -1;
                while (true) {
                    byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
                    String var37 = BindGUI.b(var21).intern();
                    switch (var10001) {
                        case 0: {
                            var20[var18++] = var37;
                            if ((var25 += var16) >= var19) {
                                b = var20;
                                c = new String[5];
                                k = new HashMap(13);
                                var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var1 = 1; var1 < 8; ++var1) {
                                    var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var6 = new long[6];
                                int var3 = 0;
                                String var4 = "8\u00a7GVZU\u0093\u00dc\u00c7\u00f0\u0080v\u00d0\u0018\u00be8\u0006\u00f5\u009e\u00ab\u007f_B\u00d5\u0013J\u00dcU\u00fb_\u008a\u00fb";
                                int var5 = "8\u00a7GVZU\u0093\u00dc\u00c7\u00f0\u0080v\u00d0\u0018\u00be8\u0006\u00f5\u009e\u00ab\u007f_B\u00d5\u0013J\u00dcU\u00fb_\u008a\u00fb".length();
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
                                                g = var6;
                                                return;
}
                                            default: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) continue block12;
                                                var4 = "\u00f1\u007fHUG\u00b3L+\u00c2\u00f4\u00c6}\u00c2I\u0097|";
                                                var5 = "\u00f1\u007fHUG\u00b3L+\u00c2\u00f4\u00c6}\u00c2I\u0097|".length();
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
                            var17 = "/\u0003\u0082\u00c8\u00af\u0090\u00bb\u00a3\n\u00b62'h\u0012\u00df\u009a(\u0095j\u00cap\u001ca\u00d9\u00fe\u00d3T\u0005\u0098\u001e\u00d3T}L\u00f9H|\u00e7#\u00da=P\u00e8\u00c2A\u00cc\r\u0094\u00c4p2)\u00e5\u001d\u0097\u00bb\u009b";
                            var19 = "/\u0003\u0082\u00c8\u00af\u0090\u00bb\u00a3\n\u00b62'h\u0012\u00df\u009a(\u0095j\u00cap\u001ca\u00d9\u00fe\u00d3T\u0005\u0098\u001e\u00d3T}L\u00f9H|\u00e7#\u00da=P\u00e8\u00c2A\u00cc\r\u0094\u00c4p2)\u00e5\u001d\u0097\u00bb\u009b".length();
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
        a = 89407818855642L;
        zkm$clinit();
        scale = new NumberSetting("Scale", 0.8f, 0.25f, 3.0f, 0.01f);
        offsetY = new NumberSetting("Offset-Y", 100.0f, 0.0f, 1000.0f, 1.0f);
        offsetX = new NumberSetting("Offset-X", 3.0f, 0.0f, 1000.0f, 1.0f);
}
}