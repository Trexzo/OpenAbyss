/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GLAllocation
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package Abyss.util.render.abyss;

import Abyss.util.render.abyss.LockedResolution;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public final class OGLUtils {
    private static final FloatBuffer windowPosition = GLAllocation.createDirectFloatBuffer((int)4);
    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer((int)16);
    private static final FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer((int)16);
    private static final FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer((int)16);
    private static final float[] BUFFER = new float[3];

    private OGLUtils() {
}
    public static void enableBlending() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((int)770, (int)771);
}
    public static void disableTexture2D() {
        GlStateManager.disableTexture2D();
}
    public static void enableTexture2D() {
        GlStateManager.enableTexture2D();
}
    public static void enableDepth() {
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
}
    public static void disableDepth() {
        GlStateManager.depthMask((boolean)false);
        GlStateManager.disableDepth();
}
    public static void preDraw(int color, int mode) {
        OGLUtils.enableBlending();
        GlStateManager.disableTexture2D();
        OGLUtils.color(color);
        GL11.glBegin((int)mode);
}
    public static void postDraw() {
        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
}
    public static void color(int color) {
        GlStateManager.color((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)((float)(color >> 24 & 0xFF) / 255.0f));
}
    public static void startScissorBox(ScaledResolution sr, int x, int y, int width, int height) {
        int sf = sr.getScaleFactor();
        GL11.glScissor((int)(x * sf), (int)((sr.getScaledHeight() - (y + height)) * sf), (int)(width * sf), (int)(height * sf));
}
    public static void startScissorBox(LockedResolution lr, int x, int y, int width, int height) {
        GL11.glScissor((int)(x * 2), (int)((lr.getHeight() - (y + height)) * 2), (int)(width * 2), (int)(height * 2));
}
    public static float[] project2D(float x, float y, float z, float coordinateScale) {
        if (!Float.isFinite(coordinateScale) || coordinateScale <= 0.0f) {
            return null;
}
        GL11.glGetFloat((int)2982, (FloatBuffer)modelMatrix);
        GL11.glGetFloat((int)2983, (FloatBuffer)projectionMatrix);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        if (GLU.gluProject((float)x, (float)y, (float)z, (FloatBuffer)modelMatrix, (FloatBuffer)projectionMatrix, (IntBuffer)viewport, (FloatBuffer)windowPosition)) {
            float projectedX = windowPosition.get(0) / coordinateScale;
            float projectedY = ((float)Display.getHeight() - windowPosition.get(1)) / coordinateScale;
            float projectedZ = windowPosition.get(2);
            if (!(Float.isFinite(projectedX) && Float.isFinite(projectedY) && Float.isFinite(projectedZ))) {
                return null;
}
            OGLUtils.BUFFER[0] = projectedX;
            OGLUtils.BUFFER[1] = projectedY;
            OGLUtils.BUFFER[2] = projectedZ;
            return BUFFER;
}
        return null;
}
}