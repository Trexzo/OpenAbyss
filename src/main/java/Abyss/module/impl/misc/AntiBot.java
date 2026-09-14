/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.scoreboard.ScorePlayerTeam
 */
package Abyss.module.impl.misc;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AntiBotBinder;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.util.MinecraftRef;
import Abyss.util.PlayerInfoCache;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class AntiBot
extends Module
implements EventSubscriber {
    public static BooleanSetting tablistCheck;
        private static Minecraft F;
        private static CopyOnWriteArrayList<String> b;

    public void onPreLivingUpdate(PreLivingUpdateEvent var1) {
        PlayerInfoCache.refresh();
}
    public static boolean T(short var0, EntityPlayer var3) {
        if (F.func_71356_B()) {
            return false;
}
        if (tablistCheck.c() && !PlayerInfoCache.inTabList(var3.func_70005_c_())) {
            return true;
}
        if (var3 instanceof EntityPlayerSP) {
            return false;
}
        NetworkPlayerInfo var6 = PlayerInfoCache.byName(var3.func_70005_c_());
        if (var6 == null) {
            return true;
}
        if (var3.func_70005_c_().startsWith("\u00a7k")) {
            return var3.func_82150_aj();
}
        if (var6.func_178853_c() < 1) {
            return true;
}
        ScorePlayerTeam var7 = var6.func_178850_i();
        if (var7 == null) {
            return false;
}
        return !var7.func_96669_c().isEmpty() ? false : var7.func_96668_e().equals("\u00a7c");
}
    public AntiBot(int var1, int var2, short var3) {
        super(((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a ^ 0x728E878E8BF4L);
        this.declare("AntiBot", Category.Misc, "Detect bots", new Setting[0]);
}
    @Override
    public final void x(long var1, EventBus var3) {
        AntiBotBinder.t(var3, this);
}
    static {
        b = new CopyOnWriteArrayList();
        F = MinecraftRef.c((byte)0, 0L);
        tablistCheck = new BooleanSetting("Tablist-check", false);
}
}