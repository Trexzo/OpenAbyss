/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.DisableRenderVisualSetting;
import Abyss.setting.settings.ExternalWindowSetting;
import Abyss.setting.settings.ScreenshotBypassSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.util.KeyBindUtil;

public class VisualSpoof
extends Module {
    public static ScreenshotBypassSetting o;
    public static TextSetting keybindToggleRenderVisual;
    public static ExternalWindowSetting v;
        private static long b;
    public static DisableRenderVisualSetting t;

    public VisualSpoof(short var1, short var2, int var3) {
        super(((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var3 << 32 >>> 32) ^ a ^ 0x4BB0F8B0DC2L);
        this.declare("VisualSpoof", Category.Configuration, "Turn on or off visual spoofing", new Setting[0]);
}
    public static boolean n(long var0) {
        return VisualSpoof.A((short)0, 130018228, 5179) && KeyBindUtil.V(KeyBindUtil.a(81924588974218L, keybindToggleRenderVisual.X()), 64165991731362L);
}
    public static boolean A(short var0, int var1, int var2) {
        long var3 = ((long)var0 << 48 | (long)var1 << 32 >>> 16 | (long)var2 << 48 >>> 48) ^ a;
        long var5 = var3 ^ 0x384E1A27810BL;
        return KeyBindUtil.a(var5, keybindToggleRenderVisual.X()) != (int)b;
}
    static {
        b = 6804156122400817152L;
        t = new DisableRenderVisualSetting("Disable-render-visual", false);
        keybindToggleRenderVisual = new TextSetting("Keybind-toggle-render-visual", "NONE");
        v = new ExternalWindowSetting("Enable-external-window", false);
        o = new ScreenshotBypassSetting("Enable-screenshot-bypass", false);
}
}