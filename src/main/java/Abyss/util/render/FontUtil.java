/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.opengl.GL11
 */
package Abyss.util.render;

import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Language;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class FontUtil {
    private static Map g;
    private static long[] e;
        public static final float x = 0.66f;
    
    
    

    public static float J(FontRenderer var0, float var1) {
        return (float)var0.field_78288_b * var1;
}
    public static void R(long var0) {
        GL11.glDisable((int)3089);
}
    public static void F(FontRenderer var0, String var1, float var2, float var3, float var4, int var5) {
        GL11.glPushMatrix();
        GL11.glScalef((float)var4, (float)var4, (float)1.0f);
        var0.func_175063_a(var1, var2 / var4, var3 / var4, var5);
        GL11.glPopMatrix();
}
    public static void u(float var0, float var1, float var4, float var5, int var6) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var0, (float)var1, (float)0.0f);
        GL11.glRotatef((float)(-90.0f + var5 * 90.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-var0), (float)(-var1), (float)0.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.8f);
        RenderUtil.P(0L, var6);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)(var0 - var4 / 2.0f), (float)(var1 - var4 / 2.0f + 1.0f));
        GL11.glVertex2f((float)var0, (float)(var1 + var4 / 2.0f - 1.0f));
        GL11.glVertex2f((float)(var0 + var4 / 2.0f), (float)(var1 - var4 / 2.0f + 1.0f));
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
}
    public static float m(FontRenderer var0, float var1, float var2) {
        return var1 + (var2 - (float)var0.field_78288_b) / 2.0f;
}
    public static float I(long var0, CustomFont var2, float var3, float var4, float var5) {
        long var6 = var0 ^ 0x2485A71EF859L;
        return var3 + (var4 - FontUtil.B(var2, var5, var6)) / 2.0f;
}
    public static void D(long var0, float var2, float var3, float var4, float var5, float var6) {
        var0 = a ^ var0;
        int var7 = (int)((var0 ^ 0x6C06C863740CL) >>> 56);
        Minecraft var10 = MinecraftRef.c((byte)var7, 0L);
        ScaledResolution var11 = new ScaledResolution(var10);
        int var12 = var11.func_78325_e();
        int var13 = Math.round(var2 * (float)var12 * var6);
        int var14 = Math.round(((float)var11.func_78328_b() - (var3 + var5) * var6) * (float)var12);
        int var15 = Math.round(var4 * (float)var12 * var6);
        int var16 = Math.round(var5 * (float)var12 * var6);
        GL11.glEnable((int)3089);
        GL11.glScissor((int)var13, (int)var14, (int)var15, (int)var16);
}
    public static float B(CustomFont var0, float var1, long var2) {
        long var4 = var2 ^ 0x695877B84C71L;
        return var0.o(var4) * var1;
}
    public static void N(CustomFont var0, long var1, String var3, float var4, float var5, float var6, int var7) {
        GL11.glPushMatrix();
        GL11.glScalef((float)var6, (float)var6, (float)1.0f);
        var0.T(37697014677608L, var3, var4 / var6, var5 / var6, var7);
        GL11.glPopMatrix();
}
    public static String O(int var0, FontRenderer var1, String var2, int var3, float var4, short var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var6 = ((long)var0 << 32 | (long)var3 << 48 >>> 32 | (long)var5 << 48 >>> 48) ^ a;
        if (var2 == null) {
            return "";
}
        if ((float)var1.func_78256_a(var2) <= var4) {
            return var2;
}
        String var8 = FontUtil.a(32506, 0x7269EF51D4C13455L ^ var6);
        String var9 = var2;
        while (!var9.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!((float)var1.func_78256_a(stringBuilder.append(var9).append(var8).toString()) > var4)) break;
            var9 = var9.substring(0, var9.length() - 1);
}
        return var9 + var8;
}
    public static CustomFont G() {
        return Font.m(0L);
}
    public static int y(int var2, float var3) {
        var3 = MathUtil.q(var3, 0.0f, 1.0f);
        int var4 = Math.round((float)(var2 >> 24 & 0xFF) * var3);
        return var2 & 0xFFFFFF | var4 << 24;
}
    public static float P(long var0, short var2, CustomFont var3, float var4, float var5) {
        long var6 = (var0 << 16 | (long)var2 << 48 >>> 48) ^ a;
        long var8 = var6 ^ 0x729283F1FCE6L;
        return var4 + (var5 - var3.o(var8)) / 2.0f;
}
    public static void S(CustomFont var0, String var1, float var2, float var3, float var4, long var5, float var7, float var8, int var9) {
        var5 = a ^ var5;
        long var10 = var5 ^ 0x208DADAF5FD0L;
        long var12 = var5 ^ 0x510EA24A873EL;
        long var14 = var5 ^ 0x3A475E6A362L;
        float var16 = var2 + (var4 - FontUtil.A(var0, var1, var8, var14)) / 2.0f;
        float var17 = var3 + (var7 - FontUtil.B(var0, var8, var12)) / 2.0f;
        FontUtil.N(var0, var10, var1, var16, var17, var8, var9);
}
    public static String p(FontRenderer var0, String var1, float var2, float var3, long var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var4 = a ^ var4;
        int var6 = (int)((var4 ^ 0x8B2BE824F83L) >>> 32);
        int var7 = (int)((var4 ^ 0x8B2BE824F83L) << 32 >>> 48);
        int var8 = (int)((var4 ^ 0x8B2BE824F83L) << 48 >>> 48);
        return FontUtil.O(var6, var0, var1, var7, var2 / var3, (short)var8);
}
    public static String l(double var0, long var2) {
        String var4 = String.format("%.2f", var0);
        if (var4.contains(".")) {
            var4 = var4.replaceAll("0+$", "").replaceAll("\\.$", "");
}
        return var4;
}
    public static String s(CustomFont var0, long var1, String var3, float var4) {
        if (var3 == null) {
            return "";
}
        if (var0.R(var3, 52019766876817L) <= var4) {
            return var3;
}
        String var7 = "...";
        String var8 = var3;
        while (!var8.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!(var0.R(stringBuilder.append(var8).append(var7).toString(), 52019766876817L) > var4)) break;
            var8 = var8.substring(0, var8.length() - 1);
}
        return var8 + var7;
}
    public static float c(FontRenderer var0, String var1, float var2, float var3, float var4) {
        if (var1 != null && !var1.isEmpty()) {
            float var5 = var0.func_78256_a(var1);
            if (var5 <= 0.0f) {
                return var3;
}
            float var6 = Math.min(var3, var2 / var5);
            return Math.max(var4, var6);
}
        return var3;
}
    public static String Q(int var0, CustomFont var1, String var2, short var3, char var4, float var5, float var6) {
        long var7 = ((long)var0 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a;
        long var9 = var7 ^ 0x21B6DDE367F0L;
        return FontUtil.s(var1, var9, var2, var5 / var6);
}
    public static float Q(FontRenderer var0, float var1, float var2, float var3) {
        return var1 + (var2 - FontUtil.J(var0, var3)) / 2.0f;
}
    public static void L(FontRenderer var0, String var1, float var2, float var3, float var4, float var5, float var6, int var7) {
        float var8 = var2 + (var4 - FontUtil.M(var0, var1, var6)) / 2.0f;
        float var9 = var3 + (var5 - FontUtil.J(var0, var6)) / 2.0f;
        FontUtil.F(var0, var1, var8, var9, var6, var7);
}
    public static float M(FontRenderer var0, String var1, float var2) {
        return (float)var0.func_78256_a(var1) * var2;
}
    public static float w(CustomFont var0, String var1, float var2, float var3, float var4, long var5) {
        long var7 = var5 ^ 0x594859E7D62CL;
        if (var1 != null && !var1.isEmpty()) {
            float var9 = var0.R(var1, var7);
            if (var9 <= 0.0f) {
                return var3;
}
            float var10 = Math.min(var3, var2 / var9);
            return Math.max(var4, var10);
}
        return var3;
}
    public static float A(CustomFont var0, String var1, float var2, long var3) {
        return var0.R(var1, 52019766876817L) * var2;
}
    public static CustomFont n(long var0, int var2) {
        return Font.m(0L);
}
    public static String G(String var0, String var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        String var6 = Language.z(var0, 0L);
        return var6 != null && !var6.isEmpty() && !var6.equals("LANGUAGE_ERROR") ? var6 : var1;
}
    private FontUtil() {
}
    public static void W(float var0, float var3, float var4, float var5, float var6, int var7, int var8) {
        RenderUtil.J(var0, var3, var4, var5, var6, var7, var8, var8);
}
    public static Color Y(Color var0, Color var1, float var2) {
        var2 = MathUtil.q(var2, 0.0f, 1.0f);
        float var3 = 1.0f - var2;
        return new Color(Math.round((float)var0.getRed() * var3 + (float)var1.getRed() * var2), Math.round((float)var0.getGreen() * var3 + (float)var1.getGreen() * var2), Math.round((float)var0.getBlue() * var3 + (float)var1.getBlue() * var2), Math.round((float)var0.getAlpha() * var3 + (float)var1.getAlpha() * var2));
}
    static {
        d = new HashMap(13);
        b = new String[]{"}\u009a Z\u000bgAs", "[\u00a4\u0097a\u00d4\u0016\u00bb[", "\u00d9\u00b7Z\u00fa\u008bVk\u0099", "O\u00cb\u00e44\u00cd\u00c4\u00bb\u00ab7\u00a3\u00d5\u00af\u00cb\u00bdZV", "\u00a9^uN\u00a8\u008c\u00ea\u00f7", "(\u0092\u00bfz\u009f\u0081\u00a0\u0093"};
        c = new String[6];
        g = new HashMap(13);
        e = new long[]{1301708283197147249L, -1541604716268540942L, 6379316386513373933L, 4355031182947347440L, 4165885267922111809L, 6434429687449019973L, -4802263111103286353L, 7726072117318443924L, 7157246289987260658L, 2471304832662197487L, -6871509033680594436L, 8276650313308325762L};
}
}