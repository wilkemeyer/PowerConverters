package powercrystals.powerconverters.power.systems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.systems.steam.BlockSteam;
import powercrystals.powerconverters.power.systems.steam.ItemBlockSteam;
import powercrystals.powerconverters.power.systems.steam.TileEntitySteamConsumer;
import powercrystals.powerconverters.power.systems.steam.TileEntitySteamProducer;

/**
 * Steam power support. Adds steam consumer and producer.
 */
public class PowerSteam extends PowerSystem {
    public static String id = "STEAM";

    public static final float DEFAULT_ENERGY_PER_INPUT = 500;
    public static final float DEFAULT_ENERGY_PER_OUTPUT = 500;
    public static final String CATEGORY_STEAM = POWERSYSTEM_CATEGORY + ".bc";

    private static final int THROTTLE_CONSUMER_DEFAULT = 1000;
    private static final int THROTTLE_PRODUCER_DEFAULT = 1000;
    private int throttleConsumer = THROTTLE_CONSUMER_DEFAULT;
    private int throttleProducer = THROTTLE_PRODUCER_DEFAULT;

    public PowerSteam() {
        name = "Steam";
        _internalEnergyPerInput = DEFAULT_ENERGY_PER_INPUT;
        _internalEnergyPerOutput = DEFAULT_ENERGY_PER_OUTPUT;
        _unit = "mB/t";

        block = new BlockSteam();
        itemBlock = ItemBlockSteam.class;
        consumer = TileEntitySteamConsumer.class;
        producer = TileEntitySteamProducer.class;
    }

    public void registerBlocks() {
        GameRegistry.registerBlock(block, itemBlock, "converter.steam");
        GameRegistry.registerTileEntity(consumer, "powerConverterSteamConsumer");
        GameRegistry.registerTileEntity(producer, "powerConverterSteamProducer");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void registerCommonRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 1), new ItemStack(block, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(block, 1, 0), new ItemStack(block, 1, 1));
    }

    @Override
    public void loadConfig(Configuration c) {
        _internalEnergyPerInput = (float) c.get(CATEGORY_STEAM, "internalEnergyPerInput", DEFAULT_ENERGY_PER_INPUT).getDouble(DEFAULT_ENERGY_PER_INPUT);
        _internalEnergyPerOutput = (float) c.get(CATEGORY_STEAM, "internalEnergyPerOutput", DEFAULT_ENERGY_PER_OUTPUT).getDouble(DEFAULT_ENERGY_PER_OUTPUT);
        throttleConsumer = c.get(CATEGORY_STEAM, "throttle.steamConsumer", THROTTLE_CONSUMER_DEFAULT, "mB/t").getInt(THROTTLE_CONSUMER_DEFAULT);
        throttleProducer = c.get(CATEGORY_STEAM, "throttle.steamProducer", THROTTLE_PRODUCER_DEFAULT, "mB/t\n (Suggested value for mod expoit handling = 1; this does not diminish steam return)").getInt(THROTTLE_PRODUCER_DEFAULT);
    }

    @Override
    public void saveConfig(Configuration c) {
        c.getCategory(CATEGORY_STEAM).get("internalEnergyPerInput").set(_internalEnergyPerInput);
        c.getCategory(CATEGORY_STEAM).get("internalEnergyPerOutput").set(_internalEnergyPerOutput);
        c.getCategory(CATEGORY_STEAM).get("throttle.steamConsumer").set(throttleConsumer);
        c.getCategory(CATEGORY_STEAM).get("throttle.steamProducer").set(throttleProducer);
    }

    public int getThrottleConsumer() {
        return throttleConsumer;
    }

    public int getThrottleProducer() {
        return throttleProducer;
    }
}
