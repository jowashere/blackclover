package com.github.jowashere.blackclover.networking.packets.stats;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketManaStatSync {

    private int manaStat;
    private int playerID;

    public PacketManaStatSync(int manaStat, int playerID)
    {
        this.manaStat = manaStat;
        this.playerID = playerID;
    }

    public static void encode(PacketManaStatSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.manaStat);
        buf.writeInt(msg.playerID);
    }

    public static PacketManaStatSync decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        int playerID = buf.readInt();
        return new PacketManaStatSync(data, playerID);
    }

    public static void handle(PacketManaStatSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setManaStat(msg.manaStat);
            });
            ctx.get().setPacketHandled(true);
        }
    }

}
