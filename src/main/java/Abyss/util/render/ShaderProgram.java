/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL20
 */
package Abyss.util.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {
    private static long a;

    public final int S;

    public void P() {
        GL20.glUseProgram((int)0);
}
    private static int T(String var2, int var3) {
        int var4 = GL20.glCreateShader((int)var3);
        GL20.glShaderSource((int)var4, (CharSequence)var2);
        GL20.glCompileShader((int)var4);
        if (GL20.glGetShaderi((int)var4, (int)35713) == 0) {
            throw new IllegalStateException("Shader failed to compile: " + GL20.glGetShaderInfoLog((int)var4, (int)4096));
}
        return var4;
}
    public ShaderProgram(long var1, String var3) {
        var1 = a ^ var1;
        int var6 = GL20.glCreateProgram();
        int var7 = ShaderProgram.T(var3, 35633);
        int var8 = ShaderProgram.T("#version 120\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}\n", 35632);
        GL20.glAttachShader((int)var6, (int)var7);
        GL20.glAttachShader((int)var6, (int)var8);
        GL20.glLinkProgram((int)var6);
        if (GL20.glGetProgrami((int)var6, (int)35713) == 0) {
            throw new IllegalStateException("Shader failed to link: " + GL20.glGetProgramInfoLog((int)var6, (int)7));
}
        this.S = var6;
}
    public void O(String var1, float ... var2) {
        int var3 = GL20.glGetUniformLocation((int)this.S, (CharSequence)var1);
        if (var3 != -1) {
            switch (var2.length) {
                case 1: {
                    GL20.glUniform1f((int)var3, (float)var2[0]);
                    break;
}
                case 2: {
                    GL20.glUniform2f((int)var3, (float)var2[0], (float)var2[1]);
                    break;
}
                case 3: {
                    GL20.glUniform3f((int)var3, (float)var2[0], (float)var2[1], (float)var2[2]);
                    break;
}
                case 4: {
                    GL20.glUniform4f((int)var3, (float)var2[0], (float)var2[1], (float)var2[2], (float)var2[3]);
}
}
}
}
    public static void p(float var2, float var3, float var4, float var5) {
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2f((float)var2, (float)var3);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2f((float)var2, (float)(var3 + var5));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2f((float)(var2 + var4), (float)(var3 + var5));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2f((float)(var2 + var4), (float)var3);
        GL11.glEnd();
}
    public void r() {
        GL20.glUseProgram((int)this.S);
}
    static {
        a = 13990146093428L;
    }
}