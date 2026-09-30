/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.util.DeferredRendererReload;

public class BarrierVisible
extends Module {
    @Override
    public void i(long var1) {
        DeferredRendererReload.request();
}
    public BarrierVisible(long var1) {
        super(0x317F196047B2L ^ var1 ^ 0x1FC7FEFE653FL);
        this.declare("BarrierVisible", Category.Visual, "Render barriers as glasses", new Setting[0]);
        var1 = 0x317F196047B2L ^ var1;
}
    @Override
    public void A(long var1) {
        DeferredRendererReload.request();
}
}