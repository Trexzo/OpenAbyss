/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 */
package Abyss.util.render.abyss;

import Abyss.util.render.ShaderProgram;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public final class AbyssShaderUtils {
    private static ShaderProgram gradientRoundedShader;
    private static final String FRAG_SOURCE = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec4 createGradient(vec2 coords, vec4 c1, vec4 c2, vec4 c3, vec4 c4){\n    vec4 color = mix(mix(c1, c2, coords.y), mix(c3, c4, coords.y), coords.x);\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    float smoothedAlpha = (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius)));\n    vec4 gradient = createGradient(st, color1, color2, color3, color4);\n    gl_FragColor = vec4(gradient.rgb, gradient.a * smoothedAlpha);\n}";

    private AbyssShaderUtils() {
}
    private static ShaderProgram getShader() {
        if (gradientRoundedShader == null) {
            gradientRoundedShader = new ShaderProgram(13990146093428L, FRAG_SOURCE);
}
        return gradientRoundedShader;
}
    public static void drawGradientRound(float x, float y, float w2, float h, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179141_d();
        GlStateManager.func_179092_a((int)516, (float)0.0f);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ShaderProgram shader = AbyssShaderUtils.getShader();
        shader.r();
        ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
        int factor = sr.func_78325_e();
        shader.O("location", x * (float)factor, (float)Minecraft.func_71410_x().field_71440_d - h * (float)factor - y * (float)factor);
        shader.O("rectSize", w2 * (float)factor, h * (float)factor);
        shader.O("radius", radius * (float)factor);
        shader.O("color1", (float)topLeft.getRed() / 255.0f, (float)topLeft.getGreen() / 255.0f, (float)topLeft.getBlue() / 255.0f, (float)topLeft.getAlpha() / 255.0f);
        shader.O("color2", (float)bottomLeft.getRed() / 255.0f, (float)bottomLeft.getGreen() / 255.0f, (float)bottomLeft.getBlue() / 255.0f, (float)bottomLeft.getAlpha() / 255.0f);
        shader.O("color3", (float)topRight.getRed() / 255.0f, (float)topRight.getGreen() / 255.0f, (float)topRight.getBlue() / 255.0f, (float)topRight.getAlpha() / 255.0f);
        shader.O("color4", (float)bottomRight.getRed() / 255.0f, (float)bottomRight.getGreen() / 255.0f, (float)bottomRight.getBlue() / 255.0f, (float)bottomRight.getAlpha() / 255.0f);
        ShaderProgram.p(x - 1.0f, y - 1.0f, w2 + 2.0f, h + 2.0f);
        shader.P();
        GlStateManager.func_179084_k();
}
    public static void drawGradientHorizontal(float x, float y, float w2, float h, float radius, Color left, Color right) {
        AbyssShaderUtils.drawGradientRound(x, y, w2, h, radius, left, left, right, right);
}
}