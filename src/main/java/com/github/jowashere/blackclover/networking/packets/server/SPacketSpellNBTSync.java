package com.github.jowashere.blackclover.networking.packets.server;

import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class SPacketSpellNBTSync {

    private int playerID;
    private String nbtName;
    private boolean toggled;

    public SPacketSpellNBTSync(int playerID, String nbtName, boolean toggled)
    {
        this.playerID = playerID;
        this.nbtName = nbtName;
        this.toggled = toggled;
    }

    public static void encode(SPacketSpellNBTSync msg, PacketBuffer buf)
    {
        buf.writeInt(msg.playerID);
        buf.writeUtf(msg.nbtName);
        buf.writeBoolean(msg.toggled);
    }

    public static SPacketSpellNBTSync decode(PacketBuffer buf)
    {
        int id = buf.readInt();
        String data = buf.readUtf();
        boolean toggled = buf.readBoolean();
        return new SPacketSpellNBTSync(id, data, toggled);
    }

    public static void handle(SPacketSpellNBTSync msg, Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                Entity target = ctx.get().getSender().level.getEntity(msg.playerID);
                if(target == null || !(target instanceof LivingEntity))
                    return;
                target.getPersistentData().putBoolean(msg.nbtName, msg.toggled);
                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(msg.playerID, msg.nbtName, msg.toggled));

            });
        }
        ctx.get().setPacketHandled(true);
    }

}
