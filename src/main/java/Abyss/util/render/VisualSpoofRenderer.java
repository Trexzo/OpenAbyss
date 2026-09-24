/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL15
 */
package Abyss.util.render;

import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.ui.swing.VisualSpoofWindow;
import Abyss.util.MinecraftRef;
import Abyss.util.Sneaky;
import Abyss.util.render.VanillaHudRenderer;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class VisualSpoofRenderer {
    private static ByteBuffer q;
    private static boolean r;
    private static boolean D;
    private static int L;
    private static boolean x;
    private static final Minecraft v;
    private static VisualSpoofWindow I;
    private static long u;
    private static boolean z;
    private static boolean m;
    private static final double y = 0.25;
    private static final Object[] e;
    private static final int X = 120;
    private static double d;
    private static long E;
    private static final long c = 103002655662339L;
    private static final int g = 96;
    private static final int T = 8;
    private static int[] k;
    private static final int R = 60;
    private static int K;
    private static boolean F;
    private static final boolean N = true;
    private static final int P = 45;
    private static final int O = 35000;
    private static final String[] f;
    private static int Q;
    private static boolean b;
    private static final int o = 35051;
    private static final int[] G;
    private static int a;
    private static boolean U;
    private static boolean H;
    private static final int Y = 2;
    private static int j;
    private static BufferedImage B;
    private static Framebuffer h;
    private static boolean J;
    private static final double V = 0.25;
    private static int C;
    private static double p;
    private static final VanillaHudRenderer W;
    private static long i;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Framebuffer r(Minecraft var2) {
        if (var2.entityRenderer == null) {
            return null;
}
        float var7 = MinecraftAccessor.o((Minecraft)var2).renderPartialTicks;
        VisualSpoofRenderer.setFramebufferFilter(var2.displayWidth, var2.displayHeight);
        Framebuffer var8 = var2.getFramebuffer();
        try {
            H = true;
            MinecraftAccessor.K(var2, h);
            h.setFramebufferColor(0.0f, 0.0f, 0.0f, 1.0f);
            h.bindFramebuffer(true);
            h.framebufferClear();
            h.bindFramebuffer(true);
            var2.entityRenderer.renderWorld(var7, System.nanoTime());
            h.bindFramebuffer(true);
            W.e(var2, var7);
            Framebuffer framebuffer = h;
            return framebuffer;
}
        catch (Throwable var14) {
            Framebuffer framebuffer = null;
            return framebuffer;
}
        finally {
            H = false;
            MinecraftAccessor.K(var2, var8);
            if (var8 != null) {
                var8.bindFramebuffer(true);
}
            GL11.glViewport((int)0, (int)0, (int)var2.displayWidth, (int)var2.displayHeight);
            VisualSpoofRenderer.G();
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void l(float var0) {
        if (z && !H) {
            if (v == null || VisualSpoofRenderer.v.thePlayer == null || VisualSpoofRenderer.v.theWorld == null || VisualSpoofRenderer.v.displayWidth <= 0 || VisualSpoofRenderer.v.displayHeight <= 0) {
                r = false;
            } else if (VisualSpoofRenderer.v.currentScreen == null && r) {
                long var1 = System.nanoTime();
                if (!VisualSpoofRenderer.o(v, var1)) {
                    r = false;
                } else {
                    VisualSpoofRenderer.z(VisualSpoofRenderer.v.displayWidth, VisualSpoofRenderer.v.displayHeight);
                    long var3 = System.nanoTime();
                    BufferedImage var5 = null;
                    try {
                        h.bindFramebuffer(true);
                        W.e(v, var0);
                        var5 = VisualSpoofRenderer.I(VisualSpoofRenderer.v.displayWidth, VisualSpoofRenderer.v.displayHeight);
}
                    catch (Throwable var11) {
                        var5 = null;
}
                    finally {
                        Framebuffer var8 = v.getFramebuffer();
                        if (var8 != null) {
                            var8.bindFramebuffer(true);
}
                        GL11.glViewport((int)0, (int)0, (int)VisualSpoofRenderer.v.displayWidth, (int)VisualSpoofRenderer.v.displayHeight);
                        VisualSpoofRenderer.G();
                        r = false;
}
                    if (var5 != null && I != null) {
                        I.o(var5);
}
                    VisualSpoofRenderer.G(v, var1, var3);
}
            } else {
                r = false;
}
}
}
    private static boolean o(Minecraft var0, long var1) {
        if (var1 < E) {
            return false;
}
        if (VisualSpoofRenderer.u$r1()) {
            return false;
}
        long var3 = VisualSpoofRenderer.Z(var0);
        E = Math.max(var1, var1 + var3);
        return true;
}
    private static void T() {
        try {
            GL15.glBindBuffer((int)35051, (int)0);
            for (int var0 = 0; var0 < 2; ++var0) {
                if (G[var0] == 0) continue;
                GL15.glDeleteBuffers((int)G[var0]);
                VisualSpoofRenderer.G[var0] = 0;
}
}
        finally {
            j = 0;
            Q = 0;
            C = 0;
            L = 0;
            F = false;
}
}
    public static Framebuffer f(long var0) {
        var0 = 0x5DAE2CB3A103L ^ var0;
        if (v == null || VisualSpoofRenderer.v.displayWidth <= 0 || VisualSpoofRenderer.v.displayHeight <= 0) {
            return null;
}
        return VisualSpoofRenderer.v.currentScreen != null ? VisualSpoofRenderer.i(v) : VisualSpoofRenderer.r(v);
}
    private static long Z(Minecraft var0) {
        int var1 = var0 != null && var0.currentScreen == null ? 120 : 60;
        long var2 = 1000000000L / (long)Math.max(1, var1);
        if (var0 != null && var0.currentScreen == null) {
            return var2;
}
        int var4 = 1;
        if (d > 0.0) {
            long var5 = VisualSpoofRenderer.j();
            var4 = Math.max(var4, VisualSpoofRenderer.y(d, var5));
}
        if (i > 19999999L) {
            var4 = Math.max(var4, 2);
}
        if (var0 != null && var0.theWorld != null && var0.theWorld.loadedEntityList != null && var0.theWorld.loadedEntityList.size() > 96) {
            int var9 = Math.max(32, 48);
            int var6 = var0.theWorld.loadedEntityList.size() - 96;
            int var7 = 1 + (var6 + var9 - 1) / var9;
            var4 = Math.max(var4, Math.min(8, var7));
}
        return var2 * (long)Math.min(8, var4);
}
    private static void t() {
        VisualSpoofRenderer.z(v != null ? VisualSpoofRenderer.v.displayWidth : 960, v != null ? VisualSpoofRenderer.v.displayHeight : 540);
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Framebuffer i(Minecraft var0) {
        float var7 = MinecraftAccessor.o((Minecraft)var0).renderPartialTicks;
        VisualSpoofRenderer.setFramebufferFilter(var0.displayWidth, var0.displayHeight);
        Framebuffer var8 = var0.getFramebuffer();
        try {
            H = true;
            MinecraftAccessor.K(var0, h);
            h.setFramebufferColor(0.0f, 0.0f, 0.0f, 1.0f);
            h.bindFramebuffer(true);
            h.framebufferClear();
            h.bindFramebuffer(true);
            if (var0.theWorld != null && var0.thePlayer != null && var0.entityRenderer != null) {
                var0.entityRenderer.renderWorld(var7, System.nanoTime());
                h.bindFramebuffer(true);
                if (var0.ingameGUI != null) {
                    var0.ingameGUI.renderGameOverlay(var7);
                    h.bindFramebuffer(true);
}
}
            if (var0.currentScreen != null) {
                ScaledResolution var9 = new ScaledResolution(var0);
                int var10 = Math.max(0, Math.min(var9.getScaledWidth(), Mouse.getX() * var9.getScaledWidth() / Math.max(1, var0.displayWidth)));
                int var11 = Math.max(0, Math.min(var9.getScaledHeight(), var9.getScaledHeight() - Mouse.getY() * var9.getScaledHeight() / Math.max(1, var0.displayHeight) - 1));
                var0.currentScreen.drawScreen(var10, var11, var7);
                h.bindFramebuffer(true);
}
            return h;
}
        catch (Throwable var15) {
            Framebuffer framebuffer = null;
            return framebuffer;
}
        finally {
            H = false;
            MinecraftAccessor.K(var0, var8);
            if (var8 != null) {
                var8.bindFramebuffer(true);
}
            GL11.glViewport((int)0, (int)0, (int)var0.displayWidth, (int)var0.displayHeight);
            VisualSpoofRenderer.G();
}
}
    private static void C(ByteBuffer var0, int var1, int var2) {
        IntBuffer var3 = var0.order(ByteOrder.nativeOrder()).asIntBuffer();
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5 = (var2 - 1 - var4) * var1;
            int var6 = var4 * var1;
            ((Buffer)var3).position(var5);
            var3.get(k, var6, var1);
            for (int var7 = 0; var7 < var1; ++var7) {
                VisualSpoofRenderer.k[var6 + var7] = k[var6 + var7] | 0xFF000000;
}
}
}
    private static void a() {
        VisualSpoofRenderer.e[0] = "g\u00061=n0m";
        VisualSpoofRenderer.e[1] = Boolean.TYPE;
        VisualSpoofRenderer.f[1] = "java/lang/Boolean";
        VisualSpoofRenderer.e[2] = Void.TYPE;
        VisualSpoofRenderer.f[2] = "java/lang/Void";
        VisualSpoofRenderer.e[3] = "@-HCgtK\"Y\f\u0006z@)]V";
        VisualSpoofRenderer.e[4] = "s07\u0001\u0013xex*n\u0016Gfa*\u0002\u0010vh9,nM%,y;SI5p{E";
}
    private static int y(double var0, long var2) {
        return var2 > 0L && !(var0 <= (double)var2) ? (int)Math.min(8.0, Math.ceil(var0 / (double)var2)) : 1;
}
    private static long j() {
        return Math.max(1500000L, 7407407L);
}
    private static BufferedImage I(int var0, int var1) {
        h.bindFramebuffer(false);
        GL11.glReadBuffer((int)OpenGlHelper.GL_COLOR_ATTACHMENT0);
        return VisualSpoofRenderer.attribute(var0, var1);
}
    public static boolean H() {
        return H;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static BufferedImage A(Minecraft var0) {
        if (var0.getFramebuffer() == null) {
            return null;
}
        Framebuffer var1 = var0.getFramebuffer();
        try {
            var1.bindFramebuffer(false);
            GL11.glReadBuffer((int)OpenGlHelper.GL_COLOR_ATTACHMENT0);
            BufferedImage bufferedImage = VisualSpoofRenderer.attribute(var0.displayWidth, var0.displayHeight);
            return bufferedImage;
}
        catch (Throwable var7) {
            BufferedImage bufferedImage = null;
            return bufferedImage;
}
        finally {
            var1.unbindFramebuffer();
            GL11.glReadBuffer((int)1029);
            GL11.glViewport((int)0, (int)0, (int)var0.displayWidth, (int)var0.displayHeight);
            VisualSpoofRenderer.G();
}
}
    private static void f(int var0, int var1) {
        int var2 = var0 * var1 * 4;
        if (G[0] == 0 || Q != var0 || C != var1 || L != var2) {
            VisualSpoofRenderer.T();
            Q = var0;
            C = var1;
            L = var2;
            j = 0;
            F = false;
            for (int var3 = 0; var3 < 2; ++var3) {
                int var4;
                VisualSpoofRenderer.G[var3] = var4 = GL15.glGenBuffers();
                GL15.glBindBuffer((int)35051, (int)var4);
                GL15.glBufferData((int)35051, (long)var2, (int)35041);
}
            GL15.glBindBuffer((int)35051, (int)0);
}
}
    public static void T(boolean var0) {
        J = var0;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static BufferedImage C(int var0, int var1) {
        VisualSpoofRenderer.f(var0, var1);
        int var2 = j;
        int var3 = (j + 1) % 2;
        int var4 = G[var2];
        int var5 = G[var3];
        GL11.glPixelStorei((int)3333, (int)4);
        GL15.glBindBuffer((int)35051, (int)var4);
        GL15.glBufferData((int)35051, (long)L, (int)35041);
        GL11.glReadPixels((int)0, (int)0, (int)var0, (int)var1, (int)32993, (int)33639, (long)0L);
        BufferedImage var6 = null;
        if (F) {
            GL15.glBindBuffer((int)35051, (int)var5);
            ByteBuffer var7 = GL15.glMapBuffer((int)35051, (int)35000, (long)L, null);
            if (var7 != null) {
                try {
                    VisualSpoofRenderer.C(var7, var0, var1);
                    var6 = B;
}
                finally {
                    GL15.glUnmapBuffer((int)35051);
}
}
}
        GL15.glBindBuffer((int)35051, (int)0);
        j = var3;
        F = true;
        return var6;
}
    private VisualSpoofRenderer() {
}
    public static void P() {
        x = false;
        r = false;
        if (z) {
            long var4;
            long var0 = System.nanoTime();
            long var2 = u;
            u = var0;
            if (var2 > 0L && (var4 = var0 - var2) > 0L && var4 <= 1000000000L) {
                i = var4;
                p = p <= 0.0 ? (double)var4 : p * 0.75 + (double)var4 * 0.25;
}
}
}
    private static void V() {
        if (v != null && VisualSpoofRenderer.v.gameSettings != null) {
            D = VisualSpoofRenderer.v.gameSettings.pauseOnLostFocus;
            U = true;
            VisualSpoofRenderer.v.gameSettings.pauseOnLostFocus = false;
}
}
    private static void y() {
        if (I != null) {
            I.H();
            I = null;
}
}
    public static void f() {
        if (z && !H && v != null && VisualSpoofRenderer.v.thePlayer != null && VisualSpoofRenderer.v.theWorld != null && VisualSpoofRenderer.v.displayWidth > 0 && VisualSpoofRenderer.v.displayHeight > 0 && VisualSpoofRenderer.v.currentScreen == null) {
            VisualSpoofRenderer.setFramebufferFilter(VisualSpoofRenderer.v.displayWidth, VisualSpoofRenderer.v.displayHeight);
            Framebuffer var0 = v.getFramebuffer();
            if (var0 != null) {
                try {
                    h.setFramebufferColor(0.0f, 0.0f, 0.0f, 1.0f);
                    h.bindFramebuffer(true);
                    h.framebufferClear();
                    h.bindFramebuffer(true);
                    var0.framebufferRenderExt(VisualSpoofRenderer.v.displayWidth, VisualSpoofRenderer.v.displayHeight, true);
                    r = true;
}
                catch (Throwable var5) {
                    r = false;
}
                finally {
                    var0.bindFramebuffer(true);
                    GL11.glViewport((int)0, (int)0, (int)VisualSpoofRenderer.v.displayWidth, (int)VisualSpoofRenderer.v.displayHeight);
                    VisualSpoofRenderer.G();
}
}
}
}
    private static void J(long var0) {
        if (var0 > 0L && var0 <= 1000000000L) {
            d = d <= 0.0 ? (double)var0 : d * 0.75 + (double)var0 * 0.25;
}
}
    private static boolean u$r1() {
        return i > 22222222L ? true : p > 0.0 && p > 2.2222222E7;
}
    private static void z(int var0, int var1) {
        if (I == null) {
            I = new VisualSpoofWindow(() -> {
                try {
                    long var0x = 134931596987523L;
                    VisualSpoofRenderer.j(false);
}
                catch (Throwable ex) {
                    throw Sneaky.rethrow(ex);
}
            });
}
        I.i(Math.max(1, var0), Math.max(1, var1));
}
    private static void C() {
        if (h != null) {
            h.deleteFramebuffer();
            h = null;
}
        K = 0;
        a = 0;
        q = null;
        B = null;
        k = null;
        VisualSpoofRenderer.T();
        b = true;
}
    private static BufferedImage W(int var0, int var1) {
        GL11.glPixelStorei((int)3333, (int)4);
        ((Buffer)q).clear();
        GL11.glReadPixels((int)0, (int)0, (int)var0, (int)var1, (int)32993, (int)33639, (ByteBuffer)q);
        VisualSpoofRenderer.C(q, var0, var1);
        return B;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static BufferedImage M(short var0, int var1, Minecraft var2, int var3, int var4, short var5, float var6) {
        long var7 = ((long)var0 << 48 | (long)var1 << 32 >>> 16 | (long)var5 << 48 >>> 48) ^ 0x5DAE2CB3A103L;
        VisualSpoofRenderer.setFramebufferFilter(var2.displayWidth, var2.displayHeight);
        Framebuffer var11 = var2.getFramebuffer();
        try {
            H = true;
            MinecraftAccessor.K(var2, h);
            h.setFramebufferColor(0.0f, 0.0f, 0.0f, 1.0f);
            h.bindFramebuffer(true);
            h.framebufferClear();
            h.bindFramebuffer(true);
            if (var2.theWorld != null && var2.thePlayer != null && var2.entityRenderer != null) {
                var2.entityRenderer.renderWorld(var6, System.nanoTime());
                h.bindFramebuffer(true);
                if (var2.ingameGUI != null) {
                    var2.ingameGUI.renderGameOverlay(var6);
                    h.bindFramebuffer(true);
}
}
            if (var2.currentScreen != null) {
                var2.currentScreen.drawScreen(var3, var4, var6);
                h.bindFramebuffer(true);
}
            BufferedImage bufferedImage = VisualSpoofRenderer.I(var2.displayWidth, var2.displayHeight);
            return bufferedImage;
}
        catch (Throwable var17) {
            BufferedImage bufferedImage = null;
            return bufferedImage;
}
        finally {
            H = false;
            MinecraftAccessor.K(var2, var11);
            if (var11 != null) {
                var11.bindFramebuffer(true);
}
            GL11.glViewport((int)0, (int)0, (int)var2.displayWidth, (int)var2.displayHeight);
            VisualSpoofRenderer.G();
}
}
    private static void setFramebufferFilter(int var0, int var1) {
        if (h == null || K != var0 || a != var1) {
            VisualSpoofRenderer.C();
            h = new Framebuffer(var0, var1, true);
            h.setFramebufferFilter(9729);
            K = var0;
            a = var1;
}
}
    public static boolean x() {
        return m;
}
    private static void G(Minecraft var0, long var1, long var3) {
        VisualSpoofRenderer.J(System.nanoTime() - var3);
        long var5 = VisualSpoofRenderer.Z(var0);
        E = Math.max(System.nanoTime(), var1 + var5);
}
    private static void u() {
        if (U && v != null && VisualSpoofRenderer.v.gameSettings != null) {
            VisualSpoofRenderer.v.gameSettings.pauseOnLostFocus = D;
}
        U = false;
}
    public static boolean B() {
        return J;
}
    public static void q(boolean var0) {
        m = var0;
}
    public static void L(int var0, int var1, float var2, long var3) {
        long var8;
        var3 = 0x5DAE2CB3A103L ^ var3;
        int var5 = (int)((var3 ^ 0x2B52A23AFFEEL) >>> 48);
        int var6 = (int)((var3 ^ 0x2B52A23AFFEEL) << 16 >>> 32);
        int var7 = (int)((var3 ^ 0x2B52A23AFFEEL) << 48 >>> 48);
        if (z && !H && !x && v != null && VisualSpoofRenderer.v.currentScreen != null && VisualSpoofRenderer.v.displayWidth > 0 && VisualSpoofRenderer.v.displayHeight > 0 && VisualSpoofRenderer.o(v, var8 = System.nanoTime())) {
            VisualSpoofRenderer.z(VisualSpoofRenderer.v.displayWidth, VisualSpoofRenderer.v.displayHeight);
            long var10 = System.nanoTime();
            BufferedImage var12 = VisualSpoofRenderer.M((short)var5, var6, v, var0, var1, (short)var7, var2);
            if (var12 != null && I != null) {
                x = true;
                I.o(var12);
}
            VisualSpoofRenderer.G(v, var8, var10);
}
}
    private static void G() {
        GL11.glMatrixMode((int)5888);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc((int)516, (float)0.1f);
}
    public static boolean h() {
        return z;
}
    public static boolean Z(long var0) {
        VisualSpoofRenderer.j(!z);
        return z;
}
    public static void j(boolean var0) {
        z = var0;
        E = 0L;
        u = 0L;
        i = 0L;
        p = 0.0;
        d = 0.0;
        x = false;
        if (!var0) {
            H = false;
            VisualSpoofRenderer.u();
            VisualSpoofRenderer.y();
            VisualSpoofRenderer.C();
        } else {
            VisualSpoofRenderer.V();
            VisualSpoofRenderer.t();
}
}
    private static void A(int var0, int var1) {
        int var2 = var0 * var1 * 4;
        if (q == null || q.capacity() < var2) {
            q = BufferUtils.createByteBuffer((int)var2);
}
        if (B == null || B.getWidth() != var0 || B.getHeight() != var1) {
            B = new BufferedImage(var0, var1, 1);
            k = ((DataBufferInt)B.getRaster().getDataBuffer()).getData();
}
}
    private static BufferedImage attribute(int var0, int var1) {
        VisualSpoofRenderer.A(var0, var1);
        if (b) {
            try {
                return VisualSpoofRenderer.C(var0, var1);
}
            catch (Throwable var3) {
                b = false;
                VisualSpoofRenderer.T();
}
}
        return VisualSpoofRenderer.W(var0, var1);
}
    static {
        r = false;
        D = true;
        L = 0;
        x = false;
        u = 0L;
        z = false;
        m = false;
        e = new Object[5];
        d = 0.0;
        E = 0L;
        K = 0;
        F = false;
        f = new String[5];
        Q = 0;
        b = true;
        G = new int[2];
        a = 0;
        U = false;
        H = false;
        j = 0;
        J = false;
        C = 0;
        p = 0.0;
        W = new VanillaHudRenderer();
        i = 0L;
        boolean var2 = false;
        VisualSpoofRenderer.a();
        v = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}