package com.github.jowashere.blackclover.networking.packets.stats;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketPhysicalStatSync {

    private int physicalStat;
    private int playerID;

    public PacketPhysicalStatSync(int physicalStat, int playerID)
    {
        this.physicalStat = physicalStat;
        this.playerID = playerID;
    }

    public static void encode(PacketPhysicalStatSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.physicalStat);
        buf.writeInt(msg.playerID);
    }

    public static PacketPhysicalStatSync decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        int playerID = buf.readInt();
        return new PacketPhysicalStatSync(data, playerID);
    }

    public static void handle(PacketPhysicalStatSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setPhysicalStat(msg.physicalStat);
            });
            ctx.get().setPacketHandled(true);
        }
    }

}
