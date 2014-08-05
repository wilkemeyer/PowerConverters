package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;

/**
 * Add recipes for Buildcraft
 */
public class RecipeBuildCraft extends RecipeProvider {
    private boolean enableRecipes = true;
    public static final String RECIPE_BUILDCRAFT_CATEGORY = RECIPE_CATEGORY + ".bc";

    @Override
    public void registerRecipes() {
        try {
            ItemStack redstoneBlock = new ItemStack(GameRegistry.findBlock("minecraft", "redstone_block"), 1);

            Item cable = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipepowergold");
            Item struct = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipestructurecobblestone");
            Item conduit = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipePowerDiamond");
            Item fluid = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipeFluidsGold");

            Block chest = GameRegistry.findBlock("Buildcraft|Transport", "filteredBufferBlock");
            Block engine = GameRegistry.findBlock("Buildcraft|Energy", "engineBlock");
            Block pump = GameRegistry.findBlock("Buildcraft|Factory", "pumpBlock");

            ItemStack gear = new ItemStack(GameRegistry.findItem("Buildcraft|Core", "goldGearItem"), 1, 1);

            if (enableRecipes) {
                GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0),
                        "PPP",
                        "PBP",
                        "PPP",
                        'B', redstoneBlock,
                        'P', conduit
                );
                GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2),
                        "TGT",
                        "#S#",
                        "T#T",
                        'T', cable,
                        'S', chest,
                        '#', engine,
                        'G', gear
                );
                if (PowerConverterCore.powerSystemSteamEnabled) {
                    GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockSteam, 1, 0),
                            "GSG",
                            "SES",
                            "GSG",
                            'G', fluid,
                            'S', struct,
                            'E', pump
                    );
                }
            }

            PowerSystem buildcraft = PowerSystemManager.getInstance().getPowerSystemByName("BC");
            if(buildcraft != null) {
                Block converterBlock = buildcraft.block;
                GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                        "GSG",
                        "SES",
                        "GSG",
                        'G', cable,
                        'S', struct,
                        'E', gear
                );
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    @Override
    public void loadConfig(Configuration c) {
        enableRecipes = c.get(RECIPE_BUILDCRAFT_CATEGORY, "enableRecipes", true).getBoolean(true);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(RECIPE_BUILDCRAFT_CATEGORY).get("enableRecipes").set(enableRecipes);
    }
}
