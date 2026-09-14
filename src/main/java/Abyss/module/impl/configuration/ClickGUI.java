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
import Abyss.setting.settings.NumberSetting;
import Abyss.ui.abyss.AbyssClickGuiScreen;
import Abyss.ui.raven.RavenClickGuiScreen;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.vestige.VestigeClickGuiScreen;
import Abyss.util.KeyBindUtil;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI
extends Module {
    private static final int DEFAULT_BIND = 54;
    public static NumberSetting scale;
    public static VestigeClickGuiScreen B;
    public static StudioClickGuiScreen Y;
    public static RavenClickGuiScreen F;
    
    public static void O(int var0, int var1, char var2) {
        f.func_147108_a((GuiScreen)AbyssClickGuiScreen.INSTANCE);
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
        ClickGUI var11 = Modules.J(ClickGUI.class);
        return var11 != null && KeyBindUtil.V(var11.h(), var9);
}
    static {
        scale = new NumberSetting("Scale", 1.0f, 0.1f, 5.0f, 0.01f);
}
}