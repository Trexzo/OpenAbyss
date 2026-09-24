/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AutoWeaponBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoWeapon
extends Module
implements EventSubscriber {
    private static final String[] c = new String[10];
    public static BooleanSetting fishingrodIsWeapon;
    private static final Minecraft E;
    private static final Object[] b;
    public static BooleanSetting axeIsWeapon;
    public static BooleanSetting stickIsWeapon;

    @Override
    public final void x(long var1, EventBus var3) {
        AutoWeaponBinder.N(var3, this);
}
    public static int M(long var0) {
        var0 = 0x198422595E89L ^ var0;
        int var2 = (int)((var0 ^ 0x5725CC3CEFBBL) >>> 48);
        int var3 = (int)((var0 ^ 0x5725CC3CEFBBL) << 16 >>> 48);
        int var5 = 0;
        double var6 = 0.0;
        for (int var8 = 0; var8 < InventoryPlayer.getHotbarSize(); ++var8) {
            ItemStack var9 = AutoWeapon.E.thePlayer.inventory.getStackInSlot(var8);
            if (var9 == null) continue;
            double var10 = 0.0;
            double var12 = 0.0;
            if (var9.getItem() instanceof ItemSword) {
                var10 = ItemUtil.p((short)var2, var9, (char)var3);
                var12 = 0.4;
            } else if (axeIsWeapon.c() && var9.getItem() instanceof ItemAxe) {
                var10 = ItemUtil.p((short)var2, var9, (char)var3);
                var12 = 0.3;
            } else if (stickIsWeapon.c() && var9.getItem() == Items.stick) {
                var10 = ItemUtil.p((short)var2, var9, (char)var3);
                var12 = 0.2;
            } else if (fishingrodIsWeapon.c() && var9.getItem() == Items.fishing_rod) {
                var10 = ItemUtil.p((short)var2, var9, (char)var3);
                var12 = 0.1;
}
            var10 += var12;
            if (!(var10 > var6)) continue;
            var6 = var10;
            var5 = var8;
}
        return var5;
}
    private static void a() {
        AutoWeapon.b[0] = "W\u00115f|3K";
        AutoWeapon.b[1] = Long.TYPE;
        AutoWeapon.c[1] = "java/lang/Long";
        AutoWeapon.b[2] = Integer.TYPE;
        AutoWeapon.c[2] = "java/lang/Integer";
        AutoWeapon.b[3] = Void.TYPE;
        AutoWeapon.c[3] = "java/lang/Void";
        AutoWeapon.b[4] = "\u0005\u0002[u9\u0002\u0017";
        AutoWeapon.b[5] = "e\b'Y9\u0005R\u001f#St!E\u0014yO";
        AutoWeapon.b[6] = ".vB\u0010@K/";
        AutoWeapon.b[7] = ":\u007fZ}\u001e\u001a1pK2\u007f\u0014:{Oh";
        AutoWeapon.b[8] = "Tsd\b7\u0002\b=n\tX(n8a\u0011'^V::\u000e%nRhk\u0011(\u0014\u0016`3\u0003X\u0014\n`<\u0018eVS|\u0002Hi\t\u001fg}\u00037_\u001f\u0003";
        AutoWeapon.b[9] = "\u000bP=/UJR\u000b\u0001\u00054V_\u0002?7\t\u0014\u0006\u001e\u0001dF\u001dCP~&ISVa;lS]_\u001ep2\u0005];";
}
    public void onPreUpdate(PreUpdateEvent var3) {
        if (AutoWeapon.E.objectMouseOver.entityHit != null && KeyBindUtil.V(AutoWeapon.E.gameSettings.keyBindAttack.getKeyCode(), 64165991731362L)) {
            ItemUtil.P(AutoWeapon.M(93384294372710L));
}
}
    public AutoWeapon(long var1) {
        super(0x198422595E89L ^ var1 ^ 0x63228F60A3A8L);
        this.declare("AutoWeapon", Category.Player, "Switch to the best weapon in hotbar during combat", new Setting[0]);
        var1 = 0x198422595E89L ^ var1;
}
    static {
        b = new Object[10];
        boolean var2 = false;
        AutoWeapon.a();
        E = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
        axeIsWeapon = new BooleanSetting("Axe-is-weapon", false);
        stickIsWeapon = new BooleanSetting("Stick-is-weapon", false);
        fishingrodIsWeapon = new BooleanSetting("FishingRod-is-weapon", false);
}
}