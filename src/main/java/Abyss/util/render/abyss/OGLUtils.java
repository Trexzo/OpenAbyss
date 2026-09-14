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
    private static final FloatBuffer windowPosition = GLAllocation.func_74529_h((int)4);
    private static final IntBuffer viewport = GLAllocation.func_74527_f((int)16);
    private static final FloatBuffer modelMatrix = GLAllocation.func_74529_h((int)16);
    private static final FloatBuffer projectionMatrix = GLAllocation.func_74529_h((int)16);
    private static final float[] BUFFER = new float[3];

    private OGLUtils() {
}
    public static void enableBlending() {
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
}
    public static void disableTexture2D() {
        GlStateManager.func_179090_x();
}
    public static void enableTexture2D() {
        GlStateManager.func_179098_w();
}
    public static void enableDepth() {
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
}
    public static void disableDepth() {
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179097_i();
}
    public static void preDraw(int color, int mode) {
        OGLUtils.enableBlending();
        GlStateManager.func_179090_x();
        OGLUtils.color(color);
        GL11.glBegin((int)mode);
}
    public static void postDraw() {
        GL11.glEnd();
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
}
    public static void color(int color) {
        GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)((float)(color >> 24 & 0xFF) / 255.0f));
}
    public static void startScissorBox(ScaledResolution sr, int x, int y, int width, int height) {
        int sf = sr.func_78325_e();
        GL11.glScissor((int)(x * sf), (int)((sr.func_78328_b() - (y + height)) * sf), (int)(width * sf), (int)(height * sf));
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