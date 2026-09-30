/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.InvClickerBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;

public class InvClicker
extends Module
implements EventSubscriber {
    private static Map d;

    private static long a = 104942853302490L;

    private int p;
    private static Object[] e;
    private static String[] g;
    private static long[] b;
    public static NumberSetting cps;
    public static BooleanSetting alwaysClick;

    public void onPreUpdate(long var1, PreUpdateEvent var3) {
        if (this.p > 0) {
            this.p -= 50;
}
        if (KeyBindUtil.V(InvClicker.f.gameSettings.keyBindAttack.getKeyCode(), 64165991731362L) && KeyBindUtil.V(42, 64165991731362L) && (InvClicker.f.currentScreen instanceof GuiInventory || InvClicker.f.currentScreen instanceof GuiContainer) && !this.isGetItemStack()) {
            if (alwaysClick.c()) {
                ItemUtil.e(InvClicker.f.currentScreen);
            } else if (this.p <= 0) {
                ItemUtil.e(InvClicker.f.currentScreen);
                this.p = (int)(1000.0 / (double)cps.L());
}
}
}
    private boolean isGetItemStack() {
        return InvClicker.f.thePlayer.inventory.getItemStack() != null;
}
    @Override
    public final void x(long var1, EventBus var3) {
        InvClickerBinder.T(var3, this);
}
    public InvClicker(long var1) {
        super(a ^ var1 ^ 0x1D164ABF7475L);
        this.declare("InvClicker", Category.Player, "Automatically click in inventory when you press shift", new Setting[0]);
        var1 = a ^ var1;
        this.p = 0;
}
    @Override
    public void A(long var1) {
        this.p = 0;
}
    static {
        e = new Object[7];
        g = new String[7];
        d = new HashMap(13);
        b = new long[]{-7855264762936904475L, 2721434635923828421L, 695722135645467035L, -8001476799126727203L};
        alwaysClick = new BooleanSetting("Always-click", true);
        cps = new NumberSetting("CPS", 10.0f, 0.0f, 20.0f, 1.0f);
}
}