package com.github.jowashere.blackclover.networking.packets.settings;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.packets.PacketHasModeSync;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSetGrimoireTexture {

    private String texture;
    private boolean toClient;

    public PacketSetGrimoireTexture(String texture, boolean toClient)
    {
        this.texture = texture;
        this.toClient = toClient;
    }

    public static void encode(PacketSetGrimoireTexture msg, PacketBuffer buf)
    {
        buf.writeUtf(msg.texture);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketSetGrimoireTexture decode(PacketBuffer buf)
    {
        String texture = buf.readUtf();
        boolean toClient = buf.readBoolean();
        return new PacketSetGrimoireTexture(texture, toClient);
    }

    public static void handle(PacketSetGrimoireTexture msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {

            if (msg.toClient) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setGrimoireTexture(msg.texture);
            } else {
                LazyOptional<IPlayerHandler> capabilities = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setGrimoireTexture(msg.texture);
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
