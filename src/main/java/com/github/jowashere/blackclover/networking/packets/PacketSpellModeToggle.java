package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.rmi.Remote;
import java.util.function.Supplier;

public class PacketSpellModeToggle {

    private boolean toClient;
    private boolean toggle;
    private int playerID;

    public PacketSpellModeToggle(boolean toClient, boolean toggle, int playerID)
    {
        this.toClient = toClient;
        this.toggle = toggle;
        this.playerID = playerID;
    }

    public static void encode(PacketSpellModeToggle msg, PacketBuffer buf)
    {
        buf.writeBoolean(msg.toClient);
        buf.writeBoolean(msg.toggle);
        buf.writeInt(msg.playerID);
    }

    public static PacketSpellModeToggle decode(PacketBuffer buf)
    {
        boolean toClient = buf.readBoolean();
        boolean toggle = buf.readBoolean();
        int playerID = buf.readInt();
        return new PacketSpellModeToggle(toClient, toggle, playerID);
    }

    public static void handle(PacketSpellModeToggle msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient)
            {
                PlayerEntity player = (PlayerEntity) Minecraft.getInstance().level.getEntity(msg.playerID);
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setSpellModeToggle(msg.toggle);
            }
            else {
                LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setSpellModeToggle(msg.toggle);
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> ctx.get().getSender()), new PacketSpellModeToggle(true, msg.toggle, ctx.get().getSender().getId()));

            }
        });
        ctx.get().setPacketHandled(true);
    }

}
