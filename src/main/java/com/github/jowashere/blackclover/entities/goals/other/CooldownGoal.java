package com.github.jowashere.blackclover.entities.goals.other;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.ai.goal.Goal;

//Specific goal for simple spells with a cooldown
public abstract class CooldownGoal extends Goal
{
    private BCEntity entity;
    private AbstractSpell spell;

    public CooldownGoal(BCEntity entity, AbstractSpell associatedSpell)
    {
        this.entity = entity;
        this.spell = associatedSpell;
    }

    //When can the mob use
    @Override
    public boolean canUse()
    {
        boolean isOnCooldown = SpellHelper.hasSpellCooldown(this.entity, this.spell);

        if (isOnCooldown)
        {
            return this.cooldownTick();
        }

        return true;
    }

    public void execute()
    {
        BCMHelper.waitThen(this.entity.level, 10, this::onGoalEnd);
    }

    public void onGoalEnd()
    {

    }

    public boolean cooldownTick()
    {
        if (SpellHelper.hasSpellCooldown(this.entity, this.spell))
        {
            int cooldown = SpellHelper.getSpellCooldown(this.entity, this.spell);
            cooldown--;
            SpellHelper.setSpellCooldown(this.entity, this.spell, cooldown);

            return false;
        }
        return true;
    }

}
