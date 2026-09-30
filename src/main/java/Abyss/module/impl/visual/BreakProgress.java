/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.BlockPos
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BreakProgressBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.PlayerControllerStateAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.util.LunarClientDetector;
import Abyss.util.render.CustomFont;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class BreakProgress
extends Module
implements EventSubscriber {
    private BlockPos p;
    private String g;
    private double B;

    public void onRender3D(Render3DEvent var1) {
        if (this.B != 0.0 && this.p != null) {
            double var10 = (double)this.p.getX() + 0.5 - BreakProgress.f.getRenderManager().viewerPosX;
            double var12 = (double)this.p.getY() + 0.5 - BreakProgress.f.getRenderManager().viewerPosY;
            double var14 = (double)this.p.getZ() + 0.5 - BreakProgress.f.getRenderManager().viewerPosZ;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)((float)var10), (float)((float)var12), (float)((float)var14));
            GlStateManager.rotate((float)(-BreakProgress.f.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)BreakProgress.f.getRenderManager().playerViewX, (float)(LunarClientDetector.q(0L) ? 1.0f : this.g()), (float)0.0f, (float)0.0f);
            GlStateManager.scale((float)-0.02266667f, (float)-0.02266667f, (float)-0.02266667f);
            GlStateManager.depthMask((boolean)false);
            GlStateManager.disableDepth();
            CustomFont var16 = Font.s(0L);
            var16.v(this.g, -BreakProgress.f.fontRendererObj.getStringWidth(this.g) / 2, -3.0f, -1, 88827598794260L, true);
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
}
}
    @Override
    public void A(long var1) {
        this.B();
}
    public BreakProgress(long var1) {
        super(0x81DF6475C99L ^ var1 ^ 0x57AE38EB1A9AL);
        this.declare("BreakProgress", Category.Visual, "Display the current breaking percentage right on the current breaking block", new Setting[0]);
        var1 = 0x81DF6475C99L ^ var1;
}
    private void B() {
        this.B = 0.0;
        this.p = null;
        this.g = "";
}
    private void w$r4() {
        this.g = (int)(100.0 * this.B) + "%";
}
    public void onPostTick(int var1, PostTickEvent var2, short var3, short var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!BreakProgress.f.thePlayer.capabilities.isCreativeMode && BreakProgress.f.thePlayer.capabilities.allowEdit) {
            this.B = PlayerControllerStateAccessor.s(0L, BreakProgress.f.playerController);
            if (this.B == 0.0) {
                this.B();
            } else {
                this.p = PlayerControllerStateAccessor.Z(BreakProgress.f.playerController);
                this.w$r4();
}
        } else {
            this.B();
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        BreakProgressBinder.Z(var3, this);
}
    private float g() {
        return BreakProgress.f.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f;
}
    private static void a() {
}
    static {
        BreakProgress.a();
}
}