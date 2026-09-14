/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.scoreboard.Score
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.EnumChatFormatting
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.MathUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;

public class ScoreBoard
extends Module {
        public static BooleanSetting textShadow;
    public static NumberSetting offsetX;
    public static BooleanSetting hideScoreboard;
    public static PercentageSetting backgroundOpacity;
    private static long[] d;
    public static NumberSetting scale;
    public static NumberSetting offsetY;
    public static BooleanSetting roundedRectangle;
    
    public static BooleanSetting disableScores;
    private static String b;

    public ScoreBoard(long var1) {
        super(a ^ var1 ^ 0x17C36EAC206BL);
        this.declare("ScoreBoard", Category.Configuration, "Manage vanilla scoreboard rendering", new Setting[0]);
        var1 = a ^ var1;
}
    public static void n(ScoreObjective var0, ScaledResolution var3) {
        if (!hideScoreboard.c()) {
            double var16 = scale.L();
            float var18 = offsetX.L();
            float var19 = offsetY.L();
            boolean var20 = disableScores.c();
            boolean var21 = roundedRectangle.c();
            boolean var22 = textShadow.c();
            Scoreboard var23 = var0.func_96682_a();
            Collection var24 = var23.func_96534_i(var0);
            List var25 = Lists.newArrayList((Iterable)Iterables.filter((Iterable)var24, var0x -> var0x.func_96653_e() != null && !var0x.func_96653_e().startsWith("#")));
            if (var25.size() > 15) {
                var25 = var25.subList(var25.size() - 15, var25.size());
}
            CustomFont var26 = Font.J();
            GlStateManager.func_179094_E();
            GlStateManager.func_179139_a((double)var16, (double)var16, (double)1.0);
            int var27 = (int)((double)var3.func_78326_a() / var16);
            int var28 = (int)((double)var3.func_78328_b() / var16);
            int var29 = (int)Math.ceil(var26.R(var0.func_96678_d(), 52019766876817L));
            for (Score var31 : var25) {
                ScorePlayerTeam var32 = var23.func_96509_i(var31.func_96653_e());
                String var33 = ScorePlayerTeam.func_96667_a((Team)var32, (String)var31.func_96653_e());
                if (!var20) {
                    var33 = var33 + b + EnumChatFormatting.RED + var31.func_96652_c();
}
                var29 = Math.max(var29, (int)Math.ceil(var26.R(var33, 52019766876817L)));
}
            int var49 = Math.max(1, (int)Math.ceil(var26.o(60714858652844L)));
            int var50 = var25.size() * var49;
            int var51 = var28 / 2 + var50 / 3 + (int)var19;
            int var52 = 3;
            int var34 = var27 - var29 - var52 + (int)var18;
            int var35 = var27 - var52 + 2 + (int)var18;
            int var36 = (int)(2.55 * (double)backgroundOpacity.k());
            int var37 = new Color(0, 0, 0, var36).getRGB();
            int var38 = new Color(0, 0, 0, (int)MathUtil.R((double)var36 * 1.5, 0.0, 255.0)).getRGB();
            if (var21) {
                int var39 = var51 - var50;
                int var40 = var39 - var49 - 1;
                RenderUtil.j(var34 - 2, var40, var35, var51, 6.0f, 4113131265056L, var37);
                int var41 = 0;
                for (Score var43 : var25) {
                    ScorePlayerTeam var44 = var23.func_96509_i(var43.func_96653_e());
                    String var45 = ScorePlayerTeam.func_96667_a((Team)var44, (String)var43.func_96653_e());
                    String var46 = EnumChatFormatting.RED + "" + var43.func_96652_c();
                    int var47 = var51 - ++var41 * var49;
                    var26.v(var45, var34, var47, 0xFFFFFF, 88827598794260L, var22);
                    if (var20) continue;
                    var26.v(var46, (float)var35 - var26.R(var46, 52019766876817L), var47, 0xFFFFFF, 88827598794260L, var22);
}
                String var56 = var0.func_96678_d();
                var26.v(var56, (float)var34 + (float)var29 / 2.0f - var26.R(var56, 52019766876817L) / 2.0f, var39 - var49, 0xFFFFFF, 88827598794260L, var22);
            } else {
                int var53 = 0;
                for (Score var55 : var25) {
                    ScorePlayerTeam var57 = var23.func_96509_i(var55.func_96653_e());
                    String var58 = ScorePlayerTeam.func_96667_a((Team)var57, (String)var55.func_96653_e());
                    String var59 = EnumChatFormatting.RED + "" + var55.func_96652_c();
                    int var60 = var51 - ++var53 * var49;
                    RenderUtil.c(125644905353792L, var34 - 2, var60, var35, var60 + var49, var37);
                    var26.v(var58, var34, var60, 0xFFFFFF, 88827598794260L, var22);
                    if (!var20) {
                        var26.v(var59, (float)var35 - var26.R(var59, 52019766876817L), var60, 0xFFFFFF, 88827598794260L, var22);
}
                    if (var53 != var25.size()) continue;
                    String var61 = var0.func_96678_d();
                    RenderUtil.c(125644905353792L, var34 - 2, var60 - var49 - 1, var35, var60 - 1, var38);
                    RenderUtil.c(125644905353792L, var34 - 2, var60 - 1, var35, var60, var37);
                    var26.v(var61, (float)var34 + (float)var29 / 2.0f - var26.R(var61, 52019766876817L) / 2.0f, var60 - var49, 0xFFFFFF, 88827598794260L, var22);
}
}
            GlStateManager.func_179121_F();
}
}
    static {
        b = ": ";
        h = new HashMap(13);
        d = new long[]{3694946831705732335L, -1892344913047605889L, -6692344859849378204L, -712116554178381252L};
        backgroundOpacity = new PercentageSetting("Background-opacity", 30);
        hideScoreboard = new BooleanSetting("Hide-scoreboard", false);
        disableScores = new BooleanSetting("Disable-scores", true);
        textShadow = new BooleanSetting("Text-shadow", false);
        roundedRectangle = new BooleanSetting("Rounded-rectangle", false);
        scale = new NumberSetting("Scale", 1.0f, 0.0f, 3.0f, 0.01f);
        offsetX = new NumberSetting("Offset-X", 0.0f, -1000.0f, 1000.0f, 1.0f);
        offsetY = new NumberSetting("Offset-Y", 0.0f, -1000.0f, 1000.0f, 1.0f);
}
}