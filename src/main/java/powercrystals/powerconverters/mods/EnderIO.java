package powercrystals.powerconverters.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.mods.base.LoaderBase;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.rf.BlockPowerConverterRF;
import powercrystals.powerconverters.power.rf.ItemBlockPowerConverterRF;
import powercrystals.powerconverters.power.rf.TileEntityRFConsumer;
import powercrystals.powerconverters.power.rf.TileEntityRFProducer;


public final class EnderIO extends LoaderBase
{
    public static final EnderIO INSTANCE = new EnderIO();

    public BlockPowerConverterRF converterBlock;
    public PowerSystem powerSystem;

    private EnderIO()
    {
    	super("EnderIO");
    }

    @Override
    protected void preInit()
    {
    	powerSystem = new PowerSystem("EnderIO", "RF", 437.5F, 437.5F, null, null, "RF/t");
    	PowerSystem.registerPowerSystem(powerSystem);
    }

    @Override
    protected void init()
    {
    	converterBlock = new BlockPowerConverterRF();
    	GameRegistry.registerBlock(converterBlock, ItemBlockPowerConverterRF.class, converterBlock.getUnlocalizedName());
    	GameRegistry.registerTileEntity(TileEntityRFConsumer.class, "powerConverterRFConsumer");
    	GameRegistry.registerTileEntity(TileEntityRFProducer.class, "powerConverterRFProducer");
    }

    @Override
    protected void postInit()
    {
    	GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 1), new ItemStack(converterBlock, 1, 0));
    	GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 0), new ItemStack(converterBlock, 1, 1));
    	
        GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                "G G",
                " T ",
                "G G",
                'G', GameRegistry.findItem("minecraft", "gold_ingot"),
                'T', GameRegistry.findItemStack( "EnderIO", "blockCapacitorBank", 1 )
        );
    }
}
