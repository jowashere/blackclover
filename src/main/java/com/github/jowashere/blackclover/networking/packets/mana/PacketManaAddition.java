package com.github.jowashere.blackclover.networking.packets.mana;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketMagicLevel;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Random;
import java.util.function.Supplier;

public class PacketManaAddition {

    public PacketManaAddition()
    {
        ;
    }

    public static void encode(PacketManaAddition msg, PacketBuffer buf) {
    }

    public static PacketManaAddition decode(PacketBuffer buf)
    {
        return new PacketManaAddition();
    }

    public static void handle(PacketManaAddition msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

            IPlayerHandler player_cap = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
            player_cap.setMaxMana(player_cap.returnRace().getStartingMana());
            player_cap.setMana(player_cap.returnRace().getStartingMana());
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new PacketMaxManaSync(player_cap.returnMaxMana(), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new PacketManaSync(player_cap.returnMana()));
            player_cap.setMagicLevel(1);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new PacketMagicLevel(1, true));
            player_cap.addRegenMana(7.5F);
            player_cap.setManaBoolean(true);
        });
        ctx.get().setPacketHandled(true);
    }

}
