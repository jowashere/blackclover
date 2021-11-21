package com.github.jowashere.blackclover.networking.packets.spells;

import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.common.spells.SpellCaller;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketSpellCaller {

    private String spellType;
    private int selectedSpell;
    private int playerID;

    public PacketSpellCaller(String spellType, int selectedSpell, int playerID)
    {
        this.spellType = spellType;
        this.selectedSpell = selectedSpell;
        this.playerID = playerID;
    }

    public static void encode(PacketSpellCaller msg, PacketBuffer buf)
    {
        buf.writeUtf(msg.spellType);
        buf.writeInt(msg.selectedSpell);
        buf.writeInt(msg.playerID);
    }

    public static PacketSpellCaller decode(PacketBuffer buf)
    {
        String data = buf.readUtf();
        int keybind = buf.readInt();
        int playerID = buf.readInt();
        return new PacketSpellCaller(data, keybind, playerID);
    }

    public static void handle(PacketSpellCaller msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            IPlayerHandler playercap = ctx.get().getSender().getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
            if (msg.selectedSpell <= 0) {
                SpellCaller.SpellCaller(ctx.get().getSender(), msg.spellType, msg.selectedSpell);
                return;
            }

            String spellString = playercap.returnKeybind(msg.selectedSpell);
            BCMSpell spell = SpellHelper.getSpellFromString(spellString);
            int cd = spell.getCooldown();

            SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), spellString, msg.selectedSpell);
        });
        ctx.get().setPacketHandled(true);
    }

}
