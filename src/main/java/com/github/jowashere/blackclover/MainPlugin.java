package com.github.jowashere.blackclover;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModeInit;
import com.github.jowashere.blackclover.init.RaceInit;
import com.github.jowashere.blackclover.spells.SpellRegistry;
import net.minecraft.potion.Effects;

public class MainPlugin implements IBCMPlugin {

    @Override
    public String getPluginId() {
        return Main.MODID;
    }

    @Override
    public void RegisterNewAttributes(BCMRegistry.AttributeRegistry attributeRegistry) {
        attributeRegistry.register(AttributeInit.NULL);
        attributeRegistry.register(AttributeInit.ANTI_MAGIC);
        attributeRegistry.register(AttributeInit.WIND);
        attributeRegistry.register(AttributeInit.LIGHTNING);
        attributeRegistry.register(AttributeInit.DARKNESS);
        attributeRegistry.register(AttributeInit.LIGHT);
        attributeRegistry.register(AttributeInit.SLASH);
        attributeRegistry.register(AttributeInit.SWORD);

    }

    @Override
    public void RegisterNewRaces(BCMRegistry.RaceRegistry raceRegistry) {
        raceRegistry.register(RaceInit.NULL);
        raceRegistry.register(RaceInit.HUMAN);
        raceRegistry.register(RaceInit.ELF);
    }

    @Override
    public void RegisterNewModes(BCMRegistry.ModeRegistry modeRegistry) {
        modeRegistry.register(ModeInit.NULL);
        modeRegistry.register(new BCMMode(this, "black_mode", 480, 0)
                .setAttackingEffect(Effects.WITHER));
    }

    @Override
    public void RegisterNewSpells(BCMRegistry.SpellRegistry spellRegistry) {
        /*WindSpells.registerSpells(spellRegistry, this);
        LightningSpells.registerSpells(spellRegistry, this);
        DarknessSpells.registerSpells(spellRegistry, this);
        AntiMagicSpells.registerSpells(spellRegistry, this);
        SlashMagicSpells.registerSpells(spellRegistry, this);
        SwordSpells.registerSpells(spellRegistry, this);
        LightMagicSpells.registerSpells(spellRegistry, this);*/

        SpellRegistry.registerSpells(spellRegistry);

    }

}
