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

public class BlockGregTechConsumer extends BlockPowerConverter {
    public BlockGregTechConsumer() {
        super(18);
        setBlockName("powerconverters.gt.consumer");
        setCreativeTab(PCCreativeTab.tab);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityGregTechConsumer(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        String[] voltages = {"ulv", "lv", "mv", "hv", "ev", "iv", "luv", "zpmv", "uv"};
        String type = "consumer";
        String[] states = {"off", "on"};
        String folderName = getUnlocalizedName().substring("tile.powerconverters.".length());
        folderName = folderName.substring(0, folderName.length() - type.length() - 1); // Strip out type

        int i = 0;
        for (String voltage : voltages) {
            for (String state : states) {
                _icons[i] = ir.registerIcon(String.format("%s:%s/%s/%s_%s", PowerConverterCore.modId, folderName, voltage, type, state));
                i++;
            }
        }
    }
}
