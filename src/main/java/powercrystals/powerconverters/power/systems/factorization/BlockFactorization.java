package powercrystals.powerconverters.power.systems.factorization;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.base.BlockPowerConverter;

/**
 * Block for Factorization consumer and producer.
 */
public class BlockFactorization extends BlockPowerConverter {
    public BlockFactorization() {
        super(2);
        setBlockName("powerconverters.factorization");
        setCreativeTab(PCCreativeTab.tab);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        if (metadata == 0) return new TileEntityFactorizationConsumer();
        else if (metadata == 1) return new TileEntityFactorizationProducer();

        return createNewTileEntity(world, metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        String[] types = {"consumer", "producer"};
        String[] states = {"off", "on"};
        String folderName = getUnlocalizedName().substring("tile.powerconverters.".length());

        int i = 0;
        for (String type : types) {
            for (String state : states) {
                _icons[i] = ir.registerIcon(String.format("%s:%s/%s_%s", PowerConverterCore.modId, folderName, type, state));
                i++;
            }
        }
    }
}
