package com.github.jowashere.blackclover.networking.packets.spells;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketIntSpellNBTSync {

    private int playerID;
    private String nbtName;
    private int amount;

    public PacketIntSpellNBTSync(int playerID, String nbtName, int amount)
    {
        this.playerID = playerID;
        this.nbtName = nbtName;
        this.amount = amount;
    }

    public static void encode(PacketIntSpellNBTSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.playerID);
        buf.writeUtf(msg.nbtName);
        buf.writeInt(msg.amount);
    }

    public static PacketIntSpellNBTSync decode(PacketBuffer buf)
    {
        int id = buf.readInt();
        String data = buf.readUtf();
        int amount = buf.readInt();
        return new PacketIntSpellNBTSync(id, data, amount);
    }

    public static void handle(PacketIntSpellNBTSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Entity target = Minecraft.getInstance().level.getEntity(msg.playerID);
                if(target == null || !(target instanceof LivingEntity))
                    return;

                target.getPersistentData().putInt(msg.nbtName, msg.amount);
            });
        }
        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler{

        public static void handle(PacketIntSpellNBTSync msg){
            Entity target = Minecraft.getInstance().level.getEntity(msg.playerID);
            if(target == null || !(target instanceof LivingEntity))
                return;

            target.getPersistentData().putInt(msg.nbtName, msg.amount);

        }
    }
}
