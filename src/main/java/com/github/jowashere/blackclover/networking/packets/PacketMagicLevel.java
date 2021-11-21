package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketMagicLevel {

    //1 = Genin

    private int magicLevel;
    private int playerID;

    public PacketMagicLevel(int magicLevel, int playerID)
    {
        this.magicLevel = magicLevel;
        this.playerID = playerID;
    }

    public static void encode(PacketMagicLevel msg, PacketBuffer buf)
    {
        buf.writeInt(msg.magicLevel);
        buf.writeInt(msg.playerID);
    }

    public static PacketMagicLevel decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        int playerID = buf.readInt();
        return new PacketMagicLevel(data, playerID);
    }

    public static void handle(PacketMagicLevel msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMagicLevel(msg.magicLevel);
            });
            ctx.get().setPacketHandled(true);
        }
    }

}
