package powercrystals.powerconverters.power.systems.gt5;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.util.StatCollector;
import net.minecraft.util.ChatComponentText;
import net.minecraft.entity.player.EntityPlayer;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.base.BlockPowerConverter;

public class BlockGregTech extends BlockPowerConverter {
    public BlockGregTech() {
        super(18);
        setBlockName("powerconverters.gt");
        setCreativeTab(PCCreativeTab.tab);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        if (metadata == 0)
            return new TileEntityGregTechConsumer(0);
        else if (metadata == 1)
            return new TileEntityGregTechProducer(0);
        else if (metadata == 2)
            return new TileEntityGregTechConsumer(1);
        else if (metadata == 3)
            return new TileEntityGregTechProducer(1);
        else if (metadata == 4)
            return new TileEntityGregTechConsumer(2);
        else if (metadata == 5)
            return new TileEntityGregTechProducer(2);
        else if (metadata == 6)
            return new TileEntityGregTechConsumer(3);
        else if (metadata == 7)
            return new TileEntityGregTechProducer(3);
        else if (metadata == 8)
            return new TileEntityGregTechConsumer(4);
        else if (metadata == 9)
            return new TileEntityGregTechProducer(4);
		else if (metadata == 10)
			return new TileEntityGregTechConsumer(5);
		else if (metadata == 11)
			return new TileEntityGregTechProducer(5);
		else if (metadata == 12)
			return new TileEntityGregTechConsumer(6);
		else if (metadata == 13)
			return new TileEntityGregTechProducer(6);
		else if (metadata == 14)
			return new TileEntityGregTechConsumer(7);
		else if (metadata == 15)
			return new TileEntityGregTechProducer(7);
		else if (metadata == 16)
			return new TileEntityGregTechConsumer(8);
		else if (metadata == 17)
			return new TileEntityGregTechProducer(8);
			
        return createNewTileEntity(world, metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        String[] voltages = {"ulv", "lv", "mv", "hv", "ev", "iv", "luv", "zpmv", "uv"};
        String[] types = {"consumer", "producer"};
        String[] states = {"off", "on"};
        String folderName = getUnlocalizedName().substring("tile.powerconverters.".length());

        int i = 0;
        for (String voltage : voltages) {
            for (String type : types) {
                for (String state : states) {
                    _icons[i] = ir.registerIcon(String.format("%s:%s/%s/%s_%s", PowerConverterCore.modId, folderName, voltage, type, state));
                    i++;
                }
            }
        }
    }

    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity te = world.getTileEntity(x, y, z);		
		
		if( !world.isRemote && te instanceof TileEntityGregTechProducer) {
			TileEntityGregTechProducer producer = (TileEntityGregTechProducer)te;			
						    
			if(player.isSneaking()){
				long newAmperage = producer.incMaxAmperage();			
				player.addChatMessage( new ChatComponentText( StatCollector.translateToLocalFormatted("powerconverters.gt.producer.amperageChanged", new Object[]{ newAmperage }) ));
			
			}
		} else {
			return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		}	
		
		return false;
	}
	
}
