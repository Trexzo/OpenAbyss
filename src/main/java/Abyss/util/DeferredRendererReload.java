/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.minecraft.client.Minecraft;

public final class DeferredRendererReload {
    private static volatile boolean pending;
    private static long lastReloadMs;
    private static final long COOLDOWN_MS = 3000L;
    private static final int MAX_RETRIES = 2;
    private static int retryCount;
    private static volatile String lastFailure;

    private DeferredRendererReload() {
}
    public static void request() {
        retryCount = 0;
        pending = true;
}
    public static void flush() {
        if (!pending) {
            return;
}
        long now = System.currentTimeMillis();
        if (now - lastReloadMs < COOLDOWN_MS) {
            return;
}
        lastReloadMs = now;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.renderGlobal == null) {
            return;
}
        pending = false;
        try {
            mc.renderGlobal.loadRenderers();
            retryCount = 0;
}
        catch (Throwable throwable) {
            DeferredRendererReload.recordFailure(throwable);
            if (retryCount < MAX_RETRIES) {
                ++retryCount;
                pending = true;
}
}
}
    private static void recordFailure(Throwable failure) {
        String detail = failure.getClass().getName() + ": "
                + String.valueOf(failure.getMessage()).replace('\r', ' ').replace('\n', ' ');
        if (detail.equals(lastFailure)) {
            return;
}
        lastFailure = detail;
        String line = System.currentTimeMillis() + "\t" + detail;
        try {
            try (OutputStreamWriter out = new OutputStreamWriter(
                    (OutputStream)new FileOutputStream(new File("abyss-renderer-failure.txt"), true), "UTF-8");){
                out.write(line + "\n");
}
}
        catch (Throwable ignored) {
}
        System.err.println("[ABYSSDIAG] deferred renderer reload failure " + line);
}
}
