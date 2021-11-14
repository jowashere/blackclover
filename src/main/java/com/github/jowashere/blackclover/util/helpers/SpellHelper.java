package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.EffectInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;

public class SpellHelper {

    public static BCMSpell getSpellFromString(String registryName) {
        for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
            if (spell.getName().equalsIgnoreCase(registryName))
                return spell;
        }
        return null;
    }

    public static BCMSpell getSpellFromName(String spellName) {
        for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
            if (("spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName()).equalsIgnoreCase(spellName))
                return spell;
        }
        return null;
    }

    /*public static void create() {
        for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
            if (spell instanceof BeNMClanJutsu) {
                beNMClanJutsus.add((BeNMClanJutsu) spell);
            }
        }
    }*/

    public static Float spellDamageCalcE(LivingEntity entity, int damageTier, float baseDamage) {

        int magicLevel = entity.getEffect(EffectInit.MAGIC_LEVEL.get()).getAmplifier();

        return baseDamage + ((damageTier * baseDamage / 2) * magicLevel / 10);
    }

    public static Float spellDamageCalcP(PlayerEntity player, int damageTier, float baseDamage) {

        LazyOptional<IPlayerHandler> playerInCap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        return baseDamage + ((damageTier * baseDamage / 2) * player_cap.returnMagicLevel() / 10);
    }

}
