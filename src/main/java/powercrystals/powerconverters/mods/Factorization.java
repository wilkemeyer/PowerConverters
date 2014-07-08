package powercrystals.powerconverters.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.mods.base.LoaderBase;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.factorization.BlockPowerConverterFactorization;
import powercrystals.powerconverters.power.factorization.ItemBlockPowerConverterFactorization;
import powercrystals.powerconverters.power.factorization.TileEntityPowerConverterFactorizationConsumer;
import powercrystals.powerconverters.power.factorization.TileEntityPowerConverterFactorizationProducer;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 3/03/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Factorization extends LoaderBase
{
    public static final Factorization INSTANCE = new Factorization();

    private Factorization() {
        super("factorization");
    }

    public BlockPowerConverterFactorization converterBlock;
    public PowerSystem powerSystem;

    @Override
    protected void preInit()
    {
        powerSystem = new PowerSystem("Factorization", "FZ", 175, 175, null, null, "CG/t");
        PowerSystem.registerPowerSystem(powerSystem);
    }

    @Override
    protected void init() {
        converterBlock = new BlockPowerConverterFactorization();
        GameRegistry.registerBlock(converterBlock, ItemBlockPowerConverterFactorization.class, converterBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationConsumer.class, "powerConverterFZConsumer");
        GameRegistry.registerTileEntity(TileEntityPowerConverterFactorizationProducer.class, "powerConverterFZProducer");
    }

    @Override
    protected void postInit() {
        try {
            Object fzRegistry = Class.forName("factorization.shared.Core").getField("registry").get(null);
            GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                    "I I", " B ", "I I",
                    'I', GameRegistry.findItem("minecraft", "gold_ingot"),
                    'B', (Class.forName("factorization.common.Registry").getField("solarboiler_item").get(fzRegistry)));
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 1), new ItemStack(converterBlock, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 0), new ItemStack(converterBlock, 1, 1));
    }
}
