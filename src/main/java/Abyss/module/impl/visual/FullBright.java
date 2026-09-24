/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;

public class FullBright
extends Module {
    private static final long b = 113539360861368L;
    private float a;

    @Override
    public void A(long var1) {
        FullBright.f.gameSettings.gammaSetting = this.a;
}
    public FullBright(long var1) {
        super(0x67437145ACB8L ^ var1 ^ 0x1F7A5F0BCA30L);
        this.declare("FullBright", Category.Visual, "Let the game always be bright", new Setting[0]);
        var1 = 0x67437145ACB8L ^ var1;
        this.a = FullBright.f.gameSettings.gammaSetting;
}
    @Override
    public void i(long var1) {
        this.a = FullBright.f.gameSettings.gammaSetting;
        FullBright.f.gameSettings.gammaSetting = 15.0f;
}
}