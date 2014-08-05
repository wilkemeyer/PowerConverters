package powercrystals.powerconverters.power.systems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.systems.ic2.BlockIndustrialCraft;
import powercrystals.powerconverters.power.systems.ic2.ChargeHandlerIndustrialCraft;
import powercrystals.powerconverters.power.systems.ic2.ItemBlockIndustrialCraft;
import powercrystals.powerconverters.power.systems.ic2.TileEntityIndustrialCraftConsumer;
import powercrystals.powerconverters.power.systems.ic2.TileEntityIndustrialCraftProducer;

/**
 * Industrialcraft support. Adds producer and consumer.
 */
public class PowerIndustrialcraft extends PowerSystem {
    public static final String id = "IC2";

    public static final float DEFAULT_ENERGY_PER_INPUT = 175;
    public static final float DEFAULT_ENERGY_PER_OUTPUT = 175;
    public static final String CATEGORY_FACTORIZATION = POWERSYSTEM_CATEGORY + ".ic2";

    public static final String[] VOLTAGE_NAMES = new String[]{"LV", "MV", "HV", "EV", "UV"};
    public static final int[] VOLTAGE_VALUES = new int[]{32, 128, 512, 2048, 8192};

    public PowerIndustrialcraft() {
        name = "Industrialcraft";
        _internalEnergyPerInput = DEFAULT_ENERGY_PER_INPUT;
        _internalEnergyPerOutput = DEFAULT_ENERGY_PER_OUTPUT;
        _unit = "EU/t";
        voltageNames = VOLTAGE_NAMES;
        voltageValues = VOLTAGE_VALUES;

        block = new BlockIndustrialCraft();
        itemBlock = ItemBlockIndustrialCraft.class;
        consumer = TileEntityIndustrialCraftConsumer.class;
        producer = TileEntityIndustrialCraftProducer.class;

        TileEntityCharger.registerChargeHandler(new ChargeHandlerIndustrialCraft());
    }

    @Override
    public String getId() {
        return id;
    }

    public void registerBlocks() {
        GameRegistry.registerBlock(block, itemBlock, "converter.ic2");
        GameRegistry.registerTileEntity(consumer, "powerConverterIC2Consumer");
        GameRegistry.registerTileEntity(producer, "powerConverterIC2Producer");
    }

    @Override
    public void registerCommonRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 1), new ItemStack(block, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 0), new ItemStack(block, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 3), new ItemStack(block, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 2), new ItemStack(block, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 5), new ItemStack(block, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 4), new ItemStack(block, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 7), new ItemStack(block, 1, 6));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 6), new ItemStack(block, 1, 7));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 9), new ItemStack(block, 1, 8));
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
