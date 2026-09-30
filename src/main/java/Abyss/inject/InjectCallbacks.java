/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.network.Packet
 */
package Abyss.inject;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.Entity.EntityRendererHooks;
import Abyss.ASM.Hooks.MinecraftHooks;
import Abyss.ASM.Hooks.Network.NetworkManagerHooks;
import Abyss.ASM.Hooks.Player.KeyBindingHooks;
import Abyss.AbyssClient;
import Abyss.inject.InjectBootstrap;
import Abyss.inject.InjectLog;
import Abyss.inject.InjectNativeBridge;
import Abyss.internal.jnic.GameStartLatch;
import Abyss.util.MinecraftRef;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.network.Packet;

public final class InjectCallbacks {
    public static final String OWNER = "Abyss/inject/InjectCallbacks";
    private static float overlayPartialTicks;
    private static float worldPartialTicks;

    private InjectCallbacks() {
}
    private static boolean live() {
        return InjectBootstrap.isStarted() && AbyssClient.w != null;
}
    public static void startGame() {
        try {
            GameStartLatch.w.set(true);
            InjectLog.line("game reached the end of startGame");
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void tickPre() {
        try {
            InjectBootstrap.tick();
            InjectNativeBridge.flushTransformLog();
            if (!InjectCallbacks.live()) {
                return;
}
            MinecraftHooks.onPreTick();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void tickPost() {
        try {
            if (!InjectCallbacks.live()) {
                return;
}
            MinecraftHooks.onPostTick();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void render2DPre(float partialTicks) {
        overlayPartialTicks = partialTicks;
}
    public static void render2DPost() {
        try {
            if (!InjectCallbacks.live()) {
                return;
}
            EntityRendererHooks.onRender2D(overlayPartialTicks);
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void render3DPre(float partialTicks) {
        worldPartialTicks = partialTicks;
}
    public static void render3DPost() {
        try {
            if (!InjectCallbacks.live()) {
                return;
}
            EntityRenderer renderer = MinecraftRef.c((byte)0, (long)0L).entityRenderer;
            if (renderer == null) {
                return;
}
            EntityRendererHooks.onRender3D(renderer, worldPartialTicks);
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static boolean clickMouse() {
        try {
            if (!InjectCallbacks.live()) {
                return false;
}
            CallbackInfo info = new CallbackInfo();
            MinecraftHooks.onClickMouse(info);
            return info.isCancelled();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
            return false;
}
}
    public static boolean rightClickMouse() {
        try {
            if (!InjectCallbacks.live()) {
                return false;
}
            CallbackInfo info = new CallbackInfo();
            MinecraftHooks.onRightClickMouse(info);
            return info.isCancelled();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
            return false;
}
}
    public static void postClickMouse() {
        try {
            if (!InjectCallbacks.live()) {
                return;
}
            MinecraftHooks.onPostClickMouse();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void postRightClick() {
        try {
            if (!InjectCallbacks.live()) {
                return;
}
            MinecraftHooks.onPostRightClick();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void keyBindState(int key, boolean pressed) {
        try {
            MinecraftHooks.onSetKeyBindState(key, pressed);
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void keyBindingTick(int key) {
        try {
            if (!InjectCallbacks.live()) {
                return;
}
            KeyBindingHooks.onTick(key);
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static void loadWorld() {
        try {
            if (!InjectCallbacks.live()) {
                return;
}
            MinecraftHooks.onLoadWorld();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
}
}
    public static boolean packetReceive(Object raw) {
        try {
            if (!InjectCallbacks.live() || !(raw instanceof Packet)) {
                return false;
}
            CallbackInfo info = new CallbackInfo();
            NetworkManagerHooks.onReceivePacket((Packet)raw, info);
            return info.isCancelled();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
            return false;
}
}
    public static boolean packetSend(Object raw) {
        try {
            if (!InjectCallbacks.live() || !(raw instanceof Packet)) {
                return false;
}
            CallbackInfo info = new CallbackInfo();
            NetworkManagerHooks.onSendPacket((Packet)raw, info);
            return info.isCancelled();
}
        catch (Throwable swallowed) {
            InjectLog.swallowed(swallowed);
            return false;
}
}
}