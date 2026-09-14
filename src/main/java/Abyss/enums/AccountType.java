/*
 * Decompiled with CFR 0.152.
 */
package Abyss.enums;

public enum AccountType {
    OFFLINE("Offline"),
    MINECRAFT("Minecraft");

    private final String N;
    
    private AccountType(String var3) {
        this.N = var3;
}
    public static AccountType E(String var0) {
        if (var0 == null) {
            return MINECRAFT;
}
        if ("CRACKED".equalsIgnoreCase(var0) || "OFFLINE".equalsIgnoreCase(var0)) {
            return OFFLINE;
}
        return !"PREMIUM".equalsIgnoreCase(var0) && !"MINECRAFT".equalsIgnoreCase(var0) ? MINECRAFT : MINECRAFT;
}
    public String S() {
        return this.N;
}
    static {
}
}