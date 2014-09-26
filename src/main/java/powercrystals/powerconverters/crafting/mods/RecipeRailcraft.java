package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerSteam;

/**
 * Add recipes for Railcraft.
 */
public class RecipeRailcraft extends RecipeProvider {
    @Override
    public void registerRecipes() {
        ItemStack stackGold = new ItemStack(GameRegistry.findItem("minecraft", "gold_ingot"));
        Object entryGold = PowerConverterCore.tryOreDict("ingotGold", stackGold);

        ItemStack stackIndustrialEngine = GameRegistry.findItemStack("Railcraft", "machine.beta.engine.steam.low", 1);


        PowerSystem steam = PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id);
        if(steam != null) {
            if(entryGold == null || stackIndustrialEngine == null) {
                PowerConverterCore.instance.logger.error("Railcraft recipe is missing items, not adding Power Converters recipe.");
                return;
            }
            Block converterBlockSteam = steam.block;
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlockSteam, 1, 0), true,
                    // Steam consumer
                    "G G",
                    " E ",
                    "G G",
                    'G', entryGold,
                    'E', stackIndustrialEngine));
        }
    }

    @Override
    public void loadConfig(Configuration c) {
    }

    @Override
    public void saveConfig(Configuration c) {
    }
}
