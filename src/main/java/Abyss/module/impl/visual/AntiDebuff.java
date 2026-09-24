/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;

public class AntiDebuff
extends Module {
    public AntiDebuff(long var1) {
        super(0x274492DF5667L ^ var1 ^ 0x782A9A62245L);
        this.declare("AntiDebuff", Category.Visual, "Remove debuff rendering", new Setting[0]);
        var1 = 0x274492DF5667L ^ var1;
}
}