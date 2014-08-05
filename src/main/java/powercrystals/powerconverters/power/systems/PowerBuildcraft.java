package powercrystals.powerconverters.power.systems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.systems.buildcraft.BlockBuildCraft;
import powercrystals.powerconverters.power.systems.buildcraft.ItemBlockBuildCraft;
import powercrystals.powerconverters.power.systems.buildcraft.TileEntityBuildCraftConsumer;
import powercrystals.powerconverters.power.systems.buildcraft.TileEntityBuildCraftProducer;

/**
 * Buildcraft power support. Adds consumer and producer.
 */
public class PowerBuildcraft extends PowerSystem {
    public static String id = "BC";

    public static final float DEFAULT_ENERGY_PER_INPUT = 10000;
    public static final float DEFAULT_ENERGY_PER_OUTPUT = 10000;
    public static final String CATEGORY_BUILDCRAFT = POWERSYSTEM_CATEGORY + ".bc";

    public PowerBuildcraft() {
        name = "Buildcraft";
        _internalEnergyPerInput = DEFAULT_ENERGY_PER_INPUT;
        _internalEnergyPerOutput = DEFAULT_ENERGY_PER_OUTPUT;
        _unit = "MJ/t";

        block = new BlockBuildCraft();
        itemBlock = ItemBlockBuildCraft.class;
        consumer = TileEntityBuildCraftConsumer.class;
        producer = TileEntityBuildCraftProducer.class;
    }

    @Override
    public String getId() {
        return id;
    }

    public void registerBlocks() {
        GameRegistry.registerBlock(block, itemBlock, "converter.bc");
        GameRegistry.registerTileEntity(consumer, "powerConverterBCConsumer");
        GameRegistry.registerTileEntity(producer, "powerConverterBCProducer");
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
