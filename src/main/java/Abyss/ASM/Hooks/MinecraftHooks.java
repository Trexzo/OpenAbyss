/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.ScreenShotHelper
 */
package Abyss.ASM.Hooks;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.Gui.GuiEventHooks;
import Abyss.AbyssClient;
import Abyss.event.events.ClickMouseEvent;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.PostClickMouseEvent;
import Abyss.event.events.PostRightClickEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.RightClickMouseEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.internal.jnic.GameStartLatch;
import Abyss.internal.jnic.StockClientBootstrap;
import Abyss.util.MinecraftRef;
import Abyss.util.render.VisualSpoofRenderer;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ScreenShotHelper;

public class MinecraftHooks {
    private static Minecraft P;
        private static long b;
    private static long v;

    public static void onClickMouse(CallbackInfo var0) {
        if (AbyssClient.w != null) {
            ClickMouseEvent var7 = new ClickMouseEvent();
            AbyssClient.w.e(var7, 18670087776179L);
            if (var7.a()) {
                var0.cancel();
}
}
}
    public static void changeCurrentItem(InventoryPlayer var0, int var1) {
        if (AbyssClient.w == null) {
            var0.func_70453_c(var1);
        } else {
            HeldItemChangeEvent var8 = new HeldItemChangeEvent(-1, var1);
            AbyssClient.w.e(var8, 18670087776179L);
            if (!var8.a()) {
                var0.func_70453_c(var1);
}
}
}
    public static void onStartGame() {
        GameStartLatch.w.set(true);
        MinecraftHooks.t();
}
    public static void onPostClickMouse() {
        if (AbyssClient.w != null) {
            PostClickMouseEvent var6 = new PostClickMouseEvent();
            AbyssClient.w.e(var6, 18670087776179L);
}
}
    public static void onSetKeyBindState(int var0, boolean var1) {
        KeyBinding.func_74510_a((int)var0, (boolean)var1);
        if (AbyssClient.w != null && var1 && MinecraftHooks.P.field_71462_r == null) {
            AbyssClient.w.e(new SetKeyBindStateEvent(var0), 18670087776179L);
}
}
    private static void t() {
        long var4;
        if (AbyssClient.w == null && (var4 = System.currentTimeMillis()) - v >= b) {
            v = var4;
            StockClientBootstrap.F();
}
}
    public static void onPostRightClick() {
        if (AbyssClient.w != null) {
            AbyssClient.w.e(new PostRightClickEvent(), 18670087776179L);
}
}
    public static void onPreTick() {
        MinecraftHooks.t();
        if (AbyssClient.w != null) {
            AbyssClient.w.e(new PreTickEvent(), 18670087776179L);
}
}
    public static void onPostTick() {
        GuiEventHooks.onClientTick();
        if (AbyssClient.w != null) {
            AbyssClient.w.e(new PostTickEvent(), 18670087776179L);
}
}
    public static boolean notAllowUserInput() {
        boolean var6;
        boolean bl = var6 = MinecraftHooks.P.field_71462_r != null && MinecraftHooks.P.field_71462_r.field_146291_p;
        if (!var6 && AbyssClient.w != null) {
            PreMouseInputEvent var7 = new PreMouseInputEvent();
            AbyssClient.w.e(var7, 18670087776179L);
}
        return var6;
}
    public static IChatComponent onSaveScreenshot(File var0, int var1, int var2, Framebuffer var3) throws Throwable {
        Framebuffer var8;
        if (VisualSpoofRenderer.B() && (var8 = VisualSpoofRenderer.f(127872219919683L)) != null) {
            return ScreenShotHelper.func_148260_a((File)var0, (int)var1, (int)var2, (Framebuffer)var8);
}
        return ScreenShotHelper.func_148260_a((File)var0, (int)var1, (int)var2, (Framebuffer)var3);
}
    public static void onOptimizeWorldSwapping() {
}
    public static boolean[] onPreMouseInput() {
        if (AbyssClient.w == null) {
            return new boolean[]{false, false, false, false};
}
        PreMouseInputEvent var8 = new PreMouseInputEvent();
        AbyssClient.w.e(var8, 18670087776179L);
        return var8.M();
}
    public static void onLoadWorld() {
        GuiEventHooks.onLoadWorld();
        if (AbyssClient.w != null) {
            AbyssClient.w.e(new WorldLoadEvent(), 18670087776179L);
}
}
    public static void onRightClickMouse(CallbackInfo var0) {
        if (AbyssClient.w != null) {
            RightClickMouseEvent var7 = new RightClickMouseEvent();
            AbyssClient.w.e(var7, 18670087776179L);
            if (var7.a()) {
                var0.cancel();
}
}
}
    static {
        P = MinecraftRef.c((byte)0, 0L);
        b = 1000L;
}
}