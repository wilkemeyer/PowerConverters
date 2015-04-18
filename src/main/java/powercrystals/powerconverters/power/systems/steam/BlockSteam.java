package powercrystals.powerconverters.power.systems.steam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.base.BlockPowerConverter;
import powercrystals.powerconverters.power.base.TileEntityBridgeComponent;
import powercrystals.powerconverters.power.systems.PowerSteam;

public class BlockSteam extends BlockPowerConverter {
    public BlockSteam() {
        super(2);
        setBlockName("powerconverters.steam");
        setCreativeTab(PCCreativeTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int offset = ((TileEntityBridgeComponent<?>) world.getTileEntity(x, y, z)).isSideConnectedClient(side) ? 1 : 0;
        return _icons[(world.getBlockMetadata(x, y, z) == 0 ? 0 : 1) * 2 + offset];
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return _icons[(metadata == 0 ? 0 : 1) * 2];
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        PowerSteam steam = (PowerSteam) PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id);
        if (metadata == 0) return new TileEntitySteamConsumer();
        else return new TileEntitySteamProducer(metadata - 1);
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
