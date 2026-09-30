/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.ui.studio;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.ui.studio.AbstractSettingComponent;
import Abyss.ui.studio.BooleanSettingComponent;
import Abyss.ui.studio.CategoryPalette;
import Abyss.ui.studio.ColorSettingComponent;
import Abyss.ui.studio.HeaderSettingComponent;
import Abyss.ui.studio.ModeSettingComponent;
import Abyss.ui.studio.NumberSettingComponent;
import Abyss.ui.studio.PercentageSettingComponent;
import Abyss.ui.studio.StudioBindBadgeLayout;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.studio.StudioModuleActionLayout;
import Abyss.ui.studio.TextSettingComponent;
import Abyss.util.Animation;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.render.CustomFont;
import Abyss.util.render.FontUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class StudioModuleFrame {
    private static long a = 12075157230319L;

    private float Z;
    private final Minecraft m;
    private boolean s;
    private static final float L = 11.5f;
    private final Animation J;
    private final List<AbstractSettingComponent<?>> t;
    private static final float T = 4.0f;
    private final Animation K;
    private final Animation H;
    private final Module M;
    private final Animation A;
    private float F;
    private final StudioClickGuiScreen d;
    private static final float q = 1.5f;
    private final Animation S;
    private float Q;
    private final Animation V;
    private static final float v = 16.0f;

    private boolean u() {
        return !this.M.S() && this.M.f() != Category.Macro;
}
    public void t(char var1, int var2) {
        for (AbstractSettingComponent<?> var4 : this.t) {
            var4.t(var1);
}
}
    public void Q(long var1) {
        for (AbstractSettingComponent<?> var6 : this.t) {
            var6.k(116871293733447L);
}
}
    private StudioBindBadgeLayout y(long var1, boolean var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        boolean var11;
        var1 = a ^ var1;
        long var4 = (var1 ^ 0x4C78596436C5L) >>> 32;
        int var6 = (int)((var1 ^ 0x4C78596436C5L) << 32 >>> 32);
        long var7 = var1 ^ 0x22D159443EABL;
        long var9 = var1 ^ 0x637D9066735CL;
        boolean bl = var11 = this.n() || this.M.h() != 0 || var3;
        if (!var11) {
            return new StudioBindBadgeLayout(false, 0.0f, 0.0f, "", null);
}
        String var12 = this.q(var9);
        float var13 = Math.max(18.0f, FontUtil.A(FontUtil.n(var4, var6), var12, 0.66f, var7) + 8.0f);
        float var14 = this.Q + this.F - 9.5f;
        return new StudioBindBadgeLayout(true, var14 - var13 - 6.0f, var13, var12, null);
}
    public boolean n() {
        return this.d.s(this);
}
    private String q(int var1, char var2, int var3, short var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = (var5 ^ 0x44681F09A142L) >>> 16;
        int var9 = (int)((var5 ^ 0x44681F09A142L) << 48 >>> 48);
        String var10 = KeyBindUtil.p(var7, (char)var9, var3);
        if (var10 != null && !var10.trim().isEmpty()) {
            String var11 = var10.toUpperCase().replace("CONTROL", "CTRL").replace("RETURN", "ENTER").replace("ESCAPE", "ESC").replace("NUMPAD", "NUM").replace("BUTTON ", "M").replace("BUTTON", "M").replace("LEFT ", "L").replace("RIGHT ", "R").replace(" ", "");
            return var11.length() > 7 ? var11.substring(0, 7) : var11;
}
        return "KEY";
}
    private void r(StudioBindBadgeLayout var1, CategoryPalette var2, float var3, float var4, int var5, int var6) {
        long var7 = ((long)var5 << 32 | (long)var6 << 32 >>> 32) ^ a;
        long var9 = (var7 ^ 0x3F7D94897A2AL) >>> 32;
        int var11 = (int)((var7 ^ 0x3F7D94897A2AL) << 32 >>> 32);
        int var12 = (int)((var7 ^ 0x75C604447364L) >>> 32);
        int var13 = (int)((var7 ^ 0x75C604447364L) << 32 >>> 48);
        long var15 = var7 ^ 0x42A954D4D0EEL;
        CustomFont var22 = FontUtil.n(var9, var11);
        Color var23 = FontUtil.Y(new Color(24, 31, 44), var2.D, 0.28f + this.V.b(var4) * 0.15f);
        if (this.M.h() != 0 || this.n()) {
            var23 = FontUtil.Y(var23, var2.i, 0.18f + this.V.b(var4) * 0.12f);
}
        FontUtil.W(StudioBindBadgeLayout.Z(var1), this.Z + 3.0f, StudioBindBadgeLayout.Z(var1) + StudioBindBadgeLayout.B(var1), this.Z + 16.0f - 3.0f, 3.0f, FontUtil.a(var12, var13, var23, var3), FontUtil.a(var12, var13, FontUtil.Y(new Color(70, 78, 98), var2.i, 0.32f + this.V.b(var4) * 0.24f), var3));
        String var26 = StudioBindBadgeLayout.I(var1);
        Color var10007 = this.M.h() == 0 && !this.n() ? new Color(194, 205, 230) : var2.g;
        float var19 = var3;
        Color var20 = var10007;
        int var24 = FontUtil.a(var12, var13, var20, var19);
        float var25 = 0.66f;
        float var21 = 10.0f;
        FontUtil.S(var22, var26, StudioBindBadgeLayout.Z(var1), this.Z + 3.0f, StudioBindBadgeLayout.B(var1), var15, var21, var25, var24);
}
    public StudioModuleFrame(StudioClickGuiScreen var1, Module var2, long var3, boolean var5) {
        var3 = a ^ var3;
        long var6 = var3 ^ 0xB6702175B8CL;
        int var8 = (int)((var3 ^ 0x17AD404AB1E3L) >>> 56);
        long var9 = (var3 ^ 0x17AD404AB1E3L) << 8 >>> 8;
        this.t = new ArrayList();
        this.m = MinecraftRef.c((byte)var8, 0L);
        this.A = new Animation(0.0f);
        this.K = new Animation(0.0f);
        this.J = new Animation(0.0f);
        this.V = new Animation(0.0f);
        this.H = new Animation(0.0f);
        this.S = new Animation(0.0f);
        this.d = var1;
        this.M = var2;
        this.s = var5;
        this.f(var6);
}
    public void e(float var1, float var2, float var3) {
        this.Q = var1;
        this.Z = var2;
        this.F = var3;
}
    private void V(StudioModuleActionLayout var1, long var2, CategoryPalette var4, float var5, float var6) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var7 = var2 ^ 0xFCC249DBA61L;
        this.y(StudioModuleActionLayout.T(var1), StudioModuleActionLayout.t(var1), StudioModuleActionLayout.s(var1), FontUtil.G("clickgui.studio.visible", "Visible"), this.u() && this.M.D(), this.H.b(var6), var4, var7, var5, !this.u());
        this.y(StudioModuleActionLayout.C(var1), StudioModuleActionLayout.m(var1), StudioModuleActionLayout.s(var1), FontUtil.G("clickgui.studio.suffix", "Suffix"), this.M.r(), this.S.b(var6), var4, var7, var5, false);
}
    private void f(long var1) {
        this.t.clear();
        List<Setting> var30 = this.M.w();
        for (int var31 = 0; var31 < var30.size(); ++var31) {
            Setting var19 = var30.get(var31);
            if (var19 instanceof BooleanSetting) {
                this.t.add(new BooleanSettingComponent(this.d, 42526367563304L, this, (BooleanSetting)var19));
                continue;
}
            if (var19 instanceof ModeSetting) {
                this.t.add(new ModeSettingComponent(24565, this.d, this, 6954670, (byte)196, (ModeSetting)var19));
                continue;
}
            if (var19 instanceof NumberSetting) {
                this.t.add(new NumberSettingComponent(5045908428858L, this.d, this, (NumberSetting)var19));
                continue;
}
            if (var19 instanceof PercentageSetting) {
                this.t.add(new PercentageSettingComponent(this.d, 12855945411416L, this, (PercentageSetting)var19));
                continue;
}
            if (var19 instanceof ColorSetting) {
                this.t.add(new ColorSettingComponent(109847924827530L, this.d, this, (ColorSetting)var19));
                continue;
}
            if (var19 instanceof TextSetting) {
                this.t.add(new TextSettingComponent(this.d, this, 57160869329594L, (TextSetting)var19));
                continue;
}
            if (!(var19 instanceof HeaderSetting) || !HeaderSetting.occupied(var30, var31)) continue;
            this.t.add(new HeaderSettingComponent(this.d, this, 63975352640112L, (HeaderSetting)var19));
}
}
    public Module E() {
        return this.M;
}
    private StudioModuleActionLayout h(float var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var2 = a ^ var2;
        long var4 = (var2 ^ 0x2F0679CC2561L) >>> 32;
        int var6 = (int)((var2 ^ 0x2F0679CC2561L) << 32 >>> 32);
        long var9 = var2 ^ 0x41AF79EC2D0FL;
        CustomFont var11 = FontUtil.n(var4, var6);
        String var12 = FontUtil.G("clickgui.studio.visible", "Visible");
        String var13 = FontUtil.G("clickgui.studio.suffix", "Suffix");
        float var14 = Math.max(34.0f, FontUtil.A(var11, var12, 0.66f, var9) + 10.0f);
        float var15 = Math.max(32.0f, FontUtil.A(var11, var13, 0.66f, var9) + 10.0f);
        float var16 = this.Q + this.F - 6.0f;
        float var17 = var16 - var15;
        float var18 = var17 - 4.0f - var14;
        return new StudioModuleActionLayout(var1, var18, var14, var17, var15, null);
}
    public void y(float var1, float var2, long var3) {
        for (AbstractSettingComponent<?> var9 : this.t) {
            var9.A(var1, var2);
}
}
    public void s(float var1, float var2, float var3, long var4, char var6, float var7, float var8, CategoryPalette var9) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var10 = (0x1901307F0000L | (long)var6 << 48 >>> 48) ^ a;
        long var12 = var10 ^ 0x5FC7A8A267E6L;
        long var14 = var10 ^ 0x371A8EBAA2C3L;
        long var16 = var10 ^ 0x26549931FE0EL;
        long var10001 = var10 ^ 0x673CE860299CL;
        int var18 = (int)((var10 ^ 0x673CE860299CL) >>> 32);
        int var19 = (int)((var10 ^ 0x673CE860299CL) << 32 >>> 48);
        int var20 = (int)(var10001 << 48 >>> 48);
        long var21 = var10 ^ 0x51D0EFA52531L;
        long var23 = (var10 ^ 0x7199A0AB86E9L) >>> 32;
        int var25 = (int)((var10 ^ 0x7199A0AB86E9L) << 32 >>> 32);
        long var26 = var10 ^ 0x61BFBCF95046L;
        long var28 = var10 ^ 0x5464AE12B167L;
        int var30 = (int)((var10 ^ 0x441F4357EE2CL) >>> 32);
        int var31 = (int)((var10 ^ 0x441F4357EE2CL) << 32 >>> 32);
        long var34 = var10 ^ 0x31B34221826EL;
        long var36 = var10 ^ 0x46FFAA4E01CAL;
        var10001 = var10 ^ 0x3B2230668FA7L;
        int var38 = (int)((var10 ^ 0x3B2230668FA7L) >>> 32);
        int var39 = (int)((var10 ^ 0x3B2230668FA7L) << 32 >>> 48);
        int var41 = (int)((var10 ^ 0x6FE74BB28ADFL) >>> 32);
        int var42 = (int)((var10 ^ 0x6FE74BB28ADFL) << 32 >>> 48);
        int var43 = (int)((var10 ^ 0x6FE74BB28ADFL) << 48 >>> 48);
        CustomFont var52 = FontUtil.n(var23, var25);
        boolean var53 = this.G(var1, var2, this.Q, this.Z, this.F, 16.0f);
        this.A.d(var53 ? 1.0f : 0.0f);
        if (var53) {
            this.d.X(this.M.j(var36), var1, var2);
}
        this.m();
        StudioBindBadgeLayout var54 = this.y(var14, var53 || this.s);
        boolean var55 = StudioBindBadgeLayout.u(var54) && this.G(var1, var2, StudioBindBadgeLayout.Z(var54), this.Z + 3.0f, StudioBindBadgeLayout.B(var54), 10.0f);
        StudioModuleActionLayout var56 = this.h(this.Z + 16.0f + 3.0f, var28);
        boolean var57 = this.K.b(var3) > 0.01f && this.G(var1, var2, StudioModuleActionLayout.T(var56), StudioModuleActionLayout.s(var56), StudioModuleActionLayout.t(var56), 11.5f);
        boolean var58 = this.K.b(var3) > 0.01f && this.G(var1, var2, StudioModuleActionLayout.C(var56), StudioModuleActionLayout.s(var56), StudioModuleActionLayout.m(var56), 11.5f);
        this.V.d(var55 ? 1.0f : 0.0f);
        this.V.y(0.28f, this.d.y());
        this.H.d(var57 ? 1.0f : 0.0f);
        this.H.y(0.28f, this.d.y());
        this.S.d(var58 ? 1.0f : 0.0f);
        this.S.y(0.28f, this.d.y());
        float var59 = this.N(var3);
        float var60 = this.K.b(var3);
        float var61 = Math.max(this.J.b(var3), var60 * 0.58f);
        Color var62 = FontUtil.Y(new Color(17, 22, 33, 222), var9.D, this.J.b(var3) * 0.22f + var60 * 0.1f);
        Color var63 = FontUtil.Y(var62, new Color(52, 64, 84, 235), this.A.b(var3) * 0.25f + var60 * 0.08f);
        Color var64 = FontUtil.Y(new Color(14, 18, 27, 226), new Color(25, 30, 43, 235), this.A.b(var3) * 0.28f + var60 * 0.1f);
        var64 = FontUtil.Y(var64, FontUtil.Y(var9.D, var9.i, 0.34f), this.J.b(var3) * 0.14f + var60 * 0.08f);
        FontUtil.W(this.Q, this.Z, this.Q + this.F, this.Z + var59, 4.0f, FontUtil.a(var38, var39, var63, var7), FontUtil.a(var38, var39, FontUtil.Y(new Color(55, 64, 82), var9.i, this.J.b(var3) * 0.35f), var7));
        RenderUtil.c(var26, this.Q + 1.0f, this.Z + 1.0f, this.Q + this.F - 1.0f, this.Z + 16.0f, FontUtil.a(var38, var39, var64, var7));
        if (this.K.b(var3) > 0.01f) {
            double var83 = this.Q + 4.0f;
            Color var10005 = new Color(255, 255, 255, 18);
            float var48 = var7;
            Color var49 = var10005;
            RenderUtil.c(var26, var83, this.Z + 16.0f, this.Q + this.F - 4.0f, this.Z + 16.0f + 0.4f, FontUtil.a(var38, var39, var49, var48));
}
        if (var61 > 0.01f) {
            float var65 = this.Z + 3.0f;
            float var66 = 10.0f;
            RenderUtil.c(var26, this.Q + 1.7f, var65, this.Q + 3.0f, var65 + var66, FontUtil.a(var38, var39, FontUtil.Y(var9.i, var9.g, 0.35f + var61 * 0.2f), var7));
}
        float var78 = this.Q + this.F - 9.5f;
        float var79 = Math.max(28.0f, (StudioBindBadgeLayout.u(var54) ? StudioBindBadgeLayout.Z(var54) - 5.0f : var78 - 3.0f) - (this.Q + 7.0f));
        String var84 = FontUtil.s(var52, var21, this.M.Q(var41, (char)var42, (char)var43), var79);
        float var86 = this.Q + 7.0f;
        float var87 = this.Z + 4.05f;
        Color var88 = this.M.o() ? var9.g : new Color(246, 248, 252);
        float var71 = var7;
        Color var74 = var88;
        int var72 = FontUtil.a(var38, var39, var74, var71);
        float var75 = var87;
        float var50 = var86;
        String var51 = var84;
        var52.T(var34, var51, var50, var75, var72);
        if (StudioBindBadgeLayout.u(var54)) {
            this.r(var54, var9, var7, var3, var30, var31);
}
        float var85 = this.Z + 8.0f + 0.3f;
        float var89 = this.K.b(var3);
        Color var90 = new Color(236, 242, 252);
        var71 = var7;
        var74 = var90;
        FontUtil.u(var78, var85, 4.2f, var89, FontUtil.a(var38, var39, var74, var71));
        if (!(this.K.b(var3) <= 0.01f)) {
            float var67 = this.h(var3) * this.K.b(var3);
            FontUtil.D(var16, this.Q + 1.0f, this.Z + 16.0f, this.F - 2.0f, var67 + 2.0f, var8);
            float var68 = this.Z + 16.0f + 3.0f;
            this.V(var56, var12, var9, var7, var3);
            var68 += 13.0f;
            for (AbstractSettingComponent<?> var70 : this.t) {
                var70.H(this.Q + 4.0f, var68, this.F - 8.0f);
                var70.Z(var18, var1, (char)var19, var2, var3, var7, (short)var20, var9);
                var68 += var70.i(var3) + 1.5f;
}
            FontUtil.R(0L);
}
}
    public void m() {
        this.A.y(0.32f, this.d.y());
        this.K.d(this.s ? 1.0f : 0.0f);
        this.K.y(0.24f, this.d.y());
        this.J.d(this.M.o() ? 1.0f : 0.0f);
        this.J.y(0.24f, this.d.y());
}
    private String q(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var1 = a ^ var1;
        int var3 = (int)((var1 ^ 0x662AB94F5BAFL) >>> 32);
        int var4 = (int)((var1 ^ 0x662AB94F5BAFL) << 32 >>> 48);
        int var5 = (int)((var1 ^ 0x662AB94F5BAFL) << 48 >>> 48);
        if (this.n()) {
            return "...";
}
        return this.M.h() == 0 ? FontUtil.G("clickgui.studio.set", "Set").toUpperCase() : this.q(var3, (char)var4, this.M.h(), (short)var5);
}
    private void O(int var1, int var2, char var3) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a;
        int var6 = (int)((var4 ^ 0x4CF45ABB64AAL) >>> 48);
        long var7 = (var4 ^ 0x4CF45ABB64AAL) << 16 >>> 16;
        long var9 = var4 ^ 0x20901AC41B4BL;
        if (this.m.thePlayer != null) {
            this.M.u((short)var6, var7);
        } else {
            this.M.I(var9, !this.M.o());
            Modules.c(0L);
}
}
    private boolean G(float var1, float var2, float var3, float var4, float var5, float var6) {
        return var1 >= var3 && var1 <= var3 + var5 && var2 >= var4 && var2 <= var4 + var6;
}
    private void y(float var1, float var2, float var3, String var4, boolean var5, float var6, CategoryPalette var7, long var8, float var10, boolean var11) {
        var8 = a ^ var8;
        long var10001 = var8 ^ 0x3A4F7C978C61L;
        int var12 = (int)((var8 ^ 0x3A4F7C978C61L) >>> 32);
        int var13 = (int)((var8 ^ 0x3A4F7C978C61L) << 32 >>> 48);
        int var14 = (int)(var10001 << 48 >>> 48);
        long var15 = (var8 ^ 0x2B695BE14981L) >>> 32;
        int var17 = (int)((var8 ^ 0x2B695BE14981L) << 32 >>> 32);
        var10001 = var8 ^ 0x61D2CB2C40CFL;
        int var18 = (int)((var8 ^ 0x61D2CB2C40CFL) >>> 32);
        int var19 = (int)((var8 ^ 0x61D2CB2C40CFL) << 32 >>> 48);
        long var21 = var8 ^ 0x56BD9BBCE345L;
        CustomFont var25 = FontUtil.n(var15, var17);
        Color var26 = FontUtil.Y(new Color(18, 23, 34, 208), new Color(28, 36, 52, 224), var6 * 0.45f);
        Color var27 = new Color(66, 74, 95, 210);
        Color var28 = new Color(178, 190, 216);
        if (var5) {
            var26 = FontUtil.Y(var26, var7.D, 0.34f + var6 * 0.18f);
            var26 = FontUtil.Y(var26, var7.i, 0.12f);
            var27 = FontUtil.Y(new Color(86, 95, 119), var7.i, 0.58f);
            var28 = var7.g;
        } else if (var11) {
            var26 = FontUtil.Y(var26, new Color(30, 31, 37), 0.25f);
            var27 = new Color(58, 60, 68, 180);
            var28 = new Color(114, 121, 139);
}
        FontUtil.W(var1, var3, var1 + var2, var3 + 11.5f, 3.1f, FontUtil.a(var18, var19, var26, var10), FontUtil.a(var18, var19, var27, var10));
        FontUtil.S(var25, FontUtil.Q(var12, var25, var4, (short)var13, (char)var14, var2 - 6.0f, 0.66f), var1, var3, var2, var21, 11.5f, 0.66f, FontUtil.a(var18, var19, var28, var10));
}
    private float h(float var1) {
        float var2 = 16.0f;
        for (AbstractSettingComponent<?> var4 : this.t) {
            var2 += var4.i(var1) + 1.5f;
}
        return var2 + 2.5f;
}
    private boolean F(StudioModuleActionLayout var1, float var2, float var3, long var4, int var6) {
        var4 = a ^ var4;
        long var7 = (var4 ^ 0x473DF46DFA23L) >>> 16;
        int var9 = (int)((var4 ^ 0x473DF46DFA23L) << 48 >>> 48);
        if (this.G(var2, var3, StudioModuleActionLayout.T(var1), StudioModuleActionLayout.s(var1), StudioModuleActionLayout.t(var1), 11.5f)) {
            if (var6 == 0 && this.u()) {
                boolean var12 = !this.M.D();
                this.M.Y(var7, var12, (short)var9);
                Modules.c(0L);
}
            return true;
}
        if (this.G(var2, var3, StudioModuleActionLayout.C(var1), StudioModuleActionLayout.s(var1), StudioModuleActionLayout.m(var1), 11.5f)) {
            if (var6 == 0) {
                this.M.C(!this.M.r());
                Modules.c(0L);
}
            return true;
}
        return false;
}
    public boolean t() {
        return this.s;
}
    public float N(float var1) {
        return 16.0f + this.h(var1) * this.K.b(var1);
}
    public boolean h(int var1, int var2, float var3, float var4, int var5, byte var6) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var7 = ((long)var1 << 32 | (long)var2 << 40 >>> 32 | (long)var6 << 56 >>> 56) ^ a;
        long var9 = var7 ^ 0x614289B8696DL;
        int var11 = (int)((var7 ^ 0x1DEECA2655E8L) >>> 48);
        int var12 = (int)((var7 ^ 0x1DEECA2655E8L) << 16 >>> 48);
        int var13 = (int)((var7 ^ 0x1DEECA2655E8L) << 32 >>> 32);
        long var14 = var7 ^ 0x7BFC2FC77C53L;
        int var16 = (int)((var7 ^ 0x67AED8554FF8L) >>> 32);
        int var17 = (int)((var7 ^ 0x67AED8554FF8L) << 32 >>> 48);
        int var18 = (int)((var7 ^ 0x67AED8554FF8L) << 48 >>> 48);
        long var19 = var7 ^ 0x348E99621D3FL;
        long var21 = var7 ^ 0x317F91A2C596L;
        long var25 = var7 ^ 0x18820F6F6FF7L;
        boolean var27 = this.G(var3, var4, this.Q, this.Z, this.F, 16.0f);
        StudioBindBadgeLayout var28 = this.y(var14, var27 || this.s);
        if (StudioBindBadgeLayout.u(var28) && this.G(var3, var4, StudioBindBadgeLayout.Z(var28), this.Z + 3.0f, StudioBindBadgeLayout.B(var28), 10.0f)) {
            if (var5 == 0) {
                this.d.E(this, (short)var11, (short)var12, var13);
            } else if (var5 == 1 && !this.n()) {
                this.M.z(var19, 0);
                Modules.c(0L);
}
            return true;
}
        if (var27) {
            if (var5 == 0) {
                this.O(var16, var17, (char)var18);
            } else if (var5 == 1) {
                this.s = !this.s;
}
            return true;
}
        if (this.K.E() <= 0.01f) {
            return false;
}
        float var29 = this.Z + 16.0f + 3.0f;
        StudioModuleActionLayout var30 = this.h(var29, var25);
        if (this.F(var30, var3, var4, var9, var5)) {
            return true;
}
        var29 += 13.0f;
        for (AbstractSettingComponent<?> var32 : this.t) {
            var32.H(this.Q + 4.0f, var29, this.F - 8.0f);
            if (var32.V(var21, var3, var4, var5)) {
                return true;
}
            var29 += var32.i(1.0f) + 1.5f;
}
        return false;
}
}