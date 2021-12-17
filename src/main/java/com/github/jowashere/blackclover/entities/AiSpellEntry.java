package com.github.jowashere.blackclover.entities;

import net.minecraft.entity.ai.goal.Goal;

public class AiSpellEntry
{
    private String id;
    private Goal goal;
    private int chance;

    public AiSpellEntry(String id, int chance, Goal goal)
    {
        this.id = id;
        this.chance = chance;
        this.goal = goal;
    }

    public String getId()
    {
        return this.id;
    }

    public int getChance()
    {
        return this.chance;
    }

    public Goal getGoal()
    {
        return this.goal;
    }
}
