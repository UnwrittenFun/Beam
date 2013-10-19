package unwrittenfun.minecraft.beam.handlers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import unwrittenfun.minecraft.beam.info.ModInfo;
import unwrittenfun.minecraft.beam.tileentities.TEItemBeam;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Mod: Beam
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class PacketHandler implements IPacketHandler {
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
        EntityPlayer entityPlayer = (EntityPlayer) player;
        byte packetId = reader.readByte();

        switch (packetId) {
            case 0:
                onROffChangePacket(reader, entityPlayer.worldObj);
                break;
            case 1:
                onRequestROffPacket(reader, entityPlayer.worldObj);
                break;
        }
    }

    public static void sendROffChangePacket(TEItemBeam beam) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try {
            dataStream.writeByte((byte) 0);
            dataStream.writeInt(beam.xCoord);
            dataStream.writeInt(beam.yCoord);
            dataStream.writeInt(beam.zCoord);
            dataStream.writeInt(beam.rOffX);
            dataStream.writeInt(beam.rOffY);
            dataStream.writeInt(beam.rOffZ);

            PacketDispatcher.sendPacketToAllInDimension(
                    PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()),
                    beam.getWorldObj().provider.dimensionId);
        } catch (IOException ex) {
            System.err.append("[Beam] Failed to send ROff change packet");
        }
    }

    public void onROffChangePacket(ByteArrayDataInput reader, World world) {
        int x = reader.readInt();
        int y = reader.readInt();
        int z = reader.readInt();
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TEItemBeam) {
            int rOffX = reader.readInt();
            int rOffY = reader.readInt();
            int rOffZ = reader.readInt();

            TEItemBeam beam = (TEItemBeam) tileEntity;
            beam.setReceiverOffset(rOffX, rOffY, rOffZ);
        }
    }

    public static void requestROffPacket(TEItemBeam beam) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try {
            dataStream.writeByte((byte) 1);
            dataStream.writeInt(beam.xCoord);
            dataStream.writeInt(beam.yCoord);
            dataStream.writeInt(beam.zCoord);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
        } catch (IOException ex) {
            System.err.append("[Beam] Failed to request ROff packet");
        }
    }

    public void onRequestROffPacket(ByteArrayDataInput reader, World world) {
        int x = reader.readInt();
        int y = reader.readInt();
        int z = reader.readInt();
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TEItemBeam) sendROffChangePacket((TEItemBeam) tileEntity);
    }

}
