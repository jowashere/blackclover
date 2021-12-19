package com.github.jowashere.blackclover.entities.goals.other;

import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import net.minecraft.entity.ai.goal.Goal;

public abstract class CooldownGoal extends Goal
{
    private BCEntity entity;
    private boolean isOnCooldown = false;
    protected int maxCooldown, cooldown = 0, randomizer;

    public CooldownGoal(BCEntity entity, int timer, int random)
    {
        this.entity = entity;
        this.maxCooldown = timer;
        this.cooldown = this.maxCooldown;
        this.randomizer = random + 1;
    }

    public CooldownGoal setCooldown (int cooldown)
    {
        this.maxCooldown = cooldown;
        return this;
    }

    @Override
    public boolean canUse()
    {
        if (this.isOnCooldown && this.cooldown <= 0)
            return false;

        if (this.isOnCooldown())
        {
            this.cooldownTick();
            return false;
        }
        return true;
    }

    public void endCooldown()
    {
        this.isOnCooldown = false;
        this.cooldown = this.maxCooldown + this.entity.getRandom().nextInt(this.randomizer);
    }

    public boolean isOnCooldown()
    {
        return this.isOnCooldown;
    }

    public void setOnCooldown(boolean value)
    {
        this.isOnCooldown = value;
    }

    public boolean cooldownTick()
    {
        if (this.isOnCooldown)
        {
            this.cooldown--;
            if (this.cooldown <= 0)
                this.endCooldown();

            return true;
        }

        return false;
    }

}
