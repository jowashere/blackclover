package com.github.jowashere.blackclover.common.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
                            if (!playercap.returnToggleSpellMessage()) playerIn.displayClientMessage(new StringTextComponent(new TranslationTextComponent("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).getString() + "!"), false);
                        } else {
                            spell.throwCancelEvent(playerIn);
                            playerIn.getPersistentData().putBoolean(nbtName, false);
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
