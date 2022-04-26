package com.github.jowashere.blackclover.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.spells.antimagic.*;
import com.github.jowashere.blackclover.spells.darkness.*;
import com.github.jowashere.blackclover.spells.light.*;
import com.github.jowashere.blackclover.spells.lightning.ThunderCrumblingOrb;
import com.github.jowashere.blackclover.spells.lightning.ThunderFiend;
import com.github.jowashere.blackclover.spells.lightning.ThunderGodBoots;
import com.github.jowashere.blackclover.spells.lightning.ThunderGodGloves;
import com.github.jowashere.blackclover.spells.slash.*;
import com.github.jowashere.blackclover.spells.lightning.*;
import com.github.jowashere.blackclover.spells.slash.*;
import com.github.jowashere.blackclover.spells.sword.CausalityBreakSword;
import com.github.jowashere.blackclover.spells.sword.OriginFlash;
import com.github.jowashere.blackclover.spells.sword.OriginFlashBarrage;
import com.github.jowashere.blackclover.spells.sword.SpellAbsorption;
import com.github.jowashere.blackclover.spells.water.WaterBall;
import com.github.jowashere.blackclover.spells.water.WaterShield;
import com.github.jowashere.blackclover.spells.wind.*;

import java.util.ArrayList;

public class SpellRegistry {

    public static final ArrayList<AbstractSpell> SPELLS = new ArrayList<>();

    //Anti-Magic
    public static final AbstractSpell BULL_THRUST = register(new BullThrust());
    public static final AbstractSpell BLACK_SLASH = register(new BlackSlash());
    public static final AbstractSpell CAUSALITY_BREAK_ANTI = register(new CausalityBreakAnti());
    public static final AbstractSpell CAUSALITY_BREAK_ANTI_SELF = register(new CausalityBreakAntiSelf());
    public static final AbstractSpell BLACK_DIVIDER = register(new BlackDivider());
    public static final AbstractSpell BLACK_MODE = register(new BlackMode());
    public static final AbstractSpell BLACK_METEORITE =register(new BlackMeteorite());

    //Darkness Magic
    public static final AbstractSpell DARK_CLOAKED_BLADE = register(new DarkCloakedBlade());
    public static final AbstractSpell AVIDYA_SLASH = register(new AvidyaSlash());
    public static final AbstractSpell BLACK_BLADE = register(new BlackBlade());
    public static final AbstractSpell BLACK_COCOON = register(new BlackCocoon());
    public static final AbstractSpell BLACK_HOLE = register(new BlackHole());

    public static final AbstractSpell LIGHT_SWORD = register(new LightSword());
    public static final AbstractSpell LIGHT_SWORD_OJ = register(new LightSwordOJ());
    public static final AbstractSpell LIGHT_MOVEMENT = register(new LightMovement());
    public static final AbstractSpell LIGHT_SWORD_OJ_MULTI = register(new LightSwordsOJMulti());
    public static final AbstractSpell LIGHT_HEALING = register(new LightHealing());
    public static final AbstractSpell ARROWS_OF_JUDGEMENT = register(new ArrowsOfJudgement());

    //Lightning Spells
    public static final AbstractSpell THUNDER_GOD_BOOTS = register(new ThunderGodBoots());
    public static final AbstractSpell THUNDER_GOD_GLOVES = register(new ThunderGodGloves());
    public static final AbstractSpell THUNDER_CRUMBLING_ORB = register(new ThunderCrumblingOrb());
    public static final AbstractSpell THUNDER_CRUMBLING_ORB_MULTI = register(new ThunderCrumblingOrbMulti());
    public static final AbstractSpell THUNDER_FIEND = register(new ThunderFiend());
    public static final AbstractSpell THUNDER_FIEND_STRIKE = register(new ThunderFiendStrike());
    public static final AbstractSpell THUNDER_DISCHARGE = register(new ThunderDischarge());

    //Water Spells
    public static final AbstractSpell WATER_BALL = register(new WaterBall());
    public static final AbstractSpell WATER_SHIELD = register(new WaterShield());

    //Slash Magic
    public static final AbstractSpell SLASH_BLADES = register(new SlashBlades());
    public static final AbstractSpell DEATH_SCYTHE = register(new DeathScythe());
    public static final AbstractSpell LUNATIC_SLASH = register(new LunaticSlash());
    public static final AbstractSpell DEATH_SCYTHE_SHOWER = register(new DeathScytheShower());
    public static final AbstractSpell FORWARD_THRUST = register(new ForwardThrust());
    public static final AbstractSpell ROUND_SCYTHE = register(new RoundLunaticSlash());

    //Sword Magic
    public static final AbstractSpell ORIGIN_FLASH = register(new OriginFlash());
    public static final AbstractSpell CAUSALITY_BREAK_SWORD = register(new CausalityBreakSword());
    public static final AbstractSpell SPELL_ABSORPTION = register(new SpellAbsorption());
    public static final AbstractSpell ORIGIN_FLASH_BARRAGE = register(new OriginFlashBarrage());

    //Wind Magic
    public static final AbstractSpell WIND_BLADE = register(new WindBlade());
    public static final AbstractSpell TOWERING_TORNADO = register(new ToweringTornado());
    public static final AbstractSpell WIND_FLIGHT = register(new WindFlight());
    public static final AbstractSpell WIND_CRESCENT = register(new WindCrescent());
    public static final AbstractSpell WIND_BLADE_SHOWER = register(new WindBladeShower());
    public static final AbstractSpell WIND_HAWK = register(new WindHawk());

    public static AbstractSpell register(AbstractSpell spell) {
        SPELLS.add(spell);
        return spell;
    }

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry) {
        SPELLS.forEach((spell -> {
            spellRegistry.register(spell);
        }));
    }

}
