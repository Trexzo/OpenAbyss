/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.DynamicTexture
 */
package Abyss.util.render;

import Abyss.util.Pair;
import Abyss.util.render.Glyph;
import java.util.Map;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class GlyphTexture {
    private static long a;
    static {
        a = 137121052021233L;
    }
            private final int y;
    private Pair<Integer, Integer> g;
    private final Glyph[] F;
    private DynamicTexture x;

    public static int k(GlyphTexture var0) {
        return var0.y;
}
    public static Pair x(GlyphTexture var0, Pair var1) {
        var0.g = var1;
        return var0.g;
}
    public static Pair o(GlyphTexture var0) {
        return var0.g;
}
    public static DynamicTexture x(GlyphTexture var0) {
        return var0.x;
}
    public static DynamicTexture d(GlyphTexture var0, DynamicTexture var1) {
        var0.x = var1;
        return var0.x;
}
    public static Glyph[] t(GlyphTexture var0) {
        return var0.F;
}
    public GlyphTexture(int var1, long var2) {
        var2 = a ^ var2;
        this.F = new Glyph[256];
        this.g = Pair.p(512, 0);
        this.y = var1;
}
    static {
}
}