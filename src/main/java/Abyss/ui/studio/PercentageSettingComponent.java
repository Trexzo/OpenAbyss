/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package Abyss.ui.studio;

import Abyss.setting.settings.PercentageSetting;
import Abyss.ui.studio.AbstractSettingComponent;
import Abyss.ui.studio.CategoryPalette;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.studio.StudioModuleFrame;
import Abyss.util.Animation;
import Abyss.util.MathUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.FontUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import org.lwjgl.input.Mouse;

public class PercentageSettingComponent
extends AbstractSettingComponent<PercentageSetting> {
    private static long a;

    private boolean g;
    private final Animation H;
    private final Animation r;

    @Override
    public float O() {
        return 14.8f;
}
    @Override
    public void A(float var1, float var2) {
        this.g = false;
}
    @Override
    public void k(long var1) {
        this.g = false;
}
    @Override
    public void Z(int var1, float var2, char var3, float var4, float var5, float var6, short var7, CategoryPalette var8) {
        long var9 = (long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var7 << 48 >>> 48;
        long var10001 = var9 ^ 0x7836FBD6A95L;
        int var11 = (int)((var9 ^ 0x7836FBD6A95L) >>> 32);
        int var12 = (int)((var9 ^ 0x7836FBD6A95L) << 32 >>> 48);
        int var13 = (int)(var10001 << 48 >>> 48);
        long var14 = var9 ^ 0x5B2590A25BA9L;
        var10001 = var9 ^ 0x26AE25D8A91EL;
        int var16 = (int)((var9 ^ 0x26AE25D8A91EL) >>> 48);
        int var17 = (int)((var9 ^ 0x26AE25D8A91EL) << 16 >>> 48);
        int var18 = (int)(var10001 << 32 >>> 32);
        int var19 = (int)((var9 ^ 0x1A551D586526L) >>> 56);
        long var20 = (var9 ^ 0x1A551D586526L) << 8 >>> 8;
        long var22 = (var9 ^ 0x16A548CBAF75L) >>> 32;
        int var24 = (int)((var9 ^ 0x16A548CBAF75L) << 32 >>> 32);
        int var25 = (int)((var9 ^ 0x7FEE263CBDCFL) >>> 32);
        long var26 = (var9 ^ 0x7FEE263CBDCFL) << 32 >>> 32;
        long var28 = var9 ^ 0x777B06D0B7BAL;
        var10001 = var9 ^ 0x5C1ED806A63BL;
        int var30 = (int)((var9 ^ 0x5C1ED806A63BL) >>> 32);
        int var31 = (int)((var9 ^ 0x5C1ED806A63BL) << 32 >>> 48);
        long var33 = var9 ^ 0x780C48EBA71BL;
        long var35 = var9 ^ 0x6B71889605B1L;
        CustomFont var39 = FontUtil.n(var22, var24);
        this.r.d(this.I(var2, var4) ? 1.0f : 0.0f);
        this.r.y(0.28f, this.M.y());
        if (this.g && Mouse.isButtonDown((int)0)) {
            float var40 = this.n + 8.0f;
            float var41 = this.C - 16.0f;
            float var42 = MathUtil.q((var2 - var40) / var41, 0.0f, 1.0f);
            ((PercentageSetting)this.O).b(var25, var26, Math.round(var42 * 100.0f));
}
        float var60 = (float)((PercentageSetting)this.O).k() / 100.0f;
        this.H.d(var60);
        this.H.y(0.28f, this.M.y());
        String var61 = ((PercentageSetting)this.O).k() + "%";
        float var62 = Math.max(18.0f, FontUtil.A(var39, var61, 0.66f, var33) + 8.0f);
        float var43 = this.n + this.C - var62 - 6.0f;
        RenderUtil.j(this.n, this.J, this.n + this.C, this.J + this.O(), 3.0f, var28, this.F(var8, (char)var16, (short)var17, this.r.b(var5), var6, var18));
        String var10002 = FontUtil.Q(var11, var39, ((PercentageSetting)this.O).e((byte)var19, this.S, var20), (short)var12, (char)var13, var43 - this.n - 10.0f, 0.66f);
        float var10003 = this.n + 6.0f;
        float var10004 = this.J + 3.0f;
        Color var10006 = new Color(239, 244, 251);
        float var37 = var6;
        Color var38 = var10006;
        FontUtil.N(var39, var14, var10002, var10003, var10004, 0.66f, FontUtil.a(var30, var31, var38, var37));
        float var44 = this.J + 1.35f;
        float var45 = 6.0f;
        String var46 = FontUtil.Q(var11, var39, var61, (short)var12, (char)var13, var62 - 4.0f, 0.66f);
        float var66 = var43 + var62;
        var10003 = var44 + var45;
        Color var10005 = new Color(27, 34, 49);
        var37 = var6;
        var38 = var10005;
        RenderUtil.j(var43, var44, var66, var10003, 2.6f, var28, FontUtil.a(var30, var31, var38, var37));
        float var67 = var43 + 1.0f;
        var10004 = var62 - 2.0f;
        Color var10008 = new Color(193, 207, 234);
        var37 = var6;
        var38 = var10008;
        FontUtil.S(var39, var46, var67, var44, var10004, var35, var45, 0.66f, FontUtil.a(var30, var31, var38, var37));
        float var47 = this.n + 8.0f;
        float var48 = this.C - 16.0f;
        float var49 = this.J + this.O() - 3.35f;
        float var68 = var47 + var48;
        var10003 = var49 + 1.6f;
        var10005 = new Color(38, 46, 60);
        var37 = var6;
        var38 = var10005;
        RenderUtil.j(var47, var49, var68, var10003, 0.8f, var28, FontUtil.a(var30, var31, var38, var37));
        float var50 = var48 * this.H.b(var5);
        RenderUtil.j(var47, var49, var47 + var50, var49 + 1.6f, 0.8f, var28, FontUtil.a(var30, var31, FontUtil.Y(var8.i, var8.g, 0.38f), var6));
        float var51 = MathUtil.q(var47 + var50 - 1.7f, var47, var47 + var48 - 3.4f);
        float var65 = var49 - 0.65f;
        float var69 = var51 + 3.4f;
        var10003 = var49 + 2.45f;
        var10005 = new Color(246, 248, 253);
        var37 = var6;
        var38 = var10005;
        RenderUtil.j(var51, var65, var69, var10003, 1.7f, var28, FontUtil.a(var30, var31, var38, var37));
}
    public PercentageSettingComponent(StudioClickGuiScreen var1, long var2, StudioModuleFrame var4, PercentageSetting var5) {
        super(var1, var4, var5, a ^ var2 ^ 0xDB20B726ED6L);
        var2 = a ^ var2;
        this.r = new Animation(0.0f);
        this.H = new Animation(0.0f);
}
    @Override
    public boolean V(long var1, float var3, float var4, int var5) {
        float var6 = this.n + 8.0f;
        float var7 = this.C - 16.0f;
        float var8 = this.J + this.O() - 3.35f;
        if (var5 == 0 && this.G(var3, var4, var6, var8 - 1.2f, var7, 4.0f)) {
            this.g = true;
            return true;
}
        return false;
}
    static {
        a = 133150059817494L;
    }
}