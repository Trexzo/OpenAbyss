/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM.Util;

public enum MappingKind {
    MCP,
    SRG,
    NOTCH;

    private static final MappingKind[] G;

    static {
        G = new MappingKind[]{MCP, SRG, NOTCH};
}
}