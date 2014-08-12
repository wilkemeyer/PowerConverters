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
import powercrystals.powerconverters.power.systems.PowerSteam;

/**
 * Add recipes for Thermal Expansion.
 */
public class RecipeThermalExpansion extends RecipeProvider {
    private boolean enableRecipes = true;
    public static final String RECIPE_THERMALEXPANSION_CATEGORY = RECIPE_CATEGORY + ".thermalExpansion";

    @Override
    public void registerRecipes() {
        ItemStack copperGear = new ItemStack(GameRegistry.findItem("ThermalFoundation", "material"), 1, 128);
        ItemStack tinGear = new ItemStack(GameRegistry.findItem("ThermalFoundation", "material"), 1, 129);
        ItemStack receptionCoil = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 1);
        ItemStack transmissionCoil = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 2);
        ItemStack basicFrame = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Frame"), 1, 0);
        ItemStack steamDynamo = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Dynamo"), 1, 0);
        ItemStack energyCell = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Cell"), 1, 1);
        ItemStack multiMeter = new ItemStack(GameRegistry.findItem("ThermalExpansion", "meter"), 1, 0);
        ItemStack silverGear = new ItemStack(GameRegistry.findItem("ThermalFoundation", "material"), 1, 130);
        ItemStack strongbox = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Strongbox"), 1, 1);
        ItemStack energisticInfuser = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Machine"), 1, 10);
        ItemStack conductanceCoil = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 3);
        ItemStack servo = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 0);
        ItemStack tank = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Tank"), 1, 1);

        PowerSystem rf = PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id);
        if(rf != null) {
            Block converterBlock = rf.block;
            GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                    "CRC",
                    "DFD",
                    "TAT",
                    'C', copperGear,
                    'R', receptionCoil,
                    'D', steamDynamo,
                    'F', basicFrame,
                    'T', tinGear,
                    'A', transmissionCoil);
        }

        if (enableRecipes) {
            GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0),
                    "CMT",
                    "REA",
                    "TFC",
                    'C', copperGear,
                    'M', multiMeter,
                    'T', tinGear,
                    'R', receptionCoil,
                    'E', energyCell,
                    'A', transmissionCoil,
                    'F', basicFrame
            );
            GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2),
                    "GSG",
                    "SES",
                    "GRG",
                    'G', silverGear,
                    'S', strongbox,
                    'E', energisticInfuser,
                    'R', receptionCoil
            );
            PowerSystem steam = PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id);
            if (steam != null) {
                GameRegistry.addRecipe(new ItemStack(steam.block, 1, 0),
                        "CNT",
                        "PAP",
                        "CFT",
                        'C', copperGear,
                        'N', conductanceCoil,
                        'T', tinGear,
                        'P', servo,
                        'A', tank,
                        'F', basicFrame
                );
            }
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
