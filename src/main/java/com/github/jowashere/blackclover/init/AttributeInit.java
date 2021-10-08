package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.client.gui.player.spells.WindSpellsScreen;
import com.github.jowashere.blackclover.events.GrimoireTextures;
import com.github.jowashere.blackclover.events.spells.AddWindSpells;

public class AttributeInit {

    public static final BCMAttribute NULL = new BCMAttribute("null", 0, false);

    public static final BCMAttribute ANTI_MAGIC = new BCMAttribute("Anti-Magic", 1, false).setAttributeMessage("Unfortunately, you have been born with no magic.").setAttributeUser("Asta");
    public static final BCMAttribute WIND = new BCMAttribute("Wind Magic", 10, false, 0, 16).setAttributeMessage("You can generate and manipulate gusts of wind with your attribute.").setAttributeColour(1).setSpellAdder(new AddWindSpells()).setSpellScreen(new WindSpellsScreen()).setGrimoireTextures(GrimoireTextures.WindGrimoires);

}
