/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.module.impl.movement;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.SprintBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.module.impl.movement.NoSlow;
import Abyss.setting.Setting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.Minecraft;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class Sprint
extends Module
implements EventSubscriber {
    private static String[] d;

    private static long a = 130957744460578L;

        private static long b;
    private static Object[] c;
    private static Minecraft H;
    

    public static boolean U(long var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return Sprint.H.thePlayer.moveForward > 0.0f && (Modules.J(NoSlow.class).o() && NoSlow.c(0L) || !Sprint.H.thePlayer.isUsingItem()) && !Sprint.H.thePlayer.isSneaking() && !Sprint.H.thePlayer.isCollidedHorizontally && Sprint.H.thePlayer.getFoodStats().getFoodLevel() > (int)b;
}
    public Sprint(short var1, int var2, short var3) {
        super(((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ a ^ 0x4C8F0637D695L);
        this.declare("Sprint", Category.Movement, "Automatically sprint", new Setting[0]);
}
    @Override
    public final void x(long var1, EventBus var3) {
        SprintBinder.J(var3, this);
}
    public void onPreUpdate(short var1, PreUpdateEvent var2, char var3, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = ((long)var1 << 48 | (long)var3 << 48 >>> 16 | (long)var4 << 32 >>> 32) ^ a;
        long var9 = var5 ^ 0x7CF65F2F8FD3L;
        if (Sprint.H.thePlayer.isUsingItem()) {
            if (Sprint.U(0L)) {
                Sprint.H.thePlayer.setSprinting(true);
}
        } else {
            KeyBindUtil.A(var9, Sprint.H.gameSettings.keyBindSprint.getKeyCode(), true);
}
}
    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0x1F574BE49C60L;
        KeyBindUtil.A(var3, Sprint.H.gameSettings.keyBindSprint.getKeyCode(), Sprint.H.gameSettings.keyBindSprint.isPressed());
}
    static {
        H = MinecraftRef.c((byte)0, 0L);
        c = new Object[7];
        d = new String[7];
        b = 3706994347716116486L;
}
}