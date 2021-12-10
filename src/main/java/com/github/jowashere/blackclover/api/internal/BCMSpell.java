package com.github.jowashere.blackclover.api.internal;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.widgets.spells.GuiButtonSpell;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class BCMSpell {

    private static final ResourceLocation SPELL_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/spells.png");

    private final String name;
    private IBCMPlugin correlatedPlugin;
    private final Type type;
    private final boolean toggle;
    private final int u;
    private final int v;
    private final float manaCost;
    private final int cooldown;
    private final boolean skillSpell;
    private final IAction action;
    private ResourceLocation resourceLocation;
    private IExtraCheck extraCheck;
    private ICancelEventListener onCancelEvent;
    private IStartEventListener onStartEvent;
    private IAttackEventListener onAttackEvent;
    private IDamageEventListener onDamageEvent;
    private IDeathEventListener onDeathEvent;
    private String checkFailMsg;
    private boolean checkToStart = true;
    private int timer = -1;
    private int unlockLevel = 0;

    public BCMSpell(IBCMPlugin plugin, String registryName, Type type, float manaCost, int cooldown, boolean skillSpell, int u, int v, boolean toggle, IAction action) {
        this.name = registryName;
        this.correlatedPlugin = plugin;
        this.type = type;
        this.toggle = toggle;
        this.u = u;
        this.v = v;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.skillSpell = skillSpell;
        this.action = action;

        this.resourceLocation = SPELL_LOCATION;
    }

    public IBCMPlugin getCorrelatedPlugin() {
        return correlatedPlugin;
    }

    public BCMSpell setPlugin(IBCMPlugin pluginIn) {
        this.correlatedPlugin = pluginIn;
        return this;
    }

    public BCMSpell setUnlockLevel(int level) {
        this.unlockLevel = level;
        return this;
    }

    public BCMSpell setResourceLocationForGUI(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        return this;
    }

    public BCMSpell setExtraSpellChecks(IExtraCheck extraCheck) {
        this.extraCheck = extraCheck;
        return this;
    }

    public BCMSpell setCheckFailMsg(String checkFailMsg) {
        this.checkFailMsg = checkFailMsg;
        return this;
    }

    public BCMSpell checkOnlyToToggle(boolean check) {
        this.checkToStart = check;
        return this;
    }

    public BCMSpell setToggleTimer(int timer) {
        this.timer = timer;
        return this;
    }

    public BCMSpell addStartEventListener(IStartEventListener onStartEvent) {
        this.onStartEvent = onStartEvent;
        return this;
    }

    public BCMSpell addCancelEventListener(ICancelEventListener onCancelEvent) {
        this.onCancelEvent = onCancelEvent;
        return this;
    }

    public BCMSpell addAttackEventListener(IAttackEventListener onAttackEvent) {
        this.onAttackEvent = onAttackEvent;
        return this;
    }

    public BCMSpell addDamageEventListener(IDamageEventListener onDamageEvent) {
        this.onDamageEvent = onDamageEvent;
        return this;
    }

    public BCMSpell addDeathEventListener(IDeathEventListener onDeathEvent) {
        this.onDeathEvent = onDeathEvent;
        return this;
    }

    public ResourceLocation getResourceLocationForGUI() {
        return this.resourceLocation;
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

    public Type getType() {
        return type;
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

    public void act(PlayerEntity playerIn, int modifier0, int modifier1, int spellKey) {
        if(!playerIn.level.isClientSide) {
            IPlayerHandler playercap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
            if (this.extraCheck != null) {
                if (!this.extraCheck.check(playerIn)) {
                    if(this.isToggle() && !checkToStart){
                        if(checkFailMsg != null){
                            playerIn.displayClientMessage(new StringTextComponent(checkFailMsg), true);
                        }
                        if (isToggle()) {
                            throwCancelEvent(playerIn, spellKey);
                            String nbtName = getCorrelatedPlugin().getPluginId() + "_" + getName();
                            playerIn.getPersistentData().putBoolean(nbtName, false);
                            NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(playerIn.getId(), nbtName, false));
                        }
                        return;
                    }
                    if(checkFailMsg != null){
                        playerIn.displayClientMessage(new StringTextComponent(checkFailMsg), true);
                    }
                    return;
                }
            }

            if(this.isToggle() && this.timer > -1){
                String timerNbt = getCorrelatedPlugin().getPluginId() + "_" + getName() + "_timer";
                int timeRemaining = playerIn.getPersistentData().getInt(timerNbt) - 1;

                if(timeRemaining <= 0){
                    throwCancelEvent(playerIn, spellKey);
                    String nbtName = getCorrelatedPlugin().getPluginId() + "_" + getName();
                    playerIn.getPersistentData().putBoolean(nbtName, false);
                    NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(playerIn.getId(), nbtName, false));
                }else{
                    playerIn.getPersistentData().putInt(timerNbt, timeRemaining);
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) playerIn), new PacketIntSpellNBTSync(playerIn.getId(), timerNbt, timeRemaining));
                }

            }

            float manaCost;
            manaCost = this.getManaCost() +  ((float) Math.sqrt(playercap.ReturnMagicLevel()) * (this.getManaCost() / 5) );

            if(this.isSkillSpell() || playercap.returnHasGrimoire()){
                if (!(playercap.returnMana() >= manaCost || playercap.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC))) {
                    if (isToggle()) {
                        throwCancelEvent(playerIn, spellKey);
                        String nbtName = getCorrelatedPlugin().getPluginId() + "_" + getName();
                        playerIn.getPersistentData().putBoolean(nbtName, false);
                        NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(playerIn.getId(), nbtName, false));
                    }
                    playerIn.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.notenoughmana"), true);
                    return;
                }

                if(!this.isToggle()){
                    playercap.addMagicExp(this.getManaCost());
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerIn), new PacketMagicExpSync(playercap.returnMagicExp(), playerIn.getId()));
                }else {
                    playercap.addMagicExp(this.getManaCost()/2);
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerIn), new PacketMagicExpSync(playercap.returnMagicExp(), playerIn.getId()));
                }
                BCMHelper.recaculateMagicLevel(playerIn);

                this.action.action(playerIn, modifier0, modifier1, playercap, manaCost);
                if(!this.isToggle())
                    this.applySpellCD(this, playerIn, spellKey);

                if(playercap.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC))
                    return;

                playercap.addMana((-manaCost));
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerIn), new PacketManaSync(playercap.returnMana()));

                    /*if (!playercap.returnToggleSpellMessage() && !this.isToggle() && !playerIn.level.isClientSide)
                        playerIn.displayClientMessage(new StringTextComponent(new TranslationTextComponent("spell." + this.getCorrelatedPlugin().getPluginId() + "." + this.getName()).getString() + "! " + + ((int) -manaCost) + " Mana"), true);*/
            }else {
                playerIn.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.nogrimoire"), true);
            }
        }
    }

    public void update(GuiButtonSpell spellButton, BCMSpell spell, IPlayerHandler playerCapability) {
        spellButton.setHasSpell(playerCapability.hasSpellBoolean(spell));
    }


    public void throwStartEvent(PlayerEntity playerIn, float manaIn) {
        if (this.onStartEvent != null)
            this.onStartEvent.onStart(playerIn, manaIn);
    }
    public void throwCancelEvent(PlayerEntity playerIn, int key) {
        if (this.onCancelEvent != null)
            this.onCancelEvent.onCancel(playerIn);
        this.applySpellCD(this, playerIn, key);
    }
    public void throwAttackEvent(PlayerEntity attacker, LivingEntity target) {
        if (this.onAttackEvent != null)
            this.onAttackEvent.onAttack(attacker, target);
    }
    public boolean throwDamageEvent(float amount, DamageSource source, PlayerEntity defender) {
        if (this.onDamageEvent != null)
            return this.onDamageEvent.onDamage(amount, source, defender);
        return false;
    }
    public boolean throwDeathEvent(PlayerEntity dyingEntity) {
        if (this.onDeathEvent != null)
            return this.onDeathEvent.onDeath(dyingEntity);
        return false;
    }

    public void sync(IPlayerHandler playerCapability, BCMSpell spell, boolean has) {
        playerCapability.setSpellBoolean(spell, has);
        //this.sync.update(playerCapability, has);
    }

    public float getManaCost() {
        return this.manaCost;
    }

    public boolean hasSpell(BCMSpell spell, IPlayerHandler playerCapability) {
        return playerCapability.hasSpellBoolean(spell);
    }

    public enum Type {
        WIND_MAGIC, ANTI_MAGIC, DARKNESS_MAGIC, LIGHTNING_MAGIC, SLASH_MAGIC, LIGHT_MAGIC, SWORD_MAGIC
    }

    public interface IAction {
        void action(PlayerEntity playerIn, int modifier0, int modifier1, IPlayerHandler playerCapability, float manaIn);
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

    public interface ICancelEventListener {
        void onCancel(PlayerEntity playerIn);
    }

    public interface IStartEventListener {
        void onStart(PlayerEntity playerIn, float manaIn);
    }

    public interface IAttackEventListener {
        void onAttack(PlayerEntity attacker, LivingEntity target);
    }

    public interface IDamageEventListener {
        boolean onDamage(float amount, DamageSource source, PlayerEntity defender);
    }

    public interface IDeathEventListener {
        boolean onDeath(PlayerEntity dyingEntity);
    }

    private void applySpellCD(BCMSpell spell, PlayerEntity player, int key){


        String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";

        player.getPersistentData().putInt(nbtName, spell.getCooldown());
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) player), new PacketIntSpellNBTSync(player.getId(), nbtName, spell.getCooldown()));

        //NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) player), new PacketKeybindCD(key, spell.getCooldown()));
    }

    public IExtraCheck getExtraCheck() {
        return extraCheck;
    }

    public boolean isCheckToStart() {
        return checkToStart;
    }

    public int getToggleTimer(){
        return timer;
    }

    public String getCheckFailMsg(){
        return checkFailMsg;
    }
}
