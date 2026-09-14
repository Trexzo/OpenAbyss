/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.ScaledResolution
 */
package Abyss.ui.abyss;

import Abyss.module.impl.visual.HUD;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

public final class AbyssUserInfoRenderer {
    private static final int MUTED = -7697773;
    private static final int WHITE = -1;

    private AbyssUserInfoRenderer() {
}
    public static void render(ScaledResolution resolution) {
        if (resolution == null) {
            return;
}
        try {
            boolean showUser = true;
            boolean showVersion = true;
            try {
                if (HUD.userInfo != null) {
                    showUser = HUD.userInfo.c();
}
                if (HUD.version != null) {
                    showVersion = HUD.version.c();
}
}
            catch (Throwable throwable) {
                // empty catch block
}
            if (!showUser && !showVersion) {
                return;
}
            if (!FontManager.isReady()) {
                return;
}
            FontRenderer font = FontManager.get();
            String user = AbyssUserInfoRenderer.user();
            String uid = AbyssUserInfoRenderer.uid();
            String buildDate = AbyssUserInfoRenderer.build();
            StringBuilder plain = new StringBuilder();
            if (showVersion) {
                plain.append("Release Build");
}
            if (showVersion) {
                plain.append(" - ").append(buildDate);
}
            if (showUser) {
                if (plain.length() > 0) {
                    plain.append(" - ");
}
                plain.append(user).append(" [").append(uid).append("]");
}
            int screenX = resolution.func_78326_a();
            int screenY = resolution.func_78328_b();
            float startX = (float)screenX - font.getWidth(plain.toString()) - 2.0f;
            boolean chatOpen = Minecraft.func_71410_x().field_71462_r instanceof GuiChat;
            float buildY = screenY - (chatOpen ? 24 : 11);
            float x = startX;
            if (showVersion) {
                x = AbyssUserInfoRenderer.drawSeg(font, "Release Build", x, buildY, -7697773);
                x = AbyssUserInfoRenderer.drawSeg(font, " - ", x, buildY, -7697773);
                x = AbyssUserInfoRenderer.drawSeg(font, buildDate, x, buildY, -1);
}
            if (showUser) {
                if (showVersion) {
                    x = AbyssUserInfoRenderer.drawSeg(font, " - ", x, buildY, -7697773);
}
                AbyssUserInfoRenderer.drawSeg(font, user + " [" + uid + "]", x, buildY, -7697773);
}
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    private static float drawSeg(FontRenderer font, String text, float x, float y, int color) {
        font.drawStringWithShadow(text, x, y, color);
        return x + font.getWidth(text);
}
    private static String user() {
        return "Player";
}
    private static String uid() {
        return "0000";
}
    private static String build() {
        try {
            return new SimpleDateFormat("MMddyy").format(new Date());
}
        catch (Throwable throwable) {
            return "";
}
}
}