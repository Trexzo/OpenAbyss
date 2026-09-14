/*
 * Decompiled with CFR 0.152.
 */
package Abyss.enums;

public enum AnimationDirection {
    FORWARDS,
    BACKWARDS;


    public AnimationDirection D() {
        return this == FORWARDS ? BACKWARDS : FORWARDS;
}
    public boolean M() {
        return this == BACKWARDS;
}
    public boolean D$r1() {
        return this == FORWARDS;
}
}