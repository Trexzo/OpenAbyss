/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject.mem;

import Abyss.inject.mem.MemHandler;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class MemLoader {
    private static volatile Map<String, byte[]> indexed;

    private MemLoader() {
}
    public static Set<String> classNames() {
        Map<String, byte[]> entries = indexed;
        HashSet<String> names = new HashSet<String>();
        if (entries == null) {
            return names;
}
        for (String name : entries.keySet()) {
            if (!name.endsWith(".class")) continue;
            names.add(name.substring(0, name.length() - ".class".length()));
}
        return names;
}
    public static URL prepare(ClassLoader loader, byte[] jarBytes) {
        Map<String, byte[]> entries;
        if (loader == null || jarBytes == null || jarBytes.length == 0) {
            return null;
}
        try {
            entries = MemLoader.index(jarBytes);
}
        catch (IOException e) {
            return null;
}
        if (entries.isEmpty()) {
            return null;
}
        indexed = entries;
        try {
            return new URL("abyssmem", "abyss", -1, "/", new MemHandler(entries));
}
        catch (Exception e) {
            return null;
}
}
    public static boolean attachReflective(ClassLoader loader, URL url) {
        if (loader == null || url == null || !(loader instanceof URLClassLoader)) {
            return false;
}
        try {
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
            addURL.invoke((Object)loader, url);
            return true;
}
        catch (Throwable t2) {
            return false;
}
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Map<String, byte[]> index(byte[] jarBytes) throws IOException {
        HashMap<String, byte[]> entries = new HashMap<String, byte[]>();
        ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(jarBytes));
        try {
            ZipEntry entry;
            byte[] buffer = new byte[8192];
            while ((entry = zip.getNextEntry()) != null) {
                int n2;
                if (entry.isDirectory()) continue;
                ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
                while ((n2 = zip.read(buffer)) > 0) {
                    out.write(buffer, 0, n2);
}
                entries.put(entry.getName(), out.toByteArray());
}
}
        finally {
            try {
                zip.close();
}
            catch (IOException iOException) {}
}
        return entries;
}
}