/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.studio;

import Abyss.setting.settings.HeaderSetting;
import Abyss.ui.studio.AbstractSettingComponent;
import Abyss.ui.studio.CategoryPalette;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.studio.StudioModuleFrame;
import Abyss.util.render.CustomFont;
import Abyss.util.render.FontUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;

public class HeaderSettingComponent
extends AbstractSettingComponent<HeaderSetting> {
    private static long a;
    @Override
    public void Z(int var1, float var2, char var3, float var4, float var5, float var6, short var7, CategoryPalette var8) {
        long var9 = (long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var7 << 48 >>> 48;
        long var10001 = var9 ^ 0x7836FBD6A95L;
        int var11 = (int)((var9 ^ 0x7836FBD6A95L) >>> 32);
        int var12 = (int)((var9 ^ 0x7836FBD6A95L) << 32 >>> 48);
        int var13 = (int)(var10001 << 48 >>> 48);
        long var14 = var9 ^ 0x5B2590A25BA9L;
        long var16 = (var9 ^ 0x16A548CBAF75L) >>> 32;
        int var18 = (int)((var9 ^ 0x16A548CBAF75L) << 32 >>> 32);
        var10001 = var9 ^ 0x5C1ED806A63BL;
        int var19 = (int)((var9 ^ 0x5C1ED806A63BL) >>> 32);
        int var20 = (int)((var9 ^ 0x5C1ED806A63BL) << 32 >>> 48);
        long var22 = var9 ^ 0x777B06D0B7BAL;
        long var24 = var9 ^ 0xD668DE7EDDFL;
        CustomFont var28 = FontUtil.n(var16, var18);
        float var10000 = this.n;
        float var32 = this.J;
        float var10002 = this.n + this.C;
        float var10003 = this.J + this.O();
        Color var10005 = new Color(15, 18, 27, 215);
        float var26 = var6;
        Color var27 = var10005;
        RenderUtil.j(var10000, var32, var10002, var10003, 2.6f, var22, FontUtil.a(var19, var20, var27, var26));
        String var33 = FontUtil.Q(var11, var28, ((HeaderSetting)this.O).U(var24, this.S), (short)var12, (char)var13, this.C - 10.0f, 0.66f);
        var10003 = this.n + 6.0f;
        float var10004 = this.J + 2.35f;
        Color var10006 = new Color(165, 176, 194);
        var26 = var6;
        var27 = var10006;
        FontUtil.N(var28, var14, var33, var10003, var10004, 0.66f, FontUtil.a(var19, var20, var27, var26));
}
    public HeaderSettingComponent(StudioClickGuiScreen var1, StudioModuleFrame var2, long var3, HeaderSetting var5) {
        super(var1, var2, var5, a ^ var3 ^ 0x4402F8BCCB6L);
        var3 = a ^ var3;
}
    @Override
    public float O() {
        return 10.5f;
}
    @Override
    public boolean V(long var1, float var3, float var4, int var5) {
        return false;
}
    static {
        a = 71972391866718L;
    }
}