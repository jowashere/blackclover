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

public class PacketRaceSync {

    private String race;
    private boolean toClient;

    public PacketRaceSync(String race, boolean toClient)
    {
        this.race = race;
        this.toClient = toClient;
    }

    public static void encode(PacketRaceSync msg, PacketBuffer buf) {
        buf.writeUtf(msg.race);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketRaceSync decode(PacketBuffer buf) {
        String race = buf.readUtf();
        boolean toClient = buf.readBoolean();
        return new PacketRaceSync(race, toClient);
    }

    public static void handle(PacketRaceSync msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setRace(RaceHelper.getRaceFromString(msg.race));
            }
            else {
                ServerPlayerEntity player = ctx.get().getSender();
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setRace(RaceHelper.getRaceFromString(msg.race));
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
