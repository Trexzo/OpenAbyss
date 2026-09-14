/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.ui.screen;

import Abyss.ui.screen.AccessTokenLoginScreen;
import Abyss.ui.screen.AccountManagerScreen;
import Abyss.ui.screen.CookieLoginScreen;
import Abyss.ui.screen.MicrosoftLoginScreen;
import Abyss.ui.screen.OfflineLoginScreen;
import Abyss.ui.screen.RefreshTokenLoginScreen;
import Abyss.util.Sneaky;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class AddAccountScreen
extends GuiScreen {
    private final GuiScreen s;
    
    public void func_146281_b() {
}
    static void $jnicClinit() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
}
    public GuiScreen j() {
        return this.s;
}
    protected void func_146284_a(GuiButton var1) {
        try {
            long var2 = 35240121263301L;
            if (var1 != null) {
                switch (var1.field_146127_k) {
                    case 0: {
                        this.field_146297_k.func_147108_a((GuiScreen)new MicrosoftLoginScreen(this.s));
                        break;
}
                    case 1: {
                        this.field_146297_k.func_147108_a((GuiScreen)new CookieLoginScreen(this.s, 80809518142778L));
                        break;
}
                    case 2: {
                        this.field_146297_k.func_147108_a((GuiScreen)new OfflineLoginScreen(this.s));
                        break;
}
                    case 3: {
                        this.field_146297_k.func_147108_a((GuiScreen)new AccessTokenLoginScreen(this.s, 53839872381595L));
                        break;
}
                    case 4: {
                        this.field_146297_k.func_147108_a((GuiScreen)new AccountManagerScreen(81800336346822L, this.s));
                        break;
}
                    case 5: {
                        this.field_146297_k.func_147108_a((GuiScreen)new RefreshTokenLoginScreen(this.s, 81544777000287L));
}
}
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public void func_73863_a(int var1, int var2, float var3) {
        try {
            long var4 = 48433265682448L;
            this.func_146276_q_();
            this.func_73732_a(this.field_146289_q, "Choose Account Type to Add", this.field_146294_l / 2, this.field_146295_m / 2 - 70, 0xFFFFFF);
            super.func_73863_a(var1, var2, var3);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public AddAccountScreen(GuiScreen var1) {
        this.s = var1;
}
    public void func_73866_w_() {
        try {
            long var1 = 59250142702415L;
            this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 2 - 45, 200, 20, "Microsoft"));
            this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 2 - 20, 200, 20, "Cookie"));
            this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 5, 200, 20, "Offline"));
            this.field_146292_n.add(new GuiButton(3, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 30, 200, 20, "Access Token"));
            this.field_146292_n.add(new GuiButton(5, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 55, 200, 20, "Refresh Token"));
            this.field_146292_n.add(new GuiButton(4, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 80, 200, 20, "Back"));
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    static {
        try {
            AddAccountScreen.$jnicClinit();
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
}
}