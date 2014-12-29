package powercrystals.powerconverters.power;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.common.BridgeSideData;
import powercrystals.powerconverters.power.base.BlockPowerConverter;
import powercrystals.powerconverters.power.base.TileEntityBridgeComponent;

public abstract class PowerSystem {
    protected String name;
    protected float _internalEnergyPerInput;
    protected float _internalEnergyPerOutput;
    protected String _unit;
    protected String[] voltageNames;
    protected int[] voltageValues;

    public BlockPowerConverter block;
    public Class<? extends ItemBlock> itemBlock;
    public Class<? extends TileEntityBridgeComponent> consumer;
    public Class<? extends TileEntityBridgeComponent> producer;

    public static final String POWERSYSTEM_CATEGORY = "powersystems";

    public abstract String getId();

    public abstract void registerBlocks();
    public abstract void registerCommonRecipes();
    public abstract void loadConfig(Configuration c);
    public abstract void saveConfig(Configuration c);

    public float getInternalEnergyPerInput() {
        return _internalEnergyPerInput;
    }

    public float getInternalEnergyPerOutput() {
        return _internalEnergyPerOutput;
    }

    public String getUnit() {
        return _unit;
    }

    public String[] getVoltageNames() {
        return voltageNames;
    }


	/**
	 * Formats the Rate String for Bridge GUI
	 *
	 * @note will be only called if isConnected
	 *
	 */
	public String getRateString(BridgeSideData data) {
		double rate = data.outputRate;
		
		if(rate > 1000000) {	// mega
			double rateMillion = (rate / 1000000);
			return String.format("%.1f %s%s", rateMillion, "m", this.getUnit());
			
		} else if (rate > 1000) {	// kilo
			double rateThousand = (rate / 1000.0);
			return String.format("%.1f %s%s", rateThousand, "k", this.getUnit());
			
		}
		
		return rate + " " + this.getUnit();
	}

}
