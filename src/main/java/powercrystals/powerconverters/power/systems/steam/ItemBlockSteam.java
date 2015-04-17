package powercrystals.powerconverters.power.systems.steam;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerSteam;

import java.util.List;

public class ItemBlockSteam extends ItemBlock {
    PowerSteam powerSteam;
    public ItemBlockSteam(Block block) {
        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
        powerSteam = (PowerSteam) PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int md = itemstack.getItemDamage();
        if (md == 0) return "powerconverters.steam.consumer";
        else return "powerconverters.steam.producer";
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        if(itemStack.getItemDamage() == 0) {
            return super.getItemStackDisplayName(itemStack);
        }
        else {
            PowerSteam.SteamType steamType = powerSteam.getSteamType(itemStack.getItemDamage() - 1);
            return StatCollector.translateToLocalFormatted(itemStack.getUnlocalizedName() + ".name", steamType.displayName);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes) {
        for (int i = 0; i <= powerSteam.getSteamTypeCount(); i++) {
            subTypes.add(new ItemStack(item, 1, i));
        }
    }
}
