/*
 * Decompiled with CFR 0.152.
 */
package Abyss.enums;

public enum DetectedCheat {
    AUTOBLOCK((int)-459581626888749036L, "\u00a7c\u00a7l"),
    SCAFFOLD((int)137313523247087626L, "\u00a7b\u00a7l"),
    NOSLOW(5, "\u00a79\u00a7l");

    public final String colorFormatCode;
    private static final DetectedCheat[] L;
    public final int FLAG_VL;

    private DetectedCheat(int var3, String var4) {
        this.FLAG_VL = var3;
        this.colorFormatCode = var4;
}
    static {
        L = new DetectedCheat[]{AUTOBLOCK, SCAFFOLD, NOSLOW};
}
}