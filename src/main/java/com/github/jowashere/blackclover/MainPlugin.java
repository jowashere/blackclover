package com.github.jowashere.blackclover;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.client.renderer.models.ModelBlackMode;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModeInit;
import com.github.jowashere.blackclover.init.RaceInit;
import com.github.jowashere.blackclover.init.spells.wind.WindSpells;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;

public class MainPlugin implements IBCMPlugin {

    @Override
    public String getPluginId() {
        return Main.MODID;
    }

    @Override
    public void registerNewAttributes(BCMRegistry.AttributeRegistry attributeRegistry) {
        attributeRegistry.register(AttributeInit.NULL);
        attributeRegistry.register(AttributeInit.ANTI_MAGIC);
        attributeRegistry.register(AttributeInit.WIND);
    }

    @Override
    public void registerNewRaces(BCMRegistry.RaceRegistry raceRegistry) {
        raceRegistry.register(RaceInit.NULL);
        raceRegistry.register(RaceInit.HUMAN);
        raceRegistry.register(RaceInit.ELF);
    }

    @Override
    public void registerNewModes(BCMRegistry.ModeRegistry modeRegistry) {
        modeRegistry.register(ModeInit.NULL);
        modeRegistry.register(new BCMMode(this, "black_mode", 480, 0)
                .setAttackingEffect(Effects.WITHER).setModelOnRender(new ModelBlackMode(false), new ResourceLocation(Main.MODID, "textures/entities/layers/bodymodes/blackmode.png")));
    }

    @Override
    public void registerNewSpells(BCMRegistry.SpellRegistry spellRegistry) {
        WindSpells.registerWindSpells(spellRegistry, this);
    }

}
