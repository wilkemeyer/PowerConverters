package powercrystals.powerconverters.integration.computercraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.common.BridgeSideData;
import powercrystals.powerconverters.power.PowerSystem;


import java.util.Map;
import java.util.HashMap;


public class BridgeHelper {

	public static Map getSideData(TileEntityEnergyBridge te, ForgeDirection dir) {
		BridgeSideData data = te.getDataForSide(dir);
		
		if (data != null && (data.isConsumer || data.isProducer) && data.powerSystem != null) {
			// Component Present
			Map sideData = new HashMap();
			
			///
			sideData.put("isConsumer",	data.isConsumer);
			sideData.put("isProducer",	data.isProducer);
			sideData.put("id", 			data.powerSystem.getId());
			sideData.put("unit",		data.powerSystem.getUnit());

			if (data.powerSystem.getVoltageNames() != null) {
				sideData.put("voltageName", 		data.powerSystem.getVoltageNames()[data.voltageNameIndex]);
			}
			
			sideData.put("isConnected", data.isConnected);
			
			if(data.isConnected){
				// connected
				sideData.put("rate", data.outputRate);
				
			}else{
				// disconnected
				sideData.put("rate", 0.0);
				
			}
			
			return sideData;
		}
		
		
		// if no component present @ requested side:
		return null;
	}//end: getSideData()
	
	
	public static void addBridgeDataToMap(TileEntityEnergyBridge te, Map m){
			// Bridge Data
			m.put("energyStored",		te.getEnergyStored() );
			m.put("energyStoredMax",	te.getEnergyStoredMax() );
			m.put("isInputLimited",		te.isInputLimited() );	
	}//end: addBridgeDataToMap()

}

