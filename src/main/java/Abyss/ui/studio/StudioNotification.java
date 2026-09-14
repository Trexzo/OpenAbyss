/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.studio;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.ui.studio.CategoryPalette;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.studio.StudioFrameState;
import Abyss.ui.studio.StudioModuleFrame;
import Abyss.util.Animation;
import Abyss.util.render.CustomFont;
import Abyss.util.render.FontUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class StudioNotification {
    private final Animation h;
    private static final float W = 3.0f;
    private static final float D = 4.0f;
    private float P;
    private final List<StudioModuleFrame> J;
    private final Category s;
    private static long private static final float O = 136.0f;
    private boolean R;
    private float t;
    private final StudioClickGuiScreen I;
    private static final float z = 2.0f;
    private float i;
    private static final float E = 16.0f;
    private final CategoryPalette d;
    private float u;
    private boolean A;
    private final Animation F;
    private final StudioFrameState N;

    public void t(float var1, char var2, float var3, float var4, int var5) {
        if (this.A) {
            float var9 = var1 - this.P;
            float var10 = var3 - this.u;
            if (!this.R && (Math.abs(var9) > 2.0f || Math.abs(var10) > 2.0f)) {
                this.R = true;
}
            if (this.R) {
                this.N.J = this.t + var9;
                this.N.h = this.i + var10 - var4;
}
}
}
    public boolean m(float var1, float var2, long var3, int var5, float var6) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        float var10 = this.N.J;
        float var11 = this.N.h + var6;
        if (this.g(var1, var2, var10, var11, 136.0f, 16.0f)) {
            if (var5 == 0) {
                this.A = true;
                this.R = false;
                this.P = var1;
                this.u = var2;
                this.t = this.N.J;
                this.i = this.N.h + var6;
            } else if (var5 == 1) {
                this.N.S = !this.N.S;
}
            return true;
}
        if (this.h.E() <= 0.01f) {
            return false;
}
        float var12 = var11 + 16.0f + 4.0f;
        for (StudioModuleFrame var14 : this.J) {
            var14.e(var10 + 4.0f, var12, 128.0f);
            if (var14.h(21991, 9503246, var1, var2, var5, (byte)121)) {
                return true;
}
            var12 += var14.N(1.0f) + 3.0f;
}
        return false;
}
    private void A(int var1, float var2, long var3, float var5, float var6, float var7) {
        long var8 = ((long)var1 << 32 | 0xAAF4ACD2L) ^ a;
        int var10 = (int)((var8 ^ 0x6E5B5CCDA32DL) >>> 32);
        int var11 = (int)((var8 ^ 0x6E5B5CCDA32DL) << 32 >>> 48);
        float var17 = 16.0f + var6;
        Color var10006 = new Color(10, 14, 24, 212);
        float var15 = var7;
        Color var16 = var10006;
        FontUtil.W(var2, var5, var2 + 136.0f, var5 + var17, 4.5f, FontUtil.a(var10, var11, var16, var15), FontUtil.a(var10, var11, FontUtil.Y(new Color(68, 75, 92), this.d.i, 0.55f), var7));
}
    public void d(float var1, float var2, float var3, float var4, float var5, long var6, float var8) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.h.d(this.N.S ? 1.0f : 0.0f);
        this.h.y(0.22f, this.I.y());
        float var21 = this.N.J;
        float var22 = this.N.h + var3;
        float var23 = this.C(var4);
        boolean var24 = this.g(var1, var2, var21, var22, 136.0f, 16.0f);
        this.F.d(var24 ? 1.0f : 0.0f);
        this.F.y(0.28f, this.I.y());
        this.A(21841, var21, 2868161746L, var22, var23, var5);
        this.v(59424431666853L, var21, var22, var23, var5, var4);
        if (!(var23 <= 1.0f)) {
            FontUtil.D(59025169005576L, var21 - 3.0f, var22 + 16.0f, 142.0f, var23 + 4.0f, var8);
            float var25 = var22 + 16.0f + 4.0f;
            for (StudioModuleFrame var27 : this.J) {
                var27.e(var21 + 4.0f, var25, 128.0f);
                var27.s(var1, var2, var4, 419508351L, '\ud0e9', var5, var8, this.d);
                var25 += var27.N(var4) + 3.0f;
}
            FontUtil.R(0L);
}
}
    private float v(float var1) {
        float var2 = 4.0f;
        for (StudioModuleFrame var4 : this.J) {
            var2 += var4.N(var1) + 3.0f;
}
        return Math.max(0.0f, var2 - 3.0f + 4.0f);
}
    private void v(long var1, float var3, float var4, float var5, float var6, float var7) {
        long var10001 = 44910181633441L;
        var10001 = 52136776939807L;
        int var25 = (int)(var10001 << 48 >>> 48);
        CustomFont var32 = FontUtil.G();
        RenderUtil.c(0L, var3 + 1.0f, var4 + 1.0f, var3 + 136.0f - 1.0f, var4 + 16.0f - 0.5f, FontUtil.a(10456, 30572, FontUtil.Y(new Color(14, 18, 27, 234), new Color(24, 30, 43, 240), this.F.b(var7) * 0.35f), var6));
        if (var5 > 1.0f) {
            double var48 = var3 + 5.0f;
            Color var10005 = new Color(255, 255, 255, 22);
            float var30 = var6;
            Color var31 = var10005;
            RenderUtil.c(0L, var48, var4 + 16.0f - 0.5f, var3 + 136.0f - 5.0f, var4 + 16.0f + 0.2f, FontUtil.a(10456, 30572, var31, var30));
}
        String var33 = String.valueOf(this.J.size());
        float var34 = FontUtil.A(var32, var33, 0.7f, 14065606937729L) + 12.0f;
        float var35 = var3 + 136.0f - 10.0f;
        float var36 = var35 - 12.0f - var34;
        float var37 = Math.max(30.0f, var36 - (var3 + 9.0f) - 7.0f);
        String var51 = FontUtil.s(var32, 72750986159927L, this.s.x(12139, 2577, (short)var25).replace('_', ' '), var37);
        float var53 = var3 + 9.0f;
        float var56 = var4 + 4.15f;
        Color var59 = new Color(246, 248, 252);
        float var39 = var6;
        Color var43 = var59;
        var32.T(37697014677608L, var51, var53, var56, FontUtil.a(10456, 30572, var43, var39));
        float var49 = var4 + 3.0f;
        float var52 = var36 + var34;
        var53 = var4 + 12.0f;
        var59 = new Color(255, 255, 255, 26);
        var39 = var6;
        var43 = var59;
        RenderUtil.j(var36, var49, var52, var53, 4.4f, 4113131265056L, FontUtil.a(10456, 30572, var43, var39));
        var53 = var36 + (var34 - FontUtil.A(var32, var33, 0.7f, 14065606937729L)) / 2.0f;
        var56 = var4 + 4.55f;
        Color var10006 = new Color(186, 198, 222);
        var39 = var6;
        var43 = var10006;
        FontUtil.N(var32, 52653074198579L, var33, var53, var56, 0.7f, FontUtil.a(10456, 30572, var43, var39));
        float var50 = var4 + 8.0f + 0.4f;
        var56 = this.h.b(var7);
        var59 = new Color(236, 242, 252);
        var39 = var6;
        var43 = var59;
        FontUtil.u(var35, var50, 4.6f, var56, FontUtil.a(10456, 30572, var43, var39));
}
    public void k(float var1, long var2, float var4) {
        this.A = false;
        this.R = false;
        for (StudioModuleFrame var9 : this.J) {
            var9.y(var1, var4, 24663603379738L);
}
}
    public void s(char var1, int var2) {
        for (StudioModuleFrame var4 : this.J) {
            var4.t(var1, var2);
}
}
    public Category S() {
        return this.s;
}
    private boolean g(float var1, float var2, float var3, float var4, float var5, float var6) {
        return var1 >= var3 && var1 <= var3 + var5 && var2 >= var4 && var2 <= var4 + var6;
}
    public void m(long var1, char var3) {
        long var4 = (0x32FB45B00000L | (long)var3 << 48 >>> 48) ^ a;
        long var6 = var4 ^ 0x6C24286048ACL;
        this.A = false;
        this.R = false;
        for (StudioModuleFrame var9 : this.J) {
            this.N.W(var9.E().b(), var9.t());
            var9.Q(var6);
}
}
    private float C(float var1) {
        return this.v(var1) * this.h.b(var1);
}
    public StudioNotification(StudioClickGuiScreen var1, Category var2, long var3, StudioFrameState var5) {
        long var8 = 92528980060864L;
        this.J = new ArrayList<StudioModuleFrame>();
        this.h = new Animation(0.0f);
        this.F = new Animation(0.0f);
        this.I = var1;
        this.s = var2;
        this.N = var5;
        this.d = CategoryPalette.o(var2);
        this.h.U(var5.S ? 1.0f : 0.0f);
        for (Module var11 : ModuleManager.S) {
            if (var11.f() != var2) continue;
            this.J.add(new StudioModuleFrame(var1, var11, var8, var5.v(var11.b())));
}
}
    public float m(float var1) {
        return this.N.h + 16.0f + this.C(var1);
}
}