package powercrystals.powerconverters.power.systems.rf;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerRedstoneFlux;

/**
 * @author samrg472
 */
public class ChargeHandlerRF implements IChargeHandler {

    @Override
    public PowerSystem getPowerSystem() {
        return PowerSystemManager.getInstance().getPowerSystemByName(PowerRedstoneFlux.id);
    }

    @Override
    public boolean canHandle(ItemStack stack) {
        return stack.getItem() instanceof IEnergyContainerItem;
    }

    @Override
    public int charge(ItemStack stack, int energyInput) {
        final int energyToUse = (int) (energyInput / getPowerSystem().getInternalEnergyPerOutput(0));
        final int energyUsed = ((IEnergyContainerItem) stack.getItem()).receiveEnergy(stack, energyToUse, false);
        return (int) ((energyToUse - energyUsed) * getPowerSystem().getInternalEnergyPerOutput(0));
    }

    @Override
    public int discharge(ItemStack stack, int energyRequest) {
        IEnergyContainerItem cell = (IEnergyContainerItem) stack.getItem();
        return (int) ((cell.extractEnergy(stack, (int) (energyRequest / getPowerSystem().getInternalEnergyPerOutput(0)), false)) * getPowerSystem().getInternalEnergyPerOutput(0));
    }
}
