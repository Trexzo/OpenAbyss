/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.raven;

import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.ui.raven.RavenCheckBoxRow;
import Abyss.ui.raven.RavenColorRow;
import Abyss.ui.raven.RavenElement;
import Abyss.ui.raven.RavenModeRow;
import Abyss.ui.raven.RavenModuleRow;
import Abyss.ui.raven.RavenSliderRow;
import Abyss.ui.raven.RavenTextRow;
import Abyss.util.Sneaky;
import java.awt.Color;

public abstract class AbstractRavenSettingRow
implements RavenElement {
    protected int P = C;
    protected int N = M;
    protected int g;
    protected RavenModuleRow O;
    public static int a;
    public static int C;
    public static int r;
    public static int M;
    protected int h;
    private static long b;
    protected int y;

    @Override
    public final void c(int var1, long var2, int var4) {
        try {
            long var5 = var2 ^ 0x6991587D70F7L;
            boolean var9 = this.D(0L, var1, var4);
            this.P = var9 ? a : C;
            this.N = var9 ? r : M;
            this.V(var5, var1, var4);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public void t(RavenModuleRow var1) {
        this.O = var1;
}
    public AbstractRavenSettingRow(RavenModuleRow var1) {
        this.t(var1);
}
    public boolean D(long var1, int var3, int var4) {
        return var3 > this.h && var3 < this.h + this.C().O.t() && var4 > this.g && var4 < this.g + 8;
}
    public static AbstractRavenSettingRow e(Setting var0, RavenModuleRow var1, long var2, int var4) {
        if (var0 instanceof HeaderSetting) {
            return new RavenTextRow((HeaderSetting)var0, var1, var4);
}
        if (var0 instanceof ModeSetting) {
            return new RavenModeRow((ModeSetting)var0, var1, var4);
}
        if (var0 instanceof NumberSetting) {
            return new RavenSliderRow((NumberSetting)var0, 27408400409158L, var1, var4);
}
        if (var0 instanceof PercentageSetting) {
            return new RavenSliderRow(0, (PercentageSetting)var0, var1, '\u7709', var4, 338882696);
}
        if (var0 instanceof BooleanSetting) {
            return new RavenCheckBoxRow(var1.R, (BooleanSetting)var0, var1, var4);
}
        if (var0 instanceof ColorSetting) {
            return new RavenColorRow((ColorSetting)var0, var1, var4, 95728611103432L);
}
        return var0 instanceof TextSetting ? new RavenTextRow((TextSetting)var0, var1, var4) : null;
}
    @Override
    public RavenModuleRow C() {
        return this.O;
}
    static {
        b = 22004650097170L;
        C = new Color(255, 255, 255).getRGB();
        a = new Color(162, 162, 162).getRGB();
        M = new Color(20, 255, 0).getRGB();
        r = new Color(20, 162, 0).getRGB();
}
}