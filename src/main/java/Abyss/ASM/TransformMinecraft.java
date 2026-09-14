/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package Abyss.ASM;

import Abyss.ASM.TransformerBase;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.MethodInsnMatcher;
import Abyss.ASM.Util.SrgNames;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class TransformMinecraft
extends TransformerBase {
    private static long d = 56557798079205L;

    public TransformMinecraft() {
        super("net/minecraft/client/Minecraft");
}
    @Override
    public boolean s(ClassNode var1) {
        boolean var4 = false;
        var4 |= BytecodeHelper.t(var1, "()V", (var1x, var2x) -> {
            boolean var5x = false;
            var5x |= this.A(var2x);
            var5x |= BytecodeHelper.M(var2x, BytecodeHelper.r("net/minecraft/client/gui/GuiScreen", "Z", "allowUserInput", "allowUserInput"), (InsnList var0) -> {
                long var1xx = d ^ 0x7473BCBBC3A6L;
                var0.add((AbstractInsnNode)new InsnNode(87));
                BytecodeHelper.Y(var0, R, "Minecraft$notAllowUserInput", "()Z");
            });
            var5x |= BytecodeHelper.R(var2x, BytecodeHelper.s("net/minecraft/entity/player/InventoryPlayer", "(I)V", "changeCurrentItem", "changeCurrentItem"), (var0, var1xx) -> BytecodeHelper.Y(var0, R, "Minecraft$changeCurrentItem", "(" + SrgNames.X("net/minecraft/entity/player/InventoryPlayer") + "I)V"));
            return (var5x |= TransformerBase.u(var2x, Type.VOID_TYPE, false, false, (var0, var1xx) -> BytecodeHelper.Y(var0, v, "minecraftRunTickHead", "()V"))) | TransformerBase.M(var2x, Type.VOID_TYPE, (var0, var1xx) -> BytecodeHelper.Y(var0, R, "Minecraft$onPostTick", "()V"));
        }, "runTick", "runTick");
        var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> {
            long var2x = d ^ 0x16A0DAB51BB3L;
            return TransformerBase.g(var1x, "net/minecraft/client/Minecraft", SrgNames.X("net/minecraft/client/gui/GuiIngame"), var0x -> {
                long var1xx = d ^ 0x7707F4F24D2AL;
                BytecodeHelper.Y(var0x, R, "Minecraft$onStartGame", "()V");
            }, "ingameGUI", "ingameGUI");
        }, "startGame", "startGame");
        var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> {
            long var2x = d ^ 0x445407F3E4B7L;
            boolean var4x = TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
                BytecodeHelper.I(var0x, var1xx);
                BytecodeHelper.Y(var0x, v, "minecraftClickMouseHead", "(" + z + ")V");
            });
            return var4x | TransformerBase.M(var1x, Type.VOID_TYPE, (var0x, var1xx) -> BytecodeHelper.Y(var0x, R, "Minecraft$onPostClickMouse", "()V"));
        }, "clickMouse", "clickMouse");
        var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> {
            long var2x = d ^ 0x3B25035897F4L;
            boolean var4x = TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
                BytecodeHelper.I(var0x, var1xx);
                BytecodeHelper.Y(var0x, v, "minecraftRightClickMouseHead", "(" + z + ")V");
            });
            return var4x | TransformerBase.M(var1x, Type.VOID_TYPE, (var0x, var1xx) -> BytecodeHelper.Y(var0x, R, "Minecraft$onPostRightClick", "()V"));
        }, "rightClickMouse", "rightClickMouse");
        var4 |= BytecodeHelper.t(var1, "(Z)V", (var0, var1x) -> {
            long var2x = d ^ 0x33BE3EAD1EC8L;
            return TransformerBase.u(var1x, Type.VOID_TYPE, false, true, (var0x, var1xx) -> {
                BytecodeHelper.I(var0x, var1xx);
                BytecodeHelper.Y(var0x, v, "minecraftSendClickBlockHead", "(" + z + ")V");
            });
        }, "sendClickBlockToController", "sendClickBlockToController");
        return (var4 |= BytecodeHelper.t(var1, "()V", (var0, var1x) -> {
            long var2x = d ^ 0x1DEFAF3FC8AEL;
            return BytecodeHelper.R(var1x, BytecodeHelper.s("net/minecraft/util/ScreenShotHelper", "(Ljava/io/File;II" + SrgNames.X("net/minecraft/client/shader/Framebuffer") + ")" + SrgNames.X("net/minecraft/util/IChatComponent"), "saveScreenshot", "saveScreenshot"), (var0x, var1xx) -> BytecodeHelper.Y(var0x, R, "Minecraft$onSaveScreenshot", "(Ljava/io/File;II" + SrgNames.X("net/minecraft/client/shader/Framebuffer") + ")" + SrgNames.X("net/minecraft/util/IChatComponent")));
        }, "dispatchKeypresses", "dispatchKeypresses")) | BytecodeHelper.t(var1, "(" + SrgNames.X("net/minecraft/client/multiplayer/WorldClient") + "Ljava/lang/String;)V", (var0, var1x) -> {
            long var2x = d ^ 0x7C4EC6B98DC2L;
            boolean var4x = TransformerBase.u(var1x, Type.VOID_TYPE, false, false, (var0x, var1xx) -> BytecodeHelper.Y(var0x, R, "Minecraft$onLoadWorld", "()V"));
            return var4x | BytecodeHelper.R(var1x, BytecodeHelper.W("java/lang/System", "gc", "()V"), (var0x, var1xx) -> BytecodeHelper.Y(var0x, R, "Minecraft$onOptimizeWorldSwapping", "()V"));
        }, "loadWorld", "loadWorld");
}
    private boolean A(MethodNode var1) {
        boolean var4 = false;
        MethodInsnMatcher var5 = BytecodeHelper.s("net/minecraft/client/multiplayer/PlayerControllerMP", "(" + SrgNames.X("net/minecraft/entity/player/EntityPlayer") + ")V", "onStoppedUsingItem", "onStoppedUsingItem");
        MethodInsnMatcher var6 = BytecodeHelper.s("net/minecraft/client/multiplayer/PlayerControllerMP", "(" + SrgNames.X("net/minecraft/client/entity/EntityPlayerSP") + ")V", "onStoppedUsingItem", "onStoppedUsingItem");
        for (AbstractInsnNode var7 = var1.instructions.getFirst(); var7 != null; var7 = var7.getNext()) {
            MethodInsnNode var8;
            if (!(var7 instanceof MethodInsnNode) || !var5.A(var8 = (MethodInsnNode)var7) && !var6.A(var8)) continue;
            LabelNode var9 = new LabelNode();
            LabelNode var10 = new LabelNode();
            InsnList var11 = new InsnList();
            var11.add((AbstractInsnNode)new InsnNode(92));
            BytecodeHelper.Y(var11, v, "minecraftShouldCancelStoppedUsingItem", "(" + SrgNames.X("net/minecraft/client/multiplayer/PlayerControllerMP") + SrgNames.X("net/minecraft/entity/player/EntityPlayer") + ")Z");
            var11.add((AbstractInsnNode)new JumpInsnNode(153, var9));
            var11.add((AbstractInsnNode)new InsnNode(88));
            var11.add((AbstractInsnNode)new JumpInsnNode(167, var10));
            var11.add((AbstractInsnNode)var9);
            var1.instructions.insertBefore((AbstractInsnNode)var8, var11);
            var1.instructions.insert((AbstractInsnNode)var8, (AbstractInsnNode)var10);
            var4 = true;
}
        return var4;
}
}