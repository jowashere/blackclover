package com.github.jowashere.blackclover.networking.packets.mana;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketMaxManaSync {
    private float maxMana;
    private boolean toClient;

    public PacketMaxManaSync(float maxMana, boolean toClient) {
        this.maxMana = maxMana;
        this.toClient = toClient;
    }

    public static void encode(PacketMaxManaSync msg, PacketBuffer buf) {
        buf.writeFloat(msg.maxMana);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketMaxManaSync decode(PacketBuffer buf) {
        float data = buf.readFloat();
        return new PacketMaxManaSync(data, buf.readBoolean());
    }

    public static void handle(PacketMaxManaSync msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                Minecraft mc = Minecraft.getInstance();
                IPlayerHandler playercap = mc.player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMaxMana(msg.maxMana);
            }
            else {
                ServerPlayerEntity player= ctx.get().getSender();
                IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMaxMana(msg.maxMana);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
