/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ASM;

import Abyss.ASM.Block.TransformBlock;
import Abyss.ASM.Block.TransformBlockBarrier;
import Abyss.ASM.Block.TransformBlockBush;
import Abyss.ASM.Block.TransformBlockGrass;
import Abyss.ASM.Block.TransformBlockLeaves;
import Abyss.ASM.Block.TransformBlockModelRenderer;
import Abyss.ASM.Block.TransformBlockModelShapes;
import Abyss.ASM.Block.TransformBlockRendererDispatcher;
import Abyss.ASM.Block.TransformBlockStateMapper;
import Abyss.ASM.ClassTransform;
import Abyss.ASM.Entity.TransformEntity;
import Abyss.ASM.Entity.TransformEntityItem;
import Abyss.ASM.Entity.TransformEntityLivingBase;
import Abyss.ASM.Entity.TransformEntityPlayer;
import Abyss.ASM.Entity.TransformEntityPlayerSP;
import Abyss.ASM.Entity.TransformEntityRenderer;
import Abyss.ASM.Entity.TransformRenderEntityItem;
import Abyss.ASM.Entity.TransformRendererLivingEntity;
import Abyss.ASM.GenericTransformer;
import Abyss.ASM.Gui.TransformGuiChat;
import Abyss.ASM.Gui.TransformGuiContainer;
import Abyss.ASM.Gui.TransformGuiDisconnected;
import Abyss.ASM.Gui.TransformGuiIngame;
import Abyss.ASM.Gui.TransformGuiMainMenu;
import Abyss.ASM.Gui.TransformGuiPlayerTabOverlay;
import Abyss.ASM.Gui.TransformGuiScreen;
import Abyss.ASM.Gui.TransformGuiTextField;
import Abyss.ASM.Network.TransformNetHandlerPlayClient;
import Abyss.ASM.Network.TransformNetworkManager;
import Abyss.ASM.Network.TransformNetworkPlayerInfo;
import Abyss.ASM.Network.TransformS12PacketEntityVelocity;
import Abyss.ASM.Player.TransformItemInWorldManager;
import Abyss.ASM.Player.TransformKeyBinding;
import Abyss.ASM.Player.TransformMovementInputFromOptions;
import Abyss.ASM.Player.TransformPlayerControllerMP;
import Abyss.ASM.Render.TransformEffectRenderer;
import Abyss.ASM.Render.TransformFontRenderer;
import Abyss.ASM.Render.TransformItemRenderer;
import Abyss.ASM.Render.TransformLoadingScreenRenderer;
import Abyss.ASM.Render.TransformModelBiped;
import Abyss.ASM.Render.TransformModelPlayer;
import Abyss.ASM.Render.TransformWorldRenderer;
import Abyss.ASM.TransformLayerCape;
import Abyss.ASM.TransformMinecraft;
import Abyss.ASM.TransformVisGraph;
import Abyss.ASM.World.TransformWorld;
import Abyss.ASM.World.TransformWorldClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransformerRegistry {
    private static long[] e;
    
    private static String[] i;
    private static Object[] h;
    
    
        private static Map g;
    public static boolean $skidonion$1232505612;
    public static final String[] Z_TARGETS;
    private static volatile Map<String, ClassTransform> stage1$registry;
    private static volatile List<ClassTransform> stage1$all;

    public static ClassTransform k(String var0) {
        if (var0 == null) {
            return null;
}
        return TransformerRegistry.H().get(var0.replace('.', '/'));
}
    public static List<ClassTransform> q() {
        TransformerRegistry.H();
        return stage1$all;
}
    public static Map<String, ClassTransform> H() {
        Map<String, ClassTransform> var0 = stage1$registry;
        if (var0 != null) {
            return var0;
        }

        synchronized (TransformerRegistry.class) {
            var0 = stage1$registry;
            if (var0 != null) {
                return var0;
            }

            Map<String, ClassTransform> var1 = new LinkedHashMap<String, ClassTransform>();
            List<ClassTransform> var2 = new ArrayList<ClassTransform>();
            TransformerRegistry.stage1$build(var1, var2);
            stage1$all = Collections.unmodifiableList(var2);
            var0 = Collections.unmodifiableMap(var1);
            stage1$registry = var0;
            return var0;
        }
    }

    private TransformerRegistry() {
}
    public static List<ClassTransform> R() {
        return TransformerRegistry.q();
}
    private static void r(Map<String, ClassTransform> var0, ClassTransform var1, String var2) {
        var0.put(var2.replace('.', '/'), var1);
}
    private static void stage1$build(Map<String, ClassTransform> var0, List<ClassTransform> var1) {
        TransformerRegistry.r(var0, new TransformBlock(), "net/minecraft/block/Block");
        TransformerRegistry.r(var0, new TransformBlockBarrier(), "net/minecraft/block/BlockBarrier");
        TransformerRegistry.r(var0, new TransformBlockBush(), "net/minecraft/block/BlockBush");
        TransformerRegistry.r(var0, new TransformBlockGrass(), "net/minecraft/block/BlockGrass");
        TransformerRegistry.r(var0, new TransformBlockLeaves(), "net/minecraft/block/BlockLeaves");
        TransformerRegistry.r(var0, new TransformBlockModelRenderer(), "net/minecraft/client/renderer/BlockModelRenderer");
        TransformerRegistry.r(var0, new TransformBlockModelShapes(), "net/minecraft/client/renderer/BlockModelShapes");
        TransformerRegistry.r(var0, new TransformBlockRendererDispatcher(), "net/minecraft/client/renderer/BlockRendererDispatcher");
        TransformerRegistry.r(var0, new TransformBlockStateMapper(), "net/minecraft/client/renderer/block/statemap/BlockStateMapper");
        TransformerRegistry.r(var0, new TransformEffectRenderer(), "net/minecraft/client/particle/EffectRenderer");
        TransformerRegistry.r(var0, new TransformEntity(), "net/minecraft/entity/Entity");
        TransformerRegistry.r(var0, new TransformEntityItem(), "net/minecraft/entity/item/EntityItem");
        TransformerRegistry.r(var0, new TransformEntityLivingBase(), "net/minecraft/entity/EntityLivingBase");
        TransformerRegistry.r(var0, new TransformEntityPlayer(), "net/minecraft/entity/player/EntityPlayer");
        TransformerRegistry.r(var0, new TransformEntityPlayerSP(), "net/minecraft/client/entity/EntityPlayerSP");
        TransformerRegistry.r(var0, new TransformEntityRenderer(), "net/minecraft/client/renderer/EntityRenderer");
        TransformerRegistry.r(var0, new TransformFontRenderer(), "net/minecraft/client/gui/FontRenderer");
        TransformerRegistry.r(var0, new TransformGuiChat(), "net/minecraft/client/gui/GuiChat");
        TransformerRegistry.r(var0, new TransformGuiContainer(), "net/minecraft/client/gui/inventory/GuiContainer");
        TransformerRegistry.r(var0, new TransformGuiDisconnected(), "net/minecraft/client/gui/GuiDisconnected");
        TransformerRegistry.r(var0, new TransformGuiIngame(), "net/minecraft/client/gui/GuiIngame");
        TransformerRegistry.r(var0, new TransformGuiMainMenu(), "net/minecraft/client/gui/GuiMainMenu");
        TransformerRegistry.r(var0, new TransformGuiPlayerTabOverlay(), "net/minecraft/client/gui/GuiPlayerTabOverlay");
        TransformerRegistry.r(var0, new TransformGuiScreen(), "net/minecraft/client/gui/GuiScreen");
        TransformerRegistry.r(var0, new TransformGuiTextField(), "net/minecraft/client/gui/GuiTextField");
        TransformerRegistry.r(var0, new TransformItemInWorldManager(), "net/minecraft/server/management/ItemInWorldManager");
        TransformerRegistry.r(var0, new TransformItemRenderer(), "net/minecraft/client/renderer/ItemRenderer");
        TransformerRegistry.r(var0, new TransformKeyBinding(), "net/minecraft/client/settings/KeyBinding");
        TransformerRegistry.r(var0, new TransformLayerCape(), "net/minecraft/client/renderer/entity/layers/LayerCape");
        TransformerRegistry.r(var0, new TransformLoadingScreenRenderer(), "net/minecraft/client/LoadingScreenRenderer");
        TransformerRegistry.r(var0, new TransformMinecraft(), "net/minecraft/client/Minecraft");
        TransformerRegistry.r(var0, new TransformModelBiped(), "net/minecraft/client/model/ModelBiped");
        TransformerRegistry.r(var0, new TransformModelPlayer(), "net/minecraft/client/model/ModelPlayer");
        TransformerRegistry.r(var0, new TransformMovementInputFromOptions(), "net/minecraft/util/MovementInputFromOptions");
        TransformerRegistry.r(var0, new TransformNetHandlerPlayClient(), "net/minecraft/client/network/NetHandlerPlayClient");
        TransformerRegistry.r(var0, new TransformNetworkManager(), "net/minecraft/network/NetworkManager");
        TransformerRegistry.r(var0, new TransformNetworkPlayerInfo(), "net/minecraft/client/network/NetworkPlayerInfo");
        TransformerRegistry.r(var0, new TransformPlayerControllerMP(), "net/minecraft/client/multiplayer/PlayerControllerMP");
        TransformerRegistry.r(var0, new TransformRenderEntityItem(), "net/minecraft/client/renderer/entity/RenderEntityItem");
        TransformerRegistry.r(var0, new TransformRendererLivingEntity(), "net/minecraft/client/renderer/entity/RendererLivingEntity");
        TransformerRegistry.r(var0, new TransformS12PacketEntityVelocity(), "net/minecraft/network/play/server/S12PacketEntityVelocity");
        TransformerRegistry.r(var0, new TransformVisGraph(), "net/minecraft/client/renderer/chunk/VisGraph");
        TransformerRegistry.r(var0, new TransformWorld(), "net/minecraft/world/World");
        TransformerRegistry.r(var0, new TransformWorldClient(), "net/minecraft/client/multiplayer/WorldClient");
        TransformerRegistry.r(var0, new TransformWorldRenderer(), "net/minecraft/client/renderer/WorldRenderer");
        for (int var2 = 0; var2 < Z_TARGETS.length; ++var2) {
            TransformerRegistry.r(var0, new GenericTransformer(Z_TARGETS[var2]), Z_TARGETS[var2]);
}
        var1.addAll(var0.values());
}
    static {
        Z_TARGETS = new String[]{"net/minecraft/client/gui/GuiSelectWorld", "net/minecraft/client/gui/GuiMultiplayer"};
}
}