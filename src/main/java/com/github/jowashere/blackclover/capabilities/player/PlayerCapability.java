package com.github.jowashere.blackclover.capabilities.player;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModeInit;
import com.github.jowashere.blackclover.init.RaceInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.AttributeHelper;
import com.github.jowashere.blackclover.util.helpers.ModeHelper;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.PacketDistributor;

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
    public void setKeybindCD(int key, int cd)
    {
        if(key == 1){
            this.key1cd = cd;
        }else if(key == 2){
            this.key2cd = cd;
        }else if(key == 3){
            this.key3cd = cd;
        }else if(key == 4){
            this.key4cd = cd;
        }else if(key == 5){
            this.key5cd = cd;
        }else if(key == 6){
            this.key6cd = cd;
        }else if(key == 7){
            this.key7cd = cd;
        }else if(key == 8){
            this.key8cd = cd;
        }else if(key == 9){
            this.key9cd = cd;
        }
    }
    @Override
    public int returnKeybindCD(int key)
    {
        if(key == 1){
            return this.key1cd;
        }else if(key == 2){
            return this.key2cd;
        }else if(key == 3){
            return this.key3cd;
        }else if(key == 4){
            return this.key4cd;
        }else if(key == 5){
            return this.key5cd;
        }else if(key == 6){
            return this.key6cd;
        }else if(key == 7){
            return this.key7cd;
        }else if(key == 8){
            return this.key8cd;
        }else if(key == 9){
            return this.key9cd;
        }
        return 0;
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

            tag.putString("key1", instance.returnKeybind(1));
            tag.putString("key2", instance.returnKeybind(2));
            tag.putString("key3", instance.returnKeybind(3));
            tag.putString("key4", instance.returnKeybind(4));
            tag.putString("key5", instance.returnKeybind(5));
            tag.putString("key6", instance.returnKeybind(6));
            tag.putString("key7", instance.returnKeybind(7));
            tag.putString("key8", instance.returnKeybind(8));
            tag.putString("key9", instance.returnKeybind(9));

            tag.putInt("key1cd", instance.returnKeybindCD(1));
            tag.putInt("key2cd", instance.returnKeybindCD(2));
            tag.putInt("key3cd", instance.returnKeybindCD(3));
            tag.putInt("key4cd", instance.returnKeybindCD(4));
            tag.putInt("key5cd", instance.returnKeybindCD(5));
            tag.putInt("key6cd", instance.returnKeybindCD(6));
            tag.putInt("key7cd", instance.returnKeybindCD(7));
            tag.putInt("key8cd", instance.returnKeybindCD(8));
            tag.putInt("key9cd", instance.returnKeybindCD(9));

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

            instance.setKeybind(1, ((CompoundNBT) tag).getString("key1"));
            instance.setKeybind(2, ((CompoundNBT) tag).getString("key2"));
            instance.setKeybind(3, ((CompoundNBT) tag).getString("key3"));
            instance.setKeybind(4, ((CompoundNBT) tag).getString("key4"));
            instance.setKeybind(5, ((CompoundNBT) tag).getString("key5"));
            instance.setKeybind(6, ((CompoundNBT) tag).getString("key6"));
            instance.setKeybind(7, ((CompoundNBT) tag).getString("key7"));
            instance.setKeybind(8, ((CompoundNBT) tag).getString("key8"));
            instance.setKeybind(9, ((CompoundNBT) tag).getString("key9"));

            instance.setKeybindCD(1, ((CompoundNBT) tag).getInt("key1cd"));
            instance.setKeybindCD(2, ((CompoundNBT) tag).getInt("key2cd"));
            instance.setKeybindCD(3, ((CompoundNBT) tag).getInt("key3cd"));
            instance.setKeybindCD(4, ((CompoundNBT) tag).getInt("key4cd"));
            instance.setKeybindCD(5, ((CompoundNBT) tag).getInt("key5cd"));
            instance.setKeybindCD(6, ((CompoundNBT) tag).getInt("key6cd"));
            instance.setKeybindCD(7, ((CompoundNBT) tag).getInt("key7cd"));
            instance.setKeybindCD(8, ((CompoundNBT) tag).getInt("key8cd"));
            instance.setKeybindCD(9, ((CompoundNBT) tag).getInt("key9cd"));

            instance.setToggleSpellMessage(((CompoundNBT) tag).getBoolean("spellmessage"));

            instance.setManaBoolean(((CompoundNBT) tag).getBoolean("hasMana"));

            for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                instance.setSpellBoolean(spell, ((CompoundNBT) tag).getBoolean(spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_save"));
            }
        }
    }
}