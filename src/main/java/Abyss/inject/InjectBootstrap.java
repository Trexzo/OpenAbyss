/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject;

import Abyss.inject.InjectLog;
import Abyss.internal.restore.AbyssBootstrap;

public final class InjectBootstrap {
    private static volatile boolean requested;
    private static volatile boolean started;

    private InjectBootstrap() {
}
    public static void requestStart() {
        requested = true;
}
    public static void tick() {
        if (!requested || started) {
            return;
}
        started = true;
        try {
            InjectBootstrap.log("constructing Abyss client on the game thread");
            AbyssBootstrap.initClient();
            InjectBootstrap.log("client ready");
}
        catch (Throwable t2) {
            InjectLog.throwable("client failed to start", t2);
}
}
    public static boolean isStarted() {
        return started;
}
    private static void log(String message) {
        InjectLog.line(message);
}
}