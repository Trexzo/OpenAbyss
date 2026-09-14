/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.raven;

import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ColorSetting;
import Abyss.ui.raven.AbstractRavenSettingRow;
import Abyss.ui.raven.RavenModuleRow;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import org.lwjgl.opengl.GL11;

public class RavenColorRow
extends AbstractRavenSettingRow {
    private ColorSetting Q;
    private Module J;
    private int G;
    private int V;
    private static long d = 34400892697301L;
    private boolean c = false;
    private int l;

    @Override
    public void r(char var1, int var2, int var3, int var4, long var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (this.O.k && var4 == 0) {
            int var11 = this.O.O.t();
            int var12 = this.V + 4;
            int var13 = this.l + 11;
            int var15 = var11 - 8;
            if (var2 >= var12 && var2 <= var12 + var15 && var3 >= var13 && var3 <= var13 + 4) {
                this.c = true;
                this.V(0L, var2, var3);
}
}
}
    private static int Q(String var0) {
        try {
            return Integer.parseInt(var0.replace("#", ""), 16) & 0xFFFFFF;
}
        catch (Exception var4) {
            return 0xFFFFFF;
}
}
    private static int a(int var0, int var3, float var4) {
        float var5 = (float)(var0 >> 16 & 0xFF) / 255.0f;
        float var6 = (float)(var0 >> 8 & 0xFF) / 255.0f;
        float var7 = (float)(var0 & 0xFF) / 255.0f;
        float var8 = (float)(var3 >> 16 & 0xFF) / 255.0f;
        float var9 = (float)(var3 >> 8 & 0xFF) / 255.0f;
        float var10 = (float)(var3 & 0xFF) / 255.0f;
        int var11 = Math.round((var5 * (1.0f - var4) + var8 * var4) * 255.0f);
        int var12 = Math.round((var6 * (1.0f - var4) + var9 * var4) * 255.0f);
        int var13 = Math.round((var7 * (1.0f - var4) + var10 * var4) * 255.0f);
        return var11 << 16 | var12 << 8 | var13;
}
    @Override
    public void U(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var3 = var1 ^ 0xAA3FD542704L;
        long var9 = var1 ^ 0x3E6AD4F2F4A9L;
        int var11 = (int)((var1 ^ 0x34F9861E47ACL) >>> 56);
        long var12 = (var1 ^ 0x34F9861E47ACL) << 8 >>> 8;
        long var14 = var1 ^ 0x59D79D969530L;
        if (this.O.k) {
            this.V = this.O.O.X();
            this.l = this.O.O.T() + this.G;
            int var18 = this.O.O.t();
            int var19 = this.V + 4;
            int var20 = this.l + 11;
            int var22 = var18 - 8;
            GL11.glPushMatrix();
            GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
            this.C(var9).v(this.Q.e((byte)var11, this.J, var12) + ": #" + RavenColorRow.W(this.Q.Q()), (this.V + 4) * 2, (this.l + 3) * 2, this.P, var3, true);
            GL11.glPopMatrix();
            GL11.glPushAttrib((int)57344);
            boolean var23 = GL11.glIsEnabled((int)3042);
            boolean var24 = GL11.glIsEnabled((int)3553);
            int var25 = GL11.glGetInteger((int)2900);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glShadeModel((int)7425);
            if (var24) {
                GL11.glDisable((int)3553);
}
            GL11.glBegin((int)7);
            int var26 = Math.max(128, var22);
            for (int var27 = 0; var27 < var26; ++var27) {
                float var28 = (float)var27 / (float)var26;
                float var29 = (float)(var27 + 1) / (float)var26;
                int var30 = Color.HSBtoRGB(var28, 1.0f, 1.0f);
                int var31 = Color.HSBtoRGB(var29, 1.0f, 1.0f);
                RavenColorRow.G(var30);
                GL11.glVertex2f((float)((float)var19 + var28 * (float)var22), (float)var20);
                GL11.glVertex2f((float)((float)var19 + var28 * (float)var22), (float)(var20 + 4));
                RavenColorRow.G(var31);
                GL11.glVertex2f((float)((float)var19 + var29 * (float)var22), (float)(var20 + 4));
                GL11.glVertex2f((float)((float)var19 + var29 * (float)var22), (float)var20);
}
            GL11.glEnd();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (var24) {
                GL11.glEnable((int)3553);
}
            GL11.glShadeModel((int)var25);
            if (!var23) {
                GL11.glDisable((int)3042);
}
            GL11.glPopAttrib();
            int var32 = RavenColorRow.Q(this.Q.Q());
            float[] var33 = Color.RGBtoHSB(var32 >> 16 & 0xFF, var32 >> 8 & 0xFF, var32 & 0xFF, null);
            int var34 = var19 + Math.round(var33[0] * (float)(var22 - 1));
            int var35 = RavenColorRow.a(var32, -1, 0.35f);
            RenderUtil.j(var34 - 3, var20 - 2, var34 + 3, var20 + 4 + 2, 2.0f, var14, 0xFF000000 | var35);
            RenderUtil.j(var34 - 2, var20 - 1, var34 + 2, var20 + 4 + 1, 2.0f, var14, 0xFF000000 | var32);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
}
    @Override
    public void f(int var1, int var2, int var3, int var4, short var5, int var6) {
        this.c = false;
}
    private void f(ColorSetting var1, RavenModuleRow var2, int var3) {
        this.Q = var1;
        this.J = var2.R;
        this.G = var3;
}
    @Override
    public void W(long var1) {
        this.c = false;
}
    private static String W(String var0) {
        var0 = var0 == null ? "" : var0.trim();
        return var0.startsWith("#") ? var0.substring(1).toUpperCase() : var0.toUpperCase();
}
    @Override
    public Setting f() {
        return this.Q;
}
    private static void G(int var0) {
        GL11.glColor3f((float)((float)(var0 >> 16 & 0xFF) / 255.0f), (float)((float)(var0 >> 8 & 0xFF) / 255.0f), (float)((float)(var0 & 0xFF) / 255.0f));
}
    public RavenColorRow(ColorSetting var1, RavenModuleRow var2, int var3, long var4) {
        super(var2);
        this.f(var1, var2, var3);
}
    @Override
    public void i(int var1, int var2, int var3, byte var4) {
        this.G = var1;
}
    @Override
    public void V(long var1, int var3, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (this.O.k && this.c) {
            int var7 = this.O.O.t();
            int var8 = this.V + 4;
            int var9 = var7 - 8;
            int var10 = Math.max(var8, Math.min(var3, var8 + var9 - 1));
            float var11 = (float)(var10 - var8) / (float)(var9 - 1);
            int var12 = Color.HSBtoRGB(var11, 1.0f, 1.0f) & 0xFFFFFF;
            this.Q.e(RavenColorRow.L(var12));
}
}
    @Override
    public int E(long var1) {
        return 16;
}
    private static String L(int var0) {
        return String.format("%06X", var0 & 0xFFFFFF);
}
}