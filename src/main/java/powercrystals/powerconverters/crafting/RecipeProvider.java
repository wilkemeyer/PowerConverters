package powercrystals.powerconverters.crafting;

import net.minecraftforge.common.config.Configuration;

/**
 * Basic class to manage mods that add recipe additions by mods.
 */
public abstract class RecipeProvider {
    public static final String RECIPE_CATEGORY = "recipes";

    public abstract void registerRecipes();
    public abstract void loadConfig(Configuration c);
    public abstract void saveConfig(Configuration c);
}
