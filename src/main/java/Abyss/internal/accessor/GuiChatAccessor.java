/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiTextField
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.FieldAccessors;
import Abyss.internal.accessor.TypedValueStore;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;

public final class GuiChatAccessor {
    private static TypedValueStore T = FieldAccessors.X(GuiChat.class, "inputField", "inputField");

    public static GuiTextField z(char var0, char var1, int var2, GuiChat var3) {
        return (GuiTextField)T.v(var3);
}
}