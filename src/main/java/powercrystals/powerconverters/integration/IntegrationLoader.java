package powercrystals.powerconverters.integration;


import powercrystals.powerconverters.PowerConverterCore;

import powercrystals.powerconverters.integration.ComputerCraft;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.common.config.Configuration;


public class IntegrationLoader {
	protected static Boolean ccEnabled;

	public static void init(FMLPreInitializationEvent event)
	{
		// CC
		if(ccEnabled && Loader.isModLoaded("ComputerCraft")){
			ComputerCraft.init();
			printLoadedStatus("ComputerCraft");
		}
		
	}
	
	public static void printLoadedStatus(String name){
		PowerConverterCore.instance.logger.info("Enabled " + name + " Integration");
	}
	
	
	
	public static void loadConfig(Configuration c){
		//
		ccEnabled = c.get("Integration", "ComputerCraft", true).getBoolean();
	}

}
