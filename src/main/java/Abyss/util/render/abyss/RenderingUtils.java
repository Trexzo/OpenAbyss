/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package Abyss.util.render.abyss;

import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.LockedResolution;
import Abyss.util.render.abyss.MathUtils;
import Abyss.util.render.abyss.OGLUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class RenderingUtils {
    private static final double DOUBLE_PI = Math.PI * 2;
    private static final Frustum FRUSTUM = new Frustum();
    private static int lastScaledWidth;
    private static int lastScaledHeight;
    private static int lastGuiScale;
    private static ScaledResolution scaledResolution;
    private static int lastWidth;
    private static int lastHeight;
    private static LockedResolution lockedResolution;

    private RenderingUtils() {
}
    public static boolean isBBInFrustum(AxisAlignedBB aabb) {
        EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        FRUSTUM.func_78547_a(player.field_70165_t, player.field_70163_u, player.field_70161_v);
        return FRUSTUM.func_78546_a(aabb);
}
    public static void drawGradientRect(double left, double top, double right, double bottom, boolean sideways, int startColor, int endColor) {
        GlStateManager.func_179090_x();
        OGLUtils.enableBlending();
        GlStateManager.func_179103_j((int)7425);
        GL11.glBegin((int)7);
        OGLUtils.color(startColor);
        if (sideways) {
            GL11.glVertex2d((double)left, (double)top);
            GL11.glVertex2d((double)left, (double)bottom);
            OGLUtils.color(endColor);
            GL11.glVertex2d((double)right, (double)bottom);
            GL11.glVertex2d((double)right, (double)top);
        } else {
            GL11.glVertex2d((double)left, (double)top);
            OGLUtils.color(endColor);
            GL11.glVertex2d((double)left, (double)bottom);
            GL11.glVertex2d((double)right, (double)bottom);
            OGLUtils.color(startColor);
            GL11.glVertex2d((double)right, (double)top);
}
        GL11.glEnd();
        GlStateManager.func_179084_k();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void drawRect(float left, float top, float right, float bottom, int color) {
        int alpha = color >> 24 & 0xFF;
        boolean needBlend = alpha < 255;
        GlStateManager.func_179090_x();
        if (needBlend) {
            OGLUtils.enableBlending();
            GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)((float)alpha / 255.0f));
        } else {
            GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)1.0f);
}
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)left, (float)top);
        GL11.glVertex2f((float)left, (float)bottom);
        GL11.glVertex2f((float)right, (float)bottom);
        GL11.glVertex2f((float)right, (float)top);
        GL11.glEnd();
        if (needBlend) {
            GlStateManager.func_179084_k();
}
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static LockedResolution getLockedResolution() {
        int width = Display.getWidth();
        int height = Display.getHeight();
        if (width != lastWidth || height != lastHeight) {
            lastWidth = width;
            lastHeight = height;
            lockedResolution = new LockedResolution(width / 2, height / 2);
            return lockedResolution;
}
        return lockedResolution;
}
    public static ScaledResolution getScaledResolution() {
        int displayWidth = Display.getWidth();
        int displayHeight = Display.getHeight();
        int guiScale = Minecraft.func_71410_x().field_71474_y.field_74335_Z;
        if (displayWidth != lastScaledWidth || displayHeight != lastScaledHeight || guiScale != lastGuiScale) {
            lastScaledWidth = displayWidth;
            lastScaledHeight = displayHeight;
            lastGuiScale = guiScale;
            scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
            return scaledResolution;
}
        return scaledResolution;
}
    public static int getColorFromPercentage(float percentage) {
        return Color.HSBtoRGB(Math.min(1.0f, Math.max(0.0f, percentage)) / 3.0f, 0.9f, 0.9f);
}
    public static int getRainbowFromEntity(long currentMillis, int speed, int offset, boolean invert, float alpha) {
        float time = (float)((currentMillis + (long)offset * 300L) % (long)speed) / (float)speed;
        int rainbow = Color.HSBtoRGB(invert ? 1.0f - time : time, 0.9f, 0.9f);
        int r2 = rainbow >> 16 & 0xFF;
        int g = rainbow >> 8 & 0xFF;
        int b = rainbow & 0xFF;
        int a = (int)(alpha * 255.0f);
        return (a & 0xFF) << 24 | (r2 & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
}
    public static int getRainbow(long currentMillis, int speed, int offset) {
        return RenderingUtils.getRainbow(currentMillis, speed, offset, 1.0f);
}
    public static int getRainbow(long currentMillis, int speed, int offset, float alpha) {
        int rainbow = Color.HSBtoRGB(1.0f - (float)((currentMillis + (long)(offset * 100)) % (long)speed) / (float)speed, 0.9f, 0.9f);
        int r2 = rainbow >> 16 & 0xFF;
        int g = rainbow >> 8 & 0xFF;
        int b = rainbow & 0xFF;
        int a = (int)(alpha * 255.0f);
        return (a & 0xFF) << 24 | (r2 & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
}
    public static void drawAndRotateArrow(float x, float y, float size, boolean rotate) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)1.0f);
        OGLUtils.enableBlending();
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)1.0f);
        GlStateManager.func_179090_x();
        GL11.glBegin((int)4);
        if (rotate) {
            GL11.glVertex2f((float)size, (float)(size / 2.0f));
            GL11.glVertex2f((float)(size / 2.0f), (float)0.0f);
            GL11.glVertex2f((float)0.0f, (float)(size / 2.0f));
        } else {
            GL11.glVertex2f((float)0.0f, (float)0.0f);
            GL11.glVertex2f((float)(size / 2.0f), (float)(size / 2.0f));
            GL11.glVertex2f((float)size, (float)0.0f);
}
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
}
    public static double progressiveAnimation(double now, double desired, double speed) {
        double dif = Math.abs(now - desired);
        int fps = Minecraft.func_175610_ah();
        if (dif > 0.0) {
            double animationSpeed = MathUtils.roundToDecimalPlace(Math.min(10.0, Math.max(0.05, 144.0 / (double)fps * (dif / 10.0) * speed)), 0.05);
            if (dif != 0.0 && dif < animationSpeed) {
                animationSpeed = dif;
}
            if (now < desired) {
                return now + animationSpeed;
}
            if (now > desired) {
                return now - animationSpeed;
}
}
        return now;
}
    public static double linearAnimation(double now, double desired, double speed) {
        double dif = Math.abs(now - desired);
        int fps = Minecraft.func_175610_ah();
        if (dif > 0.0) {
            double animationSpeed = MathUtils.roundToDecimalPlace(Math.min(10.0, Math.max(0.005, 144.0 / (double)fps * speed)), 0.005);
            if (dif != 0.0 && dif < animationSpeed) {
                animationSpeed = dif;
}
            if (now < desired) {
                return now + animationSpeed;
}
            if (now > desired) {
                return now - animationSpeed;
}
}
        return now;
}
    public static int alphaComponent(int color, int alphaComp) {
        int r2 = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        return (r2 & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (alphaComp & 0xFF) << 24;
}
    public static int darkerClamped(int color, float factor) {
        int r2 = (int)Math.max(0.0f, Math.min(255.0f, (float)(color >> 16 & 0xFF) * factor));
        int g = (int)Math.max(0.0f, Math.min(255.0f, (float)(color >> 8 & 0xFF) * factor));
        int b = (int)Math.max(0.0f, Math.min(255.0f, (float)(color & 0xFF) * factor));
        int a = Math.max(0, Math.min(255, color >> 24 & 0xFF));
        return (r2 & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
}
    public static int darker(int color, float factor) {
        int r2 = (int)((float)(color >> 16 & 0xFF) * factor);
        int g = (int)((float)(color >> 8 & 0xFF) * factor);
        int b = (int)((float)(color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;
        return (r2 & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
}
    public static int darker(int color) {
        return RenderingUtils.darker(color, 0.6f);
}
    public static void drawOutlinedString(FontRenderer fr, String s, float x, float y, int color, int outlineColor) {
        fr.drawString(s, x - 0.5f, y, outlineColor);
        fr.drawString(s, x, y - 0.5f, outlineColor);
        fr.drawString(s, x + 0.5f, y, outlineColor);
        fr.drawString(s, x, y + 0.5f, outlineColor);
        fr.drawString(s, x, y, color);
}
    public static void drawImage(float x, float y, float width, float height, float r2, float g, float b, ResourceLocation image) {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(image);
        float f = 1.0f / width;
        float f1 = 1.0f / height;
        GlStateManager.func_179131_c((float)r2, (float)g, (float)b, (float)1.0f);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)x, (double)(y + height), 0.0).func_181673_a(0.0, (double)(height * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), 0.0).func_181673_a((double)(width * f), (double)(height * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)y, 0.0).func_181673_a((double)(width * f), 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)x, (double)y, 0.0).func_181673_a(0.0, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static int fadeBetween(int startColor, int endColor, float progress) {
        if (progress > 1.0f) {
            progress = 1.0f - progress % 1.0f;
}
        return RenderingUtils.fadeTo(startColor, endColor, progress);
}
    public static int fadeBetween(int startColor, int endColor) {
        return RenderingUtils.fadeBetween(startColor, endColor, (float)(System.currentTimeMillis() % 2000L) / 1000.0f);
}
    public static int fadeTo(int startColor, int endColor, float progress) {
        float invert = 1.0f - progress;
        int r2 = (int)((float)(startColor >> 16 & 0xFF) * invert + (float)(endColor >> 16 & 0xFF) * progress);
        int g = (int)((float)(startColor >> 8 & 0xFF) * invert + (float)(endColor >> 8 & 0xFF) * progress);
        int b = (int)((float)(startColor & 0xFF) * invert + (float)(endColor & 0xFF) * progress);
        int a = (int)((float)(startColor >> 24 & 0xFF) * invert + (float)(endColor >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r2 & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
}
    public static void drawLoop(float x, float y, double radius, int points, float width, int color, boolean filled) {
        GlStateManager.func_179090_x();
        GL11.glLineWidth((float)width);
        OGLUtils.enableBlending();
        OGLUtils.color(color);
        int smooth = filled ? 2881 : 2848;
        GL11.glEnable((int)smooth);
        GL11.glHint((int)(filled ? 3155 : 3154), (int)4354);
        GL11.glBegin((int)(filled ? 6 : 2));
        for (int i = 0; i < points; ++i) {
            if (filled) {
                double cs = (double)i * Math.PI / 180.0;
                double ps = (double)(i - 1) * Math.PI / 180.0;
                GL11.glVertex2d((double)((double)x + Math.cos(ps) * radius), (double)((double)y + -Math.sin(ps) * radius));
                GL11.glVertex2d((double)((double)x + Math.cos(cs) * radius), (double)((double)y + -Math.sin(cs) * radius));
                GL11.glVertex2d((double)x, (double)y);
                continue;
}
            GL11.glVertex2d((double)((double)x + radius * Math.cos((double)i * (Math.PI * 2) / (double)points)), (double)((double)y + radius * Math.sin((double)i * (Math.PI * 2) / (double)points)));
}
        GL11.glEnd();
        GL11.glDisable((int)smooth);
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void drawRoundedRect(float x1, float y1, float x2, float y2, float radius, int color) {
        double rad;
        int i;
        GlStateManager.func_179090_x();
        GlStateManager.func_179118_c();
        OGLUtils.enableBlending();
        OGLUtils.color(color);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        float r2 = Math.min(radius, Math.min((x2 - x1) / 2.0f, (y2 - y1) / 2.0f));
        for (i = 0; i <= 90; i += 6) {
            rad = Math.toRadians(i);
            GL11.glVertex2d((double)((double)(x2 - r2) + Math.sin(rad) * (double)r2), (double)((double)(y1 + r2) - Math.cos(rad) * (double)r2));
}
        for (i = 90; i <= 180; i += 6) {
            rad = Math.toRadians(i);
            GL11.glVertex2d((double)((double)(x2 - r2) + Math.sin(rad) * (double)r2), (double)((double)(y2 - r2) - Math.cos(rad) * (double)r2));
}
        for (i = 180; i <= 270; i += 6) {
            rad = Math.toRadians(i);
            GL11.glVertex2d((double)((double)(x1 + r2) + Math.sin(rad) * (double)r2), (double)((double)(y2 - r2) - Math.cos(rad) * (double)r2));
}
        for (i = 270; i <= 360; i += 6) {
            rad = Math.toRadians(i);
            GL11.glVertex2d((double)((double)(x1 + r2) + Math.sin(rad) * (double)r2), (double)((double)(y1 + r2) - Math.cos(rad) * (double)r2));
}
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void drawGradientBar(float x, float y, float width, float height, float pct, int colorStart, int colorEnd) {
        GlStateManager.func_179090_x();
        GlStateManager.func_179118_c();
        OGLUtils.enableBlending();
        GlStateManager.func_179103_j((int)7425);
        float filled = width * Math.max(0.0f, Math.min(1.0f, pct));
        GL11.glBegin((int)7);
        OGLUtils.color(colorStart);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x, (float)(y + height));
        OGLUtils.color(colorEnd);
        GL11.glVertex2f((float)(x + filled), (float)(y + height));
        GL11.glVertex2f((float)(x + filled), (float)y);
        GL11.glEnd();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
    public static void drawRoundedHead(int x, int y, int size, float radius, ResourceLocation skin) {
        if (skin == null) {
            RenderingUtils.drawRoundedRect(x, y, x + size, y + size, radius, -14408662);
            return;
}
        GL11.glEnable((int)2960);
        GL11.glClear((int)1024);
        GL11.glStencilFunc((int)519, (int)1, (int)255);
        GL11.glStencilOp((int)7680, (int)7680, (int)7681);
        GL11.glStencilMask((int)255);
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        GL11.glDepthMask((boolean)false);
        RenderingUtils.drawRoundedRect(x, y, x + size, y + size, radius, -1);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glDepthMask((boolean)true);
        GL11.glStencilFunc((int)514, (int)1, (int)255);
        GL11.glStencilMask((int)0);
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(skin);
        Gui.func_152125_a((int)x, (int)y, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)size, (int)size, (float)64.0f, (float)64.0f);
        Gui.func_152125_a((int)x, (int)y, (float)40.0f, (float)8.0f, (int)8, (int)8, (int)size, (int)size, (float)64.0f, (float)64.0f);
        GL11.glDisable((int)2960);
}
    public static int withAlpha(int rgb, int alpha) {
        return rgb & 0xFFFFFF | (alpha & 0xFF) << 24;
}
    public static double interpolate(double old, double now, float partialTicks) {
        return old + (now - old) * (double)partialTicks;
}
    public static float interpolate(float old, float now, float partialTicks) {
        return old + (now - old) * partialTicks;
}
    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderingUtils.drawRect((float)(x + width), (float)(y + width), (float)(x1 - width), (float)(y1 - width), internalColor);
        RenderingUtils.drawRect((float)(x + width), (float)y, (float)(x1 - width), (float)(y + width), borderColor);
        RenderingUtils.drawRect((float)x, (float)y, (float)(x + width), (float)y1, borderColor);
        RenderingUtils.drawRect((float)(x1 - width), (float)y, (float)x1, (float)y1, borderColor);
        RenderingUtils.drawRect((float)(x + width), (float)(y1 - width), (float)(x1 - width), (float)y1, borderColor);
}
    public static void drawGradientRectBordered(double left, double top, double right, double bottom, double width, int startColor, int endColor, int borderStartColor, int borderEndColor) {
        RenderingUtils.drawGradientRect(left + width, top + width, right - width, bottom - width, true, startColor, endColor);
        RenderingUtils.drawGradientRect(left + width, top, right - width, top + width, true, borderStartColor, borderEndColor);
        RenderingUtils.drawGradientRect(left, top, left + width, bottom, true, borderStartColor, borderEndColor);
        RenderingUtils.drawGradientRect(right - width, top, right, bottom, true, borderStartColor, borderEndColor);
        RenderingUtils.drawGradientRect(left + width, bottom - width, right - width, bottom, true, borderStartColor, borderEndColor);
}
    public static int applyOpacity(int color, float alpha) {
        int a = (int)((float)(color >> 24 & 0xFF) * alpha);
        return color & 0xFFFFFF | (a & 0xFF) << 24;
}
    public static void drawGuiBackground(int width, int height) {
        int top = -15464180;
        int bottom = -16382970;
        RenderingUtils.drawGradientRect(0.0, 0.0, width, height, false, top, bottom);
        long ms = System.currentTimeMillis();
        float pulse = (float)(0.5 + 0.5 * Math.sin((double)ms / 1400.0));
        int glow = RenderingUtils.alphaComponent(-1689274, (int)(30.0f + pulse * 40.0f));
        RenderingUtils.drawGradientRect(0.0, (double)height * 0.55, width, height, false, 0, glow);
}
}