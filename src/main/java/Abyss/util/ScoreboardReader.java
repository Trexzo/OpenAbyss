/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.scoreboard.Score
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 */
package Abyss.util;

import Abyss.util.BuildInfo;
import Abyss.util.MinecraftRef;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

public class ScoreboardReader {
    private static Minecraft U;
            public static AtomicBoolean X;

    public static boolean v(long var0) {
        return !BuildInfo.W.equalsIgnoreCase("Development") && !BuildInfo.W.equalsIgnoreCase("NoHackClient") ? !X.get() : true;
}
    public static ArrayList<String> l() {
        if (ScoreboardReader.U.field_71441_e == null) {
            return new ArrayList<String>();
}
        Scoreboard var0 = ScoreboardReader.U.field_71441_e.func_96441_U();
        if (var0 == null) {
            return new ArrayList<String>();
}
        ScoreObjective var1 = var0.func_96539_a(1);
        if (var1 == null) {
            return new ArrayList<String>();
}
        ArrayList<String> var2 = new ArrayList<String>();
        for (Score var4 : var0.func_96534_i(var1)) {
            String var5 = ScorePlayerTeam.func_96667_a((Team)var0.func_96509_i(var4.func_96653_e()), (String)var4.func_96653_e());
            var2.add(var5);
}
        return var2;
}
    static {
        X = new AtomicBoolean(false);
        U = MinecraftRef.c((byte)0, 0L);
}
}