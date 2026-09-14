/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin$MCVersion
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin$SortingIndex
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin$TransformerExclusions
 */
package Abyss.ASM;

import Abyss.ASM.ClassNameFilterTransformer;
import Abyss.ASM.CoreModCallHook;
import Abyss.ASM.EncryptedClassMarkerHook;
import Abyss.ASM.TransformerOrdering;
import Abyss.ASM.Util.AsmUtil;
import Abyss.util.Sneaky;
import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(value="1.8.9")
@IFMLLoadingPlugin.TransformerExclusions(value={"Abyss.ASM"})
@IFMLLoadingPlugin.SortingIndex(value=0x7FFFFFFF)
public class CoreMod
implements IFMLLoadingPlugin {
    public String getSetupClass() {
        return CoreModCallHook.class.getName();
}
    public void injectData(Map<String, Object> var1) {
        Object var2 = var1.get("runtimeDeobfuscationEnabled");
        AsmUtil.O(var2 instanceof Boolean && (Boolean)var2 != false);
}
    public String getModContainerClass() {
        return null;
}
    public String[] getASMTransformerClass() {
        try {
            TransformerOrdering.L();
            return new String[]{EncryptedClassMarkerHook.class.getName(), ClassNameFilterTransformer.class.getName()};
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public String getAccessTransformerClass() {
        return null;
}
}