package com.github.jowashere.blackclover.entities.goals.spells.wind;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.entities.goals.other.CooldownGoal;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.spells.SpellRegistry;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class ToweringTornadoGoal extends CooldownGoal
{
    private BCEntity entity;

    public ToweringTornadoGoal(BCEntity entity)
    {
        super(entity, SpellRegistry.TOWERING_TORNADO);
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

        if (!this.entity.getAttribute().equals(AttributeInit.WIND))
            return false;

        if(!this.entity.canSee(this.entity.getTarget()))
            return false;

        if (this.entity.distanceTo(this.entity.getTarget()) > 5)
            return false;

        if(this.entity.getCurrentGoal() != null)
            return false;

        if (Beapi.randomWithRange(1, 10) <= 1)
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

        List<LivingEntity> entities = BCMHelper.GetEntitiesNear(entity.blockPosition(), entity.level, 6F, LivingEntity.class);
        entities.remove(entity);

        entities.forEach(entityi ->
        {
            Vector3d speed = BCMHelper.Propulsion(entity, 2.5, 2.5, 2.5);
            entityi.setDeltaMovement(speed.x, speed.y, speed.z);
            entityi.hurtMarked = true;
            entityi.hasImpulse = true;

            int magicLevel = BCMHelper.getMagicLevel(entity);

            BCMHelper.doSpellDamage(entity, entityi, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

        });
        if (entity.level instanceof ServerWorld) {
            ((ServerWorld) entity.level).sendParticles(ParticleTypes.SPIT, entity.getX(), entity.getY(), entity.getZ(), (int) 100, 3, 2, 3, 1);
        }

        this.entity.setCurrentGoal(this);
        entity.applySpellCD(spell);
    }
}
