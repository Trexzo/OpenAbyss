/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.util.render;

import Abyss.util.ClientUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.render.FontGlyphPage;
import Abyss.util.render.GlyphDrawer;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import java.awt.Font;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CustomFont {
    private static long a;
    static {
        a = 123238688271397L;
    }
    public final boolean b;
    private final Minecraft H;
    private final FontGlyphPage G;
    private final FontRenderer abyssRenderer;

    private static Font a(ResourceLocation var0, long var1) {
        try {
            InputStream var6 = MinecraftRef.c((byte)0, 0L).getResourceManager().getResource(var0).getInputStream();
            return Font.createFont(0, var6).deriveFont(20.0f);
}
        catch (Exception var7) {
            return new Font("default", 0, 20);
}
}
    private Integer G(long var1, char var3) {
        switch (Character.toLowerCase(var3)) {
            case '0': {
                return -16777216;
}
            case '1': {
                return -16777046;
}
            case '2': {
                return -16733696;
}
            case '3': {
                return -16733526;
}
            case '4': {
                return -5636096;
}
            case '5': {
                return -5635926;
}
            case '6': {
                return -22016;
}
            case '7': {
                return -5592406;
}
            case '8': {
                return -11184811;
}
            case '9': {
                return -11184641;
}
            default: {
                return null;
}
            case 'a': {
                return -11141291;
}
            case 'b': {
                return -11141121;
}
            case 'c': {
                return -43691;
}
            case 'd': {
                return -43521;
}
            case 'e': {
                return -171;
}
            case 'f': 
}
        return -1;
}
    public void X(String var1, float var2, long var3, float var5, int var6) {
        this.v(var1, var2, var5, var6, 88827598794260L, false);
}
    private void B(String var1, float var2, float var3, int var4, boolean var5, GlyphDrawer var8) {
        if (var1 == null) {
            return;
}
        float x = var2;
        float offset = 0.0f;
        Integer formattingColor = null;
        for (int i = 0; i < var1.length(); ++i) {

            char ch = var1.charAt(i);
            if (ch == '\u00a7' && i + 1 < var1.length()) {
                Integer parsed = this.G(0L, var1.charAt(i + 1));
                if (parsed != null) {
                    formattingColor = parsed;
                } else if (Character.toLowerCase(var1.charAt(i + 1)) == 'r') {
                    formattingColor = null;
}
                ++i;
                continue;
}
            String glyph = String.valueOf(ch);
            float width = this.abyssRenderer.getWidth(glyph);
            int color = var8 == null ? (formattingColor == null ? var4 : formattingColor) : var8.s(ch, offset, (int)Math.ceil(width), formattingColor);
            if (var5) {
                this.abyssRenderer.drawStringWithShadow(glyph, x, var3, color);
            } else {
                this.abyssRenderer.drawString(glyph, x, var3, color);
}
            x += width;
            offset += width;
}
}
    public float o(long var1) {
        return this.b ? this.abyssRenderer.getHeight("Ag") : (float)this.G.e(25129460711095L);
}
    public void p(String var1, float var2, float var3, long var4, int var6) {
        this.v(var1, var2 - this.R(var1, 52019766876817L) / 2.0f, var3, var6, 88827598794260L, false);
}
    public void T(long var1, String var3, float var4, float var5, int var6) {
        this.v(var3, var4, var5, var6, 88827598794260L, true);
}
    public void A(String var1, float var2, float var3, int var4, long var5, boolean var7, GlyphDrawer var8) {
        var1 = ClientUtil.replaceString(var1);
        if (this.b) {
            this.B(var1, var2, var3, var4, var7, var8);
        } else {
            this.G.j(var1, var2, 94109581654416L, var3, var4, var7, var8);
}
}
    public void v(String var1, float var2, float var3, int var4, long var5, boolean var7) {
        var1 = ClientUtil.replaceString(var1);
        if (this.b) {
            if (var7) {
                this.abyssRenderer.drawStringWithShadow(var1, var2, var3, var4);
            } else {
                this.abyssRenderer.drawString(var1, var2, var3, var4);
}
        } else if (var7) {
            this.G.F(var1, (double)var2, var3, var4, 130877858286392L);
        } else {
            this.G.P(var1, var2, var3, 73237590039151L, var4);
}
}
    public float R(String var1, long var2) {
        var1 = ClientUtil.replaceString(var1);
        return this.b ? this.abyssRenderer.getWidth(var1) : this.G.S(var1, 2692364323580L);
}
    public CustomFont(long var1, String var3) {
        var1 = a ^ var1;
        long var4 = var1 ^ 0x2B67CA749072L;
        long var6 = var1 ^ 0x3DFC397AC042L;
        int var8 = (int)((var1 ^ 0x74758D5CE0F0L) >>> 56);
        long var9 = (var1 ^ 0x74758D5CE0F0L) << 8 >>> 8;
        this.H = MinecraftRef.c((byte)var8, 0L);
        this.b = var3.equalsIgnoreCase("NONE");
        this.abyssRenderer = FontManager.FR;
        this.G = this.b ? null : new FontGlyphPage(CustomFont.a(new ResourceLocation("font/" + var3 + ".ttf"), var6), var4);
}
}