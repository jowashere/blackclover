package com.github.jowashere.blackclover.api;

public interface IBCMPlugin {

    String getPluginId();

    void RegisterNewRaces(BCMRegistry.RaceRegistry raceRegistry);

    void RegisterNewAttributes(BCMRegistry.AttributeRegistry attributeRegistry);

    void RegisterNewModes(BCMRegistry.ModeRegistry modeRegistry);

    void RegisterNewSpells(BCMRegistry.SpellRegistry spellRegistry);

}
