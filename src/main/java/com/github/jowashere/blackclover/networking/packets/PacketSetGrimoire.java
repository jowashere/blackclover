package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSetGrimoire {

    private boolean hasGrimoire;
    private boolean toClient;

    public PacketSetGrimoire(boolean hasGrimoire, boolean toClient)
    {
        this.hasGrimoire = hasGrimoire;
        this.toClient = toClient;
    }

    public static void encode(PacketSetGrimoire msg, PacketBuffer buf) {
        buf.writeBoolean(msg.hasGrimoire);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketSetGrimoire decode(PacketBuffer buf) {
        boolean hasGrimoire = buf.readBoolean();
        boolean toClient = buf.readBoolean();
        return new PacketSetGrimoire(hasGrimoire, toClient);
    }

    public static void handle(PacketSetGrimoire msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setHasGrimoire(msg.hasGrimoire);
            }
            else {
                ServerPlayerEntity player = ctx.get().getSender();
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setHasGrimoire(msg.hasGrimoire);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
