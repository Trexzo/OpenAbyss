/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Opcodes
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.FieldNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package Abyss.ASM;

import Abyss.ASM.ClassTransform;
import Abyss.ASM.Hooks.Block.BlockBarrierHooks;
import Abyss.ASM.Hooks.Block.BlockBushHooks;
import Abyss.ASM.Hooks.Block.BlockGrassHooks;
import Abyss.ASM.Hooks.Block.BlockHooks;
import Abyss.ASM.Hooks.Block.BlockLeavesHooks;
import Abyss.ASM.Hooks.Block.BlockModelRendererHooks;
import Abyss.ASM.Hooks.Block.BlockModelShapesHooks;
import Abyss.ASM.Hooks.Block.BlockRendererDispatcherHooks;
import Abyss.ASM.Hooks.Block.BlockStateMapperHooks;
import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.ASM.Hooks.Entity.EntityHookDispatch;
import Abyss.ASM.Hooks.Entity.EntityRenderStateHooks;
import Abyss.ASM.Hooks.Entity.EntityRendererHooks;
import Abyss.ASM.Hooks.Entity.RenderEntityItemHooks;
import Abyss.ASM.Hooks.Entity.RendererLivingEntityHooks;
import Abyss.ASM.Hooks.Gui.GuiChatHooks;
import Abyss.ASM.Hooks.Gui.GuiContainerHooks;
import Abyss.ASM.Hooks.Gui.GuiIngameHooks;
import Abyss.ASM.Hooks.Gui.GuiPlayerTabOverlayHooks;
import Abyss.ASM.Hooks.Gui.GuiScreenHooks;
import Abyss.ASM.Hooks.Gui.GuiTextFieldHooks;
import Abyss.ASM.Hooks.HookDispatch;
import Abyss.ASM.Hooks.LayerCapeHooks;
import Abyss.ASM.Hooks.MiscHooks;
import Abyss.ASM.Hooks.Network.NetHandlerPlayClientHooks;
import Abyss.ASM.Hooks.Network.NetworkManagerHooks;
import Abyss.ASM.Hooks.Network.NetworkPlayerInfoHooks;
import Abyss.ASM.Hooks.Render.EffectRendererHooks;
import Abyss.ASM.Hooks.Render.ItemRendererHooks;
import Abyss.ASM.Hooks.Render.ModelBipedHooks;
import Abyss.ASM.Hooks.Render.WorldRendererHooks;
import Abyss.ASM.Hooks.VisGraphHooks;
import Abyss.ASM.Hooks.World.WorldClientHooks;
import Abyss.ASM.Util.AsmUtil;
import Abyss.ASM.Util.BytecodeHelper;
import Abyss.ASM.Util.InsnEditor;
import Abyss.ASM.Util.MethodInsnMatcher;
import Abyss.ASM.Util.ReturnSiteEmitter;
import Abyss.util.ClientUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public abstract class TransformerBase
implements ClassTransform,
Opcodes {
    public static String D;
    public static String a;
    public static String b;
    public static String o;
    public static String k;
    public static String h;
    public static String Y;
    public static String r;
    public static String s;
    public static String T;
    public static String j;
    public static String u;
    public static String q;
    public static String A;
    public static String z;
    public static String v;
    public static String S;
    public static String x;
    public static String L;
    public static String e;
    public static String H;
    public static String K;
    private static long c;
    public static String m;
    public static String B;
    public static String f;
    public static String I;
    public static String Q;
    private final String X;
    public static String p;
    public static String R;
    protected static String n;
    public static String C;
    public static String F;
    protected static String N;
    public static String g;
    public static String V;

    public static String e(Class<?> var0) {
        return Type.getInternalName(var0);
}
    public static boolean u(MethodNode var0, Type var1, boolean var2, boolean var3, ReturnSiteEmitter var4) {
        InsnList var7 = new InsnList();
        int var8 = var2 ? BytecodeHelper.S(var0, var7) : BytecodeHelper.O(var0, var7);
        var4.c(var7, var8);
        if (var3) {
            BytecodeHelper.E(var7, var8, var1);
}
        var0.instructions.insert(var7);
        return true;
}
    public static boolean v(MethodNode var0, MethodInsnMatcher var1, int var2, InsnEditor var3) {
        int var6 = 0;
        for (AbstractInsnNode var7 = var0.instructions.getFirst(); var7 != null; var7 = var7.getNext()) {
            if (!(var7 instanceof MethodInsnNode) || !var1.A((MethodInsnNode)var7) || var6++ != var2) continue;
            InsnList var8 = new InsnList();
            var3.D(var8);
            var0.instructions.insertBefore(var7, var8);
            return true;
}
        return false;
}
    public static void Y(MethodNode var0) {
        AbstractInsnNode var3 = var0.instructions.getFirst();
        while (var3 != null) {
            AbstractInsnNode var4 = var3.getNext();
            var0.instructions.remove(var3);
            var3 = var4;
}
        var0.tryCatchBlocks.clear();
        var0.localVariables.clear();
}
    public static boolean Q(MethodNode var0, MethodInsnMatcher var1, int var2, InsnEditor var3) {
        int var6 = 0;
        for (AbstractInsnNode var7 = var0.instructions.getFirst(); var7 != null; var7 = var7.getNext()) {
            if (!(var7 instanceof MethodInsnNode) || !var1.A((MethodInsnNode)var7) || var6++ != var2) continue;
            InsnList var8 = new InsnList();
            var3.D(var8);
            var0.instructions.insert(var7, var8);
            return true;
}
        return false;
}
    public static boolean M(MethodNode var0, Type var1, ReturnSiteEmitter var2) {
        boolean var5 = false;
        for (AbstractInsnNode var6 = var0.instructions.getFirst(); var6 != null; var6 = var6.getNext()) {
            int var9;
            if (var6.getOpcode() != BytecodeHelper.N(var1)) continue;
            InsnList var7 = new InsnList();
            int var8 = -1;
            if (var1.getSort() == 0) {
                var9 = BytecodeHelper.O(var0, var7);
            } else {
                var8 = BytecodeHelper.t(var0, var1);
                var7.add((AbstractInsnNode)new VarInsnNode(BytecodeHelper.D(var1), var8));
                var9 = BytecodeHelper.r(var0, var7, var1, var8);
}
            var2.c(var7, var9);
            if (var1.getSort() != 0) {
                BytecodeHelper.E(var7, var9, var1);
                var7.add((AbstractInsnNode)new VarInsnNode(BytecodeHelper.R(var1), var8));
}
            var0.instructions.insertBefore(var6, var7);
            var5 = true;
}
        return var5;
}
    public abstract boolean s(ClassNode var1);

    public static boolean g(MethodNode var0, String var1, String var2, InsnEditor var3, String ... var4) {
        for (AbstractInsnNode var7 = var0.instructions.getFirst(); var7 != null; var7 = var7.getNext()) {
            FieldInsnNode var8;
            if (!(var7 instanceof FieldInsnNode) || var7.getOpcode() != 181 || !AsmUtil.v(var8 = (FieldInsnNode)var7, var1, var2, var4)) continue;
            InsnList var9 = new InsnList();
            var3.D(var9);
            var0.instructions.insert(var7, var9);
            return true;
}
        return false;
}
    protected TransformerBase(String var1) {
        this.X = var1;
}
    public static String j(ClassNode var0, String var1, String ... var2) {
        for (String var8 : var2) {
            for (Object var10 : var0.fields) {
                FieldNode var11 = (FieldNode)var10;
                if (!AsmUtil.v(new FieldInsnNode(180, var0.name, var11.name, var11.desc), var0.name, var1, var8)) continue;
                return var11.name;
}
}
        return var2[0];
}
    @Override
    public String E() {
        return this.X;
}
    public static boolean K(MethodNode var0, String var1, String var2, String var3, int var4, InsnEditor var5) {
        int var8 = 0;
        for (AbstractInsnNode var9 = var0.instructions.getFirst(); var9 != null; var9 = var9.getNext()) {
            FieldInsnNode var10;
            if (!(var9 instanceof FieldInsnNode) || (var10 = (FieldInsnNode)var9).getOpcode() != 180 || !AsmUtil.v(var10, var1, var3, var2) || var8++ != var4) continue;
            InsnList var11 = new InsnList();
            var5.D(var11);
            var0.instructions.insertBefore(var9, var11);
            return true;
}
        return false;
}
    @Override
    public byte[] S(byte[] var1) throws Throwable {
        return BytecodeHelper.G(var1, this::s);
}
    public static boolean G(ClassNode var0, String var1, String var2, String var3, String var4, String ... var5) {
        return BytecodeHelper.t(var0, var1, (var4x, var5x) -> {
            InsnList var8 = new InsnList();
            BytecodeHelper.Y(var8, var2, var3, var4);
            var8.add((AbstractInsnNode)new InsnNode(BytecodeHelper.N(Type.getReturnType((String)var1))));
            TransformerBase.Y(var5x);
            var5x.instructions.add(var8);
            return true;
        }, var5);
}
    static {
        c = 7055454155035L;
        z = Type.getDescriptor(CallbackInfo.class);
        I = Type.getDescriptor(CallbackInfoReturnable.class);
        v = TransformerBase.e(MiscHooks.class);
        R = TransformerBase.e(HookDispatch.class);
        m = TransformerBase.e(EntityHookDispatch.class);
        H = TransformerBase.e(BlockHooks.class);
        B = TransformerBase.e(BlockBarrierHooks.class);
        a = TransformerBase.e(BlockBushHooks.class);
        F = TransformerBase.e(BlockGrassHooks.class);
        S = TransformerBase.e(BlockLeavesHooks.class);
        K = TransformerBase.e(BlockModelShapesHooks.class);
        L = TransformerBase.e(BlockStateMapperHooks.class);
        k = TransformerBase.e(GuiChatHooks.class);
        o = TransformerBase.e(GuiContainerHooks.class);
        C = TransformerBase.e(GuiIngameHooks.class);
        p = TransformerBase.e(GuiPlayerTabOverlayHooks.class);
        f = TransformerBase.e(GuiScreenHooks.class);
        V = TransformerBase.e(GuiTextFieldHooks.class);
        A = TransformerBase.e(NetHandlerPlayClientHooks.class);
        q = TransformerBase.e(NetworkManagerHooks.class);
        Q = TransformerBase.e(NetworkPlayerInfoHooks.class);
        g = TransformerBase.e(BlockModelRendererHooks.class);
        r = TransformerBase.e(BlockRendererDispatcherHooks.class);
        b = TransformerBase.e(EffectRendererHooks.class);
        s = TransformerBase.e(EntityRendererHooks.class);
        D = TransformerBase.e(ItemRendererHooks.class);
        n = TransformerBase.e(LayerCapeHooks.class);
        j = TransformerBase.e(ModelBipedHooks.class);
        h = TransformerBase.e(EntityRenderStateHooks.class);
        u = TransformerBase.e(RenderEntityItemHooks.class);
        Y = TransformerBase.e(RendererLivingEntityHooks.class);
        N = TransformerBase.e(VisGraphHooks.class);
        T = TransformerBase.e(WorldClientHooks.class);
        e = TransformerBase.e(WorldRendererHooks.class);
        x = TransformerBase.e(ClientUtil.class);
}
}