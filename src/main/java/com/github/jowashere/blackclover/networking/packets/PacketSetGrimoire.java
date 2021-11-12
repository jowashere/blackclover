package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketSetGrimoire {

    private boolean hasGrimoire;
    private boolean toClient;
    private int playerID;

    public PacketSetGrimoire(boolean hasGrimoire, boolean toClient, int playerID)
    {
        this.hasGrimoire = hasGrimoire;
        this.toClient = toClient;
        this.playerID = playerID;
    }

    public static void encode(PacketSetGrimoire msg, PacketBuffer buf) {
        buf.writeBoolean(msg.hasGrimoire);
        buf.writeBoolean(msg.toClient);
        buf.writeInt(msg.playerID);
    }

    public static PacketSetGrimoire decode(PacketBuffer buf) {
        boolean hasGrimoire = buf.readBoolean();
        boolean toClient = buf.readBoolean();
        int playerId = buf.readInt();
        return new PacketSetGrimoire(hasGrimoire, toClient, playerId);
    }

    public static void handle(PacketSetGrimoire msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                PlayerEntity player = (PlayerEntity) Minecraft.getInstance().level.getEntity(msg.playerID);
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
