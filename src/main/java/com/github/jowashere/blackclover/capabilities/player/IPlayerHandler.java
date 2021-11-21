package com.github.jowashere.blackclover.capabilities.player;

import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IPlayerHandler {

	void setSpellModeToggle(boolean toggled);
	boolean returnSpellModeToggle();

	void setMana(float amount);
	void addMana(float amount);
	float returnMana();
	
	void setMaxMana(float amount);
	void addMaxMana(float amount);
	float returnMaxMana();

	void setRegenMana(float amount);
	void addRegenMana(float amount);
	float returnRegenMana();

	void setMagicAttribute(BCMAttribute attribute);
	BCMAttribute returnMagicAttribute();

	void setRace(BCMRace race);
	BCMRace returnRace();

	void setHasGrimoire(boolean hasGrimoire);
	boolean returnHasGrimoire();

	void setToggleSpellMessage(boolean toggleSpellMessage);
	boolean returnToggleSpellMessage();

	void setJoinWorld(boolean joined);
	boolean joinWorld();

	void setColourMana(int amount);
	int returnColourMana();

	void setMagicLevel(int level);
	void addMagicLevel(int add);
	int returnMagicLevel();

	void setMagicExp(float amount);
	void addMagicExp(float amount);
	float returnMagicExp();

	void setManaSkinToggled(boolean handInfusion);
	boolean returnManaSkinToggled();
	void setReinforcementToggled(boolean bodyInfusion);
	boolean returnReinforcementToggled();

	void setPlayerBodyMode(BCMMode playerMode);
	BCMMode returnPlayerMode();

	void setManaBoolean(boolean has);
	boolean hasManaBoolean();

	void setGrimoireTexture(String texture);
	String getGrimoireTexture();

	void setKeybind(int key, String name);

	String returnKeybind(int key);

	void setKeybindCD(int key, int cd);

	int returnKeybindCD(int key);

	void setSpellBoolean(BCMSpell spell, boolean has);
	boolean hasSpellBoolean(BCMSpell spell);
}
