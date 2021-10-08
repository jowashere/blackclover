package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketMagicExpSync {
    private float xp;
    private boolean toClient;

    public PacketMagicExpSync(float xp, boolean toClient)
    {
        this.xp = xp;
        this.toClient = toClient;
    }

    public static void encode(PacketMagicExpSync msg, PacketBuffer buf)
    {
        buf.writeFloat(msg.xp);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketMagicExpSync decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        boolean toClient = buf.readBoolean();
        return new PacketMagicExpSync(data, toClient);
    }

    public static void handle(PacketMagicExpSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                Minecraft mc = Minecraft.getInstance();
                IPlayerHandler playercap = mc.player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMagicExp(msg.xp);
            }
            else if (!msg.toClient)
            {
                IPlayerHandler playercap = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMagicExp(msg.xp);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
