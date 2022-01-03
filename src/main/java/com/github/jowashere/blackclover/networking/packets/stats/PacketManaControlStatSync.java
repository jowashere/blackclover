package com.github.jowashere.blackclover.networking.packets.stats;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketManaControlStatSync {

    private int manaControlStat;
    private int playerID;

    public PacketManaControlStatSync(int manaControlStat, int playerID)
    {
        this.manaControlStat = manaControlStat;
        this.playerID = playerID;
    }

    public static void encode(PacketManaControlStatSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.manaControlStat);
        buf.writeInt(msg.playerID);
    }

    public static PacketManaControlStatSync decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        int playerID = buf.readInt();
        return new PacketManaControlStatSync(data, playerID);
    }

    public static void handle(PacketManaControlStatSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setManaControlStat(msg.manaControlStat);
            });
            ctx.get().setPacketHandled(true);
        }
    }

}
