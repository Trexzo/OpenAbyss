/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiDownloadTerrain
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.Display
 */
package Abyss.util.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.Display;

public final class StallWatchdog {
    private static final long POLL_MS = 100L;
    private static final long STALL_THRESHOLD_MS = 400L;
    private static final int MAX_SAMPLES = 12;
    private static final long MAX_LOG_BYTES = 0x400000L;
    private static final ConcurrentLinkedQueue<String> QUEUE = new ConcurrentLinkedQueue();
    private static volatile boolean started;
    private static volatile Thread gameThread;
    private static volatile long heartbeat;
    private static volatile boolean inWorld;

    private StallWatchdog() {
}
    public static void start() {
        if (started) {
            return;
}
        String gate = System.getProperty("abyss.watchdog");
        if ("0".equals(gate) || "false".equalsIgnoreCase(gate)) {
            return;
}
        started = true;
        Thread watchdog = new Thread(new Runnable(){

            @Override
            public void run() {
                StallWatchdog.runWatchdog();
}
        }, "Abyss-StallWatchdog");
        watchdog.setDaemon(true);
        watchdog.setPriority(1);
        watchdog.start();
        Thread drainer = new Thread(new Runnable(){

            @Override
            public void run() {
                StallWatchdog.runDrainer();
}
        }, "Abyss-StallWatchdog-IO");
        drainer.setDaemon(true);
        drainer.setPriority(1);
        drainer.start();
}
    public static void tick(boolean worldActive) {
        ++heartbeat;
        inWorld = worldActive;
        if (gameThread == null) {
            gameThread = Thread.currentThread();
}
}
    private static void runWatchdog() {
        long lastBeat = heartbeat;
        long lastBeatAt = System.nanoTime();
        boolean stalling = false;
        long stallStartMs = 0L;
        long stallBeat = 0L;
        long gcCountAtStart = 0L;
        long gcTimeAtStart = 0L;
        ArrayList<String> samples = new ArrayList<String>();
        while (true) {
            StallWatchdog.sleep(100L);
            long beat = heartbeat;
            long now = System.nanoTime();
            if (beat != lastBeat) {
                if (stalling) {
                    StallWatchdog.finishStall(stallStartMs, beat == stallBeat, samples, gcCountAtStart, gcTimeAtStart);
                    stalling = false;
                    samples.clear();
}
                lastBeat = beat;
                lastBeatAt = now;
                continue;
}
            long silentMs = (now - lastBeatAt) / 1000000L;
            if (silentMs < 400L) continue;
            if (!stalling) {
                if (!StallWatchdog.reportable()) continue;
                stalling = true;
                stallStartMs = System.currentTimeMillis() - silentMs;
                stallBeat = beat;
                gcCountAtStart = StallWatchdog.totalGcCount();
                gcTimeAtStart = StallWatchdog.totalGcTime();
                samples.clear();
                StallWatchdog.enqueue("STALL began ~" + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(stallStartMs)) + " (no game tick for " + silentMs + "ms)");
                StallWatchdog.enqueue("  inWorld=" + inWorld + " screen=" + StallWatchdog.screenName() + " windowActive=" + StallWatchdog.windowActive());
                samples.add(StallWatchdog.sample());
                continue;
}
            if (samples.size() >= 12 || samples.size() % 2 != 0) continue;
            samples.add(StallWatchdog.sample());
}
}
    private static void finishStall(long stallStartMs, boolean beatNeverAdvanced, List<String> samples, long gcCountAtStart, long gcTimeAtStart) {
        long endMs = System.currentTimeMillis();
        long duration = endMs - stallStartMs;
        long gcCount = StallWatchdog.totalGcCount() - gcCountAtStart;
        long gcTime = StallWatchdog.totalGcTime() - gcTimeAtStart;
        StallWatchdog.enqueue("STALL ended " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(endMs)) + " duration=" + duration + "ms");
        StallWatchdog.enqueue("  GC during stall: " + gcCount + " collection(s), " + gcTime + "ms" + (duration > 0L && gcTime * 100L / duration > 50L ? "  <-- GC-DOMINATED" : ""));
        if (beatNeverAdvanced) {
            StallWatchdog.enqueue("  heartbeat never advanced during the stall window");
}
        for (int i = 0; i < samples.size(); ++i) {
            StallWatchdog.enqueue("  sample #" + (i + 1) + ":");
            String[] lines = samples.get(i).split("\n");
            for (int j = 0; j < lines.length && j < 28; ++j) {
                StallWatchdog.enqueue("    " + lines[j]);
}
}
        Runtime rt = Runtime.getRuntime();
        StallWatchdog.enqueue("  heap used=" + (rt.totalMemory() - rt.freeMemory()) / 0x100000L + "M total=" + rt.totalMemory() / 0x100000L + "M max=" + rt.maxMemory() / 0x100000L + "M");
        StallWatchdog.enqueue("----");
}
    private static boolean reportable() {
        if (!inWorld) {
            return false;
}
        try {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.theWorld == null || mc.thePlayer == null) {
                return false;
}
            if (mc.currentScreen instanceof GuiDownloadTerrain) {
                return false;
}
            if (mc.isGamePaused()) {
                return false;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        return true;
}
    private static String screenName() {
        try {
            GuiScreen s = Minecraft.getMinecraft().currentScreen;
            return s == null ? "none" : s.getClass().getSimpleName();
}
        catch (Throwable t2) {
            return "?";
}
}
    private static boolean windowActive() {
        try {
            return Display.isActive();
}
        catch (Throwable t2) {
            return false;
}
}
    private static String sample() {
        Thread t2 = gameThread;
        if (t2 == null) {
            return "(game thread unknown)";
}
        StackTraceElement[] frames = t2.getStackTrace();
        StringBuilder sb = new StringBuilder(2048);
        sb.append(t2.getName()).append(" state=").append((Object)t2.getState()).append('\n');
        for (int i = 0; i < frames.length && i < 24; ++i) {
            sb.append("  at ").append(frames[i]).append('\n');
}
        return sb.toString();
}
    private static long totalGcCount() {
        long total = 0L;
        for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            total += Math.max(0L, bean.getCollectionCount());
}
        return total;
}
    private static long totalGcTime() {
        long total = 0L;
        for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            total += Math.max(0L, bean.getCollectionTime());
}
        return total;
}
    private static void enqueue(String line) {
        if (QUEUE.size() < 4096) {
            QUEUE.offer(line);
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private static void runDrainer() {
        while (true) {
            try {
                String line = QUEUE.poll();
                if (line == null) {
                    StallWatchdog.sleep(500L);
                    continue;
}
                File f = StallWatchdog.logFile();
                if (f == null) {
                    continue;
}
                if (f.length() > 0x400000L) {
                    File rotated = new File(f.getParentFile(), "stall.old.log");
                    if (rotated.exists()) {
                        rotated.delete();
}
                    f.renameTo(rotated);
}
                Writer writer = null;
                try {
                    writer = new OutputStreamWriter((OutputStream)new FileOutputStream(f, true), "UTF-8");
                    do {
                        writer.write(line);
                        writer.write(10);
                    } while ((line = QUEUE.poll()) != null);
}
                finally {
                    if (writer != null) {
                        try {
                            writer.close();
}
                        catch (Throwable ignored) {
}
}
}
}
            catch (Throwable ignored) {
                StallWatchdog.sleep(1000L);
}
}
}
    private static File logFile() {
        try {
            File dir;
            Minecraft mc = Minecraft.getMinecraft();
            File base = mc.mcDataDir;
            if (base == null) {
                base = new File(".");
}
            if (!(dir = new File(base, "Abyss" + File.separator + "logs")).isDirectory()) {
                dir.mkdirs();
}
            if (!dir.isDirectory()) {
                return null;
}
            return new File(dir, "stall.log");
}
        catch (Throwable t2) {
            return null;
}
}
    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
}
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
}
}
}