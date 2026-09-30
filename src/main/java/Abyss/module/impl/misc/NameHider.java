/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.module.impl.misc;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.setting.Setting;
import Abyss.setting.settings.TextSetting;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;

public class NameHider
extends Module {
    public static TextSetting name;
    private static final Minecraft n;

    public NameHider(long var1) {
        super(0x7156FA88F6ECL ^ var1 ^ 0x496473870612L);
        this.declare("NameHider", Category.Misc, "Replace all string that matches your name", new Setting[0]);
        var1 = 0x7156FA88F6ECL ^ var1;
}
    public static String U(String var0) {
        if (NameHider.n.thePlayer == null || ModuleManager.J == null || var0 == null) {
            return var0;
}
        String self = NameHider.n.thePlayer.getName();
        if (self == null || self.isEmpty()) {
            return var0;
}
        return ModuleManager.J.o() ? var0.replace(self, name.X()) : var0;
}
    static {
        boolean var2 = false;
        n = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
        name = new TextSetting("Name", "You");
}
}