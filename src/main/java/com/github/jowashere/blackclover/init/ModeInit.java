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
        public void registerNewAttributes(BCMRegistry.AttributeRegistry attributeRegistry) {

        }

        @Override
        public void registerNewRaces(BCMRegistry.RaceRegistry raceRegistry) {

        }

        @Override
        public void registerNewModes(BCMRegistry.ModeRegistry modeRegistry) {

        }

        @Override
        public void registerNewSpells(BCMRegistry.SpellRegistry spellRegistry) {

        }
    }, "null", 240, 240);
}
