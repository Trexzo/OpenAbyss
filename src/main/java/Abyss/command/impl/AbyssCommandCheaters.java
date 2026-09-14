/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.enums.DetectedCheat;
import Abyss.internal.CheaterDetector;
import Abyss.util.CheaterRegistry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public final class AbyssCommandCheaters
extends Command {
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"cheaters", "ac", "nocheaters"};
}
    @Override
    public void h(long var1) {
        this.j(new String[0], 0L);
}
    @Override
    public void j(String[] var1, long var2) {
        Minecraft var4 = Minecraft.func_71410_x();
        if (var4 == null || var4.field_71441_e == null) {
            AbyssCommands.chat("\u00a7cNot in a world.");
            return;
}
        Map<UUID, EntityPlayer> var5 = CheaterDetector.c;
        Map<UUID, CheaterRegistry> var6 = CheaterDetector.R;
        if (var5 == null || var6 == null) {
            AbyssCommands.chat("\u00a7cThe detector has not started yet.");
            return;
}
        LinkedHashMap<UUID, CheaterRegistry> var7 = new LinkedHashMap<UUID, CheaterRegistry>();
        for (Map.Entry<UUID, EntityPlayer> entry : var5.entrySet()) {
            CheaterRegistry var10;
            if (var4.field_71441_e.func_152378_a(entry.getKey()) == null || (var10 = var6.get(entry.getKey())) == null || !var10.M()) continue;
            var7.put(entry.getKey(), var10);
}
        if (var7.isEmpty()) {
            AbyssCommands.chat("No cheaters found");
            return;
}
        AbyssCommands.chat("\u00a77Flagged players:");
        for (Map.Entry<UUID, Object> entry : var7.entrySet()) {
            EntityPlayer var13 = var4.field_71441_e.func_152378_a(entry.getKey());
            if (var13 == null) continue;
            ArrayList<String> var14 = new ArrayList<String>();
            for (Map.Entry<DetectedCheat, Boolean> var16 : ((CheaterRegistry)entry.getValue()).e.entrySet()) {
                if (!Boolean.TRUE.equals(var16.getValue())) continue;
                DetectedCheat var17 = var16.getKey();
                var14.add(var17.colorFormatCode + var17.name());
}
            AbyssCommands.chat("\u00a77" + var13.func_145748_c_().func_150254_d() + "\u00a78: " + String.join((CharSequence)"\u00a78, ", var14));
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        return new ArrayList();
}
}