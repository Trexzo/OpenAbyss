/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.util;

import net.minecraft.client.Minecraft;

public final class DeferredRendererReload {
    private static volatile boolean pending;
    private static long lastReloadMs;
    private static final long COOLDOWN_MS = 3000L;

    private DeferredRendererReload() {
}
    public static void request() {
        pending = true;
}
    public static void flush() {
        if (pending) {
            long now = System.currentTimeMillis();
            if (now - lastReloadMs < 3000L) {
                return;
}
            pending = false;
            lastReloadMs = now;
            try {
                Minecraft mc = Minecraft.getMinecraft();
                if (mc != null && mc.renderGlobal != null) {
                    mc.renderGlobal.loadRenderers();
}
}
            catch (Throwable throwable) {
                // empty catch block
}
}
}
}