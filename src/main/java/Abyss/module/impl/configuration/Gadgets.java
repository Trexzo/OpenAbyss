/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;

public class Gadgets
extends Module {
    public static BooleanSetting noScreenBackground;
    public static BooleanSetting noMiningParticles;
    public static BooleanSetting betterWorldSwapping;

    public Gadgets(long var1) {
        super(0x1C73F7715166L ^ var1 ^ 0x29FE7081E71EL);
        this.declare("Gadgets", Category.Configuration, "Some useful items", new Setting[0]);
        var1 = 0x1C73F7715166L ^ var1;
}
    static {
        noMiningParticles = new BooleanSetting("No-mining-particles", false);
        betterWorldSwapping = new BooleanSetting("Better-world-swapping", true);
        noScreenBackground = new BooleanSetting("No-screen-background", true);
}
}