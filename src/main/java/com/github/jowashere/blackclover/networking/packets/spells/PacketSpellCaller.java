package com.github.jowashere.blackclover.networking.packets.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.common.spells.SpellCaller;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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
                SpellCaller.SpellCaller(ctx.get().getSender(), msg.spellType);
                return;
            }
            switch (msg.selectedSpell) {
                case 1:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind1());
                    break;
                case 2:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind2());
                    break;
                case 3:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind3());
                    break;
                case 4:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind4());
                    break;
                case 5:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind5());
                    break;
                case 6:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind6());
                    break;
                case 7:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind7());
                    break;
                case 8:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind8());
                    break;
                case 9:
                    SpellCaller.SpellCaller((PlayerEntity) ctx.get().getSender().level.getEntity(msg.playerID), playercap.returnKeybind9());
                    break;
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
