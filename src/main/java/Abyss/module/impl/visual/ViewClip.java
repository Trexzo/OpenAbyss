/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;

public class ViewClip
extends Module {
    private static final long public ViewClip(long var1) {
        super(0x7512D5A2B9D7L ^ var1 ^ 0x5F56B9F36277L);
        this.declare("ViewClip", Category.Visual, "Remove the camera blocking by blocks in 3rd person view", new Setting[0]);
        var1 = 0x7512D5A2B9D7L ^ var1;
}
}