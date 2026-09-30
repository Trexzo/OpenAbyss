/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package Abyss.ui.studio;

import Abyss.setting.settings.NumberSetting;
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

public class NumberSettingComponent
extends AbstractSettingComponent<NumberSetting> {
    private static long a;

    private final Animation B;
    private boolean W;
    private final Animation E;

    @Override
    public float O() {
        return 14.8f;
}
    @Override
    public void Z(int var1, float var2, char var3, float var4, float var5, float var6, short var7, CategoryPalette var8) {
        long var9 = (long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var7 << 48 >>> 48;
        long var10001 = var9 ^ 0x7836FBD6A95L;
        int var11 = (int)((var9 ^ 0x7836FBD6A95L) >>> 32);
        int var12 = (int)((var9 ^ 0x7836FBD6A95L) << 32 >>> 48);
        int var13 = (int)(var10001 << 48 >>> 48);
        int var14 = (int)((var9 ^ 0x5B01BFBEAFC7L) >>> 56);
        long var15 = (var9 ^ 0x5B01BFBEAFC7L) << 8 >>> 8;
        long var17 = var9 ^ 0x5B2590A25BA9L;
        var10001 = var9 ^ 0x26AE25D8A91EL;
        int var21 = (int)((var9 ^ 0x26AE25D8A91EL) >>> 48);
        int var22 = (int)((var9 ^ 0x26AE25D8A91EL) << 16 >>> 48);
        int var23 = (int)(var10001 << 32 >>> 32);
        int var24 = (int)((var9 ^ 0x1A551D586526L) >>> 56);
        long var25 = (var9 ^ 0x1A551D586526L) << 8 >>> 8;
        long var27 = (var9 ^ 0x16A548CBAF75L) >>> 32;
        int var29 = (int)((var9 ^ 0x16A548CBAF75L) << 32 >>> 32);
        long var30 = var9 ^ 0x777B06D0B7BAL;
        var10001 = var9 ^ 0x5C1ED806A63BL;
        int var32 = (int)((var9 ^ 0x5C1ED806A63BL) >>> 32);
        int var33 = (int)((var9 ^ 0x5C1ED806A63BL) << 32 >>> 48);
        long var35 = var9 ^ 0x780C48EBA71BL;
        long var37 = var9 ^ 0x6B71889605B1L;
        CustomFont var41 = FontUtil.n(var27, var29);
        this.E.d(this.I(var2, var4) ? 1.0f : 0.0f);
        this.E.y(0.28f, this.M.y());
        if (this.W && Mouse.isButtonDown((int)0)) {
            float var42 = this.n + 8.0f;
            float var43 = this.C - 16.0f;
            float var44 = MathUtil.q((var2 - var42) / var43, 0.0f, 1.0f);
            ((NumberSetting)this.O).o((byte)var14, var15, ((NumberSetting)this.O).i() + var44 * (((NumberSetting)this.O).F() - ((NumberSetting)this.O).i()));
}
        float var62 = (((NumberSetting)this.O).L() - ((NumberSetting)this.O).i()) / (((NumberSetting)this.O).F() - ((NumberSetting)this.O).i());
        this.B.d(var62);
        this.B.y(0.28f, this.M.y());
        String var63 = FontUtil.l(((NumberSetting)this.O).L(), 0L);
        float var64 = Math.max(18.0f, FontUtil.A(var41, var63, 0.66f, var35) + 8.0f);
        float var45 = this.n + this.C - var64 - 6.0f;
        RenderUtil.j(this.n, this.J, this.n + this.C, this.J + this.O(), 3.0f, var30, this.F(var8, (char)var21, (short)var22, this.E.b(var5), var6, var23));
        String var10002 = FontUtil.Q(var11, var41, ((NumberSetting)this.O).e((byte)var24, this.S, var25), (short)var12, (char)var13, var45 - this.n - 10.0f, 0.66f);
        float var10003 = this.n + 6.0f;
        float var10004 = this.J + 3.0f;
        Color var10006 = new Color(239, 244, 251);
        float var39 = var6;
        Color var40 = var10006;
        FontUtil.N(var41, var17, var10002, var10003, var10004, 0.66f, FontUtil.a(var32, var33, var40, var39));
        float var46 = this.J + 1.35f;
        float var47 = 6.0f;
        String var48 = FontUtil.Q(var11, var41, var63, (short)var12, (char)var13, var64 - 4.0f, 0.66f);
        float var68 = var45 + var64;
        var10003 = var46 + var47;
        Color var10005 = new Color(27, 34, 49);
        var39 = var6;
        var40 = var10005;
        RenderUtil.j(var45, var46, var68, var10003, 2.6f, var30, FontUtil.a(var32, var33, var40, var39));
        float var69 = var45 + 1.0f;
        var10004 = var64 - 2.0f;
        Color var10008 = new Color(193, 207, 234);
        var39 = var6;
        var40 = var10008;
        FontUtil.S(var41, var48, var69, var46, var10004, var37, var47, 0.66f, FontUtil.a(var32, var33, var40, var39));
        float var49 = this.n + 8.0f;
        float var50 = this.C - 16.0f;
        float var51 = this.J + this.O() - 3.35f;
        float var70 = var49 + var50;
        var10003 = var51 + 1.6f;
        var10005 = new Color(38, 46, 60);
        var39 = var6;
        var40 = var10005;
        RenderUtil.j(var49, var51, var70, var10003, 0.8f, var30, FontUtil.a(var32, var33, var40, var39));
        float var52 = var50 * this.B.b(var5);
        RenderUtil.j(var49, var51, var49 + var52, var51 + 1.6f, 0.8f, var30, FontUtil.a(var32, var33, FontUtil.Y(var8.i, var8.g, 0.38f), var6));
        float var53 = MathUtil.q(var49 + var52 - 1.7f, var49, var49 + var50 - 3.4f);
        float var67 = var51 - 0.65f;
        float var71 = var53 + 3.4f;
        var10003 = var51 + 2.45f;
        var10005 = new Color(246, 248, 253);
        var39 = var6;
        var40 = var10005;
        RenderUtil.j(var53, var67, var71, var10003, 1.7f, var30, FontUtil.a(var32, var33, var40, var39));
}
    public NumberSettingComponent(long var1, StudioClickGuiScreen var3, StudioModuleFrame var4, NumberSetting var5) {
        super(var3, var4, var5, a ^ var1 ^ 0x6A200759459CL);
        var1 = a ^ var1;
        this.E = new Animation(0.0f);
        this.B = new Animation(0.0f);
}
    @Override
    public void A(float var1, float var2) {
        this.W = false;
}
    @Override
    public void k(long var1) {
        this.W = false;
}
    @Override
    public boolean V(long var1, float var3, float var4, int var5) {
        float var6 = this.n + 8.0f;
        float var7 = this.C - 16.0f;
        float var8 = this.J + this.O() - 3.35f;
        if (var5 == 0 && this.G(var3, var4, var6, var8 - 1.2f, var7, 4.0f)) {
            this.W = true;
            return true;
}
        return false;
}
    static {
        a = 19434520715838L;
    }
}