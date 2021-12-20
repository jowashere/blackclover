package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.events.GrimoireTextures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AttributeInit {

    public static final BCMAttribute NULL = new BCMAttribute("null", 0, false,  null);

    public static final List<Supplier<BCMAttribute>> attributeList = new ArrayList<>();

    public static final BCMAttribute WIND = new BCMAttribute("wind", 30, false, 0, 16,  AbstractSpell.Type.WIND_MAGIC).setAttributeMessage("You have Wind Magic.").setAttributeColour(1).setGrimoireTextures(GrimoireTextures.WindGrimoires);
    public static final BCMAttribute LIGHTNING = new BCMAttribute("lightning", 30, false, 0, 48,  AbstractSpell.Type.LIGHTNING_MAGIC).setAttributeMessage("You have Lightning Magic").setAttributeColour(3).setGrimoireTextures(GrimoireTextures.LightningGrimoires);
    public static final BCMAttribute SWORD = new BCMAttribute("sword", 0, false, 0, 96,  AbstractSpell.Type.SWORD_MAGIC).setAttributeMessage("You have Sword Magic").setAttributeColour(0).setGrimoireTextures(GrimoireTextures.SwordGrimoires);
    public static final BCMAttribute SLASH = new BCMAttribute("slash", 20, false, 0, 80,  AbstractSpell.Type.SLASH_MAGIC).setAttributeMessage("You have Slash Magic.").setAttributeColour(1).setGrimoireTextures(GrimoireTextures.SlashGrimoires);
    public static final BCMAttribute DARKNESS = new BCMAttribute("darkness", 5, false, 0, 64,  AbstractSpell.Type.DARKNESS_MAGIC).setAttributeMessage("You have Darkness Magic.").setAttributeColour(2).setGrimoireTextures(GrimoireTextures.DarknessGrimoires);
    public static final BCMAttribute LIGHT = new BCMAttribute("light", 5, false, 0, 32,  AbstractSpell.Type.LIGHT_MAGIC).setAttributeMessage("You have Light Magic.").setAttributeColour(7).setGrimoireTextures(GrimoireTextures.LightGrimoires);
    public static final BCMAttribute ANTI_MAGIC = new BCMAttribute("antimagic", 0, false, 0, 0,  AbstractSpell.Type.ANTI_MAGIC).setAttributeMessage("You have no magic.").setAttributeColour(0).setGrimoireTextures(GrimoireTextures.AntiMagicGrimoires);

}
