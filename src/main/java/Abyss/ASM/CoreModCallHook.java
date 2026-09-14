/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.IFMLCallHook
 */
package Abyss.ASM;

import Abyss.ASM.TransformerOrdering;
import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

public class CoreModCallHook
implements IFMLCallHook {
    public Void call() {
        TransformerOrdering.E();
        TransformerOrdering.Q();
        return null;
}
    public void injectData(Map<String, Object> var1) {
}
}