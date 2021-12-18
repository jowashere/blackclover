package com.github.jowashere.blackclover.entities.mobs;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.entities.AiSpellEntry;
import com.github.jowashere.blackclover.entities.goals.spells.wind.ToweringTornadoGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.WindBladeGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.WindBladeShowerGoal;
import com.github.jowashere.blackclover.spells.wind.ToweringTornado;
import com.github.jowashere.blackclover.spells.wind.WindBlade;
import com.github.jowashere.blackclover.spells.wind.WindBladeShower;

import java.util.ArrayList;
import java.util.List;

public interface ISpellUser
{
    List<AiSpellEntry> SPELL_POOL = new ArrayList<AiSpellEntry>();

    public default void addSpells(BCEntity entity, int max)
    {
        if (entity.level.isClientSide)
            return;

        if (SPELL_POOL.isEmpty())
        {
            SPELL_POOL.add(new AiSpellEntry(ToweringTornado.INSTANCE.getName().toLowerCase(), 100, new ToweringTornadoGoal(entity)));
            SPELL_POOL.add(new AiSpellEntry(WindBlade.INSTANCE.getName().toLowerCase(), 75, new WindBladeGoal(entity)));
            SPELL_POOL.add(new AiSpellEntry(WindBladeShower.INSTANCE.getName().toLowerCase(), 50, new WindBladeShowerGoal(entity)));
        }

        List<String> goals = new ArrayList<String>();
        int spellCount = 0;

        while(spellCount < max)
        {
            for(AiSpellEntry entry : SPELL_POOL)
            {
                if(!goals.contains(entry.getId()) && Beapi.randomWithRange(1, 15) <= entry.getChance())
                {
                    entity.goalSelector.addGoal(1, entry.getGoal());
                    spellCount++;
                    goals.add(entry.getId());
                    break;
                }
            }

            spellCount++;
        }

    }
}
