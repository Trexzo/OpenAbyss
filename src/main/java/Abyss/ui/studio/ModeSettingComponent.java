/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.studio;

import Abyss.setting.settings.ModeSetting;
import Abyss.ui.studio.AbstractSettingComponent;
import Abyss.ui.studio.CategoryPalette;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.studio.StudioModuleFrame;
import Abyss.util.Animation;
import Abyss.util.render.CustomFont;
import Abyss.util.render.FontUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ModeSettingComponent
extends AbstractSettingComponent<ModeSetting> {
    private static long a;

    private final Animation E;
    private boolean V;
    private static final float f = 8.0f;
    private final Animation p = new Animation(0.0f);
    private static final float s = 0.8f;

    @Override
    public void k(long var1) {
        this.V = false;
}
    @Override
    public boolean V(long var1, float var3, float var4, int var5) {
        if (this.I(var3, var4)) {
            if ((var5 == 0 || var5 == 1) && ((ModeSetting)this.O).S().size() > 1) {
                this.V = !this.V;
}
            return true;
}
        if (this.E.E() > 0.01f) {
            float var6 = this.n + 5.0f;
            float var7 = this.J + this.O() + 1.9f;
            float var8 = this.C - 10.0f;
            float var9 = var7 + 2.0f;
            for (String var11 : this.Q()) {
                if (this.G(var3, var4, var6 + 2.0f, var9, var8 - 4.0f, 8.0f)) {
                    ((ModeSetting)this.O).i(var11);
                    this.V = false;
                    return true;
}
                var9 += 8.8f;
}
}
        this.V = false;
        return false;
}
    @Override
    public float O() {
        return 13.0f;
}
    private List<String> Q() {
        return new ArrayList<String>(((ModeSetting)this.O).S());
}
    private String K(String var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var2 = a ^ var2;
        int var4 = (int)((var2 ^ 0x2BA012940A01L) >>> 48);
        int var5 = (int)((var2 ^ 0x2BA012940A01L) << 16 >>> 32);
        int var6 = (int)((var2 ^ 0x2BA012940A01L) << 48 >>> 48);
        List var7 = ((ModeSetting)this.O).o((char)var4, var5, this.S, var6);
        int var8 = ((ModeSetting)this.O).S().indexOf(var1);
        return var8 >= 0 && var8 < var7.size() ? (String)var7.get(var8) : var1;
}
    @Override
    public float L(float var1) {
        return ((float)this.Q().size() * 8.8f + 6.0f) * this.E.b(var1);
}
    @Override
    public void Z(int var1, float var2, char var3, float var4, float var5, float var6, short var7, CategoryPalette var8) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var9 = (long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var7 << 48 >>> 48;
        long var10001 = var9 ^ 0x7836FBD6A95L;
        int var11 = (int)((var9 ^ 0x7836FBD6A95L) >>> 32);
        int var12 = (int)((var9 ^ 0x7836FBD6A95L) << 32 >>> 48);
        int var13 = (int)(var10001 << 48 >>> 48);
        long var14 = var9 ^ 0x2C13D34E727L;
        int var16 = (int)((var9 ^ 0x1A551D586526L) >>> 56);
        long var17 = (var9 ^ 0x1A551D586526L) << 8 >>> 8;
        long var19 = (var9 ^ 0x16A548CBAF75L) >>> 32;
        int var21 = (int)((var9 ^ 0x16A548CBAF75L) << 32 >>> 32);
        long var22 = var9 ^ 0x780C48EBA71BL;
        long var24 = var9 ^ 0x6B71889605B1L;
        var10001 = var9 ^ 0x133564AFA096L;
        int var26 = (int)((var9 ^ 0x133564AFA096L) >>> 48);
        int var27 = (int)((var9 ^ 0x133564AFA096L) << 16 >>> 48);
        int var28 = (int)(var10001 << 32 >>> 32);
        long var29 = var9 ^ 0x5B2590A25BA9L;
        var10001 = var9 ^ 0x26AE25D8A91EL;
        int var31 = (int)((var9 ^ 0x26AE25D8A91EL) >>> 48);
        int var32 = (int)((var9 ^ 0x26AE25D8A91EL) << 16 >>> 48);
        int var33 = (int)(var10001 << 32 >>> 32);
        long var34 = var9 ^ 0x777B06D0B7BAL;
        var10001 = var9 ^ 0x5C1ED806A63BL;
        int var36 = (int)((var9 ^ 0x5C1ED806A63BL) >>> 32);
        int var37 = (int)((var9 ^ 0x5C1ED806A63BL) << 32 >>> 48);
        long var43 = var9 ^ 0x5985D74336C6L;
        CustomFont var50 = FontUtil.n(var19, var21);
        this.p.d(this.I(var2, var4) ? 1.0f : 0.0f);
        this.p.y(0.28f, this.M.y());
        this.E.d(this.V ? 1.0f : 0.0f);
        this.E.y(0.26f, this.M.y());
        String var51 = ((ModeSetting)this.O).a((char)var26, (short)var27, this.S, var28);
        float var52 = Math.min(this.C * 0.6f, Math.max(42.0f, FontUtil.A(var50, var51, 0.66f, var22) + 14.0f));
        float var53 = this.n + this.C - var52 - 6.0f;
        float var54 = Math.max(24.0f, var53 - this.n - 9.0f);
        RenderUtil.j(this.n, this.J, this.n + this.C, this.J + this.O(), 3.0f, var34, this.F(var8, (char)var31, (short)var32, this.p.b(var5), var6, var33));
        String var10002 = FontUtil.Q(var11, var50, ((ModeSetting)this.O).e((byte)var16, this.S, var17), (short)var12, (char)var13, var54, 0.66f);
        float var10003 = this.n + 6.0f;
        float var10004 = this.J + 3.12f;
        Color var10006 = new Color(239, 244, 251);
        float var45 = var6;
        Color var46 = var10006;
        FontUtil.N(var50, var29, var10002, var10003, var10004, 0.66f, FontUtil.a(var36, var37, var46, var45));
        float var55 = this.J + 1.35f;
        float var56 = this.O() - 2.7f;
        float var57 = 8.5f;
        float var58 = var52 - var57 - 6.5f;
        float var59 = FontUtil.w(var50, var51, var58, 0.66f, 0.52f, var14);
        RenderUtil.j(var53, var55, var53 + var52, var55 + var56, 2.6f, var34, FontUtil.a(var36, var37, FontUtil.Y(new Color(27, 35, 52), var8.i, 0.17f), var6));
        float var87 = var53 + 2.5f;
        var10004 = var52 - var57 - 3.0f;
        Color var10008 = new Color(191, 207, 234);
        var45 = var6;
        var46 = var10008;
        FontUtil.S(var50, var51, var87, var55, var10004, var24, var56, var59, FontUtil.a(var36, var37, var46, var45));
        float var10000 = var53 + var52 - 4.6f;
        float var85 = var55 + var56 / 2.0f + 0.1f;
        var10004 = this.E.b(var5);
        Color var10005 = new Color(240, 245, 252);
        var45 = var6;
        var46 = var10005;
        FontUtil.u(var10000, var85, 2.8f, var10004, FontUtil.a(var36, var37, var46, var45));
        if (!(this.E.b(var5) <= 0.01f)) {
            float var60 = this.n + 5.0f;
            float var61 = this.J + this.O() + 1.9f;
            float var62 = this.C - 10.0f;
            float var63 = (float)this.Q().size() * 8.8f + 4.0f;
            float var64 = var6 * this.E.b(var5);
            var10006 = new Color(18, 23, 34, 238);
            var45 = var64;
            var46 = var10006;
            int var96 = FontUtil.a(var36, var37, var46, var45);
            Color var10007 = new Color(76, 88, 112, 160);
            var45 = var64;
            var46 = var10007;
            FontUtil.W(var60, var61, var60 + var62, var61 + var63, 3.0f, var96, FontUtil.a(var36, var37, var46, var45));
            float var65 = var61 + 2.0f;
            for (String var67 : this.Q()) {
                boolean var68 = this.G(var2, var4, var60 + 2.0f, var65, var62 - 4.0f, 8.0f);
                boolean var69 = var67.equalsIgnoreCase(((ModeSetting)this.O).Y());
                RenderUtil.j(var60 + 2.0f, var65, var60 + var62 - 2.0f, var65 + 8.0f, 2.2f, var34, FontUtil.a(var36, var37, FontUtil.Y(new Color(18, 23, 35), var69 ? var8.D : new Color(44, 53, 70), var69 ? 0.32f : (var68 ? 0.16f : 0.0f)), var64));
                String var86 = FontUtil.Q(var11, var50, this.K(var67, var43), (short)var12, (char)var13, var62 - 10.0f, 0.66f);
                float var88 = var60 + 4.0f;
                var10005 = var69 ? var8.g : new Color(231, 236, 245);
                var45 = var64;
                var46 = var10005;
                int var75 = FontUtil.a(var36, var37, var46, var45);
                float var81 = 0.66f;
                float var47 = var65 + 1.25f;
                float var48 = var88;
                String var49 = var86;
                FontUtil.N(var50, var29, var49, var48, var47, var81, var75);
                var65 += 8.8f;
}
}
}
    public ModeSettingComponent(int var1, StudioClickGuiScreen var2, StudioModuleFrame var3, int var4, byte var5, ModeSetting var6) {
        super(var2, var3, var6, ((long)var1 << 32 | (long)var4 << 40 >>> 32 | (long)var5 << 56 >>> 56) ^ a ^ 0x496DB433B20CL);
        this.E = new Animation(0.0f);
}
    static {
        a = 116011316814672L;
    }
}