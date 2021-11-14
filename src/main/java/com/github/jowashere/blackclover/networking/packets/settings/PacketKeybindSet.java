package com.github.jowashere.blackclover.networking.packets.settings;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketKeybindSet {

    private int key;
    private String spell;
    private boolean toClient;

    public PacketKeybindSet(int key, String spell, boolean toClient)
    {
        this.key = key;
        this.toClient = toClient;
        this.spell = spell;
    }

    public static void encode(PacketKeybindSet msg, PacketBuffer buf)
    {
        buf.writeInt(msg.key);
        buf.writeUtf(msg.spell);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketKeybindSet decode(PacketBuffer buf)
    {
        int key = buf.readInt();
        String spell = buf.readUtf();
        boolean toClient = buf.readBoolean();
        return new PacketKeybindSet(key, spell, toClient);
    }

    public static void handle(PacketKeybindSet msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {

            if (msg.toClient)
            {
                Minecraft mc = Minecraft.getInstance();
                ClientPlayerEntity player = mc.player;
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setKeybind(msg.key, msg.spell);
            }
            else if(!msg.toClient)
            {
                ServerPlayerEntity player = ctx.get().getSender();
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                playercap.setKeybind(msg.key, msg.spell);
            }
        });
        ctx.get().setPacketHandled(true);
    }


}