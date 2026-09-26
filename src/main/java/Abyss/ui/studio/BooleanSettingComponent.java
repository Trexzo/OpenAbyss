/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.studio;

import Abyss.setting.settings.BooleanSetting;
import Abyss.ui.studio.AbstractSettingComponent;
import Abyss.ui.studio.CategoryPalette;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.studio.StudioModuleFrame;
import Abyss.util.Animation;
import Abyss.util.render.CustomFont;
import Abyss.util.render.FontUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;

public class BooleanSettingComponent
extends AbstractSettingComponent<BooleanSetting> {
    private static long a;

    private final Animation c;
    private final Animation L;

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
        long var25 = var9 ^ 0x777B06D0B7BAL;
        var10001 = var9 ^ 0x5C1ED806A63BL;
        int var27 = (int)((var9 ^ 0x5C1ED806A63BL) >>> 32);
        int var28 = (int)((var9 ^ 0x5C1ED806A63BL) << 32 >>> 48);
        CustomFont var32 = FontUtil.n(var22, var24);
        this.L.d(this.I(var2, var4) ? 1.0f : 0.0f);
        this.L.y(0.28f, this.M.y());
        this.c.d(((BooleanSetting)this.O).c() ? 1.0f : 0.0f);
        this.c.y(0.28f, this.M.y());
        RenderUtil.j(this.n, this.J, this.n + this.C, this.J + this.O(), 3.0f, var25, this.F(var8, (char)var16, (short)var17, this.L.b(var5), var6, var18));
        String var10002 = FontUtil.Q(var11, var32, ((BooleanSetting)this.O).e((byte)var19, this.S, var20), (short)var12, (char)var13, this.C - 24.0f, 0.66f);
        float var10003 = this.n + 6.0f;
        float var10004 = this.J + 3.15f;
        Color var10006 = new Color(239, 244, 251);
        float var30 = var6;
        Color var31 = var10006;
        FontUtil.N(var32, var14, var10002, var10003, var10004, 0.66f, FontUtil.a(var27, var28, var31, var30));
        float var33 = 13.0f;
        float var34 = 6.0f;
        float var35 = this.n + this.C - var33 - 6.0f;
        float var36 = this.J + this.O() / 2.0f - var34 / 2.0f;
        Color var37 = FontUtil.Y(new Color(57, 66, 84), var8.g, this.c.b(var5));
        RenderUtil.j(var35, var36, var35 + var33, var36 + var34, 3.0f, var25, FontUtil.a(var27, var28, var37, var6));
        float var38 = 4.0f;
        float var39 = var35 + 1.0f + (var33 - var38 - 2.0f) * this.c.b(var5);
        float var44 = var36 + 1.0f;
        float var45 = var39 + var38;
        var10003 = var36 + 1.0f + var38;
        Color var10005 = new Color(248, 250, 255);
        var30 = var6;
        var31 = var10005;
        RenderUtil.j(var39, var44, var45, var10003, 2.0f, var25, FontUtil.a(var27, var28, var31, var30));
}
    public BooleanSettingComponent(StudioClickGuiScreen var1, long var2, StudioModuleFrame var4, BooleanSetting var5) {
        super(var1, var4, var5, a ^ var2 ^ 0x110EB5BCA275L);
        var2 = a ^ var2;
        this.L = new Animation(0.0f);
        this.c = new Animation(0.0f);
}
    @Override
    public float O() {
        return 13.0f;
}
    @Override
    public boolean V(long var1, float var3, float var4, int var5) throws Throwable {
        long var6 = var1 ^ 0x8502173EF3AL;
        if (var5 == 0 && this.I(var3, var4)) {
            ((BooleanSetting)this.O).W(var6);
            return true;
}
        return false;
}
    static {
        a = 79963295163333L;
    }
}