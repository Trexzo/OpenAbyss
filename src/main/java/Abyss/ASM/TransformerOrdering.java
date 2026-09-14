/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.Launch
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package Abyss.ASM;

import Abyss.ASM.ClassNameFilterTransformer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransformerOrdering {
    private static final Logger m = LogManager.getLogger((String)"Abyss ASM Bootstrap");
    private static volatile boolean O;
    private static volatile boolean v;
    private static volatile boolean K;

    public static void E() {
        if (!K) {
            K = true;
            Thread var0 = new Thread(() -> {
                for (int var0x = 0; var0x < 600; ++var0x) {
                    if (TransformerOrdering.Q()) {
                        return;
}
                    try {
                        Thread.sleep(100L);
                        continue;
}
                    catch (InterruptedException var2) {
                        return;
}
}
            }, "Abyss transformer ordering");
            var0.setDaemon(true);
            var0.start();
}
}
    private TransformerOrdering() {
}
    private static boolean I(Object var0) {
        if (var0 == null) {
            return false;
}
        String var1 = ClassNameFilterTransformer.class.getName();
        if (var0.getClass().getName().equals(var1)) {
            return true;
}
        try {
            String var2 = String.valueOf(var0);
            if (var2.contains(var1)) {
                return true;
}
}
        catch (Throwable var2) {
            // empty catch block
}
        Object var4 = TransformerOrdering.A(var0, "parent");
        return var4 != null && var4 != var0 && TransformerOrdering.I(var4);
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void L() throws Throwable {
        Class<TransformerOrdering> clazz = TransformerOrdering.class;
        synchronized (TransformerOrdering.class) {
            if (System.getProperty("Abyss.bootstrap.done") == null) {
                System.setProperty("Abyss.bootstrap.done", "true");
                TransformerOrdering.E();
}
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
}
}
    private static Field f(Class<?> var0, String var1) throws NoSuchFieldException {
        for (Class<?> var2 = var0; var2 != null; var2 = var2.getSuperclass()) {
            try {
                return var2.getDeclaredField(var1);
}
            catch (NoSuchFieldException noSuchFieldException) {
                continue;
}
}
        throw new NoSuchFieldException(var1);
}
    public static boolean Q() {
        if (O) {
            return true;
}
        try {
            Field var0 = TransformerOrdering.f(Launch.classLoader.getClass(), "transformers");
            var0.setAccessible(true);
            List var1 = (List)var0.get(Launch.classLoader);
            ArrayList<Object> var2 = new ArrayList<Object>(var1);
            int var3 = -1;
            Object var4 = null;
            for (int var5 = 0; var5 < var2.size(); ++var5) {
                Object var6 = var2.get(var5);
                if (!TransformerOrdering.I(var6)) continue;
                var3 = var5;
                var4 = var6;
}
            if (var3 < 0) {
                if (!v) {
                    v = true;
                    m.info("Abyss ASM transformer not found yet. LaunchClassLoader transformer order: {}", new Object[]{TransformerOrdering.o(var2)});
}
                return false;
}
            if (var3 + 1 < var2.size()) {
                var2.remove(var3);
                var2.add(var4);
                var0.set(Launch.classLoader, new CopyOnWriteArrayList<Object>(var2));
                O = true;
                m.info("Moved Abyss ASM transformer to the end of LaunchClassLoader transformer list.");
                m.info("LaunchClassLoader transformer order: {}", new Object[]{TransformerOrdering.o(var2)});
                return true;
}
            if (!(var1 instanceof CopyOnWriteArrayList)) {
                var0.set(Launch.classLoader, new CopyOnWriteArrayList<Object>(var2));
}
            O = true;
            return true;
}
        catch (Throwable var7) {
            return false;
}
}
    private static Object A(Object var0, String var1) {
        for (Class<?> var2 = var0.getClass(); var2 != null; var2 = var2.getSuperclass()) {
            try {
                Field var3 = var2.getDeclaredField(var1);
                var3.setAccessible(true);
                return var3.get(var0);
}
            catch (Throwable throwable) {
                continue;
}
}
        return null;
}
    private static String o(List<Object> var0) {
        StringBuilder var1 = new StringBuilder();
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            if (var2 > 0) {
                var1.append(" -> ");
}
            Object var3 = var0.get(var2);
            var1.append(var2).append(':');
            if (var3 == null) {
                var1.append("null");
                continue;
}
            var1.append(var3);
}
        return var1.toString();
}
}