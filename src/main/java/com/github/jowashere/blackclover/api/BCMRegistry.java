package com.github.jowashere.blackclover.api;

import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMMode;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.api.internal.BCMSpell;

import java.util.ArrayList;
import java.util.List;

public abstract class BCMRegistry<T> {

    public static List<IBCMPlugin> PLUGINS = new ArrayList<>();

    public static BCMRegistry.SpellRegistry SPELLS = new BCMRegistry.SpellRegistry();
    public static BCMRegistry.RaceRegistry RACES = new BCMRegistry.RaceRegistry();
    public static BCMRegistry.AttributeRegistry ATTRIBUTES = new BCMRegistry.AttributeRegistry();
    public static BCMRegistry.ModeRegistry MODES = new BCMRegistry.ModeRegistry();

    public abstract ArrayList<T> getValues();
    public abstract void register(T registryObject);

    public static void registerPlugin(IBCMPlugin plugin) {
        PLUGINS.add(plugin);
    }

    public static class SpellRegistry extends BCMRegistry<BCMSpell> {

        private ArrayList<BCMSpell> SPELLS = new ArrayList<>();

        @Override
        public ArrayList<BCMSpell> getValues() {
            return SPELLS;
        }

        @Override
        public void register(BCMSpell registryObject) {
            SPELLS.add(registryObject);
        }
    }

    public static class RaceRegistry extends BCMRegistry<BCMRace> {

        private ArrayList<BCMRace> RACES = new ArrayList<>();

        @Override
        public ArrayList<BCMRace> getValues() {
            return RACES;
        }

        @Override
        public void register(BCMRace registryObject) {
            RACES.add(registryObject);
        }
    }

    public static class AttributeRegistry extends BCMRegistry<BCMAttribute> {

        private ArrayList<BCMAttribute> ATTRIBUTES = new ArrayList<>();

        @Override
        public ArrayList<BCMAttribute> getValues() {
            return ATTRIBUTES;
        }

        @Override
        public void register(BCMAttribute registryObject) {
            ATTRIBUTES.add(registryObject);
        }
    }

    public static class ModeRegistry extends BCMRegistry<BCMMode> {

        private ArrayList<BCMMode> MODES = new ArrayList<>();

        @Override
        public ArrayList<BCMMode> getValues() {
            return MODES;
        }

        @Override
        public void register(BCMMode registryObject) {
            MODES.add(registryObject);
        }
    }

}
