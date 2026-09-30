/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.DefaultPlayerSkin
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.StringUtils
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.enums.TargetHudElement;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ClosestPlayerHUDBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.internal.synthetic.ClosestPlayerHUDSwitchMapTargetHudElement;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.visual_utility.ClosestPlayerEntry;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.render.CustomFont;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ClosestPlayerHUD
extends Module
implements EventSubscriber {
    private static long a = 23755569410545L;
    private static final float o = 10.0f;
    public static Color E;
    public static Color M;
    public static BooleanSetting displayArrow;
    private static final float N = 8.0f;
    public static Color K;
    public static NumberSetting scale;
    public static BooleanSetting displayTeamSize;
    private static final float D = 7.0f;
    public static PercentageSetting backgroundOpacity;
    public static NumberSetting offsetY;
    private final List<ClosestPlayerEntry> I;
    public static BooleanSetting displayHealth;
    private static final float L = 1.0f;
    private static final float S = 4.0f;
    public static BooleanSetting displayName;
    public static NumberSetting offsetX;
        private static final float s = 1.0f;
    public static BooleanSetting displayHeight;
    public static Color T;
        private static String[] e;
    public static BooleanSetting displayDistance;
    public static BooleanSetting displayHead;
    private static long[] h;

    private float p(float[] var1, List<TargetHudElement> var2) {
        float var3 = 0.0f;
        for (int var4 = 0; var4 < var2.size(); ++var4) {
            var3 += var1[var2.get(var4).ordinal()];
            if (var4 >= var2.size() - 1) continue;
            var3 += 4.0f;
}
        return var3;
}
    public void onPostTick(byte var1, long var2, PostTickEvent var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = ((long)var1 << 56 | var2 << 8 >>> 8) ^ a;
        int var7 = (int)((var5 ^ 0x20C6BA9D538AL) >>> 56);
        long var8 = (var5 ^ 0x20C6BA9D538AL) << 8 >>> 8;
        this.I.clear();
        this.I.addAll(this.p((byte)var7, var8));
}
    private float[] T(int var1, byte var2, int var3, List var4, CustomFont var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var6 = ((long)var1 << 32 | (long)var2 << 56 >>> 32 | (long)var3 << 40 >>> 40) ^ a;
        long var12 = var6 ^ 0x3977D06F0E5DL;
        float[] var14 = new float[TargetHudElement.values().length];
        for (ClosestPlayerEntry var16 : (Iterable<ClosestPlayerEntry>)(var4)) {
            if (displayHead.c()) {
                var14[TargetHudElement.HEAD.ordinal()] = 8.0f;
}
            if (displayName.c()) {
                var14[TargetHudElement.NAME.ordinal()] = Math.max(var14[TargetHudElement.NAME.ordinal()], var5.R(var16.F + var16.a.getName(), var12));
}
            if (displayHealth.c()) {
                var14[TargetHudElement.HP.ordinal()] = Math.max(var14[TargetHudElement.HP.ordinal()], var5.R(this.getHealth(var16), var12));
}
            if (displayHeight.c()) {
                var14[TargetHudElement.HEIGHT.ordinal()] = Math.max(var14[TargetHudElement.HEIGHT.ordinal()], var5.R(this.N(var16.a), var12));
}
            if (displayArrow.c()) {
                var14[TargetHudElement.ARROW.ordinal()] = 7.0f;
}
            if (displayTeamSize.c()) {
                var14[TargetHudElement.TEAM.ordinal()] = Math.max(var14[TargetHudElement.TEAM.ordinal()], var5.R("\u00a77(" + var16.l + ")", var12));
}
            if (!displayDistance.c()) continue;
            var14[TargetHudElement.DIST.ordinal()] = Math.max(var14[TargetHudElement.DIST.ordinal()], var5.R(var16.F + (int)var16.D + "m", var12));
}
        return var14;
}
    private List<TargetHudElement> T() {
        ArrayList<TargetHudElement> var1 = new ArrayList<TargetHudElement>();
        if (displayHead.c()) {
            var1.add(TargetHudElement.HEAD);
}
        if (displayName.c()) {
            var1.add(TargetHudElement.NAME);
}
        if (displayDistance.c()) {
            var1.add(TargetHudElement.DIST);
}
        if (displayArrow.c()) {
            var1.add(TargetHudElement.ARROW);
}
        if (displayTeamSize.c()) {
            var1.add(TargetHudElement.TEAM);
}
        if (displayHeight.c()) {
            var1.add(TargetHudElement.HEIGHT);
}
        if (displayHealth.c()) {
            var1.add(TargetHudElement.HP);
}
        return var1;
}
    private void J(CustomFont var1, String var2, long var3, float var5, float var6, float var7) {
        float var12 = var1.R(var2, 52019766876817L);
        var1.T(37697014677608L, var2, (int)(var5 + var6 - var12), (int)var7 + 1, -1);
}
    private void ordinal(ClosestPlayerEntry var1, float var2, float var3, float[] var4, long var5, List var7, CustomFont var8) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        float var21 = var2;
        for (int var22 = 0; var22 < var7.size(); ++var22) {
            TargetHudElement var23 = (TargetHudElement)((Object)var7.get(var22));
            float var24 = var4[var23.ordinal()];
            switch (ClosestPlayerHUDSwitchMapTargetHudElement.b[var23.ordinal()]) {
                case 1: {
                    this.L(var1.a, (int)var21, (int)var3 + 1);
                    break;
}
                case 2: {
                    var8.T(37697014677608L, var1.F + var1.a.getName(), (int)var21, (int)var3 + 1, -1);
                    break;
}
                case 3: {
                    this.J(var8, this.getHealth(var1), 49042228730578L, var21, var24, var3);
                    break;
}
                case 4: {
                    this.J(var8, this.N(var1.a), 49042228730578L, var21, var24, var3);
                    break;
}
                case 5: {
                    this.v(0L, var1.a, var21, var24, var3, var1.F);
                    break;
}
                case 6: {
                    this.J(var8, "\u00a77(" + var1.l + ")", 49042228730578L, var21, var24, var3);
                    break;
}
                case 7: {
                    this.J(var8, var1.F + (int)var1.D + "m", 49042228730578L, var21, var24, var3);
}
}
            var21 += var24;
            if (var22 >= var7.size() - 1) continue;
            var21 += 4.0f;
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        ClosestPlayerHUDBinder.k(var3, this);
}
    private void h(EntityPlayer var1, int var2, int var3, String var6) {
        double var7 = var1.posX - ClosestPlayerHUD.f.thePlayer.posX;
        double var9 = var1.posZ - ClosestPlayerHUD.f.thePlayer.posZ;
        double var11 = Math.toDegrees(Math.atan2(var9, var7)) - 90.0;
        float var13 = (float)(var11 - (double)ClosestPlayerHUD.f.thePlayer.rotationYaw);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)((float)var2 + 3.0f), (float)((float)var3 + 4.0f), (float)0.0f);
        GlStateManager.rotate((float)var13, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glLineWidth((float)1.5f);
        Color var14 = this.s(var6);
        GL11.glColor3f((float)((float)var14.getRed() / 255.0f), (float)((float)var14.getGreen() / 255.0f), (float)((float)var14.getBlue() / 255.0f));
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)-2.5f, (float)2.0f);
        GL11.glVertex2f((float)0.0f, (float)-3.5f);
        GL11.glVertex2f((float)2.5f, (float)2.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GlStateManager.popMatrix();
}
    private List p(byte var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (ClosestPlayerHUD.f.thePlayer != null && ClosestPlayerHUD.f.theWorld != null) {
            String[] var8 = new String[]{"c", "a", "e", "9"};
            HashMap var9 = new HashMap();
            for (String string : var8) {
                var9.put(string, new ArrayList());
}
            String var25 = this.Z(0L, (EntityPlayer)ClosestPlayerHUD.f.thePlayer);
            ClosestPlayerEntry var26 = null;
            ArrayList<ClosestPlayerEntry> var27 = new ArrayList<ClosestPlayerEntry>();
            for (EntityPlayer var14 : ClosestPlayerHUD.f.theWorld.playerEntities) {
                String var15;
                if (var14 == null || var14 == ClosestPlayerHUD.f.thePlayer || var14.isInvisible() || var14.isDead || (var15 = this.Z(0L, var14)) == null || !var9.containsKey(var15)) continue;
                ((List)var9.get(var15)).add(var14);
}
            for (String var16 : var8) {
                List var17 = (List)var9.get(var16);
                if (var17.isEmpty()) continue;
                EntityPlayer var18 = null;
                double var19 = Double.MAX_VALUE;
                for (EntityPlayer var22 : (Iterable<EntityPlayer>)(var17)) {
                    double var23 = ClosestPlayerHUD.f.thePlayer.getDistanceSqToEntity((Entity)var22);
                    if (!(var23 < var19)) continue;
                    var19 = var23;
                    var18 = var22;
}
                if (var18 == null) continue;
                ClosestPlayerEntry var33 = new ClosestPlayerEntry(var18, "\u00a7" + var16, Math.sqrt(var19), var17.size());
                if (var16.equals(var25)) {
                    var26 = var33;
                    continue;
}
                var27.add(var33);
}
            var27.sort(Comparator.comparingDouble(var0 -> var0.D));
            ArrayList<ClosestPlayerEntry> arrayList = new ArrayList<ClosestPlayerEntry>();
            if (var26 != null) {
                arrayList.add(var26);
}
            arrayList.addAll(var27);
            return arrayList;
}
        return Collections.emptyList();
}
    private String Z(long var1, EntityPlayer var3) {
        if (var3 == null) {
            return null;
}
        if (!(var3.getTeam() instanceof ScorePlayerTeam)) {
            return null;
}
        ScorePlayerTeam var4 = (ScorePlayerTeam)var3.getTeam();
        String var5 = var4.getColorPrefix();
        if (var5 != null && !var5.isEmpty()) {
            String var6 = StringUtils.stripControlCodes((String)var5);
            if (var6 != null) {
                if (var6.contains("[R]")) {
                    return "c";
}
                if (var6.contains("[G]")) {
                    return "a";
}
                if (var6.contains("[Y]")) {
                    return "e";
}
                if (var6.contains("[B]")) {
                    return "9";
}
}
            for (int var7 = 0; var7 < var5.length() - 1; ++var7) {
                char var8;
                if (var5.charAt(var7) != '\u00a7' || (var8 = Character.toLowerCase(var5.charAt(var7 + 1))) != 'c' && var8 != 'a' && var8 != 'e' && var8 != '9') continue;
                return String.valueOf(var8);
}
            return null;
}
        return null;
}
    private String N(EntityPlayer var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var4 = (int)Math.round(var1.posY - ClosestPlayerHUD.f.thePlayer.posY);
        if (var4 > 0) {
            return "\u00a7a\u25b2" + var4;
}
        return var4 < 0 ? "\u00a7c\u25bc" + -var4 : "\u00a77\u25a0";
}
    @Override
    public void A(long var1) {
        this.I.clear();
}
    public ClosestPlayerHUD(long var1) {
        super(a ^ var1 ^ 0xEE8B252B2A5L);
        this.declare("ClosestPlayerHUD", Category.Visual_utility, "Show the closest Mega Walls player in each team", new Setting[0]);
        var1 = a ^ var1;
        this.I = new ArrayList<ClosestPlayerEntry>();
}
    private void L(EntityPlayer var3, int var4, int var5) {
        if (var3 != null) {
            ResourceLocation var6 = DefaultPlayerSkin.getDefaultSkin((UUID)var3.getUniqueID());
            NetworkPlayerInfo var7 = f.getNetHandler().getPlayerInfo(var3.getUniqueID());
            if (var7 != null) {
                var6 = var7.getLocationSkin();
}
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            f.getTextureManager().bindTexture(var6);
            Gui.drawScaledCustomSizeModalRect((int)var4, (int)var5, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)8, (int)8, (float)64.0f, (float)64.0f);
            Gui.drawScaledCustomSizeModalRect((int)var4, (int)var5, (float)40.0f, (float)8.0f, (int)8, (int)8, (int)8, (int)8, (float)64.0f, (float)64.0f);
}
}
    private void v(long var1, EntityPlayer var3, float var4, float var5, float var6, String var7) {
        float var10 = var4 + (var5 - 7.0f) / 2.0f;
        this.h(var3, (int)var10, (int)var6, var7);
}
    private String getHealth(ClosestPlayerEntry var1) {
        int var4 = (int)Math.ceil(var1.a.getHealth());
        String var5 = var4 <= 8 ? "\u00a7c" : (var4 <= 14 ? "\u00a7e" : "\u00a7a");
        return var5 + var4;
}
    public void onRender2D(Render2DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        List<ClosestPlayerEntry> var11 = this.I;
        if (!var11.isEmpty()) {
            CustomFont var12 = Font.F(0L);
            float var13 = scale.L();
            float var14 = offsetX.L();
            float var15 = offsetY.L();
            List<TargetHudElement> var16 = this.T();
            float[] var17 = this.T(931, (byte)29, 9463101, var11, var12);
            float var18 = this.p(var17, var16);
            float var19 = var18 + 2.0f;
            float var20 = (float)var11.size() * 10.0f + 2.0f;
            GlStateManager.pushMatrix();
            GlStateManager.scale((float)var13, (float)var13, (float)var13);
            if (backgroundOpacity.k() > 0) {
                int var21 = (int)((float)(255 * backgroundOpacity.k()) / 100.0f);
                Gui.drawRect((int)((int)var14), (int)((int)var15), (int)((int)(var14 + var19)), (int)((int)(var15 + var20)), (int)new Color(0, 0, 0, var21).getRGB());
}
            float var25 = var14 + 1.0f;
            float var22 = var15 + 1.0f;
            for (int var23 = 0; var23 < var11.size(); ++var23) {
                this.ordinal(var11.get(var23), var25, var22 + (float)var23 * 10.0f, var17, 46158516820447L, var16, var12);
}
            GlStateManager.popMatrix();
}
}
    private Color s(String var1) {
        if (var1 != null && var1.length() >= 2) {
            switch (var1.charAt(1)) {
                case '9': {
                    return K;
}
                case 'a': {
                    return E;
}
                case 'c': {
                    return T;
}
                case 'e': {
                    return M;
}
}
            return Color.WHITE;
}
        return Color.WHITE;
}
    static {
        K = new Color(85, 85, 255);
        E = new Color(85, 255, 85);
        T = new Color(255, 85, 85);
        M = new Color(255, 255, 85);
        backgroundOpacity = new PercentageSetting("Background-opacity", 40);
        displayHead = new BooleanSetting("Display-head", true);
        displayName = new BooleanSetting("Display-name", false);
        displayDistance = new BooleanSetting("Display-distance", true);
        displayTeamSize = new BooleanSetting("Display-team-size", true);
        displayArrow = new BooleanSetting("Display-arrow", true);
        displayHeight = new BooleanSetting("Display-height", true);
        displayHealth = new BooleanSetting("Display-health", true);
        offsetX = new NumberSetting("Offset-X", 4.0f, 0.0f, 1000.0f, 1.0f);
        offsetY = new NumberSetting("Offset-Y", 180.0f, 0.0f, 1000.0f, 1.0f);
        scale = new NumberSetting("Scale", 0.9f, 0.5f, 3.0f, 0.01f);
}
}