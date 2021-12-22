package com.github.jowashere.blackclover.networking.packets.entity;

import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncBCEntityTarget {

    private int entityID;
    private int targetID;

    public PacketSyncBCEntityTarget(int entityID, int targetID)
    {
        this.entityID = entityID;
        this.targetID = targetID;
    }

    public static void encode(PacketSyncBCEntityTarget msg, PacketBuffer buf)
    {
        buf.writeInt(msg.entityID);
        buf.writeInt(msg.targetID);

    }

    public static PacketSyncBCEntityTarget decode(PacketBuffer buf)
    {
        int entityID = buf.readInt();
        int targetID = buf.readInt();
        return new PacketSyncBCEntityTarget(entityID, targetID);
    }

    public static void handle(PacketSyncBCEntityTarget msg, Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            ctx.get().enqueueWork(() -> {

                BCEntity entity = (BCEntity) Minecraft.getInstance().level.getEntity(msg.entityID);
                LivingEntity target = (LivingEntity) Minecraft.getInstance().level.getEntity(msg.targetID);
                entity.setTarget(target);

            });
            ctx.get().setPacketHandled(true);
        }
    }

}
