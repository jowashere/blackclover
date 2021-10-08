package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketMagicLevel {

    //1 = Genin

    private int magicLevel;
    private boolean toClient;

    public PacketMagicLevel(int magicLevel, boolean toClient)
    {
        this.magicLevel = magicLevel;
        this.toClient = toClient;
    }

    public static void encode(PacketMagicLevel msg, PacketBuffer buf)
    {
        buf.writeInt(msg.magicLevel);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketMagicLevel decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        boolean toClient = buf.readBoolean();
        return new PacketMagicLevel(data, toClient);
    }

    public static void handle(PacketMagicLevel msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {

            if (msg.toClient)
            {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMagicLevel(msg.magicLevel);
            }
            else {
                IPlayerHandler playercap = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMagicLevel(msg.magicLevel);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
