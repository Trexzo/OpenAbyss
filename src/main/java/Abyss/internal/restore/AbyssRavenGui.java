/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.ui.raven.AbstractRavenSettingRow;
import Abyss.ui.raven.RavenAnimation;
import Abyss.ui.raven.RavenCategoryPanel;
import Abyss.ui.raven.RavenClickGuiScreen;
import Abyss.ui.raven.RavenFramebuffer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class AbyssRavenGui {
    public static final long AD2_SEED_A = 52686871891298L;
    public static final long AI_SEED_B = 7843458566225L;
    public static final long AD2_CTOR_LOW15 = 23731L;
    public static final long AD2_INITMAIN_LOW15 = 7275L;
    public static final int AD2_CTOR_SLOT = 7;
    public static final int ZA2_CTOR_SLOT = 2;
    public static final long ZA2_SEED_LOW15 = 2270L;
    public static final long AI_K_SEED = 63404359328282L;
    public static final long AI_CTOR_CARRIER = 7747707525139L;
    public static final int AI_CTOR_V1 = 1803;
    public static final char AI_CTOR_V3 = '\ue75a';
    public static final int AI_CTOR_V4 = 55315;
    public static final int EXECUTOR_CORE = 8;
    public static final int PANEL_X = 5;
    public static final int PANEL_Y0 = 5;
    public static final int PANEL_DY = 20;
    public static final int EXPECT_WIDTH = 92;
    public static final int EXPECT_HEADER = 13;
    public static final long EXPECT_ANIM_MILLIS = 600L;
    public static final int EXPECT_TITLE_X_OFF = 12;
    public static final int EXPECT_SIGN_X_OFF = 80;
    public static final long DES_PARITY_BITS = 0x101010101010101L;
    public static final long GATE_POISON = 0x200000000L;
    public static RavenClickGuiScreen RAVEN;
    public static final List<String> DEGRADED;
    private static boolean attempted;
    private static ScheduledThreadPoolExecutor EXECUTOR;
    public static int rows;
    public static int settingRows;
    public static String rowTally;

    private static int modulesIn(Category c) {
        if (ModuleManager.S == null) {
            return -1;
}
        int n2 = 0;
        for (int i = 0; i < ModuleManager.S.size(); ++i) {
            Module m2 = ModuleManager.S.get(i);
            if (m2 == null || !c.equals((Object)m2.f())) continue;
            ++n2;
}
        return n2;
}
    private AbyssRavenGui() {
}
    public static void installPanels() {
        if (RavenClickGuiScreen.A == null) {
            EXECUTOR = new ScheduledThreadPoolExecutor(8, new RavenThreads());
            RavenClickGuiScreen.A = EXECUTOR;
}
        HashMap<Category, RavenCategoryPanel> panels = new HashMap<Category, RavenCategoryPanel>();
        ArrayList<Category> order = new ArrayList<Category>();
        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; ++i) {
            RavenCategoryPanel panel;
            int y = 5 + 20 * i;
            try {
                panel = new RavenCategoryPanel(1803, cats[i], '\ue75a', 55315);
                panel.k(y);
}
            catch (Throwable t2) {
                panel = new RavenCategoryPanel(cats[i], y);
                DEGRADED.add("Abyss/ui/raven/RavenCategoryPanel(" + (Object)((Object)cats[i]) + ") fell back to the row-less constructor: the ZKM one threw " + String.valueOf(t2) + " -- that panel has 0 module rows");
}
            panels.put(cats[i], panel);
            order.add(cats[i]);
}
        RavenClickGuiScreen.P = panels;
        RavenClickGuiScreen.h = order;
}
    public static RavenClickGuiScreen installRaven(List<String> pending) {
        RavenClickGuiScreen screen;
        long za2d;
        long aib;
        long ad2a;
        if (attempted) {
            return RAVEN;
}
        attempted = true;
        if ("0".equals(System.getProperty("abyss.raven")) || "0".equals(System.getenv("ABYSS_RAVEN"))) {
            return AbyssRavenGui.refuse(pending, "disabled by abyss.raven=0 / ABYSS_RAVEN=0 (attribution run)");
}
        try {
            ad2a = AbyssRavenGui.readLong(RavenClickGuiScreen.class, "a");
            aib = AbyssRavenGui.readLong(RavenCategoryPanel.class, "b");
            za2d = AbyssRavenGui.readLong(RavenFramebuffer.class, "d");
}
        catch (Throwable t2) {
            return AbyssRavenGui.refuse(pending, "cannot read the ZKM class seeds (" + t2 + ')');
}
        if (ad2a != 52686871891298L) {
            return AbyssRavenGui.refuse(pending, "Abyss/Ad_2.a is " + ad2a + ", the derivation used " + 52686871891298L + " -- every slot index below would be wrong");
}
        if (aib != 7843458566225L) {
            return AbyssRavenGui.refuse(pending, "Abyss/AI.b is " + aib + ", the derivation used " + 7843458566225L);
}
        if ((za2d & 0x7FFFL) != 2270L) {
            return AbyssRavenGui.refuse(pending, "Abyss/ui/raven/RavenFramebuffer.d & 32767 is " + (za2d & 0x7FFFL) + ", solved " + 2270L);
}
        long seedT = 75567915170629L;
        long chainT = 0x2DD18536B3D6CDBCL ^ seedT;
        long chainTitle = 0x505BA14874A43135L ^ seedT;
        long chainSign = 0x70D8F8E0C49E01B9L ^ seedT;
        if (AbyssRavenGui.numericLayerInlined()) {
            AbyssRavenGui.note(pending, "Abyss/ui/raven/RavenCategoryPanel numeric layer is inlined (width/titleX/signX are literals); gate 2 has nothing to decrypt");
        } else {
            Integer[] cache = AbyssRavenGui.aiCache();
            if (cache == null || cache.length != 21) {
                return AbyssRavenGui.refuse(pending, "Abyss/AI.f is " + (cache == null ? "null" : "length " + cache.length) + ", expected 21");
}
            long poison = chainT ^ 0x200000000L;
            int slotW = 0xEF1 ^ (int)(chainT & 0x7FFFL) ^ 0x3818;
            cache[slotW] = null;
            Integer poisoned = AbyssRavenGui.aiConst(3825, poison);
            cache[slotW] = null;
            if (poisoned != null && poisoned == 92) {
                return AbyssRavenGui.refuse(pending, "gate 2 is dead: a corrupted chain on the same table cell still decrypted to 92");
}
            Integer width = AbyssRavenGui.aiConst(3825, chainT);
            Integer titleX = AbyssRavenGui.aiConst(29295, chainTitle);
            Integer signX = AbyssRavenGui.aiConst(17129, chainSign);
            if (width == null || width != 92 || titleX == null || titleX != 12 || signX == null || signX != 80) {
                return AbyssRavenGui.refuse(pending, "Abyss/ui/raven/RavenCategoryPanel numeric layer disagrees with the 2.4.6 dump: width=" + width + " titleX=" + titleX + " signX=" + signX + ", expected " + 92 + '/' + 12 + '/' + 80 + " (negative control returned " + poisoned + ')');
}
}
        if (RavenCategoryPanel.ravenAnimationMillis() != 600L) {
            return AbyssRavenGui.refuse(pending, "Abyss/AI.i is " + RavenCategoryPanel.ravenAnimationMillis() + ", the 2.4.6 dump recorded " + 600L);
}
        if (!AbyssRavenGui.seed(pending, RavenClickGuiScreen.class, "c", 14, 7) || !AbyssRavenGui.seed(pending, RavenFramebuffer.class, "k", 12, 2)) {
            return null;
}
        try {
            screen = new RavenClickGuiScreen(52686871908817L);
}
        catch (Throwable t3) {
            return AbyssRavenGui.refuse(pending, "Abyss/ui/raven/RavenClickGuiScreen constructor threw " + String.valueOf(t3));
}
        if (RavenClickGuiScreen.P == null || RavenClickGuiScreen.P.size() != 9 || RavenClickGuiScreen.h == null || RavenClickGuiScreen.h.size() != 9) {
            return AbyssRavenGui.refuse(pending, "N.P replacement published " + AbyssRavenGui.size(RavenClickGuiScreen.P) + " panels and " + AbyssRavenGui.size(RavenClickGuiScreen.h) + " draw-order entries, expected 9 and 9");
}
        if (RavenClickGuiScreen.A == null) {
            return AbyssRavenGui.refuse(pending, "Abyss/Ad_2.A is still null; Ad_2.P(J)V would NPE on open");
}
        if (screen.Z == null) {
            return AbyssRavenGui.refuse(pending, "Abyss/Ad_2.Z (the Za_2 blur) is null");
}
        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; ++i) {
            RavenCategoryPanel p = RavenClickGuiScreen.P.get((Object)cats[i]);
            if (p == null) {
                return AbyssRavenGui.refuse(pending, "no RAVEN panel for " + (Object)((Object)cats[i]));
}
            if (p.ravenWidth() != 92 || p.ravenHeader() != 13 || p.X() != 5 || p.T() != 5 + 20 * i) {
                return AbyssRavenGui.refuse(pending, "RAVEN panel " + (Object)((Object)cats[i]) + " is " + p.X() + ',' + p.T() + ' ' + p.ravenWidth() + 'x' + p.ravenHeader() + ", the 2.4.6 dump had " + 5 + ',' + (5 + 20 * i) + ' ' + 92 + 'x' + 13);
}
            if (cats[i] == RavenClickGuiScreen.h.get(i)) continue;
            return AbyssRavenGui.refuse(pending, "draw order slot " + i + " is " + (Object)((Object)RavenClickGuiScreen.h.get(i)) + ", the 2.4.6 dump had declaration order");
}
        rows = 0;
        settingRows = 0;
        StringBuilder tally = new StringBuilder();
        for (int i = 0; i < cats.length; ++i) {
            RavenCategoryPanel p = RavenClickGuiScreen.P.get((Object)cats[i]);
            int got = p.s() == null ? -1 : p.s().size();
            int expect = AbyssRavenGui.modulesIn(cats[i]);
            for (int r2 = 0; got > 0 && r2 < got; ++r2) {
                ArrayList<AbstractRavenSettingRow> sub = p.s().get((int)r2).H;
                settingRows += sub == null ? 0 : sub.size();
}
            rows += got < 0 ? 0 : got;
            tally.append(i == 0 ? "" : " ").append((Object)cats[i]).append('=').append(got);
            if (got == expect) continue;
            return AbyssRavenGui.refuse(pending, "RAVEN panel " + (Object)((Object)cats[i]) + " built " + got + " module row(s), tD.S holds " + expect + " module(s) in that category");
}
        if (rows == 0) {
            return AbyssRavenGui.refuse(pending, "every RAVEN panel came up with 0 module rows even though tD.S holds " + (ModuleManager.S == null ? -1 : ModuleManager.S.size()) + " module(s)");
}
        rowTally = tally.toString();
        int y = AbyssRavenGui.readInt((Object)screen, "y");
        if (y != 0) {
            return AbyssRavenGui.refuse(pending, "Abyss/Ad_2.y came out " + y + ", not 0 -- the pre-seeded slot " + 7 + " was not the one the constructor read");
}
        ClickGUI.F = screen;
        RAVEN = screen;
        AbyssRavenGui.note(pending, "Abyss.ui.raven.RavenClickGuiScreen (RAVEN theme) published into zu_3.F: 9 panels at x=5 y=5+20*ordinal, 92x13, " + rows + " module rows (" + rowTally + "), " + settingRows + " setting rows");
        return screen;
}
    public static boolean primeFirstFrame(RavenClickGuiScreen screen) {
        if (screen == null) {
            return false;
}
        try {
            screen.w = new RavenAnimation(500.0f);
            screen.w.y();
            return true;
}
        catch (Throwable t2) {
            return false;
}
}
    public static void shutdownExecutor() {
        ScheduledThreadPoolExecutor e = EXECUTOR;
        if (e != null) {
            e.shutdownNow();
}
}
    public static List<String> degraded() {
        return DEGRADED;
}
    private static Integer[] aiCache() {
        try {
            Field f = RavenCategoryPanel.class.getDeclaredField("f");
            f.setAccessible(true);
            return (Integer[])f.get(null);
}
        catch (Throwable t2) {
            return null;
}
}
    private static boolean numericLayerInlined() {
        return AbyssRavenGui.numericLayerInlined(RavenCategoryPanel.class);
}
    private static boolean numericLayerInlined(Class<?> cls) {
        try {
            cls.getDeclaredMethod("a", Integer.TYPE, Long.TYPE);
            return false;
}
        catch (NoSuchMethodException gone) {
            return true;
}
        catch (Throwable t2) {
            return false;
}
}
    private static Integer aiConst(int n2, long chain) {
        int slot = n2 ^ (int)(chain & 0x7FFFL) ^ 0x3818;
        if (slot < 0 || slot >= 21) {
            return null;
}
        try {
            Method m2 = RavenCategoryPanel.class.getDeclaredMethod("a", Integer.TYPE, Long.TYPE);
            m2.setAccessible(true);
            return (Integer)m2.invoke(null, n2, chain);
}
        catch (Throwable t2) {
            return null;
}
}
    private static boolean seed(List<String> pending, Class<?> cls, String name, int len, int slot) {
        if (AbyssRavenGui.numericLayerInlined(cls)) {
            AbyssRavenGui.note(pending, cls.getName() + '.' + name + " is a vestigial ZKM ctor-slot cache: the class has no numeric decryptor left, so there is no slot to reserve");
            return true;
}
        try {
            Field f = cls.getDeclaredField(name);
            f.setAccessible(true);
            Integer[] tab = (Integer[])f.get(null);
            if (tab == null) {
                AbyssRavenGui.refuse(pending, cls.getName() + '.' + name + " is null; the class did not run its ZKM clinit");
                return false;
}
            if (tab.length != len) {
                AbyssRavenGui.refuse(pending, cls.getName() + '.' + name + " has length " + tab.length + ", the slot bijection assumed " + len);
                return false;
}
            if (tab[slot] != null && tab[slot] != 0) {
                AbyssRavenGui.refuse(pending, cls.getName() + '.' + name + '[' + slot + "] is already " + tab[slot] + " -- some in-jar site reads this slot, so the bijection is wrong and seeding it would corrupt that site");
                return false;
}
            tab[slot] = 0;
            return true;
}
        catch (Throwable t2) {
            AbyssRavenGui.refuse(pending, "cannot seed " + cls.getName() + '.' + name + '[' + slot + "] (" + t2 + ')');
            return false;
}
}
    private static long readLong(Class<?> cls, String name) throws Exception {
        Field f = cls.getDeclaredField(name);
        f.setAccessible(true);
        return f.getLong(null);
}
    private static int readInt(Object o2, String name) {
        try {
            Field f = o2.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.getInt(o2);
}
        catch (Throwable t2) {
            return Integer.MIN_VALUE;
}
}
    private static int size(Object o2) {
        if (o2 instanceof Map) {
            return ((Map)o2).size();
}
        if (o2 instanceof List) {
            return ((List)o2).size();
}
        return -1;
}
    private static RavenClickGuiScreen refuse(List<String> pending, String why) {
        DEGRADED.add("Abyss.module.impl.configuration.ClickGUI.F : Abyss/ui/raven/RavenClickGuiScreen REFUSED -- " + why);
        AbyssRavenGui.note(pending, "Abyss.ui.raven.RavenClickGuiScreen (RAVEN theme) REFUSED -- " + why);
        return null;
}
    private static void note(List<String> pending, String s) {
        if (pending != null) {
            pending.add(s);
}
}
    static {
        DEGRADED = new ArrayList<String>();
        rowTally = "not built";
}
    private static final class RavenThreads
    implements ThreadFactory {
        private final AtomicInteger n = new AtomicInteger(1);

        private RavenThreads() {
}
        @Override
        public Thread newThread(Runnable r2) {
            Thread t2 = new Thread(r2, "Abyss-RAVEN-anim-" + this.n.getAndIncrement());
            t2.setDaemon(true);
            return t2;
}
}
}