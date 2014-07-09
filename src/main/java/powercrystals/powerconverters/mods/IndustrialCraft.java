package powercrystals.powerconverters.mods;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.TileEntityCharger;
import powercrystals.powerconverters.mods.base.LoaderBase;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.ic2.BlockPowerConverterIndustrialCraft;
import powercrystals.powerconverters.power.ic2.ChargeHandlerIndustrialCraft;
import powercrystals.powerconverters.power.ic2.ItemBlockPowerConverterIndustrialCraft;
import powercrystals.powerconverters.power.ic2.TileEntityIndustrialCraftConsumer;
import powercrystals.powerconverters.power.ic2.TileEntityIndustrialCraftProducer;

/**
 * @author samrg472
 */
public final class IndustrialCraft extends LoaderBase {

    public static final IndustrialCraft INSTANCE = new IndustrialCraft();

    public BlockPowerConverterIndustrialCraft converterBlock;
    public PowerSystem powerSystem;

    private IndustrialCraft() {
        super("IC2");
    }

    @Override
    protected void preInit() {
        powerSystem = new PowerSystem("IndustrialCraft", "IC2", 4000, 4000,/*1800, 1800,*/new String[]{"LV", "MV", "HV", "EV", "UV"}, new int[]{32, 128, 512, 2048, 8192}, "EU/t");
        PowerSystem.registerPowerSystem(powerSystem);
        TileEntityCharger.registerChargeHandler(new ChargeHandlerIndustrialCraft());
    }

    @Override
    protected void init() {
        converterBlock = new BlockPowerConverterIndustrialCraft();
        GameRegistry.registerBlock(converterBlock, ItemBlockPowerConverterIndustrialCraft.class, "converter.ic2");
        GameRegistry.registerTileEntity(TileEntityIndustrialCraftConsumer.class, "powerConverterIC2Consumer");
        GameRegistry.registerTileEntity(TileEntityIndustrialCraftProducer.class, "powerConverterIC2Producer");
    }

    @Override
    protected void postInit() {
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 1), new ItemStack(converterBlock, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 0), new ItemStack(converterBlock, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 3), new ItemStack(converterBlock, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 2), new ItemStack(converterBlock, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 5), new ItemStack(converterBlock, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 4), new ItemStack(converterBlock, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 7), new ItemStack(converterBlock, 1, 6));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 6), new ItemStack(converterBlock, 1, 7));
        GameRegistry.addShapelessRecipe(new ItemStack(converterBlock, 1, 9), new ItemStack(converterBlock, 1, 8));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 0), true, new Object[]{
                "CPC",
                "PTP",
                "CPC",
                'C', IC2Items.getItem("insulatedTinCableItem"),
                'P', PowerConverterCore.tryOreDict("plateTin", IC2Items.getItem("platetin")),
                'T', IC2Items.getItem("reBattery")
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 2), true, new Object[]{
                "CPC",
                "PTP",
                "CPC",
                'C', IC2Items.getItem("insulatedCopperCableItem"),
                'P', PowerConverterCore.tryOreDict("plateCopper", IC2Items.getItem("platecopper")),
                'T', IC2Items.getItem("lvTransformer")
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 4), true, new Object[]{
                "CPC",
                "PTP",
                "CPC",
                'C', IC2Items.getItem("insulatedGoldCableItem"),
                'P', PowerConverterCore.tryOreDict("plateGold", IC2Items.getItem("plategold")),
                'T', IC2Items.getItem("mvTransformer")
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 6), true, new Object[]{
                "CPC",
                "PTP",
                "CPC",
                'C', IC2Items.getItem("insulatedIronCableItem"),
                'P', PowerConverterCore.tryOreDict("plateIron", IC2Items.getItem("plateiron")),
                'T', IC2Items.getItem("hvTransformer")
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(converterBlock, 1, 8), true, new Object[]{
                "CPC",
                "PTP",
                "CPC",
                'C', IC2Items.getItem("glassFiberCableItem"),
                'P', PowerConverterCore.tryOreDict("plateLapis", IC2Items.getItem("platelapi")),
                'T', IC2Items.getItem("evTransformer")
        }));

        ItemStack fluid = IC2Items.getItem("ejectorUpgrade");
        ItemStack storage = IC2Items.getItem("batBox");
        ItemStack cable = IC2Items.getItem("insulatedGoldCableItem");
        Object tin = PowerConverterCore.tryOreDict("plateTin", IC2Items.getItem("platetin"));
        Object bronze = PowerConverterCore.tryOreDict("plateBronze", IC2Items.getItem("platebronze"));
        ItemStack charger = IC2Items.getItem("RTGenerator");
        ItemStack transmit = IC2Items.getItem("insulatedIronCableItem");
        if (IndustrialCraft.INSTANCE.powerSystem.getRecipesEnabled()) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0), true, new Object[]{
                    "CTC",
                    "SDS",
                    "CTC",
                    'C', cable,
                    'T', tin,
                    'S', storage,
                    'D', GameRegistry.findItem("minecraft", "diamond")
            }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockCommon, 1, 2), true, new Object[]{
                    "T#T",
                    "CSC",
                    "TCT",
                    'T', transmit,
                    'C', cable,
                    'S', GameRegistry.findItem("minecraft", "chest"),
                    '#', charger
            }));
            if (PowerConverterCore.powerSystemSteamEnabled) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PowerConverterCore.converterBlockSteam, 1, 0), true, new Object[]{
                        "CPC",
                        "PTP",
                        "CPC",
                        'C', IC2Items.getItem("FluidCell"),
                        'P', bronze,
                        'T', fluid
                }));
            }
        }
    }
}
