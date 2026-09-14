/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.scoreboard.Scoreboard
 */
package Abyss.util;

import Abyss.util.MinecraftRef;
import Abyss.util.ScoreboardUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.scoreboard.Scoreboard;

public class HypixelScoreboardParser {
    private final List<String> j;
    private static Pattern A;
    private static long b;
    private static int e;
        private boolean V;
    private static Pattern T;
    private String Z;
    private String y;
    private static Pattern a;
        private static Pattern x;
    private static long[] i;
    private boolean Q;
    public static Pattern S;
    private static Pattern M;
    private static Pattern g;
    private boolean z;
    private boolean X;
    private boolean u;
    private boolean L;
    private static Map l;
    private static Pattern f;
    private boolean J;
    private boolean F;
    private boolean v;
    

    public boolean F() {
        return this.L;
}
    public boolean i() {
        return this.z;
}
    private void h(String var1, int var2, int var3, char var4) {
        Matcher var7 = T.matcher(var1);
        if (var7.find()) {
            int var10 = Integer.parseInt(var7.group(1)) * 60 + Integer.parseInt(var7.group(2));
            boolean var9 = Math.abs(e - var10) > 10;
            e = var10;
            if (!var9 && var10 == 0) {
                this.X = true;
}
        } else {
            Matcher var8 = f.matcher(var1);
            if (var8.find()) {
                this.v = true;
            } else if (g.matcher(var1).find()) {
                this.v = true;
}
}
}
    public boolean V() {
        return this.Q;
}
    public boolean f() {
        return this.F;
}
    public HypixelScoreboardParser(long var1) {
        Scoreboard var14;
        var1 = b ^ var1;
        long var5 = (var1 ^ 0x42C31E25D49L) >>> 16;
        int var7 = (int)((var1 ^ 0x42C31E25D49L) << 48 >>> 48);
        int var8 = (int)((var1 ^ 0x461E746BE2D3L) >>> 32);
        int var9 = (int)((var1 ^ 0x461E746BE2D3L) << 32 >>> 40);
        int var10 = (int)((var1 ^ 0x461E746BE2D3L) << 56 >>> 56);
        int var11 = (int)((var1 ^ 0x560ABFE69FC0L) >>> 56);
        long var12 = (var1 ^ 0x560ABFE69FC0L) << 8 >>> 8;
        this.j = new ArrayList<String>(4);
        this.Z = null;
        this.Q = false;
        this.L = false;
        this.F = false;
        this.J = false;
        this.u = false;
        this.y = null;
        this.z = false;
        this.V = false;
        this.v = false;
        this.X = false;
        if (MinecraftRef.c((byte)((byte)var11), (long)0L).field_71441_e != null && (var14 = MinecraftRef.c((byte)((byte)var11), (long)0L).field_71441_e.func_96441_U()) != null) {
            String var15 = ScoreboardUtil.w(var14);
            String var16 = ScoreboardUtil.r(var15);
            if (var16 != null && var16.contains("MEGA WALLS")) {
                this.L = true;
                String var17 = ScoreboardUtil.Z((short)var7, var15, "MEGA WALLS");
                this.U(var8, var9, var14, var17, (byte)var10);
            } else if (var16 != null && var16.contains("REPLAY")) {
                this.F = true;
                this.B(0L, var14);
            } else if (var16 != null && var16.contains("ATLAS")) {
                this.F = true;
                this.J = true;
                this.B(0L, var14);
            } else if (var16 != null && var16.contains("SKYBLOCK")) {
                this.z = true;
}
}
}
    public boolean W() {
        return this.u;
}
    private void B(long var1, Scoreboard var3) {
        List var6 = ScoreboardUtil.b(var3);
        List<String> var7 = ScoreboardUtil.L(var6);
        if (!var7.isEmpty()) {
            int var9 = var7.size();
            for (int var8 = 0; var8 < var9; ++var8) {
                String var10 = var7.get(var8);
                Matcher var11 = M.matcher(var10);
                if (var11.find()) {
                    this.y = var11.group(1);
                    continue;
}
                if (!var10.contains("Game: Mega Walls")) continue;
                this.u = true;
}
}
}
    public static void M(short var0, int var1) {
        e = 0;
}
    public boolean d() {
        return this.j.size() == 4;
}
    private void matcher(String var1, List var2, long var3, byte var5, List var6) {
        long var7 = (var3 << 8 | (long)var5 << 56 >>> 56) ^ b;
        int var11 = (int)((var7 ^ 0x452C09E18E0CL) << 48 >>> 48);
        int var12 = 0;
        for (int var13 = 3; var13 < 7; ++var13) {
            String var14 = (String)var2.get(var13);
            Matcher var15 = x.matcher(var14);
            if (var15.find()) {
                String var16 = ScoreboardUtil.Z((short)var11, (String)var6.get(var13), "[");
                this.j.add(var16);
}
            if (!var14.contains("eliminated!")) continue;
            ++var12;
}
        if (var12 == 3) {
            this.X = true;
}
}
    public List<String> B() {
        return this.j;
}
    private void U(int var1, int var2, Scoreboard var3, String var4, byte var5) {
        long var6 = ((long)var1 << 32 | (long)var2 << 40 >>> 32 | (long)var5 << 56 >>> 56) ^ b;
        long var8 = (var6 ^ 0x71E4C683196L) >>> 8;
        int var10 = (int)((var6 ^ 0x71E4C683196L) << 56 >>> 56);
        int var11 = (int)((var6 ^ 0x66A43FEB4B5EL) >>> 32);
        int var12 = (int)((var6 ^ 0x66A43FEB4B5EL) << 32 >>> 48);
        int var13 = (int)((var6 ^ 0x66A43FEB4B5EL) << 48 >>> 48);
        List var16 = ScoreboardUtil.b(var3);
        List<String> var17 = ScoreboardUtil.L(var16);
        if (var17.size() >= 10) {
            if (a.matcher(var17.get(9)).find()) {
                this.Q = true;
                Matcher var18 = S.matcher(var17.get(0));
                if (var18.find()) {
                    this.Z = var18.group(1);
}
            } else {
                for (String var19 : var17) {
                    if (!A.matcher(var19).find()) continue;
                    this.V = true;
                    return;
}
}
            this.h(var17.get(1), var11, var12, (char)var13);
            this.matcher(var4, var17, var8, (byte)var10, var16);
}
}
    public boolean P() {
        return this.v;
}
    public String c() {
        return this.Z;
}
    public boolean s(String var1) {
        return this.j.contains(var1);
}
    public boolean l() {
        return this.J;
}
    public boolean y() {
        return this.j.size() == 1;
}
    public boolean O() {
        return this.j.isEmpty();
}
    public boolean Z() {
        return this.X;
}
    public boolean k() {
        return this.V;
}
    public String x() {
        return this.y;
}
    static {
        b = 30480468594522L;
        S = Pattern.compile("\\d+/\\d+/\\d+\\s+(\\w+)");
        g = Pattern.compile("Gates Open: \\d+:\\d+");
        f = Pattern.compile("Walls Fall: (\\d+):(\\d+)");
        T = Pattern.compile("Game End: (\\d+):(\\d+)");
        a = Pattern.compile("[0-9]+\\sF\\.\\sKills?\\s[0-9]+\\sF\\.\\sAssists?");
        A = Pattern.compile("Players:\\s*[0-9]+/[0-9]+");
        x = Pattern.compile("\\[[BGRY]] Wither HP: ([,\\d]+)");
        M = Pattern.compile("Map: ([a-zA-Z0-9_ ]+)");
}
}