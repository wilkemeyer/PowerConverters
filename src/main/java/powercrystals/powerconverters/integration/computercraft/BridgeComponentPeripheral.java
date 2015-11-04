package powercrystals.powerconverters.integration.computercraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.base.TileEntityBridgeComponent;
import powercrystals.powerconverters.power.PowerSystem;

import powercrystals.powerconverters.integration.computercraft.BridgeHelper;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

import java.util.Map;
import java.util.HashMap;

public class BridgeComponentPeripheral implements IPeripheral {
	TileEntityBridgeComponent te;

	public BridgeComponentPeripheral(TileEntityBridgeComponent tile){
		this.te = tile;
	}
	
	@Override
	public String getType(){
		return "bridgecomponent";
	}
	
	@Override 
	public String[] getMethodNames(){
		return new String[]{"getBridgeData", "getData", "getSideData"};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arg) throws LuaException {
		
		switch(method){
			case 0: {		// getData() 
							// Returns: just the bridge statistic data
			
				TileEntityEnergyBridge eb = te.getFirstBridge();
				if(eb == null){
					throw new LuaException("Energy Bridge Components must be connected to an EnergyBridge");
				}			
			
				Map ret = new HashMap();
				
				BridgeHelper.addBridgeDataToMap(eb, ret);
			
			return new Object[]{ret};
			}// end: case 0 (getData)
		
			/////////////////////////////////////
			/////////////////////////////////////
			
			case 1: { 	// getData()
						// Returns all statistical data of this bridge, 
						// including component statistics for all connected components.
			
				TileEntityEnergyBridge eb = te.getFirstBridge();
				if(eb == null){
					throw new LuaException("Energy Bridge Components must be connected to an EnergyBridge");
				}			
									
				Map ret = new HashMap();
									
				// Bridge Data
				BridgeHelper.addBridgeDataToMap(eb, ret);
				
				
				// Side Data
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						// No Component Present
						ret.put(dir.toString().toLowerCase(),  BridgeHelper.getSideData(eb, dir) );	
				}
			
			return new Object[]{ret};
			}//end: case 1 (getAllData())
			
			/////////////////////////////////////
			/////////////////////////////////////
			
			case 2: {	// getDataForSide()
						// Returns staistical data of the component of the requested side.
						//
			
				// we're expectring one argument - the side name - 
				if(arg.length != 1 || !(arg[0] instanceof String) ){
					throw new LuaException("Expected one Argument: Side (Example: getSideData('NORTH') )");
				}
			
				TileEntityEnergyBridge eb = te.getFirstBridge();
				if(eb == null){
					throw new LuaException("Energy Bridge Components must be connected to an EnergyBridge");
				}			
			
			
			return new Object[]{BridgeHelper.getSideData(eb, ForgeDirection.valueOf( ((String)arg[0]).toUpperCase() ) )};	//end: case 0	
			}//end: case 2 (getSideData())
			
		}
		
		return null;
	}
	
	@Override
	public void attach(IComputerAccess computer) {
		
	}

	@Override
	public void detach(IComputerAccess computer) {
		
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}	

}
