package powercrystals.powerconverters.power.systems.gt5;

import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.metatileentity.BaseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.position.BlockPosition;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.systems.PowerGregTech5;


public class TileEntityGregTechConsumer extends BaseGTConsumerTileEntity<IEnergyConnected> implements IEnergyConnected {
	private static final long[] typeVoltageIndex = new long[]{8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288};
	private byte voltageIndex;
	private long maxSafeVoltage;
	private byte gtTileColor;   
	private double _euLastTick;
    private long _lastTickInjected;
    private boolean needsBlockUpdate = true;
    
    @SuppressWarnings("unused")
    public TileEntityGregTechConsumer() {
        this(0);
    }

    public TileEntityGregTechConsumer(int voltageIndex) {
        super(PowerSystemManager.getInstance().getPowerSystemByName(PowerGregTech5.id), voltageIndex, IEnergyConnected.class);
		
		setVoltageByIndex(voltageIndex);        
		setColorization((byte)-1);
    }
	
	private void setVoltageByIndex(int vi) {
		if(vi > 8)
			vi = 0;
		
		voltageIndex = (byte)vi;
		maxSafeVoltage = typeVoltageIndex[voltageIndex];
	}

    @Override
    public void updateEntity() {
        super.updateEntity();
        
        if(!worldObj.isRemote){
        
        	if (worldObj.getWorldTime() - _lastTickInjected > 2) {
           		_euLastTick = 0;
           	}
           	
           	if(needsBlockUpdate) {
				
           		// GT's TE caches which surrounding TE's are present
				// so this is required, otherwise a Consumer placed next to
				// an already existing Cable would'nt be provided with energy
				// as the Cable's TE would assume we're not present at all.
				for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {

					TileEntity te = BlockPosition.getAdjacentTileEntity(this, d);
					if(te instanceof BaseTileEntity){
						((BaseTileEntity)te).onAdjacentBlockChange(xCoord, yCoord, zCoord);
					}
	        	}
				
				// Well, we've also to invalidate/refresh our neighbour cache again	        	
				onNeighboorChanged();
					                     	
				needsBlockUpdate = false;
			}
		}			                            
    }

    @Override
    public void validate() {
        super.validate();
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public double getInputRate() {
        return _euLastTick;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        setVoltageByIndex( tag.getByte("voltageIndex") );
        setColorization( tag.getByte("gtTileColor") );
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setByte("voltageIndex", voltageIndex);
        tag.setByte("gtTileColor", gtTileColor);
    }

    private void onOvervoltage() {
    	// @TODO: Implement proper explosion, the whole bridge should explode.
    	
    	//
    	Block b = worldObj.getBlock(xCoord, yCoord, zCoord);
    	
        b.dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, worldObj.getBlockMetadata(xCoord, yCoord, zCoord), 0);
        worldObj.setBlockToAir(xCoord, yCoord, zCoord);    		
    }

    /** GregTech API Part **/
    @Override
    public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
    	double dInternalFactor = getPowerSystem().getInternalEnergyPerInput(0);
    	double dEU = (double)aVoltage;
    	double dAmperage = (double)aAmperage;
		long usedAmps;

		if(aVoltage > maxSafeVoltage){
			onOvervoltage();
			return 0;
		}
		
		    	
        // Note about behavior:
        //  We're not going to waste 'half amps' in order to fill the bridge up to 100%.
        //  Only a multiple of 1-Amp gets consumed.
        //  If there's not enough free capacity to store (voltage*amperage)*InternalEnergyPerInput 
        //  it will just not store it.
        //
                
	    // Determine how much Amps we need
	    double dDemandInEU = ((double)getTotalEnergyDemand()) / dInternalFactor;
	    if( dDemandInEU < dEU ) {
	    	return 0; // as we can't even store 1 Amp.
	    }
		
		// Determine the Demand in Amperes at the given current
	    double dDemandInAmps = dDemandInEU / dEU;
	    if( dDemandInAmps < 1.0 ) { // should'nt happen but better to be paranoid :)
	    	return 0;
	    }
	    
	    // Limit the Demand to the provided max. Energy
	   	if( dDemandInAmps > dAmperage ){
	   		dDemandInAmps = dAmperage;	
	   	}
	    
		
		// Charge/Store ->
		double dUnusedAmps = storeEnergy( ((dDemandInAmps * dEU) * dInternalFactor), false );
		dUnusedAmps /= dInternalFactor;
		dUnusedAmps /= dEU; // divide with current to get the unused Amperage.
		
		if(dUnusedAmps > 0){
			usedAmps = (long)dDemandInAmps - (long)Math.floor(dUnusedAmps); 
		} else {
			usedAmps = (long)dDemandInAmps;
		}	
	    
	    
	    // Update Stat Counters
	    if (_lastTickInjected == worldObj.getWorldTime()) {
	    	_euLastTick += dEU * usedAmps;
	    } else {
	    	_euLastTick = dEU * usedAmps;
	    	_lastTickInjected = worldObj.getWorldTime();
	    }

	    
    	return usedAmps;
    }
    
    @Override
    public boolean inputEnergyFrom(byte aSide) {
    	return true;
    }
    
    @Override
    public boolean outputsEnergyTo(byte aSide) {
    	return false;
    }


    // @TODO: Implement Colorization Support
    @Override
	public byte getColorization() {
		return -1;
	}
	
	@Override
	public byte setColorization(byte aColor) {
		return -1;
	}

	
}
