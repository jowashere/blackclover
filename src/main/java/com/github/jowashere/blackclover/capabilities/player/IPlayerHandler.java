package com.github.jowashere.blackclover.capabilities.player;

import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
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

	void setKeybind1(String k1name);
	void setKeybind2(String k2name);
	void setKeybind3(String k3name);
	void setKeybind4(String k4name);
	void setKeybind5(String k5name);
	void setKeybind6(String k6name);
	void setKeybind7(String k7name);
	void setKeybind8(String k8name);
	void setKeybind9(String k9name);

	String returnKeybind1();
	String returnKeybind2();
	String returnKeybind3();
	String returnKeybind4();
	String returnKeybind5();
	String returnKeybind6();
	String returnKeybind7();
	String returnKeybind8();
	String returnKeybind9();

	void setKeybind1CD(int cd);
	void setKeybind2CD(int cd);
	void setKeybind3CD(int cd);
	void setKeybind4CD(int cd);
	void setKeybind5CD(int cd);
	void setKeybind6CD(int cd);
	void setKeybind7CD(int cd);
	void setKeybind8CD(int cd);
	void setKeybind9CD(int cd);

	int returnKeybind1CD();
	int returnKeybind2CD();
	int returnKeybind3CD();
	int returnKeybind4CD();
	int returnKeybind5CD();
	int returnKeybind6CD();
	int returnKeybind7CD();
	int returnKeybind8CD();
	int returnKeybind9CD();

	void setSpellBoolean(BCMSpell spell, boolean has);
	boolean hasSpellBoolean(BCMSpell spell);
}
