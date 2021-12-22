package com.github.jowashere.blackclover.entities.goals.spells.slash;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.entities.goals.other.CooldownGoal;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.spells.SpellRegistry;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class LunaticSlashGoal extends CooldownGoal
{
    private BCEntity entity;

    public LunaticSlashGoal(BCEntity entity)
    {
        super(entity, SpellRegistry.LUNATIC_SLASH);
        this.entity = entity;
        this.entity.addThreat(10);
    }

    @Override
    public boolean canUse()
    {
        if(!super.canUse())
            return false;

        if (this.entity.getTarget() == null)
            return false;

        if(!this.entity.canSee(this.entity.getTarget()))
            return false;

        if (this.entity.distanceTo(this.entity.getTarget()) > 5)
            return false;

        if(this.entity.getCurrentGoal() != null)
            return false;

        if (Beapi.randomWithRange(1, 10) <= 8)
            return false;

        this.execute();
        return true;
    }

    @Override
    public void onGoalEnd()
    {
        super.onGoalEnd();
        this.entity.setCurrentGoal(null);
        this.entity.setPreviousGoal(this);
    }

    public void execute()
    {
        super.execute();

        RayTraceResult mop = BCMHelper.RayTraceBlocksAndEntities(this.entity, 7);

        List<LivingEntity> entities = BCMHelper.GetEntitiesNear(new BlockPos(mop.getLocation()), this.entity.level, 7, LivingEntity.class);
        if(entities.contains(this.entity))
            entities.remove(this.entity);

        entities.forEach(entity -> {

            int magicLevel = BCMHelper.getMagicLevel(this.entity);

            BCMHelper.doSpellDamage(this.entity, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

            ((ServerWorld) this.entity.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                    entity.getZ(), (int) 10, 3, 3, 3, 0.1);
            (this.entity.level).playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.entity.getSoundSource(), (float) 1, (float) 1);
            this.entity.swing(Hand.MAIN_HAND, true);

            BCMHelper.waitThen(this.entity.level, 11, () -> {

                BCMHelper.doSpellDamage(this.entity, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                ((ServerWorld) this.entity.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                        entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                (this.entity.level).playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.entity.getSoundSource(), (float) 1, (float) 1);
                this.entity.swing(Hand.MAIN_HAND, true);
            });

            BCMHelper.waitThen(this.entity.level, 22, () -> {

                BCMHelper.doSpellDamage(this.entity, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                ((ServerWorld) this.entity.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                        entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                (this.entity.level).playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.entity.getSoundSource(), (float) 1, (float) 1);
                this.entity.swing(Hand.MAIN_HAND, true);
            });
        });

        this.entity.setCurrentGoal(this);
        entity.applySpellCD(spell);

    }
}
