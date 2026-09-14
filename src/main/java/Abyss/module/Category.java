/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.module;

import Abyss.module.impl.configuration.Language;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;

public enum Category {
    Combat,
    Movement,
    Player,
    World,
    Visual,
    Visual_utility,
    Misc,
    Configuration,
    Macro;

    private static final ResourceLocation n;
    private static final ResourceLocation m;
    private static final ResourceLocation u;
    private static final ResourceLocation J;
    private static final ResourceLocation V;
    private static final ResourceLocation q;
    private static final ResourceLocation a;
    private static final ResourceLocation L;
    private static final ResourceLocation o;

    public String c() {
        return this.name();
}
    public static List<Category> j() {
        ArrayList<Category> var0 = new ArrayList<Category>();
        for (Category var4 : Category.values()) {
            if (var4 == Configuration || var4 == Macro || var4 == Visual_utility) continue;
            var0.add(var4);
}
        return var0;
}
    public static ResourceLocation n(Category var0) {
        if (var0 == Combat) {
            return m;
}
        if (var0 == Movement) {
            return q;
}
        if (var0 == Visual) {
            return V;
}
        if (var0 == Visual_utility) {
            return u;
}
        if (var0 == Player) {
            return a;
}
        if (var0 == World) {
            return J;
}
        if (var0 == Misc) {
            return o;
}
        return var0 == Configuration ? L : n;
}
    public String x(int var1, int var2, short var3) {
        return Language.Y(this.name());
}
    static {
        n = new ResourceLocation("minecraft", "icons/macro.png");
        m = new ResourceLocation("minecraft", "icons/combat.png");
        u = new ResourceLocation("minecraft", "icons/visual_utility.png");
        J = new ResourceLocation("minecraft", "icons/world.png");
        V = new ResourceLocation("minecraft", "icons/visual.png");
        q = new ResourceLocation("minecraft", "icons/movement.png");
        a = new ResourceLocation("minecraft", "icons/player.png");
        L = new ResourceLocation("minecraft", "icons/configuration.png");
        o = new ResourceLocation("minecraft", "icons/misc.png");
        Category[] var10000 = new Category[3655614568782102537L];
        var10000[0] = Combat;
        var10000[1] = Movement;
        var10000[2] = Player;
        var10000[3] = World;
        var10000[4] = Visual;
        var10000[5] = Visual_utility;
        var10000[6162584402476924934L] = Misc;
        var10000[-5414625232382066681L] = Configuration;
        var10000[37596606110892040L] = Macro;
}
}