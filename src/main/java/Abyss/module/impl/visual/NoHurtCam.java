/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.PercentageSetting;

public class NoHurtCam
extends Module {
    public static PercentageSetting effect = new PercentageSetting("Effect", 0);
    public NoHurtCam(long var1) {
        super(0x27240547BB06L ^ var1 ^ 0x527D778FA58EL);
        this.declare("NoHurtCam", Category.Visual, "Change the hurt camera effect", new Setting[0]);
        var1 = 0x27240547BB06L ^ var1;
}
}