package com.github.jowashere.blackclover.networking.packets.spells;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.packets.settings.PacketKeybindSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketKeybindCD {

    private int key;
    private int cd;

    public PacketKeybindCD(int key, int cd)
    {
        this.key = key;
        this.cd = cd;
    }

    public static void encode(PacketKeybindCD msg, PacketBuffer buf)
    {
        buf.writeInt(msg.key);
        buf.writeInt(msg.cd);
    }

    public static PacketKeybindCD decode(PacketBuffer buf)
    {
        int key = buf.readInt();
        int cd = buf.readInt();
        return new PacketKeybindCD(key, cd);
    }

    public static void handle(PacketKeybindCD msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)){
            ctx.get().enqueueWork(() -> {

                PlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

                playercap.setKeybindCD(msg.key, msg.cd);

            });
            ctx.get().setPacketHandled(true);
        }

    }

}
