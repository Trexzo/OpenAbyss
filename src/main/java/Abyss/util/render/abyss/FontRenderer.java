/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util.render.abyss;

public interface FontRenderer {
    public int drawString(String var1, float var2, float var3, int var4);

    public int drawStringWithShadow(String var1, float var2, float var3, int var4);

    default public void drawStringWithOutline(String text, float x, float y, int color) {
        this.drawString(text, x - 0.5f, y, -16777216);
        this.drawString(text, x + 0.5f, y, -16777216);
        this.drawString(text, x, y - 0.5f, -16777216);
        this.drawString(text, x, y + 0.5f, -16777216);
        this.drawString(text, x, y, color);
}
    public float getWidth(String var1);

    default public float getHeight(String text) {
        return 11.0f;
}
}