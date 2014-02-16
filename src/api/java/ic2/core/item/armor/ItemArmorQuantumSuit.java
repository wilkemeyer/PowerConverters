package ic2.core.item.armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemArmorQuantumSuit extends Item {
    public ItemArmorQuantumSuit(int par1) {
        super(par1);
    }

    public boolean hasColor(ItemStack itemstack) {
        return false;
    }

    public int getColor(ItemStack itemstack) {
        return 0;
    }

    public void colorQArmor(ItemStack itemstack, int i1) {
    }
}
