package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
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
        ItemStack receptionCoil = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 1);
        ItemStack transmissionCoil = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 2);
        ItemStack basicFrame = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Frame"), 1, 0);
        ItemStack steamDynamo = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Dynamo"), 1, 0);
        ItemStack energyCell = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Cell"), 1, 1);
        ItemStack multiMeter = new ItemStack(GameRegistry.findItem("ThermalExpansion", "meter"), 1, 0);
        ItemStack strongbox = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Strongbox"), 1, 1);
        ItemStack energisticInfuser = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Machine"), 1, 10);
        ItemStack conductanceCoil = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 3);
        ItemStack servo = new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 0);
        ItemStack tank = new ItemStack(GameRegistry.findItem("ThermalExpansion", "Tank"), 1, 1);

        PowerSystem rf = PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id);
        if(rf != null) {
            Block converterBlock = rf.block;
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 0), true, new Object[]{
                    "CRC",
                    "DFD",
                    "TAT",
                    'C', "gearCopper",
                    'R', receptionCoil,
                    'D', steamDynamo,
                    'F', basicFrame,
                    'T', "gearTin",
                    'A', transmissionCoil
			}));
        }

        if (enableRecipes) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0), true, new Object[]{
                    "CMT",
                    "REA",
                    "TFC",
                    'C', "gearCopper",
                    'M', multiMeter,
                    'T', "gearTin",
                    'R', receptionCoil,
                    'E', energyCell,
                    'A', transmissionCoil,
                    'F', basicFrame
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2), true, new Object[]{
                    "GSG",
                    "SES",
                    "GRG",
                    'G', "gearSilver",
                    'S', strongbox,
                    'E', energisticInfuser,
                    'R', receptionCoil
            }));
            PowerSystem steam = PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id);
            if (steam != null) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam.block, 1, 0), true, new Object[]{
                        "CNT",
                        "PAP",
                        "CFT",
                        'C', "gearCopper",
                        'N', conductanceCoil,
                        'T', "gearTin",
                        'P', servo,
                        'A', tank,
                        'F', basicFrame
                }));
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
