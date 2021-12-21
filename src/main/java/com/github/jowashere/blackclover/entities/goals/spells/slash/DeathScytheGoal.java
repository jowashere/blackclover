package com.github.jowashere.blackclover.entities.goals.spells.slash;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.goals.other.CooldownGoal;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.spells.slash.DeathScythe;
import net.minecraft.util.Hand;

public class DeathScytheGoal extends CooldownGoal
{
    private BCEntity entity;
    private final AbstractSpell spell = DeathScythe.INSTANCE;

    public DeathScytheGoal(BCEntity entity)
    {
        super(entity, DeathScythe.INSTANCE);
        this.entity = entity;
        this.entity.addThreat(12);
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

        if (this.entity.distanceTo(this.entity.getTarget()) < 5)
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

        double d1 = entity.getTarget().getX() - entity.getX();
        double d2 = entity.getTarget().getBoundingBox().minY + entity.getTarget().getBbHeight() / 2.0F - (entity.getY() + entity.getBbHeight() / 2.0F);
        double d3 = entity.getTarget().getZ() - entity.getZ();

        DeathScytheEntity projectile = new DeathScytheEntity(this.entity.level, this.entity, 0);
        projectile.setPos(entity.getX(), entity.getY() + entity.getBbHeight() / 2.0F + 0.5D, entity.getZ());
        projectile.shoot(d1 + entity.getRandom().nextGaussian(), d2, d3 + entity.getRandom().nextGaussian(), 1.5F, 0);
        projectile.setOwner(entity);
        this.entity.level.addFreshEntity(projectile);
        this.entity.swing(Hand.MAIN_HAND, true);

        this.entity.setCurrentGoal(this);
        entity.applySpellCD(spell);
    }
}
