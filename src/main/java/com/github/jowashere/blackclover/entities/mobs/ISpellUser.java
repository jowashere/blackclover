package com.github.jowashere.blackclover.entities.mobs;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.entities.AiSpellEntry;
import com.github.jowashere.blackclover.entities.goals.spells.lightning.ThunderCrumblingOrbGoal;
import com.github.jowashere.blackclover.entities.goals.spells.lightning.ThunderFiendGoal;
import com.github.jowashere.blackclover.entities.goals.spells.lightning.ThunderGodBootsGoal;
import com.github.jowashere.blackclover.entities.goals.spells.lightning.ThunderGodGlovesGoal;
import com.github.jowashere.blackclover.entities.goals.spells.slash.DeathScytheGoal;
import com.github.jowashere.blackclover.entities.goals.spells.slash.LunaticSlashGoal;
import com.github.jowashere.blackclover.entities.goals.spells.slash.SlashBladesGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.ToweringTornadoGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.WindBladeGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.WindBladeShowerGoal;
import com.github.jowashere.blackclover.entities.goals.spells.wind.WindCrescentGoal;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.spells.SpellRegistry;

import java.util.ArrayList;
import java.util.List;

public interface ISpellUser // Basically gives the entities spells of an attribute on a random chance
{

    public default void addSpells(BCEntity entity, int max)
    {
        if (entity.level.isClientSide)
            return;

        if (entity.getAttribute() == null)
            return;

        if (entity.SPELL_POOL.isEmpty())
        {
            if(entity.getAttribute().equals(AttributeInit.LIGHTNING)) {
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.THUNDER_GOD_GLOVES.getName().toLowerCase(), 10, new ThunderGodGlovesGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.THUNDER_GOD_BOOTS.getName().toLowerCase(), 10, new ThunderGodBootsGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.THUNDER_CRUMBLING_ORB.getName().toLowerCase(), 8, new ThunderCrumblingOrbGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.THUNDER_FIEND.getName().toLowerCase(), 5, new ThunderFiendGoal(entity)));
            } else if (entity.getAttribute().equals(AttributeInit.WIND)) {
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.TOWERING_TORNADO.getName().toLowerCase(), 10, new ToweringTornadoGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.WIND_BLADE.getName().toLowerCase(), 8, new WindBladeGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.WIND_CRESCENT.getName().toLowerCase(), 7, new WindCrescentGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.WIND_BLADE_SHOWER.getName().toLowerCase(), 5, new WindBladeShowerGoal(entity)));
            } else if(entity.getAttribute().equals(AttributeInit.SLASH)) {
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.SLASH_BLADES.getName().toLowerCase(), 10, new SlashBladesGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.DEATH_SCYTHE.getName().toLowerCase(), 8, new DeathScytheGoal(entity)));
                entity.SPELL_POOL.add(new AiSpellEntry(SpellRegistry.LUNATIC_SLASH.getName().toLowerCase(), 5, new LunaticSlashGoal(entity)));
            }
        }

        List<String> goals = new ArrayList<String>();
        int spellCount = 0;

        while(spellCount < max)
        {
            for(AiSpellEntry entry : entity.SPELL_POOL)
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
