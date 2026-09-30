/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 */
package Abyss.module.impl.player;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.misc.AntiBot;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.util.MinecraftRef;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class GhostHand
extends Module {
    public static BooleanSetting blacklistEnemy;
    public static BooleanSetting playersOnly;
    public static BooleanSetting disableWhileHoldingSword;
    private static final Minecraft N;
    public static BooleanSetting teammatesOnly;
    public static BooleanSetting toolsOnly;

    public static void T(List<Entity> var0) {
        var0.removeIf(GhostHand::w);
}
    public GhostHand(long var1) {
        super(0x3BB2DD1DCBC8L ^ var1 ^ 0x3E78ABB1DD03L);
        this.declare("GhostHand", Category.Player, "Allows you to interact through entity", new Setting[0]);
        var1 = 0x3BB2DD1DCBC8L ^ var1;
}
    private static boolean w(Entity var0) {
        return !(var0 instanceof EntityLivingBase) || !blacklistEnemy.c() || (!(var0 instanceof EntityPlayer) || AntiBot.T((short)0, (EntityPlayer)var0) || Teams.g(0L, var0)) && !Teams.Y(var0) ? !(disableWhileHoldingSword.c() && GhostHand.N.thePlayer.getHeldItem() != null && GhostHand.N.thePlayer.getHeldItem().getItem() instanceof ItemSword || toolsOnly.c() && (GhostHand.N.thePlayer.getHeldItem() == null || !(GhostHand.N.thePlayer.getHeldItem().getItem() instanceof ItemTool)) || playersOnly.c() && !(var0 instanceof EntityPlayer) || teammatesOnly.c() && (!(var0 instanceof EntityLivingBase) || !Teams.g(0L, var0))) : false;
}
    static {
        boolean var2 = false;
        N = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
        teammatesOnly = new BooleanSetting("Teammates-only", true);
        disableWhileHoldingSword = new BooleanSetting("Disable-while-holding-sword", true);
        toolsOnly = new BooleanSetting("Tools-only", false);
        playersOnly = new BooleanSetting("Players-only", true);
        blacklistEnemy = new BooleanSetting("Blacklist-enemy", true);
}
}