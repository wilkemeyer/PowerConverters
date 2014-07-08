package powercrystals.powerconverters.power.ic2;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPowerConverterIndustrialCraft extends BlockPowerConverter
{
    public BlockPowerConverterIndustrialCraft()
    {
	super(10);
	setBlockName("powerconverters.ic2");
	setCreativeTab(PCCreativeTab.tab);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
	if (metadata == 0)
	    return new TileEntityIndustrialCraftConsumer(0);
	else if (metadata == 1)
	    return new TileEntityIndustrialCraftProducer(0);
	else if (metadata == 2)
	    return new TileEntityIndustrialCraftConsumer(1);
	else if (metadata == 3)
	    return new TileEntityIndustrialCraftProducer(1);
	else if (metadata == 4)
	    return new TileEntityIndustrialCraftConsumer(2);
	else if (metadata == 5)
	    return new TileEntityIndustrialCraftProducer(2);
	else if (metadata == 6)
	    return new TileEntityIndustrialCraftConsumer(3);
	else if (metadata == 7)
	    return new TileEntityIndustrialCraftProducer(3);
	else if (metadata == 8)
	    return new TileEntityIndustrialCraftConsumer(4);
	else if (metadata == 9)
	    return new TileEntityIndustrialCraftProducer(4);

	return createNewTileEntity(world, metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir)
    {
        String[] voltages = { "lv", "mv", "hv", "ev", "uv" };
        String[] types = { "consumer", "producer" };
        String[] states = { "off", "on" };
        String folderName = getUnlocalizedName().substring("tile.powerconverters.".length());

        int i = 0;
        for(String voltage : voltages)
        {
            for(String type : types)
            {
                for(String state : states)
                {
                    _icons[i] = ir.registerIcon(String.format("%s:%s/%s/%s_%s", PowerConverterCore.modId, folderName, voltage, type, state));
                    i++;
                }
            }
        }
    }
}
