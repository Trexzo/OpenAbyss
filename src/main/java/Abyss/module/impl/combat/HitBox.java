/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.module.impl.combat;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class HitBox
extends Module {
    public static HeaderSetting targetSettings;
    public static BooleanSetting friends;
    public static NumberSetting expand;
    public static BooleanSetting enemies;
    private static final long c = 77663890389010L;
    public static BooleanSetting bosses;
    public static BooleanSetting players;
    public static BooleanSetting animals;
    public static BooleanSetting bots;
    public static BooleanSetting teammates;
    public static BooleanSetting mobs;

    private static void a() {
}
    public HitBox(long var1) {
        super(0x46A288993812L ^ var1 ^ 0x221D33F399D7L);
        this.declare("HitBox", Category.Combat, "Modify entities hitbox to help reach target easier", new Setting[0]);
        var1 = 0x46A288993812L ^ var1;
}
    @Override
    public String g(long var1) {
        return "+" + expand.L();
}
    public static boolean k(byte var0, EntityLivingBase var1, long var2) {
        long var4 = ((long)var0 << 56 | 0x56E60D9DAC87L) ^ 0x46A288993812L;
        long var6 = var4 ^ 0x393F693F3DFL;
        return EntityUtil.q((Entity)var1, players.c(), mobs.c(), animals.c(), bosses.c(), friends.c(), enemies.c(), teammates.c(), bots.c(), var6);
}
    static {
        HitBox.a();
        expand = new NumberSetting("Expand", 0.1f, 0.0f, 1.0f, 0.01f);
        animals = new BooleanSetting("Animals", false);
        enemies = new BooleanSetting("Enemies", true);
        mobs = new BooleanSetting("Mobs", false);
        bots = new BooleanSetting("Bots", false);
        bosses = new BooleanSetting("Bosses", false);
        friends = new BooleanSetting("Friends", false);
        players = new BooleanSetting("Players", true);
        teammates = new BooleanSetting("Teammates", false);
        targetSettings = new HeaderSetting("Target settings");
}
}