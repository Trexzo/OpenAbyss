/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

public final class Sneaky {
    private Sneaky() {
}
    public static <T extends Throwable> RuntimeException rethrow(Throwable t2) throws T {
        throw (T)t2;
}
}