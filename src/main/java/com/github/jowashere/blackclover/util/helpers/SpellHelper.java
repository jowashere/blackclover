package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;

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

}
