/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module;

import Abyss.internal.synthetic.ModulePriorityCtorMarker;
import Abyss.module.PriorityModule;

public class ModulePriorityEntry {
    public final Class<? extends PriorityModule> P;
    private static long b;
    public final int L;
    private boolean j = (b & 1L) != 0L;
    
    public void N(boolean var1) {
        this.j = var1;
}
    public boolean d() {
        return this.j;
}
    static boolean z(ModulePriorityEntry var0, boolean var1) {
        var0.j = var1;
        return var0.j;
}
    ModulePriorityEntry(Class var1, int var2, ModulePriorityCtorMarker var3, long var4) {
        this(var1, var2, a ^ var4 ^ 0x7B0A316A708EL);
        var4 = a ^ var4;
}
    private ModulePriorityEntry(Class var1, int var2, long var3) {
        this.P = var1;
        this.L = var2;
}
    static boolean r(ModulePriorityEntry var0) {
        return var0.j;
}
    static {
}
}