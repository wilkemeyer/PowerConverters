package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerRedstoneFlux;

/**
 * Add recipes for Thermal Expansion.
 */
public class RecipeThermalExpansion extends RecipeProvider {
    private boolean enableRecipes = true;
    public static final String RECIPE_THERMALEXPANSION_CATEGORY = RECIPE_CATEGORY + ".thermalExpansion";

    @Override
    public void registerRecipes() {
        ItemStack cell = GameRegistry.findItemStack("ThermalExpansion", "cellBasic", 1);
        ItemStack conduit = GameRegistry.findItemStack("ThermalExpansion", "conduitEnergyBasic", 1);
        ItemStack transmit = GameRegistry.findItemStack("ThermalExpansion", "powerCoilSilver", 1);
        ItemStack recieve = GameRegistry.findItemStack("ThermalExpansion", "powerCoilGold", 1);

        ItemStack fluid = GameRegistry.findItemStack("ThermalExpansion", "conduitFluidOpaque", 1);
        ItemStack tank = GameRegistry.findItemStack("ThermalExpansion", "tankBasic", 1);
        ItemStack frame = GameRegistry.findItemStack("ThermalExpansion", "machineFrame", 1);

        ItemStack storage = GameRegistry.findItemStack("ThermalExpansion", "cellBasic", 1);
        ItemStack charger = GameRegistry.findItemStack("ThermalExpansion", "charger", 1);
        ItemStack hconduit = GameRegistry.findItemStack("ThermalExpansion", "conduitEnergyHardened", 1);

        PowerSystem rf = PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id);
        if(rf != null) {
            Block converterBlock = rf.block;
            GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0), "CTC", "RSR", "CTC", 'S', cell, 'C', conduit, 'R', recieve, 'T', transmit);
        }

        if (enableRecipes) {
            GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0),
                    " T ",
                    "SDS",
                    " T ",
                    'T', transmit,
                    'S', storage,
                    'D', GameRegistry.findItem("minecraft", "diamond")
            );
            GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2),
                    "T#T",
                    "CSC",
                    "TCT",
                    'T', transmit,
                    'C', hconduit,
                    'S', GameRegistry.findBlock("minecraft", "chest"),
                    '#', charger
            );
            if (PowerConverterCore.powerSystemSteamEnabled)
                GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockSteam, 1, 0),
                        "CTC",
                        "TST",
                        "CTC",
                        'S', tank,
                        'C', fluid,
                        'T', frame
                );
        }
    }

    @Override
    public void loadConfig(Configuration c) {
        enableRecipes = c.get(RECIPE_THERMALEXPANSION_CATEGORY, "enableRecipes", true).getBoolean(true);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(RECIPE_THERMALEXPANSION_CATEGORY).get("enableRecipes").set(enableRecipes);
    }
}
