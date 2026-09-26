/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.AbyssClient;
import Abyss.command.AbyssCommands;
import Abyss.event.EventBus;
import Abyss.internal.BrokenBlockTracker;
import Abyss.internal.CheaterDetector;
import Abyss.internal.ChatInputHandler;
import Abyss.internal.MiningEngine;
import Abyss.internal.MiningRenderSubscriber;
import Abyss.internal.auth.SessionAccessor;
import Abyss.internal.auth.TrustAllSslContext;
import Abyss.internal.jnic.StockCommandRegistry;
import Abyss.internal.restore.AbyssAzPump;
import Abyss.internal.restore.AbyssClickGui;
import Abyss.internal.restore.AbyssConfig;
import Abyss.internal.restore.AbyssCtorCache;
import Abyss.internal.restore.AbyssGuiData;
import Abyss.internal.restore.AbyssModuleRegistry;
import Abyss.internal.restore.AbyssModuleSettings;
import Abyss.internal.restore.AbyssSettingStatics;
import Abyss.internal.restore.AbyssTruthNames;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.CustomCape;
import Abyss.module.impl.visual.KeyStrokes;
import Abyss.setting.Setting;
import Abyss.ui.ModuleTagRenderer;
import Abyss.ui.screen.MainMenuTheme;
import Abyss.ui.swing.ConfigManagerWindow;
import Abyss.util.AttackTracker;
import Abyss.util.AutoToolService;
import Abyss.util.BuildInfo;
import Abyss.util.HypixelGameState;
import Abyss.util.KeyBindUtil;
import Abyss.util.RotationManager;
import Abyss.util.debug.StallWatchdog;
import Abyss.util.packet.IncomingPacketHold;
import Abyss.util.packet.PacketManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public final class AbyssBootstrap {
    public static final long REGISTRATION_CARRIER = 0L;
    public static final List<String> SUBSCRIBED = new ArrayList<String>();
    public static final List<String> PENDING = new ArrayList<String>();

    private AbyssBootstrap() {
}
    public static void forceEnableCommandLine() {
        try {
            Module cl = ModuleManager.byName("CommandLine");
            if (cl != null && !cl.o()) {
                cl.I(0L, true);
}
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public static void initClient() {
        if (AbyssClient.w != null) {
            return;
}
        SUBSCRIBED.clear();
        PENDING.clear();
        EventBus var2 = new EventBus();
        if (ModuleManager.S == null) {
            ModuleManager.S = new ArrayList<Module>();
}
        if (ModuleManager.o == null) {
            ModuleManager.o = new HashMap();
}
        if (ConfigManagerWindow.D == null) {
            ConfigManagerWindow.D = new ArrayList<String>();
}
        var2.beginBatch();
        AbyssBootstrap.sub(var2, "Abyss.internal.MiningEngine", MiningEngine.uq);
        AbyssBootstrap.sub(var2, "Abyss.util.AutoToolService", AutoToolService.K);
        AbyssBootstrap.sub(var2, "Abyss.internal.BrokenBlockTracker", BrokenBlockTracker.m);
        AbyssBootstrap.sub(var2, "Abyss.internal.MiningRenderSubscriber", new MiningRenderSubscriber());
        AbyssBootstrap.sub(var2, "Abyss.util.packet.IncomingPacketHold", new IncomingPacketHold());
        AbyssBootstrap.sub(var2, "Abyss.util.AttackTracker", new AttackTracker());
        AbyssBootstrap.sub(var2, "Abyss.ui.ModuleTagRenderer", new ModuleTagRenderer());
        AbyssBootstrap.sub(var2, "Abyss.util.RotationManager", new RotationManager());
        AbyssBootstrap.sub(var2, "Abyss.util.HypixelGameState", new HypixelGameState());
        AbyssBootstrap.sub(var2, "Abyss.util.packet.PacketManager", new PacketManager());
        AbyssModuleRegistry.publish();
        AbyssAzPump.install(var2, 0L, SUBSCRIBED, PENDING);
        PENDING.addAll(AbyssModuleRegistry.PENDING);
        AbyssBootstrap.sub(var2, "Abyss.internal.ChatInputHandler", new ChatInputHandler(0L));
        AbyssBootstrap.sub(var2, "Abyss.ui.screen.MainMenuTheme", new MainMenuTheme(0L));
        AbyssSettingStatics.apply(PENDING);
        AbyssModuleSettings.apply(PENDING);
        AbyssTruthNames.apply(PENDING);
        AbyssBootstrap.runOrphanedStaticInit();
        AbyssClickGui.install(PENDING);
        AbyssCommands.install(PENDING);
        AbyssBootstrap.subscribeAlways(var2, "Abyss.module.impl.misc.Timer", "Timer");
        if (AbyssModuleRegistry.PLAIN_LISTENER instanceof CheaterDetector) {
            var2.s(AbyssModuleRegistry.PLAIN_LISTENER, 0L);
            SUBSCRIBED.add("Abyss.internal.CheaterDetector (internal service; held out of ModuleManager.S)");
} else {
            PENDING.add("Abyss.internal.CheaterDetector internal listener missing from ModuleManager.o");
}
        var2.endBatch();
        AbyssClient.w = var2;
        AbyssConfig.apply(PENDING);
        AbyssBootstrap.forceEnableCommandLine();
        PENDING.add("Abyss.config boot snapshot = " + AbyssConfig.snapshotBoot() + " setting value(s); a later save preserves the file's value for any of them the load did not actually apply, instead of overwriting it");
        StallWatchdog.start();
        AbyssBootstrap.startBackgroundWarmup();
        AbyssBootstrap.diag$dump();
}
    private static void startBackgroundWarmup() {
        Thread warm = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
}
                catch (InterruptedException ignored) {
                    return;
}
                try {
                    TrustAllSslContext.j();
}
                catch (Throwable throwable) {
                    // empty catch block
}
                try {
                    Class.forName("Abyss.util.ChatFormatting", true, AbyssBootstrap.class.getClassLoader());
}
                catch (Throwable throwable) {
                    // empty catch block
}
}
        }, "Abyss-BgWarmup");
        warm.setDaemon(true);
        warm.setPriority(1);
        warm.start();
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String census() {
        StringBuilder t2 = new StringBuilder();
        TreeMap<String, Integer> perCat = new TreeMap<String, Integer>();
        int named = 0;
        for (int i = 0; i < ModuleManager.S.size(); ++i) {
            Integer p;
            int ns;
            String c;
            String n2;
            Module m2 = ModuleManager.S.get(i);
            if (m2 == null) continue;
            try {
                n2 = m2.b();
}
            catch (Throwable x) {
                n2 = "<name threw>";
}
            try {
                c = m2.f() == null ? "<null>" : m2.f().name();
}
            catch (Throwable x) {
                c = "<cat threw>";
}
            try {
                ns = m2.w() == null ? -1 : m2.w().size();
}
            catch (Throwable x) {
                ns = -1;
}
            if (n2 != null && n2.length() > 2 && Character.isUpperCase(n2.charAt(0))) {
                ++named;
}
            perCat.put(c, (p = (Integer)perCat.get(c)) == null ? 1 : p + 1);
            t2.append(m2.getClass().getName()).append('\t').append(n2).append('\t').append(c).append('\t').append(ns).append('\n');
}
        try {
            File f = new File("abyss-census.tsv");
            try (OutputStreamWriter w2 = new OutputStreamWriter((OutputStream)new FileOutputStream(f), "UTF-8");){
                w2.write(t2.toString());
}
}
        catch (Throwable f) {
            // empty catch block
}
        StringBuilder b = new StringBuilder();
        b.append("[ABYSSDIAG] census names       = ").append(named).append(" of ").append(ModuleManager.S.size()).append(" (truth 112)\n");
        b.append("[ABYSSDIAG] census per-cat     = ").append(perCat).append('\n');
        return b.toString();
}
    private static String moduleUsability() {
        int toggleable = 0;
        int stockDisabled = 0;
        int invalid = 0;
        int nullSettings = 0;
        List<String> stockDisabledNames = new ArrayList<String>();
        if (ModuleManager.S != null) {
            for (Module m2 : ModuleManager.S) {
                if (m2 == null) {
                    ++invalid;
                    continue;
}
                try {
                    String name = m2.b();
                    Category category = m2.f();
                    List<Setting> settings = m2.w();
                    if (name == null || name.trim().isEmpty() || category == null || settings == null) {
                        ++invalid;
                        continue;
}
                    for (Setting setting : settings) {
                        if (setting != null) continue;
                        ++nullSettings;
}
                    if (m2.I()) {
                        ++toggleable;
                    } else {
                        ++stockDisabled;
                        stockDisabledNames.add(name);
}
}
                catch (Throwable throwable) {
                    ++invalid;
}
}
}
        return "[ABYSSDIAG] module usability   = toggleable=" + toggleable
                + " stockDisabled=" + stockDisabled
                + " invalid=" + invalid
                + " nullSettings=" + nullSettings
                + " stockDisabledNames=" + stockDisabledNames + "\n";
}
    private static void diag$dump() {
        try {
            StringBuilder b = new StringBuilder("\n[ABYSSDIAG] ==== bootstrap outcome ====\n");
            b.append("[ABYSSDIAG] AZ.w             = ").append(AbyssClient.w == null ? "null" : "live").append('\n');
            b.append("[ABYSSDIAG] subscribed       = ").append(SUBSCRIBED.size()).append(' ').append(SUBSCRIBED).append('\n');
            b.append("[ABYSSDIAG] pending          = ").append(PENDING.size()).append('\n');
            b.append("[ABYSSDIAG] tD.S (list)      = ").append(ModuleManager.S == null ? "null" : String.valueOf(ModuleManager.S.size())).append('\n');
            b.append("[ABYSSDIAG] tD.o (byClass)   = ").append(ModuleManager.o == null ? "null" : String.valueOf(ModuleManager.o.size())).append('\n');
            b.append("[ABYSSDIAG] zu_3.B VESTIGE   = ").append(ClickGUI.B == null ? "null" : "live").append('\n');
            b.append("[ABYSSDIAG] zu_3.Y STUDIO    = ").append(ClickGUI.Y == null ? "null" : "live").append('\n');
            b.append("[ABYSSDIAG] zu_3.F RAVEN     = ").append(ClickGUI.F == null ? "null" : "live").append('\n');
            b.append("[ABYSSDIAG] zu_3 open bind   = ").append(Modules.J(ClickGUI.class) == null ? "null" : KeyBindUtil.p(0L, '\u0000', Modules.J(ClickGUI.class).h())).append('\n');
            b.append("[ABYSSDIAG] t6.L (commands)  = ").append(StockCommandRegistry.L == null ? "null" : String.valueOf(StockCommandRegistry.L.size())).append('\n');
            b.append("[ABYSSDIAG] config writable   = ").append(AbyssModuleRegistry.writableNote()).append('\n');
            b.append("[ABYSSDIAG] eventbus selftest  = ").append(EventBus.selfTest()).append('\n');
            b.append("[ABYSSDIAG] module selftest    = ").append(Module.selfTest()).append('\n');
            b.append("[ABYSSDIAG] config selftest    = ").append(Boolean.getBoolean("abyss.runtimeSelfTest") ? AbyssConfig.selfTest() : "SKIPPED").append('\n');
            b.append("[ABYSSDIAG] session selftest   = ").append(Boolean.getBoolean("abyss.runtimeSelfTest") ? SessionAccessor.selfTest() : "SKIPPED").append('\n');
            b.append("[ABYSSDIAG] command data load = ").append(AbyssCommandData.lastLoadNote).append('\n');
            b.append("[ABYSSDIAG] command selftest  = ").append(AbyssCommands.selfTest()).append('\n');
            b.append("[ABYSSDIAG] clickgui selftest = ").append(ClickGUI.selfTest()).append('\n');
            b.append("[ABYSSDIAG] ctorcache        = built ").append(AbyssCtorCache.built).append(" failed ").append(AbyssCtorCache.failed).append('\n');
            b.append("[ABYSSDIAG] module count     = ").append(ModuleManager.S == null ? -1 : ModuleManager.S.size()).append(" of ").append(AbyssModuleRegistry.expectedModuleCount()).append(' ').append(AbyssModuleRegistry.countGateGreen ? "OK" : "REGRESSION").append(AbyssModuleRegistry.MISSING.isEmpty() ? "" : " missing " + AbyssModuleRegistry.MISSING).append('\n');
            for (String c : AbyssCtorCache.LOG) {
                b.append("[ABYSSDIAG]   ctorcache> ").append(c).append('\n');
}
            if (ModuleManager.S != null) {
                int on = 0;
                StringBuilder names = new StringBuilder();
                for (int i = 0; i < ModuleManager.S.size(); ++i) {
                    Module m2 = ModuleManager.S.get(i);
                    if (m2 == null) continue;
                    if (m2.o()) {
                        ++on;
}
                    if (i >= 12) continue;
                    names.append(i == 0 ? "" : ", ").append(m2.b()).append(m2.o() ? "*" : "");
}
                b.append("[ABYSSDIAG] modules enabled   = ").append(on).append('\n');
                b.append("[ABYSSDIAG] first 12          = ").append((CharSequence)names).append('\n');
                b.append(AbyssBootstrap.census());
                b.append(AbyssBootstrap.moduleUsability());
}
            for (String p : PENDING) {
                if (!p.contains("ClickGui") && !p.contains("Ts_2") && !p.contains("Ad_2") && !p.contains("REFUSED") && !p.contains("threw")) continue;
                b.append("[ABYSSDIAG]   pending> ").append(p).append('\n');
}
            for (String d : AbyssClickGui.degraded()) {
                b.append("[ABYSSDIAG]   degraded> ").append(d).append('\n');
}
            b.append("[ABYSSDIAG] ==== end ====");
            String report = b.toString();
            System.out.println(report);
            StringBuilder full = new StringBuilder(report);
            full.append('\n').append("[ABYSSDIAG] ==== full pending ====\n");
            for (String pending : PENDING) {
                full.append("[ABYSSDIAG] pending-all> ").append(pending).append('\n');
}
            full.append("[ABYSSDIAG] ==== full pending end ====\n");
            try (OutputStreamWriter diag = new OutputStreamWriter((OutputStream)new FileOutputStream(new File("abyss-bootstrap-diagnostics.txt")), "UTF-8");){
                diag.write(full.toString());
}
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    private static void subscribeAlways(EventBus var0, String var1, String var2) {
        Module var3 = null;
        if (ModuleManager.S != null) {
            for (Module var5 : ModuleManager.S) {
                if (!var2.equalsIgnoreCase(var5.b())) continue;
                var3 = var5;
                break;
}
}
        if (var3 == null) {
            PENDING.add(var1 + " not in ModuleManager.S; its listener stays unregistered");
            return;
}
        var0.s(var3, 0L);
        SUBSCRIBED.add(var1);
}
    private static void sub(EventBus var0, String var1, Object var2) {
        if (var2 == null) {
            throw new IllegalStateException("AbyssBootstrap: null listener " + var1);
}
        var0.s(var2, 0L);
        SUBSCRIBED.add(var1);
}
    private static void runOrphanedStaticInit() {
        try {
            KeyStrokes.T();
            SUBSCRIBED.add("za_4.T (KeyStrokes last-press map seeded)");
}
        catch (Throwable var0) {
            PENDING.add("za_4.T threw: " + String.valueOf(var0));
}
        try {
            String[] capes = new String[]{"VALENTINE", "valentine", "MJ_STUDIOS", "mojang_studios", "SCROLLS", "scrolls", "2012", "2012", "2011", "2011", "OXEYE", "oxeye", "MJ_CLASSIC", "mojang_classic", "MOJIRA", "mojira", "SPADE", "spade", "2016", "2016", "2015", "2015", "SNOWMAN", "snowman", "2013", "2013", "PRISMARINE", "prismarine", "TURTLE", "turtle", "REALMS", "realms", "MJ", "mojang", "TRANSLATOR", "translator", "BIRTHDAY", "birthday", "MILLIONTH", "millionth", "DB", "db", "COBALT", "cobalt", "SIZE_M", "size-m"};
            if (CustomCape.O != null) {
                int i = 0;
                while (i + 1 < capes.length) {
                    CustomCape.O.put(capes[i], capes[i + 1]);
                    i += 2;
}
                SUBSCRIBED.add("zH_3.O seeded with " + CustomCape.O.size() + " cape(s)");
            } else {
                PENDING.add("zH_3.O is null -- CustomCape cannot resolve any texture");
}
}
        catch (Throwable var1) {
            PENDING.add("zH_3.O seeding threw: " + String.valueOf(var1));
}
        try {
            String user = AbyssGuiData.sessionName();
            if (user != null && user.length() > 0) {
                BuildInfo.W = user;
                SUBSCRIBED.add("yg_2.W set to the Minecraft session name");
            } else {
                PENDING.add("yg_2.W left empty -- no Minecraft session name available");
}
}
        catch (Throwable var2) {
            PENDING.add("yg_2.W seeding threw: " + String.valueOf(var2));
}
}
    private static void startSessionValidation() {
}
}