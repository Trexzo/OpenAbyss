/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public final class AbyssCommandIgn
extends Command {
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"ign", "name"};
}
    @Override
    public void h(long var1) {
        this.j(new String[0], 0L);
}
    @Override
    public void j(String[] var1, long var2) {
        Minecraft var4 = Minecraft.func_71410_x();
        if (var4 == null || var4.field_71439_g == null) {
            AbyssCommands.chat("\u00a7cNot in a world.");
            return;
}
        String var5 = var4.field_71439_g.func_70005_c_();
        try {
            GuiScreen.func_146275_d((String)var5);
}
        catch (Throwable var7) {
            AbyssCommands.chat("\u00a77Your IGN is \u00a7f" + var5 + "\u00a77 (clipboard unavailable: " + var7 + ")");
            return;
}
        AbyssCommands.chat("\u00a77Copied \u00a7f" + var5 + "\u00a77 to the clipboard.");
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        return new ArrayList();
}
}