package com.github.jowashere.blackclover.networking.packets.spells;

import com.github.jowashere.blackclover.networking.NetworkLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketSpellNBTSync {

    private int playerID;
    private String nbtName;
    private boolean toggled;

    public PacketSpellNBTSync(int playerID, String nbtName, boolean toggled)
    {
        this.playerID = playerID;
        this.nbtName = nbtName;
        this.toggled = toggled;
    }

    public static void encode(PacketSpellNBTSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.playerID);
        buf.writeUtf(msg.nbtName);
        buf.writeBoolean(msg.toggled);
    }

    public static PacketSpellNBTSync decode(PacketBuffer buf)
    {
        int id = buf.readInt();
        String data = buf.readUtf();
        boolean toggled = buf.readBoolean();
        return new PacketSpellNBTSync(id, data, toggled);
    }

    public static void handle(PacketSpellNBTSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Entity target = Minecraft.getInstance().level.getEntity(msg.playerID);
                if(target == null || !(target instanceof LivingEntity))
                    return;

                target.getPersistentData().putBoolean(msg.nbtName, msg.toggled);
            });
        }
        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler{

        public static void handle(PacketSpellNBTSync msg){
            Entity target = Minecraft.getInstance().level.getEntity(msg.playerID);
            if(target == null || !(target instanceof LivingEntity))
                return;

            target.getPersistentData().putBoolean(msg.nbtName, msg.toggled);

        }
    }
}
