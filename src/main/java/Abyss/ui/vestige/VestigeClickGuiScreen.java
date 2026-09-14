/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.ChatAllowedCharacters
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.vestige;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Language;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.ui.vestige.ClickGuiPanel;
import Abyss.ui.vestige.VestigeSelectedSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import Abyss.util.Sneaky;
import Abyss.util.render.CustomFont;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class VestigeClickGuiScreen
extends GuiScreen {
    private static int c;
    private static Color k;
        private static Map<Module, Boolean> p;
    private static long[] m;
    public final int Y;
    
    private int R;
    private static int u;
    private Module o;
    private static Map i;
        private VestigeSelectedSetting s;
        private static Color q;
    private long F;
    private static Map<Category, List<Module>> E;
    private static Map<Category, ClickGuiPanel> L;
    private static Color w;
    private static int j;
    private String B = "";
    private static int Q;
            public final int D;
    public final int M;
    private static Color h;
    private static Color t;
    public final int O;
    private static Object[] v;
    private static Color Z;
    private TextSetting K;
    private boolean I;

    public VestigeClickGuiScreen() {
        this.D = 20;
        this.M = 18;
        this.O = 14;
        this.Y = 0;
        VestigeClickGuiScreen.M();
}
    protected void func_73869_a(char var1, int var2) {
        if (var2 == 1) {
            if (this.K != null) {
                this.Q();
            } else {
                this.field_146297_k.func_147108_a(null);
                if (this.field_146297_k.field_71462_r == null) {
                    this.field_146297_k.func_71381_h();
}
                this.j(1310, 48485, '\ua408');
}
        } else if (this.K != null) {
            this.A(var1, var2);
        } else {
            this.Y(101849160492560L, var2);
}
}
    private Color z(Color var1, Color var2, double var3) {
        if (var3 > 1.0) {
            double var5 = var3 % 1.0;
            int var7 = (int)var3;
            var3 = var7 % 2 == 0 ? var5 : 1.0 - var5;
}
        double var10 = 1.0 - var3;
        int var11 = (int)((double)var1.getRed() * var10 + (double)var2.getRed() * var3);
        int var8 = (int)((double)var1.getGreen() * var10 + (double)var2.getGreen() * var3);
        int var9 = (int)((double)var1.getBlue() * var10 + (double)var2.getBlue() * var3);
        return new Color(var11, var8, var9);
}
    private int I(Module var1, long var2, int var4, int var5, int var6, int var7, int var8, int var9) throws Throwable {
        List<Setting> var14 = this.z(var1);
        int var15 = 0;
        for (Setting var17 : var14) {
            int var18 = var4 + 1;
            int var19 = var5 - 1;
            int var20 = var6;
            int var21 = var6 + 14;
            if (var15 == 0) {
                var20 = var6 += 0;
                var21 = var6 + 14;
}
            if (this.d(var7, var8, var18, var20, var19, var21)) {
                this.g(var1, var17, var18, var19, var7, var9, 4230632914209L);
}
            var6 += 14;
            ++var15;
}
        int var23 = var4 + 1;
        int var25 = var6;
        int var24 = var5 - 1;
        int var26 = var6 + 14;
        if (this.d(var7, var8, var23, var25, var24, var26)) {
            this.X(var1, 117129471363386L, var9);
}
        return var6 + 14;
}
    private void U(Module var1, int var2, int var3, int var4, int var5, byte var6, int var7, int var8) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var9 = ((long)var6 << 56 | (long)var7 << 32 >>> 8 | (long)var8 << 40 >>> 40) ^ a;
        long var11 = var9 ^ 0x654078369388L;
        long var13 = var9 ^ 0x641FE236550BL;
        long var15 = var9 ^ 0x4B0D1A194CE2L;
        int var17 = (int)((var9 ^ 0x3B1471A59B39L) >>> 32);
        int var18 = (int)((var9 ^ 0x3B1471A59B39L) << 32 >>> 48);
        int var19 = (int)((var9 ^ 0x3B1471A59B39L) << 48 >>> 48);
        long var20 = var9 ^ 0x15A50EF3EA24L;
        CustomFont var22 = this.s(var15);
        if (var1.o()) {
            for (int var23 = var2; var23 < var4; ++var23) {
                Gui.func_73734_a((int)var23, (int)var3, (int)(var23 + 1), (int)var5, (int)this.S(var20, var23));
}
        } else {
            Gui.func_73734_a((int)var2, (int)var3, (int)var4, (int)var5, (int)w.getRGB());
}
        if (this.B(var1)) {
            double var28 = var4 - 11;
            double var25 = var3 + 7;
            int var27 = new Color(225, 225, 225).getRGB();
            Gui.func_73734_a((int)((int)var28), (int)((int)var25), (int)(var4 - 5), (int)((int)(var25 + 1.0)), (int)var27);
            Gui.func_73734_a((int)((int)(var28 + 1.0)), (int)((int)(var25 + 1.0)), (int)(var4 - 6), (int)((int)(var25 + 2.0)), (int)var27);
            Gui.func_73734_a((int)((int)(var28 + 2.0)), (int)((int)(var25 + 2.0)), (int)(var4 - 7), (int)((int)(var25 + 3.0)), (int)var27);
}
        var22.T(var11, this.a(var1.Q(var17, (char)var18, (char)var19), var4 - var2 - 18, var13), var2 + 4, var3 + 5, Z.getRGB());
}
    public boolean func_73868_f() {
        return false;
}
    private void g(Module var1, Setting var2, int var3, int var4, int var5, int var6, long var7) throws Throwable {
        if (var2 instanceof BooleanSetting) {
            ((BooleanSetting)var2).W(112370683098682L);
        } else if (var2 instanceof ModeSetting) {
            ModeSetting var19 = (ModeSetting)var2;
            if (var6 == 1) {
                var19.X();
            } else {
                var19.w(53199746843302L);
}
        } else if (var2 instanceof NumberSetting) {
            this.s = new VestigeSelectedSetting(var2, null);
            this.a((NumberSetting)var2, 121452147764704L, var3, var4, var5);
        } else if (var2 instanceof PercentageSetting) {
            this.s = new VestigeSelectedSetting(var2, null);
            this.C((PercentageSetting)var2, var3, 65480692878177L, var4, var5);
        } else if (var2 instanceof ColorSetting) {
            this.s = new VestigeSelectedSetting(var2, null);
            this.U((ColorSetting)var2, var3, var4, var5, 92078714498853L);
        } else if (var2 instanceof TextSetting) {
            this.K = (TextSetting)var2;
            this.B = this.K.X();
}
}
    private void k(Module var1, NumberSetting var2, int var3, int var4, int var5, int var6, int var7, short var8, char var9, int var10) {
        long var11 = ((long)var8 << 48 | (long)var9 << 48 >>> 16 | (long)var10 << 32 >>> 32) ^ a;
        long var13 = var11 ^ 0x117567A689DAL;
        int var15 = (int)((var11 ^ 0x5DAFD0BF470EL) >>> 56);
        long var16 = (var11 ^ 0x5DAFD0BF470EL) << 8 >>> 8;
        long var18 = var11 ^ 0x5D49AA922452L;
        long var20 = var11 ^ 0x102AFDA64F59L;
        long var22 = var11 ^ 0x3F38058956B0L;
        CustomFont var24 = this.s(var22);
        double var25 = var5 - var3;
        double var27 = (double)var3 + (double)(var2.L() - var2.i()) * var25 / (double)(var2.F() - var2.i());
        Gui.func_73734_a((int)var3, (int)var4, (int)var5, (int)var6, (int)q.getRGB());
        Gui.func_73734_a((int)var3, (int)var4, (int)((int)var27), (int)var6, (int)new Color(25, 25, 25).getRGB());
        String var29 = var2.e((byte)var15, var1, var16) + " : " + var2.L();
        var24.T(var13, this.a(var29, var5 - var3 - 10, var20), var3 + 4, var4 + 3, t.getRGB());
        if (this.F(var2)) {
            this.a(var2, var18, var3, var5, var7);
}
}
    private int H(float var2, long var3) {
        var3 += 40L;
        float var9 = (float)((System.currentTimeMillis() + var3) % (long)((int)(var2 * 1000.0f))) / (var2 * 1000.0f);
        float var10 = var9 * 2.0f;
        if (var10 > 1.0f) {
            var10 = 2.0f - var10;
}
        return this.z(new Color(0, 200, 235), new Color(20, 75, 230), var10).getRGB();
}
    private void N(Module var1, ColorSetting var2, int var3, short var4, int var5, int var6, int var7, int var8, int var9, int var10) {
        long var11 = ((long)var3 << 32 | (long)var4 << 48 >>> 32 | (long)var10 << 48 >>> 48) ^ a;
        long var13 = var11 ^ 0x6087D858CEDAL;
        int var15 = (int)((var11 ^ 0x2C5D6F41000EL) >>> 56);
        long var16 = (var11 ^ 0x2C5D6F41000EL) << 8 >>> 8;
        long var18 = var11 ^ 0x61D842580859L;
        long var20 = var11 ^ 0x117062622197L;
        long var22 = var11 ^ 0x4ECABA7711B0L;
        long var24 = var11 ^ 0x1505A04CF8E4L;
        CustomFont var26 = this.s(var22);
        double var27 = var7 - var5;
        float[] var29 = Color.RGBtoHSB(var2.k(var24) >> 16 & 0xFF, var2.k(var24) >> 8 & 0xFF, var2.k(var24) & 0xFF, null);
        double var30 = (double)var5 + (double)var29[0] * var27;
        Gui.func_73734_a((int)var5, (int)var6, (int)var7, (int)var8, (int)q.getRGB());
        Gui.func_73734_a((int)var5, (int)var6, (int)((int)var30), (int)var8, (int)new Color(25, 25, 25).getRGB());
        Gui.func_73734_a((int)(var7 - 10), (int)(var6 + 2), (int)(var7 - 3), (int)(var8 - 2), (int)var2.k(var24));
        String var32 = var2.e((byte)var15, var1, var16) + " : #" + var2.Q().toUpperCase();
        var26.T(var13, this.a(var32, var7 - var5 - 22, var18), var5 + 4, var6 + 3, t.getRGB());
        if (this.F(var2)) {
            this.U(var2, var5, var7, var9, var20);
}
}
    private void d(Module var1, HeaderSetting var2, int var3, int var4, int var5, int var6, long var7) {
        CustomFont var17 = this.s(13213047758594L);
        Gui.func_73734_a((int)var3, (int)var4, (int)var5, (int)var6, (int)q.getRGB());
        var17.T(37697014677608L, this.a(var2.U(133728681395781L, var1), var5 - var3 - 10, 38580066682603L), var3 + 5, var4 + 3, h.getRGB());
}
    private void U(Module var1, Setting var2, int var3, long var4, int var6, int var7, int var8, int var9) {
        int var14 = 20296;
        int var27 = 2086799432;
        if (var2 instanceof BooleanSetting) {
            this.F(var1, (BooleanSetting)var2, var3, var6, var7, 88893057151773L, var8);
        } else if (var2 instanceof ModeSetting) {
            this.v(var1, (ModeSetting)var2, var3, 185474325321L, var6, var7, var8, (byte)-96);
        } else if (var2 instanceof NumberSetting) {
            this.k(var1, (NumberSetting)var2, var3, var6, var7, var8, var9, (short)0, '\u3e6c', var27);
        } else if (var2 instanceof PercentageSetting) {
            this.I(var1, (PercentageSetting)var2, var3, var6, var7, var8, var9, 7131923145670L);
        } else if (var2 instanceof ColorSetting) {
            this.N(var1, (ColorSetting)var2, 20382, (short)-15460, var3, var6, var7, var8, var9, var14);
        } else if (var2 instanceof HeaderSetting) {
            this.d(var1, (HeaderSetting)var2, var3, var6, var7, var8, 91641500148269L);
        } else if (var2 instanceof TextSetting) {
            this.X((byte)0, var1, (TextSetting)var2, var3, var6, 87686144156819L, var7, var8);
        } else {
            this.B(var1, var2, var3, var6, var7, 80920452746949L, var8);
}
}
    private void F(Module var1, BooleanSetting var2, int var3, int var4, int var5, long var6, int var8) {
        CustomFont var18 = this.s(13213047758594L);
        Gui.func_73734_a((int)var3, (int)var4, (int)var5, (int)var8, (int)q.getRGB());
        int var19 = var2.c() ? k.getRGB() : t.getRGB();
        var18.T(37697014677608L, this.a(var2.e((byte)0, var1, 121580628905660L), var5 - var3 - 10, 38580066682603L), var3 + 5, var4 + 3, var19);
}
    public void func_73863_a(int var1, int var2, float var3) {
        try {
            long var4 = 17448604102766L;
            VestigeClickGuiScreen.Y(2549667498153L);
            VestigeClickGuiScreen.M();
            this.B(2806383397540L);
            this.n(126550276172091L);
            double var19 = (double)ClickGUI.scale.L() * 0.7;
            int var21 = (int)((double)var1 / var19);
            int var22 = (int)((double)var2 / var19);
            if (this.R != 0) {
                int var23 = (int)((double)this.R * 0.15);
                if (var23 == 0) {
                    this.R = 0;
                } else {
                    Iterator<ClickGuiPanel> iterator = L.values().iterator();
                    while (iterator.hasNext()) {
                        ClickGuiPanel var25;
                        ClickGuiPanel var26 = var25 = iterator.next();
                        ClickGuiPanel.set_E(var26, ClickGuiPanel.get_E(var26) + var23);
}
                    this.R -= var23;
}
}
            for (ClickGuiPanel var40 : L.values()) {
                if (!ClickGuiPanel.get_O(var40)) continue;
                int var42 = var21 - ClickGuiPanel.get_z(var40);
                int var44 = var22 - ClickGuiPanel.get_d(var40);
                if (!ClickGuiPanel.get_w(var40) && Math.abs(var42) <= 3 && Math.abs(var44) <= 3) continue;
                ClickGuiPanel.set_w(var40, true);
                ClickGuiPanel.set_v(var40, ClickGuiPanel.get_e(var40) + var42);
                ClickGuiPanel.set_E(var40, ClickGuiPanel.get_h(var40) + var44);
}
            GL11.glPushMatrix();
            GL11.glScaled((double)var19, (double)var19, (double)1.0);
            for (Category var45 : Category.values()) {
                ClickGuiPanel var27 = L.get((Object)var45);
                int var28 = ClickGuiPanel.get_v(var27);
                int var29 = ClickGuiPanel.get_E(var27);
                int var30 = var28 + 116;
                int var31 = var29 + 20;
                this.q(var45, var28, 128967919821462L, var29, var30, var31);
                int var32 = var29 + 20;
                boolean var33 = true;
                if (!ClickGuiPanel.get_S(var27)) continue;
                for (Module var35 : this.O(var45)) {
                    int var36 = var32;
                    int var37 = var32 + 18;
                    this.U(var35, var28, var36, var30, var37, (byte)0, 4872547, 15864346);
                    if (var33 && var35.o()) {
                        this.func_73733_a(var28, var36, var30, var36 + 3, 0x60000000, 0x5000000);
}
                    var32 += 18;
                    if (this.O(var35)) {
                        var32 = this.g(var35, var28, var30, 115844804322685L, var32, var21);
}
                    var33 = false;
}
}
            GL11.glPopMatrix();
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private List<Module> O(Category var1) {
        List<Module> var2 = E.get((Object)var1);
        return var2 == null ? Collections.emptyList() : var2;
}
    private void J(Module var1, int var2, short var3, long var4) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var6 = ((long)var3 << 48 | 0x117EFDDE3AB4L) ^ a;
        int var8 = (int)((var6 ^ 0x6293F0DDDE65L) >>> 48);
        long var9 = (var6 ^ 0x6293F0DDDE65L) << 16 >>> 16;
        long var11 = var6 ^ 0xEF7B0A2A184L;
        long var13 = var6 ^ 0x5449865417E4L;
        if (var2 == 0) {
            if (this.field_146297_k.field_71439_g != null) {
                var1.u((short)var8, var9);
            } else {
                var1.I(var11, !var1.o());
                Modules.c(var13);
}
        } else if (var2 == 1 && this.B(var1)) {
            p.put(var1, !this.O(var1));
}
}
    private String q(Category var1, long var2) {
        return var1.x(12139, 2577, (short)-18145).replace('_', ' ');
}
    private void Y(long var1, int var3) {
        if (this.o != null) {
            this.o.z(118276941480361L, var3);
            this.o = null;
            Modules.c(79608920009898L);
}
}
    private void a(NumberSetting var1, long var2, int var4, int var5, int var6) {
        var2 = a ^ var2;
        int var7 = (int)((var2 ^ 0x4CE2C6246847L) >>> 56);
        long var8 = (var2 ^ 0x4CE2C6246847L) << 8 >>> 8;
        float var10 = var5 - var4;
        float var11 = MathUtil.q(var6 - var4, 0.0f, var10);
        float var12 = var11 / var10;
        var1.o((byte)var7, var8, var12 * (var1.F() - var1.i()) + var1.i());
}
    private void C(PercentageSetting var1, int var2, long var3, int var5, int var6) {
        double var10 = var5 - var2;
        double var12 = MathUtil.R(var6 - var2, 0.0, var10);
        int var14 = (int)Math.round(var12 / var10 * 100.0);
        var1.b(2856, 2304136789L, var14);
}
    private boolean B(Module var1) {
        return true;
}
    private void v(Module var1, ModeSetting var2, int var3, long var4, int var6, int var7, int var8, byte var9) {
        long var10 = (0x2B2F219B4900L | (long)var9 << 56 >>> 56) ^ a;
        int var12 = (int)((var10 ^ 0x418CF4B1C356L) >>> 48);
        int var13 = (int)((var10 ^ 0x418CF4B1C356L) << 16 >>> 48);
        int var14 = (int)((var10 ^ 0x418CF4B1C356L) << 32 >>> 32);
        long var15 = var10 ^ 0x4363A5FC832L;
        int var17 = (int)((var10 ^ 0x48EC8D4606E6L) >>> 56);
        long var18 = (var10 ^ 0x48EC8D4606E6L) << 8 >>> 8;
        long var20 = var10 ^ 0x569A05F0EB1L;
        long var22 = var10 ^ 0x2A7B58701758L;
        CustomFont var24 = this.s(var22);
        Gui.func_73734_a((int)var3, (int)var6, (int)var7, (int)var8, (int)q.getRGB());
        String var25 = var2.e((byte)var17, var1, var18) + " : " + var2.a((char)var12, (short)var13, var1, var14);
        var24.T(var15, this.a(var25, var7 - var3 - 10, var20), var3 + 5, var6 + 3, t.getRGB());
}
    private List<Setting> z(Module var1) {
        ArrayList<Setting> var2 = new ArrayList<Setting>();
        for (Setting var4 : var1.w()) {
            if (!(var4 instanceof BooleanSetting) && !(var4 instanceof ModeSetting) && !(var4 instanceof NumberSetting) && !(var4 instanceof PercentageSetting) && !(var4 instanceof ColorSetting) && !(var4 instanceof HeaderSetting) && !(var4 instanceof TextSetting)) continue;
            var2.add(var4);
}
        return HeaderSetting.prune(var2);
}
    public void func_146281_b() {
        super.func_146281_b();
        this.j(1310, 48485, '\ua408');
}
    private CustomFont s(long var1) {
        return Font.m(23209167808904L);
}
    private void B(Module var1, Setting var2, int var3, int var4, int var5, long var6, int var8) {
        CustomFont var18 = this.s(13213047758594L);
        Gui.func_73734_a((int)var3, (int)var4, (int)var5, (int)var8, (int)q.getRGB());
        var18.T(37697014677608L, this.a(var2.e((byte)0, var1, 121580628905660L), var5 - var3 - 10, 38580066682603L), var3 + 5, var4 + 3, t.getRGB());
}
    public void func_73866_w_() {
        super.func_73866_w_();
        VestigeClickGuiScreen.Y(2549667498153L);
        VestigeClickGuiScreen.M();
        this.I = false;
        this.s = null;
        this.R = 0;
        this.F = System.currentTimeMillis();
}
    private void X(byte var1, Module var2, TextSetting var3, int var4, int var5, long var6, int var8, int var9) {
        long var10 = ((long)var1 << 56 | 0x4FC005796493L) ^ a;
        long var12 = var10 ^ 0x60D91EBDE501L;
        int var14 = (int)((var10 ^ 0x2C03A9A42BD5L) >>> 56);
        long var15 = (var10 ^ 0x2C03A9A42BD5L) << 8 >>> 8;
        long var17 = var10 ^ 0x618684BD2382L;
        long var19 = var10 ^ 0x4E947C923A6BL;
        CustomFont var21 = this.s(var19);
        Gui.func_73734_a((int)var4, (int)var5, (int)var8, (int)var9, (int)q.getRGB());
        String var22 = this.K == var3 ? this.B + "_" : var3.X();
        String var23 = var3.e((byte)var14, var2, var15) + " : " + var22;
        var21.T(var12, this.a(var23, var8 - var4 - 10, var17), var4 + 5, var5 + 3, t.getRGB());
}
    private int j(Module var1, int var2, int var3, int var4, int var5, short var6, int var7) {
        long var8 = ((long)var4 << 32 | (long)var5 << 48 >>> 32 | (long)var6 << 48 >>> 48) ^ a;
        long var10 = var8 ^ 0x77D72F15F371L;
        long var12 = (var8 ^ 0x3ABAA2DB4882L) >>> 16;
        int var14 = (int)((var8 ^ 0x3ABAA2DB4882L) << 48 >>> 48);
        long var15 = var8 ^ 0x7688B51535F2L;
        long var17 = var8 ^ 0x599A4D3A2C1BL;
        long var19 = var8 ^ 0x193E18BEF17AL;
        CustomFont var21 = this.s(var17);
        int var22 = var3 + 14;
        Gui.func_73734_a((int)var2, (int)var3, (int)var7, (int)var22, (int)q.getRGB());
        String var23 = var1.h() == 0 ? "NONE" : KeyBindUtil.p(var12, (char)var14, var1.h());
        String var24 = this.o == var1 ? "Bind : " + Language.z("clickgui.bind.press", var19) : "Bind : " + var23;
        var21.T(var10, this.a(var24, var7 - var2 - 10, var15), var2 + 5, var3 + 3, t.getRGB());
        return var22;
}
    public static void q(int var0, char var1, short var2) {
        long var3 = ((long)var0 << 32 | (long)var1 << 48 >>> 32 | (long)var2 << 48 >>> 48) ^ a;
        int var5 = (int)((var3 ^ 0x6933EB5BEBD3L) >>> 56);
        long var6 = (var3 ^ 0x6933EB5BEBD3L) << 8 >>> 8;
        long var8 = var3 ^ 0x14DF6987FAB9L;
        L.clear();
        p.clear();
        Minecraft var10 = MinecraftRef.c((byte)var5, var6);
        ScaledResolution var11 = new ScaledResolution(var10);
        int var12 = Math.max(1, Math.max(1, var11.func_78326_a() - 40) / 126);
        Category[] var13 = Category.values();
        for (int var14 = 0; var14 < var13.length; ++var14) {
            int var15 = var14 % var12;
            int var16 = var14 / var12;
            int var17 = 40 + var15 * 126;
            int var18 = 50 + var16 * 140;
            L.put(var13[var14], new ClickGuiPanel(var17, var8, var18, false, null));
}
}
    private boolean d(int var1, int var2, int var3, int var4, int var5, int var6) {
        return var1 >= var3 && var1 <= var5 && var2 >= var4 && var2 <= var6;
}
    public void func_146274_d() {
        super.func_146274_d();
        int var5 = Mouse.getDWheel();
        if (var5 != 0) {
            this.x(var5);
}
}
    private void A(char var3, int var4) {
        if (this.K != null) {
            if (var4 == 28 || var4 == 156) {
                this.Q();
            } else if (var4 == 14) {
                if (!this.B.isEmpty()) {
                    this.B = this.B.substring(0, this.B.length() - 1);
}
            } else if (GuiScreen.func_146271_m() && var4 == 47) {
                String var5 = GuiScreen.func_146277_j();
                if (var5 != null) {
                    this.B = this.B + var5;
}
            } else if (ChatAllowedCharacters.func_71566_a((char)var3)) {
                this.B = this.B + var3;
}
}
}
    private static void M() {
        if (E.isEmpty()) {
            for (Category var3 : Category.values()) {
                E.put(var3, new ArrayList());
}
            for (Module var5 : ModuleManager.S) {
                E.computeIfAbsent(var5.f(), var0 -> new ArrayList()).add(var5);
}
}
}
    private boolean F(Setting var1) {
        return this.s != null && VestigeSelectedSetting.R(this.s) == var1 && this.I;
}
    private int g(Module var1, int var2, int var3, long var4, int var6, int var7) {
        List<Setting> var13 = this.z(var1);
        int var14 = 0;
        for (Setting var16 : var13) {
            int var17 = var2 + 1;
            int var18 = var3 - 1;
            int var19 = var6;
            int var20 = var6 + 14;
            if (var14 == 0) {
                Gui.func_73734_a((int)var17, (int)var19, (int)var18, (int)(var19 + 0), (int)q.getRGB());
                var19 = var6 += 0;
                var20 = var6 + 14;
}
            this.U(var1, var16, var17, 122252038458843L, var19, var18, var20, var7);
            var6 += 14;
            ++var14;
}
        return this.j(var1, var2 + 1, var6, 22734, 13521, (short)29411, var3 - 1);
}
    private void Q() {
        if (this.K != null) {
            this.K.O(this.B);
            this.K = null;
            this.B = "";
}
}
    private void U(ColorSetting var1, int var2, int var3, int var4, long var5) {
        long var7 = var5 ^ 0x475C22ED973L;
        double var9 = var3 - var2;
        double var11 = MathUtil.R(var4 - var2, 0.0, var9);
        float var13 = (float)(var11 / var9);
        Color var14 = new Color(var1.k(var7));
        float[] var15 = Color.RGBtoHSB(var14.getRed(), var14.getGreen(), var14.getBlue(), null);
        float var16 = var15[1] <= 0.05f ? 0.9f : var15[1];
        float var17 = var15[2] <= 0.05f ? 0.95f : var15[2];
        int var18 = Color.HSBtoRGB(var13, var16, var17) & 0xFFFFFF;
        var1.e(String.format("%06X", var18));
}
    protected void func_73864_a(int var1, int var2, int var3) {
        try {
            long var4 = 41053911563077L;
            super.func_73864_a(var1, var2, var3);
            if (this.o != null && var3 > 2) {
                this.o.z(118276941480361L, KeyBindUtil.w('\u0000', var3, 132797583844084L));
                this.o = null;
                Modules.c(79608920009898L);
            } else {
                double var18 = ClickGUI.scale.L();
                int var20 = (int)((double)var1 / var18);
                int var21 = (int)((double)var2 / var18);
                this.Q();
                this.I = true;
                for (Category var25 : Category.values()) {
                    int var30;
                    int var29;
                    int var28;
                    ClickGuiPanel var26 = L.get((Object)var25);
                    int var27 = ClickGuiPanel.get_v(var26);
                    if (this.d(var20, var21, var27, var28 = ClickGuiPanel.get_E(var26), var29 = var27 + 116, var30 = var28 + 20)) {
                        this.d(var26, var20, var21, var3);
                        continue;
}
                    int var31 = var28 + 20;
                    if (!ClickGuiPanel.get_S(var26)) continue;
                    for (Module var33 : this.O(var25)) {
                        int var34 = var31;
                        int var35 = var31 + 18;
                        if (this.d(var20, var21, var27, var34, var29, var35)) {
                            this.J(var33, var3, (short)0, 19237122751156L);
}
                        var31 += 18;
                        if (!this.O(var33)) continue;
                        var31 = this.I(var33, 77463035357270L, var27, var29, var31, var20, var21, var3);
}
}
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private void d(ClickGuiPanel var1, int var2, int var3, int var4) {
        if (var4 == 0 || var4 == 1) {
            ClickGuiPanel.set_O(var1, true);
            ClickGuiPanel.set_w(var1, false);
            ClickGuiPanel.set_Q(var1, var4);
            ClickGuiPanel.set_z(var1, var2);
            ClickGuiPanel.set_d(var1, var3);
            ClickGuiPanel.set_e(var1, ClickGuiPanel.get_v(var1));
            ClickGuiPanel.set_h(var1, ClickGuiPanel.get_E(var1));
}
}
    private void B(long var1) {
        this.K(139064014055973L, this.field_146297_k.field_71474_y.field_74351_w);
        this.K(139064014055973L, this.field_146297_k.field_71474_y.field_74370_x);
        this.K(139064014055973L, this.field_146297_k.field_71474_y.field_74366_z);
        this.K(139064014055973L, this.field_146297_k.field_71474_y.field_74368_y);
        this.K(139064014055973L, this.field_146297_k.field_71474_y.field_74314_A);
}
    protected void func_146286_b(int var1, int var2, int var3) {
        super.func_146286_b(var1, var2, var3);
        this.I = false;
        this.s = null;
        for (ClickGuiPanel var7 : L.values()) {
            if (!ClickGuiPanel.get_O(var7) || ClickGuiPanel.get_Q(var7) != var3) continue;
            if (var3 == 1 && !ClickGuiPanel.get_w(var7)) {
                ClickGuiPanel.set_S(var7, !ClickGuiPanel.get_S(var7));
}
            ClickGuiPanel.set_O(var7, false);
            ClickGuiPanel.set_w(var7, false);
            ClickGuiPanel.set_Q(var7, -1);
}
}
    private static void Y(long var0) {
        if (L.size() != Category.values().length) {
            VestigeClickGuiScreen.q(11538, '\u5677', (short)25573);
}
}
    private void I(Module var1, PercentageSetting var2, int var3, int var4, int var5, int var6, int var7, long var8) {
        CustomFont var21 = this.s(13213047758594L);
        double var22 = var5 - var3;
        double var24 = (double)var3 + (double)var2.k() * var22 / 100.0;
        Gui.func_73734_a((int)var3, (int)var4, (int)var5, (int)var6, (int)q.getRGB());
        Gui.func_73734_a((int)var3, (int)var4, (int)((int)var24), (int)var6, (int)new Color(25, 25, 25).getRGB());
        String var26 = var2.e((byte)0, var1, 121580628905660L) + " : " + var2.k() + "%";
        var21.T(37697014677608L, this.a(var26, var5 - var3 - 10, 38580066682603L), var3 + 4, var4 + 3, t.getRGB());
        if (this.F(var2)) {
            this.C(var2, var3, 65480692878177L, var5, var7);
}
}
    private void X(Module var1, long var2, int var4) {
        if (this.o == null) {
            this.o = var1;
        } else {
            this.o = null;
            if (var4 == 2) {
                var1.z(118276941480361L, 0);
                Modules.c(79608920009898L);
}
}
}
    private void K(long var1, KeyBinding var3) {
        KeyBindUtil.A(82009306480869L, var3.func_151463_i(), GameSettings.func_100015_a((KeyBinding)var3));
}
    private int S(long var1, int var3) {
        int var6 = -252851571;
        return this.H(4.0f, (long)(-var3) * 3L);
}
    private void n(long var1) {
        if (this.field_146297_k.field_71439_g == null) {
            this.F = System.currentTimeMillis();
        } else {
            long var5 = System.currentTimeMillis();
            long var7 = Math.max(1L, var5 - this.F);
            this.F = var5;
            float var9 = 0.15f * (float)var7;
            if (Keyboard.isKeyDown((int)205)) {
                RotationManager.r(RotationManager.p() + var9);
}
            if (Keyboard.isKeyDown((int)203)) {
                RotationManager.r(RotationManager.p() - var9);
}
            if (Keyboard.isKeyDown((int)200)) {
                RotationManager.v(74908232914960L, RotationManager.s() - var9);
}
            if (Keyboard.isKeyDown((int)208)) {
                RotationManager.v(74908232914960L, RotationManager.s() + var9);
}
}
}
    private void j(int var1, int var2, char var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a;
        long var6 = var4 ^ 0x4029C6EF8958L;
        this.o = null;
        this.I = false;
        this.s = null;
        this.Q();
        Modules.c(var6);
}
    private String a(String var1, int var2, long var3) {
        var3 = a ^ var3;
        long var5 = var3 ^ 0x1094A7D9D80L;
        long var7 = var3 ^ 0x2242E6C0D813L;
        if (var1 == null) {
            return "";
}
        CustomFont var9 = this.s(var7);
        if (var9.R(var1, var5) <= (float)var2) {
            return var1;
}
        String var10 = "...";
        String var11 = var1;
        while (!var11.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!(var9.R(stringBuilder.append(var11).append(var10).toString(), var5) > (float)var2)) break;
            var11 = var11.substring(0, var11.length() - 1);
}
        return var11 + var10;
}
    private void x(int var1) {
        if (var1 > 0) {
            this.R += 30;
        } else if (var1 < 0) {
            this.R -= 30;
}
}
    private void q(Category var1, int var2, long var3, int var5, int var6, int var7) {
        CustomFont var16 = this.s(13213047758594L);
        for (int var17 = var2; var17 < var6; ++var17) {
            Gui.func_73734_a((int)var17, (int)var5, (int)(var17 + 1), (int)var7, (int)this.S(90900632844740L, var17));
}
        var16.T(37697014677608L, this.q(var1, 95043846217018L), var2 + 5, var5 + 6, Z.getRGB());
}
    private boolean O(Module var1) {
        return p.getOrDefault(var1, false);
}
    static {
        Z = new Color(240, 240, 240);
        t = new Color(240, 240, 240);
        q = new Color(50, 50, 50);
        w = new Color(42, 42, 42);
        k = new Color(20, 210, 20);
        h = new Color(180, 180, 180);
        L = new EnumMap<Category, ClickGuiPanel>(Category.class);
        E = new EnumMap<Category, List<Module>>(Category.class);
        p = new HashMap<Module, Boolean>();
}
}