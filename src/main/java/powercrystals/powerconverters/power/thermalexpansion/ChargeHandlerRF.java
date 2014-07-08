package powercrystals.powerconverters.power.thermalexpansion;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.mods.ThermalExpansion;
import powercrystals.powerconverters.power.PowerSystem;

/**
 * @author samrg472
 */
public class ChargeHandlerRF implements IChargeHandler {

    @Override
    public PowerSystem getPowerSystem() {
        return ThermalExpansion.INSTANCE.powerSystem;
    }

    @Override
    public boolean canHandle(ItemStack stack) {
        return stack.getItem() instanceof IEnergyContainerItem;
    }

    @Override
    public int charge(ItemStack stack, int energyInput) {
        final int energyToUse = (int) (energyInput / getPowerSystem().getInternalEnergyPerOutput());
        final int energyUsed = ((IEnergyContainerItem) stack.getItem()).receiveEnergy(stack, energyToUse, false);
        return (int) ((energyToUse - energyUsed) * getPowerSystem().getInternalEnergyPerOutput());
    }

    @Override
    public int discharge(ItemStack stack, int energyRequest) {
        IEnergyContainerItem cell = (IEnergyContainerItem) stack.getItem();
        return (int) ((cell.extractEnergy(stack, (int) (energyRequest / getPowerSystem().getInternalEnergyPerOutput()), false)) * getPowerSystem().getInternalEnergyPerOutput());
    }
}
