/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject.mem;

import Abyss.inject.mem.MemConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Map;

final class MemHandler
extends URLStreamHandler {
    private final Map<String, byte[]> entries;

    MemHandler(Map<String, byte[]> entries) {
        this.entries = entries;
}
    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        String key = MemHandler.keyOf(url);
        byte[] data = this.entries.get(key);
        if (data == null) {
            throw new IOException("no in-memory entry");
}
        return new MemConnection(url, data);
}
    private static String keyOf(URL url) {
        String path = url.getPath();
        if (path == null) {
            path = "";
}
        if (path.startsWith("/")) {
            path = path.substring(1);
}
        return MemHandler.decode(path);
}
    private static String decode(String s) {
        if (s.indexOf(37) < 0) {
            return s;
}
        ByteArrayOutputStream out = new ByteArrayOutputStream(s.length());
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '%' && i + 2 < s.length()) {
                int hi = Character.digit(s.charAt(i + 1), 16);
                int lo = Character.digit(s.charAt(i + 2), 16);
                if (hi >= 0 && lo >= 0) {
                    out.write((hi << 4) + lo);
                    i += 2;
                    continue;
}
}
            out.write(c);
}
        try {
            return out.toString("UTF-8");
}
        catch (UnsupportedEncodingException e) {
            return out.toString();
}
}
}