package powercrystals.powerconverters.power.thermalexpansion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;

/**
 * @author samrg472
 */
public class BlockPowerConverterRF extends BlockPowerConverter {

    public BlockPowerConverterRF(int blockId) {
        super(blockId, 2);
        setUnlocalizedName("powerconverters.te");
        setCreativeTab(PCCreativeTab.tab);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        if (metadata == 0) return new TileEntityRFConsumer();
        else if (metadata == 1) return new TileEntityRFProducer();

        return createNewTileEntity(world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        String[] types = { "consumer", "producer" };
        String[] states = { "on", "off" };
        String folderName = getUnlocalizedName().substring("tile.powerconverters.".length());

        int i = 0;
        for(String type : types)
        {
            for(String state : states)
            {
                _icons[i] = ir.registerIcon(String.format("%s:%s/%s_%s", PowerConverterCore.modId, folderName, type, state));
                i++;
            }
        }
    }
}
