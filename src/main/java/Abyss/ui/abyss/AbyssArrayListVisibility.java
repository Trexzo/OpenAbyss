/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.client.Minecraft
 */
package Abyss.ui.abyss;

import Abyss.module.Module;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;

public final class AbyssArrayListVisibility {
    private static final String FILE = "abyss-arraylist.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Object LOCK = new Object();
    private static final ExecutorService SAVE_THREAD = Executors.newSingleThreadExecutor();
    private static volatile Set<String> hidden;

    private AbyssArrayListVisibility() {
}
    private static String key(Module m2) {
        if (m2 == null) {
            return null;
}
        try {
            String n2 = m2.name();
            if (n2 != null) {
                return n2;
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        try {
            return m2.b();
}
        catch (Throwable throwable) {
            return null;
}
}
    private static File file() {
        File dir = null;
        try {
            dir = Minecraft.func_71410_x().field_71412_D;
}
        catch (Throwable throwable) {
            // empty catch block
}
        File d = dir == null ? new File("Abyss") : new File(dir, "Abyss");
        try {
            d.mkdirs();
}
        catch (Throwable throwable) {
            // empty catch block
}
        return new File(d, FILE);
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Set<String> data() {
        Set<String> local = hidden;
        if (local != null) {
            return local;
}
        Object object = LOCK;
        synchronized (object) {
            if (hidden != null) {
                return hidden;
}
            LinkedHashSet<String> loaded = new LinkedHashSet<String>();
            Reader r2 = null;
            try {
                Type t2;
                Set parsed;
                File f = AbyssArrayListVisibility.file();
                if (f.exists() && (parsed = (Set)GSON.fromJson(r2 = new InputStreamReader((InputStream)new FileInputStream(f), "UTF-8"), t2 = new TypeToken<LinkedHashSet<String>>(){}.getType())) != null) {
                    loaded.addAll(parsed);
}
}
            catch (Throwable throwable) {
}
            finally {
                if (r2 != null) {
                    try {
                        r2.close();
}
                    catch (Throwable throwable) {}
}
}
            hidden = loaded;
            return hidden;
}
}
    private static void save() {
        final LinkedHashSet snapshot = new LinkedHashSet(hidden == null ? Collections.emptySet() : hidden);
        SAVE_THREAD.execute(new Runnable(){

            @Override
            public void run() {
                Writer w2 = null;
                try {
                    File f = AbyssArrayListVisibility.file();
                    w2 = new OutputStreamWriter((OutputStream)new FileOutputStream(f), "UTF-8");
                    GSON.toJson((Object)snapshot, (Appendable)w2);
                    w2.flush();
}
                catch (Throwable throwable) {
}
                finally {
                    if (w2 != null) {
                        try {
                            w2.close();
}
                        catch (Throwable throwable) {}
}
}
}
        });
}
    public static void preload() {
        AbyssArrayListVisibility.data();
}
    public static boolean isShown(Module m2) {
        String k = AbyssArrayListVisibility.key(m2);
        if (k == null) {
            return true;
}
        try {
            return !AbyssArrayListVisibility.data().contains(k);
}
        catch (Throwable ignored) {
            return true;
}
}
    public static void toggle(Module m2) {
        AbyssArrayListVisibility.setShown(m2, !AbyssArrayListVisibility.isShown(m2));
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setShown(Module m2, boolean shown) {
        String k = AbyssArrayListVisibility.key(m2);
        if (k == null) {
            return;
}
        try {
            Object object = LOCK;
            synchronized (object) {
                boolean changed;
                Set<String> set = AbyssArrayListVisibility.data();
                boolean bl = changed = shown ? set.remove(k) : set.add(k);
                if (changed) {
                    AbyssArrayListVisibility.save();
}
}
}
        catch (Throwable throwable) {
            // empty catch block
}
}
}