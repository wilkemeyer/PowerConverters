package powercrystals.powerconverters.power.factorization;

import net.minecraft.creativetab.CreativeTabs;
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
public class ItemBlockPowerConverterFactorization extends ItemBlock
{
    public ItemBlockPowerConverterFactorization(int id)
    {
        super(id);
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
    public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes) {
        for (int i = 0; i <= 1; i++) {
            subTypes.add(new ItemStack(itemId, 1, i));
        }
    }
}
