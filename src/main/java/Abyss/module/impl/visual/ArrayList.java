/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ArrayListBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Theme;
import Abyss.module.impl.visual.ArrayListRect;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.ui.ArrayListEntry;
import Abyss.ui.abyss.AbyssArrayListRenderer;
import Abyss.ui.abyss.AbyssUserInfoRenderer;
import Abyss.util.MathUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import Abyss.util.render.ShaderRenderer;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ArrayList
extends Module
implements EventSubscriber {
    private static long a;
    private static Map h;
    private static String[] b;
    public static BooleanSetting abyssCustomFont;
    public static BooleanSetting abyssBackground;
    public static BooleanSetting abyssLine;
    public static BooleanSetting abyssOutline;
    public static ModeSetting abyssSort;
    public static ModeSetting abyssColorMode;
    public static NumberSetting abyssFadeSpeed;
    public static TextSetting customText;
    public static NumberSetting barWidth;
    private static final float o = 0.75f;
    public static NumberSetting offsetY;
    public static NumberSetting rectangleYSpace;
    public static ModeSetting gradientMode;
    public static BooleanSetting textShadow;
    public static NumberSetting rectangleYEdge;
    private static Map x;
    
    public static ModeSetting barColor;
    private final java.util.ArrayList<Float> d;
    public static PercentageSetting backgroundOpacity;
    public static ColorSetting customColor;
    private static Object[] C;
    public static BooleanSetting bar;
    private static String[] E;
        public static BooleanSetting suffixNameLowercase;
    public static ModeSetting mode;
    public static BooleanSetting showSuffix;
    private static String[] e;
        public static BooleanSetting moduleNameLowercase;
    public static BooleanSetting capitalize;
    public static NumberSetting scale;
    public static BooleanSetting icons;
    public static ColorSetting textCustomColor;
    public static BooleanSetting splitSuffixAndName;
    public static ModeSetting textColor;
    public static BooleanSetting onlyShowSuffixModules;
    public static NumberSetting offsetX;
    private final java.util.ArrayList<ArrayListEntry> U;
    private static long[] m;

    private static void N(CustomFont var0, String var1, float var2, float var3, int var4, boolean var5, float var6, long var7, double var9) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        boolean var17;
        String var16 = textColor.Y();
        boolean bl = var17 = gradientMode != null && gradientMode.R("LEFT_RIGHT") && (var16.equals("THEME") || var16.equals("THEME_CUSTOM"));
        if (var17) {
            ArrayList.m(var0, var1, var2, 43968468732776L, var3, var4, var5, var6, var9, var16.equals("THEME_CUSTOM"));
        } else {
            ArrayList.g(28962, var0, var1, 16056385, var2, var3, var4, var5, (byte)51, var6);
}
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    private void D(float var1, float var2, float var3, float var4, int var5, float var6, boolean var7, boolean var8, boolean var9, boolean var10, long var11) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        boolean var15 = !var10;
        boolean var16 = !var9;
        boolean var17 = !var8;
        boolean var18 = !var7;
        float var19 = var6;
        int var20 = var5;
        float var21 = var4;
        float var22 = var3;
        float var23 = var2;
        float var24 = var1;
        this.K(119483497834473L, var24, var23, var22, var21, var20, var19, var18, var17, var16, var15);
}
    private ArrayListRect h(int var1, float var2, boolean var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, boolean var11, float var12) {
        for (int var13 = var1; var13 < this.U.size(); ++var13) {
            ArrayListEntry var14 = this.U.get(var13);
            float var15 = MathUtil.k(ArrayListEntry.l(var14), ArrayListEntry.f(var14), var12);
            var15 = MathUtil.q(var15, 0.0f, 1.0f);
            boolean var16 = ArrayListEntry.w(var14);
            float var17 = ArrayList.B(var15);
            if (!var16 && var17 <= 0.0f || !ArrayListEntry.i(var14) && var11) continue;
            float var18 = ArrayListEntry.j(var14) + var6 + var7;
            float var19 = ArrayListEntry.T(var14) == 0.0f ? 0.0f : ArrayListEntry.T(var14) + var8 * 2.0f;
            float var20 = var18 + var19 + ArrayListEntry.c(var14);
            float var21 = ArrayList.V(1.0f - var15);
            float var22 = var16 ? 1.0f - var17 : var21;
            float var23 = var22 * (var20 + var5 + 10.0f * var9);
            float var24 = var16 ? 0.0f : var21 * 3.6f * var9;
            return this.r(var3, var4, var5, var20, var23, var2, var2 + var10, var24, var9);
}
        return null;
}
    private void m(float var1, float var2, float var3, long var4, float var6, int var7, float var8, ArrayListRect var9, ArrayListRect var10, float var11, boolean var12) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        boolean var29;
        float var19 = 0.05f;
        boolean var20 = var9 != null && Math.abs(ArrayListRect.J(var9) - var2) <= var19;
        float var21 = 0.0f;
        float var22 = 0.0f;
        if (var20) {
            var21 = Math.max(var1, ArrayListRect.H(var9));
            var22 = Math.min(var3, ArrayListRect.N(var9));
            var20 = var22 > var21 + var19;
}
        boolean var23 = var10 != null && Math.abs(ArrayListRect.e(var10) - var6) <= var19;
        float var24 = 0.0f;
        float var25 = 0.0f;
        if (var23) {
            var24 = Math.max(var1, ArrayListRect.H(var10));
            var25 = Math.min(var3, ArrayListRect.N(var10));
            var23 = var25 > var24 + var19;
}
        boolean var26 = var20 && var21 <= var1 + var19;
        boolean var27 = var20 && var22 >= var3 - var19;
        boolean var28 = var23 && var24 <= var1 + var19;
        boolean bl = var29 = var23 && var25 >= var3 - var19;
        if (!var20) {
            if (var12) {
                var27 = true;
            } else {
                var26 = true;
}
}
        if (!var20 && !var23) {
            this.D(var1, var2, var3, var6, var7, var8, var26, var27, var29, var28, 105458755338260L);
        } else {
            java.util.ArrayList<Float> var30 = this.d;
            var30.clear();
            this.z(var30, var1, var1, var3);
            this.z(var30, var3, var1, var3);
            if (var20) {
                this.z(var30, var21, var1, var3);
                this.z(var30, var22, var1, var3);
}
            if (var23) {
                this.z(var30, var24, var1, var3);
                this.z(var30, var25, var1, var3);
}
            if (var30.size() >= 2) {
                boolean var35;
                var30.sort(null);
                float var31 = Math.max(0.0f, var11);
                boolean var32 = var20 && var21 <= var1 + 0.01f;
                boolean var33 = var20 && var22 >= var3 - 0.01f;
                boolean var34 = var23 && var24 <= var1 + 0.01f;
                boolean bl2 = var35 = var23 && var25 >= var3 - 0.01f;
                if (!var20) {
                    if (var12) {
                        var33 = true;
                    } else {
                        var32 = true;
}
}
                float var36 = var30.get(0).floatValue();
                for (int var37 = 1; var37 < var30.size(); ++var37) {
                    float var38 = var30.get(var37).floatValue();
                    if (var38 <= var36 + 0.01f) continue;
                    float var39 = (var36 + var38) * 0.5f;
                    boolean var40 = var20 && var39 >= var21 - 0.01f && var39 <= var22 + 0.01f;
                    boolean var41 = var23 && var39 >= var24 - 0.01f && var39 <= var25 + 0.01f;
                    float var42 = var2 + (var40 ? var31 : 0.0f);
                    float var43 = var6 - (var41 ? var31 : 0.0f);
                    var42 = (float)Math.round(var42 * 2.0f) / 2.0f;
                    if ((var43 = (float)Math.round(var43 * 2.0f) / 2.0f) < var42) {
                        float var44;
                        var42 = var44 = (var42 + var43) * 0.5f;
                        var43 = var44;
}
                    boolean var54 = var36 <= var1 + 0.01f;
                    boolean var45 = var38 >= var3 - 0.01f;
                    boolean var46 = var54 && !var32;
                    boolean var47 = var54 && !var34;
                    boolean var48 = var45 && !var33;
                    boolean var49 = var45 && !var35;
                    this.K(119483497834473L, var36, var42, var38, var43, var7, var8, var46, var48, var49, var47);
                    var36 = var38;
}
                if (var23 && var25 > var24) {
                    float var51 = (float)Math.round(var6 * 2.0f) / 2.0f;
                    RenderUtil.c(125644905353792L, var24, var51 - var31, var25, var51 + var31, var7);
}
}
}
}
    private static float V(long var0, float var2, boolean var3, boolean var4) {
        return (2.0f + (var3 && var4 ? 1.0f : 0.0f)) * var2 + ArrayList.I(var2);
}
    private static float V(float var0) {
        var0 = MathUtil.q(var0, 0.0f, 1.0f);
        return var0 * var0 * var0;
}
    private static void m(CustomFont var0, String var1, float var2, long var3, float var5, int var6, boolean var7, float var8, double var9, boolean var11) {
        float var16 = MathUtil.q(var8, 0.0f, 1.0f);
        float var17 = Math.max(1.0f, var0.R(var1, 52019766876817L));
        var0.A(var1, var2, var5, var6, 103391699357661L, var7, (var5x, var6x, var7x, var8x) -> {
            if (var8x != null) {
                return ArrayList.u(var8x, var16);
}
            double var17x = (var6x + var7x * 0.5f) / var17;
            int var19 = var11 ? Theme.Z(var9, 23812163747166L, var17x) : Theme.L(var9, 72497430032154L, var17x);
            return ArrayList.u(var19, var16);
        });
}
    private static float k(float var0, boolean var3, boolean var4) {
        return (2.0f + (!var3 && var4 ? 1.0f : 0.0f)) * var0 + ArrayList.I(var0);
}
    public void onPostTick(PostTickEvent var1, int var2, long var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        AbyssArrayListRenderer.tick();
}
    private static void g(int var0, CustomFont var1, String var2, int var3, float var4, float var5, int var6, boolean var7, byte var8, float var9) {
        long var10 = ((long)var0 << 32 | (long)var3 << 40 >>> 32 | (long)var8 << 56 >>> 56) ^ a;
        long var12 = var10 ^ 0x3C6AD371467BL;
        var9 = MathUtil.q(var9, 0.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)var9);
        var1.v(var2, var4, var5, var6, var12, var7);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
}
    private void N(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var10001 = 59909721217249L;
        int var5 = (int)(var10001 << 48 >>> 48);
        var10001 = 116568669000716L;
        CustomFont var17 = Font.Q(73094284682035L);
        float var18 = scale.L();
        String var19 = mode.Y();
        boolean var20 = var19.contains("RIGHT");
        boolean var21 = var19.contains("CURVE");
        boolean var22 = splitSuffixAndName.c();
        boolean var23 = showSuffix.c();
        boolean var24 = moduleNameLowercase.c();
        boolean var25 = suffixNameLowercase.c();
        float var26 = 2.0f * var18;
        float var27 = ArrayList.V(0L, var18, var20, var21);
        float var28 = ArrayList.k(var18, var20, var21);
        for (int var29 = 0; var29 < this.U.size(); ++var29) {
            ArrayListEntry var30 = this.U.get(var29);
            ArrayListEntry.e(var30, ArrayListEntry.l(var30));
            ArrayListEntry.g(var30, false);
}
        HashMap<Module, ArrayListEntry> var42 = new HashMap<Module, ArrayListEntry>();
        for (int var43 = 0; var43 < this.U.size(); ++var43) {
            ArrayListEntry var31 = this.U.get(var43);
            var42.put(ArrayListEntry.s(var31), var31);
}
        List<Module> var44 = ModuleManager.S;
        for (int var45 = 0; var45 < var44.size(); ++var45) {
            String var34;
            Module var32 = var44.get(var45);
            if (!var32.D() || !var32.o()) continue;
            boolean var33 = var23 && var32.r() && var32.g(20826436655957L) != null;
            String string = var34 = var24 ? var32.t(13948, 53670, (short)var5).toLowerCase() : var32.t(13948, 53670, (short)var5);
            String var35 = var33 ? (var25 ? var32.g(20826436655957L).toLowerCase() : var32.g(20826436655957L)) : "";
            float var36 = var17.R(var34, 52019766876817L) * var18;
            float var37 = var33 ? var17.R(var35, 52019766876817L) * var18 : 0.0f;
            float var38 = var33 && var22 && var21 ? var18 : 0.0f;
            float var39 = var36 + var27 + var28 + var37 + (var37 == 0.0f ? 0.0f : var26 * 2.0f) + var38;
            ArrayListEntry var40 = (ArrayListEntry)var42.get(var32);
            if (var40 == null) {
                var40 = new ArrayListEntry(var32, null);
                var42.put(var32, var40);
                this.U.add(var40);
}
            ArrayListEntry.k(var40, var34);
            ArrayListEntry.t(var40, var35);
            ArrayListEntry.p(var40, var33);
            ArrayListEntry.p(var40, var36);
            ArrayListEntry.o(var40, var37);
            ArrayListEntry.F(var40, var38);
            ArrayListEntry.z(var40, var39);
            ArrayListEntry.f(var40, true);
            ArrayListEntry.g(var40, true);
}
        for (int var46 = 0; var46 < this.U.size(); ++var46) {
            ArrayListEntry var47 = this.U.get(var46);
            if (!ArrayListEntry.J(var47)) {
                ArrayListEntry.f(var47, false);
}
            float var48 = ArrayListEntry.w(var47) ? 1.0f : 0.0f;
            float var49 = 0.38f;
            ArrayListEntry var50 = var47;
            ArrayListEntry.X(var50, ArrayListEntry.l(var50) + (var48 - ArrayListEntry.l(var47)) * var49);
            if (!(Math.abs(var48 - ArrayListEntry.l(var47)) <= 0.015f)) continue;
            ArrayListEntry.X(var47, var48);
}
        this.U.removeIf(var0 -> !ArrayListEntry.w(var0) && ArrayListEntry.l(var0) <= 0.0f);
        this.U.sort((var0, var1x) -> Float.compare(ArrayListEntry.O(var1x), ArrayListEntry.O(var0)));
}
    private static void a() {
        ArrayList.C[0] = "T-Qc\u0017`p";
        ArrayList.C[1] = Long.TYPE;
        ArrayList.E[1] = "java/lang/Long";
        ArrayList.C[2] = "4*,\u001c\u001bo\u0003=(\u0016VK\u00146r\n";
        ArrayList.C[3] = "?\u001e\u0001yP/M";
        ArrayList.C[4] = Void.TYPE;
        ArrayList.E[4] = "java/lang/Void";
        ArrayList.C[5] = "o\u0012FL\u0015>d\u001dW\u0003t0o\u0016SY";
        ArrayList.C[6] = "A\u000b\u0002\u0018\u0001[\b\\\ra\u0015k\n]\r\u0006\u001c\u001b\u001a\bYaH\u000b\u0012\u0019U\u0005\u0001[\u001f\u00143[\u0010\u001a\u0010\u001d\u0002\n\f\u0017\u0013d\b\bJ\f\tXU\u000bL\u0016q";
}
    private static int u(int var0, float var3) {
        var3 = MathUtil.q(var3, 0.0f, 1.0f);
        int var4 = Math.round((float)(var0 >> 24 & 0xFF) * var3);
        return var0 & 0xFFFFFF | var4 << 24;
}
    private void s(float var1, float var2, float var3, float var4, int var5, long var6, String var8) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.f(var1, var2, 49215341321510L, var3, var4, var5, var8, null, null);
}
    private void K(long var1, float var3, float var4, float var5, float var6, int var7, float var8, boolean var9, boolean var10, boolean var11, boolean var12) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var8 <= 0.0f) {
            RenderUtil.c(125644905353792L, var3, var4, var5, var6, var7);
        } else {
            float var17 = Math.max(0.0f, var5 - var3);
            float var18 = Math.max(0.0f, var6 - var4);
            if (!(var17 <= 0.0f) && !(var18 <= 0.0f)) {
                float var19 = Math.min(var17, var18) * 0.5f * var8;
                if (var19 <= 0.0f) {
                    RenderUtil.c(125644905353792L, var3, var4, var5, var6, var7);
                } else if (!(var9 || var10 || var11 || var12)) {
                    RenderUtil.c(125644905353792L, var3, var4, var5, var6, var7);
                } else {
                    try {
                        ShaderRenderer.F(var3, var4, var17, 2001336113403L, var18, var19, var7, var9, var10, var11, var12);
}
                    catch (Throwable var20) {
                        RenderUtil.c(125644905353792L, var3, var4, var5, var6, var7);
}
}
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        ArrayListBinder.l(var3, this);
}
    private void f(float var1, float var2, long var3, float var5, float var6, int var7, String var8, ArrayListRect var9, ArrayListRect var10) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var8.contains("CURVE")) {
            this.m(var1, var2, var5, 83626183259914L, var6, var7, 0.75f, var9, var10, 0.5f, var8.contains("RIGHT"));
        } else {
            RenderUtil.c(125644905353792L, var1, var2, var5, var6, var7);
}
}
    public void onRender2D(Render2DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        float var61;
        float var56;
        AbyssArrayListRenderer.render(var1.C, var1.r);
        AbyssUserInfoRenderer.render(var1.C);
        if (System.currentTimeMillis() >= 0L) {
            return;
}
        long var10001 = 124394953261363L;
        int var22 = (int)(var10001 << 56 >>> 56);
        var10001 = 116568669000716L;
        CustomFont var38 = Font.Q(73094284682035L);
        GlStateManager.pushMatrix();
        ScaledResolution var39 = var1.C;
        float var40 = scale.L();
        GlStateManager.scale((float)var40, (float)var40, (float)var40);
        String var41 = mode.Y();
        boolean var42 = var41.contains("RIGHT");
        boolean var43 = var41.contains("CURVE");
        boolean var44 = icons.c();
        boolean var45 = textShadow.c();
        boolean var46 = onlyShowSuffixModules.c();
        boolean var47 = bar.c();
        int var48 = 255 * backgroundOpacity.k() / 100 << 24;
        float var49 = 2.0f * var40;
        float var50 = ArrayList.V(0L, var40, var42, var43);
        float var51 = ArrayList.k(var40, var42, var43);
        float var52 = rectangleYEdge.L() * (var43 ? 2.0f : 1.0f) * var40;
        float var53 = rectangleYSpace.L() * var40;
        float var54 = var38.o(60714858652844L) * var40;
        float var55 = var54 + var52 * 2.0f;
        float f = var56 = var44 ? (var55 - 4.0f * var40) / var40 : 0.0f;
        float var57 = var44 ? (var43 ? 2.0f : 1.0f) * var40 : 0.0f;
        float var58 = var44 ? var55 + var57 : 0.0f;
        float var59 = (1.0f + offsetY.L()) * var40;
        float var60 = barWidth.L() * var40;
        float f2 = var61 = var42 ? (float)var39.getScaledWidth() - offsetX.L() * var40 : offsetX.L() * var40;
        float var62 = var42 ? var61 + (var43 ? 2.0f * var40 : 0.0f) : offsetX.L() * var40 - var60;
        String var63 = customText.X();
        if (!var63.isEmpty()) {
            float var64 = var61 - (var42 ? var38.R(var63, 52019766876817L) * var40 : 0.0f) + (var47 ? (var42 ? 2.0f * var40 : -2.0f * var40) : 0.0f);
            var38.v(var63, var64 / var40, (var59 - (var38.o(60714858652844L) + 1.0f) * var40) / var40, -1, 88827598794260L, true);
}
        double var110 = 0.0;
        double var66 = 0.0;
        ArrayListRect var68 = null;
        for (int var69 = 0; var69 < this.U.size(); ++var69) {
            ArrayListRect var100;
            boolean var75;
            ArrayListEntry var70 = this.U.get(var69);
            Module var71 = ArrayListEntry.s(var70);
            float var72 = MathUtil.k(ArrayListEntry.l(var70), ArrayListEntry.f(var70), var1.r);
            var72 = MathUtil.q(var72, 0.0f, 1.0f);
            boolean var73 = ArrayListEntry.w(var70);
            float var74 = ArrayList.B(var72);
            if (!var73 && var74 <= 0.0f || !(var75 = ArrayListEntry.i(var70)) && var46) continue;
            int var76 = barColor.Y().equals("THEME") ? Theme.S(var110, 35338930340239L) : (barColor.Y().equals("THEME_CUSTOM") ? Theme.X(65301174328177L, var110) : customColor.k(96531491288662L));
            var110 += (double)Theme.offset.L();
            double var77 = var66;
            int var79 = textColor.Y().equals("THEME") ? Theme.S(var77, 35338930340239L) : (textColor.Y().equals("THEME_CUSTOM") ? Theme.X(65301174328177L, var77) : textCustomColor.k(96531491288662L));
            var66 += (double)Theme.offset.L();
            String var80 = ArrayListEntry.V(var70);
            String var81 = ArrayListEntry.m(var70);
            float var82 = ArrayListEntry.j(var70) + var50 + var51;
            float var83 = ArrayListEntry.T(var70) == 0.0f ? 0.0f : ArrayListEntry.T(var70) + var49 * 2.0f;
            float var84 = var82 + var83 + ArrayListEntry.c(var70);
            float var85 = 1.0f - var74;
            float var86 = ArrayList.V(1.0f - var72);
            float var87 = var73 ? var85 : var86;
            float var88 = var87 * (var84 + var58 + 10.0f * var40);
            float var89 = var73 ? 0.0f : var86 * 3.6f * var40;
            float var90 = var73 ? 0.96f + 0.04f * var74 : 1.0f - 0.04f * var86;
            int var91 = ArrayList.u(var48, var74);
            int var92 = ArrayList.u(var76, var74);
            int var93 = ArrayList.u(var79, var74);
            int var94 = ArrayList.u(-5592406, var74);
            float var95 = var59;
            float var96 = var59 + var55;
            float var97 = var73 ? 0.18f + 0.82f * var74 : ArrayList.B(var72);
            float var98 = var59 + (var55 + var53) * var97;
            ArrayListRect var99 = this.r(var42, var61, var58, var84, var88, var95, var96, var89, var40);
            ArrayListRect arrayListRect = var100 = var43 ? this.h(var69 + 1, var98, var42, var61, var58, var50, var51, var49, var40, var55, var46, var1.r) : null;
            if (var47) {
                float var101 = var42 ? var62 + var88 : var62 - var88;
                float var102 = var101 + var60 * var40 * 0.5f;
                float var103 = (var95 + var96 + var53) * 0.5f + var89 * 0.5f;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(var102 / var40), (float)(var103 / var40), (float)0.0f);
                GlStateManager.scale((float)var90, (float)var90, (float)1.0f);
                GlStateManager.translate((float)(-var102 / var40), (float)(-var103 / var40), (float)0.0f);
                RenderUtil.c(125644905353792L, var101 / var40, (var95 + var89 * 0.5f) / var40, (var101 + var60 * var40) / var40, (var96 + var53 + var89 * 0.5f) / var40, var92);
                GlStateManager.popMatrix();
}
            float var112 = (var95 + var55 - var52 - var54 + var89) / var40;
            float var113 = (var95 + 2.0f * var40 + var89) / var40;
            if (var42) {
                float var114 = var61 - var58 - var84 + var88;
                float var104 = var61 - var58 + var88;
                float var105 = (var114 + var104) * 0.5f;
                float var106 = (var95 + var96) * 0.5f + var89 * 0.5f;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(var105 / var40), (float)(var106 / var40), (float)0.0f);
                GlStateManager.scale((float)var90, (float)var90, (float)1.0f);
                GlStateManager.translate((float)(-var105 / var40), (float)(-var106 / var40), (float)0.0f);
                this.f(ArrayListRect.H(var99), ArrayListRect.e(var99), 49215341321510L, ArrayListRect.N(var99), ArrayListRect.J(var99), var91, var41, var68, var100);
                if (var44) {
                    float var107 = var61 - var58 + var57 + var88;
                    float var108 = var61 + var88;
                    this.s(var107 / var40, (var95 + var89) / var40, var108 / var40, (var96 + var89) / var40, var91, 50263086633642L, var41);
                    RenderUtil.u((var61 - var58 + var57 + 2.0f * var40 + var88) / var40, var113, var56, Category.n(var71.f()));
}
                ArrayList.N(var38, var80, (var61 - var58 - var84 + var50 + var88) / var40, var112, var93, var45, var74, 34995704477528L, var77);
                if (var75) {
                    ArrayList.g(28962, var38, var81, 16056385, (var61 - var58 - var84 + var82 + ArrayListEntry.c(var70) + var49 + var88) / var40, var112, var94, var45, (byte)var22, var74);
}
                GlStateManager.popMatrix();
            } else {
                float var115 = var61 + var58 - var88;
                float var116 = var61 + var58 + var84 - var88;
                float var117 = (var115 + var116) * 0.5f;
                float var118 = (var95 + var96) * 0.5f + var89 * 0.5f;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(var117 / var40), (float)(var118 / var40), (float)0.0f);
                GlStateManager.scale((float)var90, (float)var90, (float)1.0f);
                GlStateManager.translate((float)(-var117 / var40), (float)(-var118 / var40), (float)0.0f);
                this.f(ArrayListRect.H(var99), ArrayListRect.e(var99), 49215341321510L, ArrayListRect.N(var99), ArrayListRect.J(var99), var91, var41, var68, var100);
                if (var44) {
                    float var119 = var61 - var88;
                    float var120 = var61 + var58 - var57 - var88;
                    this.s(var119 / var40, (var95 + var89) / var40, var120 / var40, (var96 + var89) / var40, var91, 50263086633642L, var41);
                    RenderUtil.u((var61 + 2.0f * var40 - var88) / var40, var113, var56, Category.n(var71.f()));
}
                ArrayList.N(var38, var80, (var61 + var58 + var50 - var88) / var40, var112, var93, var45, var74, 34995704477528L, var77);
                if (var75) {
                    ArrayList.g(28962, var38, var81, 16056385, (var61 + var58 + var82 + ArrayListEntry.c(var70) + var49 - var88) / var40, var112, var94, var45, (byte)var22, var74);
}
                GlStateManager.popMatrix();
}
            var68 = var99;
            var59 = var98;
}
        GlStateManager.popMatrix();
}
    private static float B(float var0) {
        var0 = MathUtil.q(var0, 0.0f, 1.0f);
        float var1 = 1.0f - var0;
        return 1.0f - var1 * var1 * var1;
}
    private void z(java.util.ArrayList<Float> var1, float var2, float var3, float var4) {
        var1.add(Float.valueOf(Math.max(var3, Math.min(var4, var2))));
}
    public ArrayList(long var1) {
        super(a ^ var1 ^ 0x5A131593A98AL);
        this.declare("ArrayList", Category.Visual, "Show a list of modules on screen", new Setting[0]);
        var1 = a ^ var1;
        this.U = new java.util.ArrayList();
        this.d = new java.util.ArrayList(6);
}
    private ArrayListRect r(boolean var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
        float var10 = var1 ? var2 - var3 - var4 + var5 : var2 + var3 - var5;
        float var11 = var1 ? var2 - var3 + var5 : var2 + var3 + var4 - var5;
        return new ArrayListRect(var10 / var9, (var6 + var8) / var9, var11 / var9, (var7 + var8) / var9, null);
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
    private void z(float var1, float var2, long var3, float var5, float var6, int var7, float var8, ArrayListRect var9, ArrayListRect var10, float var11) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var12 = var3 ^ 0x1F5A5E837417L;
        this.m(var1, var2, var5, var12, var6, var7, var8, var9, var10, var11, false);
}
    private static float I(float var0) {
        if (Font.arraylistFont == null) {
            return 0.0f;
}
        String var3 = Font.arraylistFont.Y();
        return !var3.equals("INTER") && !var3.equals("ROBOTO") ? 0.0f : 1.5f * var0;
}
    @Override
    public void A(long var1) {
        this.U.clear();
}
    private static void zkm$clinit() {
        try {
            C = new Object[7]; E = new String[7]; a(); h = new HashMap(13); long var11 = a ^ 106054434392260L;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var14 = 1; var14 < 8; ++var14) { var10003[var14] = (byte)(var11 << var14 * 8 >>> 56); }
            Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var20 = new String[11];
            int var18 = 0;
            String var17 = "%\u00b1\u00c5\u0006\u00c4+HH+\u007f\u0086\u0019\u0010\u00a2\u0083=\u0010U\u00c6\f\u0018\u00e3g|ua\u0091\u0002\u00a4\u00ad\u000b\u00b49\u0010\u0019;\u009a0\u00f6\u00fe\u00c0?>xlv\u001b^ar\u0018Y\u00e1\u00c08\u00e1GS\u00c5\u0012ZWu\u00beI\u00f0\u00a1\u00c8@\u0017\u0081\u00c7fd \u0010r:\u00d0\u0015\u00b5:\u00f0f\u0014\u00eb\u009f\u009f\u00d4c\u0083\u00ba\u0010Y\u00f7\b;\u0001\u0018\u00e0/z\u00b9\u000f?!M\u0099\u00d5\u0010W(,\u000b\u0001\u009d\u00b6\u00baM\u00bd\u0007\u00ae3\u00e9\u0012\u00fa\u0010\u0014\u0093\u00e6\u00dd\u0087\u009e\u000bvk\u00aa\u00fcz\u0089\u00dd\u00e8> >\u00f6bZ\u009c\u0011\u00f2@\u00d8T\u0083xY\u00a9\u0091#\u00b3\f\u0015\u009d\u008dnc\u00f1\u0089hs\u00a8\u00e1\u0019n\u00d1";
            int var19 = "%\u00b1\u00c5\u0006\u00c4+HH+\u007f\u0086\u0019\u0010\u00a2\u0083=\u0010U\u00c6\f\u0018\u00e3g|ua\u0091\u0002\u00a4\u00ad\u000b\u00b49\u0010\u0019;\u009a0\u00f6\u00fe\u00c0?>xlv\u001b^ar\u0018Y\u00e1\u00c08\u00e1GS\u00c5\u0012ZWu\u00beI\u00f0\u00a1\u00c8@\u0017\u0081\u00c7fd \u0010r:\u00d0\u0015\u00b5:\u00f0f\u0014\u00eb\u009f\u009f\u00d4c\u0083\u00ba\u0010Y\u00f7\b;\u0001\u0018\u00e0/z\u00b9\u000f?!M\u0099\u00d5\u0010W(,\u000b\u0001\u009d\u00b6\u00baM\u00bd\u0007\u00ae3\u00e9\u0012\u00fa\u0010\u0014\u0093\u00e6\u00dd\u0087\u009e\u000bvk\u00aa\u00fcz\u0089\u00dd\u00e8> >\u00f6bZ\u009c\u0011\u00f2@\u00d8T\u0083xY\u00a9\u0091#\u00b3\f\u0015\u009d\u008dnc\u00f1\u0089hs\u00a8\u00e1\u0019n\u00d1".length();
            int var16 = 16;
            int var25 = -1;
            block9: while (true) {
                String var26 = var17.substring(++var25, var25 + var16);
                int var10001 = -1;
                while (true) {
                    byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
                    String var37 = ArrayList.b(var21).intern();
                    switch (var10001) {
                        case 0: {
                            var20[var18++] = var37;
                            if ((var25 += var16) >= var19) {
                                b = var20;
                                e = new String[11];
                                x = new HashMap(13);
                                var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var1 = 1; var1 < 8; ++var1) {
                                    var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var6 = new long[8];
                                int var3 = 0;
                                String var4 = "\u0017\u0086\u00e8\u00a6\u00ed\u00d3\u0006\u0003\u00f9H\u001c\u0016\u000e\u0096$\u0088( \u00fd\u00be\u00a5i\u0098N,\u0016\u00c9\u0010\u001a@\u0014\r\u00b2;L\u0007p\u00dc\u00bd\u00a0%\u0094\u0013\u0084\u0017<\u00d0O";
                                int var5 = "\u0017\u0086\u00e8\u00a6\u00ed\u00d3\u0006\u0003\u00f9H\u001c\u0016\u000e\u0096$\u0088( \u00fd\u00be\u00a5i\u0098N,\u0016\u00c9\u0010\u001a@\u0014\r\u00b2;L\u0007p\u00dc\u00bd\u00a0%\u0094\u0013\u0084\u0017<\u00d0O".length();
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
                                                m = var6;
                                                return;
}
                                            default: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) continue block12;
                                                var4 = "\u009fQ\u0094ad\u00c9\u00fe\u0097\u00ed\u00c3\u00b9z\u00cejt\u00fe";
                                                var5 = "\u009fQ\u0094ad\u00c9\u00fe\u0097\u00ed\u00c3\u00b9z\u00cejt\u00fe".length();
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
                            var17 = "\u0087\u00a2\u009f\u00e00\u00c7\u0083\u00eeh?\u00a2\u001dk\u00c6\u00f9dl\u00ffS\u00d5Ox\u00988\u008el\u0081\u009dO`;L\u0010\u00a9\u00a1\u00d0},\u00c1\u00892\u0004\u0088E\u001dN\u0012m\u000b";
                            var19 = "\u0087\u00a2\u009f\u00e00\u00c7\u0083\u00eeh?\u00a2\u001dk\u00c6\u00f9dl\u00ffS\u00d5Ox\u00988\u008el\u0081\u009dO`;L\u0010\u00a9\u00a1\u00d0},\u00c1\u00892\u0004\u0088E\u001dN\u0012m\u000b".length();
                            var16 = 32;
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
        a = 32443898390876L;
        zkm$clinit();
        abyssCustomFont = new BooleanSetting("CFont", true);
        abyssBackground = new BooleanSetting("Background", true);
        abyssLine = new BooleanSetting("Line", true);
        abyssOutline = new BooleanSetting("Outline", true);
        abyssSort = new ModeSetting("A-Sort Mode", "LENGTH", "ALPHABETICAL");
        abyssColorMode = new ModeSetting("A-Color Mode", "FADE", "BLEND", "RAINBOW", "STATIC");
        abyssFadeSpeed = new NumberSetting("A-Fade Speed", 1.0f, 0.1f, 5.0f, 0.1f);
        customText = new TextSetting("Custom-text", "");
        bar = new BooleanSetting("Bar", true);
        scale = new NumberSetting("Scale", 0.8f, 0.25f, 3.0f, 0.01f);
        barColor = new ModeSetting("Bar-color", "THEME", "THEME_CUSTOM", "CUSTOM");
        offsetX = new NumberSetting("Offset-X", 4.0f, 0.0f, 100.0f, 1.0f);
        moduleNameLowercase = new BooleanSetting("Module-name-lowercase", true);
        capitalize = new BooleanSetting("Capitalize", true);
        textColor = new ModeSetting("Text-color", "THEME", "THEME_CUSTOM", "CUSTOM");
        rectangleYSpace = new NumberSetting("Rectangle-Y-space", 0.0f, 0.0f, 5.0f, 0.01f);
        customColor = new ColorSetting("Custom-color", "FFFFFF");
        rectangleYEdge = new NumberSetting("Rectangle-Y-edge", 0.25f, 0.0f, 5.0f, 0.01f);
        textCustomColor = new ColorSetting("Text-custom-color", "FFFFFF");
        suffixNameLowercase = new BooleanSetting("Suffix-name-lowercase", true);
        onlyShowSuffixModules = new BooleanSetting("Only-show-suffix-modules", false);
        icons = new BooleanSetting("Icons", false);
        backgroundOpacity = new PercentageSetting("Background-opacity", 50);
        offsetY = new NumberSetting("Offset-Y", 3.0f, 0.0f, 100.0f, 1.0f);
        splitSuffixAndName = new BooleanSetting("Split-suffix-and-name", true);
        gradientMode = new ModeSetting("Gradient-mode", false, "LEFT_RIGHT", "UP_DOWN", "LEFT_RIGHT");
        mode = new ModeSetting("ArrayList Pos", "TOP", "BOTTOM");
        barWidth = new NumberSetting("Bar-width", 2.0f, 0.0f, 5.0f, 0.01f);
        showSuffix = new BooleanSetting("Show-suffix", true);
        textShadow = new BooleanSetting("Text-shadow", true);
}
}