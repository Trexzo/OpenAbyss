/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.S03PacketTimeUpdate
 *  net.minecraft.network.play.server.S2BPacketChangeGameState
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AmbienceBinder;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import java.io.UnsupportedEncodingException;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class Ambience
extends Module
implements EventSubscriber {
    private static long a;
    static {
        a = 81694087725472L;
    }
    public static NumberSetting time;
    public static ModeSetting mode;
    public static NumberSetting speed;
    
    public void onUpdateWalkingPlayer(long var1, UpdateWalkingPlayerEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (Ambience.f.thePlayer.ticksExisted % 20 == 0) {
            switch (mode.Y()) {
                case "CLEAR": {
                    this.D((byte)-117);
                    break;
}
                case "RAIN": {
                    Ambience.f.theWorld.setRainStrength(1.0f);
                    Ambience.f.theWorld.getWorldInfo().setCleanWeatherTime(0);
                    Ambience.f.theWorld.getWorldInfo().setRainTime(Integer.MAX_VALUE);
                    Ambience.f.theWorld.getWorldInfo().setThunderTime(Integer.MAX_VALUE);
                    Ambience.f.theWorld.getWorldInfo().setRaining(true);
                    Ambience.f.theWorld.getWorldInfo().setThundering(false);
}
}
}
}
    @Override
    public void A(long var1) {
        int var5 = (int)((var1 ^ 0x71B4F091760EL) << 56 >>> 56);
        this.D((byte)var5);
}
    public Ambience(long var1) {
        super(a ^ var1 ^ 0x1F02B6C6EC5EL);
        this.declare("Ambience", Category.Visual, "Change the environment rendering", new Setting[0]);
        var1 = a ^ var1;
}
    public void onRender2D(Render2DEvent var1) {
        Ambience.f.theWorld.setWorldTime((long)(time.L() + (float)System.currentTimeMillis() * speed.L()));
}
    @Override
    public String g(long var1) {
        if (mode.R("NONE")) {
            return time.L() % 24000.0f >= 12000.0f ? "NIGHT" : "DAY";
}
        return mode.Y();
}
    public void onReceivePacket(ReceivePacketEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        S2BPacketChangeGameState var7;
        if (var3.d instanceof S03PacketTimeUpdate) {
            var3.I(21307, 3074332907L);
        } else if (var3.d instanceof S2BPacketChangeGameState && !mode.R("NONE") && ((var7 = (S2BPacketChangeGameState)var3.d).getGameState() == 1 || var7.getGameState() == 2)) {
            var3.I(21307, 3074332907L);
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        AmbienceBinder.K(var3, this);
}
    private void D(byte var3) {
        Ambience.f.theWorld.setRainStrength(0.0f);
        Ambience.f.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
        Ambience.f.theWorld.getWorldInfo().setRainTime(0);
        Ambience.f.theWorld.getWorldInfo().setThunderTime(0);
        Ambience.f.theWorld.getWorldInfo().setRaining(false);
        Ambience.f.theWorld.getWorldInfo().setThundering(false);
}
    static {
        time = new NumberSetting("Time", 0.0f, 0.0f, 24000.0f, 10.0f);
        speed = new NumberSetting("Speed", 0.0f, 0.0f, 100.0f, 1.0f);
        mode = new ModeSetting("Mode", "NONE", "RAIN", "CLEAR");
}
}