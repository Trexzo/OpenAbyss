/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package Abyss.module;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.combat.HitBox;
import Abyss.module.impl.misc.AntiBot;
import Abyss.module.impl.misc.AntiNick;
import Abyss.module.impl.misc.NameHider;
import Abyss.module.impl.misc.NoObfuscation;
import Abyss.module.impl.player.ChestStealer;
import Abyss.module.impl.player.GhostHand;
import Abyss.module.impl.player.NoHitDelay;
import Abyss.module.impl.visual.Animations;
import Abyss.module.impl.visual.AntiDebuff;
import Abyss.module.impl.visual.BarrierVisible;
import Abyss.module.impl.visual.CaveXray;
import Abyss.module.impl.visual.Chams;
import Abyss.module.impl.visual.ItemScale;
import Abyss.module.impl.visual.NoHurtCam;
import Abyss.module.impl.visual.TeamInvisible;
import Abyss.module.impl.visual.ViewClip;
import Abyss.module.impl.world.Scaffold;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class ModuleManager {
    public static CaveXray m = new CaveXray(0L);
    public static NameHider J;
    public static AntiNick f;
    private static Map j;
    public static ViewClip h;
    public static List<String> n;
    private static long b;
    public static NoHurtCam g;
    public static TeamInvisible y;
    public static ChestStealer q;
    public static NoHitDelay p;
    public static Scaffold I;
    private static Logger z;
    public static GhostHand Q;
    public static BarrierVisible W;
    public static AntiDebuff O;
    public static Chams a;
    public static NoObfuscation k;
    public static Animations d;
    public static HitBox r;
    public static AntiBot c;
    public static ItemScale v;
    public static List<Module> S;
    private static String[] i;
    public static HashMap<Class<? extends Module>, Module> o;
    private static String[] e;
    public static boolean $skidonion$1876635314;

    public static List<Module> modules() {
        return S == null ? Collections.emptyList() : S;
}
    public static Module byClass(Class<? extends Module> var0) {
        return o == null ? null : o.get(var0);
}
    public static Module byName(String var0) {
        if (var0 != null) {
            for (Module var1 : ModuleManager.modules()) {
                if (!var0.equalsIgnoreCase(var1.name())) continue;
                return var1;
}
}
        return null;
}
    public static List<Module> inCategory(Category var0) {
        ArrayList<Module> var1 = new ArrayList<Module>();
        for (Module var2 : ModuleManager.modules()) {
            if (var2.f() != var0) continue;
            var1.add(var2);
}
        return var1;
}
    public static List<String> names() {
        ArrayList<String> var0 = new ArrayList<String>();
        for (Module var1 : ModuleManager.modules()) {
            if (var1.name() == null) continue;
            var0.add(var1.name());
}
        return var0;
}
    public static String describe(String var0) {
        Module var1 = ModuleManager.byName(var0);
        return var1 == null ? null : var1.description();
}
    static {
        W = new BarrierVisible(0L);
        d = new Animations(0L);
        h = new ViewClip(0L);
        O = new AntiDebuff(0L);
        g = new NoHurtCam(0L);
        Q = new GhostHand(0L);
        q = new ChestStealer(0L);
        r = new HitBox(0L);
        v = new ItemScale(0L);
}
}