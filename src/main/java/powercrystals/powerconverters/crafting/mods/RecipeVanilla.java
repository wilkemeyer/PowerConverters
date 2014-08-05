package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.crafting.RecipeProvider;

/**
 * Add Vanilla recipes.
 */
public class RecipeVanilla extends RecipeProvider {
    private boolean enableRecipes = true;
    public static final String RECIPE_VANILLA_CATEGORY = RECIPE_CATEGORY + ".vanilla";

    @Override
    public void registerRecipes() {
        //To change body of implemented methods use File | Settings | File Templates.
        if(enableRecipes) {
            ItemStack stackGold = new ItemStack(GameRegistry.findItem("minecraft", "gold_ingot"));
            ItemStack stackRedstone = new ItemStack(GameRegistry.findItem("minecraft", "redstone"));
            ItemStack stackIron = new ItemStack(GameRegistry.findItem("minecraft", "iron_ingot"));
            ItemStack stackGlass = new ItemStack(GameRegistry.findBlock("minecraft", "glass"));
            ItemStack stackDiamond = new ItemStack(GameRegistry.findItem("minecraft", "diamond"));

            Object entryGold = PowerConverterCore.tryOreDict("ingotGold", stackGold);
            Object entryRedstone = PowerConverterCore.tryOreDict("dustRedstone", stackRedstone);
            Object entryIron = PowerConverterCore.tryOreDict("ingotIron", stackIron);
            Object entryGlass = PowerConverterCore.tryOreDict("blockGlass", stackGlass);
            Object entryDiamond = PowerConverterCore.tryOreDict("gemDiamond", stackDiamond);
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0), true, new Object[]{
                    // Energy Bridge
                    "GRG",
                    "LDL",
                    "GRG",
                    'R', entryRedstone,
                    'G', entryGold,
                    'L', entryGlass,
                    'D', entryDiamond
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2), true, new Object[]{
                    // Universal charger
                    "GRG",
                    "ICI",
                    "GRG",
                    'R', entryRedstone,
                    'G', entryGold,
                    'I', entryIron,
                    'C', Blocks.chest
            }));
        }
    }

    @Override
    public void loadConfig(Configuration c) {
        enableRecipes = c.get(RECIPE_VANILLA_CATEGORY, "enableRecipes", true).getBoolean(true);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(RECIPE_VANILLA_CATEGORY).get("enableRecipes").set(enableRecipes);
    }
}
