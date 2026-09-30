/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.misc;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;

public class InputFix
extends Module {
    public InputFix(short var1, int var2, int var3) {
        super(((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ 0x6A0B188463EEL ^ 0x1912C15E9119L);
        this.declare("InputFix", Category.Misc, "Fix some special characters typing", new Setting[0]);
}
}