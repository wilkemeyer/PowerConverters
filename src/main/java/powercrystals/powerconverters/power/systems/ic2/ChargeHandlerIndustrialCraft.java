package powercrystals.powerconverters.power.systems.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerIndustrialcraft;

public class ChargeHandlerIndustrialCraft implements IChargeHandler {
    @Override
    public PowerSystem getPowerSystem() {
        return PowerSystemManager.getInstance().getPowerSystemByName(PowerIndustrialcraft.id);
    }

    @Override
    public boolean canHandle(ItemStack stack) {
        return stack != null && stack.getItem() instanceof IElectricItem;
    }

    @Override
    public int charge(ItemStack stack, int energyInput) {
        if (stack != null && stack.getItem() instanceof IElectricItem) {
            IElectricItem electricItem = (IElectricItem) stack.getItem();
            PowerIndustrialcraft ic2 = (PowerIndustrialcraft) getPowerSystem();
            double eu = energyInput / ic2.getInternalEnergyPerOutput(0);
            eu -= ElectricItem.manager.charge(stack, eu, electricItem.getTier(stack), false, false);
            return (int) (eu * ic2.getInternalEnergyPerOutput(0));
        }
        return 0;
    }

    @Override
    public int discharge(ItemStack stack, int energyRequest) {
        if (stack != null && stack.getItem() instanceof IElectricItem) {
            IElectricItem electricItem = (IElectricItem) stack.getItem();
            PowerIndustrialcraft ic2 = (PowerIndustrialcraft) getPowerSystem();
            double eu = energyRequest / ic2.getInternalEnergyPerOutput(0);
            eu = ElectricItem.manager.discharge(stack, eu, electricItem.getTier(stack), false, false, false);
            return (int) (eu * ic2.getInternalEnergyPerInput(0));
        }
        return 0;
    }
}
