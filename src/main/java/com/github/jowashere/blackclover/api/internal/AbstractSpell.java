package com.github.jowashere.blackclover.api.internal;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.MainPlugin;
import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.widgets.spells.GuiButtonSpell;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketMagicExpSync;
import com.github.jowashere.blackclover.networking.packets.mana.PacketManaSync;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public abstract class AbstractSpell {

    private static final ResourceLocation SPELL_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/spells.png");

    private String name = "";
    private IBCMPlugin correlatedPlugin;
    private BCMAttribute attribute;
    private boolean toggle;
    private int u;
    private int v;
    private float manaCost = 1;
    private int cooldown = 1;
    private boolean skillSpell = false;
    protected AbstractSpell.IAction action;
    protected AbstractSpell.IExtraCheck extraCheck;
    private ResourceLocation resourceLocation;
    private String checkFailMsg;

    private int unlockLevel = 0;

    public AbstractSpell(String registryName, BCMAttribute attribute) {
        this.name = registryName;
        this.correlatedPlugin = new MainPlugin();
        this.attribute = attribute;

        this.resourceLocation = SPELL_LOCATION;
    }

    public IBCMPlugin getCorrelatedPlugin() {
        return correlatedPlugin;
    }

    public void setPlugin(IBCMPlugin pluginIn) {
        this.correlatedPlugin = pluginIn;
    }

    protected void setToggle(boolean toggle){
        this.toggle = toggle;
    }

    public void setCooldown(int cooldown){
        this.cooldown = cooldown;
    }

    public void setManaCost(float mana){
        this.manaCost = mana;
    }

    public void setResourceLocationForGUI(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public void setCheckFailMsg(String checkFailMsg) {
        this.checkFailMsg = checkFailMsg;
    }

    public ResourceLocation getResourceLocationForGUI() {
        return this.resourceLocation;
    }

    public void setUnlockLevel(int level){
        this.unlockLevel = level;
    }

    public void setUV(int u, int v) {
        this.u = u;
        this.v = v;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public boolean isToggle() {
        return toggle;
    }

    public BCMAttribute getAttribute() {
        return attribute;
    }

    public String getName() {
        return name;
    }

    public int getUnlockLevel(){
        if(this.unlockLevel > 0){
            return unlockLevel;
        }
        return 0;
    }

    public int getCooldown(){
        if(cooldown > 0){
            return cooldown;
        }
        return 0;
    }

    public boolean isSkillSpell(){
        return skillSpell;
    }

    public void setSkillSpell(boolean skill){
        this.skillSpell = skill;
    }

    public void act(LivingEntity caster) {

        if(!caster.level.isClientSide) {
            if(caster instanceof PlayerEntity){

                PlayerEntity player = (PlayerEntity) caster;

                IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

                //Check for spell extraChecks
                if (this.extraCheck != null) {
                    if (!this.extraCheck.check(player)) {
                        if (this.isToggle() && !((AbstractToggleSpell) this).isCheckToStart()) {
                            if (checkFailMsg != null) {
                                player.displayClientMessage(new StringTextComponent(checkFailMsg), true);
                            }
                            if (isToggle()) {
                                ((AbstractToggleSpell) this).throwCancelEvent(player);
                                String nbtName = getCorrelatedPlugin().getPluginId() + "_" + getName();
                                player.getPersistentData().putBoolean(nbtName, false);
                                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(player.getId(), nbtName, false));
                            }
                            return;
                        }
                        if (checkFailMsg != null) {
                            player.displayClientMessage(new StringTextComponent(checkFailMsg), true);
                        }
                        return;
                    }
                }

                //Check if toggle timer is up
                if(this instanceof AbstractToggleSpell)
                    ((AbstractToggleSpell) this).toggleTimerChecks(player);

                float manaCost;
                manaCost = this.getManaCost() + ((float) Math.sqrt(playercap.ReturnMagicLevel()) * (this.getManaCost() / 5));

                if (this.isSkillSpell() || playercap.returnHasGrimoire()) {

                    //Check if player has sufficient mana
                    if (!(playercap.returnMana() >= manaCost || playercap.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC))) {
                        if (isToggle()) {
                            ((AbstractToggleSpell) this).throwCancelEvent(player);
                            String nbtName = getCorrelatedPlugin().getPluginId() + "_" + getName();
                            player.getPersistentData().putBoolean(nbtName, false);
                            NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(player.getId(), nbtName, false));
                        }
                        player.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.notenoughmana"), true);
                        return;
                    }

                    //Adds magic experience
                    if (!this.isToggle()) {
                        Beapi.experienceMultiplier(caster, this.getManaCost());
                    } else {
                        Beapi.experienceMultiplier(caster, (this.getManaCost()/2));
                    }
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(playercap.returnMagicExp(), player.getId()));
                    BCMHelper.recaculateMagicLevel(player);

                    //Casts the spell
                    if (this.action != null)
                        this.action.action(player, manaCost);

                    //Handles cooldown
                    if (!this.isToggle())
                        this.applySpellCD(this, player);

                    if (playercap.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC))
                        return;

                    //subtracts mana
                    playercap.addMana((-manaCost));
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(playercap.returnMana()));

                    /*if (!playercap.returnToggleSpellMessage() && !this.isToggle() && !player.level.isClientSide)
                        player.displayClientMessage(new StringTextComponent(new TranslationTextComponent("spell." + this.getCorrelatedPlugin().getPluginId() + "." + this.getName()).getString() + "! " + + ((int) -manaCost) + " Mana"), true);*/
                } else {
                    player.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.nogrimoire"), true);
                }
            }else if(caster instanceof BCEntity) {
                if (this.action != null)
                    this.action.action(caster, manaCost);
            }
        }
    }

    public void update(GuiButtonSpell spellButton, AbstractSpell spell, IPlayerHandler playerCapability) {
        spellButton.setHasSpell(playerCapability.hasSpellBoolean(spell));
    }

    public void sync(IPlayerHandler playerCapability, AbstractSpell spell, boolean has) {
        playerCapability.setSpellBoolean(spell, has);
        //this.sync.update(playerCapability, has);
    }

    public float getManaCost() {
        return this.manaCost;
    }

    public boolean hasSpell(AbstractSpell spell, IPlayerHandler playerCapability) {
        return playerCapability.hasSpellBoolean(spell);
    }

    public enum Type {
        WIND_MAGIC, ANTI_MAGIC, DARKNESS_MAGIC, LIGHTNING_MAGIC, SLASH_MAGIC, LIGHT_MAGIC, SWORD_MAGIC
    }

    public interface IAction {
        void action(LivingEntity livingEntity, float manaIn);
    }

    public interface ISpellServerSync {
        void update(IPlayerHandler playerCapability, boolean has);
    }

    public interface IHasSpell {
        boolean has(IPlayerHandler playerCapability);
    }

    public interface IExtraCheck {
        boolean check(PlayerEntity playerIn);
    }

    protected void applySpellCD(AbstractSpell spell, PlayerEntity player){
        String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";

        player.getPersistentData().putInt(nbtName, spell.getCooldown());
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) player), new PacketIntSpellNBTSync(player.getId(), nbtName, spell.getCooldown()));
    }

    public AbstractSpell.IExtraCheck getExtraCheck() {
        return extraCheck;
    }

    public String getCheckFailMsg(){
        return checkFailMsg;
    }
}
