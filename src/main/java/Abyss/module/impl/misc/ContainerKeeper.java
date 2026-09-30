/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.network.play.client.C0DPacketCloseWindow
 *  net.minecraft.network.play.client.C0EPacketClickWindow
 *  net.minecraft.network.play.server.S2DPacketOpenWindow
 *  net.minecraft.network.play.server.S2EPacketCloseWindow
 *  org.lwjgl.input.Keyboard
 */
package Abyss.module.impl.misc;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ContainerKeeperBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.movement.InvMove;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.render.CustomFont;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjgl.input.Keyboard;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ContainerKeeper
extends Module
implements EventSubscriber {
    private static long a = 89937564371945L;
    private boolean v;
    private boolean t;
    private boolean T;
    public static TextSetting toggleKey;
        public static BooleanSetting requireShiftToSave;
    private GuiScreen H;

    private void p() {
        this.v = false;
        this.t = false;
        this.H = null;
        this.T = false;
}
    public void onPreUpdate(int var1, int var2, byte var3, PreUpdateEvent var4) {
        long var5 = ((long)var1 << 32 | (long)var2 << 40 >>> 32 | (long)var3 << 56 >>> 56) ^ a;
        long var7 = var5 ^ 0x705494DABA00L;
        long var11 = var5 ^ 0x50A16BF4C144L;
        boolean var15 = KeyBindUtil.V(Keyboard.getKeyIndex((String)toggleKey.X()), var11);
        if (!var15) {
            this.T = false;
}
        if (this.v) {
            InvMove.Q(0L);
}
        if (KeyBindUtil.V(1, var11)) {
            this.p();
        } else if (ContainerKeeper.f.currentScreen instanceof GuiInventory) {
            this.p();
        } else {
            this.W(var15, var7);
}
}
    public void onSendPacket(SendPacketEvent var1, int var2, short var3, int var4) {
        if (var1.B instanceof C0EPacketClickWindow) {
            this.v = true;
        } else if (var1.B instanceof C0DPacketCloseWindow) {
            this.p();
}
}
    public void onReceivePacket(ReceivePacketEvent var1) {
        if (var1.d instanceof S2EPacketCloseWindow) {
            this.p();
        } else if (var1.d instanceof S2DPacketOpenWindow) {
            this.v = false;
}
}
    public void onRender2D(long var1, int var3, Render2DEvent var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = (var1 << 32 | (long)var3 << 32 >>> 32) ^ a;
        long var7 = var5 ^ 0x4A9A08B8F4CEL;
        if (this.t) {
            float var11 = (float)var4.C.getScaledWidth() / 2.0f + 10.0f;
            float var12 = (float)var4.C.getScaledHeight() / 2.0f + 10.0f;
            CustomFont var13 = Font.s(0L);
            var13.T(var7, "Press " + toggleKey.X() + " for container", var11, var12, -1);
}
}
    @Override
    public void A(long var1) {
        this.p();
}
    private void W(boolean var1, long var2) {
        long var6 = var2 ^ 0x20F5FF2E7B44L;
        if (!this.T && var1) {
            if (!this.t && ContainerKeeper.f.currentScreen instanceof GuiContainer && (!requireShiftToSave.c() || KeyBindUtil.V(ContainerKeeper.f.gameSettings.keyBindSneak.getKeyCode(), var6))) {
                this.H = ContainerKeeper.f.currentScreen;
                f.displayGuiScreen(null);
                if (!this.v) {
                    InvMove.c(0L);
}
                ContainerKeeper.f.inGameHasFocus = true;
                this.t = true;
                this.T = true;
            } else if (this.t) {
                f.displayGuiScreen(this.H);
                this.t = false;
                this.T = true;
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x41D5EC339622L) >>> 32);
        ContainerKeeperBinder.Q(var3, var4, this);
}
    public void onWorldLoad(WorldLoadEvent var3) {
        this.p();
}
    public ContainerKeeper(long var1) {
        super(a ^ var1 ^ 0x35BAA0E7800DL);
        this.declare("ContainerKeeper", Category.Misc, "Save a container to open later", new Setting[0]);
        var1 = a ^ var1;
        this.v = false;
        this.t = false;
        this.H = null;
        this.T = false;
}
    static {
        toggleKey = new TextSetting("Toggle-key", "NONE");
        requireShiftToSave = new BooleanSetting("Require-shift-to-save", false);
}
}