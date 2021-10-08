package com.github.jowashere.blackclover.networking.packets.mana;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketRegenManaSync {

    private float regenMana;
    private boolean toClient;

    public PacketRegenManaSync(float regenMana, boolean toClient) {
        this.regenMana = regenMana;
        this.toClient = toClient;
    }

    public static void encode(PacketRegenManaSync msg, PacketBuffer buf) {
        buf.writeFloat(msg.regenMana);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketRegenManaSync decode(PacketBuffer buf)
    {
        float data = buf.readFloat();
        boolean toClient = buf.readBoolean();
        return new PacketRegenManaSync(data, toClient);
    }

    public static void handle(PacketRegenManaSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                IPlayerHandler playercap = Minecraft.getInstance().player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setRegenMana(msg.regenMana);
            }
            else {
                IPlayerHandler playercap = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setRegenMana(msg.regenMana);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
