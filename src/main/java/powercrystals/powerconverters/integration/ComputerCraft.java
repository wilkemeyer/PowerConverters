package powercrystals.powerconverters.integration;

import powercrystals.powerconverters.integration.computercraft.PeripheralHandler;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

//
public class ComputerCraft { 

	public static void init(){
			IPeripheralProvider h = new PeripheralHandler();
			
			ComputerCraftAPI.registerPeripheralProvider(h);
			
	}

}
