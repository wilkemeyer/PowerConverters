package powercrystals.powerconverters.power.systems.factorization;

import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.powerconverters.position.BlockPosition;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.base.TileEntityEnergyProducer;
import powercrystals.powerconverters.power.systems.PowerFactorization;

import java.util.Map.Entry;

public class TileEntityFactorizationProducer extends TileEntityEnergyProducer<IChargeConductor> implements IChargeConductor {
    private Charge _charge = new Charge(this);
    private static final int _maxCG = 1000;
    private boolean neighbourDirty = false;

    public TileEntityFactorizationProducer() {
        super(PowerSystemManager.getInstance().getPowerSystemByName(PowerFactorization.id), 0, IChargeConductor.class);
    }

    @Override
    public double produceEnergy(double energy) {
        double CG = energy / getPowerSystem().getInternalEnergyPerOutput(0);
        boolean powered = getWorldObj().getStrongestIndirectPower(xCoord, yCoord, zCoord) > 0;
        if(!powered) {
            for (Entry<ForgeDirection, IChargeConductor> output : this.getTiles().entrySet()) {
                IChargeConductor o = output.getValue();
                if (o != null) {
                    if (o.getCharge().getValue() < _maxCG) {
                        int store = (int) Math.min(_maxCG - o.getCharge().getValue(), CG);
                        o.getCharge().addValue(store);
                        CG -= store;
                        if (CG <= 0) {
                            break;
                        }
                    }
                }
            }
        }
        return CG * getPowerSystem().getInternalEnergyPerOutput(0);
    }

    @Override
    public Charge getCharge() {
        return this._charge;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Coord getCoord() {
        return new Coord(this);
    }

    @Override
    public void onNeighboorChanged() {
        super.onNeighboorChanged();

        try {
            Class fzNullClass = Class.forName("factorization.shared.TileEntityFzNull");
            for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity te = BlockPosition.getAdjacentTileEntity(this, d);
                //noinspection unchecked
                if (te != null && fzNullClass != null && fzNullClass.isAssignableFrom(te.getClass())) {
                    neighbourDirty = true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        if (neighbourDirty) {
            onNeighboorChanged();
            neighbourDirty = false;
        }
        return super.isConnected();
    }
}

