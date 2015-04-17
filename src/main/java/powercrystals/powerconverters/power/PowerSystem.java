package powercrystals.powerconverters.power;

import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import powercrystals.powerconverters.common.BridgeSideData;
import powercrystals.powerconverters.power.base.BlockPowerConverter;
import powercrystals.powerconverters.power.base.TileEntityBridgeComponent;

public abstract class PowerSystem {
    protected String name;
    protected float _internalEnergyPerInput;
    protected float _internalEnergyPerOutput;
    protected float serverInternalEnergyPerInput;
    protected float serverInternalEnergyPerOutput;
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

    public void readEnergyValues(NBTTagCompound nbt) {
        serverInternalEnergyPerInput = nbt.getFloat("Input");
        serverInternalEnergyPerOutput = nbt.getFloat("Output");
    }

    public NBTTagCompound writeEnergyValues() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setFloat("Input", _internalEnergyPerInput);
        nbt.setFloat("Output", _internalEnergyPerOutput);
        return nbt;
    }

    public float getInternalEnergyPerInput(int meta) {
        return _internalEnergyPerInput;
    }

    public float getInternalEnergyPerOutput(int meta) {
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
	 * Will be only called if isConnected
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
