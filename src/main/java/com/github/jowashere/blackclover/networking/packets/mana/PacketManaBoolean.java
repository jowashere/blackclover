package com.github.jowashere.blackclover.networking.packets.mana;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketManaBoolean {

    private boolean has;
    private boolean toClient;

    public PacketManaBoolean(boolean has, boolean toClient)
    {
        this.has = has;
        this.toClient = toClient;
    }

    public static void encode(PacketManaBoolean msg, PacketBuffer buf)
    {
        buf.writeBoolean(msg.has);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketManaBoolean decode(PacketBuffer buf)
    {
        boolean has = buf.readBoolean();
        boolean toClient = buf.readBoolean();
        return new PacketManaBoolean(has, toClient);
    }

    public static void handle(PacketManaBoolean msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient)
            {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setManaBoolean(msg.has);
            }
            else {
                LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setManaBoolean(msg.has);
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
