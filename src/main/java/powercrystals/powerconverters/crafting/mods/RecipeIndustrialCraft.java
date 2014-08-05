package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;

/**
 * Add recipes for IC2
 */
public class RecipeIndustrialCraft extends RecipeProvider {
    private boolean enableRecipes = true;
    public static final String RECIPE_INDUSTRIALCRAFT_CATEGORY = RECIPE_CATEGORY + ".ic2";

    @Override
    public void registerRecipes() {
        ItemStack fluid = IC2Items.getItem("ejectorUpgrade");
        ItemStack storage = IC2Items.getItem("batBox");
        ItemStack cable = IC2Items.getItem("insulatedGoldCableItem");
        Object tin = PowerConverterCore.tryOreDict("plateTin", IC2Items.getItem("platetin"));
        Object bronze = PowerConverterCore.tryOreDict("plateBronze", IC2Items.getItem("platebronze"));
        ItemStack charger = IC2Items.getItem("RTGenerator");
        ItemStack transmit = IC2Items.getItem("insulatedIronCableItem");
        if (enableRecipes) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0), true, new Object[]{
                    "CTC",
                    "SDS",
                    "CTC",
                    'C', cable,
                    'T', tin,
                    'S', storage,
                    'D', GameRegistry.findItem("minecraft", "diamond")
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2), true, new Object[]{
                    "T#T",
                    "CSC",
                    "TCT",
                    'T', transmit,
                    'C', cable,
                    'S', GameRegistry.findItem("minecraft", "chest"),
                    '#', charger
            }));
            if (PowerConverterCore.powerSystemSteamEnabled) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockSteam, 1, 0), true, new Object[]{
                        "CPC",
                        "PTP",
                        "CPC",
                        'C', IC2Items.getItem("FluidCell"),
                        'P', bronze,
                        'T', fluid
                }));
            }
        }

        PowerSystem ic2 = PowerSystemManager.getInstance().getPowerSystemByName("ic2");
        if(ic2 != null) {
            Block converterBlock = ic2.block;
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 0), true, new Object[]{
                    "CPC",
                    "PTP",
                    "CPC",
                    'C', IC2Items.getItem("insulatedTinCableItem"),
                    'P', PowerConverterCore.tryOreDict("plateTin", IC2Items.getItem("platetin")),
                    'T', IC2Items.getItem("reBattery")
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 2), true, new Object[]{
                    "CPC",
                    "PTP",
                    "CPC",
                    'C', IC2Items.getItem("insulatedCopperCableItem"),
                    'P', PowerConverterCore.tryOreDict("plateCopper", IC2Items.getItem("platecopper")),
                    'T', IC2Items.getItem("lvTransformer")
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 4), true, new Object[]{
                    "CPC",
                    "PTP",
                    "CPC",
                    'C', IC2Items.getItem("insulatedGoldCableItem"),
                    'P', PowerConverterCore.tryOreDict("plateGold", IC2Items.getItem("plategold")),
                    'T', IC2Items.getItem("mvTransformer")
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 6), true, new Object[]{
                    "CPC",
                    "PTP",
                    "CPC",
                    'C', IC2Items.getItem("insulatedIronCableItem"),
                    'P', PowerConverterCore.tryOreDict("plateIron", IC2Items.getItem("plateiron")),
                    'T', IC2Items.getItem("hvTransformer")
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 8), true, new Object[]{
                    "CPC",
                    "PTP",
                    "CPC",
                    'C', IC2Items.getItem("glassFiberCableItem"),
                    'P', PowerConverterCore.tryOreDict("plateLapis", IC2Items.getItem("platelapi")),
                    'T', IC2Items.getItem("evTransformer")
            }));
        }
    }

    @Override
    public void loadConfig(Configuration c) {
        enableRecipes = c.get(RECIPE_INDUSTRIALCRAFT_CATEGORY, "enableRecipes", true).getBoolean(true);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(RECIPE_INDUSTRIALCRAFT_CATEGORY).get("enableRecipes").set(enableRecipes);
    }
}
