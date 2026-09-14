/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject.mem;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

final class MemConnection
extends URLConnection {
    private final byte[] data;

    MemConnection(URL url, byte[] data) {
        super(url);
        this.data = data;
}
    @Override
    public void connect() {
        this.connected = true;
}
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.data);
}
    @Override
    public int getContentLength() {
        return this.data.length;
}
    @Override
    public long getContentLengthLong() {
        return this.data.length;
}
    @Override
    public String getContentType() {
        return "application/octet-stream";
}
}