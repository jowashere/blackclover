package com.github.jowashere.blackclover.networking.packets.spells;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.packets.settings.PacketKeybindSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketKeybindCD {

    private int key;
    private int cd;
    private boolean toClient;

    public PacketKeybindCD(int key, int cd, boolean toClient)
    {
        this.key = key;
        this.toClient = toClient;
        this.cd = cd;
    }

    public static void encode(PacketKeybindCD msg, PacketBuffer buf)
    {
        buf.writeInt(msg.key);
        buf.writeInt(msg.cd);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketKeybindCD decode(PacketBuffer buf)
    {
        int key = buf.readInt();
        int cd = buf.readInt();
        boolean toClient = buf.readBoolean();
        return new PacketKeybindCD(key, cd, toClient);
    }

    public static void handle(PacketKeybindCD msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            switch (msg.key)
            {
                case 1:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind1CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind1CD(msg.cd);
                    }
                    break;
                case 2:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind2CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind2CD(msg.cd);
                    }
                    break;
                case 3:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind3CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind3CD(msg.cd);
                    }
                    break;
                case 4:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind4CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind4CD(msg.cd);
                    }
                    break;
                case 5:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind5CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind5CD(msg.cd);
                    }
                    break;
                case 6:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind6CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind6CD(msg.cd);
                    }
                    break;
                case 7:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind7CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind7CD(msg.cd);
                    }
                    break;
                case 8:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind8CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind8CD(msg.cd);
                    }
                    break;
                case 9:
                    if (msg.toClient)
                    {
                        Minecraft mc = Minecraft.getInstance();
                        ClientPlayerEntity player = mc.player;
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind9CD(msg.cd);
                    }
                    else if(!msg.toClient)
                    {
                        ServerPlayerEntity player = ctx.get().getSender();
                        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
                        playercap.setKeybind9CD(msg.cd);
                    }
                    break;
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
