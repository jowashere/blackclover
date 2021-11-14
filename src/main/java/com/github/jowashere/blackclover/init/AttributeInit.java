package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.client.gui.player.spells.LightningSpellsScreen;
import com.github.jowashere.blackclover.client.gui.player.spells.WindSpellsScreen;
import com.github.jowashere.blackclover.events.GrimoireTextures;
import com.github.jowashere.blackclover.events.spells.AddLightningSpells;
import com.github.jowashere.blackclover.events.spells.AddWindSpells;

public class AttributeInit {

    public static final BCMAttribute NULL = new BCMAttribute("null", 0, false,  null);

    public static final BCMAttribute ANTI_MAGIC = new BCMAttribute("antimagic", 1, false,  BCMSpell.Type.ANTI_MAGIC).setAttributeMessage("Unfortunately, you have been born with no magic.").setAttributeUser("Asta");
    public static final BCMAttribute WIND = new BCMAttribute("wind", 20, false, 0, 16,  BCMSpell.Type.WIND_MAGIC).setAttributeMessage("You can generate and manipulate gusts of wind with your attribute.").setAttributeColour(1).setSpellAdder(new AddWindSpells()).setGrimoireTextures(GrimoireTextures.WindGrimoires);
    public static final BCMAttribute LIGHTNING = new BCMAttribute("lightning", 20, false, 0, 48,  BCMSpell.Type.LIGHTNING_MAGIC).setAttributeMessage("You feel electricity surging throughout your body. Your magic is Lightning.").setAttributeColour(3).setSpellAdder(new AddLightningSpells()).setGrimoireTextures(GrimoireTextures.LightningGrimoires);

}
