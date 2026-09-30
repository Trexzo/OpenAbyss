/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.internal.restore.AbyssRavenGui;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.ui.vestige.VestigeClickGuiScreen;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class AbyssClickGui {
    public static final long CTOR_VAR1 = 135029260739073L;
    public static final long MEASURED_SEED_A = 14637767574010L;
    public static final int EXPECT_D = 20;
    public static final int EXPECT_M = 18;
    public static final int EXPECT_O = 14;
    public static final int EXPECT_Y = 0;
    public static final long TS2_CARRIER = 42173082507915L;
    public static StudioClickGuiScreen STUDIO;
    public static VestigeClickGuiScreen INSTANCE;
    public static final List<String> DEGRADED;
    private static boolean attempted;
    private static final Comparator<Module> DISPLAY_NAME;
    private static List<Module> unsortedS;
    private static int sortDepth;
    public static boolean displaySortClash;
    public static int displaySortWindows;

    private AbyssClickGui() {
}
    public static void beginDisplaySort() {
        if (sortDepth++ > 0) {
            return;
}
        ++displaySortWindows;
        List<Module> cur = ModuleManager.S;
        if (cur == null || cur.size() < 2) {
            unsortedS = null;
            return;
}
        ArrayList<Module> copy = new ArrayList<Module>(cur);
        Collections.sort(copy, DISPLAY_NAME);
        unsortedS = cur;
        ModuleManager.S = copy;
}
    public static void endDisplaySort() {
        if (sortDepth > 0) {
            --sortDepth;
}
        if (sortDepth > 0) {
            return;
}
        List<Module> orig = unsortedS;
        unsortedS = null;
        if (orig == null) {
            return;
}
        if (ModuleManager.S != null && ModuleManager.S != orig && ModuleManager.S.size() != orig.size()) {
            displaySortClash = true;
            return;
}
        ModuleManager.S = orig;
}
    public static VestigeClickGuiScreen install(List<String> pending) {
        AbyssClickGui.beginDisplaySort();
        try {
            VestigeClickGuiScreen vestigeClickGuiScreen = AbyssClickGui.install0(pending);
            return vestigeClickGuiScreen;
}
        finally {
            AbyssClickGui.endDisplaySort();
}
}
    private static VestigeClickGuiScreen install0(List<String> pending) {
        VestigeClickGuiScreen screen;
        if (attempted) {
            return INSTANCE;
}
        attempted = true;
        DEGRADED.clear();
        try {
            Field fa = VestigeClickGuiScreen.class.getDeclaredField("a");
            fa.setAccessible(true);
            long seed = fa.getLong(null);
            if (seed != 14637767574010L) {
                DEGRADED.add("Abyss.ui.vestige.VestigeClickGuiScreen.a moved: measured 14637767574010, runtime " + seed + " -- carrier follows the runtime value");
}
}
        catch (Throwable t2) {
            DEGRADED.add("Abyss.ui.vestige.VestigeClickGuiScreen.a not readable (" + t2.getClass().getName() + "); falling back to the measured seed");
}
        if (ModuleManager.S == null || ModuleManager.S.isEmpty()) {
            AbyssClickGui.note(pending, "Abyss.ui.vestige.VestigeClickGuiScreen built with an EMPTY tD.S -- the category->module map is filled once and never refreshed, so the ClickGUI will show no modules");
}
        AbyssClickGui.clearCategoryCache();
        try {
            screen = new VestigeClickGuiScreen();
}
        catch (Throwable t3) {
            AbyssClickGui.note(pending, "Abyss.ui.vestige.VestigeClickGuiScreen ctor threw " + t3.getClass().getName() + ": " + t3.getMessage());
            return null;
}
        if (screen.D != 20 || screen.M != 18 || screen.O != 14 || screen.Y != 0) {
            AbyssClickGui.note(pending, "Abyss.ui.vestige.VestigeClickGuiScreen REFUSED -- decrypted " + screen.D + "/" + screen.M + "/" + screen.O + "/" + screen.Y + ", expected " + 20 + "/" + 18 + "/" + 14 + "/" + 0);
            return null;
}
        ClickGUI.B = screen;
        INSTANCE = screen;
        AbyssClickGui.installStudio(pending);
        AbyssRavenGui.installRaven(pending);
        DEGRADED.addAll(AbyssRavenGui.degraded());
        AbyssClickGui.note(pending, "Abyss.ui.vestige.VestigeClickGuiScreen published into zu_3.B (D/M/O/Y = " + screen.D + "/" + screen.M + "/" + screen.O + "/" + screen.Y + "); zu_3.Y=" + (ClickGUI.Y == null ? "null" : "Ts_2") + " zu_3.F=" + (ClickGUI.F == null ? "null" : "Ad_2") + " -- see AbyssClickGui.DEGRADED");
        return screen;
}
    private static void installStudio(List<String> pending) {
        try {
            StudioClickGuiScreen studio;
            ClickGUI.Y = studio = new StudioClickGuiScreen(42173082507915L);
            STUDIO = studio;
            AbyssClickGui.note(pending, "Abyss.ui.studio.StudioClickGuiScreen (STUDIO theme) published into zu_3.Y");
}
        catch (Throwable t2) {
            AbyssClickGui.note(pending, "Abyss.ui.studio.StudioClickGuiScreen REFUSED -- ctor threw " + t2.getClass().getName() + ": " + t2.getMessage() + " (a wrong carrier makes java.awt.Color reject a component)");
            DEGRADED.add("Abyss.module.impl.configuration.ClickGUI.Y : Abyss/ui/studio/StudioClickGuiScreen still null -- the STUDIO theme screen, and STUDIO is what the shipped ClickGUI config selects");
}
}
    public static List<String> degraded() {
        return Collections.unmodifiableList(DEGRADED);
}
    private static void clearCategoryCache() {
        try {
            Field fe = VestigeClickGuiScreen.class.getDeclaredField("E");
            fe.setAccessible(true);
            Object m2 = fe.get(null);
            if (m2 instanceof Map) {
                ((Map)m2).clear();
}
}
        catch (Throwable t2) {
            DEGRADED.add("ClickGuiScreen.E not clearable (" + t2.getClass().getName() + "); the category map may be stale");
}
}
    private static void note(List<String> pending, String s) {
        if (pending != null) {
            pending.add(s);
}
}
    static {
        DEGRADED = new ArrayList<String>();
        DISPLAY_NAME = new Comparator<Module>(){

            @Override
            public int compare(Module a, Module b) {
                String x = a == null || a.b() == null ? "" : a.b();
                String y = b == null || b.b() == null ? "" : b.b();
                int c = x.compareToIgnoreCase(y);
                return c != 0 ? c : x.compareTo(y);
}
        };
}
}