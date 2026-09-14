/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.synthetic;

import Abyss.enums.BlurDirection;

public class RavenFramebufferSwitchMapBlurDirection {
    public static final int[] O = new int[BlurDirection.values().length];

    static {
        try {
            RavenFramebufferSwitchMapBlurDirection.O[BlurDirection.LR.ordinal()] = 1;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
        try {
            RavenFramebufferSwitchMapBlurDirection.O[BlurDirection.TB.ordinal()] = 2;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
}
}