package com.github.jowashere.blackclover.entities.goals.spells.lightning;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.entities.goals.other.CooldownGoal;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.entities.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.spells.SpellRegistry;
import net.minecraft.util.Hand;

public class ThunderCrumblingOrbGoal extends CooldownGoal
{
    private BCEntity entity;

    public ThunderCrumblingOrbGoal(BCEntity entity)
    {
        super(entity, SpellRegistry.THUNDER_CRUMBLING_ORB);
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

        if (!this.entity.getAttribute().equals(AttributeInit.LIGHTNING))
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

        ThunderOrbEntity projectile = new ThunderOrbEntity(this.entity.level, this.entity, 0);
        projectile.setPos(entity.getX(), entity.getY() + entity.getBbHeight() / 2.0F + 0.5D, entity.getZ());
        projectile.shoot(d1 + entity.getRandom().nextGaussian(), d2, d3 + entity.getRandom().nextGaussian(), 1.5F, 0);
        projectile.setOwner(entity);
        this.entity.level.addFreshEntity(projectile);
        this.entity.swing(Hand.MAIN_HAND, true);

        this.entity.setCurrentGoal(this);
        entity.applySpellCD(spell);
    }
}
