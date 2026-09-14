/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ClipPlayer {
    private static final ExecutorService AUDIO_THREAD = Executors.newSingleThreadExecutor(r2 -> {
        Thread t2 = new Thread(r2, "Abyss-Audio");
        t2.setDaemon(true);
        return t2;
    });

    public static void x(String var0) {
        AUDIO_THREAD.execute(() -> ClipPlayer.playBlocking(var0));
}
    private static void playBlocking(String var0) {
        try (InputStream var1 = ClipPlayer.class.getResourceAsStream(var0);){
            if (var1 != null) {
                BufferedInputStream var3 = new BufferedInputStream(var1);
                AudioInputStream var4 = AudioSystem.getAudioInputStream(var3);
                Clip var5 = AudioSystem.getClip();
                var5.open(var4);
                var5.start();
}
}
        catch (Exception exception) {
            // empty catch block
}
}
}