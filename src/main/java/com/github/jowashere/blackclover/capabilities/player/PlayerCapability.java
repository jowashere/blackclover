package com.github.jowashere.blackclover.capabilities.player;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.api.internal.BCMRace;
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

    private int healthStat;
    private int physicalStat;
    private int manaStat;
    private int manaControlStat;

    private int statPoints;

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
    private Map<AbstractSpell, Boolean> spellBooleans = new HashMap<>();

    private float spellCooldown;

    private ItemStack swordSlot1 = ItemStack.EMPTY;
    private ItemStack swordSlot2 = ItemStack.EMPTY;
    private ItemStack swordSlot3 = ItemStack.EMPTY;
    private ItemStack swordSlot4 = ItemStack.EMPTY;
    private ItemStack swordSlot5 = ItemStack.EMPTY;
    private ItemStack swordSlot6 = ItemStack.EMPTY;
    private ItemStack swordSlot7 = ItemStack.EMPTY;
    private ItemStack swordSlot8 = ItemStack.EMPTY;
    private ItemStack swordSlot9 = ItemStack.EMPTY;



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
    public boolean JoinWorld() {
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
    public BCMAttribute ReturnMagicAttribute() {
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
    public void setHealthStat(int amount) {
        this.healthStat = amount;
    }

    @Override
    public void addHealthStat(int add) {
        this.healthStat += add;
    }

    @Override
    public int getHealthStat() {
        return this.healthStat;
    }

    @Override
    public void setPhysicalStat(int amount) {
        this.physicalStat = amount;
    }

    @Override
    public void addPhysicalStat(int add){
        this.physicalStat += add;
    }

    @Override
    public int getPhysicalStat() {
        return this.physicalStat;
    }

    @Override
    public void setManaStat(int amount) {
        this.manaStat = amount;
    }

    @Override
    public void addManaStat(int add) {
        this.manaStat += add;
    }

    @Override
    public int getManaStat() {
        return this.manaStat;
    }

    @Override
    public void setManaControlStat(int amount) {
        this.manaControlStat = amount;
    }

    @Override
    public void addManaControlStat(int add) {
        this.manaControlStat += add;
    }

    @Override
    public int getManaControlStat() {
        return this.manaControlStat;
    }

    @Override
    public void setStatPoints (int amount) {
        this.statPoints = amount;
    }

    @Override
    public void addStatPoints (int add) {
        this.statPoints += add;
    }

    @Override
    public int getStatPoints() {
        return this.statPoints;
    }


    @Override
    public void setMagicLevel(int amount)
    {
        this.magicLevel = amount;
    }
    @Override
    public void addMagicLevel(int amount)
    {
        this.magicLevel += amount;
    }
    @Override
    public int getMagicLevel()
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
        this.magicXp += amount;
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
    public boolean HasManaBoolean()
    {
        return !this.manaless;
    }

    @Override
    public void SetGrimoireTexture(String texture){
	    this.grimoireTextureLoc = texture;
    }
    @Override
    public String getGrimoireTexture(){
	    return grimoireTextureLoc;
    }

    @Override
    public void setKeybind(int key, String name)
    {
        if(key == 1){
            this.key1 = name;
        }else if(key == 2){
            this.key2 = name;
        }else if(key == 3){
            this.key3 = name;
        }else if(key == 4){
            this.key4 = name;
        }else if(key == 5){
            this.key5 = name;
        }else if(key == 6){
            this.key6 = name;
        }else if(key == 7){
            this.key7 = name;
        }else if(key == 8){
            this.key8 = name;
        }else if(key == 9){
            this.key9 = name;
        }
    }
    @Override
    public String returnKeybind(int key)
    {
        if(key == 1){
            return this.key1;
        }else if(key == 2){
            return this.key2;
        }else if(key == 3){
            return this.key3;
        }else if(key == 4){
            return this.key4;
        }else if(key == 5){
            return this.key5;
        }else if(key == 6){
            return this.key6;
        }else if(key == 7){
            return this.key7;
        }else if(key == 8){
            return this.key8;
        }else if(key == 9){
            return this.key9;
        }
        return null;
    }

    @Override
    public void setSwordSlot(int swordSlot, ItemStack stack)
    {

        ItemStack itemStack = new ItemStack(stack.getItem());
        itemStack.getOrCreateTag().merge(stack.getOrCreateTag());

        if(swordSlot == 1){

            this.swordSlot1 = itemStack;
        }else if(swordSlot == 2){
            this.swordSlot2 = itemStack;
        }else if(swordSlot == 3){
            this.swordSlot3 = itemStack;
        }else if(swordSlot == 4){
            this.swordSlot4 = itemStack;
        }else if(swordSlot == 5){
            this.swordSlot5 = itemStack;
        }else if(swordSlot == 6){
            this.swordSlot6 = itemStack;
        }else if(swordSlot == 7){
            this.swordSlot7 = itemStack;
        }else if(swordSlot == 8){
            this.swordSlot8 = itemStack;
        }else if(swordSlot == 9){
            this.swordSlot9 = itemStack;
        }
    }
    @Override
    public ItemStack returnSwordSlot(int swordSlot)
    {
        if(swordSlot == 1){
            return this.swordSlot1;
        }else if(swordSlot == 2){
            return this.swordSlot2;
        }else if(swordSlot == 3){
            return this.swordSlot3;
        }else if(swordSlot == 4){
            return this.swordSlot4;
        }else if(swordSlot == 5){
            return this.swordSlot5;
        }else if(swordSlot == 6){
            return this.swordSlot6;
        }else if(swordSlot == 7){
            return this.swordSlot7;
        }else if(swordSlot == 8){
            return this.swordSlot8;
        }else if(swordSlot == 9){
            return this.swordSlot9;
        }
        return null;
    }

    @Override
    public void setSpellBoolean(AbstractSpell spell, boolean has) {
        if (this.spellBooleans.containsKey(spell)) {
            this.spellBooleans.replace(spell, has);
            return;
        }
        this.spellBooleans.put(spell, has);
    }

    @Override
    public boolean hasSpellBoolean(AbstractSpell spell) {
        return this.spellBooleans.getOrDefault(spell, false);
    }


    public static class Storage implements Capability.IStorage<IPlayerHandler>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IPlayerHandler> capability, IPlayerHandler instance, Direction side)
        {
            CompoundNBT tag = new CompoundNBT();

            tag.putBoolean("joined", instance.JoinWorld());
            tag.putBoolean("spellMode", instance.returnSpellModeToggle());
            tag.putBoolean("manaskin", instance.returnManaSkinToggled());
            tag.putBoolean("reinforcement", instance.returnReinforcementToggled());
            tag.putBoolean("spellmessage", instance.returnToggleSpellMessage());
            tag.putFloat("mana", instance.returnMana());
            tag.putFloat("maxMana", instance.returnMaxMana());
            tag.putFloat("regenMana", instance.returnRegenMana());
            tag.putInt("colorMana", instance.returnColourMana());
            tag.putFloat("magicExp", instance.returnMagicExp());
            tag.putInt("magicLevel", instance.getMagicLevel());
            tag.putString("playerRace", instance.returnRace().getString());
            tag.putString("playerMode", instance.returnPlayerMode().getName());
            tag.putString("magicAttribute", instance.ReturnMagicAttribute().getString());
            tag.putBoolean("hasGrimoire", instance.returnHasGrimoire());

            tag.putString("grimoireTex", instance.getGrimoireTexture());

            tag.putInt("healthStat", instance.getHealthStat());
            tag.putInt("physicalStat", instance.getPhysicalStat());
            tag.putInt("manaStat", instance.getManaStat());
            tag.putInt("manaControlStat", instance.getManaControlStat());

            tag.putString("key1", instance.returnKeybind(1));
            tag.putString("key2", instance.returnKeybind(2));
            tag.putString("key3", instance.returnKeybind(3));
            tag.putString("key4", instance.returnKeybind(4));
            tag.putString("key5", instance.returnKeybind(5));
            tag.putString("key6", instance.returnKeybind(6));
            tag.putString("key7", instance.returnKeybind(7));
            tag.putString("key8", instance.returnKeybind(8));
            tag.putString("key9", instance.returnKeybind(9));

            CompoundNBT swordSlot1 = new CompoundNBT();
            instance.returnSwordSlot(1).save(swordSlot1);
            tag.put("swordslot1", swordSlot1);

            CompoundNBT swordSlot2 = new CompoundNBT();
            instance.returnSwordSlot(2).save(swordSlot2);
            tag.put("swordslot2", swordSlot2);

            CompoundNBT swordSlot3 = new CompoundNBT();
            instance.returnSwordSlot(3).save(swordSlot3);
            tag.put("swordslot3", swordSlot3);

            CompoundNBT swordSlot4 = new CompoundNBT();
            instance.returnSwordSlot(4).save(swordSlot4);
            tag.put("swordslot4", swordSlot4);

            CompoundNBT swordSlot5 = new CompoundNBT();
            instance.returnSwordSlot(5).save(swordSlot5);
            tag.put("swordslot5", swordSlot5);

            CompoundNBT swordSlot6 = new CompoundNBT();
            instance.returnSwordSlot(6).save(swordSlot6);
            tag.put("swordslot6", swordSlot6);

            CompoundNBT swordSlot7 = new CompoundNBT();
            instance.returnSwordSlot(7).save(swordSlot7);
            tag.put("swordslot7", swordSlot7);

            CompoundNBT swordSlot8 = new CompoundNBT();
            instance.returnSwordSlot(8).save(swordSlot8);
            tag.put("swordslot8", swordSlot8);

            CompoundNBT swordSlot9 = new CompoundNBT();
            instance.returnSwordSlot(2).save(swordSlot9);
            tag.put("swordslot9", swordSlot9);

            tag.putBoolean("spellmessage", instance.returnToggleSpellMessage());

            tag.putBoolean("hasMana", instance.HasManaBoolean());

            for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
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
            instance.setMagicExp(((CompoundNBT) tag).getFloat("magicExp"));
            instance.setMagicLevel(((CompoundNBT) tag).getInt("magicLevel"));
            instance.setRace(RaceHelper.getRaceFromString(((CompoundNBT) tag).getString("playerRace")));
            instance.setMagicAttribute(AttributeHelper.getAttributeFromString(((CompoundNBT) tag).getString("magicAttribute")));
            instance.setPlayerBodyMode(ModeHelper.getModeFromString(((CompoundNBT) tag).getString("playerMode")));
            instance.setHasGrimoire(((CompoundNBT) tag).getBoolean("hasGrimoire"));

            instance.SetGrimoireTexture(((CompoundNBT) tag).getString("grimoireTex"));

            instance.setHealthStat(((CompoundNBT) tag).getInt("healthStat"));
            instance.setPhysicalStat(((CompoundNBT) tag).getInt("physicalStat"));
            instance.setManaStat(((CompoundNBT) tag).getInt("manaStat"));
            instance.setManaStat(((CompoundNBT) tag).getInt("manaControlStat"));

            instance.setSwordSlot(1, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot1")));
            instance.setSwordSlot(2, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot2")));
            instance.setSwordSlot(3, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot3")));
            instance.setSwordSlot(4, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot4")));
            instance.setSwordSlot(5, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot5")));
            instance.setSwordSlot(6, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot6")));
            instance.setSwordSlot(7, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot7")));
            instance.setSwordSlot(8, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot8")));
            instance.setSwordSlot(9, ItemStack.of(((CompoundNBT) tag).getCompound("swordslot9")));


            instance.setKeybind(1, ((CompoundNBT) tag).getString("key1"));
            instance.setKeybind(2, ((CompoundNBT) tag).getString("key2"));
            instance.setKeybind(3, ((CompoundNBT) tag).getString("key3"));
            instance.setKeybind(4, ((CompoundNBT) tag).getString("key4"));
            instance.setKeybind(5, ((CompoundNBT) tag).getString("key5"));
            instance.setKeybind(6, ((CompoundNBT) tag).getString("key6"));
            instance.setKeybind(7, ((CompoundNBT) tag).getString("key7"));
            instance.setKeybind(8, ((CompoundNBT) tag).getString("key8"));
            instance.setKeybind(9, ((CompoundNBT) tag).getString("key9"));

            instance.setToggleSpellMessage(((CompoundNBT) tag).getBoolean("spellmessage"));

            instance.setManaBoolean(((CompoundNBT) tag).getBoolean("hasMana"));

            for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
                AbstractSpell abstractSpell = spell;
                instance.setSpellBoolean(spell, ((CompoundNBT) tag).getBoolean(spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_save"));
            }
        }
    }
}