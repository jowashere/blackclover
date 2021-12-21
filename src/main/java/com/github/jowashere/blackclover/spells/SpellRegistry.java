package com.github.jowashere.blackclover.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.spells.antimagic.*;
import com.github.jowashere.blackclover.spells.darkness.*;
import com.github.jowashere.blackclover.spells.light.*;
import com.github.jowashere.blackclover.spells.lightning.ThunderCrumblingOrb;
import com.github.jowashere.blackclover.spells.lightning.ThunderFiend;
import com.github.jowashere.blackclover.spells.lightning.ThunderGodBoots;
import com.github.jowashere.blackclover.spells.lightning.ThunderGodGloves;
import com.github.jowashere.blackclover.spells.slash.DeathScythe;
import com.github.jowashere.blackclover.spells.slash.LunaticSlash;
import com.github.jowashere.blackclover.spells.slash.SlashBlades;
import com.github.jowashere.blackclover.spells.sword.CausalityBreakSword;
import com.github.jowashere.blackclover.spells.sword.OriginFlash;
import com.github.jowashere.blackclover.spells.sword.OriginFlashBarrage;
import com.github.jowashere.blackclover.spells.sword.SpellAbsorption;
import com.github.jowashere.blackclover.spells.wind.*;

public class SpellRegistry {

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry) {

        // Anti-Magic
        spellRegistry.register(new BullThrust());
        spellRegistry.register(new BlackSlash());
        spellRegistry.register(new CausalityBreakAnti());
        spellRegistry.register(new CausalityBreakAntiSelf());
        spellRegistry.register(new BlackDivider());
        spellRegistry.register(new BlackMode());
        spellRegistry.register(new BlackMeteorite());

        // Darkness Magic
        spellRegistry.register(new DarkCloakedBlade());
        spellRegistry.register(new AvidyaSlash());
        spellRegistry.register(new BlackBlade());
        spellRegistry.register(new BlackCocoon());
        spellRegistry.register(new BlackHole());

        // Light Magic
        spellRegistry.register(new LightSword());
        spellRegistry.register(new LightSwordOJ());
        spellRegistry.register(new LightMovement());
        spellRegistry.register(new LightSwordsOJMulti());
        spellRegistry.register(new LightHealing());
        spellRegistry.register(new ArrowsOfJudgement());

        // Lightning Magic
        spellRegistry.register(new ThunderGodBoots());
        spellRegistry.register(new ThunderGodGloves());
        spellRegistry.register(new ThunderCrumblingOrb());
        spellRegistry.register(new ThunderFiend());

        // Slash Magic
        spellRegistry.register(new SlashBlades());
        spellRegistry.register(new DeathScythe());
        spellRegistry.register(new LunaticSlash());

        // Sword Magic
        spellRegistry.register(new OriginFlash());
        spellRegistry.register(new CausalityBreakSword());
        spellRegistry.register(new SpellAbsorption());
        spellRegistry.register(new OriginFlashBarrage());

        // Wind Magic
        spellRegistry.register(new WindBlade());
        spellRegistry.register(new ToweringTornado());
        spellRegistry.register(new WindFlight());
        spellRegistry.register(new WindCrescent());
        spellRegistry.register(new WindBladeShower());
        spellRegistry.register(new WindHawk());

    }

}
