package powercrystals.powerconverters.crafting.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.ItemList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.crafting.RecipeProvider;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerGregTech;

/**
 * Add recipes for GregTech
 */
public class RecipeGregTech extends RecipeProvider {
    private boolean enableRecipes = true;
    public static final String RECIPE_GREGTECH_CATEGORY = RECIPE_CATEGORY + ".gt";

    @Override
    public void registerRecipes() {

        PowerSystem gt = PowerSystemManager.getInstance().getPowerSystemByName(PowerGregTech.id);
        if(gt != null) {
        	Block converterBlock = gt.block;
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 0), true, new Object[]{ // ULV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt01Lead",
        		'P', "plateLead",
        		'B', ItemList.Battery_Buffer_1by1_ULV.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 2), true, new Object[]{ // LV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt01Tin",
        		'P', "plateTin",
        		'B', ItemList.Battery_Buffer_1by1_LV.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 4), true, new Object[]{ // MV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt01Copper",
        		'P', "plateCopper",
        		'B', ItemList.Battery_Buffer_1by1_MV.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 6), true, new Object[]{ // HV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt01Gold",
        		'P', "plateGold",
        		'B', ItemList.Battery_Buffer_1by1_HV.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 8), true, new Object[]{ // EV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt01Aluminium",
        		'P', "plateAluminium",
        		'B', ItemList.Battery_Buffer_1by1_EV.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 10), true, new Object[]{ // IV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt02Steel",
        		'P', "plateSteel",
        		'B', ItemList.Battery_Buffer_1by1_IV.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 12), true, new Object[]{ // LuV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt04Tungsten",
        		'P', "plateTungsten",
        		'B', ItemList.Battery_Buffer_1by1_LuV.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 14), true, new Object[]{ // ZPM
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "cableGt04Osmium",
        		'P', "plateOsmium",
        		'B', ItemList.Battery_Buffer_1by1_ZPM.get(1).copy()
        	}));
        	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 16), true, new Object[]{ // UV
        		"CPC",
        		"PBP",
        		"CPC",
        		'C', "wireGt16Osmium",
        		'P', "plateOsmium",
        		'B', ItemList.Battery_Buffer_1by1_UV.get(1).copy()
        	}));
        	

        	

        
        }
    }

    @Override
    public void loadConfig(Configuration c) {
        enableRecipes = c.get(RECIPE_GREGTECH_CATEGORY, "enableRecipes", true).getBoolean(true);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(RECIPE_GREGTECH_CATEGORY).get("enableRecipes").set(enableRecipes);
    }
}
