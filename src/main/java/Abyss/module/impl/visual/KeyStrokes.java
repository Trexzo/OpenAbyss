/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.settings.KeyBinding
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.KeyStrokesBinder;
import Abyss.event.events.IsPressedEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.awt.event.MouseEvent;
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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;

public class KeyStrokes
extends Module
implements EventSubscriber {
    private static Object[] t;
        public static NumberSetting offsetY;
    private static long[] o;
        private static String[] u;
    private static long b;
    public static NumberSetting offsetX;
    private final float L;
    private static Map s;
    
    private CustomFont n;
    private static Map m;
    private final float D;
    public static PercentageSetting backgroundOpacity;
    private static Map<KeyBinding, Long> h;
    private static Minecraft I;
    private static long[] g;
    private static Integer[] k;

    private void a(KeyBinding var1, float var2, float var3, long var4, int var6, float var7, float var8, float var9, float var10, String var11) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var12 = (0x2EB300000000L | (long)var6 << 32 >>> 32) ^ b;
        long var14 = var12 ^ 0x39BE7FB98F5BL;
        long var16 = var12 ^ 0x6B21FFF9BCF1L;
        GlStateManager.func_179094_E();
        RenderUtil.c(var16, var2, var3, var7, var8, new Color(0, 0, 0, (int)(2.55f * (float)backgroundOpacity.k())).getRGB());
        if (System.currentTimeMillis() - h.get(var1) < 30L) {
            RenderUtil.c(var16, var2, var3, var7, var8, new Color(255, 255, 255, this.f(h.get(var1))).getRGB());
}
        RenderUtil.J(var14, this.n, var11, var9, var10, 0xFFFFFF, 0);
        GlStateManager.func_179121_F();
}
    private float u(String var1, float var2, long var3, float var5) {
        return var2 + (var5 - var2) / 2.0f - this.n.R(var1, 52019766876817L) / 2.0f;
}
    private static void a() {
        KeyStrokes.t[0] = "q17,1GS";
        KeyStrokes.t[1] = "'n8}bu\u0010y<w/Q\u0007rfk";
        KeyStrokes.t[2] = Long.TYPE;
        KeyStrokes.u[2] = "java/lang/Long";
        KeyStrokes.t[3] = "\u000e74V\u00185*";
        KeyStrokes.t[4] = Void.TYPE;
        KeyStrokes.u[4] = "java/lang/Void";
        KeyStrokes.t[5] = "kf\u0007\u0019v\u0015`i\u0016V\u0017\u001bkb\u0012\f";
        KeyStrokes.t[6] = "x\nrI\u0001$ \t3*\u0007\u0018(P`@Q~}\u000foEj\"(\u0005cQ\u0005aa\u00111*Ptq\nc\u0012\u0004#|U\t\u0010\u0018qo\u001bd\u0011Pivk";
}
    private void D(KeyBinding var1, float var2, float var3, float var4, float var5, float var6, long var7, float var9) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.a(var1, var2, var3, 11955L, 1464655768, var4, var5, var6, var9, KeyBindUtil.p(1864665317L, '\ufb9b', var1.func_151463_i()));
}
    @Override
    public final void x(long var1, EventBus var3) {
        KeyStrokesBinder.e(var3, this);
}
    public static void T() {
        h.put(KeyStrokes.I.field_71474_y.field_74351_w, System.currentTimeMillis());
        h.put(KeyStrokes.I.field_71474_y.field_74368_y, System.currentTimeMillis());
        h.put(KeyStrokes.I.field_71474_y.field_74370_x, System.currentTimeMillis());
        h.put(KeyStrokes.I.field_71474_y.field_74366_z, System.currentTimeMillis());
        h.put(KeyStrokes.I.field_71474_y.field_74314_A, System.currentTimeMillis());
        h.put(KeyStrokes.I.field_71474_y.field_74312_F, System.currentTimeMillis());
        h.put(KeyStrokes.I.field_71474_y.field_74313_G, System.currentTimeMillis());
}
    private void n(KeyBinding var1, float var2, long var3, float var5, float var6, float var7) {
        GlStateManager.func_179094_E();
        RenderUtil.c(125644905353792L, var2, var5, var6, var7, new Color(0, 0, 0, (int)(2.55f * (float)backgroundOpacity.k())).getRGB());
        if (System.currentTimeMillis() - h.get(var1) < 40L) {
            RenderUtil.c(125644905353792L, var2, var5, var6, var7, new Color(255, 255, 255, this.f(h.get(var1))).getRGB());
}
        float var15 = var2 + 15.0f;
        float var16 = var5 + 4.0f;
        float var17 = var6 - 15.0f;
        float var18 = var5 + 5.0f;
        RenderUtil.m(var15, var16, 91446790430251L, var17, var18, 5.0f, Color.BLACK.getRGB());
        RenderUtil.c(125644905353792L, var15, var16, var17, var18, Color.WHITE.getRGB());
        GlStateManager.func_179121_F();
}
    public void onMouse(MouseEvent var1, long var2) {
        for (Map.Entry<KeyBinding, Long> var5 : h.entrySet()) {
            if (var5.getKey().func_151463_i() + 100 != var1.getButton()) continue;
            h.put(var5.getKey(), System.currentTimeMillis());
}
}
    private int f(long var4) {
        if (System.currentTimeMillis() - var4 > 40L) {
            return 0;
}
        int var8 = 100 - (int)(System.currentTimeMillis() - var4);
        return (int)(2.0f * (float)var8);
}
    public KeyStrokes(byte var1, int var2, int var3) {
        super(((long)var1 << 56 | (long)var2 << 32 >>> 8 | (long)var3 << 40 >>> 40) ^ b ^ 0xFA45B3F5E90L);
        this.declare("KeyStrokes", Category.Visual, "Show your keys interactions", new Setting[0]);
        this.D = 4.0f;
        this.L = 2.0f;
        this.n = null;
}
    private static int d(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x3A4B;
        if (k[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = g[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])m.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    m.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/module/impl/visual/KeyStrokes", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            KeyStrokes.k[var3] = var15;
}
        return k[var3];
}
    public void onIsPressed(IsPressedEvent var1) {
        if (var1.a) {
            for (Map.Entry<KeyBinding, Long> var3 : h.entrySet()) {
                if (var3.getKey().func_151463_i() != var1.o) continue;
                h.put(var3.getKey(), System.currentTimeMillis());
}
}
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
    public void onSetKeyBindState(SetKeyBindStateEvent var1) {
        for (Map.Entry<KeyBinding, Long> var3 : h.entrySet()) {
            if (var3.getKey().func_151463_i() != var1.R) continue;
            h.put(var3.getKey(), System.currentTimeMillis());
}
}
    private float T(long var1) {
        return 8.0f + this.n.o(60714858652844L);
}
    public void onRender2D(long var1, Render2DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.n = Font.F(0L);
        float var17 = offsetX.L();
        float var18 = offsetY.L();
        float var19 = var17 - this.T(120224034947841L) - 2.0f;
        float var20 = var17 + this.T(120224034947841L) * 2.0f + 2.0f;
        float var21 = var18;
        float var22 = var18 + this.T(120224034947841L) + 2.0f;
        float var23 = var18 + this.T(120224034947841L) * 2.0f + 4.0f;
        float var24 = var23 + 8.0f + 2.0f;
        for (Map.Entry<KeyBinding, Long> var26 : h.entrySet()) {
            if (!var26.getKey().func_151470_d()) continue;
            h.put(var26.getKey(), System.currentTimeMillis());
}
        this.E(KeyStrokes.I.field_71474_y.field_74351_w, 13170027296889L, var17, var21);
        this.E(KeyStrokes.I.field_71474_y.field_74368_y, 13170027296889L, var17, var22);
        this.E(KeyStrokes.I.field_71474_y.field_74370_x, 13170027296889L, var19, var22);
        this.E(KeyStrokes.I.field_71474_y.field_74366_z, 13170027296889L, var19 + this.T(120224034947841L) * 2.0f + 4.0f, var22);
        float var30 = var20 - var19;
        float var31 = Math.abs(var30);
        this.n(KeyStrokes.I.field_71474_y.field_74314_A, var19, 92495927115386L, var23, var20, var23 + 8.0f);
        float var27 = var19 + var31 / 2.0f - 2.0f;
        this.a(KeyStrokes.I.field_71474_y.field_74312_F, var19, var24, 11955L, 1464655768, var27, var24 + this.T(120224034947841L), this.u("LMB", var19, 60932596744412L, var27), var24 + 4.0f, "LMB");
        float var28 = var19 + var31 / 2.0f + 2.0f;
        this.a(KeyStrokes.I.field_71474_y.field_74313_G, var28, var24, 11955L, 1464655768, var20, var24 + this.T(120224034947841L), this.u("RMB", var28, 60932596744412L, var20), var24 + 4.0f, "RMB");
}
    private void E(KeyBinding var1, long var2, float var4, float var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        float var15 = this.n.o(60714858652844L);
        this.D(var1, var4, var5, var4 + 8.0f + var15, var5 + 8.0f + var15, this.u(KeyBindUtil.p(1864665317L, '\ufb9b', var1.func_151463_i()), var4, 60932596744412L, var4 + 8.0f + var15), 46633935861873L, var5 + 4.0f);
}
                Cipher var22 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var22.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var29 = new String[4];
            int var27 = 0;
            String var26 = "XG\u0016\u001f\u0088\u000e\u00f7\u00ac\u00f0\u00b0,I!\u00f8\u0010T\u0010\u00cc\u0010G2\u0016\u00e11\u00f6m\u00b8\u00fd\u000e\u009d##\u0000";
            int var28 = "XG\u0016\u001f\u0088\u000e\u00f7\u00ac\u00f0\u00b0,I!\u00f8\u0010T\u0010\u00cc\u0010G2\u0016\u00e11\u00f6m\u00b8\u00fd\u000e\u009d##\u0000".length();
            int var25 = 16;
            int var39 = -1;
            block9: while (true) {
                String var40 = var26.substring(++var39, var39 + var25);
                int var10001 = -1;
                while (true) {
                    byte[] var30 = var22.doFinal(var40.getBytes("ISO-8859-1"));
                    String var54 = KeyStrokes.b(var30).intern();
                    switch (var10001) {
                        case 0: {
                            var29[var27++] = var54;
                            if ((var39 += var25) >= var28) {
                                c = var29;
                                d = new String[4];
                                m = new HashMap(13);
                                var10003 = new byte[]{(byte)(var31 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(var31 << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var17 = new long[5];
                                int var14 = 0;
                                String var15 = "f\u00e3\rN\u00d0\b\u0004Y\u008f\u0014\u00e8g\u00dep}\u0003AY\u00b5w\b`\u0012\u00ee";
                                int var16 = "f\u00e3\rN\u00d0\b\u0004Y\u008f\u0014\u00e8g\u00dep}\u0003AY\u00b5w\b`\u0012\u00ee".length();
                                int var13 = 0;
                                block12: while (true) {
                                    var10001 = var13;
                                    byte[] var18 = var15.substring(var10001, var13 += 8).getBytes("ISO-8859-1");
                                    long[] var43 = var17;
                                    var10001 = var14++;
                                    long var58 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                    int var62 = -1;
                                    while (true) {
                                        long var19 = var58;
                                        byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                                        long var66 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                                        switch (var62) {
                                            case 0: {
                                                var43[var10001] = var66;
                                                if (var13 < var16) break;
                                                g = var17;
                                                k = new Integer[5];
                                                s = new HashMap(13);
                                                var10003 = new byte[]{(byte)(var31 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(var31 << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                long[] var6 = new long[3];
                                                int var3 = 0;
                                                String var4 = "\u009c\u00e5\u00f5\u008d\u00f4\u0088\u00edZ\u00da\u00ae\u00b4n\u00b9\u00cb\u00aaU\u00b6\u001bEw\"\u00bary";
                                                int var5 = "\u009c\u00e5\u00f5\u008d\u00f4\u0088\u00edZ\u00da\u00ae\u00b4n\u00b9\u00cb\u00aaU\u00b6\u001bEw\"\u00bary".length();
                                                int var2 = 0;
                                                do {
                                                    int var51 = var2;
                                                    byte[] var7 = var4.substring(var51, var2 += 8).getBytes("ISO-8859-1");
                                                    var51 = var3++;
                                                    long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                                    var6[var51] = var66 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                                } while (var2 < var5);
                                                o = var6;
                                                return;
}
                                            default: {
                                                var43[var10001] = var66;
                                                if (var13 < var16) continue block12;
                                                var15 = "\u00fd$c\u0001\u00f3\u00b7U\u00e0\u0018\u0095s\u00b1\u00c8\u00d6\u0095L";
                                                var16 = "\u00fd$c\u0001\u00f3\u00b7U\u00e0\u0018\u0095s\u00b1\u00c8\u00d6\u0095L".length();
                                                var13 = 0;
}
}
                                        int var50 = var13;
                                        var18 = var15.substring(var50, var13 += 8).getBytes("ISO-8859-1");
                                        var43 = var17;
                                        var10001 = var14++;
                                        var58 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                        var62 = 0;
}
                                    break;
}
}
                            var25 = var26.charAt(var39);
                            break;
}
                        default: {
                            var29[var27++] = var54;
                            if ((var39 += var25) < var28) {
                                var25 = var26.charAt(var39);
                                continue block9;
}
                            var26 = "\u00fd'\u008d\u008f\u0093sq8\u00f3\u00d8\u00de\u00ef\u00ce-\u00c7=\u0010E\u00a4X\u00aa\f+\u00d2\\Q\u0003\u009b\u00c7\u00c3\u008a\b_";
                            var28 = "\u00fd'\u008d\u008f\u0093sq8\u00f3\u00d8\u00de\u00ef\u00ce-\u00c7=\u0010E\u00a4X\u00aa\f+\u00d2\\Q\u0003\u009b\u00c7\u00c3\u008a\b_".length();
                            var25 = 16;
                            var39 = -1;
}
}
                    var40 = var26.substring(++var39, var39 + var25);
                    var10001 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var36) {
            throw new RuntimeException(var36);
}
}
    static {
        b = 61397954654505L;
        h = new HashMap<KeyBinding, Long>();
        I = MinecraftRef.c((byte)0, 0L);
        offsetX = new NumberSetting("Offset-X", 120.0f, 0.0f, 1000.0f, 1.0f);
        offsetY = new NumberSetting("Offset-Y", 30.0f, 0.0f, 1000.0f, 1.0f);
        backgroundOpacity = new PercentageSetting("Background-opacity", 50);
}
}