package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMMode;

public class ModeInit {

    public static final BCMMode NULL = new BCMMode(new IBCMPlugin() {
        @Override
        public String getPluginId() {
            return Main.MODID;
        }

        @Override
        public void RegisterNewAttributes(BCMRegistry.AttributeRegistry attributeRegistry) {

        }

        @Override
        public void RegisterNewRaces(BCMRegistry.RaceRegistry raceRegistry) {

        }

        @Override
        public void RegisterNewModes(BCMRegistry.ModeRegistry modeRegistry) {

        }

        @Override
        public void RegisterNewSpells(BCMRegistry.SpellRegistry spellRegistry) {

        }
    }, "null", 240, 240);
}
