package powercrystals.powerconverters;

import com.google.common.base.Throwables;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.powerconverters.common.BlockPowerConverterCommon;
import powercrystals.powerconverters.common.ItemBlockPowerConverterCommon;
import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.gui.PCGUIHandler;
import powercrystals.powerconverters.mods.BuildCraft;
import powercrystals.powerconverters.mods.Factorization;
import powercrystals.powerconverters.mods.IndustrialCraft;
import powercrystals.powerconverters.mods.ThermalExpansion;
import powercrystals.powerconverters.mods.base.LoaderBase;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.steam.BlockPowerConverterSteam;
import powercrystals.powerconverters.power.steam.ItemBlockPowerConverterSteam;
import powercrystals.powerconverters.power.steam.TileEntitySteamConsumer;
import powercrystals.powerconverters.power.steam.TileEntitySteamProducer;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
//import powercrystals.powerconverters.power.steam.BlockPowerConverterSteam;
//import powercrystals.powerconverters.power.steam.ItemBlockPowerConverterSteam;
//import powercrystals.powerconverters.power.steam.TileEntitySteamConsumer;
//import powercrystals.powerconverters.power.steam.TileEntitySteamProducer;

@Mod(modid = PowerConverterCore.modId, name = PowerConverterCore.modName, dependencies = "after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public final class PowerConverterCore
{
    public static final String modId = "PowerConverters";
    public static final String modName = "Power Converters";
    public static final String version;

    static {
        Properties prop = new Properties();

        try {
            InputStream stream = PowerConverterCore.class.getClassLoader().getResourceAsStream("version.properties");
            prop.load(stream);
            stream.close();
        }
        catch (Exception e) {
            //noinspection ThrowableResultOfMethodCallIgnored
            Throwables.propagate(e);
        }

        version = String.format("%s_build-%s", prop.getProperty("version"), prop.getProperty("build_number"));
    }

    public static final String guiFolder = modId + ":" + "textures/gui/";

    public static Block converterBlockCommon;
    public static Block converterBlockSteam;

    @Mod.Instance(modId)
    public static PowerConverterCore instance;

    public static int blockIdThermalExpansion;
    public static int blockIdIndustrialCraft;
    public static int blockIdFactorization;
    public static int blockIdBuildCraft;

    public static int bridgeBufferSize;
    public static int throttleSteamConsumer;
    public static int throttleSteamProducer;
    public static PowerSystem powerSystemSteam;
    public static boolean powerSystemSteamEnabled;

    private static int blockIdCommon;
    private static int blockIdSteam;
    private LoaderBase[] bases = new LoaderBase[] { BuildCraft.INSTANCE, Factorization.INSTANCE,IndustrialCraft.INSTANCE, ThermalExpansion.INSTANCE };

    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {
        evt.getModMetadata().version = PowerConverterCore.version;

        File dir = evt.getModConfigurationDirectory();
        loadConfig(dir);

        converterBlockCommon = new BlockPowerConverterCommon(blockIdCommon);
        GameRegistry.registerBlock(converterBlockCommon, ItemBlockPowerConverterCommon.class, converterBlockCommon.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntityEnergyBridge.class, "powerConverterEnergyBridge");
        GameRegistry.registerTileEntity(TileEntityCharger.class, "powerConverterUniversalCharger");

        if(powerSystemSteamEnabled) {
            converterBlockSteam = new BlockPowerConverterSteam(blockIdSteam);
            GameRegistry.registerBlock(converterBlockSteam, ItemBlockPowerConverterSteam.class, converterBlockSteam.getUnlocalizedName());
            GameRegistry.registerTileEntity(TileEntitySteamConsumer.class, "powerConverterSteamConsumer");
            GameRegistry.registerTileEntity(TileEntitySteamProducer.class, "powerConverterSteamProducer");
        }

        powerSystemSteam = new PowerSystem("Steam", "STEAM", 500, 500,/*875, 875,*/null, null, "mB/t");
        PowerSystem.registerPowerSystem(powerSystemSteam);
        for (LoaderBase base : bases)
            base.load(LoaderBase.Stage.PREINIT);
        }

    @SuppressWarnings("UnusedParameters")
    @EventHandler
    public void init(FMLInitializationEvent evt) throws Exception
    {
	for (LoaderBase base : bases)
	    base.load(LoaderBase.Stage.INIT);
    }

    @SuppressWarnings("UnusedParameters")
    @EventHandler
    public void postInit(FMLPostInitializationEvent evt) throws Exception
    {
	for (LoaderBase base : bases)
	    base.load(LoaderBase.Stage.POSTINIT);

	System.out.println("+++++++++++++++++++++++++[PowerConverters][NOTICE]+++++++++++++++++++++++++");
	System.out.println("Default power ratios are based on FTB standards and not all mods follow these");
	System.out.println("If you find conflicting ratios in your pack, please adjust the config acoordingly or remove PC");
	System.out.println("These conflicts are not the fault of any mod author. It is the nature on minecraft");
	System.out.println("-------------------------[PowerConverters][STEAM]-------------------------");
	System.out.println("Default steam ratios, while based on standards, will create infinite energy loops");
	System.out.println("To prevent over powered infinite energy, use a steam throttle values of less than 5");
	System.out.println("+++++++++++++++++++++++++[PowerConverters][NOTICE]+++++++++++++++++++++++++");

        registerRecipes();
        if (powerSystemSteamEnabled) {
            loadSteamConverters();
        }

	NetworkRegistry.instance().registerGuiHandler(instance, new PCGUIHandler());
	MinecraftForge.EVENT_BUS.register(instance);

	// Cleanup
	bases = null;
    }

    private void registerRecipes() {
        ItemStack stackGold = new ItemStack(Item.ingotGold);
        ItemStack stackRedstone = new ItemStack(Item.redstone);
        ItemStack stackIron = new ItemStack(Item.ingotIron);
        ItemStack stackGlass = new ItemStack(Block.glass);
        ItemStack stackDiamond = new ItemStack(Item.diamond);

        Object entryGold = tryOreDict("ingotGold", stackGold);
        Object entryRedstone = tryOreDict("dustRedstone", stackRedstone);
        Object entryIron = tryOreDict("ingotIron", stackIron);
        Object entryGlass = tryOreDict("blockGlass", stackGlass);
        Object entryDiamond = tryOreDict("gemDiamond", stackDiamond);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlockCommon, 1, 0), true, new Object[]{
                // Energy Bridge
                "GRG",
                "LDL",
                "GRG",
                'R', entryRedstone,
                'G', entryGold,
                'L', entryGlass,
                'D', entryDiamond
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlockCommon, 1, 2), true, new Object[]{
                // Universal charger
                "GRG",
                "ICI",
                "GRG",
                'R', entryRedstone,
                'G', entryGold,
                'I', entryIron,
                'C', Block.chest
        }));


    }

    private Object tryOreDict(String name, ItemStack itemStack) {
        for(ItemStack ore : OreDictionary.getOres(name)) {
            if(ore.isItemEqual(itemStack)) {
                return name;
            }
        }
        return itemStack;
    }

    private void loadSteamConverters() throws Exception
    {

        ItemStack stackPowerBridge = new ItemStack(converterBlockCommon, 1, 0);

	    if (Loader.isModLoaded("Railcraft"))
	    {
            Object entrySteelPlate = tryOreDict("plateSteel", GameRegistry.findItemStack("Railcraft", "part.plate.steel", 1));

            ItemStack stackValve = GameRegistry.findItemStack("Railcraft", "machine.beta.tank.steel.gauge", 1);
            ItemStack stackLiquidFirebox = GameRegistry.findItemStack("Railcraft", "machine.beta.boiler.firebox.liquid", 1);
            ItemStack stackIndustrialEngine = GameRegistry.findItemStack("Railcraft", "machine.beta.engine.steam.high", 1);

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlockSteam, 1, 0), true,
                    // Steam consumer
                    "PVP",
                    "VBV",
                    "PLP",
                    'P', entrySteelPlate,
                    'V', stackValve,
                    'B', stackPowerBridge,
                    'L', stackLiquidFirebox));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlockSteam, 1, 1), true,
                    // Steam producer
                    "PVP",
                    "VBV",
                    "PEP",
                    'P', entrySteelPlate,
                    'V', stackValve,
                    'B', stackPowerBridge,
                    'E', stackIndustrialEngine));
	    }
		//Object fzRegistry = Class.forName("factorization.common.Core").getField("registry").get(null);
		//GameRegistry.addRecipe(new ItemStack(converterBlockSteam, 1, 0), "G G", " E ", "G G", 'G', Item.ingotGold, 'E', (Class.forName("factorization.common.Registry").getField("steamturbine_item").get(fzRegistry)));

	    /*GameRegistry.addShapelessRecipe(new ItemStack(converterBlockSteam, 1, 1), new ItemStack(converterBlockSteam, 1, 0));
	    GameRegistry.addShapelessRecipe(new ItemStack(converterBlockSteam, 1, 0), new ItemStack(converterBlockSteam, 1, 1));*/
    }

    private static void loadConfig(File dir)
    {
	dir = new File(new File(dir, modId.toLowerCase()), "common.cfg");
	Configuration c = new Configuration(dir);

	blockIdCommon = c.getBlock("ID.BlockCommon", 2850).getInt();
	blockIdBuildCraft = c.getBlock("ID.BlockBuildcraft", 2851).getInt();
	blockIdIndustrialCraft = c.getBlock("ID.BlockIndustrialCraft", 2852).getInt();
	blockIdSteam = c.getBlock("ID.BlockSteam", 2853).getInt();
	blockIdFactorization = c.getBlock("ID.BlockFactorization", 2854).getInt();
	blockIdThermalExpansion = c.getBlock("ID.BlockThermalExpansion", 2855).getInt();

	bridgeBufferSize = c.get(Configuration.CATEGORY_GENERAL, "BridgeBufferSize", 160000000).getInt();
	throttleSteamConsumer = c.get("Throttles", "Steam.Consumer", 1000, "mB/t").getInt();
	throttleSteamProducer = c.get("Throttles", "Steam.Producer", 1000, "mB/t (Sugested value for mod exploit handling = 1; this does not diminish steam return)").getInt();

	PowerSystem.loadConfig(c);
	c.save();
    }
}
