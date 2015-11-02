package powercrystals.powerconverters.power.systems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.common.BridgeSideData;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.base.BlockPowerConverter;
import powercrystals.powerconverters.power.systems.gt5.BlockGregTechConsumer;
import powercrystals.powerconverters.power.systems.gt5.BlockGregTechProducer;
import powercrystals.powerconverters.power.systems.gt5.ItemBlockGregTechConsumer;
import powercrystals.powerconverters.power.systems.gt5.ItemBlockGregTechProducer;
import powercrystals.powerconverters.power.systems.gt5.TileEntityGregTechConsumer;
import powercrystals.powerconverters.power.systems.gt5.TileEntityGregTechProducer;

/**
 * GregTech support. Adds producer and consumer.
 */
public class PowerGregTech5 extends PowerSystem {
    public static final String id = "GT";

    public static final float DEFAULT_ENERGY_PER_INPUT = 4000;
    public static final float DEFAULT_ENERGY_PER_OUTPUT = 4000;
    public static final String CATEGORY_FACTORIZATION = POWERSYSTEM_CATEGORY + ".gt";

    public static final String[] VOLTAGE_NAMES = new String[]{"ULV", "LV", "MV", "HV", "EV", "IV", "LuV", "ZPMV", "UV"};
    public static final int[] VOLTAGE_VALUES = new int[]{8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288};

    public BlockPowerConverter blockProducer, blockConsumer;
    public Class<? extends ItemBlock> itemBlockProducer, itemBlockConsumer;

    public PowerGregTech5() {
        name = "GregTech";
        _internalEnergyPerInput = DEFAULT_ENERGY_PER_INPUT;
        _internalEnergyPerOutput = DEFAULT_ENERGY_PER_OUTPUT;
        _unit = "EU";
        voltageNames = VOLTAGE_NAMES;
        voltageValues = VOLTAGE_VALUES;

        blockConsumer = new BlockGregTechConsumer();
        blockProducer = new BlockGregTechProducer();
        itemBlockConsumer = ItemBlockGregTechConsumer.class;
        itemBlockProducer = ItemBlockGregTechProducer.class;
        block = blockConsumer;
        itemBlock = itemBlockConsumer;

        consumer = TileEntityGregTechConsumer.class;
        producer = TileEntityGregTechProducer.class;

    }

    @Override
    public String getId() {
        return id;
    }

    public void registerBlocks() {
        GameRegistry.registerBlock(blockConsumer, itemBlockConsumer, "converter.gt.consumer");
        GameRegistry.registerBlock(blockProducer, itemBlockProducer, "converter.gt.producer");
        GameRegistry.registerTileEntity(consumer, "powerConverterGTConsumer");
        GameRegistry.registerTileEntity(producer, "powerConverterGTProducer");
    }

    @Override
    public void registerCommonRecipes() {
		// ULV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 0), new ItemStack(blockProducer, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 0), new ItemStack(blockConsumer, 1, 0));
        // LV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 1), new ItemStack(blockProducer, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 1), new ItemStack(blockConsumer, 1, 1));
        // MV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 2), new ItemStack(blockProducer, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 2), new ItemStack(blockConsumer, 1, 2));
        // HV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 3), new ItemStack(blockProducer, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 3), new ItemStack(blockConsumer, 1, 3));
        // EV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 4), new ItemStack(blockProducer, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 4), new ItemStack(blockConsumer, 1, 4));
        // IV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 5), new ItemStack(blockProducer, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 5), new ItemStack(blockConsumer, 1, 5));
        // LuV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 6), new ItemStack(blockProducer, 1, 6));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 6), new ItemStack(blockConsumer, 1, 6));
        // ZPM
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 7), new ItemStack(blockProducer, 1, 7));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 7), new ItemStack(blockConsumer, 1, 7));
        // UV
        GameRegistry.addShapelessRecipe(new ItemStack(blockConsumer, 1, 8), new ItemStack(blockProducer, 1, 8));
        GameRegistry.addShapelessRecipe(new ItemStack(blockProducer, 1, 8), new ItemStack(blockConsumer, 1, 8));
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

    @Override
	public String getRateString(BridgeSideData data) {
		long EUt, AMP;
		EUt	= VOLTAGE_VALUES[data.voltageNameIndex];
		AMP	= (long)Math.ceil(data.outputRate / (double)EUt);
		
		return AMP + " A";
	}
}
