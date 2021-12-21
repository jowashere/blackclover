package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncBCEntityData {

    private int entityID;
    private int targetID;
    private int magicLevel;
    private String grimoireTexLoc;

    public PacketSyncBCEntityData(int entityID, int targetID, int magicLevel, String grimoireTexLoc)
    {
        this.entityID = entityID;
        this.targetID = targetID;
        this.magicLevel = magicLevel;
        this.grimoireTexLoc = grimoireTexLoc;
    }

    public static void encode(PacketSyncBCEntityData msg, PacketBuffer buf)
    {
        buf.writeInt(msg.entityID);
        buf.writeInt(msg.targetID);
        buf.writeInt(msg.magicLevel);
        buf.writeUtf(msg.grimoireTexLoc);

    }

    public static PacketSyncBCEntityData decode(PacketBuffer buf)
    {
        int entityID = buf.readInt();
        int targetID = buf.readInt();
        int magicLevel = buf.readInt();
        String grimoireTexLoc = buf.readUtf();
        return new PacketSyncBCEntityData(entityID, targetID, magicLevel, grimoireTexLoc);
    }

    public static void handle(PacketSyncBCEntityData msg, Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            ctx.get().enqueueWork(() -> {

                BCEntity entity = (BCEntity) Minecraft.getInstance().level.getEntity(msg.entityID);
                LivingEntity target = (LivingEntity) Minecraft.getInstance().level.getEntity(msg.targetID);

                entity.setMagicLevel(msg.magicLevel);
                entity.setTarget(target);
                entity.setGrimoireTexLoc(msg.grimoireTexLoc);
            });
            ctx.get().setPacketHandled(true);
        }
    }

}
