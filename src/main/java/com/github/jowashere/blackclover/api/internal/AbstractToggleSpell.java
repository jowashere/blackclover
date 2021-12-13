package com.github.jowashere.blackclover.api.internal;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.network.PacketDistributor;

public abstract class AbstractToggleSpell extends AbstractSpell {

    private boolean checkToStart = true;
    private int timer = -1;

    protected ICancelEventListener onCancelEvent;
    protected IStartEventListener onStartEvent;
    protected IAttackEventListener onAttackEvent;
    protected IDamageEventListener onDamageEvent;
    protected IDeathEventListener onDeathEvent;

    public AbstractToggleSpell(IBCMPlugin plugin, String registryName, BCMAttribute attribute) {
        super(plugin, registryName, attribute);

        this.setToggle(true);
    }

    public void toggleTimerChecks (PlayerEntity player) {
        if (this.isToggle() && ((AbstractToggleSpell) this).getToggleTimer() > -1) {
            String timerNbt = getCorrelatedPlugin().getPluginId() + "_" + getName() + "_timer";
            int timeRemaining = player.getPersistentData().getInt(timerNbt) - 1;

            if (timeRemaining <= 0) {
                ((AbstractToggleSpell) this).throwCancelEvent(player);
                String nbtName = getCorrelatedPlugin().getPluginId() + "_" + getName();
                player.getPersistentData().putBoolean(nbtName, false);
                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(player.getId(), nbtName, false));
            } else {
                player.getPersistentData().putInt(timerNbt, timeRemaining);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketIntSpellNBTSync(player.getId(), timerNbt, timeRemaining));
            }

        }
    }


    public void checkOnlyToToggle(boolean check) {
        this.checkToStart = check;
    }

    public void setToggleTimer(int timer) {
        this.timer = timer;
    }

    public interface ICancelEventListener {
        void onCancel(LivingEntity caster);
    }

    public interface IStartEventListener {
        void onStart(LivingEntity caster, float manaIn);
    }

    public interface IAttackEventListener {
        void onAttack(LivingEntity attacker, LivingEntity target);
    }

    public interface IDamageEventListener {
        boolean onDamage(float amount, DamageSource source, LivingEntity defender);
    }

    public interface IDeathEventListener {
        boolean onDeath(LivingEntity dyingEntity);
    }

    public void throwStartEvent(LivingEntity livingEntity, float manaIn) {
        if (this.onStartEvent != null)
            this.onStartEvent.onStart(livingEntity, manaIn);
    }
    public void throwCancelEvent(LivingEntity livingEntity) {
        if (this.onCancelEvent != null)
            this.onCancelEvent.onCancel(livingEntity);

        if(livingEntity instanceof PlayerEntity)
        this.applySpellCD(this, (PlayerEntity) livingEntity);
    }
    public void throwAttackEvent(LivingEntity attacker, LivingEntity target) {
        if (this.onAttackEvent != null)
            this.onAttackEvent.onAttack(attacker, target);
    }
    public boolean throwDamageEvent(float amount, DamageSource source, LivingEntity defender) {
        if (this.onDamageEvent != null)
            return this.onDamageEvent.onDamage(amount, source, defender);
        return false;
    }
    public boolean throwDeathEvent(LivingEntity dyingEntity) {
        if (this.onDeathEvent != null)
            return this.onDeathEvent.onDeath(dyingEntity);
        return false;
    }

    public boolean isCheckToStart() {
        return checkToStart;
    }

    public int getToggleTimer(){
        return timer;
    }

}
