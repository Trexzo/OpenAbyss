/*
 * Decompiled with CFR 0.152.
 */
package Abyss.enums;

import java.awt.Color;

public enum GradientPalette {
    SPECTRUM(new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)),
    ABYSS(new Color(0, 246, 205), new Color(0, 172, 250), new Color(12, 92, 255)),
    TRENCH(new Color(0, 34, 58), new Color(0, 76, 122), new Color(0, 148, 206)),
    FATHOM(new Color(8, 10, 34), new Color(28, 32, 96), new Color(86, 58, 190)),
    TIDE(new Color(0, 98, 152), new Color(0, 162, 236), new Color(138, 226, 244)),
    SEAFOAM(new Color(114, 252, 198), new Color(166, 252, 220), new Color(206, 252, 242)),
    GLACIER(new Color(174, 226, 255), new Color(112, 198, 255), new Color(62, 146, 255)),
    VOLT(new Color(0, 252, 255), new Color(0, 164, 255), new Color(96, 0, 255)),
    ARC(new Color(0, 196, 255), new Color(0, 250, 252), new Color(176, 252, 255)),
    CIRCUIT(new Color(0, 250, 252), new Color(0, 144, 252), new Color(132, 38, 232)),
    GRID(new Color(0, 250, 0), new Color(0, 174, 76), new Color(0, 114, 56)),
    VENOM(new Color(44, 250, 44), new Color(174, 252, 0), new Color(250, 252, 0)),
    VERDANT(new Color(28, 134, 28), new Color(56, 174, 108), new Color(138, 234, 138)),
    MOLTEN(new Color(122, 0, 0), new Color(214, 16, 56), new Color(252, 94, 66)),
    CINDER(new Color(252, 44, 44), new Color(252, 114, 0), new Color(252, 196, 0)),
    FORGE(new Color(252, 64, 0), new Color(252, 134, 0), new Color(252, 212, 0)),
    CRIMSON(new Color(114, 0, 0), new Color(174, 0, 0), new Color(252, 34, 34)),
    EMBER(new Color(252, 88, 72), new Color(252, 144, 122), new Color(252, 190, 108)),
    HELIOS(new Color(252, 114, 0), new Color(252, 174, 0), new Color(252, 252, 114)),
    BULLION(new Color(252, 210, 0), new Color(252, 188, 12), new Color(252, 154, 0)),
    DUNE(new Color(206, 174, 134), new Color(226, 196, 154), new Color(252, 230, 184)),
    CORAL(new Color(252, 174, 134), new Color(252, 206, 174), new Color(252, 232, 206)),
    BLUSH(new Color(252, 98, 174), new Color(252, 176, 188), new Color(252, 224, 220)),
    NEON(new Color(252, 34, 154), new Color(252, 84, 196), new Color(252, 154, 216)),
    PRISM(new Color(252, 0, 252), new Color(252, 0, 134), new Color(252, 74, 74)),
    VIOLET(new Color(114, 0, 252), new Color(174, 0, 252), new Color(252, 0, 196)),
    REVERIE(new Color(122, 0, 252), new Color(180, 78, 206), new Color(216, 154, 216)),
    ORCHID(new Color(174, 154, 252), new Color(196, 174, 252), new Color(226, 206, 252)),
    DUSK(new Color(20, 20, 108), new Color(66, 56, 134), new Color(132, 38, 222)),
    OBSIDIAN(new Color(24, 24, 30), new Color(56, 56, 76), new Color(114, 76, 154)),
    GRAPHITE(new Color(20, 20, 20), new Color(46, 46, 46), new Color(86, 86, 86));

    private final Color c;
    private final Color y;
    private final Color H;
    
    public Color s() {
        return this.H;
}
    public int A(double var1, double var3, long var5) {
        return GradientPalette.J(var1, var3, this.H, this.y, this.c);
}
    private static int T(Color var0, double var1, double var5) {
        long var7 = System.currentTimeMillis();
        double var9 = 2000.0 / var5;
        double var11 = (Math.sin(((double)var7 + var1 * 50.0) / var9 * Math.PI * 2.0) + 1.0) / 2.0;
        double var13 = 0.75 + var11 * 0.25;
        int var15 = (int)Math.min(255.0, (double)var0.getRed() * var13);
        int var16 = (int)Math.min(255.0, (double)var0.getGreen() * var13);
        int var17 = (int)Math.min(255.0, (double)var0.getBlue() * var13);
        return var15 << 16 | var16 << 8 | var17 | 0xFF000000;
}
    public String O() {
        return this.name();
}
    public Color a() {
        return this.c;
}
    private GradientPalette(Color var3, Color var4, Color var5) {
        this.H = var3;
        this.y = var4;
        this.c = var5;
}
    public static int J(double var2, double var4, Color var6, Color var7, Color var8) {
        float var18;
        Color var17;
        Color var16;
        long var11;
        long var9 = System.currentTimeMillis();
        long var13 = (long)((double)var9 + var2 * 50.0);
        float var15 = (float)((double)(var13 % (var11 = (long)(2000.0 / var4))) / (double)var11);
        if (var15 < 0.33333334f) {
            var16 = var6;
            var17 = var7;
            var18 = var15 * 3.0f;
        } else if (var15 < 0.6666667f) {
            var16 = var7;
            var17 = var8;
            var18 = (var15 - 0.33333334f) * 3.0f;
        } else {
            var16 = var8;
            var17 = var6;
            var18 = (var15 - 0.6666667f) * 3.0f;
}
        int var19 = (int)((float)var16.getRed() + (float)(var17.getRed() - var16.getRed()) * var18);
        int var20 = (int)((float)var16.getGreen() + (float)(var17.getGreen() - var16.getGreen()) * var18);
        int var21 = (int)((float)var16.getBlue() + (float)(var17.getBlue() - var16.getBlue()) * var18);
        return var19 << 16 | var20 << 8 | var21 | 0xFF000000;
}
    public int H(double var1, double var3, long var5) {
        return GradientPalette.T(this.c, var1, var3);
}
    public int M(double var1, long var3, double var5) {
        return GradientPalette.T(this.y, var1, var5);
}
    public static int R(double var0, double var2) {
        long var4 = System.currentTimeMillis();
        long var6 = (long)(2000.0 / var2);
        long var8 = (long)((double)var4 + var0 * 50.0);
        float var10 = (float)((double)(var8 % var6) / (double)var6);
        return Color.HSBtoRGB(var10, 1.0f, 1.0f);
}
    public int m(long var1, byte var3, double var4, double var6) {
        return GradientPalette.T(this.H, var4, var6);
}
    public Color p() {
        return this.y;
}
    public static String[] N() {
        GradientPalette[] var0 = GradientPalette.values();
        String[] var1 = new String[var0.length];
        for (int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = var0[var2].name();
}
        return var1;
}
    static {
}
}