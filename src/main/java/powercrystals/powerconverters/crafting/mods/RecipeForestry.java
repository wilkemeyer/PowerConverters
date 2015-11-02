package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerRedstoneFlux;

/**
 * Add recipes for Forestry
 */
public class RecipeForestry extends RecipeProvider {
    private boolean enableRecipes = true;
    public static final String RECIPE_FORESTRY_CATEGORY = RECIPE_CATEGORY + ".forestry";

    @Override
    public void registerRecipes() {
        ItemStack bronzeGear = new ItemStack(GameRegistry.findItem("Forestry", "gearBronze"), 1, 0);
        ItemStack circuitBoard = new ItemStack(GameRegistry.findItem("Forestry", "chipsets"), 1, 3);
        ItemStack engine = new ItemStack(GameRegistry.findItem("Forestry", "engine"), 1, 2);
        ItemStack tubes = new ItemStack(GameRegistry.findItem("Forestry", "thermonicTubes"), 1, 5);
        ItemStack chest = new ItemStack(Blocks.chest, 1, 0);

        PowerSystem rf = PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id);
        if(rf != null) {
            Block converterBlock = rf.block;
            GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                    "TGT",
                    "EBE",
                    "TGT",
                    'G', bronzeGear,
                    'B', circuitBoard,
                    'E', engine,
                    'T', tubes);
        }

        if (enableRecipes) {
            GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0),
                    "TBT",
                    "BBB",
                    "TBT",
                    'T', tubes,
                    'B', circuitBoard
            );
            GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2),
                    "TBT",
                    "BCB",
                    "TBT",
                    'T', tubes,
                    'B', circuitBoard,
                    'C', chest
            );
        }
    }

    @Override
    public void loadConfig(Configuration c) {
        enableRecipes = c.get(RECIPE_FORESTRY_CATEGORY, "enableRecipes", true).getBoolean(true);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(RECIPE_FORESTRY_CATEGORY).get("enableRecipes").set(enableRecipes);
    }
}
