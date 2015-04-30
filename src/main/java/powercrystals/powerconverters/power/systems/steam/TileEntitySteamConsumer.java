package powercrystals.powerconverters.power.systems.steam;

import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import powercrystals.powerconverters.mods.reference.InterfaceReference;
import powercrystals.powerconverters.mods.reference.ModIDReference;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.base.TileEntityEnergyConsumer;
import powercrystals.powerconverters.power.systems.PowerSteam;

@Optional.Interface(modid = ModIDReference.BUILDCRAFT, iface = InterfaceReference.BuildCraft.IPipeConnection)
public class TileEntitySteamConsumer extends TileEntityEnergyConsumer<IFluidHandler> implements IFluidHandler, IPipeConnection {
    private FluidTank _steamTank;
    private int _mBLastTick;
    PowerSteam powerSteam;
    private int lastSubtype = -1;

    public TileEntitySteamConsumer() {
        super(PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id), 0, IFluidHandler.class);
        _steamTank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);
        powerSteam = (PowerSteam) PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if(_steamTank.getFluid() != null && _steamTank.getFluid().getFluid() != null &&
                _steamTank.getFluid().getFluid().getName() != null) {
            String fluidName = _steamTank.getFluid().getFluid().getName();
            PowerSteam.SteamType steamType = powerSteam.getSteamType(fluidName);
            if (steamType != null && _steamTank.getFluidAmount() > 0) {
                lastSubtype = powerSteam.getSteamSubtype(steamType);
                int amount = Math.min(_steamTank.getFluidAmount(), powerSteam.getThrottleConsumer());
                float energy = amount * steamType.energyPerInput;
                energy = (int) storeEnergy(energy, false);
                int toDrain;
                try {
                    toDrain = (int) (amount - (energy / powerSteam.getInternalEnergyPerInput(this.blockMetadata)));
                }
                catch (ArithmeticException e) {
                    toDrain = 0;
                }
                _steamTank.drain(toDrain, true);
                _mBLastTick = toDrain;
            } else {
                _mBLastTick = 0;
            }
        } else {
            _mBLastTick = 0;
        }
    }

    @Override
    public int getVoltageIndex() {
        return 0;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource.getFluid().getName().equalsIgnoreCase("steam"))
            return _steamTank.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{_steamTank.getInfo()};
    }

    @Override
    public double getInputRate() {
        return _mBLastTick;
    }

    @Override
    public int getSubtype() {
        return lastSubtype;
    }

    @Override
    @Optional.Method(modid = ModIDReference.BUILDCRAFT)
    public ConnectOverride overridePipeConnection(IPipeTile.PipeType pipeType, ForgeDirection direction) {
        if (pipeType == IPipeTile.PipeType.FLUID)
            return ConnectOverride.CONNECT;
        return ConnectOverride.DISCONNECT;
    }
}
