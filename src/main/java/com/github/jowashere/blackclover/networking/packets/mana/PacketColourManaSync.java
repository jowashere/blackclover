package com.github.jowashere.blackclover.networking.packets.mana;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketColourManaSync {
    private int colourMana;

    public PacketColourManaSync(int colourMana)
    {
        this.colourMana = colourMana;
    }

    public static void encode(PacketColourManaSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.colourMana);
    }

    public static PacketColourManaSync decode(PacketBuffer buf)
    {
        int data = buf.readInt();
        return new PacketColourManaSync(data);
    }

    public static void handle(PacketColourManaSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            IPlayerHandler playercap = mc.player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
            playercap.setColourMana(msg.colourMana);
        });
        ctx.get().setPacketHandled(true);
    }
}
