package com.github.jowashere.blackclover.capabilities.player;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModeInit;
import com.github.jowashere.blackclover.init.RaceInit;
import com.github.jowashere.blackclover.util.helpers.AttributeHelper;
import com.github.jowashere.blackclover.util.helpers.ModeHelper;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PlayerCapability implements IPlayerHandler {

    private boolean spellModeToggle;

    private String key1 = "";
    private String key2 = "";
    private String key3 = "";
    private String key4 = "";
    private String key5 = "";
    private String key6 = "";
    private String key7 = "";
    private String key8 = "";
    private String key9 = "";
    private String skillSpell = "";
    private int key1cd = 0;
    private int key2cd = 0;
    private int key3cd = 0;
    private int key4cd = 0;
    private int key5cd = 0;
    private int key6cd = 0;
    private int key7cd = 0;
    private int key8cd = 0;
    private int key9cd = 0;
    private int skillSpellcd = 0;

    private String grimoireTextureLoc = "";

    private boolean joined;

	private float mana;
	private float maxMana;
	private float regenMana;
	private int manaColour;

	private BCMRace race = RaceInit.NULL;

    private boolean manaless;

	private float magicXp;
	private int magicLevel;
	private BCMAttribute magicAttribute = AttributeInit.NULL;
    private boolean hasGrimoire;

    private boolean manaSkinToggled;
    private boolean reinforcementToggled;
    private BCMMode mode = ModeInit.NULL;

    private boolean toggleMessageSpell;
    private Map<BCMSpell, Boolean> spellBooleans = new HashMap<>();

    private float spellCooldown;

    private boolean storedDemonSlayer;
    private boolean storedDemonDweller;
    private boolean storedDemonDestroyer;

	@Override
    public float returnMana() {
        return this.mana;
    }

	@Override
	public void setMana(float amount) {
		this.mana = amount;
	}

    @Override
    public void addMana(float amount) {
        this.mana += amount;

        if (this.mana > this.maxMana)
        	this.mana = this.maxMana;

        if (this.mana < 0)
            this.mana = 0;
    }


    @Override
    public void setJoinWorld(boolean joined) {
        this.joined = joined;
    }
    @Override
    public boolean joinWorld() {
        return this.joined;
    }

    @Override
    public void setSpellModeToggle(boolean toggled) {
        this.spellModeToggle = toggled;
    }
    @Override
    public boolean returnSpellModeToggle() {
    	return this.spellModeToggle;
    }

    @Override
    public void setMaxMana(float amount) {
    	this.maxMana = amount;
    }
    @Override
    public void addMaxMana(float amount) {
    	this.maxMana += amount;
    }
    @Override
    public float returnMaxMana() {
    	return this.maxMana;
    }

    @Override
    public void setHasGrimoire(boolean hasGrimoire){
	    this.hasGrimoire = hasGrimoire;
    }
    @Override
    public boolean returnHasGrimoire(){
	    return this.hasGrimoire;
    }

    @Override
    public void setRace(BCMRace race){
	    this.race = race;
    }
    @Override
    public BCMRace returnRace(){
	    return this.race;
    }

    @Override
    public void setMagicAttribute(BCMAttribute attribute) {
        this.magicAttribute = attribute;
    }
    @Override
    public BCMAttribute returnMagicAttribute() {
        return magicAttribute;
    }

    @Override
    public void setRegenMana(float amount) {
    	this.regenMana = amount;
    }
    @Override
    public void addRegenMana(float amount) {
    	this.regenMana += amount;
    }
    @Override
    public float returnRegenMana() {
    	return this.regenMana;
    }

    @Override
    public void setToggleSpellMessage(boolean toggleMessageSpell)
    {
        this.toggleMessageSpell = toggleMessageSpell;
    }
    @Override
    public boolean returnToggleSpellMessage()
    {
        return this.toggleMessageSpell;
    }

    @Override
    public void setColourMana(int amount) {
	    this.manaColour = amount;
    }
    @Override
    public int returnColourMana() {
	    return this.manaColour;
    }

    @Override
    public void setMagicLevel(int amount)
    {
        this.magicLevel = amount;
    }
    @Override
    public int returnMagicLevel()
    {
        return this.magicLevel;
    }

    @Override
    public void setMagicExp(float amount)
    {
        this.magicXp = amount;
    }
    @Override
    public void addMagicExp(float amount) {
        this.regenMana += amount;
    }
    @Override
    public float returnMagicExp()
    {
        return this.magicXp;
    }

    @Override
    public void setPlayerBodyMode(BCMMode playerMode) {
        this.mode = playerMode;
    }
    @Override
    public BCMMode returnPlayerMode() {
        return this.mode;
    }

    @Override
    public void setManaSkinToggled(boolean handInfusion)
    {
        this.manaSkinToggled = handInfusion;
    }
    @Override
    public boolean returnManaSkinToggled()
    {
        return this.manaSkinToggled;
    }
    @Override
    public void setReinforcementToggled(boolean bodyInfusion)
    {
        this.reinforcementToggled = bodyInfusion;
    }
    @Override
    public boolean returnReinforcementToggled()
    {
        return this.reinforcementToggled;
    }

    @Override
    public void setManaBoolean(boolean has)
    {
        this.manaless = !has;
    }
    @Override
    public boolean hasManaBoolean()
    {
        return !this.manaless;
    }

    @Override
    public void setGrimoireTexture(String texture){
	    this.grimoireTextureLoc = texture;
    }
    @Override
    public String getGrimoireTexture(){
	    return grimoireTextureLoc;
    }

    @Override
    public void setKeybind1CD(int cd)
    {
        this.key1cd = cd;
    }
    @Override
    public int returnKeybind1CD()
    {
        return this.key1cd;
    }
    @Override
    public void setKeybind2CD(int cd)
    {
        this.key2cd = cd;
    }
    @Override
    public int returnKeybind2CD()
    {
        return this.key2cd;
    }
    @Override
    public void setKeybind3CD(int cd)
    {
        this.key3cd = cd;
    }
    @Override
    public int returnKeybind3CD()
    {
        return this.key3cd;
    }
    @Override
    public void setKeybind4CD(int cd)
    {
        this.key4cd = cd;
    }
    @Override
    public int returnKeybind4CD()
    {
        return this.key4cd;
    }
    @Override
    public void setKeybind5CD(int cd)
    {
        this.key5cd = cd;
    }
    @Override
    public int returnKeybind5CD()
    {
        return this.key5cd;
    }
    @Override
    public void setKeybind6CD(int cd)
    {
        this.key6cd = cd;
    }
    @Override
    public int returnKeybind6CD()
    {
        return this.key6cd;
    }
    @Override
    public void setKeybind7CD(int cd)
    {
        this.key7cd = cd;
    }
    @Override
    public int returnKeybind7CD()
    {
        return this.key7cd;
    }
    @Override
    public void setKeybind8CD(int cd)
    {
        this.key8cd = cd;
    }
    @Override
    public int returnKeybind8CD()
    {
        return this.key8cd;
    }
    @Override
    public void setKeybind9CD(int cd)
    {
        this.key9cd = cd;
    }
    @Override
    public int returnKeybind9CD()
    {
        return this.key9cd;
    }

    @Override
    public void setKeybind1(String name)
    {
        this.key1 = name;
    }
    @Override
    public String returnKeybind1()
    {
        return this.key1;
    }
    @Override
    public void setKeybind2(String name)
    {
        this.key2 = name;
    }
    @Override
    public String returnKeybind2()
    {
        return this.key2;
    }
    @Override
    public void setKeybind3(String name)
    {
        this.key3 = name;
    }
    @Override
    public String returnKeybind3()
    {
        return this.key3;
    }
    @Override
    public void setKeybind4(String name)
    {
        this.key4 = name;
    }
    @Override
    public String returnKeybind4()
    {
        return this.key4;
    }
    @Override
    public void setKeybind5(String name)
    {
        this.key5 = name;
    }
    @Override
    public String returnKeybind5()
    {
        return this.key5;
    }
    @Override
    public void setKeybind6(String name)
    {
        this.key6 = name;
    }
    @Override
    public String returnKeybind6()
    {
        return this.key6;
    }
    @Override
    public void setKeybind7(String name)
    {
        this.key7 = name;
    }
    @Override
    public String returnKeybind7()
    {
        return this.key7;
    }
    @Override
    public void setKeybind8(String name)
    {
        this.key8 = name;
    }
    @Override
    public String returnKeybind8()
    {
        return this.key8;
    }
    @Override
    public void setKeybind9(String name)
    {
        this.key9 = name;
    }
    @Override
    public String returnKeybind9()
    {
        return this.key9;
    }

    @Override
    public void setSpellBoolean(BCMSpell spell, boolean has) {
        if (this.spellBooleans.containsKey(spell)) {
            this.spellBooleans.replace(spell, has);
            return;
        }
        this.spellBooleans.put(spell, has);
    }

    @Override
    public boolean hasSpellBoolean(BCMSpell spell) {
        return this.spellBooleans.getOrDefault(spell, false);
    }


    public static class Storage implements Capability.IStorage<IPlayerHandler>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IPlayerHandler> capability, IPlayerHandler instance, Direction side)
        {
            CompoundNBT tag = new CompoundNBT();

            tag.putBoolean("joined", instance.joinWorld());
            tag.putBoolean("spellMode", instance.returnSpellModeToggle());
            tag.putBoolean("manaskin", instance.returnManaSkinToggled());
            tag.putBoolean("reinforcement", instance.returnReinforcementToggled());
            tag.putBoolean("spellmessage", instance.returnToggleSpellMessage());
            tag.putFloat("mana", instance.returnMana());
            tag.putFloat("maxMana", instance.returnMaxMana());
            tag.putFloat("regenMana", instance.returnRegenMana());
            tag.putInt("colorMana", instance.returnColourMana());
            tag.putFloat("magicExp", instance.returnMagicExp());
            tag.putInt("magicLevel", instance.returnMagicLevel());
            tag.putString("playerRace", instance.returnRace().getString());
            tag.putString("playerMode", instance.returnPlayerMode().getName());
            tag.putString("magicAttribute", instance.returnMagicAttribute().getString());
            tag.putBoolean("hasGrimoire", instance.returnHasGrimoire());

            tag.putString("grimoireTex", instance.getGrimoireTexture());

            tag.putString("key1", instance.returnKeybind1());
            tag.putString("key2", instance.returnKeybind2());
            tag.putString("key3", instance.returnKeybind3());
            tag.putString("key4", instance.returnKeybind4());
            tag.putString("key5", instance.returnKeybind5());
            tag.putString("key6", instance.returnKeybind6());
            tag.putString("key7", instance.returnKeybind7());
            tag.putString("key8", instance.returnKeybind8());
            tag.putString("key9", instance.returnKeybind9());

            tag.putInt("key1cd", instance.returnKeybind1CD());
            tag.putInt("key2cd", instance.returnKeybind2CD());
            tag.putInt("key3cd", instance.returnKeybind3CD());
            tag.putInt("key4cd", instance.returnKeybind4CD());
            tag.putInt("key5cd", instance.returnKeybind5CD());
            tag.putInt("key6cd", instance.returnKeybind6CD());
            tag.putInt("key7cd", instance.returnKeybind7CD());
            tag.putInt("key8cd", instance.returnKeybind8CD());
            tag.putInt("key9cd", instance.returnKeybind9CD());

            tag.putBoolean("spellmessage", instance.returnToggleSpellMessage());

            tag.putBoolean("hasMana", instance.hasManaBoolean());

            for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                tag.putBoolean(spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_save", instance.hasSpellBoolean(spell));
            }

            return tag;
        }

        @Override
        public void readNBT(Capability<IPlayerHandler> capability, IPlayerHandler instance, Direction side, INBT nbt)
        {
            INBT tag = nbt;
            instance.setJoinWorld(((CompoundNBT) tag).getBoolean("joined"));
            instance.setSpellModeToggle(((CompoundNBT) tag).getBoolean("spellMode"));
            instance.setManaSkinToggled(((CompoundNBT) tag).getBoolean("manaskin"));
            instance.setReinforcementToggled(((CompoundNBT) tag).getBoolean("reinforcement"));
            instance.setMana(((CompoundNBT) tag).getFloat("mana"));
            instance.setMaxMana(((CompoundNBT) tag).getFloat("maxMana"));
            instance.setRegenMana(((CompoundNBT) tag).getFloat("regenMana"));
            instance.setColourMana(((CompoundNBT) tag).getInt("colorMana"));
            instance.setMagicLevel(((CompoundNBT) tag).getInt("magicLevel"));
            instance.setRace(RaceHelper.getRaceFromString(((CompoundNBT) tag).getString("playerRace")));
            instance.setMagicAttribute(AttributeHelper.getAttributeFromString(((CompoundNBT) tag).getString("magicAttribute")));
            instance.setPlayerBodyMode(ModeHelper.getModeFromString(((CompoundNBT) tag).getString("playerMode")));
            instance.setHasGrimoire(((CompoundNBT) tag).getBoolean("hasGrimoire"));

            instance.setGrimoireTexture(((CompoundNBT) tag).getString("grimoireTex"));

            instance.setKeybind1(((CompoundNBT) tag).getString("key1"));
            instance.setKeybind2(((CompoundNBT) tag).getString("key2"));
            instance.setKeybind3(((CompoundNBT) tag).getString("key3"));
            instance.setKeybind4(((CompoundNBT) tag).getString("key4"));
            instance.setKeybind5(((CompoundNBT) tag).getString("key5"));
            instance.setKeybind6(((CompoundNBT) tag).getString("key6"));
            instance.setKeybind7(((CompoundNBT) tag).getString("key7"));
            instance.setKeybind8(((CompoundNBT) tag).getString("key8"));
            instance.setKeybind9(((CompoundNBT) tag).getString("key9"));

            instance.setKeybind1CD(((CompoundNBT) tag).getInt("key1cd"));
            instance.setKeybind2CD(((CompoundNBT) tag).getInt("key2cd"));
            instance.setKeybind3CD(((CompoundNBT) tag).getInt("key3cd"));
            instance.setKeybind4CD(((CompoundNBT) tag).getInt("key4cd"));
            instance.setKeybind5CD(((CompoundNBT) tag).getInt("key5cd"));
            instance.setKeybind6CD(((CompoundNBT) tag).getInt("key6cd"));
            instance.setKeybind7CD(((CompoundNBT) tag).getInt("key7cd"));
            instance.setKeybind8CD(((CompoundNBT) tag).getInt("key8cd"));
            instance.setKeybind9CD(((CompoundNBT) tag).getInt("key9cd"));

            instance.setToggleSpellMessage(((CompoundNBT) tag).getBoolean("spellmessage"));

            instance.setManaBoolean(((CompoundNBT) tag).getBoolean("hasMana"));

            for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                instance.setSpellBoolean(spell, ((CompoundNBT) tag).getBoolean(spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_save"));
            }
        }
    }
}