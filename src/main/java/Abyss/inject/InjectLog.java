/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class InjectLog {
    public static final boolean ENABLED = InjectLog.enabled();
    private static final Object LOCK = new Object();
    private static final Set<String> SWALLOWED = Collections.synchronizedSet(new HashSet());
    private static final ConcurrentLinkedQueue<String> PENDING = new ConcurrentLinkedQueue();
    private static final int MAX_PENDING = 8192;
    private static volatile Thread drainer;
    private static File file;
    private static boolean resolved;

    private static boolean enabled() {
        String quiet = System.getProperty("abyss.quiet");
        return !"1".equals(quiet) && !"true".equalsIgnoreCase(quiet);
}
    private InjectLog() {
}
    public static void line(String message) {
        if (!ENABLED) {
            return;
}
        InjectLog.enqueue("[abyss-inject] " + message);
}
    public static void lines(String prefix, Iterable<String> messages) {
        if (!ENABLED) {
            return;
}
        for (String message : messages) {
            InjectLog.enqueue("[abyss-inject] " + prefix + message);
}
}
    public static void swallowed(Throwable cause) {
        if (!ENABLED) {
            return;
}
        try {
            String where;
            StackTraceElement[] frames = cause.getStackTrace();
            String string = where = frames.length > 0 ? frames[0].getClassName() + "." + frames[0].getMethodName() + ":" + frames[0].getLineNumber() : "unknown";
            if (!SWALLOWED.add(where + " " + cause.getClass().getName() + " " + cause.getMessage())) {
                return;
}
            InjectLog.enqueue("[abyss-inject] swallowed at " + where + ": " + cause);
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public static void throwable(String message, Throwable cause) {
        if (!ENABLED) {
            return;
}
        InjectLog.enqueue("[abyss-inject] " + message + ": " + cause);
        for (StackTraceElement frame : cause.getStackTrace()) {
            InjectLog.enqueue("[abyss-inject]     at " + frame);
}
}
    private static void enqueue(String text) {
        if (PENDING.size() >= 8192) {
            return;
}
        PENDING.offer(text);
        InjectLog.startDrainer();
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void startDrainer() {
        if (drainer != null) {
            return;
}
        Class<InjectLog> clazz = InjectLog.class;
        synchronized (InjectLog.class) {
            if (drainer == null) {
                Thread thread = new Thread(new Runnable(){

                    @Override
                    public void run() {
                        InjectLog.runDrainer();
}
                }, "Abyss-InjectLog");
                thread.setDaemon(true);
                thread.setPriority(1);
                thread.start();
                drainer = thread;
}
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
}
}
    private static void runDrainer() {
        while (true) {
            String text;
            if ((text = PENDING.poll()) == null) {
                try {
                    Thread.sleep(250L);
}
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
}
}
            try {
                System.out.println(text);
}
            catch (Throwable throwable) {
                // empty catch block
}
            InjectLog.append(text);
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void append(String text) {
        Object object = LOCK;
        synchronized (object) {
            if (!resolved) {
                resolved = true;
                String temp = System.getProperty("java.io.tmpdir");
                if (temp != null) {
                    file = new File(temp, "abyss-inject.log");
}
}
            if (file == null) {
                return;
}
            Writer writer = null;
            try {
                writer = new OutputStreamWriter((OutputStream)new FileOutputStream(file, true), "UTF-8");
                writer.write(text);
                writer.write(System.getProperty("line.separator", "\n"));
}
            catch (Throwable throwable) {
}
            finally {
                if (writer != null) {
                    try {
                        writer.close();
}
                    catch (Throwable throwable) {}
}
}
}
}
}