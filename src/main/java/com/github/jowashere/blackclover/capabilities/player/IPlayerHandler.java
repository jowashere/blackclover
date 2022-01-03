package com.github.jowashere.blackclover.capabilities.player;

import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
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
	BCMAttribute ReturnMagicAttribute();

	void setRace(BCMRace race);
	BCMRace returnRace();

	void setHasGrimoire(boolean hasGrimoire);
	boolean returnHasGrimoire();

	void setToggleSpellMessage(boolean toggleSpellMessage);
	boolean returnToggleSpellMessage();

	void setJoinWorld(boolean joined);
	boolean JoinWorld();

	void setColourMana(int amount);
	int returnColourMana();

	void setHealthStat(int amount);
	void addHealthStat(int add);
	int getHealthStat();

	void setPhysicalStat(int amount);
	void addPhysicalStat(int add);
	int getPhysicalStat();

	void setManaStat(int amount);
	void addManaStat(int add);
	int getManaStat();

	void setManaControlStat(int amount);
	void addManaControlStat(int add);
	int getManaControlStat();

	void setStatPoints(int amount);
	void addStatPoints(int add);
	int getStatPoints();

	void setMagicLevel(int level);
	void addMagicLevel(int add);
	int getMagicLevel();

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
	boolean HasManaBoolean();

	void SetGrimoireTexture(String texture);
	String getGrimoireTexture();

	void setKeybind(int key, String name);
	String returnKeybind(int key);

	void setSwordSlot(int slot, ItemStack name);
	ItemStack returnSwordSlot(int slot);

	void setSpellBoolean(AbstractSpell spell, boolean has);
	boolean hasSpellBoolean(AbstractSpell spell);
}
