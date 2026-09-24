/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiDisconnected
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSelectWorld
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.client.renderer.GlStateManager
 *  org.apache.commons.lang3.StringUtils
 */
package Abyss.ui.screen;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ReconnectHandlerBinder;
import Abyss.event.events.DisconnectedInitEvent;
import Abyss.event.events.PreDrawScreenEvent;
import Abyss.event.events.ServerJoinEvent;
import Abyss.internal.auth.Account;
import Abyss.internal.auth.AltManager;
import Abyss.internal.auth.SessionAccessor;
import Abyss.util.ChatFormatting;
import Abyss.util.MinecraftRef;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.StringUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ReconnectHandler
implements EventSubscriber {
    
    private static long[] h;
    
        private static Map j;
    private static Map g;
    
    private static Minecraft T;

    public void onPreDrawScreen(PreDrawScreenEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (ReconnectHandler.c(var1.Q)) {
            String var9 = ChatFormatting.y(String.format("&7Username: &3%s&r", SessionAccessor.d().getUsername()));
            GlStateManager.disableLighting();
            var1.Q.drawString(ReconnectHandler.T.fontRendererObj, var9, 3, 3, -1);
            GlStateManager.enableLighting();
}
}
    private static long t(String var0) {
        long var3 = System.currentTimeMillis();
        block12: for (String var8 : var0.split(" ")) {
            if (var8.isEmpty()) continue;
            String var9 = var8.substring(var8.length() - 1);
            long var10 = Long.parseLong(var8.substring(0, var8.length() - 1));
            switch (var9) {
                case "d": {
                    var3 += var10 * 86400000L;
                    continue block12;
}
                case "h": {
                    var3 += var10 * 3600000L;
                    continue block12;
}
                case "m": {
                    var3 += var10 * 60000L;
                    continue block12;
}
                case "s": {
                    var3 += var10 * 1000L;
}
}
}
        return var3;
}
    private static void z(long var0, long var2) {
        AltManager.Q(17200, (short)3883, (short)-9723);
        for (Account var10 : AltManager.Q) {
            if (!SessionAccessor.d().getUsername().equals(var10.h())) continue;
            var10.G(var0);
}
        AltManager.O(101554584226764L);
}
    public void onDisconnectedInit(long var1, DisconnectedInitEvent var3) {
        if (var3.O instanceof GuiDisconnected && var3.X != null) {
            String var8 = var3.X.getFormattedText().split("\n\n")[0];
            if (!var8.equals("\u00a7r\u00a7cYou are permanently banned from this server!") && !var8.equals("\u00a7r\u00a7cYour account has been blocked.")) {
                String var9;
                if ((var8.matches("\u00a7r\u00a7cYou are temporarily banned for \u00a7r\u00a7f.*\u00a7r\u00a7c from this server!") || var8.matches("\u00a7r\u00a7cYour account is temporarily blocked for \u00a7r\u00a7f.*\u00a7r\u00a7c from this server!")) && (var9 = StringUtils.substringBetween((String)var8, (String)"\u00a7r\u00a7f", (String)"\u00a7r\u00a7c")) != null) {
                    ReconnectHandler.z(ReconnectHandler.t(var9), 60323149919382L);
}
            } else {
                ReconnectHandler.z(-1L, 60323149919382L);
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        ReconnectHandlerBinder.v(var3, this);
}
    private static boolean c(GuiScreen var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var0 == null) {
            return false;
}
        if (!(var0 instanceof GuiSelectWorld) && !(var0 instanceof GuiMultiplayer)) {
            for (Class<?> var3 = var0.getClass(); var3 != null; var3 = var3.getSuperclass()) {
                String var4 = var3.getName();
                String var5 = var4.toLowerCase();
                if (!var4.endsWith(".GuiSelectWorld") && !var4.endsWith(".GuiMultiplayer") && !var4.equals("net.minecraft.client.gui.GuiSelectWorld") && !var4.equals("net.minecraft.client.gui.GuiMultiplayer") && !var5.contains("multiplayer") && !var5.contains("selectworld") && !var5.contains("worldselection")) continue;
                return true;
}
            return false;
}
        return true;
}
    public void onServerJoin(long var1, ServerJoinEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        String var7;
        ServerData var6 = var3.u;
        if (var6 != null && (var7 = var6.serverIP) != null && (var7.endsWith("hypixel.net") || var7.endsWith("hypixel.io"))) {
            ReconnectHandler.z(0L, 60323149919382L);
}
}
    static {
        T = MinecraftRef.c((byte)0, 0L);
}
}