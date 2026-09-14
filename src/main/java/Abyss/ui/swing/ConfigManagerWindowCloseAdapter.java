/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.swing;

import Abyss.ui.swing.ConfigManagerWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ConfigManagerWindowCloseAdapter
extends WindowAdapter {
    private static final long final ConfigManagerWindow W;

    @Override
    public void windowClosing(WindowEvent var1) {
        ConfigManagerWindow.S(this.W, 0L);
}
    ConfigManagerWindowCloseAdapter(ConfigManagerWindow var1) {
        this.W = var1;
}
}