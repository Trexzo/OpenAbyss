/*
 * Decompiled with CFR 0.152.
 */
package Abyss.inject;

import Abyss.ASM.TransformerRegistry;
import Abyss.inject.InjectHookRegistry;
import Abyss.inject.InjectLog;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class InjectHooks {
    private static final String MINECRAFT = "net/minecraft/client/Minecraft";
    private static final String KEY_BINDING = "net/minecraft/client/settings/KeyBinding";
    private static final String GUI_INGAME = "net/minecraft/client/gui/GuiIngame";
    private static final String ENTITY_RENDERER = "net/minecraft/client/renderer/EntityRenderer";
    private static final String NETWORK_MANAGER = "net/minecraft/network/NetworkManager";
    private static final String WORLD_CLIENT = "net/minecraft/client/multiplayer/WorldClient";
    private static final String PACKET = "net/minecraft/network/Packet";
    private static final String CHANNEL_CONTEXT = "io/netty/channel/ChannelHandlerContext";
    private static final Map<String, Runnable> DEFINITIONS = new LinkedHashMap<String, Runnable>();
    private static final Set<String> ARMED = new LinkedHashSet<String>();
    private static boolean defined;

    private InjectHooks() {
}
    public static synchronized boolean hasFallback(String mcpOwner) {
        if (mcpOwner == null) {
            return false;
}
        InjectHooks.define();
        return DEFINITIONS.containsKey(mcpOwner.replace('.', '/'));
}
    public static synchronized void register() {
        InjectHooks.define();
        ArrayList<String> suppressed = new ArrayList<String>();
        for (String owner : DEFINITIONS.keySet()) {
            if (InjectHooks.ownedByCoremod(owner)) {
                suppressed.add(owner);
                continue;
}
            InjectHooks.arm(owner);
}
        if (!suppressed.isEmpty()) {
            InjectLog.line("supplementary hooks held back for " + suppressed.size() + " class(es) the coremod already patches: " + suppressed);
}
}
    public static synchronized boolean armFallback(String mcpOwner) {
        if (mcpOwner == null) {
            return false;
}
        InjectHooks.define();
        String key = mcpOwner.replace('.', '/');
        if (!DEFINITIONS.containsKey(key) || ARMED.contains(key)) {
            return false;
}
        InjectLog.line("arming the supplementary fallback for " + key + " -- its coremod transformer matched but patched nothing");
        InjectHooks.arm(key);
        return true;
}
    private static void arm(String owner) {
        if (!ARMED.add(owner)) {
            return;
}
        Runnable definition = DEFINITIONS.get(owner);
        if (definition == null) {
            return;
}
        try {
            definition.run();
}
        catch (Throwable t2) {
            InjectLog.throwable("could not register the hooks for " + owner, t2);
}
}
    private static boolean ownedByCoremod(String mcpOwner) {
        try {
            return TransformerRegistry.k(mcpOwner) != null;
}
        catch (Throwable t2) {
            InjectLog.swallowed(t2);
            return false;
}
}
    private static synchronized void define() {
        if (defined) {
            return;
}
        defined = true;
        DEFINITIONS.put(MINECRAFT, new Runnable(){

            @Override
            public void run() {
                InjectHooks.minecraft();
}
        });
        DEFINITIONS.put(KEY_BINDING, new Runnable(){

            @Override
            public void run() {
                InjectHooks.keyBinding();
}
        });
        DEFINITIONS.put(GUI_INGAME, new Runnable(){

            @Override
            public void run() {
                InjectHooks.overlay();
}
        });
        DEFINITIONS.put(ENTITY_RENDERER, new Runnable(){

            @Override
            public void run() {
                InjectHooks.world();
}
        });
        DEFINITIONS.put(NETWORK_MANAGER, new Runnable(){

            @Override
            public void run() {
                InjectHooks.network();
}
        });
}
    private static void minecraft() {
        InjectHookRegistry.hook(MINECRAFT, "runTick", "()V").at(InjectHookRegistry.Position.HEAD).calls("tickPre").add();
        InjectHookRegistry.hook(MINECRAFT, "runTick", "()V").at(InjectHookRegistry.Position.RETURN).calls("tickPost").add();
        InjectHookRegistry.hook(MINECRAFT, "startGame", "()V").at(InjectHookRegistry.Position.RETURN).calls("startGame").add();
        InjectHookRegistry.hook(MINECRAFT, "clickMouse", "()V").at(InjectHookRegistry.Position.HEAD).cancellable().calls("clickMouse", "()Z").add();
        InjectHookRegistry.hook(MINECRAFT, "clickMouse", "()V").at(InjectHookRegistry.Position.RETURN).calls("postClickMouse").add();
        InjectHookRegistry.hook(MINECRAFT, "rightClickMouse", "()V").at(InjectHookRegistry.Position.HEAD).cancellable().calls("rightClickMouse", "()Z").add();
        InjectHookRegistry.hook(MINECRAFT, "rightClickMouse", "()V").at(InjectHookRegistry.Position.RETURN).calls("postRightClick").add();
        InjectHookRegistry.hook(MINECRAFT, "loadWorld", "(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V").at(InjectHookRegistry.Position.HEAD).calls("loadWorld").add();
        InjectHookRegistry.hook(MINECRAFT, "runTick", "()V").at(InjectHookRegistry.Position.REPLACE_INVOKE).invoking(KEY_BINDING, "setKeyBindState", "(IZ)V").calls("keyBindState", "(IZ)V").add();
}
    private static void keyBinding() {
        InjectHookRegistry.hook(KEY_BINDING, "onTick", "(I)V").at(InjectHookRegistry.Position.HEAD).args("0").calls("keyBindingTick", "(I)V").add();
}
    private static void overlay() {
        InjectHookRegistry.hook(GUI_INGAME, "renderGameOverlay", "(F)V").at(InjectHookRegistry.Position.HEAD).args("0").calls("render2DPre", "(F)V").add();
        InjectHookRegistry.hook(GUI_INGAME, "renderGameOverlay", "(F)V").at(InjectHookRegistry.Position.RETURN).calls("render2DPost").add();
}
    private static void world() {
        InjectHookRegistry.hook(ENTITY_RENDERER, "renderWorldPass", "(IFJ)V").at(InjectHookRegistry.Position.HEAD).args("1").calls("render3DPre", "(F)V").add();
        InjectHookRegistry.hook(ENTITY_RENDERER, "renderWorldPass", "(IFJ)V").at(InjectHookRegistry.Position.BEFORE_FIELD).field(ENTITY_RENDERER, "renderHand", "Z").calls("render3DPost").add();
}
    private static void network() {
        InjectHookRegistry.hook(NETWORK_MANAGER, "channelRead0", "(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V").at(InjectHookRegistry.Position.HEAD).args("1").cancellable().calls("packetReceive", "(Ljava/lang/Object;)Z").add();
        InjectHookRegistry.hook(NETWORK_MANAGER, "sendPacket", "(Lnet/minecraft/network/Packet;)V").at(InjectHookRegistry.Position.HEAD).args("0").cancellable().calls("packetSend", "(Ljava/lang/Object;)Z").add();
}
}