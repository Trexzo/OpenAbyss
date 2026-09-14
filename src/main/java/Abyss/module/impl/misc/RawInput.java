/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MouseHelper
 */
package Abyss.module.impl.misc;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.util.SmoothMouseHelper;
import net.minecraft.util.MouseHelper;

public class RawInput
extends Module {
    public static MouseHelper T;
    private SmoothMouseHelper M;
    private static final long @Override
    public void A(long var1) {
        this.M.M();
        this.M = null;
        MouseHelper var3 = T;
        RawInput.f.field_71417_B = var3 != null ? var3 : new MouseHelper();
}
    @Override
    public void i(long var1) {
        long var3 = var1 ^ 0x211DEAB4B5AL;
        long var5 = var1 ^ 0x8588AD55DAFL;
        MouseHelper var7 = RawInput.f.field_71417_B;
        if (!(var7 instanceof SmoothMouseHelper)) {
            T = var7;
}
        this.M = new SmoothMouseHelper(var3);
        this.M.f(var5);
}
    public RawInput(long var1) {
        super(0x327FC694D6BBL ^ var1 ^ 0x4B350588F0ADL);
        this.declare("RawInput", Category.Misc, "Fix your mouse input", new Setting[0]);
        var1 = 0x327FC694D6BBL ^ var1;
}
}