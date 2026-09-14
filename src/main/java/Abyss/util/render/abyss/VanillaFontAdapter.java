/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package Abyss.util.render.abyss;

import Abyss.util.render.abyss.FontRenderer;
import net.minecraft.client.Minecraft;

public final class VanillaFontAdapter
implements FontRenderer {
    public static final VanillaFontAdapter INSTANCE = new VanillaFontAdapter();

    private VanillaFontAdapter() {
}
    private net.minecraft.client.gui.FontRenderer mc() {
        return Minecraft.func_71410_x().field_71466_p;
}
    @Override
    public int drawString(String text, float x, float y, int color) {
        return this.mc().func_175065_a(text, x, y, color, false);
}
    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return this.mc().func_175065_a(text, x, y, color, true);
}
    @Override
    public float getWidth(String text) {
        return this.mc().func_78256_a(text);
}
    @Override
    public float getHeight(String text) {
        return this.mc().field_78288_b;
}
}