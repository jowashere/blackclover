package com.github.jowashere.blackclover.networking.packets.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSetSpellBoolean {

    private boolean has;
    private boolean toClient;
    private String spellName;

    public PacketSetSpellBoolean(String spellName, boolean has, boolean toClient)
    {
        this.spellName = spellName;
        this.toClient = toClient;
        this.has = has;
    }

    public static void encode(PacketSetSpellBoolean msg, PacketBuffer buf)
    {
        buf.writeUtf(msg.spellName);
        buf.writeBoolean(msg.has);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketSetSpellBoolean decode(PacketBuffer buf)
    {
        String data = buf.readUtf();
        boolean has = buf.readBoolean();
        boolean toClient = buf.readBoolean();
        return new PacketSetSpellBoolean(data, has, toClient);
    }

    public static void handle(PacketSetSpellBoolean msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {

            for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
                if (spell.getName().equalsIgnoreCase(msg.spellName)) {
                    if (msg.toClient) {
                        ClientPlayerEntity player = Minecraft.getInstance().player;
                        IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                        spell.sync(playercap, spell, msg.has);
                    }
                    else {
                        ServerPlayerEntity player = ctx.get().getSender();
                        IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                        spell.sync(playercap, spell, msg.has);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
