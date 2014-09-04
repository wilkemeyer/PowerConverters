package powercrystals.powerconverters.power.systems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.systems.gt.BlockGregTech;
import powercrystals.powerconverters.power.systems.gt.ItemBlockGregTech;
import powercrystals.powerconverters.power.systems.gt.TileEntityGregTechConsumer;
import powercrystals.powerconverters.power.systems.gt.TileEntityGregTechProducer;

/**
 * GregTech support. Adds producer and consumer.
 */
public class PowerGregTech extends PowerSystem {
    public static final String id = "GT";

    public static final float DEFAULT_ENERGY_PER_INPUT = 4000;
    public static final float DEFAULT_ENERGY_PER_OUTPUT = 4000;
    public static final String CATEGORY_FACTORIZATION = POWERSYSTEM_CATEGORY + ".gt";

    public static final String[] VOLTAGE_NAMES = new String[]{"ULV", "LV", "MV", "HV", "EV", "IV", "LuV", "ZPMV", "UV"};
    public static final int[] VOLTAGE_VALUES = new int[]{8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288};

    public PowerGregTech() {
        name = "GregTech";
        _internalEnergyPerInput = DEFAULT_ENERGY_PER_INPUT;
        _internalEnergyPerOutput = DEFAULT_ENERGY_PER_OUTPUT;
        _unit = "EU/t";
        voltageNames = VOLTAGE_NAMES;
        voltageValues = VOLTAGE_VALUES;

        block = new BlockGregTech();
        itemBlock = ItemBlockGregTech.class;
        consumer = TileEntityGregTechConsumer.class;
        producer = TileEntityGregTechProducer.class;

    }

    @Override
    public String getId() {
        return id;
    }

    public void registerBlocks() {
        GameRegistry.registerBlock(block, itemBlock, "converter.gt");
        GameRegistry.registerTileEntity(consumer, "powerConverterGTConsumer");
        GameRegistry.registerTileEntity(producer, "powerConverterGTProducer");
    }

    @Override
    public void registerCommonRecipes() {
		// ULV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 1), new ItemStack(block, 1, 0)); 
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 0), new ItemStack(block, 1, 1)); 
        // LV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 3), new ItemStack(block, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 2), new ItemStack(block, 1, 3));
        // MV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 5), new ItemStack(block, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 4), new ItemStack(block, 1, 5));
        // HV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 7), new ItemStack(block, 1, 6));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 6), new ItemStack(block, 1, 7));
        // EV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 9), new ItemStack(block, 1, 8));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 8), new ItemStack(block, 1, 9));
		// IV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 11), new ItemStack(block, 1, 10));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 10), new ItemStack(block, 1, 11));
		// LuV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 13), new ItemStack(block, 1, 12));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 12), new ItemStack(block, 1, 13));
        // ZPM
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 15), new ItemStack(block, 1, 14));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 14), new ItemStack(block, 1, 15));
        // UV
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 17), new ItemStack(block, 1, 16));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 16), new ItemStack(block, 1, 17));        
    }

    @Override
    public void loadConfig(Configuration c) {
        _internalEnergyPerInput = (float) c.get(CATEGORY_FACTORIZATION, "internalEnergyPerInput", DEFAULT_ENERGY_PER_INPUT).getDouble(DEFAULT_ENERGY_PER_INPUT);
        _internalEnergyPerOutput = (float) c.get(CATEGORY_FACTORIZATION, "internalEnergyPerOutput", DEFAULT_ENERGY_PER_OUTPUT).getDouble(DEFAULT_ENERGY_PER_OUTPUT);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(CATEGORY_FACTORIZATION).get("internalEnergyPerInput").set(_internalEnergyPerInput);
        c.getCategory(CATEGORY_FACTORIZATION).get("internalEnergyPerOutput").set(_internalEnergyPerOutput);
    }
}
