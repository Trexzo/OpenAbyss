/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.command.impl.AbyssCommandBind;
import Abyss.internal.restore.AbyssCommandData;
import Abyss.internal.restore.AbyssCommandSelect;
import Abyss.module.Category;
import Abyss.module.Module;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;

public final class AbyssCommandReset
extends Command {
    private static final String DEFAULTS = "default.json";
    private static final String VISIBILITY = "visibility";
    private static final String SUFFIX = "suffix";

    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return new String[]{"reset", "restet", "r"};
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat("\u00a7eReset module settings to default");
        AbyssCommands.chat("\u00a77Usage:");
        AbyssCommands.chat("\u00a7f  .reset <modules... | category... | \"all\" | \"visibility\" | \"suffix\">");
        AbyssCommands.chat("\u00a78Source: \u00a77" + AbyssCommandData.dirFile().getPath() + "\\" + DEFAULTS + "\u00a78 " + (AbyssCommandData.exists(DEFAULTS) ? "(present)" : "(MISSING)"));
}
    @Override
    public void j(String[] var1, long var2) {
        List<Module> var7;
        JsonObject var4 = AbyssCommandData.readJson(DEFAULTS);
        if (var4 == null) {
            AbyssCommands.chat("\u00a7cNo \u00a7fdefault.json\u00a7c in " + AbyssCommandData.dirFile().getPath() + ". The stock reset reads a stored config; nothing is invented here, so there is nothing to reset to.");
            return;
}
        boolean var5 = false;
        boolean var6 = false;
        if (var1.length == 1 && VISIBILITY.equalsIgnoreCase(var1[0])) {
            var5 = true;
            var7 = AbyssCommandSelect.all();
        } else if (var1.length == 1 && SUFFIX.equalsIgnoreCase(var1[0])) {
            var6 = true;
            var7 = AbyssCommandSelect.all();
        } else {
            var7 = AbyssCommandSelect.resolve(var1, 0, var1.length);
            AbyssCommandSelect.reportUnresolved();
}
        if (var7.isEmpty()) {
            return;
}
        int var8 = 0;
        int var9 = 0;
        int var10 = 0;
        boolean var11 = AbyssCommandBind.gateOk();
        for (int var12 = 0; var12 < var7.size(); ++var12) {
            Module var13 = var7.get(var12);
            JsonElement var14 = var4.get(var13.b());
            if (var14 == null || !var14.isJsonObject()) {
                ++var10;
                continue;
}
            JsonObject var15 = var14.getAsJsonObject();
            boolean var16 = false;
            if (!var5 && !var6) {
                var16 |= AbyssCommandReset.status(var13, var15);
                var16 |= AbyssCommandReset.keyBind(var13, var15, var11);
}
            if (!var6) {
                var16 |= AbyssCommandReset.visible(var13, var15);
}
            if (!var5) {
                var16 |= AbyssCommandReset.suffix(var13, var15);
}
            if (var16) {
                ++var8;
                continue;
}
            ++var9;
}
        AbyssCommands.chat("\u00a7aReset from \u00a7fdefault.json\u00a7a: " + var8 + " module(s) changed, " + var9 + " already matched, " + var10 + " had no entry.");
        AbyssCommands.chat("\u00a78Only status / keyBind / visible / suffix-visible are applied -- the per-module setting values have no proven writer here and are left alone.");
        if (var8 > 0) {
            AbyssCommands.chat("\u00a78Not saved yet -- use \u00a77.config save <name>\u00a78 to persist it.");
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>(AbyssCommandSelect.pool());
        var5.add(VISIBILITY);
        var5.add(SUFFIX);
        return var5;
}
    private static Boolean strictBoolean(JsonObject block, String key) {
        if (block == null || key == null || !block.has(key)) {
            return null;
}
        try {
            JsonElement value = block.get(key);
            if (value == null || !value.isJsonPrimitive()) {
                return null;
}
            JsonPrimitive primitive = value.getAsJsonPrimitive();
            return primitive.isBoolean() ? Boolean.valueOf(primitive.getAsBoolean()) : null;
}
        catch (Throwable ignored) {
            return null;
}
}
    private static Integer strictInteger(JsonObject block, String key) {
        if (block == null || key == null || !block.has(key)) {
            return null;
}
        try {
            JsonElement value = block.get(key);
            if (value == null || !value.isJsonPrimitive()) {
                return null;
}
            JsonPrimitive primitive = value.getAsJsonPrimitive();
            if (!primitive.isNumber()) {
                return null;
}
            double numeric = primitive.getAsDouble();
            if (Double.isNaN(numeric) || Double.isInfinite(numeric) || numeric != Math.rint(numeric)
                    || numeric < Integer.MIN_VALUE || numeric > Integer.MAX_VALUE) {
                return null;
}
            return Integer.valueOf((int)numeric);
}
        catch (Throwable ignored) {
            return null;
}
}
    private static boolean status(Module var0, JsonObject var1) {
        Boolean decoded = AbyssCommandReset.strictBoolean(var1, "status");
        if (decoded == null || !var0.I()) {
            return false;
}
        boolean var2 = decoded.booleanValue();
        if (var0.o() == var2) {
            return false;
}
        try {
            var0.I(20724619369162L, var2);
}
        catch (Throwable var4) {
            return false;
}
        return var0.o() == var2;
}
    private static boolean keyBind(Module var0, JsonObject var1, boolean var2) {
        Integer decoded = AbyssCommandReset.strictInteger(var1, "keyBind");
        if (decoded == null || !var2 || var0.S()) {
            return false;
}
        int var3 = decoded.intValue();
        if (var0.h() == var3) {
            return false;
}
        try {
            var0.z(118276941480361L, var3);
}
        catch (Throwable var5) {
            return false;
}
        return var0.h() == var3;
}
    private static boolean visible(Module var0, JsonObject var1) {
        Boolean decoded = AbyssCommandReset.strictBoolean(var1, "visible");
        if (decoded == null || var0.S() || var0.f() == Category.Macro) {
            return false;
}
        boolean var2 = decoded.booleanValue();
        if (var0.D() == var2) {
            return false;
}
        try {
            var0.Y(0L, var2, (short)0);
}
        catch (Throwable var4) {
            return false;
}
        return var0.D() == var2;
}
    private static boolean suffix(Module var0, JsonObject var1) {
        Boolean decoded = AbyssCommandReset.strictBoolean(var1, "suffix-visible");
        if (decoded == null) {
            return false;
}
        boolean var2 = decoded.booleanValue();
        if (var0.r() == var2) {
            return false;
}
        try {
            var0.C(var2);
}
        catch (Throwable var4) {
            return false;
}
        return var0.r() == var2;
}
    public static String selfTest() {
        try {
            JsonObject valid = new JsonObject();
            valid.addProperty("status", Boolean.TRUE);
            valid.addProperty("visible", Boolean.FALSE);
            valid.addProperty("suffix-visible", Boolean.TRUE);
            valid.addProperty("keyBind", Integer.valueOf(54));
            if (!Boolean.TRUE.equals(AbyssCommandReset.strictBoolean(valid, "status"))
                    || !Boolean.FALSE.equals(AbyssCommandReset.strictBoolean(valid, "visible"))
                    || !Boolean.TRUE.equals(AbyssCommandReset.strictBoolean(valid, "suffix-visible"))
                    || !Integer.valueOf(54).equals(AbyssCommandReset.strictInteger(valid, "keyBind"))) {
                return "FAIL valid-common";
}
            JsonObject malformed = new JsonObject();
            malformed.addProperty("status", "true");
            malformed.addProperty("visible", Integer.valueOf(1));
            malformed.addProperty("suffix-visible", "false");
            malformed.addProperty("keyBind", Double.valueOf(1.5d));
            if (AbyssCommandReset.strictBoolean(malformed, "status") != null
                    || AbyssCommandReset.strictBoolean(malformed, "visible") != null
                    || AbyssCommandReset.strictBoolean(malformed, "suffix-visible") != null
                    || AbyssCommandReset.strictInteger(malformed, "keyBind") != null) {
                return "FAIL malformed-common-accepted";
}

            class ProbeModule extends Module {
                ProbeModule() {
                    super(0L);
                    this.declare("ResetSelfTest", Category.Misc, "reset probe", new Abyss.setting.Setting[0]);
}
            }
            ProbeModule probe = new ProbeModule();
            probe.I(20724619369162L, false);
            probe.z(118276941480361L, 48);
            probe.Y(0L, true, (short)0);
            probe.C(false);

            JsonObject statusBlock = new JsonObject();
            statusBlock.addProperty("status", Boolean.TRUE);
            if (!AbyssCommandReset.status(probe, statusBlock) || !probe.o()
                    || probe.h() != 48 || !probe.D() || probe.r()) {
                return "FAIL status-isolation status=" + probe.o() + " key=" + probe.h()
                        + " visible=" + probe.D() + " suffix=" + probe.r();
}

            JsonObject keyBlock = new JsonObject();
            keyBlock.addProperty("keyBind", Integer.valueOf(54));
            if (!AbyssCommandReset.keyBind(probe, keyBlock, AbyssCommandBind.gateOk())
                    || probe.h() != 54 || !probe.o() || !probe.D() || probe.r()) {
                return "FAIL keybind-isolation status=" + probe.o() + " key=" + probe.h()
                        + " visible=" + probe.D() + " suffix=" + probe.r();
}

            JsonObject visibleBlock = new JsonObject();
            visibleBlock.addProperty("visible", Boolean.FALSE);
            if (!AbyssCommandReset.visible(probe, visibleBlock) || probe.D()
                    || !probe.o() || probe.h() != 54 || probe.r()) {
                return "FAIL visible-isolation status=" + probe.o() + " key=" + probe.h()
                        + " visible=" + probe.D() + " suffix=" + probe.r();
}

            JsonObject suffixBlock = new JsonObject();
            suffixBlock.addProperty("suffix-visible", Boolean.TRUE);
            if (!AbyssCommandReset.suffix(probe, suffixBlock) || !probe.r()
                    || !probe.o() || probe.h() != 54 || probe.D()) {
                return "FAIL suffix-isolation status=" + probe.o() + " key=" + probe.h()
                        + " visible=" + probe.D() + " suffix=" + probe.r();
}

            return "PASS strict-common-fields=4 isolated-writes=4";
}
        catch (Throwable failure) {
            return "FAIL " + failure.getClass().getName() + ": " + failure.getMessage();
}
}

}