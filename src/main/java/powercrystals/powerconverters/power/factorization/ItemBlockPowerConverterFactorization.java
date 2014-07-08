package powercrystals.powerconverters.power.factorization;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 3/03/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemBlockPowerConverterFactorization extends ItemBlock {
    public ItemBlockPowerConverterFactorization(Block block) {
        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int md = itemstack.getItemDamage();
        if (md == 0) return "powerconverters.factorization.consumer";
        if (md == 1) return "powerconverters.factorization.producer";
        return "unknown";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes) {
        for (int i = 0; i <= 1; i++) {
            subTypes.add(new ItemStack(item, 1, i));
        }
    }
}
