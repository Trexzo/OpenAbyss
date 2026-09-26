/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package Abyss.util.render.abyss;

import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.OGLUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TrueTypeFontRenderer
implements FontRenderer {
    private static final char COLOR_INVOKER = '\u00a7';
    private static final Random RANDOM = new Random();
    private static final int ATLAS_SCALE = 2;
    private static final float RENDER_SCALE = 0.25f;
    private final Font font;
    public final CharacterData[] charData = new CharacterData[256];
    private final int[] colorCodes = new int[32];
    private final int margin;
    private final boolean antiAlias;
    private final boolean fracMetrics;

    public TrueTypeFontRenderer(Font font, boolean antiAlias, boolean fracMetrics) {
        this.generateColors();
        this.font = font.deriveFont(font.getSize2D() * 2.0f);
        this.margin = 12;
        this.antiAlias = antiAlias;
        this.fracMetrics = fracMetrics;
}
    @Override
    public int drawString(String text, float x, float y, int color) {
        if (!FontManager.isReady()) {
            return 0;
}
        this.renderString(text, x, y, color, false);
        return 0;
}
    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        if (!FontManager.isReady()) {
            return 0;
}
        double s = 0.5;
        GL11.glTranslated((double)s, (double)s, (double)0.0);
        this.renderString(text, x, y, color, true);
        GL11.glTranslated((double)(-s), (double)(-s), (double)0.0);
        this.renderString(text, x, y, color, false);
        return 0;
}
    @Override
    public float getWidth(String text) {
        if (!FontManager.isReady()) {
            return 0.0f;
}
        if (text == null || text.length() == 0) {
            return 0.0f;
}
        float width = 0.0f;
        CharacterData[] characterData = this.charData;
        int length = text.length();
        for (int i = 0; i < length; ++i) {
            CharacterData charData;
            char character = text.charAt(i);
            if (character == '\u00a7' || (i > 0 ? (int)text.charAt(i - 1) : 46) == 167 || !this.isValid(character) || (charData = characterData[character]) == null) continue;
            width += (charData.width - (float)(2 * this.margin)) * 0.25f;
}
        return width;
}
    @Override
    public float getHeight(String text) {
        if (!FontManager.isReady()) {
            return 0.0f;
}
        if (text == null || text.length() == 0) {
            return 0.0f;
}
        float height = 0.0f;
        CharacterData[] characterData = this.charData;
        int length = text.length();
        for (int i = 0; i < length; ++i) {
            CharacterData charData;
            char character = text.charAt(i);
            if ((i > 0 ? (int)text.charAt(i - 1) : 46) == 167 || character == '\u00a7' || !this.isValid(character) || (charData = characterData[character]) == null) continue;
            height = Math.max(height, charData.height);
}
        return (height - (float)this.margin) * 0.25f;
}
    public void generateTextures() {
        for (int i = 0; i < 256; ++i) {
            char c = (char)i;
            if (!this.isValid(c) || this.charData[i] != null) continue;
            this.setup(c);
}
}
    public void generateSingleGlyph(int index) {
        char c = (char)index;
        if (this.isValid(c) && this.charData[index] == null) {
            this.setup(c);
}
}
    private void setup(char character) {
        BufferedImage utilityImage = new BufferedImage(1, 1, 2);
        Graphics2D utilityGraphics = (Graphics2D)utilityImage.getGraphics();
        utilityGraphics.setFont(this.font);
        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();
        Rectangle2D characterBounds = fontMetrics.getStringBounds(String.valueOf(character), utilityGraphics);
        BufferedImage characterImage = new BufferedImage((int)StrictMath.ceil(characterBounds.getWidth() + (double)(2 * this.margin)), (int)StrictMath.ceil(characterBounds.getHeight()), 2);
        Graphics2D graphics = (Graphics2D)characterImage.getGraphics();
        graphics.setFont(this.font);
        graphics.setColor(new Color(255, 255, 255, 0));
        graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
        graphics.setColor(Color.WHITE);
        if (this.antiAlias) {
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
}
        if (this.fracMetrics) {
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
}
        graphics.drawString(String.valueOf(character), this.margin, fontMetrics.getAscent());
        int textureId = GL11.glGenTextures();
        this.createTexture(textureId, characterImage);
        this.charData[character] = new CharacterData(characterImage.getWidth(), characterImage.getHeight(), textureId);
}
    private void createTexture(int textureId, BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer((int)(image.getWidth() * image.getHeight() * 4));
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 0xFF));
                buffer.put((byte)(pixel >> 8 & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)(pixel >> 24 & 0xFF));
}
}
        buffer.flip();
        GlStateManager.bindTexture((int)textureId);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)image.getWidth(), (int)image.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)buffer);
}
    private void renderString(CharSequence text, float x, float y, int color, boolean shadow) {
        if (text == null || text.length() == 0) {
            return;
}
        GL11.glPushMatrix();
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
}
        if (color == 0x20FFFFFF) {
            color = -5263441;
}
        GL11.glScaled((double)0.25, (double)0.25, (double)1.0);
        x -= (float)this.margin * 0.25f;
        y -= 2.0f;
        x /= 0.25f;
        y /= 0.25f;
        CharacterData[] characterData = this.charData;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        int length = text.length();
        float multiplier = shadow ? 4 : 1;
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        OGLUtils.enableBlending();
        GlStateManager.color((float)(r2 / multiplier), (float)(g / multiplier), (float)(b / multiplier), (float)a);
        for (int i = 0; i < length; ++i) {
            CharacterData charData;
            int previous;
            char character = text.charAt(i);
            int n2 = previous = i > 0 ? (int)text.charAt(i - 1) : 46;
            if (previous == 167) continue;
            if (character == '\u00a7') {
                if (i + 1 >= length) break;
                int index = "0123456789ABCDEFKLMNOR".indexOf(Character.toUpperCase(text.charAt(i + 1)));
                if (index < 16) {
                    obfuscated = false;
                    strikethrough = false;
                    underlined = false;
                    characterData = this.charData;
                    if (index < 0) {
                        index = 15;
}
                    if (shadow) {
                        index += 16;
}
                    int textColor = this.colorCodes[index];
                    GlStateManager.color((float)((float)(textColor >> 16) / 255.0f), (float)((float)(textColor >> 8 & 0xFF) / 255.0f), (float)((float)(textColor & 0xFF) / 255.0f), (float)a);
                    continue;
}
                if (index == 16) {
                    obfuscated = true;
                    continue;
}
                if (index == 18) {
                    strikethrough = true;
                    continue;
}
                if (index == 19) {
                    underlined = true;
                    continue;
}
                obfuscated = false;
                strikethrough = false;
                underlined = false;
                characterData = this.charData;
                GlStateManager.color((float)(1.0f / multiplier), (float)(1.0f / multiplier), (float)(1.0f / multiplier), (float)a);
                continue;
}
            if (!this.isValid(character)) continue;
            if (obfuscated) {
                character = (char)(33 + RANDOM.nextInt(94));
}
            if ((charData = characterData[character]) == null) continue;
            this.drawChar(charData, x, y);
            if (strikethrough) {
                this.drawLine(0.0f, charData.height / 2.0f, charData.width, charData.height / 2.0f, 3.0f);
}
            if (underlined) {
                this.drawLine(0.0f, charData.height - 15.0f, charData.width, charData.height - 15.0f, 3.0f);
}
            x += charData.width - (float)(2 * this.margin);
}
        GlStateManager.bindTexture((int)0);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GL11.glPopMatrix();
}
    private boolean isValid(char c) {
        return c > '\n' && c < '\u0100' && c != '\u007f';
}
    public void drawChar(CharacterData characterData, float x, float y) {
        characterData.bind();
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)x, (double)(y + characterData.height));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)(x + characterData.width), (double)(y + characterData.height));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2d((double)(x + characterData.width), (double)y);
        GL11.glEnd();
}
    private void drawLine(float x, float y, float x2, float y2, float width) {
        GlStateManager.disableTexture2D();
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)1);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
}
    private void generateColors() {
        for (int i = 0; i < 32; ++i) {
            int thingy = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + thingy;
            int green = (i >> 1 & 1) * 170 + thingy;
            int blue = (i & 1) * 170 + thingy;
            if (i == 6) {
                red += 85;
}
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
}
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
}
}
    public static class CharacterData {
        private final int textureId;
        public float width;
        public float height;

        private CharacterData(float width, float height, int textureId) {
            this.width = width;
            this.height = height;
            this.textureId = textureId;
}
        public void bind() {
            GlStateManager.bindTexture((int)this.textureId);
}
}
}