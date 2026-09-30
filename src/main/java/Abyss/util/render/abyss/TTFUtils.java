/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.util.render.abyss;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public final class TTFUtils {
    private TTFUtils() {
}
    public static Font getFontFromLocation(String fileName, int size) {
        try {
            return Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("abyss/fonts/" + fileName)).getInputStream()).deriveFont(0, size);
}
        catch (FontFormatException | IOException | RuntimeException ignored) {
            return new Font("SansSerif", 0, size);
}
}
}