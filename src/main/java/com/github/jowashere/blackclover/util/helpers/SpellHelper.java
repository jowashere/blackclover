package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.EffectInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

public class SpellHelper {

    public static AbstractSpell getSpellFromName(String registryName) {
        for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
            if (spell.getName().equalsIgnoreCase(registryName))
                return spell;
        }
        return null;
    }

    public static AbstractSpell getSpellFromString(String spellName) {
        for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
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


    public static Float spellDamageCalc(int magicLevel, int damageTier, float baseDamage) {

        return baseDamage + ((damageTier * baseDamage / 2) * magicLevel / 10);
    }

    public static Float spellDamageCalcE(LivingEntity entity, int damageTier, float baseDamage) {

        int magicLevel = entity.getEffect(EffectInit.MAGIC_LEVEL.get()).getAmplifier();

        return baseDamage + ((damageTier * baseDamage / 2) * magicLevel / 10);
    }

    public static Float spellDamageCalcP(PlayerEntity player, int damageTier, float baseDamage) {

        LazyOptional<IPlayerHandler> playerInCap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        return baseDamage + ((damageTier * baseDamage / 2) * player_cap.ReturnMagicLevel() / 10);
    }

    public static int findSpellKey(PlayerEntity player, AbstractSpell spell) {

        IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

        String spellString = "spell." + spell.getCorrelatedPlugin().getPluginId() + "." + spell.getName();

        for (int i = 0; i < 9; i++) {

            if (spellString.equals( playercap.returnKeybind(i+1))) {
                return (i+1);
            }
        }
        return 0;
    }

}
