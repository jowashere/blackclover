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
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketKeybindCD {

    private int key;
    private int cd;
    private int playerID;

    public PacketKeybindCD(int key, int cd, int playerID)
    {
        this.key = key;
        this.cd = cd;
        this.playerID = playerID;
    }

    public static void encode(PacketKeybindCD msg, PacketBuffer buf)
    {
        buf.writeInt(msg.key);
        buf.writeInt(msg.cd);
        buf.writeInt(msg.playerID);
    }

    public static PacketKeybindCD decode(PacketBuffer buf)
    {
        int key = buf.readInt();
        int cd = buf.readInt();
        int playerID = buf.readInt();
        return new PacketKeybindCD(key, cd, playerID);
    }

    public static void handle(PacketKeybindCD msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {

            PlayerEntity player = (PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID);
            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            switch (msg.key)
            {
                case 1:
                    playercap.setKeybind1CD(msg.cd);
                    break;
                case 2:
                    playercap.setKeybind2CD(msg.cd);
                    break;
                case 3:
                    playercap.setKeybind3CD(msg.cd);
                    break;
                case 4:
                    playercap.setKeybind4CD(msg.cd);
                    break;
                case 5:
                    playercap.setKeybind5CD(msg.cd);
                    break;
                case 6:
                    playercap.setKeybind6CD(msg.cd);
                    break;
                case 7:
                    playercap.setKeybind7CD(msg.cd);
                    break;
                case 8:
                    playercap.setKeybind8CD(msg.cd);
                    break;
                case 9:
                    playercap.setKeybind9CD(msg.cd);
                    break;
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
