/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiDisconnected
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.IChatComponent
 *  org.lwjgl.input.Mouse
 */
package Abyss.ASM.Hooks.Gui;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.AbyssClient;
import Abyss.event.Event;
import Abyss.event.events.ActionPerformedEvent;
import Abyss.event.events.DisconnectedInitEvent;
import Abyss.event.events.GuiMouseEvent;
import Abyss.event.events.InitGuiEvent;
import Abyss.event.events.PreDrawScreenEvent;
import Abyss.event.events.ServerJoinEvent;
import Abyss.internal.auth.AltManager;
import Abyss.util.MinecraftRef;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;

public class GuiEventHooks {
    private static boolean k;
    private static Field j;
    private static boolean J;
    private static Field G;
    private static final Minecraft n;
    private static final long private static void ensureInitialized() {
        if (!k && AbyssClient.w != null) {
            try {
                AltManager.M(7874752644491L);
                k = true;
}
            catch (Throwable throwable) {
                // empty catch block
}
}
}
    private static void post(Event var0) {
        if (AbyssClient.w != null) {
            AbyssClient.w.e(var0, 18670087776179L);
}
}
    public static void onDrawScreen(GuiScreen var0) {
        GuiEventHooks.ensureInitialized();
        GuiEventHooks.post(new PreDrawScreenEvent(var0));
}
    public static void onInitGui(GuiScreen var0) {
        GuiEventHooks.ensureInitialized();
        GuiEventHooks.post(new InitGuiEvent(var0, GuiEventHooks.buttonList(var0)));
}
    public static void onClientTick() {
        GuiScreen var4 = GuiEventHooks.n.field_71462_r;
        boolean var5 = Mouse.isButtonDown((int)0);
        boolean var6 = var5 && !J;
        J = var5;
        if (var4 != null) {
            GuiEventHooks.ensureInitialized();
            int var7 = Mouse.getX() * var4.field_146294_l / GuiEventHooks.n.field_71443_c;
            int var8 = var4.field_146295_m - Mouse.getY() * var4.field_146295_m / GuiEventHooks.n.field_71440_d - 1;
            GuiEventHooks.post(new GuiMouseEvent(var4, var6, var7, var8));
}
}
    public static void onLoadWorld() {
        GuiEventHooks.ensureInitialized();
        GuiEventHooks.post(new ServerJoinEvent(n.func_147104_D(), 31027, '\u0e8c', -11246));
}
    public static void onActionPerformed(GuiScreen var0, GuiButton var1, CallbackInfo var2) {
        GuiEventHooks.ensureInitialized();
        ActionPerformedEvent var7 = new ActionPerformedEvent(var0, var1);
        GuiEventHooks.post(var7);
        if (var7.a()) {
            var2.cancel();
}
}
    private static Field findButtonListField() throws NoSuchFieldException {
        try {
            return GuiEventHooks.findField(GuiScreen.class, "buttonList", "field_146292_n", "n");
}
        catch (Throwable var6) {
            for (Class var0 = GuiScreen.class; var0 != null; var0 = var0.getSuperclass()) {
                Field[] var1;
                for (Field var5 : var1 = var0.getDeclaredFields()) {
                    if (!List.class.isAssignableFrom(var5.getType()) && !ArrayList.class.isAssignableFrom(var5.getType())) continue;
                    var5.setAccessible(true);
                    return var5;
}
}
            throw new NoSuchFieldException("buttonList");
}
}
    private GuiEventHooks() {
}
    public static List<GuiButton> buttonList(GuiScreen var0) {
        try {
            if (j == null) {
                j = GuiEventHooks.findButtonListField();
}
            return (List)j.get(var0);
}
        catch (Throwable var2) {
            return null;
}
}
    public static void onDisconnectedInit(GuiScreen var0) {
        if (var0 instanceof GuiDisconnected) {
            GuiEventHooks.ensureInitialized();
            try {
                if (G == null) {
                    G = GuiEventHooks.findField(GuiDisconnected.class, "message", "field_146304_f", "f");
}
                GuiEventHooks.post(new DisconnectedInitEvent(var0, (IChatComponent)G.get(var0)));
}
            catch (Throwable throwable) {
                // empty catch block
}
}
}
    private static Field findField(Class<?> var0, String ... var1) throws NoSuchFieldException {
        for (Class<?> var2 = var0; var2 != null; var2 = var2.getSuperclass()) {
            for (String var6 : var1) {
                try {
                    Field var7 = var2.getDeclaredField(var6);
                    var7.setAccessible(true);
                    return var7;
}
                catch (NoSuchFieldException noSuchFieldException) {
}
}
}
        throw new NoSuchFieldException(var0.getName());
}
    public static void onMouseClicked(GuiScreen var0, int var1, int var2, int var3, CallbackInfo var4) {
        if (var3 == 0) {
            GuiEventHooks.ensureInitialized();
            GuiMouseEvent var9 = new GuiMouseEvent(var0, true, var1, var2);
            GuiEventHooks.post(var9);
            if (var9.a()) {
                var4.cancel();
}
}
}
    static {
        n = MinecraftRef.c((byte)0, 0L);
}
}