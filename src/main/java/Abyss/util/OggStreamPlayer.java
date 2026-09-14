/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jogg.Packet
 *  com.jcraft.jogg.Page
 *  com.jcraft.jogg.StreamState
 *  com.jcraft.jogg.SyncState
 *  com.jcraft.jorbis.Block
 *  com.jcraft.jorbis.Comment
 *  com.jcraft.jorbis.DspState
 *  com.jcraft.jorbis.Info
 */
package Abyss.util;

import Abyss.internal.synthetic.OggStreamPlayerCtorMarker;
import Abyss.util.SoundCallback;
import Abyss.util.SoundEngine;
import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

class OggStreamPlayer
implements Runnable {
    private volatile boolean c = false;
    private static String b;
    private volatile boolean o = false;
    private Thread O;
    private static long[] d;
    private SoundCallback W;
    private final InputStream J;
    private static Map f;
        private final float p;
    private static long g;

    public void L() {
        this.o = true;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void r(InputStream var1, float var4) {
        block68: {
            var5 = null;
            var6 = null;
            var7 = null;
            var8 = null;
            var9 = null;
            var10 = null;
            var11 = null;
            var12 = null;
            var13 = null;
            try {
                var5 = new SyncState();
                var6 = new StreamState();
                var7 = new Page();
                var8 = new Packet();
                var9 = new Info();
                var10 = new Comment();
                var11 = new DspState();
                var12 = new Block(var11);
                var5.init();
                var14 = var5.buffer(4096);
                var15 = var5.data;
                var16 = var1.read(var15, var14, 4096);
                if (var16 <= 0) return;
                var5.wrote(var16);
                if (var5.pageout(var7) != 1) return;
                var6.init(var7.serialno());
                var6.pagein(var7);
                if (var6.packetout(var8) != 1) return;
                var9.init();
                var10.init();
                if (var9.synthesis_headerin(var10, var8) < 0) return;
                var17 = 0;
lbl35:
                // 2 sources

                while (true) {
                    if (var17 < 2) {
                        var18 = var5.pageout(var7);
                        if (var18 == 0) {
                            var15 = var5.data;
                            var14 = var5.buffer(4096);
                            var16 = var1.read(var15, var14, 4096);
                            if (var16 <= 0) {
                                return;
}
                            var5.wrote(var16);
                            continue;
}
                        if (var18 != 1) continue;
                        var6.pagein(var7);
                        break;
}
                    var11.synthesis_init(var9);
                    var12.init(var11);
                    var61 = new AudioFormat(var9.rate, 16, var9.channels, true, false);
                    if (!AudioSystem.isLineSupported(new DataLine.Info(SourceDataLine.class, var61))) {
                        var19 = new AudioFormat(Math.min(var9.rate, 44100), 16, var9.channels, true, false);
                        var13 = (SourceDataLine)AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, var19));
                        var13.open(var19);
                    } else {
                        var13 = (SourceDataLine)AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, var61));
                        var13.open(var61);
}
                    var13.start();
                    OggStreamPlayer.l((SourceDataLine)var13, var4);
                    var62 = new float[1][][];
                    var20 = new int[var9.channels];
                    var21 = false;
                    block32: while (var21 == false) {
                        if (this.c != false) return;
                        if (Thread.currentThread().isInterrupted()) {
                            this.c = true;
                            return;
}
                        while (this.o && !this.c) {
                            try {
                                Thread.sleep(OggStreamPlayer.g);
}
                            catch (InterruptedException var48) {
                                this.c = true;
                                Thread.currentThread().interrupt();
                                if (var13 != null) {
                                    var13.drain();
                                    OggStreamPlayer.g((SourceDataLine)var13);
}
                                if (var6 != null) {
                                    var6.clear();
}
                                if (var12 != null) {
                                    var12.clear();
}
                                if (var11 != null) {
                                    var11.clear();
}
                                if (var9 != null) {
                                    var9.clear();
}
                                if (var5 != null) {
                                    var5.clear();
}
                                try {
                                    if (var1 == null) return;
                                    var1.close();
                                    return;
}
                                catch (IOException var21_32) {
                                    // empty catch block
}
                                return;
}
                            if (!Thread.currentThread().isInterrupted()) continue;
                            this.c = true;
                            return;
}
                        if (this.c) {
                            return;
}
                        var22 = var5.pageout(var7);
                        if (var22 == 0) {
                            var15 = var5.data;
                            var14 = var5.buffer(4096);
                            var16 = var1.read(var15, var14, 4096);
                            if (var16 <= 0) {
                                // empty if block
}
                            var5.wrote(var16);
                            continue;
}
                        if (var22 != 1) continue;
                        var6.pagein(var7);
                        while (true) {
                            if (var6.packetout(var8) == 1) {
                                if (var12.synthesis(var8) == 0) {
                                    var11.synthesis_blockin(var12);
}
                                break block68;
}
                            if (var7.eos() != 0) continue block32;
                            continue block32;
                            break;
}
}
                    return;
}
}
            catch (Throwable var12_14) {
                return;
}
            finally {
                if (var13 != null) {
                    var13.drain();
                    OggStreamPlayer.g((SourceDataLine)var13);
}
                if (var6 != null) {
                    var6.clear();
}
                if (var12 != null) {
                    var12.clear();
}
                if (var11 != null) {
                    var11.clear();
}
                if (var9 != null) {
                    var9.clear();
}
                if (var5 != null) {
                    var5.clear();
}
                try {
                    if (var1 != null) {
                        var1.close();
}
}
                catch (IOException var17_21) {}
}
            while (true) {
                if (var17 < 2 && var6.packetout(var8) == 1) break;
                continue;
                if (var9.synthesis_headerin(var10, var8) != 0) {
                    return;
}
                ++var17;
}
}
        while (true) {
            if ((var23 = var11.synthesis_pcmout((float[][][])var62, var20)) <= 0 || this.c) continue;
            var24 = var62[0];
            var25 = Math.min(var23, 1024);
            var26 = new byte[var25 * var9.channels * 2];
            var27 = 0;
            for (var28 = 0; var28 < var25; ++var28) {
                for (var29 = 0; var29 < var9.channels; ++var29) {
                    var30 = (int)(var24[var29][var20[var29] + var28] * 32767.0f);
                    if (var30 > 32767) {
                        var30 = 32767;
}
                    if (var30 < -32768) {
                        var30 = -32768;
}
                    var26[var27++] = (byte)(var30 & 255);
                    var26[var27++] = (byte)(var30 >>> 8 & 255);
}
}
            if (!this.c && !this.o) {
                var13.write(var26, 0, var26.length);
}
            var11.synthesis_read(var25);
}
}
    public void F(long var1) {
        this.o = false;
}
    @Override
    public void run() {
        try {
            this.r(this.J, this.p);
}
        finally {
            SoundEngine.l().remove(this);
            if (!this.c && this.W != null) {
                this.W.s();
}
            return;
}
}
    public void G(SoundCallback var1) {
        this.W = var1;
}
    private OggStreamPlayer(long var1, InputStream var3, float var4) {
        this.J = var3;
        this.p = var4;
}
    OggStreamPlayer(int var1, short var2, InputStream var3, char var4, float var5, OggStreamPlayerCtorMarker var6) {
        this(((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a ^ 0x6E2CAE42757FL, var3, var5);
}
    private static void g(SourceDataLine var0) {
        try {
            var0.stop();
}
        catch (Throwable throwable) {
            // empty catch block
}
        try {
            var0.close();
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public void b(long var1) {
        this.c = true;
        this.o = false;
        if (this.O != null) {
            this.O.interrupt();
}
}
    public void t(char var1, char var2) {
        this.O = new Thread((Runnable)this, b);
        this.O.setDaemon(true);
        this.O.start();
}
    private static void l(SourceDataLine var0, float var1) {
        if (var1 != 0.0f) {
            try {
                if (var0.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl var2 = (FloatControl)var0.getControl(FloatControl.Type.MASTER_GAIN);
                    float var3 = OggStreamPlayer.N(var1, var2.getMinimum(), var2.getMaximum());
                    var2.setValue(var3);
}
}
            catch (Throwable throwable) {
                // empty catch block
}
}
}
    public boolean E() {
        return this.c;
}
    public boolean F() {
        return this.o;
}
    private static float N(float var0, float var1, float var2) {
        return Math.max(var1, Math.min(var2, var0));
}
    static {
        b = "OggPlayer-Thread";
        f = new HashMap(13);
        d = new long[]{4083265199998620853L, -7804156243392218070L, -5450520617468154535L, -3206907126933529369L, -2007018012284532477L, -5091787317120875423L, -5512749978260152769L, 2390692812596265243L, -7667186462599823788L, 9069488050007130747L, 5934094339055260779L, -983851958378323050L, 7096782722566740143L, 7599962049540685148L, 2899210336350044887L, -6552084433110130406L, -4140279938191877032L};
        g = 10L;
}
}