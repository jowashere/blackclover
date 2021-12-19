package com.github.jowashere.blackclover.entities.mobs;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.entities.AiSpellEntry;
import com.github.jowashere.blackclover.entities.goals.spells.lightning.ThunderCrumblingOrbGoal;
import com.github.jowashere.blackclover.entities.goals.spells.lightning.ThunderFiendGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.ToweringTornadoGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.WindBladeGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.WindBladeShowerGoal;
import com.github.jowashere.blackclover.spells.lightning.ThunderCrumblingOrb;
import com.github.jowashere.blackclover.spells.lightning.ThunderFiend;
import com.github.jowashere.blackclover.spells.wind.ToweringTornado;
import com.github.jowashere.blackclover.spells.wind.WindBlade;
import com.github.jowashere.blackclover.spells.wind.WindBladeShower;

import java.util.ArrayList;
import java.util.List;

public interface ISpellUser // Basically gives the entities spells of an attribute on a random chance
{
    List<AiSpellEntry> SPELL_POOL = new ArrayList<AiSpellEntry>();

    public default void addSpells(BCEntity entity, int max)
    {
        if (entity.level.isClientSide)
            return;

        if (SPELL_POOL.isEmpty())
        {
            int random_int = Beapi.RNG(2);

            switch (random_int)
            {
                case 0:
                    SPELL_POOL.add(new AiSpellEntry(ToweringTornado.INSTANCE.getName().toLowerCase(), 100, new ToweringTornadoGoal(entity)));
                    SPELL_POOL.add(new AiSpellEntry(WindBlade.INSTANCE.getName().toLowerCase(), 75, new WindBladeGoal(entity)));
                    SPELL_POOL.add(new AiSpellEntry(WindBladeShower.INSTANCE.getName().toLowerCase(), 50, new WindBladeShowerGoal(entity)));
                    break;
                case 1:
                    SPELL_POOL.add(new AiSpellEntry(ThunderCrumblingOrb.INSTANCE.getName().toLowerCase(), 100, new ThunderCrumblingOrbGoal(entity)));
                    SPELL_POOL.add(new AiSpellEntry(ThunderFiend.INSTANCE.getName().toLowerCase(), 75, new ThunderFiendGoal(entity)));
                    break;
            }
        }

        List<String> goals = new ArrayList<String>();
        int spellCount = 0;

        while(spellCount < max)
        {
            for(AiSpellEntry entry : SPELL_POOL)
            {
                if(!goals.contains(entry.getId()) && Beapi.randomWithRange(1, 10) <= entry.getChance())
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
