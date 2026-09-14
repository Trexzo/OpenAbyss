/*
 * Decompiled with CFR 0.152.
 */
package Abyss.enums;

public enum DetectedAction {
    ROTATION,
    SWING,
    PLACE;

    private static final DetectedAction[] X;

    static {
        X = new DetectedAction[]{ROTATION, SWING, PLACE};
}
}