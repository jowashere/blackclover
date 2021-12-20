package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.init.spells.antimagic.*;
import com.github.jowashere.blackclover.init.spells.darkness.*;
import com.github.jowashere.blackclover.init.spells.light.*;
import com.github.jowashere.blackclover.init.spells.lightning.ThunderCrumblingOrb;
import com.github.jowashere.blackclover.init.spells.lightning.ThunderFiend;
import com.github.jowashere.blackclover.init.spells.lightning.ThunderGodBoots;
import com.github.jowashere.blackclover.init.spells.lightning.ThunderGodGloves;
import com.github.jowashere.blackclover.init.spells.slash.DeathScythe;
import com.github.jowashere.blackclover.init.spells.slash.LunaticSlash;
import com.github.jowashere.blackclover.init.spells.slash.SlashBlades;
import com.github.jowashere.blackclover.init.spells.sword.CausalityBreakSword;
import com.github.jowashere.blackclover.init.spells.sword.OriginFlash;
import com.github.jowashere.blackclover.init.spells.sword.OriginFlashBarrage;
import com.github.jowashere.blackclover.init.spells.sword.SpellAbsorption;
import com.github.jowashere.blackclover.init.spells.wind.*;

public class SpellRegistry {

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin plugin) {

        // Anti-Magic
        spellRegistry.register(new BullThrust(plugin));
        spellRegistry.register(new BlackSlash(plugin));
        spellRegistry.register(new CausalityBreakAnti(plugin));
        spellRegistry.register(new CausalityBreakAntiSelf(plugin));
        spellRegistry.register(new BlackDivider(plugin));
        spellRegistry.register(new BlackMode(plugin));
        spellRegistry.register(new BlackMeteorite(plugin));

        // Darkness Magic
        spellRegistry.register(new DarkCloakedBlade(plugin));
        spellRegistry.register(new AvidyaSlash(plugin));
        spellRegistry.register(new BlackBlade(plugin));
        spellRegistry.register(new BlackCocoon(plugin));
        spellRegistry.register(new BlackHole(plugin));

        // Light Magic
        spellRegistry.register(new LightSword(plugin));
        spellRegistry.register(new LightSwordOJ(plugin));
        spellRegistry.register(new LightMovement(plugin));
        spellRegistry.register(new LightSwordsOJMulti(plugin));
        spellRegistry.register(new LightHealing(plugin));
        spellRegistry.register(new ArrowsOfJudgement(plugin));

        // Lightning Magic
        spellRegistry.register(new ThunderGodBoots(plugin));
        spellRegistry.register(new ThunderGodGloves(plugin));
        spellRegistry.register(new ThunderCrumblingOrb(plugin));
        spellRegistry.register(new ThunderFiend(plugin));

        // Slash Magic
        spellRegistry.register(new SlashBlades(plugin));
        spellRegistry.register(new DeathScythe(plugin));
        spellRegistry.register(new LunaticSlash(plugin));

        // Sword Magic
        spellRegistry.register(new OriginFlash(plugin));
        spellRegistry.register(new CausalityBreakSword(plugin));
        spellRegistry.register(new SpellAbsorption(plugin));
        spellRegistry.register(new OriginFlashBarrage(plugin));

        // Wind Magic
        spellRegistry.register(new WindBlade(plugin));
        spellRegistry.register(new ToweringTornado(plugin));
        spellRegistry.register(new WindFlight(plugin));
        spellRegistry.register(new WindCrescent(plugin));
        spellRegistry.register(new WindBladeShower(plugin));
        spellRegistry.register(new WindHawk(plugin));

    }

}
