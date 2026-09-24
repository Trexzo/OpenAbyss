/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ChatAllowedCharacters
 *  org.lwjgl.input.Keyboard
 */
package Abyss.ui.screen;

import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.RenderingUtils;
import Abyss.util.render.abyss.Theme;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public final class AbyssLoginScreen
extends GuiScreen {
    private static final String IDLE_STATUS = "Enter your Abyss credentials";
    private static final long FLASH_MS = 2000L;
    private static final int COL_W = 200;
    private static final int ROW_H = 20;
    private static final int CARD_PAD = 12;
    private static final int OFF_TITLE = -100;
    private static final int OFF_SUBTITLE = -64;
    private static final int OFF_CARD = -48;
    private static final int OFF_STATUS = -42;
    private static final int OFF_USERNAME = -26;
    private static final int OFF_PASSWORD = -2;
    private static final int OFF_LOGIN = 26;
    private static final int OFF_QUIT = 50;
    private final GuiScreen next;
    private final List<Field> fields = new ArrayList<Field>();
    private Field username;
    private Field password;
    private Button login;
    private Button quit;
    private String status = "Enter your Abyss credentials";
    private int statusColor = -7697773;
    private long statusUntil;

    public AbyssLoginScreen() {
        this(null);
}
    public AbyssLoginScreen(GuiScreen next) {
        this.next = next;
}
    private static FontRenderer title() {
        return FontManager.FR;
}
    private static FontRenderer body() {
        return FontManager.SMALL_FR;
}
    private static float lineHeight(FontRenderer font) {
        return font.getHeight("Ag");
}
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        FontManager.ensureTextures();
        int colX = this.width / 2 - 100;
        int centerY = this.height / 2;
        this.fields.clear();
        this.username = new Field(colX, centerY + -26, 200, 20, "Username", 32, false);
        this.password = new Field(colX, centerY + -2, 200, 20, "Password", 64, true);
        this.username.focused = true;
        this.fields.add(this.username);
        this.fields.add(this.password);
        this.login = new Button(colX, centerY + 26, 200, 20, "Login", true);
        this.quit = new Button(colX, centerY + 50, 200, 20, "Quit", false);
}
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
}
    public boolean doesGuiPauseGame() {
        return false;
}
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontManager.ensureTextures();
        RenderingUtils.drawGuiBackground(this.width, this.height);
        int colX = this.width / 2 - 100;
        int centerY = this.height / 2;
        float cardTop = centerY + -48;
        float cardBottom = centerY + 50 + 20 + 12;
        AbyssLoginScreen.rect(colX - 12, cardTop, colX + 200 + 12, cardBottom, Theme.withAlpha(-15330022, 230));
        AbyssLoginScreen.outline(colX - 12, cardTop, colX + 200 + 12, cardBottom, -14408918);
        AbyssLoginScreen.rect(colX - 12, cardTop, colX + 200 + 12, cardTop + 1.0f, Theme.pulsingPrimary());
        this.drawTitle(centerY);
        boolean flashing = this.statusUntil > System.currentTimeMillis();
        this.centered(AbyssLoginScreen.body(), flashing ? this.status : IDLE_STATUS, centerY + -42, flashing ? this.statusColor : -7697773);
        for (int i = 0; i < this.fields.size(); ++i) {
            this.fields.get(i).draw();
}
        this.login.draw(mouseX, mouseY);
        this.quit.draw(mouseX, mouseY);
        this.centered(AbyssLoginScreen.body(), "Tab switches fields  -  Enter submits", cardBottom + 6.0f, Theme.withAlpha(-7697773, 153));
}
    private void drawTitle(int centerY) {
        float scale = 3.0f;
        String name = "ABYSS";
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)3.0f, (float)3.0f, (float)3.0f);
        float x = ((float)this.width / 2.0f - AbyssLoginScreen.title().getWidth("ABYSS") * 3.0f / 2.0f) / 3.0f;
        float y = Math.max(4.0f, (float)(centerY + -100)) / 3.0f;
        AbyssLoginScreen.title().drawStringWithShadow("ABYSS", x, y, Theme.pulsingPrimary());
        GlStateManager.popMatrix();
        this.centered(AbyssLoginScreen.body(), "Client access", centerY + -64, -7697773);
}
    private void centered(FontRenderer font, String text, float y, int color) {
        font.drawStringWithShadow(text, (float)this.width / 2.0f - font.getWidth(text) / 2.0f, y, color);
}
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 15) {
            boolean userFocused = this.username.focused;
            this.username.focused = !userFocused;
            this.password.focused = userFocused;
            return;
}
        if (keyCode == 28 || keyCode == 156) {
            this.attemptLogin();
            return;
}
        for (int i = 0; i < this.fields.size(); ++i) {
            Field f = this.fields.get(i);
            if (!f.focused) continue;
            f.key(typedChar, keyCode);
            return;
}
}
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int i;
        if (mouseButton != 0) {
            return;
}
        Field hit = null;
        for (i = 0; i < this.fields.size(); ++i) {
            if (!this.fields.get(i).contains(mouseX, mouseY)) continue;
            hit = this.fields.get(i);
            break;
}
        if (hit != null) {
            for (i = 0; i < this.fields.size(); ++i) {
                this.fields.get(i).focused = false;
}
            hit.focused = true;
            hit.placeCursor(mouseX);
            return;
}
        if (this.login.contains(mouseX, mouseY)) {
            this.attemptLogin();
        } else if (this.quit.contains(mouseX, mouseY)) {
            this.mc.shutdown();
}
}
    private void attemptLogin() {
        String user = this.username.text.toString().trim();
        String pass = this.password.text.toString();
        if (user.isEmpty() || pass.isEmpty()) {
            this.flash("Enter a username and password", -41876);
            return;
}
        this.mc.displayGuiScreen(this.next);
}
    private void flash(String message, int color) {
        this.status = message;
        this.statusColor = color;
        this.statusUntil = System.currentTimeMillis() + 2000L;
}
    private static void outline(float l, float t2, float r2, float b, int color) {
        AbyssLoginScreen.rect(l, t2, r2, t2 + 1.0f, color);
        AbyssLoginScreen.rect(l, b - 1.0f, r2, b, color);
        AbyssLoginScreen.rect(l, t2, l + 1.0f, b, color);
        AbyssLoginScreen.rect(r2 - 1.0f, t2, r2, b, color);
}
    private static void rect(float l, float t2, float r2, float b, int color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        Gui.drawRect((int)((int)l), (int)((int)t2), (int)((int)r2), (int)((int)b), (int)color);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.resetColor();
}
    private static final class Button {
        private final int x;
        private final int y;
        private final int w;
        private final int h;
        private final String label;
        private final boolean primary;

        private Button(int x, int y, int w2, int h, String label, boolean primary) {
            this.x = x;
            this.y = y;
            this.w = w2;
            this.h = h;
            this.label = label;
            this.primary = primary;
}
        private boolean contains(int mouseX, int mouseY) {
            return mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h;
}
        private void draw(int mouseX, int mouseY) {
            int text;
            int border;
            int fill;
            boolean hovered = this.contains(mouseX, mouseY);
            if (this.primary) {
                fill = hovered ? -1689274 : -7725272;
                border = hovered ? -41876 : -1689274;
                text = -855307;
            } else {
                fill = hovered ? -14408918 : -15921904;
                border = -14408918;
                text = hovered ? -855307 : -7697773;
}
            AbyssLoginScreen.rect(this.x, this.y, this.x + this.w, this.y + this.h, fill);
            AbyssLoginScreen.outline(this.x, this.y, this.x + this.w, this.y + this.h, border);
            FontRenderer font = AbyssLoginScreen.body();
            font.drawStringWithShadow(this.label, (float)this.x + (float)this.w / 2.0f - font.getWidth(this.label) / 2.0f, (float)this.y + ((float)this.h - AbyssLoginScreen.lineHeight(font)) / 2.0f, text);
}
}
    private static final class Field {
        private final int x;
        private final int y;
        private final int w;
        private final int h;
        private final String placeholder;
        private final int maxLength;
        private final boolean mask;
        private final StringBuilder text = new StringBuilder();
        private int cursor;
        private int scroll;
        private boolean focused;

        private Field(int x, int y, int w2, int h, String placeholder, int maxLength, boolean mask) {
            this.x = x;
            this.y = y;
            this.w = w2;
            this.h = h;
            this.placeholder = placeholder;
            this.maxLength = maxLength;
            this.mask = mask;
}
        private void clear() {
            this.text.setLength(0);
            this.cursor = 0;
            this.scroll = 0;
}
        private boolean contains(int mouseX, int mouseY) {
            return mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h;
}
        private String display() {
            if (!this.mask) {
                return this.text.toString();
}
            StringBuilder out = new StringBuilder(this.text.length());
            for (int i = 0; i < this.text.length(); ++i) {
                out.append('*');
}
            return out.toString();
}
        private void key(char typedChar, int keyCode) {
            if (GuiScreen.isKeyComboCtrlV((int)keyCode)) {
                this.write(GuiScreen.getClipboardString());
                return;
}
            switch (keyCode) {
                case 14: {
                    if (this.cursor > 0) {
                        this.text.deleteCharAt(this.cursor - 1);
                        --this.cursor;
}
                    return;
}
                case 211: {
                    if (this.cursor < this.text.length()) {
                        this.text.deleteCharAt(this.cursor);
}
                    return;
}
                case 203: {
                    if (this.cursor > 0) {
                        --this.cursor;
}
                    return;
}
                case 205: {
                    if (this.cursor < this.text.length()) {
                        ++this.cursor;
}
                    return;
}
                case 199: {
                    this.cursor = 0;
                    return;
}
                case 207: {
                    this.cursor = this.text.length();
                    return;
}
}
            this.write(String.valueOf(typedChar));
}
        private void write(String in) {
            if (in == null) {
                return;
}
            for (int i = 0; i < in.length() && this.text.length() < this.maxLength; ++i) {
                char c = in.charAt(i);
                if (c >= '\u0100' || !ChatAllowedCharacters.isAllowedCharacter((char)c)) continue;
                this.text.insert(this.cursor, c);
                ++this.cursor;
}
}
        private void placeCursor(int mouseX) {
            String shown = this.display();
            float offset = this.x + 5 - mouseX;
            this.cursor = shown.length();
            for (int i = this.scroll; i <= shown.length(); ++i) {
                if (!(offset + AbyssLoginScreen.body().getWidth(shown.substring(this.scroll, i)) >= 0.0f)) continue;
                this.cursor = i;
                break;
}
}
        private void draw() {
            int end;
            AbyssLoginScreen.rect(this.x, this.y, this.x + this.w, this.y + this.h, -15921904);
            AbyssLoginScreen.outline(this.x, this.y, this.x + this.w, this.y + this.h, this.focused ? Theme.pulsingPrimary() : -14408918);
            FontRenderer font = AbyssLoginScreen.body();
            float textY = (float)this.y + ((float)this.h - AbyssLoginScreen.lineHeight(font)) / 2.0f;
            if (this.text.length() == 0 && !this.focused) {
                font.drawString(this.placeholder, this.x + 5, textY, -7697773);
                return;
}
            String shown = this.display();
            float avail = this.w - 10;
            if (this.cursor > shown.length()) {
                this.cursor = shown.length();
}
            if (this.scroll > this.cursor) {
                this.scroll = this.cursor;
}
            while (this.scroll < this.cursor && font.getWidth(shown.substring(this.scroll, this.cursor)) > avail) {
                ++this.scroll;
}
            for (end = shown.length(); end > this.scroll && font.getWidth(shown.substring(this.scroll, end)) > avail; --end) {
}
            font.drawString(shown.substring(this.scroll, end), this.x + 5, textY, -855307);
            if (this.focused && System.currentTimeMillis() / 500L % 2L == 0L) {
                float cx = (float)(this.x + 5) + font.getWidth(shown.substring(this.scroll, Math.min(this.cursor, end)));
                AbyssLoginScreen.rect(cx, this.y + 4, cx + 1.0f, this.y + this.h - 4, -855307);
}
}
}
}