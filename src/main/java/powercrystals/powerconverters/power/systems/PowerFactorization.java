package powercrystals.powerconverters.power.systems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.systems.factorization.BlockFactorization;
import powercrystals.powerconverters.power.systems.factorization.ItemBlockFactorization;
import powercrystals.powerconverters.power.systems.factorization.TileEntityFactorizationConsumer;
import powercrystals.powerconverters.power.systems.factorization.TileEntityFactorizationProducer;

/**
 * Factorization support. Adds producer and consumer.
 */
public class PowerFactorization extends PowerSystem {
    public static final String id = "FZ";

    public static final float DEFAULT_ENERGY_PER_INPUT = 175;
    public static final float DEFAULT_ENERGY_PER_OUTPUT = 175;
    public static final String CATEGORY_FACTORIZATION = POWERSYSTEM_CATEGORY + ".fz";

    public PowerFactorization() {
        name = "Factorization";
        _internalEnergyPerInput = DEFAULT_ENERGY_PER_INPUT;
        _internalEnergyPerOutput = DEFAULT_ENERGY_PER_OUTPUT;
        _unit = "CG/t";

        block = new BlockFactorization();
        itemBlock = ItemBlockFactorization.class;
        consumer = TileEntityFactorizationConsumer.class;
        producer = TileEntityFactorizationProducer.class;
    }

    @Override
    public String getId() {
        return id;
    }

    public void registerBlocks() {
        GameRegistry.registerBlock(block, itemBlock, "converter.fz");
        GameRegistry.registerTileEntity(consumer, "powerConverterFZConsumer");
        GameRegistry.registerTileEntity(producer, "powerConverterFZProducer");
    }

    @Override
    public void registerCommonRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 1), new ItemStack(block, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 0), new ItemStack(block, 1, 1));
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
