package com.github.jowashere.blackclover.api;

public interface IBCMPlugin {

    String getPluginId();

    void registerNewRaces(BCMRegistry.RaceRegistry raceRegistry);

    void registerNewAttributes(BCMRegistry.AttributeRegistry attributeRegistry);

    void registerNewModes(BCMRegistry.ModeRegistry modeRegistry);

    void registerNewSpells(BCMRegistry.SpellRegistry spellRegistry);

}
