package powercrystals.powerconverters.integration.waila;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.impl.ConfigHandler;

import powercrystals.powerconverters.common.BlockPowerConverterCommon;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.base.BlockPowerConverter;
import powercrystals.powerconverters.power.base.TileEntityBridgeComponent;

import java.text.DecimalFormat;
import java.util.List;


public class WailaCompat implements IWailaDataProvider {
	public static final WailaCompat INSTANCE = new WailaCompat();
	public static final DecimalFormat format = new DecimalFormat("###.##");
	

	/** 
	 * Registers Data Providers @ Waila
	 *
	 * @note called by Waila, caused by IMC to Waila in preInit
	 *
	 */
	public static void onWailaRegister(IWailaRegistrar registrar) {
		
		// BlockPowerConverterCommon is used by Charger and Bridge Main Part	
		registrar.registerHeadProvider(INSTANCE, BlockPowerConverterCommon.class );
		registrar.registerBodyProvider(INSTANCE, BlockPowerConverterCommon.class );
		registrar.registerTailProvider(INSTANCE, BlockPowerConverterCommon.class );

		// BlockPowerConverter is used by all Consumers and Producers
		registrar.registerHeadProvider(INSTANCE, BlockPowerConverter.class );
		registrar.registerBodyProvider(INSTANCE, BlockPowerConverter.class );
		registrar.registerTailProvider(INSTANCE, BlockPowerConverter.class );		    
	}
       
       	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}
	
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}	

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {	
        TileEntity te = accessor.getTileEntity();
        
        if(te == null)
        	return currenttip;
        
        if(te instanceof TileEntityBridgeComponent<?>){
        	// Producer / Consumer
			currenttip.add(EnumChatFormatting.YELLOW + "Yey a Component!");

        }else if(te instanceof TileEntityEnergyBridge){
        	//
        	// Bridge
        	//
        	TileEntityEnergyBridge bridge = (TileEntityEnergyBridge)te;
        	
			// isInputLimited?
			if(bridge.isInputLimited()) {
				currenttip.add( StatCollector.translateToLocal("powerconverters.inputlimited") );
				
			} else {
				currenttip.add( StatCollector.translateToLocal("powerconverters.outputlimited") );
				
			}
			
			// Charge level
			{
				float percentage = 100F * (((float) bridge.getEnergyStored()) / ((float) bridge.getEnergyStoredMax()));
				currenttip.add( "Buffer: "+format.format(percentage) + "% " + StatCollector.translateToLocal("powerconverters.percentfull"));	//"
			}
			
        }
       
		return currenttip;	
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return currenttip;
	}


	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z){
		return tag;
	}
	

}
