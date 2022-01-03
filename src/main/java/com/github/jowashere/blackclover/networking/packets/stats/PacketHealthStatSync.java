package com.github.jowashere.blackclover.networking.packets.stats;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketHealthStatSync {

    private int healthStat;
    private int playerID;

    public PacketHealthStatSync(int healthStat, int playerID)
    {
        this.healthStat = healthStat;
        this.playerID = playerID;
    }

    public static void encode(PacketHealthStatSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.healthStat);
        buf.writeInt(msg.playerID);
    }

    public static PacketHealthStatSync decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        int playerID = buf.readInt();
        return new PacketHealthStatSync(data, playerID);
    }

    public static void handle(PacketHealthStatSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setHealthStat(msg.healthStat);
            });
            ctx.get().setPacketHandled(true);
        }
    }

}
