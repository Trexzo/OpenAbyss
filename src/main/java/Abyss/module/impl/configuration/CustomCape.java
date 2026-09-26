/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class CustomCape
extends Module {
    private static long a;

    
        
    public static HashMap<String, String> O;
    public static ModeSetting cape;

    public static ResourceLocation d(long var0) {
        return new ResourceLocation("minecraft", "capes/" + O.get(cape.Y()) + ".png");
}
    public CustomCape(char var1, long var2) {
        super(((long)var1 << 48 | 0L) ^ a ^ 0x34C0F04064E8L);
        this.declare("CustomCape", Category.Configuration, "Get a fake better cape", new Setting[0]);
}
    static {
        a = 38052850158322L;
        O = new HashMap();
        cape = new ModeSetting("Cape", "NONE", "2011", "2012", "2013", "2015", "2016", "MJ", "MJ_STUDIOS", "MJ_CLASSIC", "REALMS", "TRANSLATOR", "MOJIRA", "COBALT", "SCROLLS", "BIRTHDAY", "MILLIONTH", "DB", "OXEYE", "PRISMARINE", "SIZE_M", "SNOWMAN", "SPADE", "TURTLE", "VALENTINE");
}
}