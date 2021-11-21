package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketMagicExpSync {
    private float xp;
    private int playerID;

    public PacketMagicExpSync(float xp, int playerID)
    {
        this.xp = xp;
        this.playerID = playerID;
    }

    public static void encode(PacketMagicExpSync msg, PacketBuffer buf)
    {
        buf.writeFloat(msg.xp);
        buf.writeInt(msg.playerID);
    }

    public static PacketMagicExpSync decode(PacketBuffer buf)
    {
        float xp = buf.readFloat();
        int playerID = buf.readInt();
        return new PacketMagicExpSync(xp, playerID);
    }

    public static void handle(PacketMagicExpSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMagicExp(msg.xp);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
