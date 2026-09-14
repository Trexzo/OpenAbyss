/*
 * Decompiled with CFR 0.152.
 */
package Abyss.enums;

public enum MiningRegionState {
    EMPTY,
    PARTIALLY_EMPTY,
    FILLED,
    FILLED_WITH_UNBREAKABLE;

    private static final MiningRegionState[] s;

    static {
        s = new MiningRegionState[]{EMPTY, PARTIALLY_EMPTY, FILLED, FILLED_WITH_UNBREAKABLE};
}
}