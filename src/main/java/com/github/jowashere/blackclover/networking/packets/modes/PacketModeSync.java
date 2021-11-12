package com.github.jowashere.blackclover.networking.packets.modes;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.util.helpers.ModeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketModeSync {

    private String mode;
    private int playerID;
    private boolean toClient;

    public PacketModeSync(String mode, int playerID,  boolean toClient)
    {
        this.mode = mode;
        this.playerID = playerID;
        this.toClient = toClient;
    }

    public static void encode(PacketModeSync msg, PacketBuffer buf)
    {
        buf.writeUtf(msg.mode);
        buf.writeInt(msg.playerID);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketModeSync decode(PacketBuffer buf)
    {
        String bodyMode = buf.readUtf();
        int playerID = buf.readInt();
        boolean toClient = buf.readBoolean();
        return new PacketModeSync(bodyMode, playerID, toClient);
    }

    public static void handle(PacketModeSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                Entity player = Minecraft.getInstance().level.getEntity(msg.playerID);
                if (player instanceof PlayerEntity) {
                    LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                    IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                    playercap.setPlayerBodyMode(ModeHelper.getModeFromString(msg.mode));
                }
            } else {
                LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setPlayerBodyMode(ModeHelper.getModeFromString(msg.mode));
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
