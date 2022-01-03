package com.github.jowashere.blackclover.networking.packets.stats;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketStatPointsSync {

    private int statPoints;
    private int playerID;

    public PacketStatPointsSync(int statPoints, int playerID)
    {
        this.statPoints = statPoints;
        this.playerID = playerID;
    }

    public static void encode(PacketStatPointsSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.statPoints);
        buf.writeInt(msg.playerID);
    }

    public static PacketStatPointsSync decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        int playerID = buf.readInt();
        return new PacketStatPointsSync(data, playerID);
    }

    public static void handle(PacketStatPointsSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setStatPoints(msg.statPoints);
            });
            ctx.get().setPacketHandled(true);
        }
    }

}
