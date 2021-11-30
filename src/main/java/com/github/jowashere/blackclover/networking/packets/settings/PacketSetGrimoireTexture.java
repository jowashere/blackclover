package com.github.jowashere.blackclover.networking.packets.settings;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSetGrimoireTexture {

    private String texture;
    private boolean toClient;
    private int playerID;

    public PacketSetGrimoireTexture(String texture, boolean toClient, int playerID)
    {
        this.texture = texture;
        this.toClient = toClient;
        this.playerID = playerID;
    }

    public static void encode(PacketSetGrimoireTexture msg, PacketBuffer buf)
    {
        buf.writeUtf(msg.texture);
        buf.writeBoolean(msg.toClient);
        buf.writeInt(msg.playerID);
    }

    public static PacketSetGrimoireTexture decode(PacketBuffer buf)
    {
        String texture = buf.readUtf();
        boolean toClient = buf.readBoolean();
        int playerID = buf.readInt();
        return new PacketSetGrimoireTexture(texture, toClient, playerID);
    }

    public static void handle(PacketSetGrimoireTexture msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {

            if (msg.toClient) {
                PlayerEntity player = (PlayerEntity) Minecraft.getInstance().level.getEntity(msg.playerID);
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.SetGrimoireTexture(msg.texture);
            } else {
                LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.SetGrimoireTexture(msg.texture);
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
