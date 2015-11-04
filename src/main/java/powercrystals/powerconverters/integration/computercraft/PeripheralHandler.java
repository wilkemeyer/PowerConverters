package powercrystals.powerconverters.integration.computercraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.base.TileEntityBridgeComponent;
import powercrystals.powerconverters.integration.computercraft.EnergyBridgePeripheral;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

public class PeripheralHandler implements IPeripheralProvider {

	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileEntityEnergyBridge){
			return new EnergyBridgePeripheral((TileEntityEnergyBridge)tile);
		
		} else if(tile instanceof TileEntityBridgeComponent){
			return new BridgeComponentPeripheral((TileEntityBridgeComponent)tile);
			
		}
		
		
		
		return null;
	}
}