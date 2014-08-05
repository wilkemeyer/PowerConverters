package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;

/**
 * Add recipes for Factorization.
 */
public class RecipeFactorization extends RecipeProvider {
    @Override
    public void registerRecipes() {
        try {
            PowerSystem factorization = PowerSystemManager.getInstance().getPowerSystemByName("FZ");
            if(factorization != null) {
                Block converterBlock = factorization.block;
                Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
                GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                        "I I",
                        " B ",
                        "I I",
                        'I', GameRegistry.findItem("minecraft", "gold_ingot"),
                        'B', (Class.forName("factorization.common.Registry").getField("solarboiler_item").get(fzRegistry)));
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    @Override
    public void loadConfig(Configuration c) {
    }

    @Override
    public void saveConfig(Configuration c) {
    }
}
