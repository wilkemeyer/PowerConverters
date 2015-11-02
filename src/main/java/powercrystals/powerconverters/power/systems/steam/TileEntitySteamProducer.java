package powercrystals.powerconverters.power.systems.steam;

import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import powercrystals.powerconverters.mods.reference.InterfaceReference;
import powercrystals.powerconverters.mods.reference.ModIDReference;
import powercrystals.powerconverters.position.BlockPosition;
import powercrystals.powerconverters.power.PowerSystemManager;
import powercrystals.powerconverters.power.base.TileEntityEnergyProducer;
import powercrystals.powerconverters.power.systems.PowerSteam;

@Optional.Interface(modid = ModIDReference.BUILDCRAFT, iface = InterfaceReference.BuildCraft.IPipeConnection)
public class TileEntitySteamProducer extends TileEntityEnergyProducer<IFluidHandler> implements IFluidHandler, IPipeConnection {
    PowerSteam powerSteam;
    int steamId;

    public TileEntitySteamProducer() {
        this(0);
    }

    public TileEntitySteamProducer(int steamId) {
        super(PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id), 0, IFluidHandler.class);
        powerSteam = (PowerSteam) PowerSystemManager.getInstance().getPowerSystemByName(PowerSteam.id);
        this.steamId = steamId;
    }

    @Override
    public double produceEnergy(double energy) {
        boolean powered = getWorldObj().getStrongestIndirectPower(xCoord, yCoord, zCoord) > 0;
        if (powered || steamId < 0 || powerSteam.getInternalEnergyPerOutput(steamId + 1) == 0) {
            return energy;
        }
        energy = energy / powerSteam.getInternalEnergyPerOutput(steamId + 1);
        for (int i = 0; i < 6; i++) {
            BlockPosition bp = new BlockPosition(this);
            bp.orientation = ForgeDirection.getOrientation(i);
            bp.moveForwards(1);
            TileEntity te = worldObj.getTileEntity(bp.x, bp.y, bp.z);

            if (te instanceof IFluidHandler) {
                final int steam = (int) Math.min(energy, powerSteam.getThrottleProducer());
                PowerSteam.SteamType steamType = powerSteam.getSteamType(steamId);
                FluidStack stack = FluidRegistry.getFluidStack(steamType.name, steam);
                if (stack != null) {
                    energy -= ((IFluidHandler) te).fill(bp.orientation.getOpposite(), stack, true);
                }
            }

            if (energy <= 0)
                return 0;
        }

        return energy * powerSteam.getInternalEnergyPerOutput(steamId + 1);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
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
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }

    @Override
    public int getSubtype() {
        PowerSteam.SteamType type = powerSteam.getSteamType(steamId);
        return type != null ? powerSteam.getSteamSubtype(type) : -1;
    }

    @Override
    @Optional.Method(modid = ModIDReference.BUILDCRAFT)
    public ConnectOverride overridePipeConnection(IPipeTile.PipeType pipeType, ForgeDirection direction) {
        if (pipeType == IPipeTile.PipeType.FLUID)
            return ConnectOverride.CONNECT;
        return ConnectOverride.DISCONNECT;
    }
}
