package com.github.jowashere.blackclover.networking.packets;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSwordSlotSet {

    private boolean toClient;
    private int playerid;
    private int slot;
    private ItemStack stack;

    public PacketSwordSlotSet(int playerid, int slot, ItemStack stack, boolean toClient) {
        this.playerid = playerid;
        this.slot = slot;
        this.stack = stack;
        this.toClient = toClient;
    }

    public static void encode(PacketSwordSlotSet msg, PacketBuffer buf) {
        buf.writeInt(msg.playerid);
        buf.writeInt(msg.slot);
        buf.writeItemStack(msg.stack, true);
        buf.writeBoolean(msg.toClient);
    }

    public static PacketSwordSlotSet decode(PacketBuffer buf) {
        return new PacketSwordSlotSet(buf.readInt(), buf.readInt(), buf.readItem(), buf.readBoolean());
    }

    public static void handle(PacketSwordSlotSet msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (msg.toClient) {
                Entity entity = Minecraft.getInstance().level.getEntity(msg.playerid);
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                    playercap.setSwordSlot(msg.slot, msg.stack);
                }
            }
            else {
                IPlayerHandler playercap = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                playercap.setSwordSlot(msg.slot, msg.stack);
            }
        });
        ctx.get().setPacketHandled(true);
    }


}