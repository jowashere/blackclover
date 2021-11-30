package com.github.jowashere.blackclover.networking.packets.spells;

import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.common.spells.SpellCaller;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSwordSlotSet;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.function.Supplier;

public class PacketGrimoireSword {

    private int slot;
    private boolean antiMagic;

    public PacketGrimoireSword(int slot, boolean antiMagic)
    {
        this.slot = slot;
        this.antiMagic = antiMagic;
    }

    public static void encode(PacketGrimoireSword msg, PacketBuffer buf)
    {
        buf.writeInt(msg.slot);
        buf.writeBoolean(msg.antiMagic);
    }

    public static PacketGrimoireSword decode(PacketBuffer buf)
    {
        int slot = buf.readInt();
        boolean antiMagic = buf.readBoolean();
        return new PacketGrimoireSword(slot, antiMagic);
    }

    public static void handle(PacketGrimoireSword msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            IPlayerHandler playercap = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

            PlayerEntity player = ctx.get().getSender();
            if(!(playercap.returnSwordSlot(msg.slot).getItem() instanceof SwordItem)){
                ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
                if(stack.getItem() instanceof SwordItem){
                    if(msg.antiMagic){
                        if(stack.getOrCreateTag().getBoolean("antimagic")){
                            playercap.setSwordSlot(msg.slot, stack);
                            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSwordSlotSet(player.getId(), msg.slot, stack, true));

                            stack.shrink(1);
                        }
                    }
                }
            }else {
                BCMHelper.GiveItem(player, playercap.returnSwordSlot(msg.slot));
                playercap.setSwordSlot(msg.slot, ItemStack.EMPTY);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSwordSlotSet(player.getId(), msg.slot, ItemStack.EMPTY, true));
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
