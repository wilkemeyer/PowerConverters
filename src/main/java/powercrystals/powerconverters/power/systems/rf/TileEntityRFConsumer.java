package powercrystals.powerconverters.power.systems.rf;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.base.TileEntityEnergyConsumer;
import powercrystals.powerconverters.power.systems.PowerRedstoneFlux;

/**
 * @author samrg472
 */
public class TileEntityRFConsumer extends TileEntityEnergyConsumer<IEnergyConnection> implements IEnergyReceiver {

    private int lastReceivedRF;

    public TileEntityRFConsumer() {
        super(PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id), 0, IEnergyConnection.class);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public double getInputRate() {
        int last = lastReceivedRF;
        lastReceivedRF = 0;
        return last;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        TileEntityEnergyBridge bridge = getFirstBridge();
        if (bridge == null)
            return 0;
        float energyToReceive = getPowerSystem().getInternalEnergyPerInput() * maxReceive;
        int received = (int) (energyToReceive - storeEnergy(energyToReceive, simulate));
        if (!simulate) {
            lastReceivedRF = (int) (received / getPowerSystem().getInternalEnergyPerInput());
            return lastReceivedRF;
        }
        return (int) (received / getPowerSystem().getInternalEnergyPerInput());
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
        return (int) (bridge.getEnergyStored() / getPowerSystem().getInternalEnergyPerInput());
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        TileEntityEnergyBridge bridge = getFirstBridge();
        if (bridge == null)
            return 0;
        return (int) (bridge.getEnergyStoredMax() / getPowerSystem().getInternalEnergyPerInput());
    }
}
