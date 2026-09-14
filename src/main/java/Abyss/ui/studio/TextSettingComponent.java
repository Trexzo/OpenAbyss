/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.ChatAllowedCharacters
 */
package Abyss.ui.studio;

import Abyss.setting.settings.TextSetting;
import Abyss.ui.studio.AbstractSettingComponent;
import Abyss.ui.studio.CategoryPalette;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.studio.StudioModuleFrame;
import Abyss.util.Animation;
import Abyss.util.render.CustomFont;
import Abyss.util.render.FontUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;

public class TextSettingComponent
extends AbstractSettingComponent<TextSetting> {
    private final Animation s;
    private boolean m;
    private String K;
    private static long @Override
    public boolean V(long var1, float var3, float var4, int var5) {
        long var6 = var1 ^ 0x2855EFBD8476L;
        float var8 = this.n + 7.0f;
        float var9 = this.J + 11.2f;
        float var10 = this.C - 14.0f;
        if (var5 == 0 && this.G(var3, var4, var8, var9, var10, 6.6f)) {
            this.M.J(var6, this);
            return true;
}
        return false;
}
    public TextSettingComponent(StudioClickGuiScreen var1, StudioModuleFrame var2, long var3, TextSetting var5) {
        super(var1, var2, var5, a ^ var3 ^ 0x2C7D813FC686L);
        var3 = a ^ var3;
        this.s = new Animation(0.0f);
        this.K = "";
}
    public void h(int var1, char var2, int var3, char var4, short var5) {
        long var6 = ((long)var1 << 32 | (long)var4 << 48 >>> 32 | (long)var5 << 48 >>> 48) ^ a;
        long var10 = var6 ^ 0x53B6D8DD9D1L;
        if (this.m) {
            if (var3 == 1) {
                this.U(0L, true);
                this.M.n(this, var10);
            } else if (var3 == 28 || var3 == 156) {
                this.U(0L, true);
                this.M.n(this, var10);
            } else if (var3 == 14) {
                if (!this.K.isEmpty()) {
                    this.K = this.K.substring(0, this.K.length() - 1);
}
            } else if (GuiScreen.func_146271_m() && var3 == 47) {
                String var12 = GuiScreen.func_146277_j();
                if (var12 != null) {
                    this.K = this.K + var12;
}
            } else if (ChatAllowedCharacters.func_71566_a((char)var2)) {
                this.K = this.K + var2;
}
}
}
    public boolean V() {
        return this.m;
}
    @Override
    public float O() {
        return 20.8f;
}
    @Override
    public void k(long var1) {
        this.U(0L, true);
}
    public void c(long var1) {
        this.m = true;
        this.K = ((TextSetting)this.O).X();
}
    public void U(long var1, boolean var3) {
        if (this.m) {
            if (var3) {
                ((TextSetting)this.O).O(this.K);
}
            this.m = false;
            this.K = "";
}
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
        long var25 = var9 ^ 0x777B06D0B7BAL;
        var10001 = var9 ^ 0x5C1ED806A63BL;
        int var27 = (int)((var9 ^ 0x5C1ED806A63BL) >>> 32);
        int var28 = (int)((var9 ^ 0x5C1ED806A63BL) << 32 >>> 48);
        CustomFont var32 = FontUtil.n(var22, var24);
        this.s.d(this.I(var2, var4) ? 1.0f : 0.0f);
        this.s.y(0.28f, this.M.y());
        RenderUtil.j(this.n, this.J, this.n + this.C, this.J + this.O(), 3.0f, var25, this.F(var8, (char)var16, (short)var17, this.s.b(var5), var6, var18));
        String var10002 = FontUtil.Q(var11, var32, ((TextSetting)this.O).e((byte)var19, this.S, var20), (short)var12, (char)var13, this.C - 12.0f, 0.66f);
        float var10003 = this.n + 6.0f;
        float var10004 = this.J + 2.8f;
        Color var10006 = new Color(239, 244, 251);
        float var30 = var6;
        Color var31 = var10006;
        FontUtil.N(var32, var14, var10002, var10003, var10004, 0.66f, FontUtil.a(var27, var28, var31, var30));
        float var33 = this.n + 7.0f;
        float var34 = this.J + 11.2f;
        float var35 = this.C - 14.0f;
        float var36 = 6.6f;
        float var44 = var33 + var35;
        var10003 = var34 + var36;
        Color var10005 = new Color(22, 28, 39);
        var30 = var6;
        var31 = var10005;
        RenderUtil.j(var33, var34, var44, var10003, 2.3f, var25, FontUtil.a(var27, var28, var31, var30));
        String var37 = this.m ? this.K + (System.currentTimeMillis() / 420L % 2L == 0L ? "_" : "") : ((TextSetting)this.O).X();
        var10002 = FontUtil.Q(var11, var32, var37, (short)var12, (char)var13, var35 - 6.0f, 0.66f);
        var10003 = var33 + 3.0f;
        var10004 = var34 + 1.15f;
        var10006 = new Color(205, 215, 235);
        var30 = var6;
        var31 = var10006;
        FontUtil.N(var32, var14, var10002, var10003, var10004, 0.66f, FontUtil.a(var27, var28, var31, var30));
}
}