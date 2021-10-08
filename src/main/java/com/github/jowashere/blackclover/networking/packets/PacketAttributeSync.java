package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.util.helpers.AttributeHelper;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketAttributeSync {

    private String attribute;
    private boolean toClient;

    public PacketAttributeSync(String attribute, boolean toClient)
    {
        this.attribute = attribute;
        this.toClient = toClient;
    }

    public static void encode(PacketAttributeSync msg, PacketBuffer buf) {
        buf.writeUtf(msg.attribute);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketAttributeSync decode(PacketBuffer buf) {
        String race = buf.readUtf();
        boolean toClient = buf.readBoolean();
        return new PacketAttributeSync(race, toClient);
    }

    public static void handle(PacketAttributeSync msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setMagicAttribute(AttributeHelper.getAttributeFromString(msg.attribute));
            }
            else {
                ServerPlayerEntity player = ctx.get().getSender();
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setMagicAttribute(AttributeHelper.getAttributeFromString(msg.attribute));
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
