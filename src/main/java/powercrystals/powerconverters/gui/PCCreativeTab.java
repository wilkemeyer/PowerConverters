package powercrystals.powerconverters.gui;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PCCreativeTab extends CreativeTabs {
    public static final PCCreativeTab tab = new PCCreativeTab();

    public PCCreativeTab() {
        super("Power Converters");
    }

    @Override
    public Item getTabIconItem() {
        return GameRegistry.findItem("PowerConverters", "converter.common");
    }

    @Override
    public String getTranslatedTabLabel() {
        return this.getTabLabel();
    }
}
