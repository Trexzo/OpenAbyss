/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 */
package Abyss.ui.abyss;

import Abyss.setting.settings.ColorSetting;
import Abyss.ui.abyss.AbyssClickGuiScreen;
import Abyss.util.render.abyss.FontManager;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

final class AbyssColorPicker {
    static final int HEIGHT = 64;
    private static final int SEGMENTS = 48;
    private static final int RINGS = 10;
    private static final float RADIUS = 24.0f;
    private static final float BAR = 52.0f;
    private static final float[] COS = new float[49];
    private static final float[] SIN = new float[49];
    private final ColorSetting setting;
    private float hue;
    private float saturation;
    private float value;
    private int drag = -1;
    private boolean hexFocused;
    private String hex = "#FFFFFF";
    private final String before;

    AbyssColorPicker(ColorSetting setting) {
        this.setting = setting;
        this.before = this.read();
        this.sync();
}
    ColorSetting setting() {
        return this.setting;
}
    boolean typing() {
        return this.hexFocused;
}
    boolean contains(float x, float y, int w2, int mx, int my) {
        return (float)mx >= x && (float)mx <= x + (float)w2 && (float)my >= y && (float)my <= y + 64.0f;
}
    void draw(float x, float y, int w2, int mx, int my) {
        if (this.drag == 0) {
            this.dragWheel(x, y, mx, my);
        } else if (this.drag == 1) {
            this.dragValue(x, w2, mx);
}
        float rx = x + (float)w2 - 58.0f;
        AbyssClickGuiScreen.rect(x, y, x + (float)w2, y + 64.0f, -267119847);
        AbyssColorPicker.wheel(x + 30.0f, y + 34.0f);
        float angle = this.hue * (float)Math.PI * 2.0f;
        float px = x + 30.0f + (float)Math.cos(angle) * this.saturation * 24.0f;
        float py = y + 34.0f + (float)Math.sin(angle) * this.saturation * 24.0f;
        AbyssClickGuiScreen.rect(px - 2.0f, py - 2.0f, px + 2.0f, py + 2.0f, -1);
        AbyssClickGuiScreen.rect(px - 1.0f, py - 1.0f, px + 1.0f, py + 1.0f, this.rgb());
        AbyssColorPicker.gradient(rx, y + 11.0f, rx + 52.0f, y + 17.0f, 0xFF000000 | Color.HSBtoRGB(this.hue, this.saturation, 0.0f) & 0xFFFFFF, 0xFF000000 | Color.HSBtoRGB(this.hue, this.saturation, 1.0f) & 0xFFFFFF);
        float vx = rx + this.value * 52.0f;
        AbyssClickGuiScreen.rect(vx - 1.0f, y + 9.0f, vx + 1.0f, y + 19.0f, -1);
        AbyssClickGuiScreen.rect(rx, y + 26.0f, rx + 16.0f, y + 40.0f, -16777216);
        AbyssClickGuiScreen.rect(rx + 1.0f, y + 27.0f, rx + 15.0f, y + 39.0f, this.rgb());
        AbyssClickGuiScreen.rect(rx, y + 46.0f, rx + 52.0f, y + 60.0f, this.hexFocused ? -12241584 : -14080466);
        String shown = this.hexFocused && (System.currentTimeMillis() / 500L & 1L) == 0L ? this.hex + "_" : this.hex;
        FontManager.getSmall().drawStringWithShadow(shown, rx + 3.0f, y + 49.0f, -855307);
        FontManager.getSmall().drawStringWithShadow("V", rx - 9.0f, y + 10.0f, -7697773);
}
    boolean click(float x, float y, int w2, int mx, int my, int b) {
        float dy;
        float dx;
        if (b != 0) {
            return this.contains(x, y, w2, mx, my);
}
        float rx = x + (float)w2 - 58.0f;
        boolean was = this.hexFocused;
        boolean bl = this.hexFocused = (float)mx >= rx && (float)mx <= rx + 52.0f && (float)my >= y + 45.0f && (float)my <= y + 61.0f;
        if (was && !this.hexFocused) {
            this.applyHex();
}
        if ((dx = (float)mx - (x + 30.0f)) * dx + (dy = (float)my - (y + 34.0f)) * dy <= 576.0f) {
            this.drag = 0;
            this.dragWheel(x, y, mx, my);
            return true;
}
        if ((float)mx >= rx && (float)mx <= rx + 52.0f && (float)my >= y + 8.0f && (float)my <= y + 20.0f) {
            this.drag = 1;
            this.dragValue(x, w2, mx);
            return true;
}
        return this.contains(x, y, w2, mx, my);
}
    void release() {
        this.drag = -1;
}
    boolean keyTyped(char ch, int key) {
        if (!this.hexFocused) {
            return false;
}
        if (key == 1) {
            this.setting.e(this.before);
            this.hexFocused = false;
            this.sync();
            return true;
}
        if (key == 28 || key == 156) {
            this.applyHex();
            this.hexFocused = false;
            return true;
}
        if (GuiScreen.isKeyComboCtrlV((int)key)) {
            String clip = GuiScreen.getClipboardString();
            if (clip != null) {
                this.hex = AbyssColorPicker.trim(clip);
}
            return true;
}
        if (key == 14) {
            if (!this.hex.isEmpty()) {
                this.hex = this.hex.substring(0, this.hex.length() - 1);
}
            return true;
}
        if ((ch == '#' && this.hex.isEmpty() || Character.digit(ch, 16) >= 0) && this.hex.length() < (this.hex.startsWith("#") ? 7 : 6)) {
            this.hex = this.hex + Character.toUpperCase(ch);
}
        return true;
}
    void closed() {
        if (this.hexFocused) {
            this.applyHex();
}
        this.hexFocused = false;
        this.drag = -1;
}
    private void dragWheel(float x, float y, int mx, int my) {
        float dx = (float)mx - (x + 30.0f);
        float dy = (float)my - (y + 34.0f);
        this.saturation = AbyssColorPicker.clamp((float)Math.sqrt(dx * dx + dy * dy) / 24.0f);
        this.hue = (float)(Math.atan2(dy, dx) / (Math.PI * 2));
        if (this.hue < 0.0f) {
            this.hue += 1.0f;
}
        this.commit();
}
    private void dragValue(float x, int w2, int mx) {
        this.value = AbyssColorPicker.clamp(((float)mx - (x + (float)w2 - 58.0f)) / 52.0f);
        this.commit();
}
    private void commit() {
        this.setting.e(String.format("%06X", this.rgb() & 0xFFFFFF));
        this.hex = "#" + this.setting.Q();
}
    private int rgb() {
        return 0xFF000000 | Color.HSBtoRGB(this.hue, this.saturation, this.value) & 0xFFFFFF;
}
    private void sync() {
        int c = Integer.parseInt(this.read(), 16) & 0xFFFFFF;
        float[] hsb = Color.RGBtoHSB(c >> 16 & 0xFF, c >> 8 & 0xFF, c & 0xFF, null);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.value = hsb[2];
        this.hex = String.format("#%06X", c);
}
    private void applyHex() {
        String raw;
        String string = raw = this.hex.startsWith("#") ? this.hex.substring(1) : this.hex;
        if (raw.length() == 3) {
            raw = new String(new char[]{raw.charAt(0), raw.charAt(0), raw.charAt(1), raw.charAt(1), raw.charAt(2), raw.charAt(2)});
}
        if (raw.length() != 6) {
            this.sync();
            return;
}
        try {
            this.setting.e(String.format("%06X", Integer.parseInt(raw, 16) & 0xFFFFFF));
}
        catch (NumberFormatException numberFormatException) {
            // empty catch block
}
        this.sync();
}
    private String read() {
        String t2;
        String v2 = this.setting.Q();
        if (v2 == null) {
            return "FFFFFF";
}
        String string = t2 = v2.startsWith("#") ? v2.substring(1) : v2;
        if (t2.length() != 6) {
            return "FFFFFF";
}
        for (int i = 0; i < 6; ++i) {
            if (Character.digit(t2.charAt(i), 16) >= 0) continue;
            return "FFFFFF";
}
        return t2.toUpperCase();
}
    private static String trim(String v2) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < v2.length() && out.length() < 7; ++i) {
            char c = v2.charAt(i);
            if ((c != '#' || out.length() != 0) && Character.digit(c, 16) < 0) continue;
            out.append(Character.toUpperCase(c));
}
        return out.toString();
}
    private static float clamp(float v2) {
        return v2 < 0.0f ? 0.0f : (v2 > 1.0f ? 1.0f : v2);
}
    private static void wheel(float cx, float cy) {
        AbyssColorPicker.beginRaw();
        GlStateManager.shadeModel((int)7425);
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (int ring = 0; ring < 10; ++ring) {
            float r0 = 24.0f * (float)ring / 10.0f;
            float r1 = 24.0f * (float)(ring + 1) / 10.0f;
            float s0 = (float)ring / 10.0f;
            float s1 = (float)(ring + 1) / 10.0f;
            for (int i = 0; i < 48; ++i) {
                float h0 = (float)i / 48.0f;
                float h1 = (float)(i + 1) / 48.0f;
                AbyssColorPicker.vtx(wr, cx + COS[i] * r0, cy + SIN[i] * r0, Color.HSBtoRGB(h0, s0, 1.0f));
                AbyssColorPicker.vtx(wr, cx + COS[i] * r1, cy + SIN[i] * r1, Color.HSBtoRGB(h0, s1, 1.0f));
                AbyssColorPicker.vtx(wr, cx + COS[i + 1] * r1, cy + SIN[i + 1] * r1, Color.HSBtoRGB(h1, s1, 1.0f));
                AbyssColorPicker.vtx(wr, cx + COS[i + 1] * r0, cy + SIN[i + 1] * r0, Color.HSBtoRGB(h1, s0, 1.0f));
}
}
        tess.draw();
        GlStateManager.shadeModel((int)7424);
        AbyssColorPicker.endRaw();
}
    private static void gradient(float x1, float y1, float x2, float y2, int left, int right) {
        AbyssColorPicker.beginRaw();
        GlStateManager.shadeModel((int)7425);
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        AbyssColorPicker.vtx(wr, x1, y1, left);
        AbyssColorPicker.vtx(wr, x1, y2, left);
        AbyssColorPicker.vtx(wr, x2, y2, right);
        AbyssColorPicker.vtx(wr, x2, y1, right);
        tess.draw();
        GlStateManager.shadeModel((int)7424);
        AbyssColorPicker.endRaw();
}
    private static void vtx(WorldRenderer wr, float x, float y, int c) {
        wr.pos((double)x, (double)y, 0.0).color(c >> 16 & 0xFF, c >> 8 & 0xFF, c & 0xFF, 255).endVertex();
}
    private static void beginRaw() {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
}
    private static void endRaw() {
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.resetColor();
}
    static {
        for (int i = 0; i <= 48; ++i) {
            double a = Math.PI * 2 * (double)i / 48.0;
            AbyssColorPicker.COS[i] = (float)Math.cos(a);
            AbyssColorPicker.SIN[i] = (float)Math.sin(a);
}
}
}