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
        if (ScoreboardReader.U.theWorld == null) {
            return new ArrayList<String>();
}
        Scoreboard var0 = ScoreboardReader.U.theWorld.getScoreboard();
        if (var0 == null) {
            return new ArrayList<String>();
}
        ScoreObjective var1 = var0.getObjectiveInDisplaySlot(1);
        if (var1 == null) {
            return new ArrayList<String>();
}
        ArrayList<String> var2 = new ArrayList<String>();
        for (Score var4 : var0.getSortedScores(var1)) {
            String var5 = ScorePlayerTeam.formatPlayerName((Team)var0.getPlayersTeam(var4.getPlayerName()), (String)var4.getPlayerName());
            var2.add(var5);
}
        return var2;
}
    static {
        X = new AtomicBoolean(false);
        U = MinecraftRef.c((byte)0, 0L);
}
}