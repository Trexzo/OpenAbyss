/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.BlockUtil;
import Abyss.util.DeferredRendererReload;
import net.minecraft.util.BlockPos;

public class CaveXray
extends Module {
    private static long a;

    public static BooleanSetting reloadRenderer;
    private static long[] b;
        public static PercentageSetting opacity;

    private void markBlockRangeForRenderUpdate(int var1) {
        BlockPos var2 = BlockUtil.Z();
        CaveXray.f.renderGlobal.markBlockRangeForRenderUpdate(var2.getX() - var1, var2.getY() - var1, var2.getZ() - var1, var2.getX() + var1, var2.getY() + var1, var2.getZ() + var1);
}
    public static int L(long var0) {
        return 255 * opacity.k() / 100;
}
    @Override
    public void A(long var1) {
        if (reloadRenderer.c()) {
            DeferredRendererReload.request();
        } else {
            this.markBlockRangeForRenderUpdate(900);
}
}
    public CaveXray(long var1) {
        super(a ^ var1 ^ 0x4E4D1FED44C7L);
        this.declare("CaveXray", Category.Visual, "Allows you to see structures underground (Only works with optifine)", new Setting[0]);
        var1 = a ^ var1;
}
    @Override
    public void i(long var1) {
        if (reloadRenderer.c()) {
            DeferredRendererReload.request();
        } else {
            this.markBlockRangeForRenderUpdate(900);
}
}
    @Override
    public String g(long var1) {
        return opacity.k() + "%";
}
    static {
        a = 106916738106937L;
        reloadRenderer = new BooleanSetting("Reload-renderer", false);
        opacity = new PercentageSetting("Opacity", 60);
}
}