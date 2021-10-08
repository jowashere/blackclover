package com.github.jowashere.blackclover.networking.packets.spells;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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
        ctx.get().enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(msg.playerID);
            if (entity != null) entity.getPersistentData().putBoolean(msg.nbtName, msg.toggled);
        });
        ctx.get().setPacketHandled(true);
    }

}
