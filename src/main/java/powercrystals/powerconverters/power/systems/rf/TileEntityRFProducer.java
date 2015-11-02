package powercrystals.powerconverters.power.systems.rf;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.position.BlockPosition;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.base.TileEntityEnergyProducer;
import powercrystals.powerconverters.power.systems.PowerRedstoneFlux;

import java.util.List;

/**
 * @author samrg472
 */
public class TileEntityRFProducer extends TileEntityEnergyProducer<IEnergyConnection> implements IEnergyProvider {

    public TileEntityRFProducer() {
        super(PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id), 0, IEnergyConnection.class);
    }

    @Override
    public double produceEnergy(double energy) {
    	final double tmpEnergyPerOutput = getPowerSystem().getInternalEnergyPerOutput(0);
        final double energyToUse = energy / tmpEnergyPerOutput;
        boolean powered = getWorldObj().getStrongestIndirectPower(xCoord, yCoord, zCoord) > 0;

        if (!powered && energyToUse > 0) {
            List<BlockPosition> positions = new BlockPosition(xCoord, yCoord, zCoord).getAdjacent(true);
            
            for (BlockPosition p : positions) {
                TileEntity te = worldObj.getTileEntity(p.x, p.y, p.z);
                if(te instanceof TileEntityRFConsumer || te instanceof TileEntityEnergyBridge)
                	continue;
				
	            if(te instanceof IEnergyHandler) {
					IEnergyHandler eHandler = (IEnergyHandler) te;
					final double received = eHandler.receiveEnergy(p.orientation.getOpposite(), (int) (energyToUse), false);
					energy -= (received * tmpEnergyPerOutput);
					if (energy <= 0)
						break;
						
	            }else if(te instanceof IEnergyReceiver){
					IEnergyReceiver eReceiver = (IEnergyReceiver) te;
					final double received = eReceiver.receiveEnergy(p.orientation.getOpposite(), (int) (energyToUse), false);
					energy -= (received * tmpEnergyPerOutput);
					if (energy <= 0)
						break;
					
				}	                	
            }
        }

        return energy;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }
	
    @Override
    public int getEnergyStored(ForgeDirection from) {
        TileEntityEnergyBridge bridge = getFirstBridge();
        if (bridge == null)
            return 0;
        return (int) (bridge.getEnergyStored() / getPowerSystem().getInternalEnergyPerInput(0));
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        TileEntityEnergyBridge bridge = getFirstBridge();
        if (bridge == null)
            return 0;
        return (int) (bridge.getEnergyStoredMax() / getPowerSystem().getInternalEnergyPerInput(0));
    }
}
