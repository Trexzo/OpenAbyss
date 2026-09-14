/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.internal.restore.AbyssCtorCache;
import Abyss.module.Module;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AbyssCtorCacheGate {
    public static final List<String> LOG = new ArrayList<String>();
    public static final int EXPECT_STRONG = 29;
    public static int shouldFail;
    public static int didFail;
    public static int positives;
    public static int positivesOk;
    public static int strong;
    public static int inert;
    private static final Map<Class<?>, Boolean> OK;
    private static final Set<Class<?>> RAN;

    private AbyssCtorCacheGate() {
}
    public static boolean planOk(Class<?> var0) {
        if (!RAN.contains(var0)) {
            return false;
}
        Boolean var1 = OK.get(var0);
        return var1 != null && var1 != false;
}
    public static boolean run() {
        LOG.clear();
        OK.clear();
        RAN.clear();
        shouldFail = 0;
        didFail = 0;
        positives = 0;
        positivesOk = 0;
        strong = 0;
        inert = 0;
        AbyssCtorCache.Plan[] var0 = AbyssCtorCache.plans();
        for (int var1 = 0; var1 < var0.length; ++var1) {
            try {
                AbyssCtorCacheGate.runPlan(var0[var1]);
                continue;
}
            catch (Throwable var6) {
                OK.put(var0[var1].cls, Boolean.FALSE);
                LOG.add("GATE " + var0[var1].name + ": the control sweep itself threw " + var6);
}
}
        AbyssCtorCache.SPlan[] var2 = AbyssCtorCache.splans();
        for (int var3 = 0; var3 < var2.length; ++var3) {
            try {
                AbyssCtorCacheGate.runSPlan(var2[var3]);
                continue;
}
            catch (Throwable var5) {
                OK.put(var2[var3].cls, Boolean.FALSE);
                LOG.add("GATE " + var2[var3].name + ": the control sweep itself threw " + var5);
}
}
        boolean var4 = positivesOk == positives && shouldFail == didFail && strong >= 29;
        LOG.add("GATE positive controls " + positivesOk + "/" + positives + ", falsified inputs rejected " + didFail + "/" + shouldFail + ", plans with a value-level control " + strong + "/" + 29 + ", inert sites " + inert + " -- " + (var4 ? "GREEN" : "RED"));
        return var4;
}
    private static void runPlan(AbyssCtorCache.Plan var0) {
        RAN.add(var0.cls);
        boolean var1 = true;
        ++positives;
        if (AbyssCtorCacheGate.tryBuild(var0) == null) {
            var1 = false;
            LOG.add("GATE " + var0.name + ": POSITIVE CONTROL FAILED -- the un-falsified plan does not build");
        } else {
            ++positivesOk;
}
        boolean var2 = AbyssCtorCache.hasCache(var0.cls, var0.cacheField);
        if (var2) {
            var1 &= AbyssCtorCacheGate.must(var0.name + " declared cache length", AbyssCtorCacheGate.with(var0, var0.seedField, var0.cacheField, var0.cacheLen + 1, AbyssCtorCacheGate.copy(var0.sites)));
            var1 &= AbyssCtorCacheGate.must(var0.name + " cache field name", AbyssCtorCacheGate.with(var0, var0.seedField, var0.cacheField + "$nope", var0.cacheLen, AbyssCtorCacheGate.copy(var0.sites)));
}
        var1 &= AbyssCtorCacheGate.must(var0.name + " seed field name", AbyssCtorCacheGate.with(var0, var0.seedField + "$nope", var0.cacheField, var0.cacheLen, AbyssCtorCacheGate.copy(var0.sites)));
        boolean var3 = false;
        for (int var4 = 0; var4 < var0.sites.length; ++var4) {
            if (var0.sites[var4].field == null || var0.sites[var4].desc == null) continue;
            var1 &= AbyssCtorCacheGate.must(var0.name + " post-condition field name of site " + var4, AbyssCtorCacheGate.fieldName(var0, var4));
            if (AbyssCtorCacheGate.reject(AbyssCtorCacheGate.value(var0, var4)) || AbyssCtorCacheGate.reject(AbyssCtorCacheGate.slot(var0, var4))) {
                var3 = true;
                continue;
}
            ++inert;
            LOG.add("GATE " + var0.name + ": site " + var4 + " (" + var0.sites[var4].field + ") is INERT -- neither its value nor its slot changes the built module");
}
        if (var3) {
            ++strong;
        } else {
            LOG.add("GATE " + var0.name + ": no value-level control is falsifiable any more");
}
        OK.put(var0.cls, var1);
}
    private static void runSPlan(AbyssCtorCache.SPlan var0) {
        RAN.add(var0.cls);
        boolean var1 = true;
        ++positives;
        if (AbyssCtorCacheGate.tryBuildS(var0) == null) {
            var1 = false;
            LOG.add("GATE " + var0.name + ": POSITIVE CONTROL FAILED -- the un-falsified plan does not build");
        } else {
            ++positivesOk;
}
        boolean var2 = AbyssCtorCache.hasCache(var0.cls, var0.cacheField);
        if (var2) {
            var1 &= AbyssCtorCacheGate.mustS(var0.name + " declared String cache length", AbyssCtorCacheGate.sWith(var0, var0.seedField, var0.cacheField, var0.cacheLen + 1, AbyssCtorCacheGate.sCopy(var0.sites)));
            var1 &= AbyssCtorCacheGate.mustS(var0.name + " String cache field name", AbyssCtorCacheGate.sWith(var0, var0.seedField, var0.cacheField + "$nope", var0.cacheLen, AbyssCtorCacheGate.sCopy(var0.sites)));
}
        var1 &= AbyssCtorCacheGate.mustS(var0.name + " String seed field name", AbyssCtorCacheGate.sWith(var0, var0.seedField + "$nope", var0.cacheField, var0.cacheLen, AbyssCtorCacheGate.sCopy(var0.sites)));
        boolean var3 = false;
        for (int var4 = 0; var4 < var0.sites.length; ++var4) {
            if (var0.sites[var4].field == null || var0.sites[var4].expect == null) continue;
            var1 &= AbyssCtorCacheGate.mustS(var0.name + " String post-condition of site " + var4, AbyssCtorCacheGate.sExpect(var0, var4));
            if (AbyssCtorCacheGate.rejectS(AbyssCtorCacheGate.sValue(var0, var4)) || AbyssCtorCacheGate.rejectS(AbyssCtorCacheGate.sSlot(var0, var4))) {
                var3 = true;
                continue;
}
            ++inert;
            LOG.add("GATE " + var0.name + ": String site " + var4 + " (" + var0.sites[var4].field + ") is INERT");
}
        if (var3) {
            ++strong;
        } else {
            LOG.add("GATE " + var0.name + ": no String value-level control is falsifiable any more");
}
        OK.put(var0.cls, var1);
}
    private static Module tryBuild(AbyssCtorCache.Plan var0) {
        try {
            return AbyssCtorCache.build(var0);
}
        catch (Throwable var2) {
            return null;
}
}
    private static Module tryBuildS(AbyssCtorCache.SPlan var0) {
        try {
            return AbyssCtorCache.buildS(var0);
}
        catch (Throwable var2) {
            return null;
}
}
    private static boolean reject(AbyssCtorCache.Plan var0) {
        return AbyssCtorCacheGate.tryBuild(var0) == null;
}
    private static boolean rejectS(AbyssCtorCache.SPlan var0) {
        return AbyssCtorCacheGate.tryBuildS(var0) == null;
}
    private static boolean must(String var0, AbyssCtorCache.Plan var1) {
        ++shouldFail;
        if (AbyssCtorCacheGate.tryBuild(var1) == null) {
            ++didFail;
            return true;
}
        LOG.add("GATE " + var0 + ": ACCEPTED -- THE GATE IS BROKEN");
        return false;
}
    private static boolean mustS(String var0, AbyssCtorCache.SPlan var1) {
        ++shouldFail;
        if (AbyssCtorCacheGate.tryBuildS(var1) == null) {
            ++didFail;
            return true;
}
        LOG.add("GATE " + var0 + ": ACCEPTED -- THE GATE IS BROKEN");
        return false;
}
    private static AbyssCtorCache.Site[] copy(AbyssCtorCache.Site[] var0) {
        AbyssCtorCache.Site[] var1 = new AbyssCtorCache.Site[var0.length];
        for (int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = new AbyssCtorCache.Site(var0[var2].idx, var0[var2].k0, var0[var2].value, var0[var2].field, var0[var2].desc);
}
        return var1;
}
    private static AbyssCtorCache.SSite[] sCopy(AbyssCtorCache.SSite[] var0) {
        AbyssCtorCache.SSite[] var1 = new AbyssCtorCache.SSite[var0.length];
        for (int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = new AbyssCtorCache.SSite(var0[var2].idx, var0[var2].k0, var0[var2].value, var0[var2].field, var0[var2].expect);
}
        return var1;
}
    private static AbyssCtorCache.Plan with(AbyssCtorCache.Plan var0, String var1, String var2, int var3, AbyssCtorCache.Site[] var4) {
        return new AbyssCtorCache.Plan(var0.cls, var0.name, var1, var2, var0.k, var3, var0.pack, var4);
}
    private static AbyssCtorCache.SPlan sWith(AbyssCtorCache.SPlan var0, String var1, String var2, int var3, AbyssCtorCache.SSite[] var4) {
        return new AbyssCtorCache.SPlan(var0.cls, var0.name, var1, var2, var0.k, var3, var0.pack, var4);
}
    private static AbyssCtorCache.Plan value(AbyssCtorCache.Plan var0, int var1) {
        AbyssCtorCache.Site[] var2 = AbyssCtorCacheGate.copy(var0.sites);
        var2[var1] = new AbyssCtorCache.Site(var2[var1].idx, var2[var1].k0, var2[var1].value + 1, var2[var1].field, var2[var1].desc);
        return AbyssCtorCacheGate.with(var0, var0.seedField, var0.cacheField, var0.cacheLen, var2);
}
    private static AbyssCtorCache.Plan slot(AbyssCtorCache.Plan var0, int var1) {
        AbyssCtorCache.Site[] var2 = AbyssCtorCacheGate.copy(var0.sites);
        var2[var1] = new AbyssCtorCache.Site(var2[var1].idx ^ 1, var2[var1].k0, var2[var1].value, var2[var1].field, var2[var1].desc);
        return AbyssCtorCacheGate.with(var0, var0.seedField, var0.cacheField, var0.cacheLen, var2);
}
    private static AbyssCtorCache.Plan fieldName(AbyssCtorCache.Plan var0, int var1) {
        AbyssCtorCache.Site[] var2 = AbyssCtorCacheGate.copy(var0.sites);
        var2[var1] = new AbyssCtorCache.Site(var2[var1].idx, var2[var1].k0, var2[var1].value, var2[var1].field + "$nope", var2[var1].desc);
        return AbyssCtorCacheGate.with(var0, var0.seedField, var0.cacheField, var0.cacheLen, var2);
}
    private static AbyssCtorCache.SPlan sValue(AbyssCtorCache.SPlan var0, int var1) {
        AbyssCtorCache.SSite[] var2 = AbyssCtorCacheGate.sCopy(var0.sites);
        var2[var1] = new AbyssCtorCache.SSite(var2[var1].idx, var2[var1].k0, var2[var1].value + "$nope", var2[var1].field, var2[var1].expect);
        return AbyssCtorCacheGate.sWith(var0, var0.seedField, var0.cacheField, var0.cacheLen, var2);
}
    private static AbyssCtorCache.SPlan sSlot(AbyssCtorCache.SPlan var0, int var1) {
        AbyssCtorCache.SSite[] var2 = AbyssCtorCacheGate.sCopy(var0.sites);
        var2[var1] = new AbyssCtorCache.SSite(var2[var1].idx ^ 1, var2[var1].k0, var2[var1].value, var2[var1].field, var2[var1].expect);
        return AbyssCtorCacheGate.sWith(var0, var0.seedField, var0.cacheField, var0.cacheLen, var2);
}
    private static AbyssCtorCache.SPlan sExpect(AbyssCtorCache.SPlan var0, int var1) {
        AbyssCtorCache.SSite[] var2 = AbyssCtorCacheGate.sCopy(var0.sites);
        var2[var1] = new AbyssCtorCache.SSite(var2[var1].idx, var2[var1].k0, var2[var1].value, var2[var1].field, var2[var1].expect + "$nope");
        return AbyssCtorCacheGate.sWith(var0, var0.seedField, var0.cacheField, var0.cacheLen, var2);
}
    static {
        OK = new HashMap();
        RAN = new HashSet();
}
}