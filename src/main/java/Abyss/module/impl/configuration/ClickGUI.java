/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.ui.abyss.AbyssClickGuiScreen;
import Abyss.ui.raven.RavenClickGuiScreen;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.vestige.VestigeClickGuiScreen;
import Abyss.util.KeyBindUtil;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI
extends Module {
    private static long a;

    private static final int DEFAULT_BIND = 54;
    public static NumberSetting scale;
    public static ModeSetting mode;
    public static TextSetting keybind;
    public static VestigeClickGuiScreen B;
    public static StudioClickGuiScreen Y;
    public static RavenClickGuiScreen F;
    
    public static void O(int var0, int var1, char var2) {
        String selected = mode == null ? "STUDIO" : mode.Y();
        if ("RAVEN".equalsIgnoreCase(selected) && F != null) {
            f.displayGuiScreen((GuiScreen)F);
            F.P();
            return;
}
        if ("VESTIGE".equalsIgnoreCase(selected) && B != null) {
            f.displayGuiScreen((GuiScreen)B);
            return;
}
        if (Y != null) {
            f.displayGuiScreen((GuiScreen)Y);
            return;
}
        f.displayGuiScreen((GuiScreen)AbyssClickGuiScreen.INSTANCE);
}
    private static void a() {
}
    public ClickGUI(long var1) {
        super(a ^ var1 ^ 0x6AC4D515CE4FL);
        this.declare("ClickGUI", Category.Configuration, "Manager ClickGUI settings", new Setting[0]);
        this.z(0L, 54);
        var1 = a ^ var1;
}
    @Override
    public void z(long var1, int var3) {
        super.z(var1, var3 == 0 ? 54 : var3);
}
    public static boolean x(int var0, short var1, char var2) {
        long var3 = ((long)var0 << 32 | (long)var1 << 48 >>> 32 | (long)var2 << 48 >>> 48) ^ a;
        long var9 = var3 ^ 0x21FA9FB5CE90L;
        int code = keybind == null ? Integer.MIN_VALUE : KeyBindUtil.a(0L, keybind.X());
        if (code == Integer.MIN_VALUE) {
            ClickGUI var11 = Modules.J(ClickGUI.class);
            code = var11 == null ? 0 : var11.h();
}
        return KeyBindUtil.V(code, var9);
}
    public static String selfTest() {
        try {
            if (scale == null || mode == null || keybind == null) {
                return "FAIL settings-null";
}
            if (!mode.S().contains("STUDIO") || !mode.S().contains("RAVEN") || !mode.S().contains("VESTIGE")) {
                return "FAIL modes " + mode.S();
}
            int code = KeyBindUtil.a(0L, keybind.X());
            if (code == Integer.MIN_VALUE) {
                return "FAIL keybind " + keybind.X();
}
            if (B == null || Y == null || F == null) {
                return "FAIL screens B=" + (B != null) + " Y=" + (Y != null) + " F=" + (F != null);
}
            return "PASS mode=" + mode.Y() + " keybind=" + keybind.X() + " code=" + code;
}
        catch (Throwable throwable) {
            return "FAIL " + throwable.getClass().getName() + ": " + throwable.getMessage();
}
}
    static {
        a = 104656739453137L;
        scale = new NumberSetting("Scale", 1.0f, 0.1f, 5.0f, 0.01f);
        keybind = new TextSetting("Keybind", "RSHIFT");
        mode = new ModeSetting("Mode", true, "STUDIO", "STUDIO", "RAVEN", "VESTIGE");
}
}