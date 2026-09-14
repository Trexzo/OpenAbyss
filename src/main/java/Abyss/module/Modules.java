/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module;

import Abyss.internal.jnic.StockConfigStore;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Modules {
        
    private static String b;
    private static long[] c;
    private static boolean G;
    public static volatile boolean gatesweep$configSaveUnavailable;
    private static volatile long pendingSaveAt;
    private static final long SAVE_DEBOUNCE_MS = 1000L;
    private static final ExecutorService SAVE_EXEC;

    public static <T extends Module> T J(Class<T> var0) {
        return (T)ModuleManager.o.get(var0);
}
    public static Module I(String var0) {
        if (var0 == null) {
            return null;
}
        for (Module var2 : ModuleManager.S) {
            if (!var2.b().equalsIgnoreCase(var0)) continue;
            return var2;
}
        return null;
}
    public static void c(long var0) {
        pendingSaveAt = System.currentTimeMillis();
}
    public static void flushPendingSave() {
        long ts = pendingSaveAt;
        if (ts == 0L) {
            return;
}
        if (System.currentTimeMillis() - ts < 1000L) {
            return;
}
        pendingSaveAt = 0L;
        if (!G) {
            G = true;
            SAVE_EXEC.execute(() -> {
                try {
                    StockConfigStore.o(b);
}
                catch (UnsatisfiedLinkError var5x) {
                    gatesweep$configSaveUnavailable = true;
}
                finally {
                    G = false;
}
            });
}
}
    static {
        SAVE_EXEC = Executors.newSingleThreadExecutor(r2 -> {
            Thread t2 = new Thread(r2, "Abyss-ConfigSave");
            t2.setDaemon(true);
            t2.setPriority(1);
            return t2;
        });
        G = false;
        b = "current";
        e = new HashMap(13);
        c = new long[]{-7749740435155550923L, -7864304823227945179L, 3071478632644015716L};
}
}