package powercrystals.powerconverters.power.systems.fortron;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.position.BlockPosition;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.base.TileEntityEnergyProducer;
import powercrystals.powerconverters.power.systems.PowerFortron;

import resonant.api.mffs.fortron.IFortronStorage;

import java.util.Map;
import java.util.Map.Entry;



public class TileEntityFortronProducer extends TileEntityEnergyProducer<IFortronStorage> {

    public TileEntityFortronProducer() {
        super(PowerSystemManager.getInstance().getPowerSystemByName(PowerFortron.id), 0, IFortronStorage.class);
    }

    @Override
    public double produceEnergy(double energy) {
        final double energyToUse = energy / getPowerSystem().getInternalEnergyPerOutput();

        if (energyToUse > 0) {
        	for (Entry<ForgeDirection, IFortronStorage> it : this.getTiles().entrySet()) {
        		
				IFortronStorage fs = (IFortronStorage)it.getValue();
				final double used = fs.provideFortron( (int)energyToUse, true );
        		
        		energy -= (used * getPowerSystem().getInternalEnergyPerOutput() );
        		
        		if(energy <= 0)
        			break;
  				      	
        	}
        }

        return energy;
    }


}
