package powercrystals.powerconverters.power.systems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.systems.rf.BlockRF;
import powercrystals.powerconverters.power.systems.rf.ChargeHandlerRF;
import powercrystals.powerconverters.power.systems.rf.ItemBlockRF;
import powercrystals.powerconverters.power.systems.rf.TileEntityRFConsumer;
import powercrystals.powerconverters.power.systems.rf.TileEntityRFProducer;

/**
 * RF support. Adds the producer and consumer.
 */
public class PowerRedstoneFlux extends PowerSystem {
    public static final String id = "RF";

    public static final float DEFAULT_ENERGY_PER_INPUT = 437.5F;
    public static final float DEFAULT_ENERGY_PER_OUTPUT = 437.5F;
    public static final String CATEGORY_BUILDCRAFT = POWERSYSTEM_CATEGORY + ".rf";

    public PowerRedstoneFlux() {
        name = "RedstoneFlux";
        _internalEnergyPerInput = DEFAULT_ENERGY_PER_INPUT;
        _internalEnergyPerOutput = DEFAULT_ENERGY_PER_OUTPUT;
        _unit = "RF/t";

        block = new BlockRF();
        itemBlock = ItemBlockRF.class;
        consumer = TileEntityRFConsumer.class;
        producer = TileEntityRFProducer.class;

        TileEntityCharger.registerChargeHandler(new ChargeHandlerRF());
    }

    @Override
    public String getId() {
        return id;
    }

    public void registerBlocks() {
        GameRegistry.registerBlock(block, itemBlock, "converter.rf");
        GameRegistry.registerTileEntity(consumer, "powerConverterRFConsumer");
        GameRegistry.registerTileEntity(producer, "powerConverterRFProducer");
    }

    @Override
    public void registerCommonRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 1), new ItemStack(block, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 0), new ItemStack(block, 1, 1));
    }

    @Override
    public void loadConfig(Configuration c) {
        _internalEnergyPerInput = (float) c.get(CATEGORY_BUILDCRAFT, "internalEnergyPerInput", DEFAULT_ENERGY_PER_INPUT).getDouble(DEFAULT_ENERGY_PER_INPUT);
        _internalEnergyPerOutput = (float) c.get(CATEGORY_BUILDCRAFT, "internalEnergyPerOutput", DEFAULT_ENERGY_PER_OUTPUT).getDouble(DEFAULT_ENERGY_PER_OUTPUT);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(CATEGORY_BUILDCRAFT).get("internalEnergyPerInput").set(_internalEnergyPerInput);
        c.getCategory(CATEGORY_BUILDCRAFT).get("internalEnergyPerOutput").set(_internalEnergyPerOutput);
    }
}
