package powercrystals.powerconverters.power.rf;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.mods.EnderIO;
import powercrystals.powerconverters.mods.ThermalExpansion;
import powercrystals.powerconverters.power.PowerSystem;

/**
 * @author samrg472
 */
public class ChargeHandlerRF implements IChargeHandler {

    @Override
    public PowerSystem getPowerSystem() {
        if(Loader.isModLoaded("ThermalExpansion")) return ThermalExpansion.INSTANCE.powerSystem;
        if(Loader.isModLoaded("EnderIO")) return EnderIO.INSTANCE.powerSystem;
        return null;
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
