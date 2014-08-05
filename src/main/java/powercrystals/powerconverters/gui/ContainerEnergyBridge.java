package powercrystals.powerconverters.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.common.BridgeSideData;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import powercrystals.powerconverters.power.PowerSystemManager;

import java.util.HashMap;
import java.util.Map;

public class ContainerEnergyBridge extends Container {
    private static enum _sideData {
        VOLTAGE_INDEX,
        IS_CONSUMER,
        IS_PRODUCER,
        POWER_SYSTEM_ID,
        IS_CONNECTED,

        /*
         * Int is broken up into shorts (see ICrafting)
         */
        OUTPUT_RATE_HIGH, // HIGH BITS (break up int into shorts)
        OUTPUT_RATE_LOW, // LOW BITS (break up int into shorts)
        ENERGY_STORED_HIGH,
        ENERGY_STORED_LOW
    }

    private static final int _flagOffset = 1000;

    private static enum _otherData {
        INPUT_LIMITED(1000),
        /*
         * Ints are broken up into shorts (see ICrafting)
         */
        ENERGY_SCALED_HIGH(1001),
        ENERGY_SCALED_LOW(1002);

        int value;

        _otherData(int newValue) {
            value = newValue;
        }

        int getValue() {
            return value;
        }
    }

    private static final Map<Integer, _otherData> _otherDataMap = new HashMap<Integer, _otherData>();

    static {
        for (_otherData data : _otherData.values()) {
            _otherDataMap.put(data.getValue(), data);
        }
    }

    private TileEntityEnergyBridge _bridge;

    public ContainerEnergyBridge(TileEntityEnergyBridge bridge, InventoryPlayer inv) {
        _bridge = bridge;
        bindPlayerInventory(inv);
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }

    @Override
    public void updateProgressBar(int var, int value) {
        if (var < _flagOffset) {
            int outputRateCombined, energyStoredCombined;

            ForgeDirection dir = ForgeDirection.getOrientation(var / _sideData.values().length);
            BridgeSideData sideData = _bridge.getDataForSide(dir);
            int sideVar = var % _sideData.values().length;
            switch (_sideData.values()[sideVar]) {
                case VOLTAGE_INDEX:
                    sideData.voltageNameIndex = value;
                    break;
                case IS_CONSUMER:
                    sideData.isConsumer = (value != 0);
                    break;
                case IS_PRODUCER:
                    sideData.isProducer = (value != 0);
                    break;
                case POWER_SYSTEM_ID:
                    sideData.powerSystem = PowerSystemManager.getInstance().getPowerSystemById(value);
                    break;
                case IS_CONNECTED:
                    sideData.isConnected = (value != 0);
                    break;
                case OUTPUT_RATE_HIGH:
                    int outputRateLow = ((int) sideData.outputRate) & 0xFFFF;
                    outputRateCombined = (value << 16) | outputRateLow;
                    sideData.outputRate = outputRateCombined;
                    break;
                case OUTPUT_RATE_LOW:
                    int outputRateHigh = ((int) sideData.outputRate) & 0xFFFF0000;
                    outputRateCombined = outputRateHigh | (value & 0xFFFF);
                    sideData.outputRate = outputRateCombined;
                    break;
                case ENERGY_STORED_HIGH:
                    int energyStoredLow = _bridge.getEnergyStored() & 0xFFFF;
                    energyStoredCombined = (value << 16) | energyStoredLow;
                    _bridge.setEnergyStored(energyStoredCombined);
                    break;
                case ENERGY_STORED_LOW:
                    int energyStoredHigh = _bridge.getEnergyStored() & 0xFFFF0000;
                    energyStoredCombined = energyStoredHigh | (value & 0xFFFF);
                    _bridge.setEnergyStored(energyStoredCombined);
                    break;
            }
        } else {
            int energyScaledCombined;
            switch (_otherDataMap.get(var)) {
                case INPUT_LIMITED:
                    _bridge.setIsInputLimited(value != 0);
                    break;
                case ENERGY_SCALED_HIGH:
                    int energyScaledLow = _bridge.getEnergyScaled() & 0xFFFF;
                    energyScaledCombined = (value << 16) | energyScaledLow;
                    _bridge.setEnergyScaled(energyScaledCombined);
                    break;
                case ENERGY_SCALED_LOW:
                    int energyScaledHigh = _bridge.getEnergyScaled() & 0xFFFF0000;
                    energyScaledCombined = energyScaledHigh | (value & 0xFFFF);
                    _bridge.setEnergyScaled(energyScaledCombined);
                    break;
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int side = 0; side < 6; side++) {
            ForgeDirection d = ForgeDirection.getOrientation(side);
            BridgeSideData data = _bridge.getDataForSide(d);
            for (Object _crafter : crafters) {
                ICrafting crafter = (ICrafting) _crafter;
                int sideVal = side * _sideData.values().length;
                crafter.sendProgressBarUpdate(this, sideVal + _sideData.VOLTAGE_INDEX.ordinal(), data.voltageNameIndex);
                crafter.sendProgressBarUpdate(this, sideVal + _sideData.IS_CONSUMER.ordinal(), data.isConsumer ? 1 : 0);
                crafter.sendProgressBarUpdate(this, sideVal + _sideData.IS_PRODUCER.ordinal(), data.isProducer ? 1 : 0);
                if (data.powerSystem != null) {
                    crafter.sendProgressBarUpdate(this, sideVal + _sideData.POWER_SYSTEM_ID.ordinal(), PowerSystemManager.getInstance().getPowerSystemId(data.powerSystem.getId()));
                }
                crafter.sendProgressBarUpdate(this, sideVal + _sideData.IS_CONNECTED.ordinal(), data.isConnected ? 1 : 0);
                crafter.sendProgressBarUpdate(this, sideVal + _sideData.OUTPUT_RATE_HIGH.ordinal(), (short) (((int) data.outputRate) >> 16));
                crafter.sendProgressBarUpdate(this, sideVal + _sideData.OUTPUT_RATE_LOW.ordinal(), (short) (((int) data.outputRate) & 0xFFFF));
                crafter.sendProgressBarUpdate(this, _sideData.ENERGY_STORED_HIGH.ordinal(), (short) (_bridge.getEnergyStored() >> 16));
                crafter.sendProgressBarUpdate(this, _sideData.ENERGY_STORED_LOW.ordinal(), (short) (_bridge.getEnergyStored() & 0xFFFF));
            }
        }

        for (Object _crafter : crafters) {
            ICrafting crafter = (ICrafting) _crafter;
            crafter.sendProgressBarUpdate(this, _otherData.INPUT_LIMITED.getValue(), _bridge.isInputLimited() ? 1 : 0);
            crafter.sendProgressBarUpdate(this, _otherData.ENERGY_SCALED_HIGH.getValue(), (short) (_bridge.getEnergyScaled() >> 16));
            crafter.sendProgressBarUpdate(this, _otherData.ENERGY_SCALED_LOW.getValue(), (short) (_bridge.getEnergyScaled() & 0xFFFF));
        }
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
        }
        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 171));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }
}
