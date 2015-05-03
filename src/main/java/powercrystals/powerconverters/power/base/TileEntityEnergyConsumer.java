package powercrystals.powerconverters.power.base;

import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.PowerSystem;

import java.util.Map.Entry;

public abstract class TileEntityEnergyConsumer<T> extends TileEntityBridgeComponent<T> {
    public TileEntityEnergyConsumer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass) {
        super(powerSystem, voltageNameIndex, adjacentClass);
    }

    /**
     * @param energy   amount of energy to store
     * @param simulate whether to actually store the energy
     * @return energy left over
     */
    protected double storeEnergy(double energy, boolean simulate) {
        boolean powered = getWorldObj().getStrongestIndirectPower(xCoord, yCoord, zCoord) > 0;
        if(!powered) {
            for (Entry<ForgeDirection, TileEntityEnergyBridge> bridge : getBridges().entrySet()) {
                energy = bridge.getValue().storeEnergy(energy, simulate);
                if (energy <= 0) {
                    return 0;
                }
            }
        }
        return energy;
    }

    protected int getTotalEnergyDemand() {
        int demand = 0;

        boolean powered = getWorldObj().getStrongestIndirectPower(xCoord, yCoord, zCoord) > 0;
        if(!powered) {
            for (Entry<ForgeDirection, TileEntityEnergyBridge> bridge : getBridges().entrySet()) {
                demand += (bridge.getValue().getEnergyStoredMax() - bridge.getValue().getEnergyStored());
            }
        }

        return demand;
    }

    public abstract double getInputRate();
}
