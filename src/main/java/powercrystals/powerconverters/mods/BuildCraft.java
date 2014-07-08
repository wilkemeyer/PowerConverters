package powercrystals.powerconverters.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.mods.base.LoaderBase;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.buildcraft.BlockPowerConverterBuildCraft;
import powercrystals.powerconverters.power.buildcraft.ItemBlockPowerConverterBuildCraft;
import powercrystals.powerconverters.power.buildcraft.TileEntityBuildCraftConsumer;
import powercrystals.powerconverters.power.buildcraft.TileEntityBuildCraftProducer;

/**
 * @author samrg472
 */
public final class BuildCraft extends LoaderBase {

    public static final BuildCraft INSTANCE = new BuildCraft();

    public BlockPowerConverterBuildCraft converterBlock;
    public PowerSystem powerSystem;

    private BuildCraft() {
        super("BuildCraft|Energy");
    }

    @Override
    protected void preInit() {
        powerSystem = new PowerSystem("BuildCraft", "BC", 10000, 10000,/*4375, 4375*/null, null, "MJ/t");
        PowerSystem.registerPowerSystem(powerSystem);
    }

    @Override
    protected void init() {
        converterBlock = new BlockPowerConverterBuildCraft();
        GameRegistry.registerBlock(converterBlock, ItemBlockPowerConverterBuildCraft.class, converterBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntityBuildCraftConsumer.class, "powerConverterBCConsumer");
        GameRegistry.registerTileEntity(TileEntityBuildCraftProducer.class, "powerConverterBCProducer");
    }

    @Override
    protected void postInit() {
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 1), new ItemStack(converterBlock, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 0), new ItemStack(converterBlock, 1, 1));
        try {
            ItemStack redstoneBlock = new ItemStack(GameRegistry.findBlock("minecraft", "redstone_block"), 1);

            Item cable = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipepowergold");
            Item struct = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipestructurecobblestone");
            Item conduit = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipePowerDiamond");
            Item fluid = GameRegistry.findItem("Buildcraft|Transport", "item.buildcraftPipe.pipeFluidsGold");

            Block chest = GameRegistry.findBlock("Buildcraft|Transport", "filteredBufferBlock");
            Block engine = GameRegistry.findBlock("Buildcraft|Energy", "engineBlock");
            Block pump = GameRegistry.findBlock("Buildcraft|Factory", "pumpBlock");

            ItemStack gear = new ItemStack(GameRegistry.findItem("Buildcraft|Core", "goldGearItem"), 1, 1);

            if (BuildCraft.INSTANCE.powerSystem.getRecipesEnabled()) {
                GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0),
                        "PPP",
                        "PBP",
                        "PPP",
                        'B', redstoneBlock,
                        'P', conduit
                );
                GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2),
                        "TGT",
                        "#S#",
                        "T#T",
                        'T', cable,
                        'S', chest,
                        '#', engine,
                        'G', gear
                );
                if (PowerConverterCore.powerSystemSteamEnabled) {
                    GameRegistry.addRecipe(new ItemStack(PowerConverterCore.converterBlockSteam, 1, 0),
                            "GSG",
                            "SES",
                            "GSG",
                            'G', fluid,
                            'S', struct,
                            'E', pump
                    );
                }
            }
            GameRegistry.addRecipe(new ItemStack(converterBlock, 1, 0),
                    "GSG",
                    "SES",
                    "GSG",
                    'G', cable,
                    'S', struct,
                    'E', gear
            );
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
