package com.github.jowashere.blackclover.entities.goals.spells.wind;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.goals.other.CooldownGoal;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.spells.wind.WindBlade;

public class WindCrescentGoal extends CooldownGoal
{
    private BCEntity entity;
    private AbstractSpell spell = WindBlade.INSTANCE;

    public WindCrescentGoal(BCEntity entity)
    {
        super(entity, WindBlade.INSTANCE);
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

        if (this.entity.distanceTo(this.entity.getTarget()) < 5)
            return false;

        if(this.entity.getCurrentGoal() != null)
            return false;

        if (Beapi.randomWithRange(1, 10) <= 5)
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

        WindCrescentEntity projectile = new WindCrescentEntity(this.entity.level, this.entity, 0);
        projectile.setPos(projectile.getX(), entity.getY() + entity.getBbHeight() / 2.0F + 0.5D, projectile.getZ());
        projectile.shoot(d1 + entity.getRandom().nextGaussian(), d2, d3 + entity.getRandom().nextGaussian(), 1.5F, 0);
        this.entity.level.addFreshEntity(projectile);

        this.entity.setCurrentGoal(this);
        entity.applySpellCD(spell);
    }
}
