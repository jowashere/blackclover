package com.github.jowashere.blackclover.entities.goals.spells.wind;

import com.github.jowashere.blackclover.entities.goals.other.CooldownGoal;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;

public class WindBladeShowerGoal extends CooldownGoal
{
    private BCEntity entity;

    public WindBladeShowerGoal(BCEntity entity)
    {
        super(entity, 8, entity.getRandom().nextInt(5));
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

        this.execute();
        return true;
    }

    @Override
    public void endCooldown()
    {
        super.endCooldown();
        this.entity.setCurrentGoal(null);
        this.entity.setPreviousGoal(this);
    }

    public void execute()
    {
        for(int i = 0; i < 15; i++)
        {
            double d1 = entity.getTarget().getX() - entity.getX();
            double d2 = entity.getTarget().getBoundingBox().minY + entity.getTarget().getBbHeight() / 2.0F - (entity.getY() + entity.getBbHeight() / 2.0F);
            double d3 = entity.getTarget().getZ() - entity.getZ();

            WindBladeEntity projectile = new WindBladeEntity(this.entity.level, this.entity, "wind_blade", 0);
            projectile.setPos(projectile.getX(), entity.getY() + entity.getBbHeight() / 2.0F + 0.5D, projectile.getZ());
            projectile.shoot(d1 + entity.getRandom().nextGaussian(), d2, d3 + entity.getRandom().nextGaussian(), 1.5F, 0);
            this.entity.level.addFreshEntity(projectile);

            this.entity.setCurrentGoal(this);
            this.setOnCooldown(true);
        }
    }
}
