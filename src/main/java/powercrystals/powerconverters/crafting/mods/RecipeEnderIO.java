package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerRedstoneFlux;

/**
 * Add recipes for EnderIO
 */
public class RecipeEnderIO extends RecipeProvider{
    @Override
    public void registerRecipes() {
        //To change body of implemented methods use File | Settings | File Templates.
        PowerSystem rf = PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id);
        if(rf != null) {
            Block converterBlock = rf.block;            
            
            //
            // Support both, new and old Capacitor bank
            // (where the new is the most prefered one)
            //
            Item capBank = GameRegistry.findItem("EnderIO", "blockCapBank");
            ItemStack stackCapacitorBank;
            if(capBank != null) {
	        	// New Style Capacitor Bank
	            stackCapacitorBank = new ItemStack(capBank, 1, 2);	// Basic -> :1,  Normal -> :2, Vibrant -> :3

    		} else {
    			// Old Capacitor Bank
    			stackCapacitorBank = GameRegistry.findItemStack("EnderIO", "blockCapacitorBank", 1);
	    		
    		}
    		
    		        
            GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                    "G G",
                    " T ",
                    "G G",
                    'G', GameRegistry.findItem("minecraft", "gold_ingot"),
                    'T', stackCapacitorBank
            );
        }
    }

    @Override
    public void loadConfig(Configuration c) {
    }

    @Override
    public void saveConfig(Configuration c) {
    }
}
