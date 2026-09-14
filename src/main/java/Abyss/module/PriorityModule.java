/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module;

import Abyss.module.Module;
import Abyss.module.ModulePriority;

public class PriorityModule
extends Module {
    private static final long ab = 76245502448272L;

    public PriorityModule(long var1, char var3) {
        super((var1 << 16 | (long)var3 << 48 >>> 48) ^ 0x45584A16D290L ^ 0x70FE52941D5CL);
}
    public void T(boolean var1) {
        ModulePriority.U(this.getClass(), var1);
}
    public boolean Y() {
        return ModulePriority.c(this.getClass());
}
}