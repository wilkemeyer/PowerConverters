package powercrystals.powerconverters.power.ic2;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.mods.IndustrialCraft;
import powercrystals.powerconverters.power.PowerSystem;

public class ChargeHandlerIndustrialCraft implements IChargeHandler {
    @Override
    public PowerSystem getPowerSystem() {
        return IndustrialCraft.INSTANCE.powerSystem;
    }

    @Override
    public boolean canHandle(ItemStack stack) {
        return stack != null && stack.getItem() instanceof IElectricItem;
    }

    @Override
    public int charge(ItemStack stack, int energyInput) {
        if(stack != null && stack.getItem() instanceof IElectricItem) {
            IElectricItem electricItem = (IElectricItem) stack.getItem();
            double eu = energyInput / IndustrialCraft.INSTANCE.powerSystem.getInternalEnergyPerOutput();
            eu -= ElectricItem.manager.charge(stack, eu, electricItem.getTier(stack), false, false);
            return (int) (eu * IndustrialCraft.INSTANCE.powerSystem.getInternalEnergyPerOutput());
        }
        return 0;
    }

    @Override
    public int discharge(ItemStack stack, int energyRequest) {
        if(stack != null && stack.getItem() instanceof IElectricItem) {
            IElectricItem electricItem = (IElectricItem) stack.getItem();
            double eu = energyRequest / IndustrialCraft.INSTANCE.powerSystem.getInternalEnergyPerOutput();
            eu = ElectricItem.manager.discharge(stack, eu, electricItem.getTier(stack), false, false, false);
            return (int) (eu * IndustrialCraft.INSTANCE.powerSystem.getInternalEnergyPerInput());
        }
        return 0;
    }
}
