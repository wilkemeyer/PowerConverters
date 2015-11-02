package powercrystals.powerconverters.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.PowerSystemManager;

/**
 * Send power system data to client on server connect.
 */
public class PacketClientSync implements IMessage {
    NBTTagCompound data;

    @SuppressWarnings("unused")
    public PacketClientSync() {
        data = new NBTTagCompound();
    }

    public PacketClientSync(NBTTagCompound nbt) {
        data = nbt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }

    public static class Handler extends GenericHandler<PacketClientSync> {

        @Override
        public void processMessage(PacketClientSync message, MessageContext context) {
            PowerConverterCore.instance.logger.debug("Got sync packet. Applying ...");
            PowerSystemManager manager = PowerSystemManager.getInstance();
            try {
                manager = PowerSystemManager.getInstance();
                manager.readPowerData(message.data);
                PowerConverterCore.instance.logger.debug("Sync packet success.");
                return;
            }
            catch (Exception e) {
                FMLLog.severe("Error processing Powerconverter sync data from server. Using client data.");
            }
            manager.readPowerData(manager.writePowerData());
        }
    }
}
