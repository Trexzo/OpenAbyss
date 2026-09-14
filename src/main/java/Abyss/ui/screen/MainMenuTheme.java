/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSelectWorld
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.client.GuiModList
 *  org.lwjgl.opengl.Display
 */
package Abyss.ui.screen;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.MainMenuThemeBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.ui.screen.DracuRiotMainMenu;
import Abyss.ui.screen.MainMenuStyleScreen;
import Abyss.ui.screen.RiddleJokerMainMenu;
import Abyss.ui.screen.SenrenBankaMainMenu;
import Abyss.util.MinecraftRef;
import Abyss.util.SoundEngine;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.opengl.Display;

public class MainMenuTheme
implements EventSubscriber {
    private static long[] h;
    public static ModeSetting mode;
    public static ResourceLocation v;
    private static String[] f;
    private final String F;
    public static boolean w;
    private String J = mode.Y();
    public static ResourceLocation a;
    private final String b;
    private boolean t = false;
    private static String[] e;
    public static ResourceLocation n;
    private final String A;
    private final String l;
    public static BooleanSetting music;
    private final String T;
    public static ResourceLocation z;
    private static long c;
    private final String E;
    public static ResourceLocation d;
    private static Minecraft I;
    private String C = "Minecraft 1.8.9";

    public static void F(int var0, int var1, long var2, int var4, List var5) {
        long var6 = ((long)var1 << 32 | 0xE2E014FDL) ^ c;
        long var8 = var6 ^ 0x19CB463F3D30L;
        if (!"User".equals("Injection")) {
            switch (mode.Y()) {
                case "SENREN_BANKA": {
                    SenrenBankaMainMenu.O(var0, var4, var5);
                    break;
}
                case "RIDDLE_JOKER": {
                    RiddleJokerMainMenu.L(var4, var5);
                    break;
}
                case "DRACU_RIOT": {
                    DracuRiotMainMenu.u(var0, var4, var8, var5);
}
}
}
}
    public static boolean u(int var0, short var1, short var2) {
        return MainMenuTheme.I.field_71462_r instanceof GuiMainMenu && MainMenuTheme.b();
}
    private static boolean A(GuiScreen var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var3 = var1 ^ 0x124C02239790L;
        if (var0 == null) {
            return false;
}
        if (!(var0 instanceof GuiMainMenu || var0 instanceof MainMenuStyleScreen || var0 instanceof GuiOptions || var0 instanceof GuiSelectWorld || var0 instanceof GuiMultiplayer || var0 instanceof GuiModList)) {
            String var7 = var0.getClass().getName();
            return MainMenuTheme.e(var7) || MainMenuTheme.y(var3, var0);
}
        return true;
}
    public static void r(int var0, int var1, int var2, byte var3, List var4, int var5) {
        long var6 = ((long)var2 << 32 | (long)var3 << 56 >>> 32 | (long)var5 << 40 >>> 40) ^ c;
        long var8 = var6 ^ 0x47D1AD1DF1EAL;
        long var10 = var6 ^ 0x2F8A1E710355L;
        long var12 = var6 ^ 0x455E3136CC2BL;
        if (!"User".equals("Injection")) {
            switch (mode.Y()) {
                case "SENREN_BANKA": {
                    SenrenBankaMainMenu.l(var0, var1, var12, var4);
                    break;
}
                case "RIDDLE_JOKER": {
                    RiddleJokerMainMenu.W(var0, var8, var1, var4);
                    break;
}
                case "DRACU_RIOT": {
                    DracuRiotMainMenu.D(var0, var1, var10, var4);
}
}
}
}
    public static boolean X(long var0) {
        return MainMenuTheme.b();
}
    public void onPostTick(long var1, PostTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!this.t) {
            this.C = Display.getTitle();
            this.t = true;
}
        if (MainMenuTheme.W(34800587899933L)) {
            switch (mode.Y()) {
                case "SENREN_BANKA": {
                    Display.setTitle((String)"\u5343\u604b\uff0a\u4e07\u82b1");
                    break;
}
                case "RIDDLE_JOKER": {
                    Display.setTitle((String)"Riddle Joker");
                    break;
}
                case "DRACU_RIOT": {
                    Display.setTitle((String)"DRACU-RIOT!");
                    break;
}
                default: {
                    Display.setTitle((String)this.C);
                    break;
}
}
        } else {
            Display.setTitle((String)this.C);
}
        if (!mode.R(this.J)) {
            SoundEngine.c(4202516140984L);
            w = false;
            this.J = mode.Y();
}
        if (!MainMenuTheme.W(34800587899933L)) {
            if (w) {
                SoundEngine.G(26533);
}
        } else if (!w) {
            w = true;
            switch (mode.Y()) {
                case "SENREN_BANKA": {
                    SoundEngine.y(16881978558250L, "/assets/minecraft/mainmenu/senrenbanka/sound.ogg");
                    break;
}
                case "RIDDLE_JOKER": {
                    SoundEngine.y(16881978558250L, "/assets/minecraft/mainmenu/riddlejoker/sound.ogg");
                    break;
}
                case "DRACU_RIOT": {
                    SoundEngine.y(16881978558250L, "/assets/minecraft/mainmenu/dracuriot/sound.ogg");
}
}
        } else {
            SoundEngine.e(8447, 3609307507L);
}
        this.J = mode.Y();
}
    private static boolean e(String var0) {
        return var0.equals("net.minecraft.client.gui.GuiControls") || var0.equals("net.minecraft.client.gui.GuiCustomizeSkin") || var0.equals("net.minecraft.client.gui.GuiLanguage") || var0.equals("net.minecraft.client.gui.GuiScreenOptionsSounds") || var0.equals("net.minecraft.client.gui.GuiScreenResourcePacks") || var0.equals("net.minecraft.client.gui.GuiSnooper") || var0.equals("net.minecraft.client.gui.GuiVideoSettings");
}
    private static boolean y(long var0, GuiScreen var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = var0 ^ 0x3F51217EA21L;
        String var7 = var2.getClass().getName();
        if (MainMenuTheme.V(var7, 0L)) {
            return true;
}
        if (!var7.equals("net.minecraft.client.gui.GuiScreenRealmsProxy")) {
            return false;
}
        Object var8 = MainMenuTheme.k(var2, var5);
        return var8 == null || MainMenuTheme.V(var8.getClass().getName(), 0L);
}
    @Override
    public final void x(long var1, EventBus var3) {
        MainMenuThemeBinder.D(var3, this);
}
    private static boolean W(long var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var2 = var0 ^ 0x452EC6029680L;
        return MainMenuTheme.b() && music.c() && MainMenuTheme.I.field_71441_e == null && MainMenuTheme.I.field_71439_g == null && MainMenuTheme.A(MainMenuTheme.I.field_71462_r, var2);
}
    public static void S(int var0, int var1) {
        if (!"User".equals("Injection")) {
            switch (mode.Y()) {
                case "SENREN_BANKA": {
                    SenrenBankaMainMenu.W(var0, var1);
                    break;
}
                case "RIDDLE_JOKER": {
                    RiddleJokerMainMenu.t(var0, var1);
                    break;
}
                case "DRACU_RIOT": {
                    DracuRiotMainMenu.h(var0, var1);
}
}
}
}
    private static boolean b() {
        return !"User".equals("Injection") && !mode.R("NONE");
}
    public void onPreTick(long var1, PreTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (MainMenuTheme.W(42846407720032L)) {
            I.func_147118_V().func_147690_c();
            MinecraftAccessor.S('\u0000', 1751831693, I).func_181557_a();
}
}
    private static Object k(GuiScreen var0, long var1) {
        try {
            Method var3 = var0.getClass().getMethod("func_154321_a", new Class[0]);
            return var3.invoke((Object)var0, new Object[0]);
}
        catch (Throwable var4) {
            return null;
}
}
    public MainMenuTheme(long var1) {
        this.E = "/assets/minecraft/mainmenu/dracuriot/sound.ogg";
        this.l = "/assets/minecraft/mainmenu/senrenbanka/sound.ogg";
        this.F = "/assets/minecraft/mainmenu/riddlejoker/sound.ogg";
        this.b = "DRACU-RIOT!";
        this.A = "\u5343\u604b\uff0a\u4e07\u82b1";
        this.T = "Riddle Joker";
}
    private static boolean V(String var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return !var0.startsWith("net.minecraft.realms.") && !var0.startsWith("com.mojang.realmsclient.") ? false : !var0.contains("Connect") && !var0.contains("Disconnected");
}
    public static boolean o(long var0) {
        return MainMenuTheme.b();
}
    static {
        c = 72823149272515L;
        z = new ResourceLocation("minecraft", "mainmenu/dracuriot/bg.png");
        a = new ResourceLocation("minecraft", "mainmenu/senrenbanka/bg.png");
        d = new ResourceLocation("minecraft", "mainmenu/riddlejoker/bg.png");
        n = new ResourceLocation("minecraft", "mainmenu/senrenbanka/icon.png");
        v = new ResourceLocation("minecraft", "mainmenu/riddlejoker/button.png");
        I = MinecraftRef.c((byte)0, 0L);
        mode = new ModeSetting("Mode", true, "NONE", "DRACU_RIOT", "RIDDLE_JOKER", "SENREN_BANKA", "NONE");
        music = new BooleanSetting("Music", true);
        w = false;
}
}