/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util.render.abyss;

import Abyss.module.impl.visual.HUD;
import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.TTFUtils;
import Abyss.util.render.abyss.TrueTypeFontRenderer;
import Abyss.util.render.abyss.VanillaFontAdapter;

public final class FontManager {
    public static final TrueTypeFontRenderer FR = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("font.ttf", 21), true, true);
    public static final TrueTypeFontRenderer MEDIUM_FR = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("font.ttf", 20), true, true);
    public static final TrueTypeFontRenderer SMALL_FR = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("font.ttf", 18), true, true);
    public static final TrueTypeFontRenderer FN_FR = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("Burbank.ttf", 36), true, false);
    public static final TrueTypeFontRenderer SP_FR = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("smallest_pixel.ttf", 16), true, true);
    private static volatile boolean texturesReady;
    private static final TrueTypeFontRenderer[] ALL_RENDERERS;
    private static int warmRendererIdx;
    private static int warmCharIdx;
    private static final int GLYPHS_PER_TICK = 8;
    private static final long WARM_BUDGET_NS = 2000000L;
    private static volatile boolean warmingInProgress;

    private FontManager() {
}
    public static void initTextures() {
        if (texturesReady) {
            return;
}
        for (TrueTypeFontRenderer r2 : ALL_RENDERERS) {
            r2.generateTextures();
}
        texturesReady = true;
        warmingInProgress = false;
}
    public static boolean warmStep() {
        if (texturesReady) {
            return true;
}
        warmingInProgress = true;
        int budget = 8;
        long deadline = System.nanoTime() + 2000000L;
        while (warmRendererIdx < ALL_RENDERERS.length && budget > 0) {
            TrueTypeFontRenderer r2 = ALL_RENDERERS[warmRendererIdx];
            while (warmCharIdx < 256 && budget > 0) {
                r2.generateSingleGlyph(warmCharIdx);
                ++warmCharIdx;
                --budget;
                if (System.nanoTime() < deadline) continue;
                budget = 0;
                break;
}
            if (warmCharIdx < 256) continue;
            ++warmRendererIdx;
            warmCharIdx = 0;
}
        if (warmRendererIdx >= ALL_RENDERERS.length) {
            texturesReady = true;
            warmingInProgress = false;
}
        return texturesReady;
}
    public static boolean isReady() {
        return texturesReady;
}
    public static void ensureTextures() {
        if (!texturesReady) {
            if (warmingInProgress) {
                return;
}
            FontManager.initTextures();
}
}
    public static FontRenderer get() {
        try {
            if (!HUD.useCustomFont.c()) {
                return VanillaFontAdapter.INSTANCE;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        if (!texturesReady) {
            if (warmingInProgress) {
                return VanillaFontAdapter.INSTANCE;
}
            FontManager.initTextures();
}
        return FR;
}
    public static FontRenderer getSmall() {
        try {
            if (!HUD.useCustomFont.c()) {
                return VanillaFontAdapter.INSTANCE;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        if (!texturesReady) {
            if (warmingInProgress) {
                return VanillaFontAdapter.INSTANCE;
}
            FontManager.initTextures();
}
        return SMALL_FR;
}
    public static FontRenderer getMedium() {
        try {
            if (!HUD.useCustomFont.c()) {
                return VanillaFontAdapter.INSTANCE;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        if (!texturesReady) {
            if (warmingInProgress) {
                return VanillaFontAdapter.INSTANCE;
}
            FontManager.initTextures();
}
        return MEDIUM_FR;
}
    static {
        ALL_RENDERERS = new TrueTypeFontRenderer[]{FR, SP_FR, MEDIUM_FR, SMALL_FR, FN_FR};
}
}