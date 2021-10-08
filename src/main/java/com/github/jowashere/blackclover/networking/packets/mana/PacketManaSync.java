package com.github.jowashere.blackclover.networking.packets.mana;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketManaSync {

    private float mana;

    public PacketManaSync(float mana)
    {
        this.mana = mana;
    }

    public static void encode(PacketManaSync msg, PacketBuffer buf)
    {
        buf.writeFloat(msg.mana);
    }

    public static PacketManaSync decode(PacketBuffer buf)
    {
        float data = buf.readFloat();
        return new PacketManaSync(data);
    }

    public static void handle(PacketManaSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            IPlayerHandler playercap = mc.player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
            playercap.setMana(msg.mana);
        });
        ctx.get().setPacketHandled(true);
    }

}
