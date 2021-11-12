package com.github.jowashere.blackclover.common.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSpellModeToggle;
import com.github.jowashere.blackclover.networking.packets.server.SPacketSpellNBTSync;
import com.github.jowashere.blackclover.networking.packets.spells.PacketKeybindCD;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class SpellCaller {

    public static void SpellCaller(PlayerEntity playerIn, String spellName)
    {
        //System.out.println("spell called");
        if (spellName != null) {
            IPlayerHandler playercap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
            for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                if (("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).equalsIgnoreCase(spellName)) {
                    if (spell.isToggle()) {
                        String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();
                        if (!playerIn.getPersistentData().getBoolean(nbtName)) {
                            playerIn.getPersistentData().putBoolean(nbtName, true);
                            NetworkLoader.INSTANCE.send(PacketDistributor.SERVER.noArg(), new SPacketSpellNBTSync(playerIn.getId(), nbtName, true));
                            NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) playerIn), new SPacketSpellNBTSync(playerIn.getId(), nbtName, true));

                            if (!playercap.returnToggleSpellMessage()) playerIn.displayClientMessage(new StringTextComponent(new TranslationTextComponent("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).getString() + "!"), false);
                            return;
                        } else {
                            spell.throwCancelEvent(playerIn);
                            playerIn.getPersistentData().putBoolean(nbtName, false);
                            NetworkLoader.INSTANCE.send(PacketDistributor.SERVER.noArg(), new SPacketSpellNBTSync(playerIn.getId(), nbtName, false));
                            NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) playerIn), new SPacketSpellNBTSync(playerIn.getId(), nbtName, true));

                        }
                    }
                    else {
                        int modifier0 = Math.max(0, playercap.returnMagicLevel() / 5);
                        int modifier1 = Math.max(0, playercap.returnMagicLevel() / 5) - 1;

                        spell.act(playerIn, modifier0, modifier1);
                    }
                }
            }
        }
    }


}
