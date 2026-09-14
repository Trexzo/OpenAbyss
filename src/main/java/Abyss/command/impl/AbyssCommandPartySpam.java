/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package Abyss.command.impl;

import Abyss.AbyssClient;
import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public final class AbyssCommandPartySpam
extends Command {
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"partyspam", "ps"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat("\u00a7eStart or stop party invite spamming someone");
        AbyssCommands.chat("\u00a77Usage:");
        AbyssCommands.chat("\u00a7f  .partyspam <name>");
        AbyssCommands.chat("\u00a77Target: \u00a7f" + (AbyssClient.I == null ? "none" : AbyssClient.I));
        AbyssCommands.chat("\u00a78Note: the party-invite sender is not available in this build, so the target slot is stored but no invite is sent.");
}
    @Override
    public void j(String[] var1, long var2) {
        String var4 = AbyssClient.I;
        if (var4 == null) {
            AbyssClient.I = var1[0];
            AbyssCommands.chat("\u00a7aNow party spamming \u00a7f" + AbyssClient.I);
        } else {
            AbyssClient.I = null;
            AbyssCommands.chat("\u00a7cStopped party spamming \u00a7f" + var4);
}
        AbyssCommands.chat("\u00a78The party-invite sender is not available in this build, so no invite is actually sent.");
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        Minecraft var6;
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1 && (var6 = Minecraft.func_71410_x()) != null && var6.field_71441_e != null) {
            for (EntityPlayer var8 : var6.field_71441_e.field_73010_i) {
                var5.add(var8.func_70005_c_());
}
}
        return var5;
}
}