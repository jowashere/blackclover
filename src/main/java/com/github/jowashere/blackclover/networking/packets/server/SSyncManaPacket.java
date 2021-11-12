package com.github.jowashere.blackclover.networking.packets.server;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSyncManaPacket {

    private int playerID;
    private float currentMana;
    private float maxMana;
    private float regenMana;

    public SSyncManaPacket( int playerID, float currentMana, float maxMana, float regenMana) {
        this.playerID = playerID;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.regenMana = regenMana;
    }

    public static void encode(SSyncManaPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.playerID);
        buf.writeFloat(msg.currentMana);
        buf.writeFloat(msg.maxMana);
        buf.writeFloat(msg.regenMana);
    }

    public static SSyncManaPacket decode(PacketBuffer buf)
    {
        int playerID = buf.readInt();
        float currentMana = buf.readFloat();
        float maxMana = buf.readFloat();
        float regenMana = buf.readFloat();
        return new SSyncManaPacket(playerID, currentMana, maxMana, regenMana);
    }

    public static void handle(SSyncManaPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                Entity entity = ctx.get().getSender().level.getEntity(msg.playerID);
                if(entity == null || !(entity instanceof PlayerEntity))
                    return;
                IPlayerHandler playercap = entity.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setMana(msg.currentMana);
                playercap.setMaxMana(msg.maxMana);
                playercap.setRegenMana(msg.regenMana);
            });
        }
        ctx.get().setPacketHandled(true);
    }

}
