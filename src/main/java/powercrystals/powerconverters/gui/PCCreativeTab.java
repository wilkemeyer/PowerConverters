package powercrystals.powerconverters.gui;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.BlockPowerConverterCommon;

public class PCCreativeTab extends CreativeTabs {
    public static final PCCreativeTab tab = new PCCreativeTab();

    public PCCreativeTab() {
        super("Power Converters");
    }

    @Override
    public Item getTabIconItem() {
        return GameRegistry.findItem("powerconverters", "powerconverters.common");
    }

    @Override
    public String getTranslatedTabLabel() {
        return this.getTabLabel();
    }
}
