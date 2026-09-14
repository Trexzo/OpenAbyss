/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.swing;

import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.ui.swing.ConfigManagerBindPanel;
import java.awt.GridBagConstraints;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;

public class ConfigManagerWindow {
    public static List<String> D;
    private static Module M;
        private final JButton[] k = new JButton[]{null};
    private final JButton[] t = new JButton[]{null};
    private boolean d = (ConfigManagerWindow.b(5772, 0L) & 1) != 0;
    private JFrame R;
    private JLabel E;
        private String F = "";
            
    
    private ConfigManagerBindPanel J;

    private native JPanel K(long var1);

    private static native int b(int var0, long var1);

    private native void h(Setting var1, JPanel var2, long var3, GridBagConstraints var5, Map var6);

    private native JPanel W(long var1, char var3);

    static native void S(ConfigManagerWindow var0, long var1);

    private native void W(int var1, char var2, String var3, int var4) throws Exception;

    private native void P(JList<Object> var1, Module[] var2, JPanel var3, Map<Setting, JLabel> var4, Map<Setting, JComponent> var5, JLabel[] var6, JLabel[] var7, GridBagConstraints var8, JScrollPane var9);

    private native void h(JSlider var1, boolean[] var2, int var3, float var4, JSpinner var5, NumberSetting var6, float var7, float var8);

    private native void z(long var1, JList var3, Module[] var4, JLabel[] var5, JLabel[] var6, Map var7, Map var8, byte var9);

    static native Module K(Module var0);

    static native Module p();

    public ConfigManagerWindow(long var1) {
        this.z();
}
    static native boolean P(ConfigManagerWindow var0, boolean var1);

    static native void r(ConfigManagerWindow var0, long var1, JButton var3, Module var4);

    private native JPanel A(int var1, int var2, int var3);

    private native void V(JSlider var1, boolean[] var2, int var3, double var4, double var6, JSpinner var8, double var9, PercentageSetting var11);

    private native String A(char var1, int var2);

    private native int V(long var1, double var3);

    private native void z();

    private native void n(long var1);

    private native void n(JButton var1, JList<Object> var2, Module[] var3, JLabel[] var4, JLabel[] var5, Map<Setting, JLabel> var6, Map<Setting, JComponent> var7, JPanel var8);
}